package com.tripstory.tripstory.member;

import com.tripstory.tripstory.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;

@RestController
public class TestController {

    @Autowired
    EntityManager em;

    @GetMapping("/{id}/{name}")
    @Transactional
    public String createMember(@PathVariable String id, @PathVariable String name) {
        Member member = Member.builder()
                .id(id)
                .name(name)
                .build();
        em.persist(member);
        return "가입된 회원 : " + member.getName();
    }
}
