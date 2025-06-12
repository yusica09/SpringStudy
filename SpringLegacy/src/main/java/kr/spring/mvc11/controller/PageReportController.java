package kr.spring.mvc11.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.mvc10.vo.PageRank;

@Controller
public class PageReportController {
	@GetMapping("/pageJson.do")
	@ResponseBody //텍스트 형태나 json 문자열 만들기 위해 많이 사용
	public List<PageRank> jsonReport(){
		List<PageRank> pageRanks = new ArrayList<PageRank>();
		pageRanks.add(new PageRank(1,"/file.do"));
		pageRanks.add(new PageRank(2,"/pageRanksExcel.do"));
		pageRanks.add(new PageRank(3,"/pageJson.do"));
		//json 반환 사용 시 view 지정 안함. (이전의 Servlet Action 에서도 json 반환시 jsp 지정 x 인것처럼)
		return pageRanks;
	}
}
