package com.springbootstudy.bbs.ajax;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springbootstudy.bbs.domain.Reply;
import com.springbootstudy.bbs.service.BoardService;
import com.springbootstudy.bbs.service.MemberService;

@RestController
public class AjaxProcessController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/passCheck.ajax")
	public Map<String, Boolean> memberPassCheck(
			@RequestParam("id") String id,@RequestParam("pass") String pass){
		
		boolean result = memberService.memberPassCheck(id, pass);
		Map<String, Boolean> map=new HashMap<String, Boolean>();
		map.put("result",result);
		
		return map;
	}
	
	@PostMapping("/recommend.ajax")
	public Map<String, Integer> recommend(@RequestParam("no")int no,
			@RequestParam("recommend")String recommend){
		return boardService.recommend(no, recommend);
	}
	
	@PostMapping("/replyWrite.ajax")
	public List<Reply> addReply(Reply reply){
		boardService.addReply(reply);
		return boardService.replyList(reply.getBbsNo());
	}
	
	@PatchMapping("/replyUpdate.ajax")
	public List<Reply> updateReply(Reply reply){
		boardService.updateReply(reply);
		return boardService.replyList(reply.getBbsNo());
	}
	
	@DeleteMapping("/replyDelete.ajax")
	public List<Reply> deleteReply(@RequestParam("no") int no,
			@RequestParam("bbsNo")int bbsNo){
		boardService.deleteReply(no);
		return boardService.replyList(bbsNo);
	}
	
}
