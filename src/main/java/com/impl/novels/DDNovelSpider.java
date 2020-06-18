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

public class DDNovelSpider extends AbstractNovelSpider {
	
	@Override
	public List<Novel> getNovel(String url) {
		List<Future<Novel>> tasks = new ArrayList<>();
		List<Novel> novels = new ArrayList<>();
		ThreadConfig config = new ThreadConfig();
		
		try {
			Elements novelList = super.getNovelList(url);
			ExecutorService service = Executors.newFixedThreadPool(config.getMaxThread());
			for (Element key : novelList) {
				tasks.add(service.submit(new DDNovelCallable(key,config.getTries())));
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

class DDNovelCallable extends AbstractSpider implements Callable<Novel> {

	private Element e;
	private int tries;

	public DDNovelCallable(Element e,int tries) {
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
				String chapter = Config.getContext(Site.getEnumByUrl(url)).get("selector");
				Elements maininfo = doc.select(info);
				Elements p = maininfo.select("div[id=info]").select("p");
				Elements intro = maininfo.select("div[id=intro]").select("p");
				Element latelyChapter = doc.select(chapter).last();
				doc.setBaseUri(url);
				
				novel.setName(novelName);
				novel.setUrl(url);
				novel.setPlatformId(Site.getEnumByUrl(url).getId());
				novel.setInfo(intro.get(0).text());
				novel.setType(ChapterSpiderUtil.getMiddleStr(doc.select(type).text()));
				try {
					novel.setAuthor(p.get(0).text().substring(p.get(0).text().indexOf("：") + 1));
					novel.setLatelychapter(latelyChapter.text());
				} catch (Exception e1) {
					System.out.println(p.get(0).text()+"===="+url);
					e1.printStackTrace();
					return novel;
				}
				novel.setLatelychapterurl(latelyChapter.attr("abs:href"));
				break;
			} catch (Exception e) {
				e.printStackTrace();
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import com.impl.novels.job.DDNovelCallable;
>>>>>>> second commit

public class DDNovelSpider extends AbstractNovelSpider {
	
	@Override
	public List<Novel> getNovel(String url) {
<<<<<<< HEAD
		List<Future<Novel>> tasks = new ArrayList<>();
		List<Novel> novels = new ArrayList<>();
		ThreadConfig config = new ThreadConfig();
		
		try {
			Elements novelList = super.getNovelList(url);
			ExecutorService service = Executors.newFixedThreadPool(config.getMaxThread());
			for (Element key : novelList) {
				tasks.add(service.submit(new DDNovelCallable(key,config.getTries())));
				Thread.sleep(config.getSleepTime());
			}
			
			service.shutdown();
			for (Future<Novel> task : tasks) {
				novels.add(task.get());
			}
=======
		try {
			Elements novelList = super.getNovelList(url);
			Map<String, List<Element>> Task = config.splitTask(novelList, new HashMap<>());
			for (Entry<String, List<Element>> entry : Task.entrySet()) {
				entry.getValue().forEach(v->{
					try {
						String link = v.attr("abs:href");
						String name = v.text();
						System.out.println(name+"--"+link);
						tasks.add(service.submit(new DDNovelCallable(link,name,config.getTries())));
						Thread.sleep(config.getSleepTime());
					} catch (Exception e) {
						log.info("站点小说目录抓取异常！！！");
					}
				});
			}
			
			service.shutdown();
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
class DDNovelCallable extends AbstractSpider implements Callable<Novel> {

	private Element e;
	private int tries;

	public DDNovelCallable(Element e,int tries) {
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
				String chapter = Config.getContext(Site.getEnumByUrl(url)).get("selector");
				Elements maininfo = doc.select(info);
				Elements p = maininfo.select("div[id=info]").select("p");
				Elements intro = maininfo.select("div[id=intro]").select("p");
				Element latelyChapter = doc.select(chapter).last();
				doc.setBaseUri(url);
				
				novel.setName(novelName);
				novel.setUrl(url);
				novel.setPlatformId(Site.getEnumByUrl(url).getId());
				novel.setInfo(intro.get(0).text());
				novel.setType(ChapterSpiderUtil.getMiddleStr(doc.select(type).text()));
				try {
					novel.setAuthor(p.get(0).text().substring(p.get(0).text().indexOf("：") + 1));
					novel.setLatelychapter(latelyChapter.text());
				} catch (Exception e1) {
					System.out.println(p.get(0).text()+"===="+url);
					e1.printStackTrace();
					return novel;
				}
				novel.setLatelychapterurl(latelyChapter.attr("abs:href"));
				break;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("尝试第[" + (i + 1) + "/" + tries + "]次抓取失败了！");
			}
		}
		return novel;
	}
}
=======
>>>>>>> second commit
>>>>>>> second commit
