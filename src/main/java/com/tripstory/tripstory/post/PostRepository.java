package com.tripstory.tripstory.post;

import com.tripstory.tripstory.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class PostRepository  {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final EntityManager em;

    public Post save(Post post) {
        em.persist(post);
        return post;
    }

    public Optional<Post> fineOne(Long id) {
        return Optional.ofNullable(em.find(Post.class, id));
    }

    public List<Post> findByMemberId(String memberId) {
        return em.createQuery("SELECT p FROM Post p WHERE p.member.id = :memberId", Post.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }
}
