package com.tripstory.tripstoryserver.member.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialLoginDto {
    private String memberId;
    private String memberName;
    private String memberEmail;
}
