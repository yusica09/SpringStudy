package kr.spring.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/main/main.do")
	public String viewMain() {
		//타일스 설정명
		return "main";
	}
	
}
