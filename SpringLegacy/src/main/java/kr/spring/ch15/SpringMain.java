package kr.spring.ch15;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMain {
	public static void main(String[] args) {
		//applicationContext3.xml 설정파일을 읽어들여 스프링 컨테이너 생성
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext3.xml");
		
		//프로퍼티 타입을 이용한 의존관계 자동 설정
		//동일한 타입의 객체가 여러개 존재하면 오류 발생
		SystemMonitor monitor = (SystemMonitor)context.getBean("systemMonitor");
		
		System.out.println(monitor);
		context.close();
		
	}//main
}
