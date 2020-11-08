package com.tripstory.tripstory.follow;

import com.tripstory.tripstory.follow.domain.Follow;
import com.tripstory.tripstory.follow.dto.FollowDTO;
import com.tripstory.tripstory.follow.dto.FollowerInfo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FollowRepository {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final EntityManager em;

    /**
     * 새로운 팔로잉 추가
     *
     * @param follow
     * @return
     */
    public Long save(Follow follow) {
        logger.info("새로운 팔로잉 저장");
        em.persist(follow);
        return follow.getId();
    }

    /**
     * 기존 팔로잉 취소
     *
     * @param follow
     */
    public void delete(Follow follow) {
        logger.info("기존 팔로잉 제거");
        em.remove(follow);
    }

    /**
     * @param memberId 본인 계정 ID
     * @param nickName 팔로잉 닉네임
     */
    public Follow findByMemberIdWithNickName(String memberId, String nickName) {
        String query = "SELECT f " +
                "FROM Follow f " +
                "WHERE f.member.id =: memberId " +
                "AND f.nickName =: nickName ";
        return em.createQuery(query, Follow.class)
                .setParameter("memberId", memberId)
                .setParameter("nickName", nickName)
                .getSingleResult();
    }

    /**
     * 내가 팔로우 하는 회원의 프로필 정보 조회
     *
     * @param memberId
     * @return
     */
    public List<String> findByMemberId(String memberId) {
        String query = "SELECT f.nickName " +
                "FROM Follow f " +
                "WHERE f.member.id = :memberId";
        return em.createQuery(query, String.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    /**
     * 나를 팔로우 하는 회원의 프로필 정보 조회
     *
     * @param nickName
     * @return
     */
    public List<String> findByNickName(String nickName) {
        String query = "SELECT f.nickName " +
                "FROM Follow f " +
                "WHERE f.nickName = :nickName";
        return em.createQuery(query, String.class)
                .setParameter("nickName", nickName)
                .getResultList();
    }
}
