<<<<<<< HEAD
package com.utlis;

import com.impl.novels.BQGNovelSpider;
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
		case biquge : return new BQGNovelSpider();
		default : throw new RuntimeException(url + "暂时不被支持");
		}
	}
}
=======
package com.utlis;

import com.impl.novels.BQGNovelSpider;
<<<<<<< HEAD
=======
import com.impl.novels.BQKNovelSpider;
>>>>>>> second commit
import com.impl.novels.DDNovelSpider;
import com.impl.novels.XBQGNovelSpider;
import com.interfaces.INovelSpider;
import com.novelEnum.Site;

public final class NovelSpiderFactory {

	public static INovelSpider getNovelSpider(String url) {
		Site novelSiteEnum = Site.getEnumByUrl(url);
		switch (novelSiteEnum) {
<<<<<<< HEAD
		case xbiqige : return new XBQGNovelSpider(); 
=======
		case biquku : return new BQKNovelSpider(); 
		case xbiquge : return new XBQGNovelSpider(); 
>>>>>>> second commit
		case booktxt : return new DDNovelSpider();
		case biquge : return new BQGNovelSpider();
		default : throw new RuntimeException(url + "暂时不被支持");
		}
	}
}
>>>>>>> second commit
