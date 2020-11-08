package com.tripstory.tripstory.follow;

import com.tripstory.tripstory.follow.domain.Follow;
import com.tripstory.tripstory.follow.dto.FollowDTO;
import com.tripstory.tripstory.member.MemberRepository;
import com.tripstory.tripstory.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final FollowRepository followRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public void follow(String memberId, String nickName) {
        logger.info(memberId + " 님이 " + nickName + " 님을 팔로우 요청합니다");
        Optional<Member> me = memberRepository.findOne(memberId);
        Optional<Member> newFollowing = memberRepository.findByNickName(nickName);
        if(me.isEmpty() || newFollowing.isEmpty() ) {
            throw new IllegalStateException("유효하지 않은 회원 아이디 또는 회원 닉네임");
        }
        Member meMember = me.get();
        Member newFollowingMember = newFollowing.get();

        Follow follow = Follow.builder()
                .member(meMember)
                .nickName(newFollowingMember.getNickName())
                .build();

        followRepository.save(follow);
    }

    @Transactional
    public void unFollow(String memberId, String nickName) {
        logger.info(memberId + " 님이 " + nickName + " 님을 언팔로우 요청합니다.");
        Follow findFollow = followRepository.findByMemberIdWithNickName(memberId, nickName);
        followRepository.delete(findFollow);
    }

    public List<FollowDTO.FollowerInfo> getMyFollowings(String memberId) {
        return followRepository.findByMemberId(memberId);
    }

    public List<FollowDTO.FollowerInfo> getMyFollowers(String nickName) {
        return followRepository.findByNickName(nickName);
    }
}
