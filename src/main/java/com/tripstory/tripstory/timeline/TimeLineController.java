package com.tripstory.tripstory.timeline;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/timeline")
@RequiredArgsConstructor
public class TimeLineController {

    private final TimeLineRepository timeLineRepository;

    @GetMapping
    public void getTimeLine() {
        List<Object[]> all = timeLineRepository.findAll(0, 3);
        for (Object[] objects : all) {
            for (Object object : objects) {
                System.out.println("object = " + object);;
            }
        }

    }
}
