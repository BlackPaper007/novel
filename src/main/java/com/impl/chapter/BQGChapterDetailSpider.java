package com.impl.chapter;


import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.entity.ChapterDetail;
import com.impl.abs.AbstractChapterDetailSpider;
import com.novelEnum.Site;
import com.utlis.ChapterSpiderUtil;
import com.utlis.Config;

/**
 * biquge.lu
 * @author smile
 *
 */
public class BQGChapterDetailSpider extends AbstractChapterDetailSpider {

	@Override
	public ChapterDetail getChapterDetail(String url) {
		try {
			String result = crawl(url);
			result = result
					.replace("&nbsp;", "  ")
					.replace("　　("+url+")", "")
					.replace("　　请记住本书首发域名：www.biquge.lu。笔趣阁手机版阅读网址：m.biquge.lu", "")
					.replace("<br />", "${line}");
			Document doc = Jsoup.parse(result);
			ChapterSpiderUtil.selectorFiter(url,doc);
			doc.setBaseUri(url);
			Map<String, String> contexts = Config.getContext(Site.getEnumByUrl(url));
			
			String title = contexts.get("chapter-detail-title");
			ChapterDetail detail = new ChapterDetail();
			detail.setTitle(doc.select(title).first().text());
			
			String contentSelector = contexts.get("chapter-detail");
			detail.setContent(doc.select(contentSelector).first().text().replace("${line}", "\n"));
			
			String prevSelector = contexts.get("chapter-detail-prev");
			detail.setPrev(doc.select(prevSelector).get(0).attr("abs:href"));
			detail.setNext(doc.select(prevSelector).get(2).attr("abs:href"));
			return detail;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}