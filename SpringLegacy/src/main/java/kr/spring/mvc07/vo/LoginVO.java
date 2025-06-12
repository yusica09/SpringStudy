package kr.spring.mvc07.vo;

import javax.validation.constraints.NotBlank;

public class LoginVO {
	@NotBlank
	private String userId;
	@NotBlank
	private String password;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "LoginVO [userId=" + userId + ", password=" + password + "]";
	}
	
	
}
