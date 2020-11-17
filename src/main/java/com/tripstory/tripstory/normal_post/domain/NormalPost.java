package com.tripstory.tripstory.normal_post.domain;

import com.tripstory.tripstory.normal_post.dto.NormalPostInfo;
import com.tripstory.tripstory.post.domain.Post;
import com.tripstory.tripstory.travel_post.domain.TravelPost;
import com.tripstory.tripstory.travel_post.dto.NestedPostInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class NormalPost {

    @Id
    private Long postId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDate visitStart;

    private LocalDate visitEnd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id")
    private TravelPost travel;

    public void changeTravelPost(TravelPost post) {
        this.travel = post;
    }

    public NormalPostInfo toInfo() {
        return NormalPostInfo.builder()
                .visitStart(visitStart)
                .visitEnd(visitEnd)
                .linkedTravel(travel != null ? travel.getId() : null)
                .build();
    }

    public NestedPostInfo toNestedPostInfo() {
        return NestedPostInfo.builder()
                .postId(postId)
                .likes(post.getPostLikes().size())
                .thumbnailImagePath(post.getPostImages().get(0).getImagePath())
                .comments(post.getPostComments().size())
                .build();
    }
}
