package com.tripstory.tripstory.tag;

import com.tripstory.tripstory.tag.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class TagRepository {

    private final EntityManager em;

    /**
     * 태그 하나 저장
     * @param tag
     * @return 저장된 태그 ID
     */
    public Long save(Tag tag) {
        em.persist(tag);
        return tag.getId();
    }

    /**
     * 회원 ID와 태그이름으로 저장된 태그 검색
     * @param memberId
     * @param tagName
     * @return 태그가 존재하면 태그가 들어있는 Optional, 없는 경우 비어있는 Optional
     */
    public Optional<Tag> findByMemberIdAndTagName(String memberId, String tagName) {
        try {
            return Optional.ofNullable(em.createQuery("SELECT t FROM Tag t WHERE t.member.memberId = :memberId AND t.name = :tagName", Tag.class)
                    .setParameter("memberId", memberId)
                    .setParameter("tagName", tagName)
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }

    }

    /**
     * 회원 ID로 사용한 태그 전부 검색
     * @param memberId
     * @return 해당 회원이 사용한 Tag 를 List 에 담아서 반환
     */
    public List<Tag> findByMemberId(String memberId) {
        return em.createQuery("SELECT t FROM Tag t WHERE t.member.memberId = :memberId", Tag.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }
}
