package com.controller;


import java.util.List;

import com.annotation.CrawlLog;
import com.interfaces.INovelDownload;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.entity.ChapterDetail;
import com.entity.Novel;
import com.factory.ChapterDetailSpiderFactory;
import com.factory.ChapterSpiderFactory;
import com.interfaces.Processor;
import com.service.NovelService;

@Api(tags = "小说抓取")
@RestController
public class NovelController {

	@Autowired NovelService novelService;

	@Autowired Processor processor;

	@Autowired INovelDownload download;


	@CrawlLog
	@ApiOperation("根据关键字查询小说列表")
	@GetMapping("/keywordSearch/{keyword}")
	public List<Novel> keywordSearch(@PathVariable("keyword") String keyword) {
		return novelService.getNovelsByKeyword(keyword);
	}

	@CrawlLog
	@ApiOperation("根据关键字查询小说")
	@GetMapping("/chapterListByKeyword/{keyword}")
	public Novel chapterListByKeyword(@PathVariable("keyword") String keyword) {
		return novelService.getNovelByKeyword(keyword);
	}

	@ApiOperation("展示小说章节")
	@GetMapping("/chapterList")
	public ModelAndView chapterList(String url) {
		ModelAndView view = new ModelAndView();
		view.setViewName("chapterList");
		view.getModel().put("chapters", ChapterSpiderFactory.getChapterSpider(url).getChapters(url));
		view.getModel().put("baseUrl", url);
		return view;
	}

	@ApiOperation("展示小说内容")
	@GetMapping("/chapterDetail")
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

	/*
	@RequestMapping("/download")
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

	@PutMapping("/put/{site}")
	public void putNovel(@PathVariable("site")String site,ModelAndView model) {
		processor.insertNovel(site);
		model.setViewName("index");
	}

	@GetMapping("/update/{site}")
	public void updateNovel(@PathVariable("site")String site,ModelAndView model) {
		processor.updateNovel(site);
		model.setViewName("index");
	}

	@GetMapping("/merge/{site}")
	public void mergeNovel(@PathVariable("site")String site ,ModelAndView model) {
		processor.merge(site);
		model.setViewName("index");
	}
}
