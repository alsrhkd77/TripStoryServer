package com.tripstory.tripstory.member;

import com.tripstory.tripstory.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberRepositoryImpl implements MemberRepository<Member, String>{

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final EntityManager em;

    @Override
    public String save(Member member) {
        logger.info("추가대상 회원 ID : " + member.getId() + " 회원 추가");
        em.persist(member);
        return member.getId();
    }

    @Override
    public Optional<Member> findById(String memberId) {
        Optional<Member> result;
        try{
            logger.info("조회 대상 ID : " + memberId + " 회원 조회");
            Member findMember = em.createQuery("select m from Member m where m.id = :memberId", Member.class)
                    .setParameter("memberId", memberId)
                    .getSingleResult();
            result = Optional.of(findMember);
        } catch (NoResultException e) {
            logger.info(memberId + "에 해당하는 회원이 없음");
            result = Optional.empty();
        }
        return result;
    }
}
