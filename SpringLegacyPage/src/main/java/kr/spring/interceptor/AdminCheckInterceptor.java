package kr.spring.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.spring.member.vo.MemberVO;

public class AdminCheckInterceptor extends 
                        HandlerInterceptorAdapter{
	private static final Logger log = 
			LoggerFactory.getLogger(
					       AdminCheckInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			                 HttpServletResponse response,
			             Object handler)throws Exception{
		log.debug("<<AdminCheckInterceptor 진입>>");
		
		HttpSession session = request.getSession();
		MemberVO user = 
				(MemberVO)session.getAttribute("user");
		if(user.getAuth()!=9) {
			log.debug("<<관리자페이지 접속 실패>>");
			//관리자 권한이 아닐 때
			RequestDispatcher dispatcher = 
					request.getRequestDispatcher(
				    "/WEB-INF/views/common/accessDenied.jsp");
			dispatcher.forward(request, response);
			return false;
		}		
		log.debug("<<관리자페이지 접속 성공>>");
		return true;
	}
	
}








