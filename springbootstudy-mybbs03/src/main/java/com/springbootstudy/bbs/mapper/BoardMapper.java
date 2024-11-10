package com.springbootstudy.bbs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.springbootstudy.bbs.domain.Board;

@Mapper
public interface BoardMapper {
// 게시글 리스트를 DB 테이블에서 읽어와 반환하는 메소드
public List<Board> boardList();

public Board getBoard(int no);
}