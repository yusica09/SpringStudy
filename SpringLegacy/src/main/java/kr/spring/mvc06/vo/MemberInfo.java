package kr.spring.mvc06.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.validation.BindingResult;

public class MemberInfo {
	/*
	 * 	@NotNull : null만 허용하지 않음
	 * @NotBlank : null, ""(빈문자열), " "(공백)을 허용하지않음
	 * @NotEmpty : null과 ""(빈문자열)을 허용하지않음
	 * 
	 * 
	 * @Valid 를 사용하는 controller 메서드에 넣어줘야지 위의 3가지 유효성검사가 반응함.
	 * @Valid 안붙이면 무반응
	 * BindingResult 에 에러정보 저장
	 */
	
	@NotBlank
	private String id;
	@NotBlank
	private String name;
	@NotBlank
	private String zipcode;
	@NotBlank
	private String address1;
	@NotBlank
	private String address2;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	@Override
	public String toString() {
		return "MemberInfo [id=" + id + ", name=" + name + ", zipcode=" + zipcode + ", address1=" + address1
				+ ", address2=" + address2 + "]";
	}
	
	
}
