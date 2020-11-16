package com.tripstory.tripstory.post.dto;

import com.tripstory.tripstory.post.domain.enums.DisclosureScope;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDetail {

    private String author;
    private String content;
    private List<String> tags;
    private List<String> imagePaths;
    private LocalDateTime createdTime;
    private DisclosureScope scope;
    private int likes;
    private boolean isLiked;

}
