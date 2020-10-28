package com.tripstory.tripstory.post.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class NormalPost {

    @Id
    @Column(name = "post_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDate visitStart;

    private LocalDate visitEnd;
}
