package com.tripstory.tripstory.normal_post;

import com.tripstory.tripstory.normal_post.dto.NormalPostDTO;
import com.tripstory.tripstory.normal_post.dto.NormalPostDetailDTO;
import com.tripstory.tripstory.post.dto.PostThumbnail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class NormalPostController {

    private final NormalPostService normalPostService;

    @PostMapping
    public ResponseEntity<NormalPostDTO.CreateResponse> createNormalPost(NormalPostDTO.Create request) {
        ResponseEntity<NormalPostDTO.CreateResponse> response = new ResponseEntity<>(new NormalPostDTO.CreateResponse(), HttpStatus.OK);
        try {
            Long newNormalPostId = normalPostService.createNormalPost(request);
            response.getBody().setPostId(newNormalPostId);
            response.getBody().setResult("success");
        } catch (Exception e) {
            response.getBody().setResult("failed");
            response.getBody().setErrors(e.getMessage());
        }
        return response;
    }

    @GetMapping("/{member-id}")
    public ResponseEntity<NormalPostDTO.ThumbnailResponse> getMyNormalPostAll(@PathVariable("member-id") String memberId) {
        ResponseEntity<NormalPostDTO.ThumbnailResponse> response = new ResponseEntity<>(new NormalPostDTO.ThumbnailResponse(), HttpStatus.OK);
        try {
            List<PostThumbnail> thumbnails = normalPostService.getMyNormalPostThumbnailAll(memberId);
            response.getBody().setResult("success");
            response.getBody().setPostCount(thumbnails.size());
            response.getBody().setPostThumbnails(thumbnails);
        } catch (Exception e) {
            response.getBody().setResult("failed");
            response.getBody().setErrors(e.getMessage());
        }
        return response;
    }

    @GetMapping("/{post-id}/{member-id}")
    public NormalPostDetailDTO getNormalPostDetail(@PathVariable("post-id") Long postId, @PathVariable("member-id") String memberId) {
        NormalPostDetailDTO normalPostDetail = new NormalPostDetailDTO();
        try {
            normalPostDetail = normalPostService.getNormalPostDetail(postId, memberId);
        } catch (Exception e) {
            normalPostDetail.setResult("failed");
            normalPostDetail.setErrors(e.getMessage());
        }
        return normalPostDetail;
    }

    @GetMapping("/other/{nickName}/{member-id}")
    public ResponseEntity<NormalPostDTO.ThumbnailResponse> getOtherNormalPostAll(@PathVariable String nickName, @PathVariable("member-id") String memberId) {
        ResponseEntity<NormalPostDTO.ThumbnailResponse> response = new ResponseEntity<>(new NormalPostDTO.ThumbnailResponse(), HttpStatus.OK);
        try {
            List<PostThumbnail> thumbnails = normalPostService.getOtherNormalPostThumbnailAll(nickName, memberId);
            response.getBody().setResult("success");
            response.getBody().setPostCount(thumbnails.size());
            response.getBody().setPostThumbnails(thumbnails);
        } catch (Exception e) {
            response.getBody().setResult("failed");
            response.getBody().setErrors(e.getMessage());
        }
        return response;
    }
}
