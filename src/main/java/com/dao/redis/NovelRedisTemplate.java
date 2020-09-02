package com.dao.redis;

import com.Enum.Site;
import com.entity.Novel;
import com.entity.NovelProto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class NovelRedisTemplate extends RedisTemplate<String, Novel> implements RedisSerializer<Novel> {

    private NovelRedisTemplate() {
        StringRedisSerializer stringSerializer = new StringRedisSerializer();

        setKeySerializer(stringSerializer);
        setHashKeySerializer(stringSerializer);

        setValueSerializer(this);
        setHashValueSerializer(this);
    }



    public NovelRedisTemplate(RedisConnectionFactory connectionFactory) {
        this();
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }

    @Override
    protected RedisConnection preProcessConnection(RedisConnection connection, boolean existingConnection) {
        return new DefaultStringRedisConnection(connection);
    }




    public void set(Novel novel) {
        String key = String.valueOf(novel.getId());
        super.boundValueOps(key).set(novel);
    }

    public void list(List<Novel> novel) {
        Set<ZSetOperations.TypedTuple<Novel>> tuples =
                novel.stream().map(c -> new DefaultTypedTuple<Novel>(c, 1.0)).collect(Collectors.toSet());
        Site url = Site.getEnumByUrl(novel.get(0).getUrl());
        String key = url + "_" + UUID.randomUUID();
        super.boundZSetOps(key).add(tuples);
    }

    public List<Novel> lGet(String key, long start, long end) {
        return super.opsForList().range(key,start,end);
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public long lGetListSize(String key) {
        try {
            return super.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public byte[] serialize(Novel novel) throws SerializationException {
        if (novel == null) {
            log.error("Failed to serialize, obj is null");
            return new byte[0];
        }
        return novel.getProtobufBody();
    }

    @Override
    public Novel deserialize(byte[] bytes) throws SerializationException {
        if (bytes.length == 0) {
            log.error("Failed to deserialize, obj is empty");
            return null;
        }
        try {
            NovelProto.novel n = NovelProto.novel.parseFrom(bytes);
            Novel novel = new Novel();
            novel.setId(n.getId());
            novel.setAuthor(StringUtils.trimToEmpty(n.getAuthor()));
            novel.setName(StringUtils.trimToNull(n.getName()));
            novel.setUrl(StringUtils.trimToNull(n.getUrl()));
            novel.setType(StringUtils.trimToNull(n.getType()));
            novel.setInfo(StringUtils.trimToNull(n.getInfo()));
            novel.setLatelychapter(StringUtils.trimToNull(n.getLatelychapter()));
            novel.setLatelychapterurl(StringUtils.trimToNull(n.getLatelychapterurl()));
            novel.setLatelytime(n.getLatelytime());
            novel.setPlatformId(n.getPlatformId());
            novel.setUpdateTime(n.getGetUpdateTime());
            return novel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
