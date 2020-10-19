package com.tripstory.tripstoryserver.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResultDto {
    private String loginResult;
    private String memberId;
    private String memberName;
    private String memberEmail;
    private String memberProfileImgPath;
}
