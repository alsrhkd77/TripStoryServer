package com.tripstory.tripstory.auth;

import com.tripstory.tripstory.auth.domain.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class AuthRepository {

    private final EntityManager em;

    /**
     * TS 가입시에만 사용됨
     * 인증 엔티티를 저장하고 저장된 엔티티 반환
     * @param auth
     * @return Auth 엔티티
     */
    public Auth save(Auth auth) {
        em.persist(auth);
        return auth;
    }

    /**
     * 회원 ID로 인증 객체 검색
     * @param memberId
     * @return 존재하면 Auth 엔티티, 없으면 null
     */
    public Auth findOne(String memberId) {
        return em.find(Auth.class, memberId);
    }
}
