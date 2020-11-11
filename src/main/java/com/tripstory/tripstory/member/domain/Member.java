package com.tripstory.tripstory.member.domain;

import com.tripstory.tripstory.member.dto.MemberInfo;
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
    @Column(name = "member_id", length = 255)
    private String memberId;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "email", length = 40)
    private String email;

    @Column(name = "profile_image_path", length = 200)
    @Builder.Default
    private String profileImagePath = "https://i.stack.imgur.com/l60Hf.png";

    @Column(name = "nick_name", unique = true, length = 30)
    private String nickName;

    /**
     * 회원 정보 형태로 변경하여 반환
     * @return MemberInfo 객체
     */
    public MemberInfo toMemberInfo() {
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setMemberId(this.getMemberId());
        memberInfo.setMemberName(this.getName());
        memberInfo.setMemberEmail(this.getEmail());
        memberInfo.setMemberNickName(this.getNickName());
        memberInfo.setMemberProfileImagePath(this.getProfileImagePath());
        return memberInfo;
    }

    public void changeNickName(String nickName) {
        this.nickName = nickName;
    }
}
