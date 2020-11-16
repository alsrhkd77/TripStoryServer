package com.tripstory.tripstory.travel_post.domain;

import com.tripstory.tripstory.travel_post.dto.TravelCourseInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TravelCourse {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_course_id")
    private Long id;

    private Double lat;

    private Double lng;

    private LocalDateTime passDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_id")
    private TravelPost travel;

    public void changeTravel(TravelPost travel) {
        this.travel = travel;
    }

    public TravelCourseInfo toCourseInfo() {
        return TravelCourseInfo.builder()
                .lat(lat)
                .lng(lng)
                .passDate(passDate)
                .build();
    }
}
