package com.utlis;

import com.impl.novels.DDNovelSpider;
import com.impl.novels.XBQGNovelSpider;
import com.interfaces.INovelSpider;
import com.novelEnum.Site;

public final class NovelSpiderFactory {

	public static INovelSpider getNovelSpider(String url) {
		Site novelSiteEnum = Site.getEnumByUrl(url);
		switch (novelSiteEnum) {
		case xbiqige : return new XBQGNovelSpider();
		case booktxt : return new DDNovelSpider();
		default : throw new RuntimeException(url + "暂时不被支持");
		}
	}
}
