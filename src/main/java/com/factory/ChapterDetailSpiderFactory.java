package com.factory;

import com.impl.detail.ChapterDetailSpider;
import com.interfaces.IChapterDetail;
import com.Enum.Site;

/**
 * 章节内容工厂类
 * @author smile
 *
 */
public final class ChapterDetailSpiderFactory {

	private ChapterDetailSpiderFactory() {}

	/*
	 * 实现IChapterSpider接口
	 */
	public static IChapterDetail getChapterDetail(String url) {
		Site site = Site.getEnumByUrl(url);
		IChapterDetail chapterDetail = null;
		switch (site) {
			case biquge:
			case uutxt:
			case xbiquge:
			case booktxt:
			case biquku:
				chapterDetail = new ChapterDetailSpider(); break;
			}
		return chapterDetail;
	}

}
