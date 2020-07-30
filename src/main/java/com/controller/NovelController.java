package com.controller;


import java.util.List;

import com.interfaces.INovelDownload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.entity.ChapterDetail;
import com.entity.Novel;
import com.factory.ChapterDetailSpiderFactory;
import com.factory.ChapterSpiderFactory;
import com.interfaces.Processor;
import com.service.NovelService;

@Controller
public class NovelController {

	@Autowired NovelService novelService;

	@Autowired Processor processor;

	@Autowired INovelDownload download;




	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/keywordSearch/{keyword}", method = RequestMethod.POST)
	@ResponseBody
	public List<Novel> keywordSearch(@PathVariable("keyword") String keyword) {
		return novelService.getNovelsByKeyword(keyword);
	}

	@RequestMapping(value = "/chapterListByKeyword/{keyword}")
	@ResponseBody
	public Novel chapterListByKeyword(@PathVariable("keyword") String keyword) {
		return novelService.getNovelByKeyword(keyword);
	}

	@RequestMapping(value = "/chapterList")
	@ResponseBody
	public ModelAndView chapterList(String url) {
		ModelAndView view = new ModelAndView();
		view.setViewName("chapterList");
		view.getModel().put("chapters", ChapterSpiderFactory.getChapterSpider(url).getsChapters(url));
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

	/*@ResponseBody
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(String url, HttpServletResponse resp) throws Exception {
		*//*ThreadConfig t = new ThreadConfig();
		t.setPath("f:/1");
		String addr = download.download(url, t);*//*
		String addr ="f:/1/booktxt.net/顾承安时语/merge/顾承安时语.txt";

		String filename = addr.substring(addr.lastIndexOf("/") + 1).trim();
		BufferedInputStream in = null;
		ServletOutputStream out = null;
		File file = new File(addr);

		resp.reset();
		//文件类型根据后缀名判断
		resp.setContentType("text/plain; charset=utf-8");
		resp.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
		resp.setContentLength((int) file.length());
		resp.setCharacterEncoding("UTF-8");

		in = new BufferedInputStream(new FileInputStream(addr));
		out = resp.getOutputStream();
		int len = 0;
		byte[] buf = new byte[1024];
		while ((len = in.read(buf)) > 0) {
			//将缓冲区的数据输出到客户端浏览器
			out.write(buf,0,len);
			out.flush();
		}
		out.close();
		in.close();
	}*/

	@RequestMapping(value = "/put/{site}", method = RequestMethod.PUT)
	public String putNovel(@PathVariable("site")String site) {
		processor.insertNovel(site);
		return "redirect:/";
	}

	@RequestMapping(value = "/update/{site}", method = RequestMethod.GET)
	public String updateNovel(@PathVariable("site")String site) {
		processor.updateNovel(site);
		return "redirect:/";
	}

	@RequestMapping(value = "/merge/{site}", method = RequestMethod.GET)
	public String mergeNovel(@PathVariable("site")String site) {
		processor.merge(site);
		return "redirect:/";
	}
}
