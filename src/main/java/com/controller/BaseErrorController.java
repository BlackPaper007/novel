package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BaseErrorController implements ErrorController{

	private static final String ERROR_PATH = "error";
	
	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

	@RequestMapping(value = ERROR_PATH,produces = "text/html")
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response){
		HttpStatus status = getStatus(request);
		ModelAndView view = new ModelAndView();
		view.setViewName(ERROR_PATH);
		view.getModel().put("code", status);
		if(status.is4xxClientError()) {
			view.getModel().put("msg", "你这个请求错了吧！");
		} else if (status.is5xxServerError()) {
			view.getModel().put("msg", "服务器都给你玩坏了！");
		} else {
			view.getModel().put("msg", "嘿嘿嘿！！！");
		}
        return view;
    }
	
	private HttpStatus getStatus(HttpServletRequest request){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode == null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return  HttpStatus.valueOf(statusCode);
        }catch (Exception ex){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
