package com.impl.novels;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Future;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.entity.Novel;
import com.impl.abs.AbstractNovelSpider;
import com.impl.novels.job.BQKNovelCallable;

public class BQKNovelSpider extends AbstractNovelSpider {
	
	@Override
	public List<Novel> getNovel(String url) {
		try {
			Elements novelList = super.getNovelList(url);
			@SuppressWarnings("unchecked")
			Map<String, List<Element>> Task = config.splitTask(novelList, new HashMap<>());
			for (Entry<String, List<Element>> entry : Task.entrySet()) {
				entry.getValue().forEach(v->{
					try {
						String link = v.attr("abs:href");
						String name = v.text();
						tasks.add(service.submit(new BQKNovelCallable(link,name,config.getTries())));
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

