package com.tripstory.tripstory.post.domain;

import com.tripstory.tripstory.member.domain.Member;
import com.tripstory.tripstory.tag.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private LocalDateTime createdTime;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private PostType type;

    @Builder.Default
    @OneToMany(mappedBy = "post",
            cascade = CascadeType.ALL)
    private List<PostTag> tags = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "post",
            cascade = CascadeType.ALL)
    private List<PostImage> images = new ArrayList<>();

    public void setMember(Member member) {
        this.member = member;
    }

    public void addTag(PostTag tag) {
        this.tags.add(tag);
    }

    public void addImage(PostImage image) {
        this.images.add(image);
    }
}
