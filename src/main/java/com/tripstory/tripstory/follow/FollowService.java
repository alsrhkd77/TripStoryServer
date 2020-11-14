package com.tripstory.tripstory.follow;

import com.tripstory.tripstory.follow.domain.Follow;
import com.tripstory.tripstory.follow.dto.FollowerInfoDTO;
import com.tripstory.tripstory.member.MemberService;
import com.tripstory.tripstory.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberService memberService;

    /**
     * 다른 사용자 팔로우
     *
     * @param memberId
     * @param followingNickName
     */
    @Transactional
    public void follow(String memberId, String followingNickName) {
        // 요청 보낸 사용자 존재 유무 확인
        if (!memberService.isMemberExist(memberId)) {
            throw new IllegalStateException("요청한 사람의 회원 ID가 잘못 되었습니다.");
        }
        // 팔로우 대상 사용자 존재 유무 확인
        if (!memberService.isMemberExistByNickName(followingNickName)) {
            throw new IllegalStateException("해당 닉네임을 가진 회원이 존재하지 않습니다.");
        }
        // 새로운 팔로우 Entity 생성
        Follow newFollow = new Follow(memberService.getMember(memberId), memberService.getMemberByNickName(followingNickName));

        // 팔로잉 저장
        followRepository.save(newFollow);
    }


    /**
     * 팔로우된 사용자 팔로우 취소
     *
     * @param memberId
     * @param followingNickName
     */
    @Transactional
    public void unFollow(String memberId, String followingNickName) {
        try {
            Follow findFollow = followRepository.findOne(memberId, followingNickName);
            followRepository.delete(findFollow);
        } catch (Exception e) {
            throw new IllegalStateException("삭제 대상이 존재하지 않습니다.");
        }
    }

    /**
     * 팔로잉 중인 회원목록 가져오기
     *
     * @param nickName
     * @return
     */
    public List<FollowerInfoDTO> getFollowings(String nickName) {
        try {
            Member findMember = memberService.getMemberByNickName(nickName);
            List<Follow> findFollowers = followRepository.findByMemberId(findMember.getMemberId());
            List<FollowerInfoDTO> infos = findFollowers.stream()
                    .map(Follow::toInfo)
                    .collect(Collectors.toList());
            return infos;
        } catch (Exception e) {
            throw new IllegalStateException("조회 도중 오류가 발생했습니다.");
        }
    }
    
    /**
     * 나를 팔로우 하는 회원목록 가져오기 (팔로워 목록)
     * @param nickName
     * @return 팔로워 정보 객체 리스트
     */
    public List<FollowerInfoDTO> getFollowers(String nickName) {
        try {
            Member findMember = memberService.getMemberByNickName(nickName);
            List<Follow> findFollowers = followRepository.findByFollowingId(findMember.getMemberId());
            List<FollowerInfoDTO> infos = findFollowers.stream()
                    .map(Follow::toInfo)
                    .collect(Collectors.toList());
            return infos;
        } catch (
                Exception e) {
            throw new IllegalStateException("조회 도중 오류가 발생했습니다.");
        }
    }

    /**
     * 자신이 팔로우 하는 사람인지 확인하는 메서드
     * @param memberId
     * @param nickName
     * @return 자신이 팔로우 하는경우 true, 자신이 팔로우 하지 않는 경우 false
     */
    public boolean isMyFollowing(String memberId, String nickName) {
        try {
            followRepository.findOne(memberId, nickName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
