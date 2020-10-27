package com.tripstory.tripstory.member;

import com.tripstory.tripstory.member.domain.Member;
import com.tripstory.tripstory.member.dto.MemberDTO;

public interface MemberService {

    MemberDTO.MemberInfo getMemberInfo(String id);

    void updateProfileImage(byte[] image, String fileName, String contentType);
}
