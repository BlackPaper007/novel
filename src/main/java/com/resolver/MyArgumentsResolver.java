package com.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class MyArgumentsResolver implements HandlerMethodArgumentResolver{

	/**
     * 解析器是否支持当前参数
     */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// 指定参数如果被应用注解（可以用自定义注解），则使用该解析器。
        // 如果直接返回true，则代表将此解析器用于所有参数
		return false;
	}

	/**
     * 将request中的请求参数解析到当前Controller参数上
     * @param parameter 需要被解析的Controller参数，此参数必须首先传给{@link #supportsParameter}并返回true
     * @param mavContainer 当前request的ModelAndViewContainer
     * @param webRequest 当前request
     * @param binderFactory 生成{@link WebDataBinder}实例的工厂
     * @return 解析后的Controller参数
     */
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		 // 解析器中的自定义逻辑——urldecode
	    //Object arg = URLDecoder.decode(webRequest.getParameter(parameter.getParameterName()), "UTF-8");
		return null;
	}

}
