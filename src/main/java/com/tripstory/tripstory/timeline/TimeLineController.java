package com.tripstory.tripstory.timeline;

import com.tripstory.tripstory.post.domain.Post;
import com.tripstory.tripstory.timeline.dto.TimeLineDTO;
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

    private final TimeLineService timeLineService;

    @GetMapping("/{member-id}/{offset}/{limit}")
    public TimeLineDTO getTimeLine(@PathVariable("member-id") String memberId, @PathVariable int offset, @PathVariable int limit) {
        List<TimeLineItem> timeLine = timeLineService.getTimeLine(offset, limit, memberId);
        TimeLineDTO timeLineDTO = new TimeLineDTO();
        timeLineDTO.setCurrentItemCount(timeLine.size());
        timeLineDTO.setTotalRequestItemCount(offset + timeLine.size());
        timeLineDTO.setResult("success");
        timeLineDTO.setItems(timeLine);
        return timeLineDTO;
    }
}
