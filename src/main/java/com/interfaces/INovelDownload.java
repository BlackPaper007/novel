package com.interfaces;

import com.config.ThreadConfig;

public interface INovelDownload {

	/**
	 * 下载书籍
	 * @param url
	 * @param config 可以为空
	 * @return
	 */
	public String download(String url,ThreadConfig config) throws NoSuchFieldException, Exception;
}

