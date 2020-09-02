package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class BaseErrorController extends BasicErrorController {

	private static final String ERROR_PATH = "/error";

	public BaseErrorController(ErrorAttributes errorAttributes) {
		super(errorAttributes, new ErrorProperties());
	}

	@RequestMapping(produces = "text/html")
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

	protected HttpStatus getStatus(HttpServletRequest request){
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
