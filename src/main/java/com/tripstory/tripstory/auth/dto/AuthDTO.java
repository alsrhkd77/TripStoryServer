package com.tripstory.tripstory.auth.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AuthDTO {

    @Setter
    @Getter
    public static class JoinRequest {
        @Size(min = 6, max = 12, message = "ID는 6자 이상 12자 이하만 가능")
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "ID는 영문과 숫자만 가능")
        @NotEmpty(message = "ID는 비어있는 문자열 사용이 불가능")
        @NonNull
        private String memberId;

        @Size(min = 6, max = 18, message = "비밀번호는 6자 이상 18자 이하만 가능")
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "비밀번호는 영문과 숫자만 가능")
        @NotEmpty(message = "비밀번호는 비어있는 문자열 사용이 불가능")
        @NonNull
        private String memberPw;

        @Size(min = 2, max = 4, message = "이름은 2자에서 4자까지 가능")
        @NotEmpty(message = "이름은 비어있는 문자열 사용이 불가능")
        @NonNull
        private String memberName;

        @NotEmpty(message = "이메일은 비어있는 문자열 사용이 불가능")
        @Email
        @NonNull
        private String memberEmail;

        @Size(min = 1, max = 10, message = "닉네임은 1자에서 10자까지 가능")
        @NotEmpty(message = "닉네임은 비어있는 문자열 사용이 불가능")
        @NonNull
        private String memberNickName;
    }

    @Setter
    @Getter
    public static class JoinResponse {
        private String result;
        private String joinedId;
        private String errors;
    }

    @Setter
    @Getter
    public static class TsLoginRequest {
        @NonNull
        @NotEmpty(message = "ID 입력되지 않았습니다")
        private String memberId;
        @NonNull
        @NotEmpty(message = "PASSWORD 입력되지 않았습니다")
        private String memberPw;
    }

    @Setter
    @Getter
    public static class SocialLoginRequest {
        @NonNull
        @NotEmpty(message = "ID 입력되지 않았습니다")
        private String memberId;
        @NonNull
        @NotEmpty(message = "NAME 입력되지 않았습니다")
        private String memberName;
        @NonNull
        @NotEmpty(message = "EMAIL 입력되지 않았습니다")
        private String memberEmail;
    }

    @Setter
    @Getter
    public static class LoginResponse {
        private String result;
        private String errors;
        private String memberId;
    }
}
