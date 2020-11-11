package com.tripstory.tripstory.auth;

import com.tripstory.tripstory.auth.domain.Auth;
import com.tripstory.tripstory.member.MemberService;
import com.tripstory.tripstory.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {


    private final AuthRepository authRepository;
    private final MemberService memberService;

    /**
     * 해당 ID 사용중인 회원 여부 확인
     *
     * @param memberId
     * @return 사용중이면 true, 미사용이면 false
     */
    public boolean isIdDuplicate(String memberId) {
        return authRepository.findOne(memberId) != null;
    }

    /**
     * 해당 닉네임을 사용중인 회원 여부 확인
     *
     * @param nickName
     * @return 사용중이면 true, 미사용이면 false
     */
    public boolean isNickNameDuplicate(String nickName) {
        try {
            memberService.getMemberByNickName(nickName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Trip Story 회원가입
     *
     * @param id
     * @param password
     * @param name
     * @param email
     * @param nickName
     * @return 가입된 회원 ID
     */
    @Transactional
    public String join(String id, String password, String name, String email, String nickName) {
        Member newMember = Member.builder()
                .memberId(id)
                .name(name)
                .email(email)
                .nickName(nickName)
                .build();

        Auth newAuth = Auth.builder()
                .member(newMember)
                .password(password)
                .build();

        memberService.addNewMember(newMember);
        authRepository.save(newAuth);
        return newMember.getMemberId();
    }

    /**
     * Trip Story 회원 로그인
     * @return 성공 시 로그인된 ID, 실패시 Exception
     */
    public String loginByTS(String memberId, String memberPw) {
        Auth findAuth = authRepository.findOne(memberId);
        if (findAuth == null) {
            throw new IllegalStateException("존재하지 않는 계정입니다.");
        }
        if (findAuth.getMemberId().equals(memberId) && findAuth.getPassword().equals(memberPw)) {
            return findAuth.getMemberId();
        }
        else {
            throw new IllegalStateException("ID 또는 Password 오류입니다.ㅂ");
        }
    }
}
