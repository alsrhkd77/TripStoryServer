package com.tripstory.tripstory.member;

import com.tripstory.tripstory.member.domain.Member;
import com.tripstory.tripstory.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberDTO.MemberInfo getMemberInfo(String id) {
        Member findMember = memberRepository.findOne(id)
                .orElseThrow(() -> new IllegalStateException("존재 하지 않는 회원"));
        return findMember.getMemberInfo();
    }

    public void updateProfileImage(byte[] image, String fileName, String contentType) {

    }

    public void changeMemberNickName(String memberId, String newNickName){
        memberRepository.findOne(memberId)
                .ifPresent(member -> member.changeNickName(newNickName));
    }
}
