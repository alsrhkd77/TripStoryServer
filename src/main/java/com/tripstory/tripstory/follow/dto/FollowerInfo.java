package com.tripstory.tripstory.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FollowerInfo {
    private String name;
    private String nickName;
    private String profilePath;
}