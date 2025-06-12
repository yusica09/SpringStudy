package kr.spring.ch12;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMain {
	public static void main(String[] args) {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext2.xml");
		//Properties 타입 프로퍼티 설정
		BookClient book = (BookClient)context.getBean("bookClient");
		System.out.println(book);
		
		context.close();
	}//main
}
