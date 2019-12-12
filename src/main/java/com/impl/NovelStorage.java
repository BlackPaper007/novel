package com.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private static Map<String,String> map = new HashMap<>();
	
	static {
		map.put("新笔趣阁", "http://www.xbiquge.la/xiaoshuodaquan/");
		map.put("顶点小说", "https://www.booktxt.net/xiaoshuodaquan/");
		map.put("笔趣阁", "https://www.biquge.lu/xiaoshuodaquan/");
	}
	@Autowired NovelMapper novelMapper;
	
	public static NovelStorage ns;
	
	@PostConstruct
	public void init(){ns=this;}
	
	@Override
	public void novelStorage() {
		delAll();
		updateNovel();
	}
	
	private void updateNovel() {
		map.forEach((k,v)->{
			ns.novelMapper.batchInsert(getFactory(v));
			log.info(k+"更新完成");
		});
	}
	
	private void delAll() {
		ns.novelMapper.deleteAll();
	}
	
	private List<Novel> getFactory(String url){
		return NovelSpiderFactory.getNovelSpider(url).getNovel(url);
	}

}
