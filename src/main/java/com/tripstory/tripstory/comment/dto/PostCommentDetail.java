package com.tripstory.tripstory.comment.dto;

import com.tripstory.tripstory.member.dto.MemberProfile;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PostCommentDetail {

    private Long commentId;
    private AuthorProfile authorProfile;
    private String content;
    private LocalDateTime createdTime;
}
