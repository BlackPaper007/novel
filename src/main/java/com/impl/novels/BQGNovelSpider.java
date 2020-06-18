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

public class BQGNovelSpider extends AbstractNovelSpider {
	
	@Override
	public List<Novel> getNovel(String url) {
		List<Future<Novel>> tasks = new ArrayList<>();
		List<Novel> novels = new ArrayList<>();
		ThreadConfig config = new ThreadConfig();
		
		try {
			Elements novelList = super.getNovelList(url);
			ExecutorService service = Executors.newFixedThreadPool(config.getMaxThread());
			for (Element key : novelList) {
				tasks.add(service.submit(new BQGNovelCallable(key,config.getTries())));
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

class BQGNovelCallable extends AbstractSpider implements Callable<Novel> {

	private Element e;
	private int tries;

	public BQGNovelCallable(Element e,int tries) {
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
				doc.setBaseUri(url);
				String info = Config.getContext(Site.getEnumByUrl(url)).get("novel-info");
				Elements maininfo = doc.select(info);
				Elements small = maininfo.select("div[class=small]").select("span");
				Elements intro = maininfo.select("div[class=intro]");

				novel.setName(novelName);
				novel.setUrl(url);
				String author=small.get(0).text().substring(small.get(0).text().indexOf("：") + 1);
				novel.setAuthor(author);
				novel.setStatus(ChapterSpiderUtil.getNovelStatus(small.get(2).text().substring(small.get(2).text().indexOf("：") + 1)));
				String strDate = small.get(4).text();
				strDate = strDate.substring(strDate.indexOf("：") + 1);
				novel.setLatelytime(ChapterSpiderUtil.getData(strDate, "yyyy-MM-dd HH:mm:ss"));
				novel.setLatelychapter(small.get(5).select("a").text());
				novel.setLatelychapterurl((small.get(5).select("a").attr("abs:href")));
				novel.setInfo(intro.text().replace("推荐地址："+url, "").replace("作者："+author+"所写的《"+novelName+"》无弹窗免费全文阅读为转载作品,章节由网友发布。", ""));
				novel.setPlatformId(Site.getEnumByUrl(url).getId());
				novel.setType(small.get(1).text().substring(small.get(1).text().indexOf("：") + 1));
				System.out.println(novel);
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
import com.impl.novels.job.BQGNovelCallable;
>>>>>>> second commit

public class BQGNovelSpider extends AbstractNovelSpider {
	
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
				tasks.add(service.submit(new BQGNovelCallable(key,config.getTries())));
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
class BQGNovelCallable extends AbstractSpider implements Callable<Novel> {

	private Element e;
	private int tries;

	public BQGNovelCallable(Element e,int tries) {
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
				doc.setBaseUri(url);
				String info = Config.getContext(Site.getEnumByUrl(url)).get("novel-info");
				Elements maininfo = doc.select(info);
				Elements small = maininfo.select("div[class=small]").select("span");
				Elements intro = maininfo.select("div[class=intro]");

				novel.setName(novelName);
				novel.setUrl(url);
				String author=small.get(0).text().substring(small.get(0).text().indexOf("：") + 1);
				novel.setAuthor(author);
				novel.setStatus(ChapterSpiderUtil.getNovelStatus(small.get(2).text().substring(small.get(2).text().indexOf("：") + 1)));
				String strDate = small.get(4).text();
				strDate = strDate.substring(strDate.indexOf("：") + 1);
				novel.setLatelytime(ChapterSpiderUtil.getData(strDate, "yyyy-MM-dd HH:mm:ss"));
				novel.setLatelychapter(small.get(5).select("a").text());
				novel.setLatelychapterurl((small.get(5).select("a").attr("abs:href")));
				novel.setInfo(intro.text().replace("推荐地址："+url, "").replace("作者："+author+"所写的《"+novelName+"》无弹窗免费全文阅读为转载作品,章节由网友发布。", ""));
				novel.setPlatformId(Site.getEnumByUrl(url).getId());
				novel.setType(small.get(1).text().substring(small.get(1).text().indexOf("：") + 1));
				System.out.println(novel);
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
