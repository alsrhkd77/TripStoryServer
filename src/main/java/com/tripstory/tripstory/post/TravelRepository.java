package com.tripstory.tripstory.post;

import com.tripstory.tripstory.post.domain.Post;
import com.tripstory.tripstory.post.domain.TravelPost;
import com.tripstory.tripstory.post.dto.PostThumbnail;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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


    public List<PostThumbnail> findByMemberId(String memberId) {
        String query = "SELECT new com.tripstory.tripstory.post.dto.PostThumbnail(p.id, p.content, p.createdTime, MAX(i.path), p.type) " +
                "FROM Post p " +
                "JOIN p.travelPost t " +
                "ON p.type = 'TRAVEL' " +
                "JOIN p.images i " +
                "WHERE p.member.id = :memberId " +
                "GROUP BY p.id " +
                "ORDER BY p.createdTime DESC ";
        return em.createQuery(query, PostThumbnail.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }
}
