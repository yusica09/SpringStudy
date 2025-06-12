package kr.spring.mvc05.service;

import kr.spring.mvc05.vo.SearchCommand;

public class SearchService {
	public String search(SearchCommand command) {
		System.out.println(command);
		
		return "검색 완료!!";
	}
}
