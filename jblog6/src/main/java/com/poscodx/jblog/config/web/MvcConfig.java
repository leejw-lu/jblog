package com.poscodx.jblog.config.web;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@SpringBootConfiguration
public class MvcConfig implements WebMvcConfigurer{
	// Locale Resolver (from LocaleConfig)
	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		localeResolver.setCookieName("lang");
		localeResolver.setCookieHttpOnly(false);

		return localeResolver;
	}
	
	// View Resolver
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		//viewResolver.setExposeContextBeansAsAttributes(true);
		//viewResolver.setExposedContextBeanNames("site");

		return viewResolver;
	}

	// Message Converters는 @SpringBootConfiguration이 자동 설정
	
}
