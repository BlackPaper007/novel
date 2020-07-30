package com.impl.novels;

import java.util.List;

import com.entity.Novel;
import com.impl.abs.AbstractNovelSpider;

public class XBQGNovelSpider extends AbstractNovelSpider {
	
	@Override
	public List<Novel> getNovel(String url) {
		return super.get(url);
	}
}
