package com.tripstory.tripstory.tag;

import com.tripstory.tripstory.tag.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TagRepository {

    private final EntityManager em;

    public void save(Tag tag) {
        em.persist(tag);
    }

    public void delete(Tag tag) {
        em.remove(tag);
    }

    /**
     * 회원 ID와 태그명으로 검색하여 존재하는 태그 하나 반환
     * @param memberId
     * @param tagName
     * @return
     */
    public Tag findByMemberIdWithTagName(String memberId, String tagName) {
        String query = "SELECT t FROM Tag t WHERE t.member.memberId = :memberId AND t.tagName = :tagName " +
                "";
        return em.createQuery(query, Tag.class)
                .setParameter("memberId", memberId)
                .setParameter("tagName", tagName)
                .getSingleResult();
    }

    /**
     * 회원 ID로 해당 회원이 사용한 모든 태그 조회
     * @param memberId
     * @return
     */
    public List<Tag> findNyMemberId(String memberId) {
        String query = "SELECT t FROM Tag t WHERE t.member.memberId = :memberId";
        return em.createQuery(query, Tag.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }
}
