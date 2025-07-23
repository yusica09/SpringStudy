package kr.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/*
 * 인터셉터(Interceptor)는 웹 요청(request)과 응답(response)
 * 처리 흐름에서 특정 공통 작업을 수행하기 위해 사용
 * HandlerInterceptorAdapter를 상속 받아서 구현.
 * 컨트롤러 전/후 요청을 가로채서 공통 로직 수행
 */
public class LoginCheckInterceptor extends 
                        HandlerInterceptorAdapter{
	private static final Logger log =
			LoggerFactory.getLogger(
					  LoginCheckInterceptor.class);
	@Override
	public boolean preHandle(
			             HttpServletRequest request,
			             HttpServletResponse response,
			             Object handler)throws Exception{
		
		log.debug("<<LoginCheckInterceptor 진입>>");
		
		//로그인 여부 검사
		HttpSession session = request.getSession();
		if(session.getAttribute("user")==null) {
			log.debug("<<로그인 실패>>");
			//로그인이 되지 않은 상태
			response.sendRedirect(
			  request.getContextPath()+"/member/login.do");
			return false;
		}	
		log.debug("<<로그인 성공>>");
		return true;
	}
}









