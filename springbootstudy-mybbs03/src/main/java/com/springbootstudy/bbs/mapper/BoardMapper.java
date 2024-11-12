package com.springbootstudy.bbs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.springbootstudy.bbs.domain.Board;

@Mapper
public interface BoardMapper {
// 게시글 리스트를 DB 테이블에서 읽어와 반환하는 메소드
public List<Board> boardList();

public Board getBoard(int no);

public void insertBoard(Board board);

public String isPassCheck(int no);

public void updateBoard(Board board);

public void deleteBoard(int no);

public List<Board> boardList(@Param("startRow") int startRow, @Param("num") int num
		,@Param("type")String type, @Param("keyword") String keyword);

public int getBoardCount(@Param("type")String type, @Param("keyword") String keyword);

public void incrementReadCount(int no);

}