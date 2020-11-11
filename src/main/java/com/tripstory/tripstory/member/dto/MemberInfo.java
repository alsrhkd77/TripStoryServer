package com.tripstory.tripstory.member.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberInfo {
    private String memberId;
    private String memberName;
    private String memberEmail;
    private String memberProfileImagePath;
    private String memberNickName;
}
