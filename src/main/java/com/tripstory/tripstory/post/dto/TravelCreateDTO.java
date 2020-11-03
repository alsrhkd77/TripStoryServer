package com.tripstory.tripstory.post.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TravelCreateDTO {

    @Setter
    @Getter
    public static class Request {
        private String author;
        private String content;
        private List<String> tags;
        private List<MultipartFile> images;
        private List<String> courses;
        private List<Long> posts;

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate travelStart;

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate travelEnd;

    }


    @Setter
    @Getter
    public static class Response {
        private String result;
        private String errors;
        private Long postId;
    }
}
