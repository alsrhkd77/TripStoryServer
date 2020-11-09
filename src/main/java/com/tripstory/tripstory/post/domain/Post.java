package com.tripstory.tripstory.post.domain;

import com.tripstory.tripstory.member.domain.Member;
import com.tripstory.tripstory.tag.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLock;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@OptimisticLocking(type = OptimisticLockType.VERSION)
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
    @Enumerated(EnumType.STRING)
    private DisclosureScope scope = DisclosureScope.PUBLIC;

    @Builder.Default
    @OneToMany(mappedBy = "post",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostTag> tags = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "post",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImage> images = new ArrayList<>();

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private NormalPost normalPost;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private TravelPost travelPost;



    public void setMember(Member member) {
        this.member = member;
    }

    public void setNormalPost(NormalPost normalPost) {
        this.normalPost = normalPost;
    }

    public void addTag(PostTag tag) {
        this.tags.add(tag);
    }

    public void addImage(PostImage image) {
        this.images.add(image);
    }

    public void changeScope(String scope) {
        DisclosureScope changedScope = DisclosureScope.valueOf(scope);
        this.scope = changedScope;
    }

}
