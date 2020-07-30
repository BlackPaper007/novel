package com.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import com.entity.Message;
import com.impl.abs.AbstractSpider;
import com.interfaces.IChapterSpider;
import lombok.val;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.config.ThreadConfig;
import com.entity.Chapter;
import com.entity.ChapterDetail;
import com.factory.ChapterDetailSpiderFactory;
import com.factory.ChapterSpiderFactory;
import com.entity.Novel;
import com.interfaces.IChapterDetail;
import com.interfaces.INovelDownload;
import com.novelEnum.Site;
import com.utlis.ChapterSpiderUtil;
import org.springframework.context.annotation.Bean;
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
		Novel novel = new Novel();
		IChapterSpider spider = ChapterSpiderFactory.getChapterSpider(url);
		List<Chapter> chapters = spider.getsChapters(url);

		//通过反射获得抓取页面
		Class<?> clazz = spider.getClass().getSuperclass().getSuperclass();
		Field http = clazz.getDeclaredField("http");
		http.setAccessible(true);

		Document doc = Jsoup.parse(http.get(spider).toString());
		//得到小说名
		String name = doc.select("meta[property=og:novel:book_name]").first().attr("content");

		Integer max=0;
		// 向上取整得到需要的线程个数
		Integer maxThreadSize = max <= config.getMaxThread() ? (int) Math.ceil(chapters.size() * 1.0 / config.getSize()) : config.getMaxThread();
		Map<String, List<Chapter>> downloadTasks = new HashMap<>();
		for (int i = 0; i < maxThreadSize; i++) {
			// 每个线程开始的章节数
			int fromIndex = i * config.getSize();
			// 每个线程结束的章节数
			int toIndex = i == maxThreadSize - 1 ? chapters.size() : fromIndex + config.getSize();
			downloadTasks.put((fromIndex + 1) + "-" + toIndex , chapters.subList(fromIndex, toIndex));
		}

		ExecutorService service = Executors.newFixedThreadPool(maxThreadSize);
		List<Future<Boolean>> tasks = new ArrayList<>();

		//保存文件路径 f:/1/booktxt.net/顾承安时语/merge/顾承安时语.txt
		String savePath = config.getPath() + "/" + Site.getEnumByUrl(url).getUrl() + "/" + name;
		new File(savePath+"/merge/").mkdirs();

		downloadTasks.forEach((k,v)->{
			tasks.add(service.submit(new LoadCallable(savePath + "/" + k + ".txt", downloadTasks.get(k),config.getTries())));
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

class LoadCallable implements Callable<Boolean> {
	private List<Chapter> chapters;
	private String path;
	private Integer tries;
	private Boolean flag = true;
	private static final Logger log = LoggerFactory.getLogger(LoadCallable.class);

	public LoadCallable(String path, List<Chapter> chapters ,int tries) {
		this.path = path;
		this.chapters = chapters;
		this.tries = tries;
	}

	/**
	 * 线程执行下载任务
	 */
	@Override
	public Boolean call() {
		try (PrintWriter out = new PrintWriter(new File(path), "UTF-8")) {
			for (Chapter chapter : chapters) {
				IChapterDetail spider = ChapterDetailSpiderFactory.getChapterDetail(chapter.getOriUrl());
				ChapterDetail detail = null;
				//线程下载任务失败，尝试重新下载j次之后放弃
				for (int j = 0; j < tries; j++) {
					try {
						detail = spider.getChapterDetail(chapter.getOriUrl());
						out.println(detail.getTitle());
						out.println(detail.getContent());
						break;
					} catch (Exception e) {
						log.error("尝试第[" + (j + 1) + "/" + tries + "]次下载失败了！");
						if ((j + 1) == tries) flag = false;
					}
				} if (!flag) break;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return flag;
	}
}
