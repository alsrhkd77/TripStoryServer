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


    @Getter
    @Setter
    public static class FollowResponse {
        private String result;
        private String errors;
    }

    @Setter
    @Getter
    public static class FollowerResponse {
        private String result;
        private String errors;
        private List<FollowerInfo> followerInfos;
    }
}
