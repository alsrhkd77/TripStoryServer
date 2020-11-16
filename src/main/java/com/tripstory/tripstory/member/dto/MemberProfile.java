package com.tripstory.tripstory.member.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberProfile {

    private String result;
    private String errors;
    private String nickName;
    private String name;
    private int followings;
    private int followers;
    private String profileImagePath;

}
