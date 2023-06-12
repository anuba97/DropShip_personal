package com.java.home.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.home.service.ShopService;
import com.java.vo.ArtistVo;
import com.java.vo.WorkVo;

@Controller
@RequestMapping("bxabout/")
public class BxaboutController {
	
	@Autowired
	ShopService shopservice;
	
	@Autowired
	WorkVo workVo;
	
	@Autowired
	ArtistVo artistVo;
	

	@PostMapping("search") // 헤더에서 검색하기
	public String search(@RequestParam("searchWord") String searchWord, Model model) {
		System.out.println("wdwdwDW"+searchWord);
		List<WorkVo> searchResultList = shopservice.search(searchWord);
		model.addAttribute("searchResultList",searchResultList);
		System.out.println("wdwdwDW"+searchResultList);
		System.out.println();
		return "home/bxabout/search";
	}
	
}
