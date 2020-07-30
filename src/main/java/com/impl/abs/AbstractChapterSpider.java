package com.impl.abs;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.entity.Chapter;
import com.entity.Novel;
import com.interfaces.IChapterSpider;
import com.novelEnum.Site;
import com.utlis.ChapterSpiderUtil;
import com.utlis.Config;

/**
 * 抓取所有章节
 * @author smile
 *
 */
public abstract class AbstractChapterSpider extends AbstractSpider implements IChapterSpider{

	@Override
	public List<Chapter> getsChapters(String url) {
		try {
			String result = crawl(url);
			Document doc = Jsoup.parse(result);
			//设置站点地址
			doc.setBaseUri(url);
			String selector = Config.getContext(Site.getEnumByUrl(url)).get("selector");
			String title = Config.getContext(Site.getEnumByUrl(url)).get("novel-title");
			//过滤字段
			ChapterSpiderUtil.selectorFiter(url,doc);
			Elements select = doc.select(selector);

			List<Chapter> chapters = new ArrayList<>();
			for (Element a : select) {
				Chapter chapter = new Chapter();
				chapter.setTitle(a.text());
				//获取每一章绝对路径的url，需要设置BaseUri不然会出现输出空格的情况
				chapter.setOriUrl(a.absUrl("href"));
				chapters.add(chapter);
			}
			return chapters;
		} catch (Exception e) {
			log.info("-----章节目录抓取失败----，链接："+url);
			throw new RuntimeException(e);
		}
	}

}

