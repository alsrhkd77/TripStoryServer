package com.tripstory.tripstory.travel_post;

import com.tripstory.tripstory.post.dto.PostThumbnail;
import com.tripstory.tripstory.travel_post.dto.TravelPostDTO;
import com.tripstory.tripstory.travel_post.dto.TravelPostDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/travel")
public class TravelController {

    private final TravelService travelService;

    @PostMapping
    public ResponseEntity<TravelPostDTO.CreateResponse> createTravelPost(TravelPostDTO.Create request) {
        ResponseEntity<TravelPostDTO.CreateResponse> response = new ResponseEntity<>(new TravelPostDTO.CreateResponse(), HttpStatus.OK);
        try {
            Long newNormalPostId = travelService.createTravelPost(request);
            response.getBody().setTravelId(newNormalPostId);
            response.getBody().setResult("success");
        } catch (Exception e) {
            response.getBody().setResult("failed");
            response.getBody().setErrors(e.getMessage());
        }
        return response;
    }

    @GetMapping("/{member-id}")
    public ResponseEntity<TravelPostDTO.ThumbnailResponse> getMyTravelPostAll(@PathVariable("member-id") String memberId) {
        ResponseEntity<TravelPostDTO.ThumbnailResponse> response = new ResponseEntity<>(new TravelPostDTO.ThumbnailResponse(), HttpStatus.OK);
        try {
            List<PostThumbnail> thumbnails = travelService.getMyNormalTravelThumbnailAll(memberId);
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
    public TravelPostDetailDTO getTravelPostDetail(@PathVariable("post-id") Long postId, @PathVariable("member-id") String memberId) {
        TravelPostDetailDTO travelPostDetail = new TravelPostDetailDTO();
        try {
            travelPostDetail = travelService.getTravelPostDetail(postId, memberId);
        } catch (Exception e) {
            travelPostDetail.setResult("failed");
            travelPostDetail.setErrors(e.getMessage());
        }
        return travelPostDetail;
    }
}
