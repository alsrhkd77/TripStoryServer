package com.tripstory.tripstory.post.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class PostImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postImageId;

    private String path;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public void changePost(Post post) {
        this.post = post;
    }
}
