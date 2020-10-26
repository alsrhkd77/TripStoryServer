package com.tripstory.tripstory.member;

import java.util.Optional;

public interface MemberRepository<T, ID> {

    ID save(T t);

    Optional<T> findById(ID id);
}
