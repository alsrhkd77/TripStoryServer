package com.tripstory.tripstory.comment.domain;

import com.tripstory.tripstory.comment.dto.PostCommentDetail;
import com.tripstory.tripstory.member.domain.Member;
import com.tripstory.tripstory.post.domain.Post;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class PostComment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdTime;

    public PostCommentDetail toCommentDetail() {
        return PostCommentDetail.builder()
                .commentId(id)
                .content(content)
                .createdTime(createdTime)
                .build();
    }
}
