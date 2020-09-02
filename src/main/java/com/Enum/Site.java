package com.Enum;


import java.util.Arrays;

/**
 * 支持的小说网站枚举
 * @author smile
 *
 */
public enum Site {
	booktxt(1, "booktxt.net"),
	uutxt(2, "u33.cc"),
	xbiquge(3, "xbiquge.la"),
	biquge(4,"biquge.lu"),
	biquku(5,"biquku.la");

	private Integer id;
	private String url;

	private Site(Integer id, String url) {
		this.id = id;
		this.url = url;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public static Site getEnumById(int id) {
		return Arrays.stream(values()).filter((v) -> id == v.id).findFirst().orElseThrow(IllegalArgumentException::new);
	}

	public static Site getEnumByUrl(String url) {
		if (url == null) throw new IllegalArgumentException("url 不能为null");
		return Arrays.stream(values()).filter((v) -> url.contains(v.url)).findFirst().orElseThrow(IllegalArgumentException::new);
	}
}
