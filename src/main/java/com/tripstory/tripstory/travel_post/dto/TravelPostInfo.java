package com.tripstory.tripstory.travel_post.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelPostInfo {

    private LocalDate travelStart;
    private LocalDate travelEnd;
    private List<TravelCourseInfo> courses;
    private List<Long> posts;
}
