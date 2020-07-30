package com;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import com.config.ThreadConfig;
import com.dao.NovelMapper;
import com.entity.Chapter;
import com.entity.ChapterDetail;
import com.entity.Novel;
import com.factory.ChapterDetailSpiderFactory;
import com.factory.NovelSpiderFactory;
import com.impl.NovelDownload;
import com.impl.chapter.ChapterSpider;
import com.interfaces.IChapterDetail;
import com.interfaces.IChapterSpider;
import com.interfaces.INovelDownload;
import com.interfaces.INovelSpider;
import com.interfaces.Processor;
import com.service.NovelService;

@SpringBootTest
class NovelApplicationTests {

	@Autowired NovelMapper novelMapper;
	@Autowired NovelService novelService;
	@Autowired Processor processor;
    @Autowired INovelDownload download;

	String url="http://www.u33.cc/u140686/";
	String url2="https://www.uutxt.com/book/43/43821/2520338.html";
	String url3="https://www.booktxt.net/23_23112/";
	String url4="http://www.biquku.la/47/47742/";

	@Test
	void chapterLoads() {
		IChapterSpider spider = new ChapterSpider();
		List<Chapter> chapters = spider.getsChapters(url4);
		for (Chapter chapter : chapters) {
			System.out.println(chapter);
		}
	}

	@Test
	void DetailLoads(){
		IChapterDetail chapterDetail = ChapterDetailSpiderFactory.getChapterDetail("http://www.biquku.la/47/47742/12670539.html");
		ChapterDetail detail = chapterDetail.getChapterDetail("http://www.biquku.la/47/47742/12670539.html");
		if(StringUtils.isEmpty(detail.getContent())) {
			System.out.println("没有内容");
		}else System.out.println("内容:"+detail.getContent());
	}

	@Test
	public void Download() throws Exception {
		ThreadConfig config = new ThreadConfig();
		config.setPath("f:/1");
		String addr = download.download(url3, config);
		File file = new File(addr);
		FileOutputStream out = new FileOutputStream(file);

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
	public void batchtest() {
		List<Novel> novels=new ArrayList<Novel>();
		Novel novel = new Novel();

		novel.setName("1231");
		novel.setUrl("123123");
		novels.add(novel);
		novelMapper.batchInsert(novels);
	}

	@Test
	public void test() {
		processor.merge("顶点小说");
	}

}
