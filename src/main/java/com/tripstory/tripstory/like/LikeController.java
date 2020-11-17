package com.tripstory.tripstory.like;

import com.tripstory.tripstory.like.dto.LikeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public LikeDTO.LikeResponse like(@RequestBody LikeDTO.LikeRequest request) {
        LikeDTO.LikeResponse response = new LikeDTO.LikeResponse();
        try {
            if (likeService.isLiked(request.getPostId(), request.getMemberId())) {
                likeService.unLike(request.getPostId(), request.getMemberId());
                response.setResult("unLiked");
            } else {
                likeService.like(request.getPostId(), request.getMemberId());
                response.setResult("liked");
            }
        } catch (Exception e) {
            response.setResult("failed");
            response.setErrors(e.getMessage());
        }
        return response;
    }
}
