<<<<<<< HEAD
package com.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.NovelMapper;
import com.entity.Novel;
import com.service.NovelService;

@Service
public class NovelServiceImpl implements NovelService {

	@Autowired NovelMapper novelMapper;
	
	@Override
	public List<Novel> getsNovelByKeyword(String keyword) {
		keyword = "%" + keyword + "%";
		List<Novel> list = novelMapper.selectByBooknameOrAuthor(keyword);
		return list;
	}

}
=======
package com.service.Impl;

<<<<<<< HEAD
import java.util.List;

=======
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.Callable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
>>>>>>> second commit
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.NovelMapper;
import com.entity.Novel;
<<<<<<< HEAD
import com.service.NovelService;

@Service
public class NovelServiceImpl implements NovelService {
=======
import com.impl.abs.AbstractSpider;
import com.impl.novels.job.DDNovelCallable;
import com.novelEnum.Site;
import com.service.NovelService;

@Service
public class NovelServiceImpl extends AbstractSpider implements NovelService {
>>>>>>> second commit

	@Autowired NovelMapper novelMapper;
	
	@Override
<<<<<<< HEAD
	public List<Novel> getsNovelByKeyword(String keyword) {
=======
	public List<Novel> getNovelsByKeyword(String keyword) {
>>>>>>> second commit
		keyword = "%" + keyword + "%";
		List<Novel> list = novelMapper.selectByBooknameOrAuthor(keyword);
		return list;
	}

<<<<<<< HEAD
=======
	@Override
	public Novel getNovelByKeyword(String keyword) {
		//String requestUrl="https://search1.booktxt.net/modules/article/search.php?searchkey=";
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
			
			Callable<Novel> novelCallable = new DDNovelCallable(novel.getUrl(),novel.getName(), config.getTries());
			novelMapper.insert(novelCallable.call());
		} catch (Exception e) {
			log.info("-----站点搜索为空-----",e);
			return null;
		}
		return novel;
	}
>>>>>>> second commit
}
>>>>>>> second commit
