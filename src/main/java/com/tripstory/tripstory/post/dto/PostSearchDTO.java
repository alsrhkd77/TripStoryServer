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
    public static class MyPostsDTO {
        private String result;
        private String errors;
        private int postCount;
        private List<PostThumbnail> postThumbnails;
    }

//    public static class MyPostDetailDTO {
//        private Long postId;
//        private String author;
//        private String content;
//
//    }
//
//    public static class OtherPostDetailDTO {
//        private Long postId;
//        private String nickName;
//    }
}
