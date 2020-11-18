package com.tripstory.tripstory.comment;

import com.tripstory.tripstory.comment.dto.PostCommentDTO;
import com.tripstory.tripstory.comment.dto.PostCommentDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public PostCommentDTO.AddResponse addComment(@RequestBody PostCommentDTO.AddRequest request) {
        PostCommentDTO.AddResponse response = new PostCommentDTO.AddResponse();
        try {
            response.setCommentId(commentService.addComment(request.getPostId(), request.getMemberId(), request.getContent()));
            response.setResult("success");
        } catch (Exception e) {
            response.setResult("failed");
            response.setErrors(e.getMessage());
        }
        return response;
    }

    @DeleteMapping("/{comment-id}")
    public PostCommentDTO.RemoveResponse removeComment(@PathVariable("comment-id") Long commentId) {
        PostCommentDTO.RemoveResponse response = new PostCommentDTO.RemoveResponse();
        try {
            commentService.removeComment(commentId);
            response.setResult("success");
        } catch (Exception e) {
            response.setResult("failed");
            response.setErrors(e.getMessage());
        }
        return response;
    }

    @GetMapping("/{post-id}")
    public PostCommentDTO.SearchResponse getComments(@PathVariable("post-id") Long postId) {
        PostCommentDTO.SearchResponse response = new PostCommentDTO.SearchResponse();
        try {
            List<PostCommentDetail> comments = commentService.getComments(postId);
            response.setResult("success");
            response.setCount(comments.size());
            response.setCommentDetails(comments);
        } catch (Exception e) {
            response.setResult("success");
            response.setCount(0);
            response.setErrors(e.getMessage());
        }
        return response;
    }
}
