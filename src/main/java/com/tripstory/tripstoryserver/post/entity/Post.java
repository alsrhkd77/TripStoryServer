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
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long postId;

    @Column(nullable = false, name = "POST_AUTHOR")
    private String postAuthor;

    @Column(name = "POST_CONTENT")
    private String postContent;

}
