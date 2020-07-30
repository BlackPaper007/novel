package com.factory;

import com.impl.novels.BQGNovelSpider;
import com.impl.novels.BQKNovelSpider;
import com.impl.novels.DDNovelSpider;
import com.impl.novels.XBQGNovelSpider;
import com.interfaces.INovelSpider;
import com.novelEnum.Site;

public final class NovelSpiderFactory {

	public static INovelSpider getNovelSpider(String url) {
		Site novelSiteEnum = Site.getEnumByUrl(url);
		switch (novelSiteEnum) {
		case xbiquge : return new XBQGNovelSpider(); 
		case booktxt : return new DDNovelSpider();
		case biquge : return new BQGNovelSpider();
		case biquku : return new BQKNovelSpider();
		default : throw new RuntimeException(url + "暂时不被支持");
		}
	}
}

