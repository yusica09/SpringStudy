package kr.spring.mvc02.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController {

	/*
	 * @RequestParam 어노테이션은 HTTP 요청 파라미터를 메서드의
	 * 파라미터로 전달
	 * [형식]
	 * 1. @RequestParam(요청파라미터네임) 메서드의 인자(파라미터)
	 *    요청파라미터를 필수적으로 사용하지 않으면 오류 발생
	 *    아래와 같이 required는 false로 지정하면 요청파라미터가 
	 *    없어도 오류가 발생하지 않음
	 *    @RequestParam(value="query", required=false)
	 *  
	 * 2. 요청파라미터명과 호출메서드의 인자명이 같으면
	 *    요청파라미터명 생략 가능 -> @RequestParam 메서드의 인자명
	 * 3. @RequestParam 생략 가능
	 *    요청파라미터명과 호출메서드의 인자명을 동일하게 표기
	 *    요청파라미터를 필수적으로 사용하지 않아도 오류가 
	 *    발생하지 않음
	 *    
	 * [전달되는 데이터가 없을 때 default 값 설정]
	 * requestParam(value="query",defaultValue="호호") String query
	 * 
	 * 
	 * [데이터가 숫자일 경우]
	 * 전송되는 데이터가 숫자일 경우 자동적으로 변환 처리
	 * @RequestParam("p" int pageNumber) 
	 * 
	 * 전송되는 파라미터가 없을 때 null로 처리되는 것이 아니라 오류발생,
	 * @RequestParam(value="p", required=false) int pageNumber
	 * 오류가 발생하지 않게 하려면 Integer로 명시해야함
	 * @RequestParam(value="p", required=false) Integer pageNum
	 * 
	 */
	
	//요청 URL과 실행 메서드 연결
	@RequestMapping("/search/internal.do")
	public ModelAndView searchInternal(@RequestParam(value="query",defaultValue="호호") String query) {
		
		System.out.println("query = " + query);
		                         //뷰이름 
		return new ModelAndView("search/internal");
	}
	
	@RequestMapping("/search/external.do")
	public ModelAndView searchExternal(
			@RequestParam(value="query",required = false) String query,
			@RequestParam(value = "p",defaultValue="1") int pageNumber) { 
			// p의 경우 String -> int 자동변환 , 대신 int는 기본자료형이기 때문에 null 처리 불가능. 
			// 따라서 required = false 해도 값을 안주면 오류남
		
		System.out.println("query = " + query);
		System.out.println("p = " + pageNumber);
		
		return new ModelAndView("search/external");
	}
}





