package com.tripstory.tripstory.comment.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PostCommentDTO {

    @Getter
    @Setter
    public static class SearchResponse {
        private String result;
        private String errors;
        private int count;
        private List<PostCommentDetail> commentDetails;
    }

    @Getter
    @Setter
    public static class AddRequest {
        private Long postId;
        private String memberId;
        private String content;
    }

    @Setter
    @Getter
    public static class AddResponse {
        private String result;
        private String errors;
        private Long commentId;
    }

    @Getter
    @Setter
    public static class RemoveResponse {
        private String result;
        private String errors;
    }

}
