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

public class DDNovelCallable extends AbstractSpider implements Callable<Novel> {

	private String url;
	private String name;
	private int tries;
	
	public DDNovelCallable(String url, String name, int tries) {
		super();
		this.url = url;
		this.name = name;
		this.tries = tries;
	}


	@Override
	public Novel call() throws Exception {
		Novel novel = new Novel();
		for (int i = 0; i < tries; i++) {
			try {
				String result = super.crawl(url);
				result = result.replace("&nbsp;", " ");
				Document doc = Jsoup.parse(result);
				String info = Config.getContext(Site.getEnumByUrl(url)).get("novel-info");
				String type = Config.getContext(Site.getEnumByUrl(url)).get("novel-type");
				String chapter = Config.getContext(Site.getEnumByUrl(url)).get("selector");
				Elements maininfo = doc.select(info);
				Elements p = maininfo.select("#info").select("p");
				Elements intro = maininfo.select("#intro").select("p");
				Element latelyChapter = doc.select(chapter).last();
				doc.setBaseUri(url);
				
				novel.setName(name);
				novel.setUrl(url);
				try {
					novel.setPlatformId(Site.getEnumByUrl(url).getId());
					novel.setInfo(intro.get(0).text());
					novel.setType(ChapterSpiderUtil.getStr(doc.select(type).text()));
					novel.setAuthor(p.get(0).text().substring(p.get(0).text().indexOf("：") + 1));
					String strDate = p.get(2).text();
					strDate = strDate.substring(strDate.indexOf("：") + 1);
					novel.setLatelytime(ChapterSpiderUtil.getData(strDate, "yyyy-MM-dd HH:mm:ss"));
					novel.setLatelychapter(latelyChapter.text());
					novel.setLatelychapterurl(latelyChapter.attr("abs:href"));
				} catch (NullPointerException e1) {
					log.warn("空指针的小说是："+p.get(0).text()+"===="+url);
					return novel;
				}
				break;
			} catch (Exception e) {
				e.printStackTrace();
				log.info("响应代码："+http.getCode());
				log.info("尝试第[" + (i + 1) + "/" + tries + "]次抓取失败了！");
			}
		}
		return novel;
	}
}