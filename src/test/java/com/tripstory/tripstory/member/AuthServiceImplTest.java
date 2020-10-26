package com.tripstory.tripstory.member;

import com.tripstory.tripstory.member.domain.Member;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AuthServiceImplTest {

    @Autowired
    EntityManager em;

    @Autowired
    AuthService authService;

    @Before
    public void 샘플회원추가() {
        Member member = Member.builder()
                .id("member1")
                .name("철수")
                .email("ironwater@test.com")
                .build();

        member.join("test1234");

        em.persist(member);
    }

    @Test
    public void ID_중복확인() throws Exception {

        // given
        String newId1 = "member1"; // 이미 존재
        String newId2 = "member2"; // 존재 X

        // when
        boolean result1 = authService.isIdDuplicate(newId1);
        boolean result2 = authService.isIdDuplicate(newId2);

        // then
        Assert.assertEquals(true, result1);
        Assert.assertEquals(false, result2);
        
    }

    @Test
    public void 회원가입() throws Exception {
        // given
        // 새로운 회원
        String newId = "member2";
        String newPw = "pw1";
        String newName = "영희";
        String newEmail = "zerohe@test.com";

        // when
        String result = authService.join(newId, newPw, newName, newEmail);

        // then
        Assert.assertEquals(newId, result);
    }
    
    @Test(expected = IllegalStateException.class)
    public void 존재하는_회원가입() throws Exception {

        // given
        // 이미 존재하는 회원
        String existId = "member1";
        String existPw = "pw1";
        String existName = "철수";
        String existEmail = "ironwater@test.com";
        
        // when
        String result1 = authService.join(existId, existPw, existName, existEmail);

        // then
        
    }

    @Test
    public void TS_로그인() throws Exception {
        // given
        // 등록된 회원
        String id = "member1";
        String pw = "test1234";

        //미등록 회원
        String id2 = "member2";
        String pw2 = "test1234";
        // when
        boolean result = authService.loginByTS(id, pw);
        boolean result2 = authService.loginByTS(id2, pw2);
        // then
        Assert.assertEquals(true, result);
        Assert.assertEquals(false, result2);
    }

}