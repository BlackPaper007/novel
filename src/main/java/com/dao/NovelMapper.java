package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.entity.Novel;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface NovelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Novel record);

    int insertSelective(Novel record);

    Novel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Novel record);

    int updateByPrimaryKey(Novel record);

    int batchInsert(List<Novel> novels);

    int updateBatch(@Param("novels")List<Novel> novels);

    List<Novel> selectByBooknameOrAuthor(String keyword);

    List<Novel> selectBySite(Integer platformId);

    int deleteAll();
}
