package com.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dao.NovelMapper;
import com.entity.Novel;
import com.interfaces.Processor;
import com.utlis.NovelSpiderFactory;

@Component
public class NovelStorage implements Processor {

	
	private static final Logger log = LoggerFactory.getLogger(NovelStorage.class);

	@Autowired NovelMapper novelMapper;
	
	public static NovelStorage ns;
	
	private final String xbqg="http://www.xbiquge.la/xiaoshuodaquan/";
	private final String dd="https://www.booktxt.net/xiaoshuodaquan/";
	
	@PostConstruct
	public void init(){ns=this;}
	
	@Override
	public void novelStorage() {
		delAll();
		log.info("开始更新数据库书籍列表");
		XBQGNovel();
		log.info("新笔趣阁更新完成");
		DDNovel();
		log.info("顶点小说更新完成");
	}
	
	private void XBQGNovel() {
		ns.novelMapper.batchInsert(getFactory(xbqg));
	}
	
	private void DDNovel() {
		ns.novelMapper.batchInsert(getFactory(dd));
	}
	private void delAll() {
		ns.novelMapper.deleteAll();
	}
	
	private List<Novel> getFactory(String url){
		return NovelSpiderFactory.getNovelSpider(url).getNovel(url);
	}

}
