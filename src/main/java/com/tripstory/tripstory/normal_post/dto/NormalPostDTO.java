package com.tripstory.tripstory.normal_post.dto;

import com.tripstory.tripstory.post.dto.PostThumbnail;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public class NormalPostDTO {

    @Setter
    @Getter
    public static class Create {
        private String author;
        private String content;
        private List<String> tags;
        private List<MultipartFile> images;
        private String scope;
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate visitStart;

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate visitEnd;
    }

    @Setter
    @Getter
    public static class CreateResponse{
        private String result;
        private String errors;
        private Long postId;
    }

    @Setter
    @Getter
    public static class ThumbnailResponse {
        private String result;
        private String errors;
        private int postCount;
        private List<PostThumbnail> postThumbnails;
    }

    @Setter
    @Getter
    public static class DeleteResponse {
        private String result;
        private String errors;
    }

    @Setter
    @Getter
    public static class NormalPostNotInTravelResponse {
        private String result;
        private String errors;
        private List<Long> posts;
    }

}




