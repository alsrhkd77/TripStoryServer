package com.tripstory.tripstory.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Member {
    @Id
    @Column(name = "MEMBER_ID")
    private String id;
    
    @Column(name = "MEMBER_NAME")
    private String name;

    @Column(name = "MEMBER_EMAIL")
    private String email;

    @Column(name = "MEMBER_PROFILE_IMAGE_PATH")
    private String profileImagePath;

}
