package com.tripstory.tripstory.member;

import com.tripstory.tripstory.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/{id}/{name}")
    public String createMember(@PathVariable String id, @PathVariable String name) {
        Member member = Member.builder()
                .id(id)
                .name(name)
                .build();
        return "가입된 회원 : " + testService.save(member);
    }
}
