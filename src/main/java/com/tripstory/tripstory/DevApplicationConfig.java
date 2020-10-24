package com.tripstory.tripstory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Profile("dev")
public class DevApplicationConfig implements WebMvcConfigurer {

    @Value("${resources.post-image.location}")
    private String postImageLocation;

    @Value("${resources.post-image.path")
    private String postImagePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(postImagePath + "/**")
                .addResourceLocations("file:///" + postImageLocation + "/**");
    }
}
