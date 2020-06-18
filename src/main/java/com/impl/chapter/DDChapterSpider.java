<<<<<<< HEAD
package com.impl.chapter;

import java.util.List;

import com.entity.Chapter;
import com.impl.abs.AbstractChapterSpider;

/**
 *booktxt.net
 */
public class DDChapterSpider extends AbstractChapterSpider {

	@Override
	public List<Chapter> getsChapters(String url) {
		List<Chapter> chapters = super.getsChapters(url);
		for (int i = 0; i < 8; i++) {
			chapters.remove(0);
		}
		return chapters;
	}
}
=======
package com.impl.chapter;

import java.util.List;

<<<<<<< HEAD
=======

>>>>>>> second commit
import com.entity.Chapter;
import com.impl.abs.AbstractChapterSpider;

/**
 *booktxt.net
 */
public class DDChapterSpider extends AbstractChapterSpider {

	@Override
	public List<Chapter> getsChapters(String url) {
		List<Chapter> chapters = super.getsChapters(url);
<<<<<<< HEAD
		for (int i = 0; i < 8; i++) {
=======
		for (int i = 0; i <= 5; i++) {
>>>>>>> second commit
			chapters.remove(0);
		}
		return chapters;
	}
<<<<<<< HEAD
=======
	
	
>>>>>>> second commit
}
>>>>>>> second commit
