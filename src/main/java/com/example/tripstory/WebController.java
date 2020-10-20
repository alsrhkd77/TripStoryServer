package com.example.tripstory;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class WebController {
    private final JdbcTemplate jdbcTemplate;

    WebController(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/insert")
    String pushInsert(){
        String result = "faild TT";
        return result;
    }

    @GetMapping("/getTuples")
    public String getTuples() {
        return this.jdbcTemplate.queryForList("SELECT * FROM test").stream()
                .map((m) -> m.values().toString())
                .collect(Collectors.toList()).toString();
    }
}
