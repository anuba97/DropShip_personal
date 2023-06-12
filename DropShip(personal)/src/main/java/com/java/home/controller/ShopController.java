package com.java.home.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.home.service.ShopService;
import com.java.vo.ArtistVo;
import com.java.vo.WorkReViewVo;
import com.java.vo.WorkVo;

@Controller
@RequestMapping("shop/")
public class ShopController {
	
	@Autowired
	ShopService shopservice;
	
	@Autowired
	WorkReViewVo workReViewVo;
	
	@Autowired
	WorkVo workVo;
	
	@Autowired
	ArtistVo artistVo;
	
	//////////////////↓  (상품비교) 관련 ↓         /////////////////////////
	@PostMapping("compare2")  //상품비교
	public String compare2(@RequestParam("work_id_list") String workIds, WorkVo workVo, Model model) {
		//System.out.println("넘어온 작품id 리스트 : " + workIds);
	    
		String[] workIdsArr = workIds.split(",");
		List<Integer> compare_work_id_list = new ArrayList<>();
		
		for (int i = 0; i < workIdsArr.length && i < 10; i++) { // for문으로 2~10개까지 비교가능, &&는 단락연산자이므로 첫 번째 피연산자가 거짓(배열이 비어있으면) 두번째 피 연산자는 평가되지 않으며 조건은 거짓으로 간주된다.
		    int work_id = Integer.parseInt(workIdsArr[i]);
		    compare_work_id_list.add(work_id);
		}

		List<WorkVo> compareWorkVoList = shopservice.selectWorkCompare(compare_work_id_list);
		model.addAttribute("compareWorkVoList", compareWorkVoList);
		
		return "home/shop/compare2";
	}
	
	
	
	

	
	
	
}
