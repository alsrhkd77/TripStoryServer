package com.tripstory.tripstory.normal_post.dto;

import com.tripstory.tripstory.post.dto.PostDetail;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class NormalPostDetailDTO {

    private String result;
    private String errors;
    private PostDetail postDetail;
    private NormalPostInfo normalPostInfo;

}
