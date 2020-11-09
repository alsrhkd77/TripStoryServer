package com.tripstory.tripstory.follow.domain;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
public class FollowId implements Serializable {

    private String memberId;
    private String followingId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowId followId = (FollowId) o;
        return Objects.equals(getMemberId(), followId.getMemberId()) &&
                Objects.equals(getFollowingId(), followId.getFollowingId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMemberId(), getFollowingId());
    }
}
