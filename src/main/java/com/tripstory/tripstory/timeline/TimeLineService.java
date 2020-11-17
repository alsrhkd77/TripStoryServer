package com.tripstory.tripstory.timeline;

import com.tripstory.tripstory.post.domain.Post;
import com.tripstory.tripstory.timeline.dto.TimeLineItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TimeLineService {

    private final TimeLineRepository timeLineRepository;

    public List<Post> getTimeLine(int offset, int limit) {
        return timeLineRepository.findAll(offset, limit);
    }
}
