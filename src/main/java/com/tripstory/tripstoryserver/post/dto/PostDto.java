package com.tripstory.tripstoryserver.post.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private String postAuthor;
    private String postContent;
    private List<MultipartFile> postImages;
    private List<String> postTags;
}
