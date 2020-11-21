package com.tripstory.tripstory.travel_post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TravelSearchDTO {

    private String result;
    private String errors;
    private int count;
    private List<TravelSearchItem> travels;

}
