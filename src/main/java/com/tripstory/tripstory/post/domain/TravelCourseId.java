package com.tripstory.tripstory.post.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TravelCourseId implements Serializable {

    private Double lat;
    private Double lng;
    private LocalDateTime passDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TravelCourseId that = (TravelCourseId) o;
        return Objects.equals(getLat(), that.getLat()) &&
                Objects.equals(getLng(), that.getLng()) &&
                Objects.equals(getPassDate(), that.getPassDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLat(), getLng(), getPassDate());
    }
}
