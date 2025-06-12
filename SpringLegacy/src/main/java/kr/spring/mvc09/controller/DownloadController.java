package kr.spring.mvc09.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
@Controller
public class DownloadController {

	@GetMapping("/file.do")
	public ModelAndView download(HttpSession session) {
		//file.txt의 컨텍스트 경로상의 절대경로 구하기
		String path = session.getServletContext().getRealPath("/WEB-INF/file.txt");
		File downloadFile = new File(path);
												//뷰이름			//속성명				속성값
		return new ModelAndView("download","downloadFile",downloadFile);
	}
}
