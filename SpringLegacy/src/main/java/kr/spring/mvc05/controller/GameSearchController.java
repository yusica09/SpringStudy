package kr.spring.mvc05.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.spring.mvc03.service.ArticleService;
import kr.spring.mvc03.vo.NewArticleCommand;
import kr.spring.mvc05.service.SearchService;
import kr.spring.mvc05.vo.SearchCommand;

@Controller
public class GameSearchController {
	
	@Autowired
	private SearchService searchService;

	@GetMapping("/search/main.do")
	public String main() {
		
		return "search/main";
	}
	
	
	@GetMapping("/search/game.do")
	public String search(@ModelAttribute("command") SearchCommand command, Model model) {
		
		String result = searchService.search(command);
		model.addAttribute("searchResult", result);
		
		return "search/game";
	}
}
