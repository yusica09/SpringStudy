package kr.spring.ch18;

import javax.annotation.Resource;

public class HomeController {
	//빈의 이름과 프로퍼티명이 일치하면 의존관계 설정
	@Resource
	private Camera camera1;
	
	//@Resource(name="Bean 객체의 이름") bean 객체의 이름 지정
	@Resource(name="cameraz")
	private Camera camera2;
	
	@Resource(name="camera3")
	private Camera camera3;


	

	public void setCamera1(Camera camera1) {
		this.camera1 = camera1;
	}

	public void setCamera2(Camera camera2) {
		this.camera2 = camera2;
	}

	public void setCamera3(Camera camera3) {
		this.camera3 = camera3;
	}

	@Override
	public String toString() {
		return "HomeController [camera1=" + camera1 + ", camera2=" + camera2 + ", camera3=" + camera3 + "]";
	}



	
	
}
