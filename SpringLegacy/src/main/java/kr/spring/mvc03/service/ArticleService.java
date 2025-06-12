package kr.spring.mvc03.service;

import org.springframework.stereotype.Service;

import kr.spring.mvc03.vo.NewArticleCommand;

@Service
public class ArticleService {
	
	public void writeArticle(NewArticleCommand command) {
		System.out.println("신규 게시글 등록 : " + command);
	}
}
