package com.tripstory.tripstory.member;


import com.tripstory.tripstory.member.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional
public class TestService {

    @PersistenceContext
    private EntityManager em;

    public String save(Member member){
        em.persist(member);
        return member.getName();
    }
}
