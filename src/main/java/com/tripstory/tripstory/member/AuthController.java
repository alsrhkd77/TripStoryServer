package com.tripstory.tripstory.member;

import com.tripstory.tripstory.member.dto.IdCheckDTO;
import com.tripstory.tripstory.member.dto.JoinDTO;
import com.tripstory.tripstory.member.dto.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final AuthService authService;

    @PostMapping("/check")
    public IdCheckDTO.Response checkIdDuplication(@Validated @RequestBody IdCheckDTO.Request request, BindingResult bindingResult) {
        logger.info("----------------ID 중복확인 요청----------------");
        IdCheckDTO.Response response = new IdCheckDTO.Response();
        if (bindingResult.hasErrors()) {
            response.setErrors(getBindingErrorResponse(bindingResult));
            response.setResult("failed");
            return response;
        }
        if (authService.isIdDuplicate(request.getMemberId())) {
            response.setResult("duplicated");
        } else {
            response.setResult("success");
        }
        return response;
    }

    @PostMapping("/sign-up")
    public JoinDTO.Response joinTripStory(@Validated @RequestBody JoinDTO.Request request, BindingResult bindingResult) {
        logger.info("----------------TripStory 회원가입 요청----------------");
        JoinDTO.Response response = new JoinDTO.Response();
        if(bindingResult.hasErrors()) {
            response.setErrors(getBindingErrorResponse(bindingResult));
            response.setResult("failed");
            return response;
        }
        try {
            String joinedMemberId = authService.join(request.getMemberId(), request.getMemberPw(), request.getMemberName(), request.getMemberEmail());
            response.setResult("success");
            response.setJoinedId(joinedMemberId);
        } catch (Exception e) {
            response.setErrors("이미 존재하는 회원");
            response.setResult("failed");
        }
        return response;
    }



    @PostMapping("/login/ts")
    public LoginDTO.Response loginByTS(@Validated @RequestBody LoginDTO.TsRequest request, BindingResult bindingResult) {
        LoginDTO.Response response = new LoginDTO.Response();
        if (bindingResult.hasErrors()) {
            response.setErrors(getBindingErrorResponse(bindingResult));
            response.setResult("failed");
            return response;
        }
        boolean result = authService.loginByTS(request.getMemberId(), request.getMemberPw());
        if(result) {
            // 로그인 성공
            response.setResult("success");
            response.setMemberId(request.getMemberId());
        } else {
            // 로그인 실패
            response.setResult("failed");
            response.setErrors("ID가 없거나 틀린 Password 입니다.");
        }
        return response;
    }

    /**
     * 바인딩 에러를 하나의 문자열로 만들어 반환
     * @param bindingResult
     * @return
     */
    private String getBindingErrorResponse(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        bindingResult.getAllErrors().forEach(
                error -> {
                    sb.append(error.getDefaultMessage());
                    sb.append("\n");
                });
        return sb.toString();
    }
}
