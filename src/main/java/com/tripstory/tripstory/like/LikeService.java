package com.tripstory.tripstory.like;

import com.tripstory.tripstory.like.domain.PostLike;
import com.tripstory.tripstory.like.domain.idclass.PostLikeId;
import com.tripstory.tripstory.member.MemberService;
import com.tripstory.tripstory.member.domain.Member;
import com.tripstory.tripstory.post.PostService;
import com.tripstory.tripstory.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberService memberService;
    private final PostService postService;

    /**
     * 새로운 좋아요 추가
     * @param postId
     * @param memberId
     */
    public void like(Long postId, String memberId) {
        Post findPost = postService.getOne(postId);
        Member findMember = memberService.getMember(memberId);
        if (findPost == null || findMember == null) {
            throw new IllegalStateException("존재하지 않는 게시물 또는 존재하지 않는 회원");
        }
        likeRepository.save(findPost, findMember);
    }

    /**
     * 좋아요 취소
     * @param postId
     * @param memberId
     */
    public void unLike(Long postId, String memberId) {
        PostLike findPostLike = likeRepository.findOne(new PostLikeId(postId, memberId));
        likeRepository.delete(findPostLike);
    }

    /**
     * 회원이 해당 게시물에 좋아요를 눌렀는지 확인
     * @param postId
     * @param memberId
     * @return 좋아요를 눌렀으면 true, 아니면 false
     */
    public boolean isLiked(Long postId, String memberId) {
        PostLike findPostLike = likeRepository.findOne(new PostLikeId(postId, memberId));
        return findPostLike != null;
    }
}
