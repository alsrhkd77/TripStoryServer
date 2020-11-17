package com.tripstory.tripstory.timeline;

import com.tripstory.tripstory.like.LikeService;
import com.tripstory.tripstory.post.domain.Post;
import com.tripstory.tripstory.timeline.dto.TimeLineItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TimeLineService {

    private final TimeLineRepository timeLineRepository;
    private final LikeService likeService;

    public List<TimeLineItem> getTimeLine(int offset, int limit, String memberId) {
        List<Post> all = timeLineRepository.findAll(offset, limit);
        List<TimeLineItem> result = all.stream().map(Post::toTimeLineItem).collect(Collectors.toList());
        result.stream().forEach(item -> {
            item.setLiked(likeService.isLiked(item.getPostId(), memberId));
        });
        return result;
    }
}
