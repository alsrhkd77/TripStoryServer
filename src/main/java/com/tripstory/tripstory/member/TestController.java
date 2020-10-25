package com.tripstory.tripstory.member;

import com.tripstory.tripstory.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/form")
    public String memberCreateForm() {
        return "member/signUpForm";
    }

    @GetMapping("/test")
    @ResponseBody
    public String createMember(@RequestParam String id,
                               @RequestParam String name,
                               @RequestParam String email) {
        Member member = Member.builder()
                .id(id)
                .name(name)
                .email(email)
                .build();
        return "가입된 회원 : " + testService.save(member);
    }
}
