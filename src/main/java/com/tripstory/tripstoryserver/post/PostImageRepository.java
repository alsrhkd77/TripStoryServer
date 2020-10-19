package com.tripstory.tripstoryserver.post;

import com.tripstory.tripstoryserver.post.entity.PostImages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImages, Long> {
    List<PostImages> findByPostPostId(Long postId);
}
