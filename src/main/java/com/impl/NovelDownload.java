package com.impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.entity.Chapter;
import com.entity.ChapterDetail;
import com.entity.ThreadConfig;
import com.entity.Novel;
import com.interfaces.IChapterDetail;
import com.interfaces.INovelDownload;
import com.novelEnum.Site;
import com.utlis.ChapterDetailSpiderFactory;
import com.utlis.ChapterSpiderFactory;
import com.utlis.ChapterSpiderUtil;

/**
 * 下载小说
 * @author smile
 *
 */
public class NovelDownload implements INovelDownload {
	
	private static final Logger log = LoggerFactory.getLogger(NovelDownload.class);

	@Override
	public String download(String url, ThreadConfig config) {
		boolean flag = true;
		List<Chapter> chapters = ChapterSpiderFactory.getChapterSpider(url);
		
		int max=0;
		// 向上取整得到需要的线程个数
		int maxThreadSize = max <= config.getMaxThread() ? (int) Math.ceil(chapters.size() * 1.0 / config.getSize()) : config.getMaxThread();
		Map<String, List<Chapter>> downloadTasks = new HashMap<>();
		for (int i = 0; i < maxThreadSize; i++) {
			// 每个线程开始的章节数
			int fromIndex = i * config.getSize();
			// 每个线程结束的章节数
			int toIndex = i == maxThreadSize - 1 ? chapters.size() : fromIndex + config.getSize();
			downloadTasks.put((fromIndex + 1) + "-" + toIndex , chapters.subList(fromIndex, toIndex));
		}
		
		ExecutorService service = Executors.newFixedThreadPool(maxThreadSize);
		Set<String> keySet = downloadTasks.keySet();
		//Set<String> keySet = Collections.synchronizedSet(downloadTasks.keySet());//线程安全
		List<Future<String>> tasks = new ArrayList<>();
		//保存文件路径 D:/root/site/bookName/
		Novel novel = new Novel();
		String savePath = config.getPath() + "/" + Site.getEnumByUrl(url).getUrl() + "/" + novel.getName();
		new File(savePath+"/merge/").mkdirs();
		
		for (String key : keySet) {
			// 将任务提交线程池去执行
			tasks.add(service.submit(new LoadCallable(savePath + "/" + key + ".txt", downloadTasks.get(key),config.getTries())));
		}
		//遍历线程执行结果，当有线程执行任务失败时终止线程池
		for (Future<String> future : tasks) {
			try {
				if(future.get()!=String.valueOf(flag)) log.info(future.get() + ",下载完成！");
				else {
					service.shutdownNow();
					flag=false;
					break;
				}
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		//释放内存
		service.shutdown();
		ChapterSpiderUtil.multiFileMerge(savePath, novel.getName(),null, true);
		if(flag) return "文件保存在  " + savePath + "/merge/" + novel.getName() + ".txt";
		else {
			new File(savePath + "/merge/" + novel.getName() + ".txt").delete();
			return "下载失败";
		}
	}
}

class LoadCallable implements Callable<String> {
	private List<Chapter> chapters;
	private String path;
	private int tries;
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
	public String call() throws Exception {
		
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
					} catch (RuntimeException e) {
						log.error("尝试第[" + (j + 1) + "/" + tries + "]次下载失败了！");
						if ((j + 1) == tries) path = "false";
					}
				} if (path == "false") break;
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} 
		return path;
	}
}
