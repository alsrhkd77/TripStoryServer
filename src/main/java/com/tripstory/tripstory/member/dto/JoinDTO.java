package com.tripstory.tripstory.member.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class JoinDTO {

    @Setter
    @Getter
    public static class Request {
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
    }

    @Setter
    @Getter
    public static class Response {
        private String result;
        private String joinedId;
        private String errors;
    }
}
