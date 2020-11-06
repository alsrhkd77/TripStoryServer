package com.tripstory.tripstory.follow;

import com.tripstory.tripstory.follow.domain.Follow;
import com.tripstory.tripstory.follow.dto.FollowDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FollowRepository {

    private final EntityManager em;

    public Long save(Follow follow) {
        em.persist(follow);
        return follow.getId();
    }

    /**
     * 내가 팔로우 하는 회원의 프로필 정보 조회
     * @param memberId
     * @return
     */
    public List<FollowDTO.FollowerInfo> findByMemberId(String memberId) {
        String query = "SELECT new com.tripstory.tripstory.follow.dto.FollowDTO.FollowerInfo(f.member.name, f.member.nickName, f.member.profileImagePath) " +
                "FROM Follow f " +
                "JOIN FETCH f.member " +
                "WHERE f.member.id = :memberId";
        return em.createQuery(query, FollowDTO.FollowerInfo.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    /**
     * 나를 팔로우 하는 회원의 프로필 정보 조회
     * @param nickName
     * @return
     */
    public List<FollowDTO.FollowerInfo> findByNickName(String nickName) {
        String query = "SELECT new com.tripstory.tripstory.follow.dto.FollowDTO.FollowerInfo(f.member.name, f.member.nickName, f.member.profileImagePath) " +
                "FROM Follow f " +
                "JOIN FETCH f.member " +
                "WHERE f.nickname = :nickName";
        em.createQuery(query)
        .setParameter("nickName", nickName);
        return null;
    }
}
