package com.tripstory.tripstory.post.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@IdClass(TravelCourseId.class)
public class TravelCourse {

    @Id
    private Double lat;

    @Id
    private Double lng;

    @Id
    private LocalDateTime passDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id")
    private TravelPost travel;
}
