package com.tripstory.tripstory.member;

import com.tripstory.tripstory.member.domain.Member;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberRepositoryImplTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 회원추가() throws Exception {
        // given
        String newId = "tester1";

        Member member = Member.builder()
                .id(newId)
                .name("세운")
                .email("tester@test.com")
                .build();
        
        // when
        Member savedMember = (Member) memberRepository.save(member);

        // then
        Assert.assertEquals(savedMember, member);
    }
    
    @Test
    public void 아이디로_회원검색() throws Exception {
        //given
        String newId = "tester1";

        Member member = Member.builder()
                .id(newId)
                .name("세운")
                .email("tester@test.com")
                .build();
        memberRepository.save(member);

        // when
        Member findMember1 = (Member) memberRepository.findOne(newId)
                .orElseGet(() -> new Member());

        Member findMember2 = (Member) memberRepository.findOne("no")
                .orElseGet(() -> new Member());

        // then
        Assert.assertEquals(member, findMember1);
        Assert.assertEquals(newId, findMember1.getId());

        Assert.assertNotEquals(member, findMember2);
        Assert.assertEquals(null, findMember2.getId());
    }
}