package com.tripstory.tripstory.like.dto;

import lombok.Getter;
import lombok.Setter;

public class LikeDTO {

    @Getter
    @Setter
    public static class LikeRequest {
        private Long postId;
        private String memberId;
    }

    @Getter
    @Setter
    public static class LikeResponse {
        private String result;
        private String errors;
    }

}
