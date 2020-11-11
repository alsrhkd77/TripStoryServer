package com.tripstory.tripstory.post.domain.idclass;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
public class PostLikeId implements Serializable {
    private Long postId;
    private String memberId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostLikeId that = (PostLikeId) o;
        return Objects.equals(getPostId(), that.getPostId()) &&
                Objects.equals(getMemberId(), that.getMemberId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPostId(), getMemberId());
    }
}
