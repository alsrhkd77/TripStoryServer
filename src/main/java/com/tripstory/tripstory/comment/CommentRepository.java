package com.tripstory.tripstory.comment;

import com.tripstory.tripstory.comment.domain.PostComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    /**
     * 새로운 댓글 저장
     * @param comment
     */
    public void save(PostComment comment) {
        em.persist(comment);
    }

    /**
     * 기존 댓글 삭제
     * @param comment
     */
    public void delete(PostComment comment) {
        em.remove(comment);
    }

    /**
     * 게시물에 작성된 모든 댓글을 가져옴
     * @param postId
     * @return PostComment 리스트
     */
    public List<PostComment> findByPostId(Long postId) {
        String query = "SELECT c FROM PostComment c WHERE c.post.postId = :postId";
        return em.createQuery(query, PostComment.class)
                .setParameter("postId", postId)
                .getResultList();
    }
}
