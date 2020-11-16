package com.tripstory.tripstory.normal_post.dto;

import lombok.*;

import java.time.LocalDate;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NormalPostInfo {

    private LocalDate visitStart;
    private LocalDate visitEnd;
    private Long linkedTravel;
}
