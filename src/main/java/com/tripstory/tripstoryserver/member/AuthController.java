package com.tripstory.tripstoryserver.member;


import com.tripstory.tripstoryserver.member.dto.LoginResultDto;
import com.tripstory.tripstoryserver.member.dto.TsLoginDto;
import com.tripstory.tripstoryserver.member.dto.SignUpDto;
import com.tripstory.tripstoryserver.member.dto.SocialLoginDto;
import com.tripstory.tripstoryserver.member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/check")
    public String checkIdDuplication(@RequestBody String memberId) {
        System.out.println("===========id check===========");
        return authService.checkIdDuplication(memberId);
    }

    @PostMapping(
            value = "/sign-up",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String createAccount(@RequestBody SignUpDto signUpDto) {
        System.out.println("===========sign up===========");
        return authService.createTsMember(signUpDto);
    }

    @PostMapping(
            value = "/login/ts",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public LoginResultDto loginByTs(@RequestBody TsLoginDto tsLoginDto) {
        System.out.println("===========ts login===========");
        Member findMember = authService.loginByTsAccount(tsLoginDto);
        if (findMember != null) {
            return LoginResultDto.builder()
                    .loginResult("success")
                    .memberId(findMember.getMemberId())
                    .memberEmail(findMember.getMemberEmail())
                    .memberName(findMember.getMemberName())
                    .memberProfileImgPath(findMember.getMemberProfileImgPath() != null ? findMember.getMemberProfileImgPath() : "None")
                    .build();
        }
        return LoginResultDto.builder().loginResult("failed").build();
    }

    @PostMapping(
            value = "/login/social",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String loginBySocial(@RequestBody SocialLoginDto socialLoginDto) {
        System.out.println("===========social login===========");
        return authService.loginBySocialAccount(socialLoginDto);
    }
}
