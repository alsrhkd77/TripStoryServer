package com.tripstory.tripstory.travel_post;

import com.tripstory.tripstory.travel_post.domain.TravelPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class TravelRepository {

    private final EntityManager em;

    public TravelPost save(TravelPost post) {
        em.persist(post);
        return post;
    }

    public TravelPost findOne(Long postId) {
        return em.find(TravelPost.class, postId);
    }
}
