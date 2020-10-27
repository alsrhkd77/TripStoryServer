package com.tripstory.tripstory.member;

import com.tripstory.tripstory.util.Repository;

import java.util.Optional;

public interface MemberRepository<T, ID> extends Repository<T, ID>{

    T save(T t);

    Optional<T> findById(ID id);
}
