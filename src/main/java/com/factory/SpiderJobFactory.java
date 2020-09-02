package com.factory;

import java.util.concurrent.Callable;

import com.entity.Novel;
import com.entity.Result;
import com.impl.novels.job.*;
import com.Enum.Site;

public final class SpiderJobFactory {

	public static Callable<Result> getJob(String url, String link, String name) {
		Site novelSiteEnum = Site.getEnumByUrl(url);
		switch (novelSiteEnum) {
			case xbiquge:
			case biquge :
			case booktxt :
			case biquku : return new BaseJob(link,name);
		default : throw new RuntimeException(url + "暂时没有该任务");
		}
	}
}

