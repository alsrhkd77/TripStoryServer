package com.tripstory.tripstory.follow;

import com.tripstory.tripstory.follow.domain.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FollowRepository {

    private final EntityManager em;

    public void save(Follow follow) {
        em.persist(follow);
    }

    public void delete(Follow follow) {
        em.remove(follow);
    }

    /**
     * 회원 ID, 팔로잉대상 ID로 팔로우 정보 단건 검색
     * @param memberId
     * @param nickName
     * @return
     */
    public Follow findOne(String memberId, String nickName) {
        String query = "SELECT f FROM Follow f WHERE f.memberId.memberId = :memberId AND f.followingId.nickName = :nickName";
        return em.createQuery(query, Follow.class)
                .setParameter("memberId", memberId)
                .setParameter("nickName", nickName)
                .getSingleResult();
    }

    /**
     * 회원 ID로 팔로우 테이블 검색
     * 자신이 팔로우 하는 사람들 목록을 가져옴
     * @param memberId
     * @return
     */
    public List<Follow> findByMemberId(String memberId) {
        String query = "SELECT f FROM Follow f WHERE f.memberId.memberId = :memberId";
        return em.createQuery(query, Follow.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    /**
     * 팔로잉 ID로 팔로우 테이블 검색
     * 자신을 팔로우 하는 사람들 목록을 가져옴
     * @param followingId
     * @return
     */
    public List<Follow> findByFollowingId(String followingId) {
        String query = "SELECT f FROM Follow f WHERE f.followingId.memberId = :followingId";
        return em.createQuery(query, Follow.class)
                .setParameter("followingId", followingId)
                .getResultList();
    }
}
