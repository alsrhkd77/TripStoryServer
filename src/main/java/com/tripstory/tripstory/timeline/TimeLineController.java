package com.tripstory.tripstory.timeline;

import com.tripstory.tripstory.post.domain.Post;
import com.tripstory.tripstory.timeline.dto.TimeLineItem;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/timeline")
@RequiredArgsConstructor
public class TimeLineController {

    private final TimeLineRepository timeLineRepository;

    @GetMapping("/{offset}/{limit}")
    public List<TimeLineItem> getTimeLine(@PathVariable Integer offset, @PathVariable Integer limit) {
        return timeLineRepository.findAll(offset, limit).stream()
                .map(Post::toTimeLineItem)
                .collect(Collectors.toList());
    }
}
