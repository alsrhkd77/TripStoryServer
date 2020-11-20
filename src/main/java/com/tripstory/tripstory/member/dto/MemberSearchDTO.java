package com.tripstory.tripstory.member.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class MemberSearchDTO {
    private String result;
    private String errors;
    private int count;
    private List<MemberSearchInfo> members;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MemberSearchInfo {
        private String name;
        private String nickName;
        private String profileImagePath;
    }
}
