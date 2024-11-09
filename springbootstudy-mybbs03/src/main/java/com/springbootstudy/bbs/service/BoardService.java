package com.springbootstudy.bbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootstudy.bbs.domain.Board;
import com.springbootstudy.bbs.mapper.BoardMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardService {
// DB 작업에 필요한 BoardMapper 객체를 의존성 주입 설정
@Autowired
private BoardMapper boardMapper;
// 전체 게시글을 읽어와 반환하는 메서드
public List<Board> boardList() {
log.info("BoardService: boardList()");
return boardMapper.boardList();
}
}
