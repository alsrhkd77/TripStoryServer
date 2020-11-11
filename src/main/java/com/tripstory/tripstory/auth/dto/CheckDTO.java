package com.tripstory.tripstory.auth.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CheckDTO {

    @Getter
    @Setter
    public static class IdCheckDTO {
        @NotNull(message = "반드시 입력 해야함")
        @Size(min=6, max=12, message="6자 이상 12자 이하 사용가능")
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "영문과 숫자 조합만 가능")
        @NotEmpty(message = "비어있는 문자열 사용불가능")
        private String memberId;
    }

    @Getter
    @Setter
    public static class NickNameDTO {
        @NotNull(message = "반드시 입력 해야함")
        @Size(min=1, max=10, message="1자 이상 10자 이하 사용가능")
        @NotEmpty(message = "비어있는 문자열 사용불가능")
        private String memberNickName;
    }

    @Getter
    @Setter
    public static class Response {
        private String result;
        private String errors;
    }
}
