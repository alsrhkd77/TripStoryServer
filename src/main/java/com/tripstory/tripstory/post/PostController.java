package com.tripstory.tripstory.post;

import com.tripstory.tripstory.post.dto.*;
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
    public PostDetailDTO test(@PathVariable("post-id") Long postId, @PathVariable("member-id") String memberId) {
        postService.getPostDetail(postId, memberId);
        return null;
    }

    @GetMapping("/{member-id}")
    public PostSearchDTO.MyPostsDTO getMyPostAll(@PathVariable("member-id") String memberId) {
        PostSearchDTO.MyPostsDTO response = new PostSearchDTO.MyPostsDTO();
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

    @DeleteMapping("/{post-id}/{member-id}")
    public PostDeleteDTO deletePost(@PathVariable("post-id") Long postId, @PathVariable("member-id") String memberId) {
        PostDeleteDTO response = new PostDeleteDTO();
        try {
            postService.deletePost(postId, memberId);
            response.setResult("success");
        } catch (Exception e) {
            response.setResult("failed");
            response.setErrors(e.getMessage());
        }
        return response;
    }
}
