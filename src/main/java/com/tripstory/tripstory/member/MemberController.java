package com.tripstory.tripstory.member;

import com.tripstory.tripstory.member.domain.Member;
import com.tripstory.tripstory.member.dto.MemberDTO;
import com.tripstory.tripstory.member.dto.MemberProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{member-id}")
    public ResponseEntity<MemberDTO.MemberInfoResponse> getMyProfileInfo(@PathVariable("member-id") String memberId) {
        ResponseEntity<MemberDTO.MemberInfoResponse> response = new ResponseEntity<>(new MemberDTO.MemberInfoResponse(), HttpStatus.OK);
        Member findMember = memberService.getMember(memberId);
        if (findMember == null) {
            response.getBody().setResult("failed");
            response.getBody().setErrors("존재하지 않는 회원");
            return response;
        }
        response.getBody().setMemberInfo(findMember.toMemberInfo());
        response.getBody().setResult("success");
        return response;
    }

    @PutMapping("/nickname")
    public ResponseEntity<MemberDTO.NickNameChangeResponse> changeNickName(@RequestBody MemberDTO.NickNameChangeRequest request) {
        ResponseEntity<MemberDTO.NickNameChangeResponse> response = new ResponseEntity<>(new MemberDTO.NickNameChangeResponse(), HttpStatus.OK);
        try {
            String changedNickName = memberService.changeNickName(request.getMemberId(), request.getMemberNickName());
            response.getBody().setResult("success");
            response.getBody().setChangedName(changedNickName);
        } catch (Exception e) {
            response.getBody().setResult("failed");
            response.getBody().setErrors(e.getMessage());
        }
        return response;
    }

    @GetMapping("/profile/{nickname}/{member-id}")
    public MemberProfile getMemberProfile(@PathVariable String nickname, @PathVariable("member-id") String memberId) {
        return memberService.getMemberProfile(nickname, memberId);
    }
}
