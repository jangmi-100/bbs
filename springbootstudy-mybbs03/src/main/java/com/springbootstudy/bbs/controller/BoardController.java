package com.springbootstudy.bbs.controller;

import java.io.PrintWriter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springbootstudy.bbs.domain.Board;
import com.springbootstudy.bbs.service.BoardService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping({"/", "/boardList"})
	public String boardList(Model model,
			@RequestParam(value="pageNum",required = false,
			defaultValue = "1")int pageNum) {
	Map<String, Object> modelMap=boardService.boardList(pageNum);	
	model.addAllAttributes(modelMap);
	
	
	return "views/boardList";
	
}
	
	@PostMapping("/delete")
	public String deleteBoard(HttpServletResponse response, PrintWriter out,
			@RequestParam("no") int no, @RequestParam("pass") String pass) {
		
		boolean isPassCheck=boardService.isPassCheck(no, pass);
		if(! isPassCheck) {
			response.setContentType("text/html; charset=utf-8");
			out.println("<script>");
			out.println("alert('비밀번호가 맞지 않습니다.');");
			out.println("history.back();");
			out.println("</script>");
			
			return null;
		}
		boardService.deleteBoard(no);
		return "redirect:boardList";
	}
	
	
	//글쓰기
	 @GetMapping("/addBoard")
	 public String addBoard() {
		 return "views/writeForm";
	 }
	
	//상세보기
	@GetMapping("/boardDetail")
	public String getBaord(Model model,@RequestParam("no") int no) {
		model.addAttribute("board",boardService.getBoard(no));
		return "views/boardDetail";
	}
	
	@PostMapping("/addBoard")
	public String addBoard(Board board) {
		log.info("title : ",board.getTitle());
		boardService.addBoard(board);
		return "redirect:boardList";
	}
	
	@PostMapping("/updateForm")
	public String updateBoard(Model model,HttpServletResponse response, PrintWriter out, @RequestParam("no") int no, @RequestParam("pass") String pass)
	{
		boolean isPassCheck=boardService.isPassCheck(no, pass);
		if(!isPassCheck) {
			response.setContentType("text/html;charset=utf-8");
			out.println("<script>");
			out.println("alert('비밀번호가 맞지 않습니다')");
			out.println("history.back()");
			out.println("</script>");
			return null;
		}
		Board board=boardService.getBoard(no);
		model.addAttribute("board",board);
		return "views/updateForm";
	}
	
	@PostMapping("/update")
	public String updateBoard(Board board, HttpServletResponse response, PrintWriter out) {
		boolean isPassCheck=boardService.isPassCheck(board.getNo(), board.getPass());
		if(! isPassCheck) {
			response.setContentType("text/html; charset=utf-8");
			out.println("alert('비밀번호가 맞지 않습니다');");
			out.println("history.back()");
			out.println("</script>");
			return null;
		}
		boardService.updateBoard(board);
		return "redirect:boardList";
	}
}
