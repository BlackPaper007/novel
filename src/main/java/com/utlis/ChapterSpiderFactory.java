<<<<<<< HEAD
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
=======
package com.utlis;

import java.util.List;

import com.entity.Chapter;
import com.impl.chapter.BQGChapterSpider;
import com.impl.chapter.ChapterSpider;
<<<<<<< HEAD
=======
import com.impl.chapter.DDChapterSpider;
>>>>>>> second commit
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
<<<<<<< HEAD
		case xbiqige:
		case booktxt:
			chapters = new ChapterSpider().getsChapters(url); break;
=======
		case xbiquge:
		case biquku:
			chapters = new ChapterSpider().getsChapters(url); break;
		case booktxt:
			chapters = new DDChapterSpider().getsChapters(url); break;
>>>>>>> second commit
		case biquge:
			chapters = new BQGChapterSpider().getsChapters(url); break;
		}
		return chapters;
	}
	
}
>>>>>>> second commit
