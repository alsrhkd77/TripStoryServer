package com.tripstory.tripstory.comment.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AuthorProfile {
    private String author;
    private String profileImagePath;
}
