package com.impl.novels.job;

import com.entity.Novel;
import com.entity.Result;
import com.impl.abs.AbstractSpider;
import com.Enum.Site;
import com.utlis.ChapterSpiderUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.concurrent.Callable;

public class BaseJob extends AbstractSpider implements Callable<Result> {

	protected String url;
	protected String name;

	public BaseJob(String url, String name) {
		super();
		this.url = url;
		this.name = name;
	}

	@Override
	public Result call(){
		Novel novel = new Novel();
		Result crawl = crawl(url);
		try {
			if(crawl.getCode()!=200) return crawl;
			String result = crawl.getDate().toString();
			Document doc = Jsoup.parse(result);
			doc.setBaseUri(url);

			novel.setName(name);
			novel.setUrl(url);
			novel.setAuthor(doc.select("meta[property=og:novel:author]").first().attr("content"));
			novel.setInfo(doc.select("meta[property=og:description]").first().attr("content"));
			novel.setType(doc.select("meta[property=og:novel:category]").first().attr("content"));
			novel.setLatelychapter(doc.select("meta[property=og:novel:latest_chapter_name]").first().attr("content"));
			novel.setLatelychapterurl(doc.select("meta[property=og:novel:latest_chapter_url]").first().attr("content"));
			Long time = ChapterSpiderUtil.getData(doc.select("meta[property=og:novel:update_time]").first().attr("content"), "yyyy-MM-dd HH:mm:ss");
			novel.setLatelytime(time);
			int status = ChapterSpiderUtil.getNovelStatus(doc.select("meta[property=og:novel:status]").first().attr("content"));
			novel.setStatus(status);
			novel.setPlatformId(Site.getEnumByUrl(url).getId());
			novel.setUpdateTime(novel.getUpdateTime());
			crawl.setDate(novel);
		} catch (Exception e) {
			log.error(novel.toString());
			log.error(e.getLocalizedMessage());
		}
		return crawl;
	}
}
