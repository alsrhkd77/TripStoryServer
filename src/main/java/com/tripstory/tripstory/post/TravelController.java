package com.tripstory.tripstory.post;

import com.tripstory.tripstory.post.dto.PostCreateDTO;
import com.tripstory.tripstory.post.dto.PostSearchDTO;
import com.tripstory.tripstory.post.dto.PostThumbnail;
import com.tripstory.tripstory.post.dto.TravelCreateDTO;
import com.tripstory.tripstory.util.ErrorCatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/travel")
public class TravelController {


    private final TravelService travelService;

    @PostMapping
    public PostCreateDTO.Response createPost(TravelCreateDTO.Request request, BindingResult bindingResult) {
        System.out.println(request.getCourses());
        PostCreateDTO.Response response = new PostCreateDTO.Response();
        if (bindingResult.hasErrors()) {
            response.setErrors(ErrorCatcher.getBindingError(bindingResult));
            response.setResult("failed");
        }
        try {
            response.setPostId(travelService.createTravel(request));
            response.setResult("success");
        } catch (Exception e) {
            e.printStackTrace();
            response.setErrors(e.getMessage());
            response.setResult("failed");
        }
        return response;
    }

    @GetMapping("/{member-id}")
    public PostSearchDTO.MyPostsDTO getMyTravelAll(@PathVariable("member-id") String memberId) {
        PostSearchDTO.MyPostsDTO response = new PostSearchDTO.MyPostsDTO();
        try {
            List<PostThumbnail> findPosts = travelService.getMyTravelThumbnailAll(memberId);
            response.setResult("success");
            response.setPostThumbnails(findPosts);
            response.setPostCount(findPosts.size());
        } catch (Exception e) {
            response.setResult("failed");
            response.setErrors(e.getMessage());
            response.setPostCount(0);
        }
        return response;
    }
}
