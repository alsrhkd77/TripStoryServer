package com.tripstory.tripstory.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class FollowDTO {

    @Getter
    public static class Follow {
        private String memberId;
        private String nickName;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class FollowerInfo {
        private String name;
        private String nickName;
        private String profilePath;
    }
    @Getter
    @Setter
    public static class FollowResponse {
        private String result;
        private String errors;
    }

    public static class FollowerResponse {
        private String result;
        private String errors;
        private List<FollowerInfo> followerInfos;
    }
}
