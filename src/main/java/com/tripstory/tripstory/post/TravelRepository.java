package com.tripstory.tripstory.post;

import com.tripstory.tripstory.post.domain.Post;
import com.tripstory.tripstory.post.domain.TravelPost;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class TravelRepository {

    private final EntityManager em;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    
    public TravelPost save(TravelPost travel) {
        logger.info("여행 게시물 생성");
        em.persist(travel);
        return travel;
    }


}
