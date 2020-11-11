package com.tripstory.tripstory;

import com.tripstory.tripstory.util.FileStorage;
import com.tripstory.tripstory.util.GCPFileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

@Configuration
@Profile("prod")
public class ProdApplicationConfig implements WebMvcConfigurer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Bean
    public FileStorage fileStorage() {
        return new GCPFileStorage();
    }

    @PostConstruct
    public void checkProfile() {
        logger.info("운영환경으로 실행");
    }
}
