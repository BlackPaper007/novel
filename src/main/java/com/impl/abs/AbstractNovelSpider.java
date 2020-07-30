package com.impl.abs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Future;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.entity.Novel;
import com.factory.SpiderJobFactory;
import com.interfaces.INovelSpider;
import com.novelEnum.Site;
import com.utlis.Config;

public abstract class AbstractNovelSpider extends AbstractSpider implements INovelSpider {

	protected Elements getNovelList(String url) throws Exception {
		String result = super.crawl(url);
		Map<String, String> context = Config.getContext(Site.getEnumByUrl(url));
		String novelName = context.get("novel-list");
		Document doc = Jsoup.parse(result);
		doc.setBaseUri(url);
		return doc.select(novelName);
	}
	
	protected List<Novel> get(String url) {
		try {
			Elements novelList = getNovelList(url);
			@SuppressWarnings("unchecked")
			Map<String, List<Element>> Task = config.splitTask(novelList, new HashMap<>());
			for (Entry<String, List<Element>> entry : Task.entrySet()) {
				entry.getValue().forEach(v->{
					try {
						String link = v.attr("abs:href");
						String name = v.text();
						tasks.add(service.submit(SpiderJobFactory.getJob(url, link, name)));
						Thread.sleep(config.getSleepTime());
					} catch (Exception e) {
						log.info("站点小说目录抓取异常！！！");
					}
				});
			}
			
			service.shutdown();
			for (Future<Novel> task : tasks) 
				novels.add(task.get());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return novels;
	}
}

