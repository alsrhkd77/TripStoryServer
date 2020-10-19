package com.tripstory.tripstoryserver.member;


import com.tripstory.tripstoryserver.member.dto.TsLoginDto;
import com.tripstory.tripstoryserver.member.dto.SignUpDto;
import com.tripstory.tripstoryserver.member.dto.SocialLoginDto;
import com.tripstory.tripstoryserver.member.entity.Member;
import com.tripstory.tripstoryserver.member.entity.MemberAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AuthRepository authRepository;

    public String checkIdDuplication(String memberId) {
        System.out.println(memberId);
        if(memberRepository.findByMemberId(memberId) == null) {
            return "success";
        }else {
            return "duplicate";
        }
    }

    public String createTsMember(SignUpDto signUpDto) {
        System.out.println("------------ 회원 생성 ------------");
        Member member = Member.builder()
                .memberId(signUpDto.getMemberId())
                .memberName(signUpDto.getMemberName())
                .memberEmail(signUpDto.getMemberEmail())
                .build();
        memberRepository.save(member);

        MemberAuth memberAuth = MemberAuth.builder()
                .member(member)
                .loginId(signUpDto.getMemberId())
                .loginPw(signUpDto.getMemberPw())
                .build();

        authRepository.save(memberAuth);
        return "success";
    }

    public Member loginByTsAccount(TsLoginDto tsLoginDto) {
        MemberAuth findMemberAuth = authRepository.findByLoginId(tsLoginDto.getMemberId());

        if((findMemberAuth != null) && (findMemberAuth.getLoginPw().equals(tsLoginDto.getMemberPw()))) {
            return memberRepository.findByMemberId(tsLoginDto.getMemberId());
        }
        return null;
    }

    public String loginBySocialAccount(SocialLoginDto socialLoginDto) {
        System.out.println(socialLoginDto.getMemberId());
        System.out.println(socialLoginDto.getMemberName());
        System.out.println(socialLoginDto.getMemberEmail());
        return "success";
    }
}
