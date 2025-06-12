package kr.spring.ch19;

import javax.inject.Named;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMain {
	public static void main(String[] args) {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContextScan.xml");
		//@Named("home") 이였으므로 getBean("home")
		HomeController home = (HomeController)context.getBean("home");
		System.out.println(home);
		context.close();
	}//main
}
