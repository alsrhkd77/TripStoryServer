package com.tripstory.tripstory.member.dto;

import com.tripstory.tripstory.member.domain.Member;
import lombok.*;

import javax.validation.constraints.NotEmpty;

public class LoginDTO {

    /**
     * TS 회원 로그인 요청 DTO
     */
    @Getter
    @Setter
    public static class TsRequest {
        @NonNull
        @NotEmpty(message = "ID 입력되지 않았습니다")
        private String memberId;
        @NonNull
        @NotEmpty(message = "PASSWORD 입력되지 않았습니다")
        private String memberPw;
    }

    /**
     * 소셜 로그인 요청 DTO
     */
    public static class SocialRequest {
        
    }

    /**
     * 로그인 응답 DTO
     */
    @Setter
    @Getter
    public static class Response {
        private String result;
        private String memberId;
        private String errors;
    }
}
