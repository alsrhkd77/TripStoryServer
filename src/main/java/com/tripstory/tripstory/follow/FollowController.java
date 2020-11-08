package com.tripstory.tripstory.follow;

import com.tripstory.tripstory.follow.dto.FollowDTO;
import com.tripstory.tripstory.follow.dto.FollowerInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping
    public FollowDTO.FollowResponse follow(@RequestBody FollowDTO.Follow request) {
        FollowDTO.FollowResponse response = new FollowDTO.FollowResponse();
        try {
            followService.follow(request.getMemberId(), request.getNickName());
            response.setResult("success");
        } catch (Exception e) {
            response.setErrors(e.getMessage());
            response.setResult("failed");
        }
        return response;
    }

    @DeleteMapping
    public FollowDTO.FollowResponse unFollow(@RequestBody FollowDTO.Follow request) {
        FollowDTO.FollowResponse response = new FollowDTO.FollowResponse();
        try {
            followService.unFollow(request.getMemberId(), request.getNickName());
            response.setResult("success");
        } catch (Exception e){
            response.setErrors(e.getMessage());
            response.setResult("failed");
        }
        return response;
    }

    @GetMapping("/following/{member-id}")
    public FollowDTO.FollowerResponse getMyFollowing(@PathVariable("member-id") String memberId) {
        FollowDTO.FollowerResponse response = new FollowDTO.FollowerResponse();
        try {
            List<FollowerInfo> myFollowings = followService.getMyFollowings(memberId);
            response.setResult("success");
            response.setFollowerInfos(myFollowings);
        } catch (Exception e) {
            response.setResult(e.getMessage());
            response.setResult("failed");
        }
        return response;
    }

    @GetMapping("/follower/{member-id}")
    public FollowDTO.FollowerResponse getMyFollower(@PathVariable("member-id") String memberId) {
        FollowDTO.FollowerResponse response = new FollowDTO.FollowerResponse();
        try {
            List<FollowerInfo> myFollowings = followService.getMyFollowers(memberId);
            response.setResult("success");
            response.setFollowerInfos(myFollowings);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResult(e.getMessage());
            response.setResult("failed");
        }
        return response;
    }
}
