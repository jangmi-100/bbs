package com.springbootstudy.bbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.springbootstudy.bbs.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping({"/", "/boardList"})
	public String boardList(Model model) {
	model.addAttribute("bList", boardService.boardList());
	
	return "main";
	
}
}
