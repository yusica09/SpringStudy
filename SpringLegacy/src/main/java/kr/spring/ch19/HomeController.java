package kr.spring.ch19;


import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/*
 * 설정파일에 <context:component-scan/> 태그를 추가하면 스프링은 지정한
 * 패키지에서 @component 어노테이션이 적용된 클래스를 검색하여 빈으로 등록
 * 자동 등록된 빈의 아이디(이름)는 클래스 이름의 첫 글자를 소문자로 바꿔서 사용
 * 예) HomeController -> homeController 로 빈의 이름 지정
 * 
 * 빈의 이름을 수동으로 지정하고 싶으면 
 * @Component("home")과 같이 명시함
 * 또는 
 * @Component
 * @Named("home")도 가능
 * 
 */

@Component
@Named("home")
public class HomeController {
	@Autowired
	private Camera camera;

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	@Override
	public String toString() {
		return "HomeController [camera=" + camera + "]";
	}


}
