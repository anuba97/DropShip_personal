package com.java.home.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import com.java.home.service.MemberService;
import com.java.home.service.MyShopService;
import com.java.home.service.ShopService;
import com.java.vo.ArtistVo;
import com.java.vo.DeliveryVo;
import com.java.vo.MemberVo;
import com.java.vo.OptionVo;

import com.java.vo.Order_MemberVo;

import com.java.vo.WorkReViewVo;
import com.java.vo.WorkVo;

@Controller
@RequestMapping("myshop/")
public class MyShopController {

	@Autowired
	ShopService shopservice;

	@Autowired
	MemberService memberservice;

	@Autowired
	HttpSession session;
	
	@Autowired
	MyShopService myShopService;
	
	@Autowired
	WorkVo workVo;

	@Autowired
	MemberVo memberVo;

	@Autowired
	OptionVo optionVo;

	@Autowired
	DeliveryVo deliveryVo;
	
	@Autowired
	WorkReViewVo workReViewVo;

	
	
	
	@GetMapping("mypage_drone") // 마이페이지 (드론페이지)
	public String mypage_drone(int id,Model model) {
		getOrderInquiryData(id, model);
		Map<String, Object> map = myShopService.selectFind_Dronshipment(id);
		model.addAttribute("map",map);
		model.addAttribute("id",id);
		
		return "home/myshop/mypage_drone";
	}

	
	// 1. 주문상세 orderinquiry.jsp랑 2. 드론조회 mypage_drone.jsp 에서 사용
	public void getOrderInquiryData(int order_member_id, Model model) {
		int member_id = 0;

		// 회원 객체 가져옴 (아직 비회원 구매기능은 구현 안해놔서 로그인 했을때만 제대로 작동)
		if (session.getAttribute("sessionMember_id") != null) {
			member_id = (int) session.getAttribute("sessionMember_id");
			memberVo = memberservice.selectOne(member_id);
		}

		// 전달받았던 order_member_id_int(회원 주문 고유번호)를 사용해서 회원 주문 객체(order_memberVo)를 통째로 가져옴.
		Order_MemberVo order_memberVo = myShopService.selectOrderMemberOne_result(order_member_id); // 회원 주문 객체. 회원 주문은

		// work_id들이 담긴 리스트와 option_id들이 담긴 리스트를 저장한 map을 리턴받는 메소드. -> workVo와
		// optionVo들이 필요하기 때문. 중간과정.
		Map<String, List<Integer>> orderDetail = myShopService.selectOrderDetail(order_member_id); // orderDetail에
																									// 담겨있음
		// 방금 받아온 optionIdList를 사용해서 OptionVo들이 담긴 리스트를 받아오기
		List<OptionVo> optionVoList = shopservice.selectOptionList(orderDetail.get("optionIdList")); // 주문 상세에 대한

		// 이번엔 workIdList를 사용해서 WorkVo들이 담긴 리스트(workVoList) 받아오기 + 작가이름을 가져오기 위해
		// artistVo들이 담긴 리스트(artistVoList)도 맵으로 한번에 가져오기
		Map<String, List<? extends Object>> workArtistVoMap = shopservice
				.selectMemberWorkList(orderDetail.get("workIdList"));

		List<WorkVo> workVoList = (List<WorkVo>) workArtistVoMap.get("workVoList");
		List<ArtistVo> artistVoList = (List<ArtistVo>) workArtistVoMap.get("artistVoList");

		model.addAttribute("memberVo", memberVo);
		model.addAttribute("order_memberVo", order_memberVo);
		model.addAttribute("workVoList", workVoList);
		model.addAttribute("artistVoList", artistVoList);
		model.addAttribute("optionVoList", optionVoList);
		model.addAttribute("optionVoListSize", optionVoList.size());

//			// 원래 내 무지성 join 방식.
//			Order_Detail_inquire_viewVo order_Detail_inquire_viewVo = shopservice.selectOptionOneInquiryView(order_member_id);
//			model.addAttribute("order_Detail_inquire_viewVo", order_Detail_inquire_viewVo);

		int total_price = 0;
		for (OptionVo optionVo : optionVoList) {
			total_price += optionVo.getOption_selected_price() * optionVo.getOption_quantity();
		}

		model.addAttribute("total_price", total_price);
	} // getOrderInquiryData()

	
	
}
