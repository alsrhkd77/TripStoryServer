package com.tripstory.tripstory.follow.domain;

import com.tripstory.tripstory.follow.dto.FollowerInfoDTO;
import com.tripstory.tripstory.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(FollowId.class)
public class Follow {

    @Id
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member memberId;

    @Id
    @ManyToOne
    @JoinColumn(name = "following_id")
    private Member followingId;

    // 내가 팔로우 하는 사람 정보를 요약하여 반환
    public FollowerInfoDTO toFollowingInfo() {
        FollowerInfoDTO infoDTO = new FollowerInfoDTO();
        infoDTO.setName(this.followingId.getName());
        infoDTO.setNickName(this.followingId.getNickName());
        infoDTO.setProfilePath(this.followingId.getProfileImagePath());
        return infoDTO;
    }

    public FollowerInfoDTO toFollowerInfo() {
        FollowerInfoDTO infoDTO = new FollowerInfoDTO();
        infoDTO.setName(this.memberId.getName());
        infoDTO.setNickName(this.memberId.getNickName());
        infoDTO.setProfilePath(this.memberId.getProfileImagePath());
        return infoDTO;
    }
}
