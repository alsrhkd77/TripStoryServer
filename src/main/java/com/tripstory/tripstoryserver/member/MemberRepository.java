package com.tripstory.tripstoryserver.member;

import com.tripstory.tripstoryserver.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByMemberId(String memberId);


}
