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
    public static class MemberInfoResponse {
        private String result;
        private String errors;
        private MemberInfo memberInfo;
    }

    @Setter
    @Getter
    public static class ChangeNickNameRequest {
        private String memberId;
        private String memberNickName;
    }

    @Setter
    @Getter
    public static class ChangeNickNameResponse {
        private String result;
        private String errors;
        private String changedName;
    }
}
