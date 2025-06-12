package kr.spring.mvc08.controller;

import java.io.File;
import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import kr.spring.mvc08.vo.SubmitReportVO;

@Controller
public class SubmitReportController {

	//파일 업로드 경로 읽기
	@Value("${file_path}")
	private String path;
	
	//유효성 체크를 위한 자바빈 초기화
	@ModelAttribute("report")
	public SubmitReportVO initCommand() {
		return new SubmitReportVO();
	}
	
	@GetMapping("/report/submitReport.do")
	public String form() {
		return "report/submitReportForm";
	}
	
	@PostMapping("/report/submitReport.do")
	public String submit(@Valid @ModelAttribute("report") SubmitReportVO vo, BindingResult result) 
			    		   throws IllegalStateException, IOException {
		
		System.out.println("전송된 데이터 : " + vo);
		
		//파일 업로드 필수 여부 체크
		if(vo.getReportFile()==null || vo.getReportFile().isEmpty()) {
			                  			//필드           오류 코드
			result.rejectValue("reportFile", "required");
		}
		
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return form();
		}
		
		//전송된 파일을 업로드 경로에 저장
		File file = new File(path + "/" + vo.getReportFile().getOriginalFilename());
		//지정한 경로에 파일 저장
		vo.getReportFile().transferTo(file);
		
		System.out.println("주제 : " + vo.getSubject());
		System.out.println("업로드한 파일 : "  + vo.getReportFile().getOriginalFilename());
		System.out.println("파일 용량 : " + vo.getReportFile().getSize());
		
		return "report/submittedReport";
	}
	
}









