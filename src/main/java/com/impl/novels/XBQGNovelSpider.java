<<<<<<< HEAD
package com.impl.novels;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.entity.Novel;
import com.entity.ThreadConfig;
import com.impl.abs.AbstractNovelSpider;
import com.impl.abs.AbstractSpider;
import com.novelEnum.Site;
import com.utlis.ChapterSpiderUtil;
import com.utlis.Config;

public class XBQGNovelSpider extends AbstractNovelSpider {
	
	@Override
	public List<Novel> getNovel(String url) {
		List<Future<Novel>> tasks = new ArrayList<>();
		List<Novel> novels = new ArrayList<>();
		ThreadConfig config = new ThreadConfig();
		
		try {
			Elements novelList = super.getNovelList(url);
			ExecutorService service = Executors.newFixedThreadPool(config.getMaxThread());
			for (Element key : novelList) {
				tasks.add(service.submit(new XBQGNovelCallable(key,config.getTries())));
				Thread.sleep(config.getSleepTime());
			}
			
			service.shutdown();
			for (Future<Novel> task : tasks) {
				novels.add(task.get());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return novels;
	}
}

class XBQGNovelCallable extends AbstractSpider implements Callable<Novel> {

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
				System.out.println(novel);
			} catch (Exception e) {
				System.out.println("尝试第[" + (i + 1) + "/" + tries + "]次抓取失败了！");
			}
		}
		return novel;
	}
}
=======
package com.impl.novels;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
=======
import java.util.List;
import java.util.concurrent.Future;


>>>>>>> second commit
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.entity.Novel;
<<<<<<< HEAD
import com.entity.ThreadConfig;
import com.impl.abs.AbstractNovelSpider;
import com.impl.abs.AbstractSpider;
import com.novelEnum.Site;
import com.utlis.ChapterSpiderUtil;
import com.utlis.Config;
=======
import com.impl.abs.AbstractNovelSpider;
import com.impl.novels.job.XBQGNovelCallable;
>>>>>>> second commit

public class XBQGNovelSpider extends AbstractNovelSpider {
	
	@Override
	public List<Novel> getNovel(String url) {
<<<<<<< HEAD
		List<Future<Novel>> tasks = new ArrayList<>();
		List<Novel> novels = new ArrayList<>();
		ThreadConfig config = new ThreadConfig();
		
		try {
			Elements novelList = super.getNovelList(url);
			ExecutorService service = Executors.newFixedThreadPool(config.getMaxThread());
=======
		try {
			Elements novelList = super.getNovelList(url);
>>>>>>> second commit
			for (Element key : novelList) {
				tasks.add(service.submit(new XBQGNovelCallable(key,config.getTries())));
				Thread.sleep(config.getSleepTime());
			}
			
			service.shutdown();
<<<<<<< HEAD
			for (Future<Novel> task : tasks) {
				novels.add(task.get());
			}
=======
			for (Future<Novel> task : tasks) 
				novels.add(task.get());
			
>>>>>>> second commit
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return novels;
	}
}

<<<<<<< HEAD
class XBQGNovelCallable extends AbstractSpider implements Callable<Novel> {

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
				System.out.println(novel);
			} catch (Exception e) {
				System.out.println("尝试第[" + (i + 1) + "/" + tries + "]次抓取失败了！");
			}
		}
		return novel;
	}
}
=======
>>>>>>> second commit
>>>>>>> second commit
