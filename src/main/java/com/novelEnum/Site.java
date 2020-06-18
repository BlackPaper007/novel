<<<<<<< HEAD
package com.novelEnum;


/**
 * 支持的小说网站枚举
 * @author smile
 *
 */
public enum Site {
	booktxt(1, "booktxt.net"),
	uutxt(2, "u33.cc"),
	xbiqige(3, "xbiquge.la"),
	biquge(4,"biquge.lu");
	
	private int id;
	private String url;
	
	private Site(int id, String url) {
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
		for (Site novelSiteEnum : values()) {
			if(novelSiteEnum.getId()==id) return novelSiteEnum;
		}
		throw new RuntimeException("id=" + id + "是不被支持的小说网站");
	}
	
	public static Site getEnumByUrl(String url) {
		if (url == null) throw new IllegalArgumentException("url 不能为null");
		for (Site novelSiteEnum : values()) {
			//比较是否包含字符
			if (url.contains(novelSiteEnum.url)) return novelSiteEnum;
		}
		throw new RuntimeException("url=" + url + "是不被支持的小说网站");
	}
}
=======
package com.novelEnum;

<<<<<<< HEAD
=======
import lombok.Getter;
>>>>>>> second commit

/**
 * 支持的小说网站枚举
 * @author smile
 *
 */
<<<<<<< HEAD
public enum Site {
	booktxt(1, "booktxt.net"),
	uutxt(2, "u33.cc"),
	xbiqige(3, "xbiquge.la"),
	biquge(4,"biquge.lu");
=======
@Getter
public enum Site {
	booktxt(1, "booktxt.net"),
	uutxt(2, "u33.cc"),
	xbiquge(3, "xbiquge.la"),
	biquge(4,"biquge.lu"),
	biquku(5,"biquku.la");
>>>>>>> second commit
	
	private int id;
	private String url;
	
	private Site(int id, String url) {
		this.id = id;
		this.url = url;
	}
<<<<<<< HEAD
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
=======
	
	public void setId(int id) {
		this.id = id;
	}

>>>>>>> second commit
	public void setUrl(String url) {
		this.url = url;
	}
	
	public static Site getEnumById(int id) {
		for (Site novelSiteEnum : values()) {
			if(novelSiteEnum.getId()==id) return novelSiteEnum;
		}
		throw new RuntimeException("id=" + id + "是不被支持的小说网站");
	}
	
	public static Site getEnumByUrl(String url) {
		if (url == null) throw new IllegalArgumentException("url 不能为null");
		for (Site novelSiteEnum : values()) {
			//比较是否包含字符
			if (url.contains(novelSiteEnum.url)) return novelSiteEnum;
		}
		throw new RuntimeException("url=" + url + "是不被支持的小说网站");
	}
<<<<<<< HEAD
=======

>>>>>>> second commit
}
>>>>>>> second commit
