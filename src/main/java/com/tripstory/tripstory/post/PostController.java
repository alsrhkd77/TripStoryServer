package com.tripstory.tripstory.post;

import com.tripstory.tripstory.post.dto.PostCreateDTO;
import com.tripstory.tripstory.util.ErrorCatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public PostCreateDTO.Response createPost(PostCreateDTO.Request request, BindingResult bindingResult) {
        PostCreateDTO.Response response = new PostCreateDTO.Response();
        if(bindingResult.hasErrors()) {
            response.setErrors(ErrorCatcher.getBindingError(bindingResult));
            response.setResult("failed");
        }
        postService.createPost(request);
        return null;
    }
}
