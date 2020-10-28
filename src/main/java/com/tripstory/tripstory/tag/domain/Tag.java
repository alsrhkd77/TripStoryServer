package com.tripstory.tripstory.tag.domain;

import com.tripstory.tripstory.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "TAG_NAME_MEMBER_UNIQUE",
                columnNames = {"member_id", "name"})})
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String name;
}
