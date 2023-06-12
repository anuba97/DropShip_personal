package com.java.admin.controller;



import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import com.java.home.service.BoardService;
import com.java.vo.BoardVo;

@Controller
public class AdminBoardController {
	
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	HttpSession session;
	
	
	////////////////////////////////////////게시판///////////////////////////////////////
		
	@GetMapping("admin_freeBoardView") //홈페이지에 등록된 게시글 1개 불러오기
		public String admin_freeBoardView(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "1") int id, Model model) {
		BoardVo boardVo = boardService.selectOneforAdmin(id);
		model.addAttribute("boardVo", boardVo);
		model.addAttribute("page", page);
		
		return "admin/admin_freeBoardView";
	}
	
	@PostMapping("admin_freeBoardModify")
		public String admin_freeBoardModify(BoardVo boardVo, Model model) {
		boardService.adminupdateBoard(boardVo);
		return "redirect:admin_freeBoardList";
	}
	
	
	
	
	
}//AdminBoardController
