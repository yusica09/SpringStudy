package kr.spring.ch07;

public class RegisterService {
	private RegisterDAO registerDAO; //프로퍼티
	
	//프로퍼티에 객체를 전달하기 위한 메서드 ( set + 프로퍼티이름 )
	public void setRegisterDAO(RegisterDAO registerDAO) {
		this.registerDAO = registerDAO;
	}
	
	public void write() {
		System.out.println("RegisterService의 write() 메서드 실행");
		registerDAO.insert();
	}
}
