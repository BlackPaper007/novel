package com.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.NovelMapper;
import com.entity.Novel;
import com.service.NovelService;

@Service
public class NovelServiceImpl implements NovelService {

	@Autowired NovelMapper novelMapper;
	
	@Override
	public List<Novel> getsNovelByKeyword(String keyword) {
		keyword = "%" + keyword + "%";
		List<Novel> list = novelMapper.selectByBooknameOrAuthor(keyword);
		return list;
	}

}
