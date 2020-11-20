package com.tripstory.tripstory.post;

import com.tripstory.tripstory.post.dto.PostSearchDTO;
import com.tripstory.tripstory.post.dto.PostSearchItem;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/search/tag/{keyword}")
    public PostSearchDTO searchByUsedTag(@PathVariable String keyword) {
        PostSearchDTO response = new PostSearchDTO();
        try {
            List<PostSearchItem> posts = postService.searchByUsedTag(keyword);
            response.setResult("success");
            response.setPosts(posts);
            response.setCount(posts.size());
        } catch (Exception e) {
            response.setResult("failed");
            response.setErrors(e.getMessage());
        }
        return response;
    }
}
