package com.tripstory.tripstory.timeline;

import com.tripstory.tripstory.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TimeLineRepository {

    private final EntityManager em;

//    public List<Post> findAll(int offset, int limit) {
//        String query = "SELECT p FROM Post p JOIN PostLike l GROUP BY p.postId";
//        return em.createQuery(query, Post.class)
//                .setFirstResult(offset)
//                .setMaxResults(limit)
//                .getResultList();
//    }
}
