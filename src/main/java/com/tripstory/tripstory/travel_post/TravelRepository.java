package com.tripstory.tripstory.travel_post;

import com.tripstory.tripstory.post.domain.Post;
import com.tripstory.tripstory.travel_post.domain.TravelPost;
import com.tripstory.tripstory.travel_post.dto.TravelSearchDTO;
import com.tripstory.tripstory.travel_post.dto.TravelSearchItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

    /**
     * 중심좌표 기준으로 특정 범위의 여행 경로를 가지는 게시물 검색
     * @param lat
     * @param lng
     * @param level
     * @return
     */
    public List<TravelSearchItem> findByLocation(Double lat, Double lng, Double level) {
        String query = "SELECT new com.tripstory.tripstory.travel_post.dto.TravelSearchItem(p.id, c.lat, c.lng) " +
                "FROM TravelPost p " +
                "JOIN p.travelCourses c " +
                "WHERE c.lat BETWEEN :latL AND :latR " +
                "AND c.lng BETWEEN  :lngL AND :lngR ";
        return em.createQuery(query, TravelSearchItem.class)
                .setParameter("latL", lat - level)
                .setParameter("latR", lat + level)
                .setParameter("lngL", lng - level)
                .setParameter("lngR", lng + level)
                .getResultList();
    }
}
