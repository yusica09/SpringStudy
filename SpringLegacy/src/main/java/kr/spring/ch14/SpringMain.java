package kr.spring.ch14;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMain {
	public static void main(String[] args) {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext3.xml");
		
		/*
		 * bean 객체의 타입이나 이름을 이용하여 의존객체를 자동으로 설정 할 수 있는 기능
		 * bean 태그의 autowire 속성에 지정할 수 있는 값
		 * 
		 * byName : 프로퍼티의 이름과 같은 이름을 갖는 bean 객체를 설정
		 * byType : 프로퍼티의 타입과 같은 타입을 갖는 bean 객체를 설정
		 * 
		 * constructor : 생성자 파라미터 타입과 같은 타입을 갖는 bean 객체를 생성자에 전달
		 * 
		 */
		
		WriteArticleService articleService = (WriteArticleService)context.getBean("writeArticleService");
		articleService.write();
		
		context.close();
	}//main
}
