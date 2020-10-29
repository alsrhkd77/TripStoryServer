package com.tripstory.tripstory;

import com.tripstory.tripstory.util.FileStorage;
import com.tripstory.tripstory.util.GCPFileStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

@Configuration
@Profile("prod")
public class ProdApplicationConfig implements WebMvcConfigurer {

    @Bean
    public FileStorage fileStorage() {
        return new GCPFileStorage();
    }

    @PostConstruct
    public void checkProfile() {
        System.out.println("--------------------------------------------");
        System.out.println("운영용 환경으로 셋업");
        System.out.println("--------------------------------------------");
    }
}
