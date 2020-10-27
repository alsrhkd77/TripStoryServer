package com.tripstory.tripstory.member.dto;

import lombok.Getter;
import lombok.Setter;

public class MemberDTO {

    @Setter
    @Getter
    public static class MemberInfo{
        private String memberId;
        private String memberName;
        private String memberEmail;
        private String memberProfileImagePath;
    }

    @Setter
    @Getter
    public static class Response {
        private String result;
        private String errors;
        private MemberInfo memberInfo;
    }
}
