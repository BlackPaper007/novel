package com.interfaces;

import com.entity.ThreadConfig;

public interface INovelDownload {

	/**
	 * 下载书籍
	 * @param url
	 * @param config 可以为null
	 * @return
	 */
	public String download(String url,ThreadConfig config);
}
