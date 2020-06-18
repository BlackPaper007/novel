package com.impl.novels.job;

import java.util.concurrent.Callable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.entity.Novel;
import com.impl.abs.AbstractSpider;
import com.novelEnum.Site;
import com.utlis.ChapterSpiderUtil;
import com.utlis.Config;

public class BQGNovelCallable extends AbstractSpider implements Callable<Novel>{

	private Element e;
	private int tries;
	
	public BQGNovelCallable(Element e,int tries) {
		this.e = e;
		this.tries = tries;
	}
	
	@Override
	public Novel call() throws Exception {
		Novel novel = new Novel();
		for (int i = 0; i < tries; i++) {
			try {
				String url = e.attr("abs:href");
				String novelName = e.text();
				String result = super.crawl(url);
				result = result.replace("&nbsp;", " ");
				Document doc = Jsoup.parse(result);
				doc.setBaseUri(url);
				String info = Config.getContext(Site.getEnumByUrl(url)).get("novel-info");
				Elements maininfo = doc.select(info);
				Elements small = maininfo.select("div[class=small]").select("span");
				Elements intro = maininfo.select("div[class=intro]");

				novel.setName(novelName);
				novel.setUrl(url);
				String author=small.get(0).text().substring(small.get(0).text().indexOf("：") + 1);
				novel.setAuthor(author);
				novel.setStatus(ChapterSpiderUtil.getNovelStatus(small.get(2).text().substring(small.get(2).text().indexOf("：") + 1)));
				String strDate = small.get(4).text();
				strDate = strDate.substring(strDate.indexOf("：") + 1);
				novel.setLatelytime(ChapterSpiderUtil.getData(strDate, "yyyy-MM-dd HH:mm:ss"));
				novel.setLatelychapter(small.get(5).select("a").text());
				novel.setLatelychapterurl((small.get(5).select("a").attr("abs:href")));
				novel.setInfo(intro.text().replace("推荐地址："+url, "").replace("作者："+author+"所写的《"+novelName+"》无弹窗免费全文阅读为转载作品,章节由网友发布。", ""));
				novel.setPlatformId(Site.getEnumByUrl(url).getId());
				novel.setType(small.get(1).text().substring(small.get(1).text().indexOf("：") + 1));
				break;
			} catch (NullPointerException e) {
				log.warn("空指针的小说是："+novel);
			} catch (Exception e) {
				log.info("响应代码："+http.getCode());
				log.info("尝试第[" + (i + 1) + "/" + tries + "]次抓取失败了！");
			}
		}
		return novel;
	}
}
