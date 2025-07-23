package kr.spring.member.controller;


import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.AuthCheckException;

@Controller
@RequestMapping("/member")
public class MemberUserController {
	private static final Logger log = 
			LoggerFactory.getLogger(MemberUserController.class);
	
	@Autowired
	private MemberService memberService;
	
	//자바빈(VO) 초기화
	@ModelAttribute
	public MemberVO initCommand() {
		return new MemberVO();
	}
	
	//회원 등록 폼 호출
	@GetMapping("/registerUser.do")
	public String form() {
		       //타일스 설정명
		return "memberRegister";
	}
	
	//회원 등록
	@PostMapping("/registerUser.do")
	public String submit(@Valid MemberVO memberVO,
			             BindingResult result,
			             Model model, 
			             HttpServletRequest request) {
		
		log.debug("<<회원 등록 - MemberVO>> : " + memberVO);
		
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return form();
		}
		
		//회원가입
		memberService.insertMember(memberVO);
		
		model.addAttribute("accessTitle", "회원가입");
		model.addAttribute("accessMsg", 
				   "회원가입이 정상적으로 완료되었습니다.");
		model.addAttribute("accessBtn", "홈으로");
		model.addAttribute("accessUrl", 
				request.getContextPath()+"/main/main.do");
		
		return "common/resultView";
	}
	//로그인 폼 호출
	@GetMapping("/login.do")
	public String loginForm() {
		return "memberLogin";
	}
	//로그인 처리
	@PostMapping("/login.do")
	public String loginSubmit(
			      @Valid MemberVO memberVO,
			      BindingResult result,
			      HttpSession session) {
		
		log.debug("<<로그인 - MemberVO>> : " + memberVO);
		
		//유효성 체크 결과 오류가 있으면 폼
		//id와 passwd 필드만 체크
		if(result.hasFieldErrors("id") || 
		   result.hasFieldErrors("passwd")) {
			return loginForm();
		}
		
		//로그인 체크(id,비밀번호 일치 여부 체크)
		try {
			MemberVO member = 
					memberService.selectCheckMember(
							         memberVO.getId());
			boolean check = false;
			
			if(member!=null) {
				//비밀번호 일치 여부 체크
				check = member.isCheckedPasswd(
						         memberVO.getPasswd());
			}
			if(check) {
				//인증 성공, 로그인 처리
				session.setAttribute("user", member);
				
				log.debug("<<인증 성공>>");
				log.debug("<<id>> : " + member.getId());
				log.debug("<<auth>> : " + member.getAuth());
				
				return "redirect:/main/main.do";
			}
			
			//인증 실패
			throw new AuthCheckException();
						
		}catch(AuthCheckException e) {
			//인증 실패로 로그인 폼 호출
			result.reject("invalidIdOrPassword");
			
			log.debug("<<인증 실패>>");
			
			return loginForm();
		}		
	}
	//로그아웃 처리
	@GetMapping("/logout.do")
	public String logout(HttpSession session) {
		//로그아웃
		session.invalidate();
		return "redirect:/main/main.do";
	}
	
	//회원 상세 정보
	@GetMapping("/myPage.do")
	public String getMyPage(HttpSession session,
			                Model model) {
		MemberVO vo = 
				(MemberVO)session.getAttribute("user");
		MemberVO member =
				memberService.selectMember(
						              vo.getMem_num());
		log.debug("<<회원 상세 정보>> : " + member);
		
		model.addAttribute("member", member);
		
		return "memberView";
	}
	
	//수정 폼 호출
	@GetMapping("/update.do")
	public String updateForm(HttpSession session,
			                 Model model) {
		MemberVO vo = 
				(MemberVO)session.getAttribute("user");
		
		MemberVO memberVO =
				memberService.selectMember(
						              vo.getMem_num());
		model.addAttribute("memberVO", memberVO);
		
		return "memberModify";
	}
	
	//수정 폼에서 전송된 데이터 처리
	@PostMapping("/update.do")
	public String updateSubmit(
			@Valid MemberVO memberVO,
			BindingResult result,
			HttpSession session) {
		log.debug("<<회원 정보 수정>> : " + memberVO);
		
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return "memberModify";
		}
		
		MemberVO vo = 
				(MemberVO)session.getAttribute("user");
		memberVO.setMem_num(vo.getMem_num());
		
		//회원 정보 수정
		memberService.updateMember(memberVO);
		
		return "redirect:/member/myPage.do";
	}
	
	//이미지 출력
	@GetMapping("/photoView.do")
	public ModelAndView viewImage(HttpSession session) {
		
		MemberVO user = 
				(MemberVO)session.getAttribute("user");
		MemberVO memberVO = 
				memberService.selectMember(
						            user.getMem_num());
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("imageView");
		mav.addObject("imageFile",memberVO.getPhoto());
		mav.addObject("filename", memberVO.getPhoto_name());
		
		return mav;
	}
	
	//비밀번호 변경 폼 호출
	@GetMapping("/changePassword.do")
	public String formChangePassword() {
		return "memberChangePassword";
	}
	
	//비밀번호 변경하기
	@PostMapping("/changePassword.do")
	public String submitChangePassword(
			      @Valid MemberVO memberVO,
			      BindingResult result,
			      HttpSession session,
			      HttpServletRequest request,
			      Model model) {
		
		log.debug("<<비밀번호 변경>> : " + memberVO);
		
		//유효성 체크 결과 오류가 있으면 폼 호출
		//now_passwd, passwd만 체크
		if(result.hasFieldErrors("now_passwd") || 
			       result.hasFieldErrors("passwd")) {
			return formChangePassword();
		}
		
		MemberVO vo = 
				(MemberVO)session.getAttribute("user");
		memberVO.setMem_num(vo.getMem_num());
		
		//폼에서 전송한 현재 비밀번호와 DB에서 받아온 현재 비밀번호
		//일치 여부 체크
		MemberVO member = memberService.selectMember(
				               memberVO.getMem_num());
		if(!member.getPasswd().equals(
				          memberVO.getNow_passwd())) {
			result.rejectValue("now_passwd", "invalidPassword");
			return formChangePassword();
		}
		
		//비밀번호 수정
		memberService.updatePassword(memberVO);
		
		model.addAttribute("message", 
				       "비밀번호가 정상적으로 변경되었습니다.");
		model.addAttribute("url", 
		   request.getContextPath()+"/member/myPage.do");
		
		return "common/resultAlert";
	}
	
	//회원 탈퇴 폼
	@GetMapping("/delete.do")
	public String formDelete() {
		return "memberDelete";
	}
	
	@PostMapping("/delete.do")
	public String submitDelete(@Valid MemberVO memberVO,
			                  BindingResult result,
			                  HttpSession session,
			                  Model model,
			                  HttpServletRequest request) {
		log.debug("<<회원탈퇴>> : " + memberVO);
		
		//유효성 체크 결과 오류가 있으면 폼 호출
		//id,passwd 필드의 에러만 체크
		if(result.hasFieldErrors("id") || 
				 result.hasFieldErrors("passwd")) {
			return formDelete();
		}
		
		MemberVO vo = 
				(MemberVO)session.getAttribute("user");
		memberVO.setMem_num(vo.getMem_num());
		
		//id,비밀번호 일치 여부 체크
		try {
			MemberVO member = 
					memberService.selectMember(
							 memberVO.getMem_num());
			boolean check = false;
			if(member!=null && 
				memberVO.getId().equals(member.getId())){
				//비밀번호 일치 여부 체크
				check = member.isCheckedPasswd(
						           memberVO.getPasswd());
			}
			if(check) {
				//인증 성공, 회원정보 삭제
				memberService.deleteMember(
						        memberVO.getMem_num());
				//로그아웃
				session.invalidate();
				
				model.addAttribute("accessTitle", "회원탈퇴");
				model.addAttribute("accessMsg", 
						    "회원탈퇴가 정상적으로 완료되었습니다.");
				model.addAttribute("accessBtn", "홈으로");
				model.addAttribute("accessUrl", 
					request.getContextPath()+"/main/main.do");
				
				return "common/resultView";
			}
			//인증 실패
			throw new AuthCheckException();
			
		}catch(AuthCheckException e) {
			//인증 실패
			result.reject("invalidIdOrPassword");
			return formDelete();
		}		
	}
}














