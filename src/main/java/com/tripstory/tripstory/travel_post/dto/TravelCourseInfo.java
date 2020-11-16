package com.tripstory.tripstory.travel_post.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelCourseInfo {
    private Double lat;
    private Double lng;
    private LocalDateTime passDate;
}
