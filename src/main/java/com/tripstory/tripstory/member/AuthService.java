package com.tripstory.tripstory.member;


import com.tripstory.tripstory.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final MemberRepository memberRepository;

    public boolean isIdDuplicate(String id) {
        logger.info("중복확인 대상 ID : " + id + " 중복확인");
        Member findMember = memberRepository.findOne(id);
        if (findMember != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isNickNameDuplicate(String nickName) {
        logger.info("중복확인 대상 닉네님 : " + nickName + " 중복확인");
        Member findMember = memberRepository.findByNickName(nickName);
        if (findMember != null) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public String join(String id, String password, String name, String email, String nickName) {
        logger.info("가입 정보 [ID : " + id + ", PW : " + password + ", NAME : " + name + ", EMAIL : " + email + ", NICKNAME : " + nickName + "]");
        Member newMember = Member.builder()
                .memberId(id)
                .name(name)
                .email(email)
                .nickName(nickName)
                .build();

        newMember.join(password);
        try {
            memberRepository.save(newMember);
            return newMember.getMemberId();
        }catch (Exception e) {
            throw new IllegalStateException("회원가입중 오류발생");
        }
    }

    public boolean loginByTS(String id, String password) {
        logger.info("로그인 유형 : TripStory 회원");
        return Optional.ofNullable(memberRepository.findOne(id))
                .map(Member::getAuth)
                .filter((auth) -> auth.getId().equals(id))
                .filter((auth -> auth.getPassword().equals(password)))
                .isPresent();
    }

    public boolean loginBySocial(String id) {
        return false;
    }
}
