package com.tripstory.tripstoryserver.post.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PostImages {

    @Id @GeneratedValue
    @Column(name = "POST_IMAGE_ID")
    private Long id;

    @Column(name = "POST_IMAGE_PATH", nullable = false)
    private String postImgPath;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

}
