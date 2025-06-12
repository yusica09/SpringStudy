package kr.spring.mvc10.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.mvc10.vo.PageRank;

@Controller
public class PageRanksController {
	
	@GetMapping("/pageRanksExcel.do")
	public ModelAndView handle() {
		List<PageRank> pageRanks = new ArrayList<PageRank>();
		pageRanks.add(new PageRank(1,"/board/list.do"));
		pageRanks.add(new PageRank(2,"/board/detail.do"));
		pageRanks.add(new PageRank(3,"/member/login.do"));
		
		return new ModelAndView("pageRanks","pageRanks",pageRanks);
	}
}





