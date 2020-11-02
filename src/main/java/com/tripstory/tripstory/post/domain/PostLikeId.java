package com.tripstory.tripstory.post.domain;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
public class PostLikeId implements Serializable {

    private Long post;
    private String member;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostLikeId that = (PostLikeId) o;
        return Objects.equals(getPost(), that.getPost()) &&
                Objects.equals(getMember(), that.getMember());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPost(), getMember());
    }
}
