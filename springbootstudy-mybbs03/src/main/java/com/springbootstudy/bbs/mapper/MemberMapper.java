package com.springbootstudy.bbs.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.springbootstudy.bbs.domain.Member;

@Mapper
public interface MemberMapper {

	public Member getMember(String id);
	
	public void addMember(Member member);
	
	public String memberPassCheck(String id);
	
	public void updateMember(Member member);
}
