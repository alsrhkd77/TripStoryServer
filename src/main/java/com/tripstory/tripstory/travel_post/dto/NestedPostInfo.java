package com.tripstory.tripstory.travel_post.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NestedPostInfo {
    private Long postId;
    private int likes;
    private boolean liked;
    private String thumbnailImagePath;
    private int comments;
}
