package com.tripstory.tripstory.member;


import com.tripstory.tripstory.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final MemberRepository<Member, String> memberRepository;

    @Override
    public boolean isIdDuplicate(String id) {
        logger.info("중복확인 대상 ID : " + id + " 중복확인");
        return !memberRepository.findById(id)
                .isEmpty();
    }

    @Override
    @Transactional
    public String join(String id, String password, String name, String email) {
        logger.info("가입 정보 [ID : " + id + ", PW : " + password + ", NAME : " + name + ", EMAIL : " + email + "]");
        Member newMember = Member.builder()
                .id(id)
                .name(name)
                .email(email)
                .build();

        newMember.join(password);
        try {
            return memberRepository.save(newMember).getId();
        }catch (Exception e) {
            throw new IllegalStateException("이미 존재하는 회원");
        }
    }

    @Override
    public boolean loginByTS(String id, String password) {
        logger.info("로그인 유형 : TripStory 회원");
        return memberRepository.findById(id)
                .map(Member::getAuth)
                .filter((auth) -> auth.getId().equals(id))
                .filter((auth -> auth.getPassword().equals(password)))
                .isPresent();
    }

    @Override
    public boolean loginBySocial(String id) {
        return false;
    }
}
