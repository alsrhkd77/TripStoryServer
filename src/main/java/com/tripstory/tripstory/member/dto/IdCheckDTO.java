package com.tripstory.tripstory.member.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class IdCheckDTO {

    @Setter
    @Getter
    public static class Request {
        @NotNull(message = "반드시 입력 해야함")
        @Size(min=6, max=12, message="6자 이상 12자 이하 사용가능")
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "영문과 숫자 조합만 가능")
        @NotEmpty(message = "비어있는 문자열 사용불가능")
        private String memberId;
    }

    @Setter
    @Getter
    public static class Response {
        private String result;
        private String errors;
    }
}
