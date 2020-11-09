package com.tripstory.tripstory.post;

import com.tripstory.tripstory.post.domain.NormalPost;
import com.tripstory.tripstory.post.domain.Post;
import com.tripstory.tripstory.post.dto.PostThumbnail;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class PostRepository {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final EntityManager em;

    public Post save(Post post) {
        logger.info("게시물 생성 대상 회원 " + post.getMember().getMemberId());
        em.persist(post);
        return post;
    }

    public void delete(Post post) {
        em.remove(post);
    }

    public void delete(NormalPost post) {
        em.remove(post);
    }

    public Post findOne(Long id) {
        return em.find(Post.class, id);
    }


    public List<PostThumbnail> findByMemberId(String memberId) {
        String query = "SELECT new com.tripstory.tripstory.post.dto.PostThumbnail(p.id, p.content, p.createdTime, MAX(i.path), p.type) " +
                "FROM Post p " +
                "JOIN p.normalPost n " +
                "ON p.type = 'NORMAL' " +
                "JOIN p.images i " +
                "WHERE p.member.memberId = :memberId " +
                "GROUP BY p.id " +
                "ORDER BY p.createdTime DESC ";
        return em.createQuery(query, PostThumbnail.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public int getLikeCount(Long postId) {
        String query = "SELECT COUNT(l) " +
                " FROM PostLike l " +
                "WHERE PostLike.post.id = :postId";
        Integer likeCount = em.createQuery(query, Integer.class)
                .setParameter("postId", postId)
                .getSingleResult();
        return likeCount.intValue();
    }

}
