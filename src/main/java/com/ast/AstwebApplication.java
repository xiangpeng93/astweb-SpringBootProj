package com.ast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

@SpringBootApplication()
public class AstwebApplication extends SpringBootServletInitializer {
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setForceEncoding(true);
        characterEncodingFilter.setEncoding("UTF-8");
        registrationBean.setFilter(characterEncodingFilter);
        return registrationBean;
    }

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AstwebApplication.class);
	}

    @Controller
    public class MainsiteErrorController extends BasicErrorController {
        private static final String ERROR_PATH = "/error";
        public MainsiteErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
            super(errorAttributes, errorProperties);
        }
        @RequestMapping(value = ERROR_PATH)
        public String handleError() {
            return "/";
        }
        @Override
        public String getErrorPath() {
            return "/";
        }
        @Override
        public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
            HttpStatus status = super.getStatus(request);
            Map model = Collections.unmodifiableMap(this.getErrorAttributes(request, this.isIncludeStackTrace(request, MediaType.TEXT_HTML)));
            response.setStatus(200);
            ModelAndView modelAndView = this.resolveErrorView(request, response, status, model);
            return modelAndView != null?modelAndView:new ModelAndView("error", model);
        }
    }

    public static void main(String[] args) {
		SpringApplication.run(AstwebApplication.class, args);
	}
}