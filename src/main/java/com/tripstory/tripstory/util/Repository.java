package com.tripstory.tripstory.util;

import java.util.Optional;

public interface Repository<T, ID> {

    T save(T t);

    Optional<T> findById(ID id);

}
