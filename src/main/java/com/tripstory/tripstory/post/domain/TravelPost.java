package com.tripstory.tripstory.post.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TravelPost {

    @Id
    @Column(name = "post_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDate travelStart;

    private LocalDate travelEnd;

    @Builder.Default
    @OneToMany(mappedBy = "travel")
    private List<NormalPost> normalPosts = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "travel",
    cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TravelCourse> courses = new ArrayList<>();
}
