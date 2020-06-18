<<<<<<< HEAD
package com.impl.abs;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.interfaces.INovelSpider;
import com.novelEnum.Site;
import com.utlis.Config;

public abstract class AbstractNovelSpider extends AbstractSpider implements INovelSpider {

	protected Elements getNovelList(String url) throws Exception {
		String result = super.crawl(url);
		Map<String, String> context = Config.getContext(Site.getEnumByUrl(url));
		String novelName = context.get("novel-list");
		Document doc = Jsoup.parse(result);
		doc.setBaseUri(url);
		return doc.select(novelName);
	}
}
=======
package com.impl.abs;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.interfaces.INovelSpider;
import com.novelEnum.Site;
import com.utlis.Config;

public abstract class AbstractNovelSpider extends AbstractSpider implements INovelSpider {

	protected Elements getNovelList(String url) throws Exception {
		String result = super.crawl(url);
		Map<String, String> context = Config.getContext(Site.getEnumByUrl(url));
		String novelName = context.get("novel-list");
		Document doc = Jsoup.parse(result);
		doc.setBaseUri(url);
		return doc.select(novelName);
	}
}
>>>>>>> second commit
