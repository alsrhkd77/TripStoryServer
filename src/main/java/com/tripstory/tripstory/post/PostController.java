package com.tripstory.tripstory.post;

import com.tripstory.tripstory.post.dto.PostCreateDTO;
import com.tripstory.tripstory.post.dto.PostSearchDTO;
import com.tripstory.tripstory.post.dto.PostThumbnail;
import com.tripstory.tripstory.util.ErrorCatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping()
    public PostCreateDTO.Response createPost(PostCreateDTO.Request request, BindingResult bindingResult) {
        PostCreateDTO.Response response = new PostCreateDTO.Response();
        if (bindingResult.hasErrors()) {
            response.setErrors(ErrorCatcher.getBindingError(bindingResult));
            response.setResult("failed");
        }
        try {
            response.setPostId(postService.createPost(request));
            response.setResult("success");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            response.setErrors(e.getMessage());
            response.setResult("failed");
        }
        return response;
    }

    @GetMapping("/{post-id}/{member-id}")
    public void test(@PathVariable("post-id") String postId, @PathVariable("member-id") String memberId) {

    }

    @GetMapping("/{member-id}")
    public PostSearchDTO.MyPostDTO getMyPostAll(@PathVariable("member-id") String memberId) {
        PostSearchDTO.MyPostDTO response = new PostSearchDTO.MyPostDTO();
        try {
            List<PostThumbnail> findPosts = postService.getMyPostThumbnailAll(memberId);

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

    @GetMapping("/no-travel/{memberId}")
    public void getPostNotIncludedInTheTravel(@PathVariable String memberId) {

    }
}
