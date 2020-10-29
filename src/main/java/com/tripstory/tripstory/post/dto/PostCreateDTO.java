package com.tripstory.tripstory.post.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PostCreateDTO {

    @Setter
    @Getter
    public static class Request {
        private String author;
        private Optional<String> content;
        private List<String> tags;
        private List<MultipartFile> images;

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private Optional<LocalDate> visitStart;

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private Optional<LocalDate> visitEnd;

    }

    @Setter
    @Getter
    public static class Response {
        private String result;
        private String errors;
        private Long postId;
    }
}
