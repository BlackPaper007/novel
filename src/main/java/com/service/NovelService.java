package com.service;

import java.util.List;

import com.entity.Novel;

public interface NovelService {

	public List<Novel> getsNovelByKeyword(String keyword);
}
