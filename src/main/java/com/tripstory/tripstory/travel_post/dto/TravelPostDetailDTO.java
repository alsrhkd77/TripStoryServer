package com.tripstory.tripstory.travel_post.dto;


import com.tripstory.tripstory.post.dto.PostDetail;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelPostDetailDTO {
    private String result;
    private String errors;
    private PostDetail postDetail;
    private TravelPostInfo travelPostInfo;
}
