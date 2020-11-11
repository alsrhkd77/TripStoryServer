package com.tripstory.tripstory.normal_post;

import com.tripstory.tripstory.normal_post.domain.NormalPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

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
     * 게시물 ID로 일반 게시물 조회
     * @param id
     * @return 존재하면 검색된 게시물, 없으면 null
     */
    public NormalPost findOne(Long id) {
        return em.find(NormalPost.class, id);
    }
}
