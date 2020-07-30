package com.service;

import java.util.List;

import com.entity.Novel;

public interface NovelService {

	public List<Novel> getNovelsByKeyword(String keyword);

	public Novel getNovelByKeyword(String keyword);

}

