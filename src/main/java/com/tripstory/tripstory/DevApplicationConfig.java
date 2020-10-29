package com.tripstory.tripstory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

@Configuration
@Profile("dev")
public class DevApplicationConfig implements WebMvcConfigurer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Value("${resources.post-image.location}")
    private String postImageLocation;

    @Value("${resources.post-image.path}")
    private String postImagePath;

//    @Value()

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(postImagePath + "/**")
                .addResourceLocations("file:///" + postImageLocation);

//        registry.addResourceHandler()
    }

    @PostConstruct
    public void checkProfile() {
        logger.info("개발환경으로 실행");
    }
}
