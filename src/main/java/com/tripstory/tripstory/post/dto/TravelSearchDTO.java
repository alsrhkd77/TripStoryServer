package com.tripstory.tripstory.post.dto;

import lombok.*;

import java.util.List;

public class TravelSearchDTO {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class MyTravelsDTO {
        private String result;
        private String errors;
        private int travelCount;
        private List<PostThumbnail> travelThumbnails;
    }
}
