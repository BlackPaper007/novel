package com.factory;

import com.impl.novels.NovelSpider;
import com.interfaces.INovelSpider;
import com.Enum.Site;

public final class NovelSpiderFactory {

	public static INovelSpider getNovelSpider(String url) {
		Site site = Site.getEnumByUrl(url);
		switch (site) {
		case booktxt :
		case xbiquge :
		case biquge :
		case biquku : return new NovelSpider();
		default : throw new RuntimeException(url + "暂时不被支持");
		}
	}
}

