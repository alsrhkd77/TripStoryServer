package com.tripstory.tripstory.travel_post;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripstory.tripstory.member.MemberService;
import com.tripstory.tripstory.member.domain.Member;
import com.tripstory.tripstory.normal_post.NormalPostService;
import com.tripstory.tripstory.post.PostService;
import com.tripstory.tripstory.post.domain.Post;
import com.tripstory.tripstory.post.domain.enums.PostType;
import com.tripstory.tripstory.post.dto.PostThumbnail;
import com.tripstory.tripstory.travel_post.domain.TravelPost;
import com.tripstory.tripstory.travel_post.dto.TravelCourseDTO;
import com.tripstory.tripstory.travel_post.dto.TravelPostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TravelService {

    private final TravelRepository travelRepository;
    private final MemberService memberService;
    private final PostService postService;
    private final ObjectMapper objectMapper;
    private final NormalPostService normalPostService;
    /**
     * 작성자 존재 유무를 확인
     * 여행 게시물 생성
     * 포함된 태그 사용여부 확인 및 저장
     * 첨부되는 이미지 저장
     * 여행 코스 저장
     * 포함되는 여행 게시물 존재 여부 검증
     * 포함되는 여행 게시물 연관관계 생성
     * @param createData
     * @return 생성되는 Post Id
     */
    @Transactional
    public Long createTravelPost(TravelPostDTO.Create createData) {
        Member findMember = memberService.getMember(createData.getAuthor());
        if (findMember == null) {
            throw new IllegalStateException("해당 작성자는 존재하지 않습니다.");
        }
        Post newPost = postService.savePost(findMember, createData.getContent(), PostType.TRAVEL);
        TravelPost newTravelPost =  TravelPost.builder()
                .post(newPost)
                .travelStart(createData.getTravelStart())
                .travelEnd(createData.getTravelEnd())
                .build();
        postService.saveTags(createData.getTags(), findMember).stream()
                .forEach(postTag -> newPost.addTag(postTag));
        postService.saveImages(createData.getImages()).stream()
                .forEach(image -> newPost.addImage(image));
        if(createData.getPosts() != null) {
            createData.getPosts().stream()
                    .filter(normalPostService::isPostExist)
                    .map(normalPostService::getNormalPostByPostId)
                    .forEach(normalPost -> newTravelPost.addNormalTravel(normalPost));
        }
        jsonToEntities(createData.getCourses()).stream()
                .map(TravelCourseDTO::toTravelCourse)
                .forEach(course -> newTravelPost.addTravelCourse(course));

        return travelRepository.save(newTravelPost).getId();
    }

    /**
     * JSON 리스트로 전송된 문자열을 여행 코스 DTO 객체로 변환하여 반환
     * @param courses
     * @return List<TravelCourseDTO>, JSON 변경 오류 혹은 여행 코스가 없는 경우 비어있는 컬렉션 반환
     */
    private List<TravelCourseDTO> jsonToEntities(List<String> courses) {
        List<TravelCourseDTO> travelCourses = new ArrayList<>();
        try {
            travelCourses = objectMapper.readValue(Arrays.toString(courses.toArray()), new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return travelCourses;
    }

    /**
     * 해당 회원이 작성한 여행 게시물 조회
     *
     * @param memberId
     * @return 여행 게시물의 썸네일 리스트
     */
    public List<PostThumbnail> getMyNormalTravelThumbnailAll(String memberId) {
        return postService.getMyPost(memberId).stream()
                .map(Post::toThumbnail)
                .filter(postThumbnail -> postThumbnail.getType().equals(PostType.TRAVEL))
                .collect(Collectors.toList());
    }

    /**
     * 게시물 ID와 요청 회원 ID로 여행 게시물 상세 조회
     * @param postId
     * @param memberId
     * @return 여행게시물 상세 정보
     */
    public void getTravelPostDetail(Long postId, String memberId) {
        
    }
}
