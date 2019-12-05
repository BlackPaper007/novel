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
import com.impl.chapter.ChapterDetailSpider;
import com.interfaces.INovelDownload;
import com.interfaces.INovelSpider;
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
		List<Chapter> chapters = ChapterSpiderFactory.getChapterSpider(url);
		for (Chapter chapter : chapters) {
			System.out.println(chapter);
		}
	}
	
	@Test
	void DetailLoads(){
		ChapterDetailSpider detailSpider = new ChapterDetailSpider();
		ChapterDetail detail = detailSpider.getChapterDetail("https://www.biquge18.com/book/134025/1_6.html");
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
		INovelSpider novelSpider = NovelSpiderFactory.getNovelSpider("https://www.booktxt.net/xiaoshuodaquan/");
		List<Novel> list = novelSpider.getNovel("https://www.booktxt.net/xiaoshuodaquan/");
		
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
