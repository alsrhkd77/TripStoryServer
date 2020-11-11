package com.tripstory.tripstory.travel_post.domain;

import com.tripstory.tripstory.normal_post.domain.NormalPost;
import com.tripstory.tripstory.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TravelPost {

    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDate travelStart;

    private LocalDate travelEnd;

    @Builder.Default
    @OneToMany(mappedBy = "travel", cascade = CascadeType.PERSIST)
    private List<NormalPost> normalPosts = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "travel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TravelCourse> travelCourses = new ArrayList<>();

    public void addNormalTravel(NormalPost post) {
        this.normalPosts.add(post);
        post.changeTravelPost(this);
    }

    public void addTravelCourse(TravelCourse course) {
        this.travelCourses.add(course);
        course.changeTravel(this);
    }
}
