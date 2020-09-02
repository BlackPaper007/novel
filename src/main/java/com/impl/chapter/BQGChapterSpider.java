package com.impl.chapter;

import java.util.List;
import java.util.stream.Collectors;

import com.entity.Chapter;
import com.entity.Result;
import com.impl.abs.AbstractChapterSpider;

/**
 *biquge.lu
 */
public class BQGChapterSpider extends AbstractChapterSpider {

	/**
	 * 过滤前12条重复数据
	 * @param url
	 * @return
	 */
	@Override
	public List<Chapter> getChapters(String url) {
		return super.getChapters(url).stream().skip(12).collect(Collectors.toList());
	}
}