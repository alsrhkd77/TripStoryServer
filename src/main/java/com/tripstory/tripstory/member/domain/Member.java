package com.tripstory.tripstory.member.domain;

import com.tripstory.tripstory.comment.dto.AuthorProfile;
import com.tripstory.tripstory.member.dto.MemberInfo;
import com.tripstory.tripstory.member.dto.MemberProfile;
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

    @Column(name = "profile_image_path", columnDefinition = "TEXT")
    @Builder.Default
    private String profileImagePath = "https://storage.googleapis.com/tripstory_image/profile_image/default_profile.png";

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

    /**
     * 회원 프로필 형태로 변경하여 반환
     * @return MemberProfile 객체
     */
    public MemberProfile toMemberProfile() {
        return MemberProfile.builder()
                .nickName(nickName)
                .name(name)
                .profileImagePath(profileImagePath)
                .build();
    }

    public void changeNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 회원 댓글 작성자 형태로 변경하여 반환
     * @return AuthorProfile 객체
     */
    public AuthorProfile toAuthorProfile() {
        return AuthorProfile.builder()
                .author(nickName)
                .profileImagePath(profileImagePath)
                .build();
    }
}
