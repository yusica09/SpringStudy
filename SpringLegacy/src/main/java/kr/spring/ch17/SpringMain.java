package kr.spring.ch17;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMain {
	public static void main(String[] args) {
		//applicationContextAnnot.xml 설정파일을 읽어들여 스프링 컨테이너 생성
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContextAnnot.xml");
		
		//@Autowired 어노테이션을 이용한 의존관계 자동 설정
		//동일한 타입의 객체가 여러개 존재하면 오류 발생
		SystemMonitor2 monitor = (SystemMonitor2)context.getBean("systemMonitor");
		
		System.out.println(monitor);
		context.close();
		
	}//main
}
