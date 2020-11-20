package com.tripstory.tripstory.normal_post;

import com.tripstory.tripstory.follow.FollowService;
import com.tripstory.tripstory.like.LikeService;
import com.tripstory.tripstory.member.MemberService;
import com.tripstory.tripstory.member.domain.Member;
import com.tripstory.tripstory.normal_post.domain.NormalPost;
import com.tripstory.tripstory.normal_post.dto.NormalPostDTO;
import com.tripstory.tripstory.normal_post.dto.NormalPostDetailDTO;
import com.tripstory.tripstory.post.PostService;
import com.tripstory.tripstory.post.domain.Post;
import com.tripstory.tripstory.post.domain.enums.DisclosureScope;
import com.tripstory.tripstory.post.domain.enums.PostType;
import com.tripstory.tripstory.post.dto.PostThumbnail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NormalPostService {

    private final NormalPostRepository normalPostRepository;
    private final PostService postService;
    private final MemberService memberService;
    private final FollowService followService;
    private final LikeService likeService;

    /**
     * 작성자 존재 유무를 확인
     * 일반 게시물 생성
     * 포함된 태그 사용여부 확인 및 저장
     * 첨부되는 이미지 저장
     *
     * @param createData
     * @return 생성되는 Post Id
     */
    @Transactional
    public Long createNormalPost(NormalPostDTO.Create createData) {
        Member findMember = memberService.getMember(createData.getAuthor());
        if (findMember == null) {
            throw new IllegalStateException("해당 작성자는 존재하지 않습니다.");
        }
        Post newPost = postService.savePost(findMember, createData.getContent(), PostType.NORMAL, createData.getScope());
        postService.saveTags(createData.getTags(), findMember).stream()
                .forEach(postTag -> newPost.addTag(postTag));
        postService.saveImages(createData.getImages()).stream()
                .forEach(image -> newPost.addImage(image));
        NormalPost normalPost = NormalPost.builder()
                .post(newPost)
                .visitStart(createData.getVisitStart())
                .visitEnd(createData.getVisitEnd())
                .build();
        return normalPostRepository.save(normalPost).getPostId();
    }

    /**
     * ID에 해당되는 게시물이 있는지 검증
     *
     * @param postId
     * @return 있으면 true, 없으면 false
     */
    public boolean isPostExist(Long postId) {
        return normalPostRepository.findOne(postId) != null;
    }

    /**
     * ID로 해당하는 일반 게시물 엔티티 조회
     *
     * @param postId
     * @return 존재하면 일반 게시물 엔티티, 없으면 null
     */
    public NormalPost getNormalPostByPostId(Long postId) {
        return normalPostRepository.findOne(postId);
    }

    /**
     * 해당 회원이 작성한 일반 게시물 조회
     *
     * @param memberId
     * @return 일반 게시물의 썸네일 리스트
     */
    public List<PostThumbnail> getMyNormalPostThumbnailAll(String memberId) {
        return postService.getMyPost(memberId).stream()
                .map(Post::toThumbnail)
                .filter(postThumbnail -> postThumbnail.getType().equals(PostType.NORMAL))
                .collect(Collectors.toList());
    }


    /**
     * 게시물 ID와 요청 회원 ID로 게시물 공개 범위에 맞게 게시물 디테일 정보 조회
     *
     * @param postId
     * @param memberId
     * @return 요청 클라이언트로 반환될 DTO 객체 반환
     */
    public NormalPostDetailDTO getNormalPostDetail(Long postId, String memberId) {
        NormalPost findPost = normalPostRepository.findOne(postId);
        if (findPost == null) {
            throw new IllegalStateException("존재하지 않는 게시물");
        }
        Post post = findPost.getPost();
        NormalPostDetailDTO normalPostDetailDTO = new NormalPostDetailDTO();
        if (post.getMember().getMemberId().equals(memberId)) {
            normalPostDetailDTO.setResult("success");
            normalPostDetailDTO.setPostDetail(post.toPostDetail());
            normalPostDetailDTO.setNormalPostInfo(findPost.toInfo());
            normalPostDetailDTO.getPostDetail().setLiked(likeService.isLiked(post.getPostId(), memberId));
            normalPostDetailDTO.getPostDetail().setPostId(findPost.getPostId());
            return normalPostDetailDTO;
        }
        switch (findPost.getPost().getScope()) {
            // 공개 게시물은 요청 대상에 관계없이 게시물 정보 제공
            case PUBLIC:
                normalPostDetailDTO.setResult("success");
                normalPostDetailDTO.setPostDetail(post.toPostDetail());
                normalPostDetailDTO.setNormalPostInfo(findPost.toInfo());
                normalPostDetailDTO.getPostDetail().setLiked(likeService.isLiked(post.getPostId(), memberId));
                normalPostDetailDTO.getPostDetail().setPostId(findPost.getPostId());
                break;
            // 비공개 게시물은 본인을 제외하고 열람 불가능     
            case PRIVATE:
                if (post.getMember().getMemberId().equals(memberId)) {
                    normalPostDetailDTO.setResult("success");
                    normalPostDetailDTO.setPostDetail(post.toPostDetail());
                    normalPostDetailDTO.setNormalPostInfo(findPost.toInfo());
                    normalPostDetailDTO.getPostDetail().setLiked(likeService.isLiked(post.getPostId(), memberId));
                    normalPostDetailDTO.getPostDetail().setPostId(findPost.getPostId());
                } else {
                    normalPostDetailDTO.setResult("unAuthorized");
                }
                break;
            // 친구 공개 게시물은 작성자가 팔로우 중인 사람에게만 공개 가능
            case FRIEND:
                Member requestMember = memberService.getMember(memberId);
                if (followService.isMyFollowing(post.getMember().getMemberId(), requestMember.getNickName())) {
                    normalPostDetailDTO.setResult("success");
                    normalPostDetailDTO.setPostDetail(post.toPostDetail());
                    normalPostDetailDTO.setNormalPostInfo(findPost.toInfo());
                    normalPostDetailDTO.getPostDetail().setLiked(likeService.isLiked(post.getPostId(), memberId));
                    normalPostDetailDTO.getPostDetail().setPostId(findPost.getPostId());
                } else {
                    normalPostDetailDTO.setResult("unAuthorized");
                }
                break;
        }
        return normalPostDetailDTO;
    }

    /**
     * 타인 닉네임과 본인 ID로 타인의 일반 게시물 썸내일 리스트 가져와 반환
     *
     * @param nickName
     * @param memberId
     * @return 일반 게시물 썸네일 리스트
     */
    public List<PostThumbnail> getOtherNormalPostThumbnailAll(String nickName, String memberId) {
        Member requestMember = memberService.getMember(memberId);
        List<Post> otherPosts = postService.getOtherPost(nickName);
        List<PostThumbnail> thumbnails = new ArrayList<>();
        for (Post otherPost : otherPosts) {
            if(otherPost.getType() == PostType.TRAVEL) {
                continue;
            }
            if (otherPost.getScope() == DisclosureScope.PUBLIC) {
                thumbnails.add(otherPost.toThumbnail());
            } else if (otherPost.getScope() == DisclosureScope.PRIVATE) {
                continue;
            } else {
                if (followService.isMyFollowing(otherPost.getMember().getMemberId(), requestMember.getNickName())) {
                    thumbnails.add(otherPost.toThumbnail());
                }
            }
        }
        return thumbnails;
    }

    /**
     * 게시물 작성자가 게시물 삭제 요청시 해당 게시물 삭제
     * @param postId
     * @param memberId
     * @return
     */
    @Transactional
    public void deleteNormalPost(Long postId, String memberId) {
        Post findPost = postService.getOne(postId);
        if(!findPost.getMember().getMemberId().equals(memberId)) {
            throw new IllegalStateException("삭제 권한이 없는 사용자입니다.");
        }
        postService.deletePost(postId);
    }

    public List<Long> getNormalPostNotInTravel(String memberId) {
        return normalPostRepository.findByMemberIdNotInTravel(memberId);
    }
}