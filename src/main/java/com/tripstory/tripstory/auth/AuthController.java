package com.tripstory.tripstory.auth;

import com.tripstory.tripstory.auth.dto.AuthDTO;
import com.tripstory.tripstory.auth.dto.CheckDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/check/id")
    public ResponseEntity<CheckDTO.Response> idDuplicationCheck(@RequestBody @Validated CheckDTO.IdCheckDTO idCheckDTO, BindingResult result) {
        ResponseEntity<CheckDTO.Response> response = new ResponseEntity<>(new CheckDTO.Response(), HttpStatus.OK);
        if (result.hasErrors()) {
            response.getBody().setResult("failed");
            StringBuilder sb = new StringBuilder();
            result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .forEach(message -> sb.append(message));
            response.getBody().setErrors(sb.toString());
            return response;
        }
        if (!authService.isIdDuplicate(idCheckDTO.getMemberId())) {
            response.getBody().setResult("success");
        } else {
            response.getBody().setResult("duplicate");
        }
        return response;
    }

    @PostMapping("/check/nickname")
    public ResponseEntity<CheckDTO.Response> nickNameDuplicationCheck(@RequestBody @Validated CheckDTO.NickNameDTO memberNickName, BindingResult result) {
        ResponseEntity<CheckDTO.Response> response = new ResponseEntity<>(new CheckDTO.Response(), HttpStatus.OK);
        if (result.hasErrors()) {
            response.getBody().setResult("failed");
            StringBuilder sb = new StringBuilder();
            result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .forEach(message -> sb.append(message));
            response.getBody().setErrors(sb.toString());
            return response;
        }
        if (!authService.isNickNameDuplicate(memberNickName.getMemberNickName())) {
            response.getBody().setResult("success");
        } else {
            response.getBody().setResult("duplicate");
        }
        return response;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthDTO.JoinResponse> join(@RequestBody @Validated AuthDTO.JoinRequest request, BindingResult result) {
        ResponseEntity<AuthDTO.JoinResponse> response = new ResponseEntity<>(new AuthDTO.JoinResponse(), HttpStatus.OK);
        if (result.hasErrors()) {
            response.getBody().setResult("failed");
            StringBuilder sb = new StringBuilder();
            result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .forEach(message -> sb.append(message));
            response.getBody().setErrors(sb.toString());
            return response;
        }
        try {
            String joinedId = authService.join(request.getMemberId(), request.getMemberPw(), request.getMemberName(), request.getMemberEmail(), request.getMemberNickName());
            response.getBody().setResult("success");
            response.getBody().setJoinedId(joinedId);
        } catch (Exception e) {
            response.getBody().setResult("failed");
            response.getBody().setErrors(e.getMessage());
        }
        return response;
    }

    @PostMapping("/login/ts")
    public ResponseEntity<AuthDTO.LoginResponse> loginByTS(@RequestBody @Validated AuthDTO.TsLoginRequest request, BindingResult result) {
        ResponseEntity<AuthDTO.LoginResponse> response = new ResponseEntity<>(new AuthDTO.LoginResponse(), HttpStatus.OK);
        if (result.hasErrors()) {
            response.getBody().setResult("failed");
            StringBuilder sb = new StringBuilder();
            result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .forEach(message -> sb.append(message));
            response.getBody().setErrors(sb.toString());
            return response;
        }
        try {
            String loginId = authService.loginByTS(request.getMemberId(), request.getMemberPw());
            response.getBody().setResult("success");
            response.getBody().setMemberId(loginId);
        } catch (Exception e) {
            response.getBody().setResult("failed");
            response.getBody().setErrors(e.getMessage());
        }
        return response;
    }
}
