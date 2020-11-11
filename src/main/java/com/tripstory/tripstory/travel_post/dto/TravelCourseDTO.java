package com.tripstory.tripstory.travel_post.dto;

import com.tripstory.tripstory.travel_post.domain.TravelCourse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class TravelCourseDTO {

    private Double lat;
    private Double lng;
    private LocalDateTime passDate;

    public TravelCourse toTravelCourse() {
        return TravelCourse.builder()
                .lat(lat)
                .lng(lng)
                .passDate(passDate)
                .build();
    }
}
