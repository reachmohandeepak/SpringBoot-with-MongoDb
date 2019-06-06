package com.ymk.project.api.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class Configurations {

	@Bean
	public MessageSource errorMessageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:/locale/error_messages");
		//messageSource.setDefaultEncoding(CharEncodin.UTF_8);
		return messageSource;
	}

}
