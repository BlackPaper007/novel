package com.interfaces;

import java.util.List;

import com.entity.Chapter;

public interface IChapterSpider {

	/**
	 * 得到所有章节
	 * @param url
	 * @return
	 */
	public List<Chapter> getChapters(String url);
	
}
