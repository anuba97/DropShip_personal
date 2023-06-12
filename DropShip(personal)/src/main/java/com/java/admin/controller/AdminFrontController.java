package com.java.admin.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.admin.service.AdminOrderService;
import com.java.admin.service.AdminService;
import com.java.admin.service.DropshipMemberService;
import com.java.home.service.BoardService;
import com.java.home.service.MemberService;
import com.java.home.service.MyShopService;
import com.java.vo.Count_Order_Price_By_MonthVo;
import com.java.vo.MemberCountDayVo;
import com.java.vo.Order_DetailVo;
import com.java.vo.Order_MemberVo;

@Controller
public class AdminFrontController {
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	MemberService memberSerivce;
	
	
	@Autowired
	AdminOrderService adminOrderService;
	
	@Autowired
	BoardService boardService;
		
	@Autowired
	DropshipMemberService dropshipMemberService;
	
	@Autowired
	Order_DetailVo order_DetailVo;
	
	@Autowired
	HttpSession session;
	
	
	
	

	
	@GetMapping("admin_freeBoardList") //어드민 일반 게시판 페이지 열기
	public String admin_freeBoardList(@RequestParam(defaultValue = "1") int page, Model model) {
		Map<String, Object> map = boardService.selectBoardAll(page);
		model.addAttribute("map", map);
		return "admin/admin_freeBoardList";
	}
	
	private Map<String, Object> getMemberCountData() {
	    Map<String , Object> memberCountDayMap = new HashMap<>();
	    String [] memberLabel= null; //Choi Ki-hwa
	    int [] memberData=null;//initialize
	    // Get access to DB memberLabel, memberData
	    List<MemberCountDayVo> list= dropshipMemberService.selectMember_Reg_Date();
	    System.out.println("Result: "+ list);

	    List<String> monthDayDateList = new ArrayList<>();
	    for(MemberCountDayVo memberCountDayVo : list) {
	        String monthDayDate = Integer.toString(memberCountDayVo.getMonth())+"월"+Integer.toString(memberCountDayVo.getDay())+"일";
	        monthDayDateList. add(monthDayDate); // [0:"March 1st", 1:"March 2nd", 2:"March 3rd"]
	    }

	    List<Integer> monthDayDateMemberCountList = new ArrayList<>();
	    for(MemberCountDayVo memberCountDayVo : list) {
	        monthDayDateMemberCountList.add(memberCountDayVo.getMember_count()); // [22, 3, 12,....]
	    }

	    memberCountDayMap.put("dateList", monthDayDateList);
	    memberCountDayMap.put("memberCountList", monthDayDateMemberCountList);

	    return memberCountDayMap;
	}

	private Map<String , Object> getOrderTotalData() {
	    Map<String , Object> map =new HashMap<>();
	    Date [] orderDay= null; 
	    int [] totalPrice=null;
	    // Get access to DB memberLabel, memberData
	    List<Count_Order_Price_By_MonthVo> list= dropshipMemberService.selectOrderTotalByMonth();
	    System.out.println("111111: "+orderDay);
	    if(list !=null) {
	    	orderDay = new Date [list.size()];
	        totalPrice = new int [list.size()];

	        for(int i=0; i<list.size(); i++) {
	        	orderDay[i] = list.get(i).getOrder_Day();
	            totalPrice[i] = list.get(i).getTotal_Price();
	        }
	    }

	    map.put("orderDay", orderDay); // horizontal axis names
	    map.put("totalPrice", totalPrice); // vertical axis data value

	    return map;
	}

	@GetMapping("/adminchartData3")
	@ResponseBody 
	public Map<String, Object> chartData3(@RequestParam String period, @RequestParam String period2) {

	    Map<String , Object> combinedData = new HashMap<>();
	    combinedData.put("memberCountDayMap",getMemberCountData());
	    combinedData.put("orderTotalMap",getOrderTotalData());

	    return combinedData;
	}
	
	
	
}
