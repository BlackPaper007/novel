package com.interfaces;

import com.entity.ChapterDetail;

public interface IChapterDetail {

	/**
	 * 得到章节内容
	 * @param url
	 * @return
	 * @throws Exception 
	 */
	public ChapterDetail getChapterDetail(String url);
}
