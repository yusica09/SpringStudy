package kr.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.board.service.BoardService;
import kr.spring.board.vo.BoardVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.FileUtil;
import kr.spring.util.PagingUtil;
import kr.spring.util.StringUtil;

@Controller
@RequestMapping("/board")
public class BoardController {
	private static final Logger log = 
			LoggerFactory.getLogger(
					            BoardController.class);
	@Autowired
	private BoardService boardService;
	
	//자바빈(VO) 객체 초기화
	@ModelAttribute
	public BoardVO initCommand() {
		return new BoardVO();
	}
	
	//글 등록 폼 호출
	@GetMapping("/write.do")
	public String form() {
		return "boardWrite";
	}
	
	//글 등록하기
	@PostMapping("/write.do")
	public String submit(@Valid BoardVO boardVO,
			             BindingResult result,
			             HttpSession session,
			             HttpServletRequest request,
			             Model model) 
	                      throws IllegalStateException,
	                             IOException{
		log.debug("<<게시판 글 저장>> : " + boardVO);
		
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return form();
		}
		
		//회원 번호 셋팅
		boardVO.setMem_num(
				((MemberVO)session.getAttribute("user"))
				                          .getMem_num());
		//ip 셋팅
		boardVO.setIp(request.getRemoteAddr());
		//파일 업로드
		boardVO.setFilename(
				FileUtil.createFile(request, 
						         boardVO.getUpload()));
		//글쓰기
		boardService.insertBoard(boardVO);
				
		model.addAttribute("message", "글 등록 완료!!");
		model.addAttribute("url",
		   request.getContextPath()+"/board/list.do");
		
		return "common/resultAlert";
	}
	
	//게시판 목록
	@GetMapping("/list.do")
	public String getList(
			   @RequestParam(defaultValue="1") int pageNum,
			   String keyfield,String keyword,Model model) {
		
		int count = boardService.selectRowCount(
				                   keyfield, keyword);
		log.debug("<<글의 레코드수>> : " + count);
		
		//페이지 처리
		PagingUtil page = 
				new PagingUtil(keyfield,keyword,pageNum,
						         count,20,10,"list.do");
		List<BoardVO> list = null;
		if(count > 0) {
			Map<String,Object> map = 
					new HashMap<String,Object>();
			map.put("keyfield", keyfield);
			map.put("keyword", keyword);
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			
			list = boardService.selectList(map);			
		}
		
		model.addAttribute("count", count);
		model.addAttribute("list", list);
		model.addAttribute("page", page.getPage());
		
		return "boardList";
	}
	
	//글상세
	@GetMapping("/detail.do")
	public String getDetail(long board_num,Model model) {
		log.debug("<<글상세 - board_num>> : " + board_num);
		
		//해당 글의 조회수 증가
		boardService.updateHit(board_num);
		
		BoardVO board = 
				boardService.selectBoard(board_num);
		board.setTitle(StringUtil.useNoHtml(board.getTitle()));
		//summer note 사용시는 content에 html 태그 사용 허용
		
		model.addAttribute("board", board);
		
		return "boardView";
	}
	//파일 다운로드
	@GetMapping("/file.do")
	public ModelAndView download(long board_num,
			                     HttpSession session) {
		BoardVO board = 
				boardService.selectBoard(board_num);
		//컨텍스트 경로상의 절대경로 구하기
		String path = session.getServletContext()
				             .getRealPath("/upload")
				               +"/"+board.getFilename();
		File downloadFile = new File(path);		
		           //뷰이름,속성명,속성값
		return new ModelAndView(
				"downloadView","downloadFile",downloadFile);
	}
	
	//글수정 폼
	@GetMapping("/update.do")
	public String formUpdate(long board_num,
			                         Model model) {
		BoardVO boardVO = 
				boardService.selectBoard(board_num);
		model.addAttribute("boardVO", boardVO);
		
		return "boardModify";
	}
	
	//글수정 처리
	@PostMapping("/update.do")
	public String submitUpdate(@Valid BoardVO boardVO,
			           BindingResult result,
			           HttpServletRequest request,
			           Model model) throws IllegalStateException, IOException {
		log.debug("<<글수정 처리>> : " + boardVO);
		
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return "boardModify";
		}
		
		//ip 셋팅
		boardVO.setIp(request.getRemoteAddr());
		//파일 업로드 처리
		//파일이 업로드 되면 파일명 반환,
		//파일이 업로드 되지 않으면 null 반환
		boardVO.setFilename(FileUtil.createFile(
				        request, boardVO.getUpload()));
		//글수정
		boardService.updateBoard(boardVO);
		
		model.addAttribute("message", "글수정 완료!!");
		model.addAttribute("url", 
		  request.getContextPath() + "/board/detail.do?board_num="+boardVO.getBoard_num());
		
		return "common/resultAlert";
	}
	
	//글삭제 처리
	@GetMapping("/delete.do")
	public String submitDelete(long board_num,
			              HttpServletRequest request) {
		
		log.debug("<<글삭제>> : " + board_num);
		
		//DB에 저장된 파일 정보 구하기
		BoardVO db_board = 
				boardService.selectBoard(board_num);
		//글삭제
		boardService.deleteBoard(board_num);
		
		if(db_board.getFilename()!=null) {
			//파일 삭제
			FileUtil.removeFile(request, 
					     db_board.getFilename());
		}		
		return "redirect:/board/list.do";
	}
	
}




















