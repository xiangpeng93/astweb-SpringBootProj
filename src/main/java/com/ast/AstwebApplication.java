package com.ast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication()
public class AstwebApplication extends SpringBootServletInitializer {
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
