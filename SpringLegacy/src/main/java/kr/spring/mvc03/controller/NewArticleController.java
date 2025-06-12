package kr.spring.mvc03.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.spring.mvc03.service.ArticleService;
import kr.spring.mvc03.vo.NewArticleCommand;

@Controller
public class NewArticleController {
	
	@Autowired //setter 자동생성
	private ArticleService articleService;

	//Get 요청이 들어올 때 호출
	@GetMapping("/article/newArticle.do")
	//@RequestMapping(value="/article/newArticle.do", method=RequestMethod.GET)
	public String form() {
					//뷰이름
		return "article/newArticleForm";
	}
	
	/*
	 *	@ModelAttribute 어노테이션을 이용해서 전송된 데이터를 자바빈에 담기
	 *	[기능]
	 *	1. 자바빈(VO) 생성
	 *	2. 전송된 데이터를 자바빈에 저장
	 *	3. View에서 사용할 수 있도록 request에 자바빈(VO)를 저장
	 * 
	 * 	[형식]
	 * 1. @ModelAttribute(속성명) NewArticleCommand command
	 * 		지정한 속성명으로 JSP에서 el을 통해 자바빈(VO) 호출가능
	 * 		예) ${속성명.title}
	 * 2. @ModelAttribute를 명시할 때 속성명을 생략할 수 있음
	 * 		속성명을 생략하면 클래스명의 첫 글자를 소문자로 속성명을 자동 생성
	 * 		예)ModelAttribute NewArticleCommand command
	 * 		예)${new ArticleCommand.title}
	 * 3. @ModelAttribute 생략
	 * 		호출 메서드에 인자명만 명시
	 * 		예)NewArticleCommand command 와 같이 인자명만 명시
	 * 
	 */
	
	
	//Post 요청이 들어올 때 호출
	@PostMapping("/article/newArticle.do")
	//@RequestMapping(value="/article/newArticle.do", method=RequestMethod.POST)
	public String submit(NewArticleCommand command) {
		
		articleService.writeArticle(command);
		
		return "article/newArticleSubmitted";
	}
}
