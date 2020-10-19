package com.tripstory.tripstoryserver.member.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class MemberAuth {

    @Id
    @Column(name = "LOGIN_ID")
    private String loginId;

    @Column(name = "LOGIN_PW")
    private String loginPw;

    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}
