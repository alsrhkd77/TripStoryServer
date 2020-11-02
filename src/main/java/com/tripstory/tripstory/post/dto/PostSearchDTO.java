package com.tripstory.tripstory.post.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class PostSearchDTO {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class MyPostDTO {
        private String result;
        private String errors;
        private int postCount;
        private List<PostThumbnail> postThumbnails;
    }
}
