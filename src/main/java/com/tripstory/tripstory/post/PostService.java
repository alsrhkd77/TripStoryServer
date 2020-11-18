package com.tripstory.tripstory.post;

import com.tripstory.tripstory.member.domain.Member;
import com.tripstory.tripstory.post.domain.Post;
import com.tripstory.tripstory.post.domain.PostImage;
import com.tripstory.tripstory.post.domain.PostTag;
import com.tripstory.tripstory.post.domain.enums.PostType;
import com.tripstory.tripstory.tag.TagService;
import com.tripstory.tripstory.tag.domain.Tag;
import com.tripstory.tripstory.util.FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TagService tagService;
    private final FileStorage fileStorage;

    /**
     * 게시물의 기본 골격이 되는 작성자와 내용 저장
     *
     * @param member
     * @param content
     * @return 생성된 Post 엔티티
     */
    public Post savePost(Member member, String content, PostType type) {
        Post newPost = Post.builder()
                .member(member)
                .content(content)
                .type(type)
                .build();
        postRepository.save(newPost);
        return newPost;
    }

    /**
     * 게시물에 포함되는 이미지 파일을 Storage 에 저장
     * 해당 이미지가 속하는 Post 에 연관관계 설정이 필수
     *
     * @param images
     * @return PostImage 엔티티
     */
    public List<PostImage> saveImages(List<MultipartFile> images) {
        List<PostImage> postImages = new ArrayList<>();
        if (images == null) {
            return postImages;
        }
        for (MultipartFile image : images) {
            try {
                String filePath = fileStorage.saveFile(image.getBytes(),
                        image.getOriginalFilename(),
                        image.getContentType());
                PostImage postImage = PostImage.builder()
                        .path(filePath)
                        .build();
                postImages.add(postImage);
            } catch (Exception e) {
                throw new IllegalStateException("이미지 저장중 오류가 발생했습니다.");
            }
        }
        return postImages;
    }

    /**
     * 게시물에 적용되는 태그의 이전 사용여부에 따라 Tag 엔티티를 생성하고
     * 게시물-태그 엔티티를 반환
     * 해당 게시물-태그가 속하는 Post 에 연관관계 설정이 필수
     * @param tags
     * @param member
     * @return PostTag 엔티티
     */
    public List<PostTag> saveTags(List<String> tags, Member member) {
        List<PostTag> postTags = new ArrayList<>();
        if (tags == null) {
            return postTags;
        }
        for (String tag : tags) {
            PostTag postTag;
            if (!tagService.isTagExist(member.getMemberId(), tag)) {
                Tag newTag = tagService.saveTag(member, tag);
                postTag = PostTag.builder()
                        .tag(newTag)
                        .build();
            } else {
                Tag findTag = tagService.getTagByMemberIdWithTagName(member.getMemberId(), tag);
                postTag = PostTag.builder()
                        .tag(findTag)
                        .build();
            }
            postTags.add(postTag);
        }
        return postTags;
    }

    /**
     * 회원이 작성한 게시물 리스트 반환
     * @param memberId
     * @return Post 엔티티 리스트
     */
    public List<Post> getMyPost(String memberId) {
        return postRepository.findByMemberId(memberId);
    }

    /**
     * 게시물 ID로 게시물 하나 조회
     * @param postId
     * @return 조회된 게시물
     */
    public Post getOne(Long postId) {
        return postRepository.findOne(postId);
    }

    /**
     * 회원 닉네임으로 해당 회원이 작성한 게시물 리스트 반환
     * @param nickName
     * @return
     */
    public List<Post> getOtherPost(String nickName) {
        return postRepository.findByNickName(nickName);
    }


    @Transactional
    public void deletePost(Long postId) {
        Post findPost = postRepository.findOne(postId);
        findPost.getPostImages().stream()
        .map(PostImage::getImagePath)
        .forEach(filePath -> fileStorage.deleteFile(filePath));
        postRepository.delete(findPost);
    }
}
