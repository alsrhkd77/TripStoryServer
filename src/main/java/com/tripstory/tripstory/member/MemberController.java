package com.tripstory.tripstory.member;

import com.tripstory.tripstory.member.domain.Member;
import com.tripstory.tripstory.member.dto.MemberDTO;
import com.tripstory.tripstory.member.dto.MemberProfile;
import com.tripstory.tripstory.member.dto.MemberSearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/profile/image")
    public MemberDTO.ProfileImageChangeResponse changeProfileImage(MemberDTO.ProfileImageChangeRequest request) {
        MemberDTO.ProfileImageChangeResponse response = new MemberDTO.ProfileImageChangeResponse();
        try {
            String changedProfileImagePath = memberService.changeProfileImage(request.getMemberId(), request.getImage());
            response.setResult("success");
            response.setProfileImagePath(changedProfileImagePath);
        } catch (Exception e) {
            response.setResult("failed");
            response.setErrors(e.getMessage());
        }
        return response;
    }

    @GetMapping("/search/name/{keyword}")
    public MemberSearchDTO searchByNameKeyword(@PathVariable String keyword) {
        MemberSearchDTO response = new MemberSearchDTO();
        try {
            List<MemberSearchDTO.MemberSearchInfo> members = memberService.searchMemberByName(keyword);
            response.setMembers(members);
            response.setCount(members.size());
            response.setResult("success");

        } catch (Exception e) {
            response.setResult("failed");
            response.setErrors(e.getMessage());
        }
        return response;
    }

    @GetMapping("/search/nickname/{keyword}")
    public MemberSearchDTO searchByNickNameKeyword(@PathVariable String keyword) {
        MemberSearchDTO response = new MemberSearchDTO();
        try {
            List<MemberSearchDTO.MemberSearchInfo> members = memberService.searchMemberByNickName(keyword);
            response.setMembers(members);
            response.setCount(members.size());
            response.setResult("success");

        } catch (Exception e) {
            response.setResult("failed");
            response.setErrors(e.getMessage());
        }
        return response;
    }
}
