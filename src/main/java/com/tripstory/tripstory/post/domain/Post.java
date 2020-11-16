package com.tripstory.tripstory.post.domain;

import com.tripstory.tripstory.like.domain.PostLike;
import com.tripstory.tripstory.member.domain.Member;
import com.tripstory.tripstory.post.domain.enums.DisclosureScope;
import com.tripstory.tripstory.post.domain.enums.PostType;
import com.tripstory.tripstory.post.dto.PostDetail;
import com.tripstory.tripstory.post.dto.PostThumbnail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Builder.Default
    private LocalDateTime createdTime = LocalDateTime.now();

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private PostType type;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private DisclosureScope scope = DisclosureScope.PUBLIC;

    @Builder.Default
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "post_id")
    private List<PostImage> postImages = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_id")
    private List<PostLike> postLikes = new ArrayList<>();

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_id")
    private List<PostTag> postTags = new ArrayList<>();


    public void addImage(PostImage postImage) {
        this.postImages.add(postImage);
        postImage.changePost(this);
    }

    public void addTag(PostTag postTag) {
        this.postTags.add(postTag);
        postTag.changePost(this);
    }

    public PostThumbnail toThumbnail() {
        return PostThumbnail.builder()
                .postId(postId)
                .content(content)
                .createTime(createdTime)
                .thumbnailPath(postImages.get(0).getPath())
                .type(type)
                .build();
    }

    public PostDetail toPostDetail() {
        return PostDetail.builder()
                .author(member.getNickName())
                .content(content)
                .createdTime(createdTime)
                .imagePaths(postImages.stream()
                        .map(PostImage::getImagePath)
                        .collect(Collectors.toList()))
                .likes(postLikes.size())
                .tags(postTags.stream()
                        .map(PostTag::getTagName)
                        .collect(Collectors.toList()))
                .scope(scope)
                .build();
    }
}
