package com.springbootstudy.bbs.controller;

import java.io.PrintWriter;
import java.lang.ProcessBuilder.Redirect;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String getBaord(Model model,@RequestParam("no") int no,@RequestParam(value="pageNum",defaultValue = "1") int pageNum) {
		Board board = boardService.getBoard(no, true);
		
		model.addAttribute("board",board);
		model.addAttribute("pageNum",pageNum);
		
		return "views/boardDetail";
	}
	
	@PostMapping("/addBoard")
	public String addBoard(Board board) {
		log.info("title : ",board.getTitle());
		boardService.addBoard(board);
		return "redirect:boardList";
	}
	
	@PostMapping("/updateForm")
	public String updateBoard(Model model,HttpServletResponse response, PrintWriter out, 
			@RequestParam("no") int no, @RequestParam("pass") String pass,@RequestParam(value="pageNum",defaultValue = "1")int pageNum)
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
		Board board=boardService.getBoard(no,false);
		model.addAttribute("board",board);
		model.addAttribute("pageNum",pageNum);
		return "views/updateForm";
	}
	
	@PostMapping("/update")
	public String updateBoard(Board board, HttpServletResponse response, PrintWriter out,
			RedirectAttributes reAttrs, @RequestParam(value="pageNum",defaultValue="1")int pageNum) {
		boolean isPassCheck=boardService.isPassCheck(board.getNo(), board.getPass());
		if(! isPassCheck) {
			response.setContentType("text/html; charset=utf-8");
			out.println("alert('비밀번호가 맞지 않습니다');");
			out.println("history.back()");
			out.println("</script>");
			return null;
		}
		boardService.updateBoard(board);
		reAttrs.addAttribute("pageNum",pageNum);
		reAttrs.addFlashAttribute("test1","1회성 파라미터");
		return "redirect:boardList";
	}
}
