package com.tripstory.tripstory.timeline.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeLineDTO {

    private String result;
    private String errors;
    private int currentItemCount;
    private int totalRequestItemCount;
    private List<TimeLineItem> items;
}
