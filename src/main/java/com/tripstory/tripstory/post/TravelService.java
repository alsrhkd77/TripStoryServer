package com.tripstory.tripstory.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripstory.tripstory.member.MemberRepository;
import com.tripstory.tripstory.member.domain.Member;
import com.tripstory.tripstory.post.domain.*;
import com.tripstory.tripstory.post.dto.TravelCreateDTO;
import com.tripstory.tripstory.tag.TagRepository;
import com.tripstory.tripstory.tag.domain.Tag;
import com.tripstory.tripstory.util.FileStorage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TravelService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final PostRepository postRepository;

    private final NormalPostRepository normalPostRepository;

    private final MemberRepository memberRepository;

    private final TagRepository tagRepository;

    private final TravelRepository travelRepository;

    private final FileStorage fileStorage;

    private final ObjectMapper mapper;

    @Transactional
    public Long createTravel(TravelCreateDTO.Request request) {
        logger.info("여행 게시물 저장 시작");
        logger.info("여행 추가 요청 회원 검증");

        Member findMember = memberRepository.findOne(request.getAuthor()).orElseGet(null);
        if (findMember == null) {
            logger.info("회원 검증 실패 존재하지 않는 회원");
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }

        // 게시물 저장
        logger.info("게시물 기본내용 저장");
        Post newPost = savePost(findMember, request.getContent());

        // 게시물-태그 저장
        logger.info("게시물 태그 저장");
        savePostTag(request.getTags(), findMember, newPost);

        // 이미지 파일 저장
        logger.info("게시물 이미지 파일 저장 및 경로 DB에 저장");
        saveImageFile(request.getImages(), newPost);

        // 여행에 포함될 게시물 사용가능 여부 조회 및 엔티티 검색
        logger.info("포함을 희망하는 게시물이 사용가능한지 조회");
        List<NormalPost> includedPost = getIncludeTargetPost(request.getPosts());

        // 여행에 포함되는 코스 엔티티로 변경
        logger.info("전달받은 여행 코스 엔티티로 변환");
        List<TravelCourse> travelCourse = getTravelCourse(request.getCourses());

        // 여행 게시물 저장
        logger.info("여행 게시물 마무리 저장");
        saveTravelPost(request.getTravelStart(), request.getTravelEnd(), newPost, includedPost, travelCourse);
        return newPost.getId();
    }

    private Post savePost(Member member, String content) {
        Post post = Post.builder()
                .content(content)
                .createdTime(LocalDateTime.now())
                .type(PostType.TRAVEL)
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
                        logger.error("이미지 파일 저장 중 오류 발생");
                        throw new IllegalStateException("이미지 파일 저장 중 오류발생");
                    }
                }
        );
    }

    private List<NormalPost> getIncludeTargetPost(List<Long> ids) {
        List<NormalPost> posts = new ArrayList<>();
        for (Long id : ids) {
            if (!normalPostRepository.findOne(id)
                    .map(NormalPost::getTravel)
                    .isPresent()) {
                NormalPost normalPost = normalPostRepository.findOne(id).orElseGet(null);
                posts.add(normalPost);
            }
        }
        return posts;
    }

    private List<TravelCourse> getTravelCourse(List<String> courses) {
        List<TravelCourse> travelCourses = new ArrayList<>();
        try {
            travelCourses = mapper.readValue(Arrays.toString(courses.toArray()), new TypeReference<List<TravelCourse>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return travelCourses;
    }


    private void saveTravelPost(LocalDate travelStart, LocalDate travelEnd, Post post, List<NormalPost> posts, List<TravelCourse> courses) {
        TravelPost travelPost;

        if (travelStart != null && travelEnd != null) {
            travelPost = TravelPost.builder()
                    .normalPosts(posts)
                    .travelStart(travelStart)
                    .travelEnd(travelEnd)
                    .post(post)
                    .build();

        }else {
            travelPost = TravelPost.builder()
                    .normalPosts(posts)
                    .post(post)
                    .build();
        }
        courses.forEach(course -> travelPost.addCourse(course));
        travelRepository.save(travelPost);
    }
}
