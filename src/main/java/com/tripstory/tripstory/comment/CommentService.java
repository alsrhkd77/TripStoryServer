package com.tripstory.tripstory.comment;

import com.tripstory.tripstory.comment.domain.PostComment;
import com.tripstory.tripstory.comment.dto.AuthorProfile;
import com.tripstory.tripstory.comment.dto.PostCommentDetail;
import com.tripstory.tripstory.member.MemberService;
import com.tripstory.tripstory.member.domain.Member;
import com.tripstory.tripstory.member.dto.MemberProfile;
import com.tripstory.tripstory.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final MemberService memberService;

    /**
     * 게시물에 회원 ID로 새로운 댓글 추가
     * @param postId
     * @param memberId
     * @param content
     */
    @Transactional
    public Long addComment(Long postId, String memberId, String content) {
        Member findMember = memberService.getMember(memberId);
        PostComment newComment = PostComment.builder()
                .post(postService.getOne(postId))
                .member(findMember)
                .content(content)
                .createdTime(LocalDateTime.now())
                .build();
        commentRepository.save(newComment);
        return newComment.getId();
    }

    /**
     * 댓글 ID로 이미 작성된 댓글 삭제
     * @param commentId
     */
    @Transactional
    public void removeComment(Long commentId) {
        commentRepository.delete(commentRepository.findOne(commentId));
    }

    /**
     * 게시물 ID로 해당 게시물에 달린 댓글 상세정보 리스트 조회
     * @param postId
     * @return 조회된 댓글 상세정보 리스트 반환
     */
    public List<PostCommentDetail> getComments(Long postId) {
        List<PostComment> comments = commentRepository.findByPostId(postId);
        List<PostCommentDetail> details = new ArrayList<>();
        for (PostComment comment : comments) {
            AuthorProfile memberProfile = comment.getMember().toAuthorProfile();
            PostCommentDetail postCommentDetail = comment.toCommentDetail();
            postCommentDetail.setAuthorProfile(memberProfile);
            details.add(postCommentDetail);
        }
        return details;
    }
}
