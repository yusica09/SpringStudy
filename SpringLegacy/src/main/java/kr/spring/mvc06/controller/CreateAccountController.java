package kr.spring.mvc06.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import kr.spring.mvc06.vo.MemberInfo;

@Controller
public class CreateAccountController {
	
	//유효성 체크를 할 경우 자바빈(VO) 초기화 필수
	//@ModelAttribute(속성명)
	@ModelAttribute("command")
	public MemberInfo initCommand() {
		return new MemberInfo();
	}
	
	@GetMapping("/account/create.do")
	public String form() {
		return "account/createForm";
	}
	
	@PostMapping("/account/create.do")
	public String submit(@Valid @ModelAttribute("command") MemberInfo memberInfo, BindingResult result) {
		
		System.out.println("데이터 전송 후 : " + memberInfo);
		
		//BindingResult에 유효성 체크 결과 오류에 대한 정보가
		//저장되어 있으면 폼을 다시 호출
		if(result.hasErrors()) {
			return "account/createForm";
		}
		
		return "account/created";
	}
}
