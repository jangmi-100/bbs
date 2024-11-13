package com.springbootstudy.bbs.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ch.qos.logback.core.model.processor.ResourceHandlerBase;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/writeForm").setViewName("views/writeForm");
		registry.addViewController("/writeBoardd").setViewName("views/writeForm");
		registry.addViewController("/loginForm").setViewName("member/loginForm");
		registry.addViewController("/joinForm").setViewName("member/memberJoinForm");
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("/resources/files/**")
		.addResourceLocations("file:./src/main/resources/static/files/")
		.setCachePeriod(1);
		
	}
}
