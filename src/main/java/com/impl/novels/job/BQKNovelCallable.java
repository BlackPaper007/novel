package com.impl.novels.job;

import java.util.concurrent.Callable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.entity.Novel;
import com.impl.abs.AbstractSpider;
import com.novelEnum.Site;
import com.utlis.ChapterSpiderUtil;
import com.utlis.Config;

public class BQKNovelCallable extends AbstractSpider implements Callable<Novel>{

	private String url;
	private String name;
	private int tries;
	
	public BQKNovelCallable(String url, String name, int tries) {
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
				doc.setBaseUri(url);
				String info = Config.getContext(Site.getEnumByUrl(url)).get("novel-info");
				Elements maininfo = doc.select(info);
				Elements small = maininfo.select("div[id=info]").select("p");
				Elements intro = maininfo.select("div[id=intro]");

				novel.setName(name);
				novel.setUrl(url);
				String author=small.get(0).text().substring(small.get(0).text().indexOf("：") + 1);
				novel.setAuthor(author);
				String strDate = small.get(2).text();
				strDate = strDate.substring(strDate.indexOf("：") + 1);
				novel.setLatelytime(ChapterSpiderUtil.getData(strDate, "yyyy-MM-dd HH:mm:ss"));
				novel.setLatelychapter(small.get(3).select("a").text());
				novel.setLatelychapterurl((small.get(3).select("a").attr("abs:href")));
				novel.setInfo(intro.select("p").text());
				novel.setPlatformId(Site.getEnumByUrl(url).getId());
				novel.setType(ChapterSpiderUtil.getStr(doc.select("div[class=con_top]").text()));
				break;
			} catch (NullPointerException e) {
				log.warn("空指针的小说是："+novel);
			} catch (Exception e) {
				log.info("响应代码："+http.getCode());
				log.info("尝试第[" + (i + 1) + "/" + tries + "]次抓取失败了！");
			}
		}
		return novel;
	}
}
