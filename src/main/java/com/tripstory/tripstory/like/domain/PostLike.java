package com.tripstory.tripstory.like.domain;

import com.tripstory.tripstory.like.domain.idclass.PostLikeId;
import com.tripstory.tripstory.member.domain.Member;
import com.tripstory.tripstory.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@IdClass(PostLikeId.class)
public class PostLike {

    @Id
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post postId;

    @Id
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member memberId;



}
