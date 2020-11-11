package com.tripstory.tripstory.tag;

import com.tripstory.tripstory.member.domain.Member;
import com.tripstory.tripstory.tag.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    /**
     * 회원 엔티티와 추가할 태그이름으로 새 태그 추가
     * @param member
     * @param tagName
     */
    public Tag saveTag(Member member, String tagName) {
        Tag tag = Tag.builder()
                .member(member)
                .tagName(tagName)
                .build();
        tagRepository.save(tag);
        return tag;
    }

    /**
     * 회원 ID와 태그명으로 태그 검색
     * 해당 메소드는 NoResultException 을 발생시킬 수 있음
     * 값이 무조건 존재하는 경우에만 호출하고
     * 존재를 확인해야 하는 경우 isTagExist 메소드 호출 할 것
     * @param memberId
     * @param tagName
     * @return 조회된 Tag 엔티티
     */
    public Tag getTagByMemberIdWithTagName(String memberId, String tagName) {
        return tagRepository.findByMemberIdWithTagName(memberId, tagName);
    }

    /**
     * 해당 회원과 태그명을 조회하여 사용된 적이 있는지 확인
     * @param memberId
     * @param tagName
     * @return 사용된적이있으면 true, 처음 사용하면 false
     */
    public boolean isTagExist(String memberId, String tagName) {
        try {
            tagRepository.findByMemberIdWithTagName(memberId, tagName);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
