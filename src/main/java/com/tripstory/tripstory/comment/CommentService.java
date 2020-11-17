package com.tripstory.tripstory.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    public void addComment(Long postId, String memberId, String content) {

    }

    public void removeComment() {

    }

    public void getComments(Long postId) {

    }
}
