package com.tripstory.tripstory.normal_post;

import com.tripstory.tripstory.member.MemberService;
import com.tripstory.tripstory.member.domain.Member;
import com.tripstory.tripstory.normal_post.domain.NormalPost;
import com.tripstory.tripstory.normal_post.dto.NormalPostDTO;
import com.tripstory.tripstory.post.PostService;
import com.tripstory.tripstory.post.domain.Post;
import com.tripstory.tripstory.post.domain.enums.PostType;
import com.tripstory.tripstory.post.dto.PostThumbnail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NormalPostService {

    private final NormalPostRepository normalPostRepository;
    private final PostService postService;
    private final MemberService memberService;

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
        Post newPost = postService.savePost(findMember, createData.getContent(), PostType.NORMAL);
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
     *
     */
    public void getNormalPostDetail(Long postId, String memberId) {

    }
}
