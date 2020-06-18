<<<<<<< HEAD
package com.utlis;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer  {

	/**
	 * 配置静态资源映射
	 * addResoureHandler：指的是对外暴露的访问路径
	 * addResourceLocations：指的是内部文件放置的目录
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}

}
=======
package com.utlis;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer  {

	/**
	 * 配置静态资源映射
	 * addResoureHandler：指的是对外暴露的访问路径
	 * addResourceLocations：指的是内部文件放置的目录
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}

}
>>>>>>> second commit
