package com.impl.chapter;

import java.util.List;
import java.util.stream.Collectors;

import com.entity.Chapter;
import com.impl.abs.AbstractChapterSpider;

/**
 *booktxt.net
 */
public class DDChapterSpider extends AbstractChapterSpider {

	/**
	 * 过滤前6条
	 * @param url
	 * @return
	 */
	@Override
	public List<Chapter> getChapters(String url) {
		return super.getChapters(url).stream().skip(6).collect(Collectors.toList());
	}
}
