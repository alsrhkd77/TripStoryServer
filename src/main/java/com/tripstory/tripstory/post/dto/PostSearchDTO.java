package com.tripstory.tripstory.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PostSearchDTO {
    private String result;
    private String errors;
    private int count;
    private List<PostSearchItem> posts;
}
