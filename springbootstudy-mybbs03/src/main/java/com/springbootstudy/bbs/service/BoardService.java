package com.springbootstudy.bbs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootstudy.bbs.domain.Board;
import com.springbootstudy.bbs.domain.Reply;
import com.springbootstudy.bbs.mapper.BoardMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardService {
// DB 작업에 필요한 BoardMapper 객체를 의존성 주입 설정
@Autowired
private BoardMapper boardMapper;

private static final int PAGE_SIZE = 10;

private static final int PAGE_GROUP=10;

// 전체 게시글을 읽어와 반환하는 메서드
public Map<String, Object> boardList(int pageNum,String type, String keyword) {
log.info("BoardService: boardList(int pageNum,String type, String keyword)");

boolean searchOption=(type.equals("null")||keyword.equals("null"))?false:true;

int currentPage=pageNum;

int startRow = (currentPage-1)*PAGE_SIZE;

int listCount = boardMapper.getBoardCount(type,keyword);

List<Board> boardList=boardMapper.boardList(startRow,PAGE_SIZE,type,keyword);

int pageCount = listCount/PAGE_SIZE + (listCount % PAGE_SIZE==0?0:1);

int startPage=(currentPage/PAGE_GROUP)*PAGE_GROUP+1-
(currentPage%PAGE_GROUP==0?PAGE_GROUP:0);

int endPage= startPage+PAGE_GROUP-1;

if(endPage>pageCount) {
	endPage=pageCount;
}
Map<String,Object> modelMap = new HashMap<String,Object>();

modelMap.put("bList",boardList);
modelMap.put("pageCount",pageCount);
modelMap.put("startPage",startPage);
modelMap.put("endPage",endPage);
modelMap.put("currentPage",currentPage);
modelMap.put("listCount", listCount);
modelMap.put("pageGroup", PAGE_GROUP);
modelMap.put("searchOption", searchOption);

if(searchOption) {
	modelMap.put("type", type);
	modelMap.put("keyword", keyword);
}

return modelMap;
}

public Board getBoard(int no,boolean isCount) {
	log.info("BoardService:getBorad(int no,boolean isCount)");
	
	if(isCount) {
		boardMapper.incrementReadCount(no);
	}
	return boardMapper.getBoard(no);
}

public void addBoard(Board board) {
	log.info("BoardService:addBoard(Board board)");
	boardMapper.insertBoard(board);
}

public boolean isPassCheck(int no, String pass) {
	log.info("BoardService:isPassCheck(int no, String pass)");
	boolean result=false;
	
	String dbPass=boardMapper.isPassCheck(no);
	
	if(dbPass.equals(pass)) {
		result=true;
	}
	return result;
}

public void updateBoard(Board board) {
	log.info("BoardService:updateBoard(Board board)");
	boardMapper.updateBoard(board);
}

public void deleteBoard(int no) {
	log.info("BoardService:deleteBoard(int no)");
	boardMapper.deleteBoard(no);
}

public List<Reply> replyList(int no){
	return boardMapper.replyList(no);
}

public Map<String, Integer> recommend(int no, String recommend){
	boardMapper.updateRecommend(no, recommend);
	
	Board board = boardMapper.getRecommend(no);
	
	Map<String, Integer> map = new HashMap<String,Integer>();
	
	map.put("recommend", board.getRecommend());
	map.put("thank", board.getThank());
	return map;
}

}
