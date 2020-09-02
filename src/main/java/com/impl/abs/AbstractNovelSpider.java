package com.impl.abs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.entity.Novel;
import com.entity.Result;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.factory.SpiderJobFactory;
import com.interfaces.INovelSpider;
import com.Enum.Site;
import com.config.Config;

public abstract class AbstractNovelSpider extends AbstractSpider implements INovelSpider {

    public List<Novel> getNovel(String url) {
        ExecutorService service = Executors.newFixedThreadPool(config.getMaxThread());
        List<Future<Result>> tasks = new ArrayList<>(500);
        List<Novel> novels = new ArrayList<>();
        try {
            Elements novelList = getNovelList(url);
            Map<String, List<Element>> Task = config.splitTask(novelList, new HashMap<>());
            for (Entry<String, List<Element>> entry : Task.entrySet()) {
                entry.getValue().forEach(v -> {
                    try {
                        String link = v.attr("abs:href");
                        String name = v.text();
                        tasks.add(service.submit(SpiderJobFactory.getJob(url, link, name)));
                        Thread.sleep(config.getSleepTime());
                    } catch (Exception e) {
                        new RuntimeException();
                    }
                });
            }

            service.shutdown();
            for (Future<Result> task : tasks)
                novels.add(task.get().getNovel());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return novels;
    }

    protected Elements getNovelList(String url) {
        String result = crawl(url).getDate().toString();
        Map<String, String> context = Config.getContext(Site.getEnumByUrl(url));
        String novelName = context.get("chapter");
        Document doc = Jsoup.parse(result);
        doc.setBaseUri(url);
        return doc.select(novelName);
    }
}

