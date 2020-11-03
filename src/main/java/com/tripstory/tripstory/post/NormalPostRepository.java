package com.tripstory.tripstory.post;

import com.tripstory.tripstory.post.domain.NormalPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class NormalPostRepository {

    private final EntityManager em;

    public Optional<NormalPost> findOne(Long id) {
        return Optional.ofNullable(em.find(NormalPost.class, id));
    }
}
