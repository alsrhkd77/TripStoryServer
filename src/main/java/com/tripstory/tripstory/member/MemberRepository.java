package com.tripstory.tripstory.member;

import com.tripstory.tripstory.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

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

    /**
     * 이름 키워드를 받아 해당 키워드로 시작하는 이름을 가진 사용자를 모두 검색
     * @param keyword
     * @return 검색된 사용자 리스트
     */
    public List<Member> findByNameKeyword(String keyword) {
        String query = "SELECT m FROM Member m WHERE m.name LIKE :keyword";
        return em.createQuery(query, Member.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getResultList();
    }

    /**
     * 닉네임 키워드를 받아 해당 키워드로 시작하는 닉네임을 가진 사용자를 모두 검색
     * @param keyword
     * @return 검색된 사용자 리스트
     */
    public List<Member> findByNickNameKeyword(String keyword) {
        String query = "SELECT m FROM Member m WHERE m.nickName LIKE :keyword";
        return em.createQuery(query, Member.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getResultList();
    }
}
