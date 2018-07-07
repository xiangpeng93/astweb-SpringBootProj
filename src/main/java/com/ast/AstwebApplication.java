package com.ast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.List;

@SpringBootApplication()
public class AstwebApplication extends SpringBootServletInitializer {

    @Configuration
    public class CustomMVCConfiguration extends WebMvcConfigurationSupport {

        @Bean
        public HttpMessageConverter<String> responseBodyConverter() {
            StringHttpMessageConverter converter = new StringHttpMessageConverter(
                    Charset.forName("UTF-8"));
            return converter;
        }

        @Override
        public void configureMessageConverters(
                List<HttpMessageConverter<?>> converters) {
            super.configureMessageConverters(converters);
            converters.add(responseBodyConverter());
        }

        @Override
        public void configureContentNegotiation(
                ContentNegotiationConfigurer configurer) {
            configurer.favorPathExtension(false);
        }
    }

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AstwebApplication.class);
	}

    @Controller
    public class MainsiteErrorController implements ErrorController {
        private static final String ERROR_PATH = "/error";
        @RequestMapping(value = ERROR_PATH)
        public String handleError() {
            return "/";
        }
        @Override
        public String getErrorPath() {
            return ERROR_PATH;
        }
    }


        public static void main(String[] args) {
		SpringApplication.run(AstwebApplication.class, args);
	}
}
