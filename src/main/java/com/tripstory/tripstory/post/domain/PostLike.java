package com.tripstory.tripstory.post.domain;

import com.tripstory.tripstory.member.domain.Member;
import com.tripstory.tripstory.post.domain.idclass.PostLikeId;

import javax.persistence.*;

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
