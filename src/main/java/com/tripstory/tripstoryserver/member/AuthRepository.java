package com.tripstory.tripstoryserver.member;

import com.tripstory.tripstoryserver.member.entity.MemberAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<MemberAuth, Long> {
    MemberAuth findByLoginId(String loginId);
}
