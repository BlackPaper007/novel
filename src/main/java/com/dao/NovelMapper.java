package com.dao;

import java.util.List;

import com.entity.Novel;

public interface NovelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Novel record);

    int insertSelective(Novel record);

    Novel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Novel record);

    int updateByPrimaryKey(Novel record);
    
    void batchInsert(List<Novel> novels);
    
    List<Novel> selectByBooknameOrAuthor(String keyword);
    
    void deleteAll();
}