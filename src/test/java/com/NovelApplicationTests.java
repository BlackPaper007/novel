<<<<<<< HEAD
package com;

import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dao.NovelMapper;
import com.entity.Chapter;
import com.entity.ChapterDetail;
import com.entity.Novel;
import com.entity.ThreadConfig;
import com.impl.NovelDownload;
import com.interfaces.IChapterDetail;
import com.interfaces.INovelDownload;
import com.interfaces.INovelSpider;
import com.utlis.ChapterDetailSpiderFactory;
import com.utlis.ChapterSpiderFactory;
import com.utlis.NovelSpiderFactory;

@SpringBootTest
class NovelApplicationTests {

	@Autowired NovelMapper novelMapper;
	
	String url="http://www.u33.cc/u140686/";
	String url2="https://www.uutxt.com/book/43/43821/2520338.html";
	String url3="http://www.xbiquge.la/0/8/";
	
	@Test
	void chapterLoads() {
		List<Chapter> chapters = ChapterSpiderFactory.getChapterSpider("https://www.biquge.lu/book/49330/");
		for (Chapter chapter : chapters) {
			System.out.println(chapter);
		}
	}
	
	@Test
	void DetailLoads(){
		IChapterDetail chapterDetail = ChapterDetailSpiderFactory.getChapterDetail("https://www.biquge.lu/book/49330/467274651.html");
		ChapterDetail detail = chapterDetail.getChapterDetail("https://www.biquge.lu/book/49330/467274651.html");
		if(StringUtils.isEmpty(detail.getContent())) {
			System.out.println("没有内容");
		}else System.out.println("内容:"+detail.getContent());
	}
	
	@Test
	public void Download() {
		INovelDownload download = new NovelDownload(); 
		ThreadConfig config = new ThreadConfig();
		config.setPath("e:/1");
		String addr = download.download(url3, config);
		System.out.println("文件下载结果："+addr);
	}
	
	@Test
	public void novelList() {
		INovelSpider novelSpider = NovelSpiderFactory.getNovelSpider("https://www.biquge.lu/xiaoshuodaquan/");
		List<Novel> list = novelSpider.getNovel("https://www.biquge.lu/xiaoshuodaquan/");
		
		for (Novel novel : list) {
			System.out.println(novel);
		}
	}
	
	@Test
	public void test() {
		List<Novel> novels=new ArrayList<Novel>();
		Novel novel = new Novel();
		
		novel.setName("1231");
		novel.setUrl("123123");
		novels.add(novel);
		novelMapper.batchInsert(novels);
	}
	
}
=======
package com;

import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dao.NovelMapper;
import com.entity.Chapter;
import com.entity.ChapterDetail;
import com.entity.Novel;
import com.entity.ThreadConfig;
import com.impl.NovelDownload;
<<<<<<< HEAD
import com.interfaces.IChapterDetail;
import com.interfaces.INovelDownload;
import com.interfaces.INovelSpider;
import com.utlis.ChapterDetailSpiderFactory;
import com.utlis.ChapterSpiderFactory;
=======
import com.impl.chapter.ChapterSpider;
import com.interfaces.IChapterDetail;
import com.interfaces.IChapterSpider;
import com.interfaces.INovelDownload;
import com.interfaces.INovelSpider;
import com.service.NovelService;
import com.utlis.ChapterDetailSpiderFactory;
>>>>>>> second commit
import com.utlis.NovelSpiderFactory;

@SpringBootTest
class NovelApplicationTests {

	@Autowired NovelMapper novelMapper;
<<<<<<< HEAD
	
	String url="http://www.u33.cc/u140686/";
	String url2="https://www.uutxt.com/book/43/43821/2520338.html";
	String url3="http://www.xbiquge.la/0/8/";
	
	@Test
	void chapterLoads() {
		List<Chapter> chapters = ChapterSpiderFactory.getChapterSpider("https://www.biquge.lu/book/49330/");
=======
	@Autowired NovelService novelService;
	
	String url="http://www.u33.cc/u140686/";
	String url2="https://www.uutxt.com/book/43/43821/2520338.html";
	String url3="http://www.xbiquge.la/55/55181/";
	String url4="http://www.biquku.la/47/47742/";
	
	@Test
	void chapterLoads() {
		IChapterSpider spider = new ChapterSpider();
		List<Chapter> chapters = spider.getsChapters(url4);
>>>>>>> second commit
		for (Chapter chapter : chapters) {
			System.out.println(chapter);
		}
	}
	
	@Test
	void DetailLoads(){
<<<<<<< HEAD
		IChapterDetail chapterDetail = ChapterDetailSpiderFactory.getChapterDetail("https://www.biquge.lu/book/49330/467274651.html");
		ChapterDetail detail = chapterDetail.getChapterDetail("https://www.biquge.lu/book/49330/467274651.html");
=======
		IChapterDetail chapterDetail = ChapterDetailSpiderFactory.getChapterDetail("http://www.biquku.la/47/47742/12670539.html");
		ChapterDetail detail = chapterDetail.getChapterDetail("http://www.biquku.la/47/47742/12670539.html");
>>>>>>> second commit
		if(StringUtils.isEmpty(detail.getContent())) {
			System.out.println("没有内容");
		}else System.out.println("内容:"+detail.getContent());
	}
	
	@Test
	public void Download() {
		INovelDownload download = new NovelDownload(); 
		ThreadConfig config = new ThreadConfig();
<<<<<<< HEAD
		config.setPath("e:/1");
=======
		config.setPath("f:/1");
>>>>>>> second commit
		String addr = download.download(url3, config);
		System.out.println("文件下载结果："+addr);
	}
	
	@Test
	public void novelList() {
<<<<<<< HEAD
		INovelSpider novelSpider = NovelSpiderFactory.getNovelSpider("https://www.biquge.lu/xiaoshuodaquan/");
		List<Novel> list = novelSpider.getNovel("https://www.biquge.lu/xiaoshuodaquan/");
=======
		INovelSpider novelSpider = NovelSpiderFactory.getNovelSpider("http://www.biquku.la/xiaoshuodaquan/");
		List<Novel> list = novelSpider.getNovel("http://www.biquku.la/xiaoshuodaquan/");
>>>>>>> second commit
		
		for (Novel novel : list) {
			System.out.println(novel);
		}
	}
	
	@Test
	public void test() {
		List<Novel> novels=new ArrayList<Novel>();
		Novel novel = new Novel();
		
		novel.setName("1231");
		novel.setUrl("123123");
		novels.add(novel);
		novelMapper.batchInsert(novels);
	}
	
}
>>>>>>> second commit
