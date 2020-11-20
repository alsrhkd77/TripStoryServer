package com.tripstory.tripstory.post.dto;

import com.tripstory.tripstory.post.domain.enums.PostType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PostSearchItem {
    private Long postId;
    private String author;
    private String content;
    private LocalDateTime createdTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> imagePaths;
    private List<String> tags;
    private int likes;
    private int comments;
    private boolean liked;
    private PostType type;
}
