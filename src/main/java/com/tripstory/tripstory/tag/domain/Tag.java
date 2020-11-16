package com.tripstory.tripstory.tag.domain;

import com.tripstory.tripstory.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Column(name = "tag_name", length = 30)
    private String tagName;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
