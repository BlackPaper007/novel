
package com.factory;

import com.impl.chapter.BQGChapterSpider;
import com.impl.chapter.ChapterSpider;
import com.impl.chapter.DDChapterSpider;
import com.interfaces.IChapterSpider;
import com.Enum.Site;

/**
 * 小说章节工厂类
 * @author smile
 *
 */
public final class ChapterSpiderFactory {

	private  ChapterSpiderFactory() {}

	public static IChapterSpider getChapterSpider(String url) {
		Site site = Site.getEnumByUrl(url);
		IChapterSpider chapters = null;
		switch (site) {
		case uutxt:
		case xbiquge:
		case biquku:
			chapters = new ChapterSpider(); break;
		case booktxt:
			chapters = new DDChapterSpider(); break;
		case biquge:
			chapters = new BQGChapterSpider(); break;
		}
		return chapters;
	}

}
