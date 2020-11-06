package com.tripstory.tripstory.follow;

import com.tripstory.tripstory.follow.dto.FollowDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping
    public FollowDTO.FollowResponse follow(@RequestBody FollowDTO.Follow request) {
        FollowDTO.FollowResponse response = new FollowDTO.FollowResponse();
        try {
            followService.follow(request.getMemberId(), request.getMemberNickName());
            response.setResult("success");
        } catch (Exception e) {
            response.setErrors(e.getMessage());
            response.setResult("failed");
        }
        return response;
    }

    @DeleteMapping
    public void unFollow() {

    }

    @GetMapping("/following/{member-id}")
    public void getMyFollowing(@PathVariable("member-id") String memberId) {

    }

    @GetMapping("/follower/{member-id}")
    public void getMyFollower(@PathVariable("member-id") String memberId) {

    }
}
