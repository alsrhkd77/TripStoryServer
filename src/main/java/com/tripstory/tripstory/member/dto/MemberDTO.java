package com.tripstory.tripstory.member.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class MemberDTO {

    @Getter
    @Setter
    public static class MemberInfoResponse {
        private String result;
        private String errors;
        private MemberInfo memberInfo;
    }

    @Setter
    @Getter
    public static class NickNameChangeRequest {
        private String memberId;
        private String memberNickName;
    }

    @Setter
    @Getter
    public static class NickNameChangeResponse {
        private String result;
        private String errors;
        private String changedName;
    }

    @Setter
    @Getter
    public static class ProfileImageChangeRequest {
        private String memberId;
        private MultipartFile image;
    }

    @Setter
    @Getter
    public static class ProfileImageChangeResponse {
        private String result;
        private String errors;
        private String profileImagePath;
    }
}
