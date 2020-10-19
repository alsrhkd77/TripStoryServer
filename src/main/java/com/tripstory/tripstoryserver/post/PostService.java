package com.tripstory.tripstoryserver.post;

import com.tripstory.tripstoryserver.post.dto.PostDto;
import com.tripstory.tripstoryserver.post.entity.Post;
import com.tripstory.tripstoryserver.post.entity.PostImages;
import com.tripstory.tripstoryserver.post.dto.PostResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    PostImageRepository postImageRepository;

    @Autowired
    PostRepository postRepository;

    public String createPost(PostDto postDto) {
        System.out.println("-------------------- POST --------------------");
        System.out.println("작성자 : " + postDto.getPostAuthor());
        System.out.println("내용 : " + postDto.getPostContent());
        Post post = Post.builder()
                .postAuthor(postDto.getPostAuthor())
                .postContent(postDto.getPostContent())
                .build();

        postRepository.save(post);
        System.out.println(post.getPostId());
        System.out.println("==========tags==========");
        postDto.getPostTags().forEach(System.out::println);

        System.out.println("=========images=========");
        postDto.getPostImages().forEach(
                image -> {
                    try{
                        String imagePath = fileStorageService.saveImage(image);
                        PostImages postImage = PostImages.builder()
                                .postImgPath(imagePath)
                                .post(post)
                                .build();
                        postImageRepository.save(postImage);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
        );
        System.out.println("-------------------- POST --------------------");

        return "success";
    }

    public PostResponseDto getPost(Long postId) {
        Optional<Post> findPost = postRepository.findById(postId);
        Post post = findPost.get();
        List<PostImages> findPostImages = postImageRepository.findByPostPostId(postId);
        List<String> postImagePaths = new ArrayList<>();
        findPostImages.forEach(postImages -> postImagePaths.add(postImages.getPostImgPath()));
        PostResponseDto postResponseDto = PostResponseDto.builder()
                .postId(postId)
                .postAuthor(post.getPostAuthor())
                .postContent(post.getPostContent())
                .postImagePaths(postImagePaths)
                .build();
        return postResponseDto;
    }
}
