package com.tripstory.tripstory.post.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@Builder
@Getter
@Setter
public class PostDetailDTO {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class DetailInfo {
        private Long postId;
        private String nickName;
        private String content;
        private LocalDateTime createTime;
        private List<String> tags;
        private List<String> imagePaths;
        private int likes;
        private LocalDate visitStart;
        private LocalDate visitEnd;
    }
}
