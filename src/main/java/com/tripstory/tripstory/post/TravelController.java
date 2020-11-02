package com.tripstory.tripstory.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class TravelController {

    private final ObjectMapper mapper;

    @PostMapping("/test")
    public void test(@RequestParam Map<String, String> map) {
        System.out.println("map.get(\"jsonTest\") = " + map.get("jsonTest"));
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class DTO {
        private String key;
        private String value;
    }
}
