package com.tripstory.tripstory.like;

import com.tripstory.tripstory.like.domain.PostLike;
import com.tripstory.tripstory.like.domain.idclass.PostLikeId;
import com.tripstory.tripstory.member.domain.Member;
import com.tripstory.tripstory.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class LikeRepository {

    private final EntityManager em;

    /**
     * 게시물, 회원 엔티티로 새로운 좋아요 추가
     * @param post
     * @param member
     */
    public void save(Post post, Member member) {
        em.persist(PostLike.builder()
                .postId(post)
                .memberId(member)
                .build());
    }

    /**
     * 좋아요 엔티티를 DB 에서 삭제 (좋아요 취소)
     * @param postLike
     */
    public void delete(PostLike postLike) {
        em.remove(postLike);
    }

    public PostLike findOne(PostLikeId postLikeId) {
        return em.find(PostLike.class, postLikeId);
    }
}
