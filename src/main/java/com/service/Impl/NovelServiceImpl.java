package com.service.Impl;

import java.util.List;

import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.NovelMapper;
import com.entity.Novel;

import com.service.NovelService;

import lombok.extern.slf4j.Slf4j;

import com.impl.abs.AbstractSpider;
import com.impl.novels.job.DDNovelJob;
import com.novelEnum.Site;

@Service
@Slf4j
public class NovelServiceImpl extends AbstractSpider implements NovelService {


	@Autowired NovelMapper novelMapper;

	public List<Novel> getNovelsByKeyword(String keyword) {

		keyword = "%" + keyword + "%";
		List<Novel> list = novelMapper.selectByBooknameOrAuthor(keyword);
		if(list.isEmpty())System.out.println("数据库暂无");
		return list;
	}

	@Override
	public Novel getNovelByKeyword(String keyword) {

		String requestUrl="https://so.biqusoso.com/s1.php?ie=gbk&siteid=booktxt.net&s=2758772450457967865&q=";
		Novel novel = new Novel();
		String pre="https://www.booktxt.net/";
		try {
			keyword = URLEncoder.encode(keyword, "gbk");
			String url = requestUrl + keyword;
			String result = crawl(url);
			Document doc = Jsoup.parse(result);

			Element e = doc.select("#search-main .search-list li").get(1);
			String attr = e.select("a").attr("href");
			String s = attr.substring(attr.lastIndexOf("/")+1);
			novel.setUrl(pre+s.substring(0,s.length()-3)+"_"+s+"/");
			novel.setName(e.select("a").text());
			novel.setAuthor(e.select(".s4").text());
			novel.setPlatformId(Site.getEnumByUrl(url).getId());

			novel = new DDNovelJob(novel.getUrl(), novel.getName()).call();

			/*Elements element = doc.select("#search-main .search-list li");
			//移除标题
			element.remove(0);
			for (Element e : element) {
				String attr = e.select("a").attr("href");
				String s = attr.substring(attr.lastIndexOf("/")+1);
				novel.setUrl(pre+s.substring(0,s.length()-3)+"_"+s+"/");
				novel.setName(e.select("a").text());
				novel.setAuthor(e.select(".s4").text());
				novel.setPlatformId(Site.getEnumByUrl(url).getId());
				novel = new DDNovelCallable(novel.getUrl(),novel.getName(), config.getTries()).call();
			}*/
			//防止插入重复数据
			List<Novel> list = novelMapper.selectByBooknameOrAuthor(novel.getName());
			if(!list.isEmpty()) return list.get(0);
			novelMapper.insert(novel);
		} catch (Exception e) {
			log.info("-----站点搜索为空-----",e);
			return null;
		}
		return novel;
	}

}

