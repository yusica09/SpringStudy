package kr.spring.mvc07.service;


import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import kr.spring.mvc06.vo.MemberInfo;
import kr.spring.mvc07.vo.LoginVO;
public class LoginService {
	public void checkLogin(LoginVO loginVO) throws LoginCheckException{
		//테스트용으로 아이디와 비밀번호가 일치하면 로그인 성공
		if(!loginVO.getUserId().equals(loginVO.getPassword())) {
			System.out.println("인증 에러 - " + loginVO.getUserId());
			throw new LoginCheckException();
		}
	}
	
}
