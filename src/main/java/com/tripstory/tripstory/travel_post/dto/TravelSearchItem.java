package com.tripstory.tripstory.travel_post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TravelSearchItem {
    private Long postId;
    private Double lat;
    private Double lng;
}
