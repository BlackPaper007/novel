package com.factory;

import java.util.concurrent.Callable;

import com.entity.Novel;
import com.impl.novels.job.BQGNovelJob;
import com.impl.novels.job.BQKNovelJob;
import com.impl.novels.job.DDNovelJob;
import com.impl.novels.job.XBQGNovelJob;
import com.novelEnum.Site;

public final class SpiderJobFactory {

	public static Callable<Novel> getJob(String url,String link,String name) {
		Site novelSiteEnum = Site.getEnumByUrl(url);
		switch (novelSiteEnum) {
		case xbiquge : return new XBQGNovelJob(link,name);
		case biquge : return new BQGNovelJob(link,name);
		case booktxt : return new DDNovelJob(link,name);
		case biquku : return new BQKNovelJob(link,name);
		default : throw new RuntimeException(url + "暂时没有该任务");
		}
	}
}

