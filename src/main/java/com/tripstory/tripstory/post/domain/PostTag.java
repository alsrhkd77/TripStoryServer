package com.tripstory.tripstory.post.domain;

import com.tripstory.tripstory.post.domain.idclass.PostTagId;
import com.tripstory.tripstory.tag.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@IdClass(PostTagId.class)
public class PostTag {

    @Id
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Id
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public void changePost(Post post) {
        this.post = post;
    }

    public String getTagName() {
        return tag.getTagName();
    }
}
