package com.tripstory.tripstory.post.domain.idclass;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
public class PostTagId implements Serializable {
    private Long post;
    private Long tag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostTagId that = (PostTagId) o;
        return Objects.equals(getPost(), that.getPost()) &&
                Objects.equals(getTag(), that.getTag());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPost(), getTag());
    }
}
