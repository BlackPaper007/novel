<<<<<<< HEAD
package com.impl.abs;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.entity.ChapterDetail;
import com.interfaces.IChapterDetail;
import com.novelEnum.Site;
import com.utlis.ChapterSpiderUtil;
import com.utlis.Config;

/**
 * 抓取章节内容
 * @author smile
 *
 */
public abstract class AbstractChapterDetailSpider extends AbstractSpider implements IChapterDetail{

	@Override
	public ChapterDetail getChapterDetail(String url){
		try {
			String result = crawl(url);
			result = result
					.replace("&nbsp;", "  ")
					.replace("<br>", "${line}")
					.replace("<br />", "${line}")
					.replace("<br/>", "${line}");
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
			detail.setPrev(doc.select(prevSelector)
					.get(ChapterSpiderUtil.Index(prevSelector)).attr("abs:href"));
			
			String nextSelector = contexts.get("chapter-detail-next");
			detail.setNext(doc.select(nextSelector)
					.get(ChapterSpiderUtil.Index(nextSelector)).attr("abs:href"));
			return detail;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
=======
package com.impl.abs;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.entity.ChapterDetail;
import com.interfaces.IChapterDetail;
import com.novelEnum.Site;
import com.utlis.ChapterSpiderUtil;
import com.utlis.Config;

/**
 * 抓取章节内容
 * @author smile
 *
 */
public abstract class AbstractChapterDetailSpider extends AbstractSpider implements IChapterDetail{

	@Override
	public ChapterDetail getChapterDetail(String url){
		try {
			String result = crawl(url);
			result = result
					.replace("&nbsp;", "  ")
					.replace("<br>", "${line}")
					.replace("<br />", "${line}")
<<<<<<< HEAD
=======
					.replace("内容更新后，请重新刷新页面，即可获取最新更新！", "")
>>>>>>> second commit
					.replace("<br/>", "${line}");
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
			detail.setPrev(doc.select(prevSelector)
					.get(ChapterSpiderUtil.Index(prevSelector)).attr("abs:href"));
			
			String nextSelector = contexts.get("chapter-detail-next");
			detail.setNext(doc.select(nextSelector)
					.get(ChapterSpiderUtil.Index(nextSelector)).attr("abs:href"));
			return detail;
		} catch (Exception e) {
<<<<<<< HEAD
			throw new RuntimeException(e);
=======
			log.info("-----章节内容抓取失败----，链接："+url,e);
			throw new RuntimeException();
>>>>>>> second commit
		}
	}
}
>>>>>>> second commit
