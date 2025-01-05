package com.example.newspost.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WebConfig implements WebFluxConfigurer {
    // 여기에 WebFlux 관련 설정

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /uploads/** URL로 접근할 때 C:/javaTest/NewsPost/src/main/resources/static/uploads/ 디렉토리 매핑
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:C:/javaTest/NewsPost/src/main/resources/static/uploads/");
    }


}
