package com.impl.novels.job;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.entity.Novel;
import com.novelEnum.Site;
import com.utlis.ChapterSpiderUtil;
import com.utlis.Config;

public class BQKNovelJob extends BaseJob{

	public BQKNovelJob(String url, String name) {
		super(url, name);
	}

	@Override
	public Novel call() throws Exception {
		Novel novel = new Novel();
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
		} catch (Exception e) {
			log.error(novel.toString());
			log.error(e.getLocalizedMessage());
		}
		return novel;
	}
}
