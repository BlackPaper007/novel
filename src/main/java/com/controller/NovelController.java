package com.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.entity.ChapterDetail;
import com.entity.Novel;
import com.interfaces.Processor;
import com.service.NovelService;
import com.utlis.ChapterDetailSpiderFactory;
import com.utlis.ChapterSpiderFactory;

@Controller
public class NovelController {

	@Autowired NovelService novelService;
	
	@Autowired Processor processor;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping(value = "/keywordSearch/{keyword}", method = RequestMethod.GET)
	@ResponseBody
	public List<Novel> keywordSearch(@PathVariable("keyword") String keyword) {
		List<Novel> novels = novelService.getsNovelByKeyword(keyword);
		return novels;
	}
	
	
	@RequestMapping(value = "/chapterList", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView chapterList(String url) {
		ModelAndView view = new ModelAndView();
		view.setViewName("chapterList");
		view.getModel().put("chapters", ChapterSpiderFactory.getChapterSpider(url));
		view.getModel().put("baseUrl", url);
		return view;
	}
	
	@RequestMapping(value = "/chapterDetail", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView chapterDetail(String url, String baseUrl) {
		ModelAndView view = new ModelAndView();
		view.setViewName("chapterDetail");
		try {
			ChapterDetail detail = ChapterDetailSpiderFactory.getChapterDetail(url).getChapterDetail(url);
			detail.setContent(detail.getContent().replace("\n", "<br>"));
			view.getModel().put("detail", detail);
			view.getModel().put("status", 0);
		} catch (Exception e) {
			view.getModel().put("status", 1);
			e.printStackTrace();
		}
		view.getModel().put("baseUrl", baseUrl);
		return view;
	}
	
	@RequestMapping(value = "/updateNovel", method = RequestMethod.GET)
	public String updateNovel() {
		processor.novelStorage();
		return "index";
	}
}