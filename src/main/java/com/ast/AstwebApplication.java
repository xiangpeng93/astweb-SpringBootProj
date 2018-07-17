package com.ast;

import org.apache.tomcat.util.descriptor.web.ErrorPage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @RequestMapping({"${server.error.path:${error.path:/error}}"})
    public class MainsiteErrorController extends AbstractErrorController {
        private static final String ERROR_PATH = "/error";

        public MainsiteErrorController(ErrorAttributes errorAttributes) {
            super(errorAttributes);
        }

        @RequestMapping(value = ERROR_PATH)
        public String handleError() {
            return "/";
        }

        @Override
        public String getErrorPath() {
            return "/";
        }
        private final ErrorProperties errorProperties = null;

        @RequestMapping(
                produces = {"text/html"}
        )
        public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
            HttpStatus status = this.getStatus(request);
            Map model = Collections.unmodifiableMap(this.getErrorAttributes(request, this.isIncludeStackTrace(request, MediaType.TEXT_HTML)));
            response.setStatus(200);
            ModelAndView modelAndView = this.resolveErrorView(request, response, status, model);
            return modelAndView != null?modelAndView:new ModelAndView("error", model);
        }

        @RequestMapping
        @ResponseBody
        public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
            Map body = this.getErrorAttributes(request, this.isIncludeStackTrace(request, MediaType.ALL));
            HttpStatus status = this.getStatus(request);
            return new ResponseEntity(body, status);
        }

        protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {
            ErrorProperties.IncludeStacktrace include = this.getErrorProperties().getIncludeStacktrace();
            return include == ErrorProperties.IncludeStacktrace.ALWAYS?true:(include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM?this.getTraceParameter(request):false);
        }

        protected ErrorProperties getErrorProperties() {
            return this.errorProperties;
        }

    }

    public static void main(String[] args) {
		SpringApplication.run(AstwebApplication.class, args);
	}
}