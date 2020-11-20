package com.tripstory.tripstory.member;

import com.tripstory.tripstory.follow.FollowRepository;
import com.tripstory.tripstory.member.domain.Member;
import com.tripstory.tripstory.member.dto.MemberProfile;
import com.tripstory.tripstory.member.dto.MemberSearchDTO;
import com.tripstory.tripstory.util.FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;
    private final FileStorage fileStorage;

    // 이미지 저장 경로
    @Value("${resources.profile-image.location}")
    private String location;

    // 이미지 불러올때 부르는 경로
    @Value("${resources.profile-image.path}")
    private String path;

    /**
     * 새로운 고객 추가
     *
     * @param member
     * @return 중복되는 값으로 인한 오류 발생시 Exception
     */
    @Transactional
    public String addNewMember(Member member) {
        try {
            memberRepository.save(member);
        } catch (Exception e) {
            throw new IllegalStateException("데이터베이스 오류 또는 잘못된 입력값입니다. ");
        }
        return member.getMemberId();
    }

    /**
     * 회원 ID로 해당 회원이 존재하는지 확인
     *
     * @param memberId
     * @return 존재하면 true, 아니면 false
     */
    public boolean isMemberExist(String memberId) {
        Member findMember = memberRepository.findOne(memberId);
        return findMember != null;
    }

    /**
     * 회원 닉네임으로 해당 회원이 존재하는지 확인
     *
     * @param nickName
     * @return 존재하면 true, 아니면 false
     */
    public boolean isMemberExistByNickName(String nickName) {
        try {
            memberRepository.findByNickName(nickName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 회원 ID로 회원 조회
     *
     * @param memberId
     * @return
     */
    public Member getMember(String memberId) {
        return memberRepository.findOne(memberId);
    }

    /**
     * 회원 닉네임으로 회원 조회
     *
     * @param nickName
     * @return
     */
    public Member getMemberByNickName(String nickName) {
        return memberRepository.findByNickName(nickName);
    }

    /**
     * 회원 ID로 회원을 조회해서 닉네임을 변경하는 메소드
     *
     * @param memberId
     * @param nickName
     * @return 성공시 변경된 닉네임, 실패시 Exception
     */
    @Transactional
    public String changeNickName(String memberId, String nickName) {
        Member findMember = getMember(memberId);
        if (findMember == null) {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
        findMember.changeNickName(nickName);
        return findMember.getNickName();
    }

    /**
     * 닉네임으로 해당 회원 프로필 정보 조회
     *
     * @param nickName
     * @return 회원 프로필 정보 객체 반환
     */
    public MemberProfile getMemberProfile(String nickName, String memberId) {
        Member findMember = memberRepository.findByNickName(nickName);
        if (findMember == null) {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
        MemberProfile memberProfile = findMember.toMemberProfile();
        try {
            memberProfile.setFollowers(followRepository.findByFollowingId(findMember.getMemberId()).size());
            memberProfile.setFollowings(followRepository.findByMemberId(findMember.getMemberId()).size());
            try {
                memberProfile.setFollowed(followRepository.findOne(memberId, nickName) != null);
            } catch (Exception e) {
                memberProfile.setFollowed(false);
            }
            memberProfile.setResult("success");
        } catch (Exception e) {
            e.printStackTrace();
            memberProfile.setFollowers(0);
            memberProfile.setFollowings(0);
        }
        return memberProfile;
    }

    /**
     * 회원 이름, 이미지를 받아서 회원의 프로필 사진을 변경함
     *
     * @param memberId
     * @param image
     */
    @Transactional
    public String changeProfileImage(String memberId, MultipartFile image) {
        Member findMember = memberRepository.findOne(memberId);
        if (!findMember.isDefaultProfileImage()) {
            fileStorage.deleteFile(findMember.getProfileImagePath().replace(path + "/", ""), location);
        }
        try {
            String savedFileName = fileStorage.saveFile(image.getBytes(),
                    image.getOriginalFilename(),
                    image.getContentType(),
                    location);
            findMember.changeProfileImage(path + "/" + savedFileName);
            return path + "/" + savedFileName;
        } catch (IOException e) {
            throw new IllegalStateException("프로필 변경중 오류발생");
        }
    }

    /**
     * 키워드로 회원을 검색(이름)해서 존재하는 회원의 일부 정보를 반환
     * @param keyword  
     * @return 회원 검색 정보 리스트 반환
     */
    public List<MemberSearchDTO.MemberSearchInfo> searchMemberByName(String keyword) {
        return memberRepository.findByNameKeyword(keyword).stream()
                .map(Member::toSearchInfo)
                .collect(Collectors.toList());
    }

    /**
     * 키워드로 회원을 검색(닉네임)해서 존재하는 회원의 일부 정보를 반환
     * @param keyword
     * @return 회원 검색 정보 리스트 반환
     */
    public List<MemberSearchDTO.MemberSearchInfo> searchMemberByNickName(String keyword) {
        return memberRepository.findByNickNameKeyword(keyword).stream()
                .map(Member::toSearchInfo)
                .collect(Collectors.toList());
    }
}
