package com.tripstory.tripstory.post.dto;

import com.tripstory.tripstory.post.domain.PostType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Setter
@Getter
@AllArgsConstructor
public class PostThumbnail {
    private Long postId;
    private String content;
    private LocalDateTime createTime;
    private String thumbnailPath;
    private PostType type;
}
