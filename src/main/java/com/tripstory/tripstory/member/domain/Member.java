package com.tripstory.tripstory.member.domain;

import com.tripstory.tripstory.member.dto.MemberDTO;
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

    private String nickName;

    @OneToOne(mappedBy = "member",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Auth auth;

    /**
     * 인증 테이블에 값을 넣기 위한 엔티티 작업
     * 생성된 member 엔티티를 바탕으로 auth 엔티티 객체를 생성하여
     * 연관관계를 이어줌
     *
     * @param password
     */
    public void join(String password) {
        this.auth = new Auth(id, this, password);
    }


    /**
     * 회원 정보 반환을 위한 메소드
     *
     * @return 해당 회원의 필수 정보만 객체로 만들어 반환
     */
    public MemberDTO.MemberInfo getMemberInfo() {
        if (this.id == null) {
            return null;
        }
        MemberDTO.MemberInfo memberInfo = new MemberDTO.MemberInfo();
        memberInfo.setMemberId(this.id);
        memberInfo.setMemberName(this.name);
        memberInfo.setMemberEmail(this.email);
        memberInfo.setMemberProfileImagePath(this.profileImagePath);
        return memberInfo;
    }

    /**
     * 새로운 닉네임을 입력 받아 회원의 닉네임을 변경하는 메소드
     * @param nickName
     */
    public void changeNickName(String nickName) {
        this.nickName = nickName;
    }
}
