package com.impl;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.impl.novels.job.DownloadJob;
import com.interfaces.IChapterSpider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.config.ThreadConfig;
import com.entity.Chapter;
import com.factory.ChapterSpiderFactory;
import com.interfaces.INovelDownload;
import com.Enum.Site;
import com.utlis.ChapterSpiderUtil;
import org.springframework.stereotype.Component;

/**
 * 下载小说
 * @author smile
 *
 */
@Component
public class NovelDownload implements INovelDownload {

	private static final Logger log = LoggerFactory.getLogger(NovelDownload.class);

	@Override
	public String download(String url, ThreadConfig config) throws Exception {
		Boolean flag = true;
		IChapterSpider spider = ChapterSpiderFactory.getChapterSpider(url);
		List<Chapter> chapters = spider.getChapters(url);

		//通过反射获得得到小说名
		Class<?> clazz = spider.getClass().getSuperclass().getSuperclass();
		Field http = clazz.getDeclaredField("http");
		http.setAccessible(true);
		Document doc = Jsoup.parse(http.get(spider).toString());
		String name = doc.select("meta[property=og:novel:book_name]").first().attr("content");

		Integer max=0;
		// 向上取整得到需要的线程个数
		Integer maxThreadSize = max <= config.getMaxThread() ? (int) Math.ceil(chapters.size() * 1.0 / config.getSize()) : config.getMaxThread();
		Map<String, List<Chapter>> downloadTasks = config.splitTask(chapters, new HashMap<>());

		ExecutorService service = Executors.newFixedThreadPool(maxThreadSize);
		List<Future<Boolean>> tasks = new ArrayList<>();

		//保存文件路径 f:/1/booktxt.net/顾承安时语/merge/顾承安时语.txt
		String savePath = config.getPath() + "/" + Site.getEnumByUrl(url).getUrl() + "/" + name;
		new File(savePath+"/merge/").mkdirs();

		downloadTasks.forEach((k,v)->{
			tasks.add(service.submit(new DownloadJob(savePath + "/" + k + ".txt", downloadTasks.get(k),config.getTries())));
		});

		//遍历线程执行结果，当有线程执行任务失败时终止线程池
		for (Future<Boolean> future : tasks) {
			try {
				if (future.get()) log.info(name + ".txt,下载完成！");
				else if (!future.get()) {
					service.shutdownNow();
					flag = false;
					break;
				}else System.out.println("======"+future.get()+"========");
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

		//释放内存
		service.shutdown();
		ChapterSpiderUtil.multiFileMerge(savePath, name,null, true);
		if(flag) return savePath + "/merge/" + name + ".txt";
		else {
			new File(savePath + "/merge/" + name + ".txt").delete();
			return "下载失败";
		}
	}
}
