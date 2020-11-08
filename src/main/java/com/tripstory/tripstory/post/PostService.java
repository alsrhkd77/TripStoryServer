package com.tripstory.tripstory.post;

import com.tripstory.tripstory.member.MemberRepository;
import com.tripstory.tripstory.member.domain.Member;
import com.tripstory.tripstory.post.domain.*;
import com.tripstory.tripstory.post.dto.PostCreateDTO;
import com.tripstory.tripstory.post.dto.PostDeleteDTO;
import com.tripstory.tripstory.post.dto.PostDetailDTO;
import com.tripstory.tripstory.post.dto.PostThumbnail;
import com.tripstory.tripstory.tag.TagRepository;
import com.tripstory.tripstory.tag.domain.Tag;
import com.tripstory.tripstory.util.FileStorage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Transactional(readOnly = true)
public class PostService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final PostRepository postRepository;

    private final MemberRepository memberRepository;

    private final TagRepository tagRepository;

    private final FileStorage fileStorage;

    @Transactional
    public Long createPost(PostCreateDTO.Request request) {
        logger.info("일반 게시물 저장 시작");
        logger.info("게시물 추가 요청 회원 검증");

        Member findMember = memberRepository.findOne(request.getAuthor()).orElseGet(null);
        if (findMember == null) {
            logger.info("회원 검증 실패 존재하지 않는 회원");
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }

        // 게시물 저장
        logger.info("게시물 기본내용 저장");
        Post newPost = savePost(findMember, request.getContent());

        if (request.getTags() != null) {
            // 게시물-태그 저장
            logger.info("게시물 태그 저장");
            savePostTag(request.getTags(), findMember, newPost);
        }

        // 이미지 파일 저장
        logger.info("게시물 이미지 파일 저장 및 경로 DB에 저장");
        saveImageFile(request.getImages(), newPost);

        // 일반 게시물 저장
        logger.info("게시물 마무리 저장");
        saveNormalPost(request.getVisitStart(), request.getVisitEnd(), newPost);
        // 생성 완료된 게시물 ID 반환
        logger.info("게시물 생성 성공 및 생성된 게시물 ID 반환");
        return newPost.getId();
    }

    @Transactional
    public void deletePost(Long postId, String memberId) {
        postRepository.findOne(postId).ifPresent(
                post -> {
                    System.out.println(post.getId());
                    if(post.getMember().getId() == memberId) {
                        System.out.println(post.getMember().getId());
                        System.out.println(memberId);
                        postRepository.delete(post);
                    } else {
                        throw new IllegalStateException("삭제 권한이 없음");
                    }
                }
        );
    }

    public List<PostThumbnail> getMyPostThumbnailAll(String memberId) {
        return postRepository.findByMemberId(memberId);
    }

    public PostDetailDTO.DetailInfo getPostDetail(Long postId, String memberId) {
        Optional<Post> findPost = postRepository.findOne(postId);
        if(findPost.isEmpty()) {
            return null;
        }
        Post post = findPost.get();
        // 비공개 게시물일 경우 post 식별자를 null 로 주어서
        // controller 에서 판단하며 비공개 게시물임을 알려야함
        if (post.getScope() == DisclosureScope.PRIVATE && post.getMember().getId() != memberId) {
            return PostDetailDTO.DetailInfo.builder()
                    .postId(null)
                    .build();
        }

        // 친구 공개 게시물일 경우 조회를 요청한 회원과 작성자의 관계를 추적
        // 추적결과 친구이면 공개 아닐경우 비공개 게시물과 동일하게 처리

        // 공개해야할 경우의 DTO 구성
        int likeCount = postRepository.getLikeCount(postId);
        PostDetailDTO.DetailInfo postDetailDTO = PostDetailDTO.DetailInfo.builder()
                .postId(post.getId())
                .nickName(post.getMember().getNickName())
                .content(post.getContent())
                .createTime(post.getCreatedTime())
                .likes(likeCount)
                .visitStart(post.getNormalPost().getVisitStart())
                .visitEnd(post.getNormalPost().getVisitEnd())
                .build();
        if(post.getTags() != null) {
            post.getTags().forEach(
                    tag -> {
                        postDetailDTO.getTags().add(tag.getTag().getName());
                    }
            );
        }
        post.getImages().forEach(
                image -> {
                    postDetailDTO.getImagePaths().add(image.getPath());
                }
        );
        return postDetailDTO;
    }

    private Post savePost(Member member, String content) {
        Post post = Post.builder()
                .content(content)
                .createdTime(LocalDateTime.now())
                .type(PostType.NORMAL)
                .build();
        post.setMember(member);
        postRepository.save(post);
        return post;
    }

    private void savePostTag(List<String> tags, Member member, Post post) {
        tags.forEach(
                (tag) -> {
                    Tag newTag;
                    if (tagRepository.findByMemberIdAndTagName(member.getId(), tag).isEmpty()) {
                        newTag = Tag.builder()
                                .name(tag)
                                .member(member)
                                .build();
                        tagRepository.save(newTag);
                    } else {
                        newTag = tagRepository.findByMemberIdAndTagName(member.getId(), tag).get();
                    }
                    post.addTag(PostTag.builder()
                            .post(post)
                            .tag(newTag)
                            .build());
                }
        );
    }

    private void saveImageFile(List<MultipartFile> images, Post post) {
        images.forEach(
                (image) -> {
                    try {
                        String imagePath = fileStorage.saveFile(image.getBytes(), image.getOriginalFilename(), image.getContentType());
                        post.addImage(PostImage.builder()
                                .path(imagePath)
                                .post(post)
                                .build());
                    } catch (Exception e) {
                        logger.error("이미지 파일 저장 중 오류발생");
                        throw new IllegalStateException("이미지 파일 저장 중 오류발생");
                    }
                });
    }

    private void saveNormalPost(LocalDate visitStart, LocalDate visitEnd, Post post) {
        if (visitStart != null && visitEnd != null) {
            post.setNormalPost(NormalPost.builder()
                    .post(post)
                    .visitStart(visitStart)
                    .visitEnd(visitEnd)
                    .build());
        }else {
            post.setNormalPost(NormalPost.builder()
                    .post(post)
                    .build());
        }
    }

}
