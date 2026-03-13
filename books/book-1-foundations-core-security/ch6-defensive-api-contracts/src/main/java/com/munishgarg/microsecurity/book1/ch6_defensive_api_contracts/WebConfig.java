package com.munishgarg.microsecurity.book1.ch6_defensive_api_contracts;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final DeadlineInterceptor deadlineInterceptor;

    public WebConfig(DeadlineInterceptor deadlineInterceptor) {
        this.deadlineInterceptor = deadlineInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(deadlineInterceptor);
    }
}
