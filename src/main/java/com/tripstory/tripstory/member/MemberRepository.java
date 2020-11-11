package com.tripstory.tripstory.member;

import com.tripstory.tripstory.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    /**
     * 새로운 회원 저장
     * @param member
     */
    public void save(Member member) {
        em.persist(member);
    }

    /**
     * 회원 ID로 해당 회원 검색
     * @param id
     * @return 회원 존재 시 해당 회원 Entity, 없는 경우 null
     */
    public Member findOne(String id) {
        return em.find(Member.class, id);
    }

    /**
     * 닉네임으로 해당 회원 검색
     * @throws NoResultException 검색 결과가 없는 경우
     * @param nickName
     * @return 회원 존재 시 해당 회원 Entity, 없는 경우 오류 발생
     */
    public Member findByNickName(String nickName) {
        String query = "SELECT m FROM Member m WHERE m.nickName = :nickName";
        return em.createQuery(query, Member.class)
                .setParameter("nickName", nickName)
                .getSingleResult();
    }
}
