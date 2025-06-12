package kr.spring.ch21;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import kr.spring.core.Product;

public class SpringMain {
	public static void main(String[] args) {
		//스프링 컨테이너 생성
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContextAOP.xml");
		//핵심 기능을 수행하는 메서드 호출
		Product p = (Product)context.getBean("product");
		p.launch();
		
		context.close();
	}
}






