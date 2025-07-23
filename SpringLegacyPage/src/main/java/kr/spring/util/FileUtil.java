package kr.spring.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	//업로드 상대경로
	private static final String UPLOAD_PATH = "/upload";
	
	//파일 업로드 처리
	public static String createFile(
			           HttpServletRequest request,
			           MultipartFile file)
			   throws IllegalStateException,IOException{
		//컨텍스트 경로상의 절대경로 구하기
		String path = request.getSession()
				             .getServletContext()
				             .getRealPath(UPLOAD_PATH);
		String filename = null;
		if(file!=null && !file.isEmpty()) {
			//파일명이 중복되지 않도록 파일명 변경
			//원래 파일명을 보존하지 않을 경우
			filename = 
				UUID.randomUUID()
				   +file.getOriginalFilename()
				         .substring(file.getOriginalFilename()
				                    	.lastIndexOf("."));
			file.transferTo(new File(path+"/"+filename));
		}		
		return filename;
	}
	//파일 삭제
	public static void removeFile(
			                HttpServletRequest request,
			                        String filename) {
		if(filename!=null) {
			//컨텍스트 경로상의 절대경로 구하기
			String path = request.getSession()
					             .getServletContext()
					             .getRealPath(UPLOAD_PATH);
			File file = new File(path + "/" + filename);
			if(file.exists()) file.delete();
		}
	}
	
}





