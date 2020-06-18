<<<<<<< HEAD
package com.impl.chapter;

import java.util.List;

import com.entity.Chapter;
import com.impl.abs.AbstractChapterSpider;

/**
 *biquge.lu
 */
public class BQGChapterSpider extends AbstractChapterSpider {

	@Override
	public List<Chapter> getsChapters(String url) {
		List<Chapter> chapters = super.getsChapters(url);
		for (int i = 0; i < 12; i++) {
			chapters.remove(0);
		}
		return chapters;
	}
}
=======
package com.impl.chapter;

import java.util.List;

import com.entity.Chapter;
import com.impl.abs.AbstractChapterSpider;

/**
 *biquge.lu
 */
public class BQGChapterSpider extends AbstractChapterSpider {

	@Override
	public List<Chapter> getsChapters(String url) {
		List<Chapter> chapters = super.getsChapters(url);
		for (int i = 0; i < 12; i++) {
			chapters.remove(0);
		}
		return chapters;
	}
}
>>>>>>> second commit
