package kr.spring.ch20;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//자바 코드 기반 설정
@Configuration
public class SpringConfig {
	
	@Bean
	public Worker worker() {
		return new Worker();
	}
	
	/*
	 * @Bean을 명시해서 빈객체를 생성할 때는 지정한 메서드명을 빈객체의, 이름으로 사용
	 * @Bean("Bean이름") 이와 같이 bean이름을 지정할 수 있음
	 * 
	 */
	
	@Bean
	public Executor executor() {
		return new Executor();
	}
}
