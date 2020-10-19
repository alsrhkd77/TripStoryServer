package com.tripstory.tripstoryserver.member.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TsLoginDto {

    private String memberId;
    private String memberPw;
}
