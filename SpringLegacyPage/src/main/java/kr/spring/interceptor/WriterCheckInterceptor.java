package kr.spring.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.spring.board.service.BoardService;
import kr.spring.board.vo.BoardVO;
import kr.spring.member.vo.MemberVO;

public class WriterCheckInterceptor extends
                           HandlerInterceptorAdapter{
	private static final Logger log = 
			LoggerFactory.getLogger(
					      WriterCheckInterceptor.class);
	
	@Autowired
	private BoardService boardService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			               HttpServletResponse response,
			           Object handler)throws Exception {
		log.debug(
		  "<<로그인 회원번호와 작성자 회원번호 일치 여부 체크>>");
		
		//작성자의 회원번호 구하기
		long board_num = Long.parseLong(
				request.getParameter("board_num"));
		BoardVO board = 
				    boardService.selectBoard(board_num);
		
		//로그인 회원번호 구하기
		HttpSession session = request.getSession();
		MemberVO user= 
				(MemberVO)session.getAttribute("user");
		log.debug("<<로그인 회원번호>> : " + user.getMem_num());
		log.debug("<<작성자 회원번호>> : " + board.getMem_num());
		
		//로그인 회원번호와 작성자 회원번호 일치 여부 체크
		if(user.getMem_num()!=board.getMem_num()) {
			log.debug("<<로그인 회원번호와 작성자 회원번호 불일치>>");
			
			request.setAttribute("accessMsg", 
					"로그인 회원과 작성자 회원 불일치");
			request.setAttribute("accessUrl", 
				  request.getContextPath()+"/board/list.do");
			
			RequestDispatcher dispatcher = 
					request.getRequestDispatcher(
					     "/WEB-INF/common/accessDenied.jsp");
			dispatcher.forward(request, response);
			
			return false;
		}
		
		return true;
	}
	
}







