/*
package com.impl.novels.job;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.entity.Novel;
import com.Enum.Site;
import com.utlis.ChapterSpiderUtil;
import com.config.Config;

public class BQGNovelJob extends BaseJob{

	public BQGNovelJob(String url, String name) {
		super(url, name);
	}

	@Override
	public Novel call(){
		Novel novel = new Novel();
		try {
			String result = crawl(url).getDate().toString();
			result = result.replace("&nbsp;", " ");
			Document doc = Jsoup.parse(result);
			doc.setBaseUri(url);
			String info = Config.getContext(Site.getEnumByUrl(url)).get("info");
			Elements maininfo = doc.select(info);
			Elements small = maininfo.select("div[class=small]").select("span");
			Elements intro = maininfo.select("div[class=intro]");

			novel.setName(name);
			novel.setUrl(url);
			String author=small.get(0).text().substring(small.get(0).text().indexOf("：") + 1);
			novel.setAuthor(author);
			novel.setStatus(ChapterSpiderUtil.getNovelStatus(small.get(2).text().substring(small.get(2).text().indexOf("：") + 1)));
			String strDate = small.get(4).text();
			strDate = strDate.substring(strDate.indexOf("：") + 1);
			novel.setLatelytime(ChapterSpiderUtil.getData(strDate, "yyyy-MM-dd HH:mm:ss"));
			novel.setLatelychapter(small.get(5).select("a").text());
			novel.setLatelychapterurl((small.get(5).select("a").attr("abs:href")));
			novel.setInfo(intro.text().replace("推荐地址："+url, "").replace("作者："+author+"所写的《"+name+"》无弹窗免费全文阅读为转载作品,章节由网友发布。", ""));
			novel.setPlatformId(Site.getEnumByUrl(url).getId());
			novel.setUpdateTime(novel.getUpdateTime());
			novel.setType(small.get(1).text().substring(small.get(1).text().indexOf("：") + 1));
		} catch (Exception e) {
			log.error(novel.toString());
			log.error(e.getLocalizedMessage());
		}
		return novel;
	}
}
*/
