package kr.spring.mvc08.vo;


import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import kr.spring.mvc06.vo.MemberInfo;
import kr.spring.mvc07.service.LoginCheckException;
import kr.spring.mvc07.service.LoginService;
import kr.spring.mvc07.vo.LoginVO;
public class SubmitReportVO {
	@NotBlank
	private String subject;
	private MultipartFile reportFile;
	
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public MultipartFile getReportFile() {
		return reportFile;
	}
	public void setReportFile(MultipartFile reportFile) {
		this.reportFile = reportFile;
	}
	@Override
	public String toString() {
		return "SubmitReportVO [subject=" + subject + ", reportFile=" + reportFile + "]";
	}
	
	
}
