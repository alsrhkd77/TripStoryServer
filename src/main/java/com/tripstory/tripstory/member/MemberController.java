package com.tripstory.tripstory.member;

import com.tripstory.tripstory.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{id}")
    public MemberDTO.MemberInfoResponse getMemberInfo(@PathVariable String id) {
        MemberDTO.MemberInfoResponse memberInfoResponse = new MemberDTO.MemberInfoResponse();
        try {
            memberInfoResponse.setMemberInfo(memberService.getMemberInfo(id));
            memberInfoResponse.setResult("success");
        } catch (Exception e) {
            memberInfoResponse.setResult("failed");
            memberInfoResponse.setErrors(e.getMessage());
        }
        return memberInfoResponse;
    }

    @PutMapping("/nickname")
    public MemberDTO.ChangeNickNameResponse updateMemberNickName(MemberDTO.ChangeNickNameRequest request) {
        MemberDTO.ChangeNickNameResponse response = new MemberDTO.ChangeNickNameResponse();
        try {
            memberService.changeMemberNickName(request.getMemberId(), request.getMemberNickName());
            response.setChangedName(request.getMemberNickName());
            response.setResult("success");
        } catch (Exception e) {
            response.setErrors(e.getMessage());
            response.setResult("failed");
        }
        return response;
    }

    @GetMapping("/profile/image/{nickname}")
    public void getProfileImagePath(@PathVariable String nickname) {

    }

    @GetMapping("/profile/{nickname}")
    public void getProfileInfo(@PathVariable String nickname) {

    }

}
