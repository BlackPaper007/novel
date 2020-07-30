package com.interfaces;

import java.util.List;

import com.entity.Novel;

public interface INovelSpider {

	/**
	 * 获取当前站点所有书籍
	 * @param url
	 * @return
	 */
	public List<Novel> getNovel(String url);
}
