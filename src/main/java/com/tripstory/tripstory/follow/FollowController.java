package com.tripstory.tripstory.follow;

import com.tripstory.tripstory.follow.dto.FollowDTO;
import com.tripstory.tripstory.follow.dto.FollowerInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping
    public ResponseEntity<FollowDTO.Response> follow(@RequestBody FollowDTO.Request request) {
        ResponseEntity<FollowDTO.Response> response;

        response = new ResponseEntity<>(new FollowDTO.Response(), HttpStatus.OK);
        try {
            followService.follow(request.getMemberId(), request.getNickName());
            response.getBody().setResult("success");
            // response = new ResponseEntity<String>(HttpStatus.OK);
        } catch (Exception e) {
            response.getBody().setResult("failed");
            response.getBody().setErrors(e.getMessage());
            // response = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @DeleteMapping("/{member-id}/{nickName}")
    public ResponseEntity<FollowDTO.Response> unFollow(@PathVariable("member-id") String memberID, @PathVariable String nickName) {
        ResponseEntity<FollowDTO.Response> response;

        response = new ResponseEntity<>(new FollowDTO.Response(), HttpStatus.OK);
        try {
            followService.unFollow(memberID, nickName);
            response.getBody().setResult("success");
        } catch (Exception e) {
            response.getBody().setResult("failed");
            response.getBody().setErrors(e.getMessage());
            // response = new ResponseEntity<FollowDTO.Response>(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    // 내가 팔로잉 중인 대상 목록 가져오기
    @GetMapping("/following/{nickName}")
    public ResponseEntity<FollowDTO.SearchResponse> getFollowings(@PathVariable String nickName) {
        ResponseEntity<FollowDTO.SearchResponse> response;

        response = new ResponseEntity<>(new FollowDTO.SearchResponse(), HttpStatus.OK);
        try {
            List<FollowerInfoDTO> followings = followService.getFollowings(nickName);
            response.getBody().setResult("success");
            response.getBody().setFollowerInfos(followings);
        }  catch (Exception e) {
            response.getBody().setResult(e.getMessage());
        }
        return response;
    }

    // 나를 팔로잉 중인 대상 목록 가져오기
    @GetMapping("/follower/{nickName}")
    public ResponseEntity<FollowDTO.SearchResponse> getFollowers(@PathVariable String nickName) {
        ResponseEntity<FollowDTO.SearchResponse> response;

        response = new ResponseEntity<>(new FollowDTO.SearchResponse(), HttpStatus.OK);
        try {
            List<FollowerInfoDTO> followings = followService.getFollowers(nickName);
            response.getBody().setResult("success");
            response.getBody().setFollowerInfos(followings);
        }  catch (Exception e) {
            response.getBody().setResult(e.getMessage());
        }
        return response;
    }
}
