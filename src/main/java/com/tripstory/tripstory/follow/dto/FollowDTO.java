package com.tripstory.tripstory.follow.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class FollowDTO {

    @Getter
    @Setter
    // 팔로우 요청 JSON 값
    public static class Request {
        private String memberId; // 새로운 팔로잉을 요청하는 고객 ID
        private String nickName; // 고객이 팔로잉을 희망하는 다른 고객 닉네임
    }

    @Getter
    @Setter
    public static class Response {
        private String result;
        private String errors;
    }

    @Setter
    @Getter
    public static class SearchResponse {
        private String result;
        private String errors;
        private List<FollowerInfoDTO> followerInfos;
    }
}
