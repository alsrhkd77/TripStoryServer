package com.tripstory.tripstoryserver.post.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDto {

    private Long postId;
    private String postAuthor;
    private String postContent;
    private List<String> postImagePaths;
}
