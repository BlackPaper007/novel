package com.utlis;

import com.impl.chapter.BQGChapterDetailSpider;
import com.impl.chapter.ChapterDetailSpider;
import com.interfaces.IChapterDetail;
import com.novelEnum.Site;

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
		case uutxt:
		case xbiqige:
		case booktxt:
			chapterDetail = new ChapterDetailSpider(); break;
		case biquge:
			chapterDetail = new BQGChapterDetailSpider(); break;
		}
		return chapterDetail;
	}
	
}
