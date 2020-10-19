package com.tripstory.tripstoryserver.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDto {
    private String memberId;
    private String memberPw;
    private String memberName;
    private String memberEmail;
}
