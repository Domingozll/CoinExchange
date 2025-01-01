package com.bizzan.bitrade.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bizzan.bitrade.entity.Member;
import com.bizzan.bitrade.service.MemberService;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


public class MemberServiceTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	private MemberService memberService;
	
	@Test
	public void test() {
        Member member=memberService.findOne(25L);
        System.out.println(">>>>>>>>>>>>>>"+member);
        
	}

}
