package com.tripstory.tripstoryserver.post;


import com.tripstory.tripstoryserver.post.dto.PostDto;
import com.tripstory.tripstoryserver.post.dto.PostResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("*")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/post/{postId}")
    public PostResponseDto getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    @PostMapping("/post")
    public String createPost(@RequestParam String postAuthor,
                             @RequestParam String postContent,
                             @RequestParam List<MultipartFile> postImages,
                             @RequestParam List<String> postTags) {
        PostDto postDto = PostDto.builder()
                .postAuthor(postAuthor)
                .postContent(postContent)
                .postImages(postImages)
                .postTags(postTags)
                .build();
        return postService.createPost(postDto);
    }

}
