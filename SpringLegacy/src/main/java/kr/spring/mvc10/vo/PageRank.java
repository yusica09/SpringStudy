package kr.spring.mvc10.vo;


import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import kr.spring.mvc06.vo.MemberInfo;
import kr.spring.mvc07.service.LoginCheckException;
import kr.spring.mvc07.service.LoginService;
import kr.spring.mvc07.vo.LoginVO;
public class PageRank {
	
	private int rank;
	private String page;
	
	
	public PageRank() {}
	
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public PageRank(int rank, String page) {
		this.rank = rank;
		this.page = page;
	}
	
	
	
}
