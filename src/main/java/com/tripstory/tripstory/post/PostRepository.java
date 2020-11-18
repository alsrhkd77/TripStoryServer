package com.tripstory.tripstory.post;

import com.tripstory.tripstory.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    /**
     * 새로운 게시물 생성
     * 게시물에 연관된 이미지, 태그 등이 있는 경우 Cascade.ALL 옵션에 의해 같이 저장됨
     *
     * @param post
     * @return 생성된 게시물 엔티티
     */
    public Post save(Post post) {
        em.persist(post);
        return post;
    }

    /**
     * 게시물 삭제
     * 일반 게시물, 여행 게시물 삭제시 연관된 엔티티로 인한 문제 발생가능 하니 사전 예방 필수
     *
     * @param post
     */
    public void delete(Post post) {
        em.createQuery("delete from Post p where p.postId = :postId")
                .setParameter("postId", post.getPostId())
                .executeUpdate();
//        em.remove(post);
    }

    /**
     * 게시물 ID로 게시물 단건 조회
     *
     * @param postId
     * @return 조회된 게시물 엔티티 반환
     */
    public Post findOne(Long postId) {
        return em.find(Post.class, postId);
    }

    /**
     * 회원 ID로 해당 회원이 작성한 게시물을 전부 가져옴
     *
     * @param memberId
     * @return
     */
    public List<Post> findByMemberId(String memberId) {
        String query = "SELECT p FROM Post p WHERE p.member.memberId = :memberId";
        return em.createQuery(query, Post.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    /**
     * 회원 닉네임으로 해당 회원이 작성한 게시물을 전부 가져옴
     *
     * @param nickName
     * @return
     */
    public List<Post> findByNickName(String nickName) {
        String query = "SELECT p FROM Post p WHERE p.member.nickName = :nickName";
        return em.createQuery(query, Post.class)
                .setParameter("nickName", nickName)
                .getResultList();
    }
}
