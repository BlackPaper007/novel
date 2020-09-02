package com.entity;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Packed;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
public class Novel {
    private transient static final String EMPTY = "";

    private Integer id;
    /** 书名 */
    private String name;
    /** 作者 */
    private String author;
    /** 原链接 */
    private String url;
    /** 书籍类别 */
    private String type;
    /** 简介 */
    private String info;
    /** 最新章节 */
    private String latelychapter;
    /** 最新章节链接 */
    private String latelychapterurl;
    /** 最新更新时间 */
    private Long latelytime;
    /** 是否完结 1连载 2完结 */
    private Integer status;
    /** 平台id 根据枚举类 */
    private Integer platformId;
    private Long updateTime;

    public Long getUpdateTime() {
        return System.currentTimeMillis();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLatelychapter() {
        return latelychapter;
    }

    public void setLatelychapter(String latelychapter) {
        this.latelychapter = latelychapter;
    }

    public String getLatelychapterurl() {
        return latelychapterurl;
    }

    public void setLatelychapterurl(String latelychapterurl) {
        this.latelychapterurl = latelychapterurl;
    }

    public Long getLatelytime() {
        return latelytime;
    }

    public void setLatelytime(Long latelytime) {
        this.latelytime = latelytime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    @JsonIgnore
    public byte[] getProtobufBody(){
        NovelProto.novel.Builder builder = NovelProto.novel.newBuilder();
        builder.setId(id == null ? Integer.valueOf(EMPTY) : id)
                .setName(name == null ? EMPTY : name)
                .setAuthor(author == null ? EMPTY : author)
                .setUrl(url == null ? EMPTY : url)
                .setType(type == null ? EMPTY : type)
                .setInfo(info == null ? EMPTY : info)
                .setLatelychapter(latelychapter == null ? EMPTY : latelychapter)
                .setLatelychapterurl(latelychapterurl == null ? EMPTY : latelychapterurl)
                .setLatelytime(latelytime == null ? 0 : latelytime)
                .setStatus(status == null ? 0 : status)
                .setPlatformId(platformId == null ? 0 : platformId)
                .setGetUpdateTime(updateTime == null ? 0 : updateTime);
        return builder.build().toByteArray();
    }
}
