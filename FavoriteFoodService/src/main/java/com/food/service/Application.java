package com.food.service;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.MultiPartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//import com.siblinks.ws.util.AppConfigManager;

@ComponentScan 
@EnableAutoConfiguration
@Configuration

public class Application {
	
	 @Bean
	    public MultipartConfigElement multipartConfigElement() {
			MultiPartConfigFactory factory = new MultiPartConfigFactory();
	        factory.setMaxFileSize("1280KB");
	        factory.setMaxRequestSize("1280KB");
	        return factory.createMultipartConfig();
	    }
	
    public static void main(String[] args) {
    	
    	String sibenv = System.getProperty("sib.env");
		if (sibenv == null) {
			sibenv = "dev";
		}
    	//AppConfigManager.init(sibenv);
        SpringApplication.run(Application.class, args);
    }
}