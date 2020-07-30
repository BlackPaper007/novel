package com.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dao.NovelMapper;
import com.entity.Novel;
import com.factory.NovelSpiderFactory;
import com.interfaces.Processor;
import com.novelEnum.Site;

@Component
public class NovelStorage implements Processor {

	private static final Logger log = LoggerFactory.getLogger(NovelStorage.class);
	private static Map<String, String> map = new HashMap<>();

	static {
		map.put("新笔趣阁", "http://www.xbiquge.la/xiaoshuodaquan/");
		map.put("顶点小说", "https://www.booktxt.net/xiaoshuodaquan/");
		map.put("笔趣阁", "https://www.biquge.lu/xiaoshuodaquan/");
		map.put("笔趣库", "http://www.biquku.la/xiaoshuodaquan/");
	}
	@Autowired
	NovelMapper novelMapper;

	public static NovelStorage ns;

	@PostConstruct
	public void init() {
		ns = this;
	}

	@Transactional(rollbackFor = Exception.class)
	public void merge(String site) {
		String url = map.get(site);
		List<Novel> novels = getFactory(url);
		List<Novel> data = ns.novelMapper.selectBySite(Site.getEnumByUrl(url).getId());
		int updateBatch = ns.novelMapper.updateBatch(novels);
		log.info("更新数据 " + updateBatch + " 条");

		novels = getDiffElement(data, novels, new ArrayList<>());

		int batchInsert = novels.size() == 0 ? 0 : ns.novelMapper.batchInsert(novels);
		log.info("插入数据 " + batchInsert + " 条");
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateNovel(String site) {
		String url = map.get(site);
		ns.novelMapper.updateBatch(getFactory(url));
	}

	@Transactional(rollbackFor = Exception.class)
	public void insertNovel(String site) {
		String url = map.get(site);
		ns.novelMapper.batchInsert(getFactory(url));
	}

	/**
	 * 取出集合中不同的元素
	 * @param data 被对比的集合
	 * @param source 外部数据
	 * @param newList 接收对象
	 * @return
	 */
	private List<Novel> getDiffElement(List<Novel> data,List<Novel> source,List<Novel> newList){
		//取集合中最后一个元素
		Novel last = data.get(data.size() - 1);
		source.forEach(k -> {
			for (Novel d : data) {
				if (d.getUrl().equals(k.getUrl()))
					break;
				else if (d.equals(last))
					newList.add(k);
			}
		});
		return newList;
	}
	
	private List<Novel> getFactory(String url) {
		return NovelSpiderFactory.getNovelSpider(url).getNovel(url);
	}
}
