package com.tripstory.tripstory.normal_post;

import com.tripstory.tripstory.normal_post.domain.NormalPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NormalPostRepository {

    private final EntityManager em;

    /**
     * 새로운 일반 게시물 저장
     * @param post
     * @return 생성된 일반게시물 엔티티
     */ 
    public NormalPost save(NormalPost post) {
        em.persist(post);
        return post;
    }

    /**
     * 일반 게시물 삭제
     * @param post
     */
    public void delete(NormalPost post) {
        em.remove(post);
    }

    /**
     * 게시물 ID로 일반 게시물 조회
     * @param id
     * @return 존재하면 검색된 게시물, 없으면 null
     */
    public NormalPost findOne(Long id) {
        return em.find(NormalPost.class, id);
    }

    /**
     * 해당 사용자가 작성한 게시물중 여행에 포함되지 않는 게시물을 조회
     * @param memberId
     * @return 여행에 포함되지 않는 일반 게시물 리스트
     */
    public List<Long> findByMemberIdNotInTravel(String memberId) {
        String query = "SELECT n.postId " +
                "FROM NormalPost n " +
                "JOIN n.post p ON p.member.memberId = :memberId " +
                "WHERE n.travel = 'NULL' ";
        return em.createQuery(query, Long.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }
}
