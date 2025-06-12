package kr.spring.ch19;

import org.springframework.stereotype.Component;

//자동스캔 대상 지정
@Component
public class Camera {
	private int number;

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "Camera [number=" + number + "]";
	}
	
	
}
