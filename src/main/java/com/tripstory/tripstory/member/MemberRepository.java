package com.tripstory.tripstory.member;

import com.tripstory.tripstory.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberRepository {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final EntityManager em;

    public Member save(Member member) {
        logger.info("추가대상 회원 ID : " + member.getId() + " 회원 추가");
        em.persist(member);
        return member;
    }

    public Optional<Member> findOne(String id) {
        logger.info("검색 대상 회원 ID : " + id);
        Optional<Member> result = Optional.ofNullable(em.find(Member.class, id));
        return result;
    }

}