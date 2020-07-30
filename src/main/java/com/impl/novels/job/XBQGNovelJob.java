package com.impl.novels.job;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.entity.Novel;
import com.novelEnum.Site;
import com.utlis.ChapterSpiderUtil;
import com.utlis.Config;

public class XBQGNovelJob extends BaseJob {

	public XBQGNovelJob(String url, String name) {
		super(url, name);
	}

	@Override
	public Novel call() throws Exception {
		Novel novel = new Novel();
			try {
				String result = super.crawl(url);
				result = result.replace("&nbsp;", " ");
				Document doc = Jsoup.parse(result);
				String info = Config.getContext(Site.getEnumByUrl(url)).get("novel-info");
				String type = Config.getContext(Site.getEnumByUrl(url)).get("novel-type");
				Elements maininfo = doc.select(info);
				Elements p = maininfo.select("div[id=info]").select("p");
				Elements intro = maininfo.select("div[id=intro]").select("p");
				doc.setBaseUri(url);
				novel.setName(name);
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
			} catch (Exception e) {
				log.error(novel.toString());
				log.error(e.getLocalizedMessage());
			}
		return novel;
	}
}
