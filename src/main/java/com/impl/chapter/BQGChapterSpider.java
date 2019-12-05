package com.impl.chapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.entity.Chapter;
import com.entity.ChapterDetail;
import com.entity.Novel;
import com.impl.abs.AbstractChapterSpider;
import com.interfaces.IChapterDetail;

/**
 * biquge18.com
 * 问题：超时
 * 从父链接开始遍历子链接，返回内容有则返回标题
 * @author smile
 *
 */
public class BQGChapterSpider extends AbstractChapterSpider {

	@Override
	public List<Chapter> getsChapters(String url) {
		List<Chapter> chapters = super.getsChapters(url);
		ExecutorService service = Executors.newFixedThreadPool(20);
		List<Chapter> newChapters = new ArrayList<>();
		IChapterDetail detailSpider = new ChapterDetailSpider();
		List<Future<ChapterDetail>> tasks = new ArrayList<>();
		ChapterDetail detail = null;
		for (Chapter chapter : chapters) {
			String string = chapter.toString().split("\\.html")[0].split("oriUrl=")[1];
			for (int i = 0; i < 3; i++) {
				String str=string + "_" + (i+1) + ".html";
				tasks.add(service.submit(new DetailCallable(str, detailSpider)));
				
				for (Future<ChapterDetail> s : tasks) {
					try {
						detail = s.get();
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
					if (StringUtils.isEmpty(detail.getContent())) break;
					Chapter c = new Chapter();
					Novel novel = new Novel();
					c.setTitle(novel.getName());
					c.setOriUrl(str);
					if(i==0) newChapters.add(chapter);
					newChapters.add(c);
				}
			}
		}
		service.shutdown();
		return newChapters;
	}
}

class DetailCallable implements Callable<ChapterDetail> {
	private String url;
	private IChapterDetail detailSpider;
	private final int TRIES = 3;
	ChapterDetail detail = null;
	private static final Logger log = LoggerFactory.getLogger(DetailCallable.class);

	public DetailCallable(String url,IChapterDetail detailSpider) {
		this.url = url;
		this.detailSpider = detailSpider;
	}
	
	@Override
	public ChapterDetail call() throws Exception {
		for (int j = 0; j < TRIES; j++) {
			try {
				detail = detailSpider.getChapterDetail(url);
				return detail;
			} catch (RuntimeException e) {
				log.error("尝试第[" + (j + 1) + "/" + TRIES + "]次抓取失败了！");
			}
		}
		throw new RuntimeException(url + "尝试" + TRIES + "次失败!章节抓取失败");
	}
}

