package com.impl.novels.job;

import java.util.concurrent.Callable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.entity.Novel;
import com.impl.abs.AbstractSpider;
import com.novelEnum.Site;
import com.utlis.ChapterSpiderUtil;
import com.utlis.Config;

public class XBQGNovelCallable extends AbstractSpider implements Callable<Novel>{

	private Element e;
	private int tries;
	
	public XBQGNovelCallable(Element e,int tries) {
		this.e = e;
		this.tries = tries;
	}
	
	@Override
	public Novel call() throws Exception {
		Novel novel = new Novel();
		for (int i = 0; i < tries; i++) {
			try {
				String url = e.attr("abs:href");
				String novelName = e.text();
				String result = super.crawl(url);
				result = result.replace("&nbsp;", " ");
				Document doc = Jsoup.parse(result);
				String info = Config.getContext(Site.getEnumByUrl(url)).get("novel-info");
				String type = Config.getContext(Site.getEnumByUrl(url)).get("novel-type");
				Elements maininfo = doc.select(info);
				Elements p = maininfo.select("div[id=info]").select("p");
				Elements intro = maininfo.select("div[id=intro]").select("p");

				novel.setName(novelName);
				novel.setUrl(url);
				novel.setAuthor(p.get(0).text().substring(p.get(0).text().indexOf("：") + 1));
				String strDate = p.get(2).text();
				strDate = strDate.substring(strDate.indexOf("：") + 1);
				novel.setLatelytime(ChapterSpiderUtil.getData(strDate, "yyyy-MM-dd HH:mm:ss"));
				novel.setLatelychapter(p.get(3).select("a").text());
				novel.setLatelychapterurl((p.get(3).select("a").attr("abs:href")));
				novel.setInfo(intro.get(1).text());
				novel.setPlatformId(Site.getEnumByUrl(url).getId());
				novel.setType(doc.select(type).get(ChapterSpiderUtil.Index(type)).text());
				break;
			} catch (NullPointerException e) {
				log.warn("空指针的小说是："+novel);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("响应代码："+http.getCode());
				log.info("尝试第[" + (i + 1) + "/" + tries + "]次抓取失败了！");
			}
		}
		return novel;
	}
}
