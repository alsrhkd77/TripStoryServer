package com.tripstory.tripstory.member.domain;

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
public class Member {

    @Id
    @Column(name = "member_id")
    private String id;

    private String name;

    private String email;

    private String profileImagePath;

    @OneToOne(mappedBy = "member",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Auth auth;

    /**
     * 인증 테이블에 값을 넣기 위한 엔티티 작업
     * 생성된 member 엔티티를 바탕으로 auth 엔티티 객체를 생성하여
     * 연관관계를 이어줌
     * @param password
     */
    public void join(String password) {
        this.auth = new Auth(id, this, password);
    }
}
