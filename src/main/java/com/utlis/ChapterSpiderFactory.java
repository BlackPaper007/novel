package com.utlis;

import java.util.List;

import com.entity.Chapter;
import com.impl.chapter.BQGChapterSpider;
import com.impl.chapter.ChapterSpider;
import com.novelEnum.Site;

/**
 * 小说章节工厂类
 * @author smile
 *
 */
public final class ChapterSpiderFactory {

	private  ChapterSpiderFactory() {}

	public static List<Chapter> getChapterSpider(String url) {
		Site site = Site.getEnumByUrl(url);
		List<Chapter> chapters = null;
		switch (site) {
		case uutxt:
		case xbiqige:
		case booktxt:
			chapters = new ChapterSpider().getsChapters(url); break;
		case biquge:
			chapters = new BQGChapterSpider().getsChapters(url); break;
		}
		return chapters;
	}
	
}
