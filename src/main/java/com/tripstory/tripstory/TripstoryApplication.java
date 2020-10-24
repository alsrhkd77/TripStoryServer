package com.tripstory.tripstory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class TripstoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(TripstoryApplication.class, args);
    }

}
