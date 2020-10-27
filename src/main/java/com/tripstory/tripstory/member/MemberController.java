package com.tripstory.tripstory.member;

import com.tripstory.tripstory.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/{id}")
    public MemberDTO.Response getMemberInfo(@PathVariable String id) {
        MemberDTO.Response response = new MemberDTO.Response();
        try {
            response.setMemberInfo(memberService.getMemberInfo(id));
            response.setResult("success");
        } catch (Exception e) {
            response.setResult("failed");
            response.setErrors(e.getMessage());
        }
        return response;
    }

}
