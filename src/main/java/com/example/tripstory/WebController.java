package com.example.tripstory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class WebController {
    //private final JdbcTemplate jdbcTemplate;

    @Autowired
    TestRepository testRepository;

    @GetMapping("/insert")
    String pushInsert(){
        String result = "faild TT";
        return result;
    }

    @GetMapping("/getTuples")
    public String getTuples() {
        List<TestEntitiy> testEntitiys = testRepository.findAll();
        String result ="";
        result = testEntitiys.get(0).getId().toString() + " " + testEntitiys.get(0).getPw().toString();
        return result;
        /*
        return this.jdbcTemplate.queryForList("SELECT * FROM test").stream()
                .map((m) -> m.values().toString())
                .collect(Collectors.toList()).toString();
         */
    }
}
