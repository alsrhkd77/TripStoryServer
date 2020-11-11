package com.tripstory.tripstory.post.dto;

import com.tripstory.tripstory.post.domain.enums.PostType;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class PostThumbnail {

    private Long postId;
    private String content;
    private LocalDateTime createTime;
    private String thumbnailPath;
    private PostType type;
}
