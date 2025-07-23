package kr.spring.board.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.board.service.BoardService;
import kr.spring.board.vo.BoardFavVO;
import kr.spring.board.vo.BoardReplyVO;
import kr.spring.board.vo.BoardVO;
import kr.spring.main.controller.MainController;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.FileUtil;
import kr.spring.util.PagingUtil;

@Controller
@RequestMapping("/board")
public class BoardAjaxController {
    private final MainController mainController;
	private static final Logger log = 
		LoggerFactory.getLogger(BoardAjaxController.class);
	
	@Autowired
	private BoardService boardService;

    BoardAjaxController(MainController mainController) {
        this.mainController = mainController;
    }
	
	//부모글 업로드 파일 삭제
	@PostMapping("/deleteFile.do")
	@ResponseBody
	public Map<String,String> deleteFile(
			                 long board_num,
			                 HttpServletRequest request,
			                 HttpSession session){
		Map<String,String> mapAjax = 
				      new HashMap<String,String>();
		
		MemberVO user = 
				(MemberVO)session.getAttribute("user");
		if(user==null) {
			mapAjax.put("result","logout");
		}else {
			BoardVO db_board = 
					boardService.selectBoard(board_num);
			//로그인한 회원번호와 작성자 회원번호 일치 여부 체크
			if(user.getMem_num() != db_board.getMem_num()) {
				//불일치
				mapAjax.put("result", "wrongAccess");
			}else {
				//일치
				boardService.deleteFile(board_num);
				FileUtil.removeFile(request, 
						        db_board.getFilename());
				mapAjax.put("result", "success");
			}
		}		
		return mapAjax;
	}
	
	//부모글 좋아요 읽기
	@GetMapping("/getFav.do")
	@ResponseBody
	public Map<String,Object> getFav(
			             BoardFavVO fav,
			             HttpSession session){
		log.debug("<<게시판 좋아요>> : " + fav);
		
		Map<String,Object> mapAjax = 
				new HashMap<String,Object>();
		
		MemberVO user = (MemberVO)session.getAttribute("user");
		if(user==null) {
			mapAjax.put("status", "noFav");
		}else {
			//로그인한 회원번호 셋팅
			fav.setMem_num(user.getMem_num());
			
			BoardFavVO boardFav = 
					      boardService.selectFav(fav);
			if(boardFav!=null) {
				mapAjax.put("status", "yesFav");
			}else {
				mapAjax.put("status", "noFav");
			}
		}
		mapAjax.put("count", 
				boardService.selectFavCount(
						     fav.getBoard_num()));
		
		return mapAjax;
	}
	
	//부모글 좋아요 등록/삭제
	@PostMapping("/writeFav.do")
	@ResponseBody
	public Map<String,Object> writeFav(BoardFavVO fav,
			                     HttpSession session){
		log.debug("<<부모글 좋아요 등록/삭제>> : " + fav);
		
		Map<String,Object> mapAjax = 
				new HashMap<String, Object>();
		
		MemberVO user = 
				(MemberVO)session.getAttribute("user");
		if(user==null) {
			mapAjax.put("result", "logout");
		}else {
			//로그인한 회원번호 셋팅
			fav.setMem_num(user.getMem_num());
			
			BoardFavVO boardFav = 
					boardService.selectFav(fav);
			if(boardFav!=null) {
				//이미 등록되어 있으면 삭제
				boardService.deleteFav(fav);
				mapAjax.put("status", "noFav");
			}else {
				//등록되어있지 않으면 등록
				boardService.insertFav(fav);
				mapAjax.put("status", "yesFav");
			}
			mapAjax.put("result","success");
			mapAjax.put("count", 
					boardService.selectFavCount(
							 fav.getBoard_num()));
		}
		return mapAjax;
	}
	
	//댓글 등록
	@PostMapping("/writeReply.do")
	@ResponseBody
	public Map<String,String> writeReply(
			       BoardReplyVO vo,
			       HttpSession session,
			       HttpServletRequest request){
		log.debug("<<댓글 등록> : " + vo);
		
		Map<String,String> mapAjax = 
				new HashMap<String,String>();
		
		MemberVO user = 
				(MemberVO)session.getAttribute("user");
		if(user==null) {
			mapAjax.put("result", "logout");
		}else {
			//회원번호 저장
			vo.setMem_num(user.getMem_num());
			//ip 등록
			vo.setRe_ip(request.getRemoteAddr());
			//댓글 등록
			boardService.insertReply(vo);
			mapAjax.put("result", "success");
		}
		
		return mapAjax;
	}
	
	//댓글 목록
	@GetMapping("/listReply.do")
	@ResponseBody
	public Map<String,Object> getList(
			@RequestParam(defaultValue="1") int pageNum,
			   int rowCount,
			   int board_num,HttpSession session){
		
		log.debug("<<댓글 목록 - pageNum>> : " + pageNum);
		log.debug("<<댓글 목록 - board_num>> : " + board_num);
		
		Map<String,Object> map = 
				new HashMap<String,Object>();
		map.put("board_num", board_num);
		
		//글의 총개수
		int count = boardService.selectRowCountReply(map);
		
		//페이지 처리
		PagingUtil page = new PagingUtil(pageNum,count,
				                   rowCount,10,null);
		MemberVO memberVO = 
				(MemberVO)session.getAttribute("user");
				                   
		List<BoardReplyVO> list = null;
		if(count > 0) {
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			
			//댓글 좋아요 처리
			if(memberVO!=null) {
				map.put("mem_num", memberVO.getMem_num());
			}else {
				map.put("mem_num", 0);
			}
			list = boardService.selectListReply(map);
		}else {
			list = Collections.emptyList();
		}
		
		Map<String,Object> mapAjax = 
				new HashMap<String,Object>();
		mapAjax.put("count", count);
		mapAjax.put("list", list);
		if(memberVO!=null) {
			mapAjax.put("user_num", memberVO.getMem_num());
		}
		 
		return mapAjax;
	}
	
	//댓글 수정
	@PostMapping("/updateReply.do")
	@ResponseBody
	public Map<String,String> modifyReply(
			             BoardReplyVO vo,
			             HttpSession session,
			             HttpServletRequest request){
		log.debug("<<댓글 수정>> : " + vo);
		
		Map<String,String> mapAjax =
				new HashMap<String,String>();
		
		MemberVO user = 
				(MemberVO)session.getAttribute("user");
		if(user==null) {
			//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else if(user.getMem_num()==vo.getMem_num()) {
			//로그인 회원번호와 작성자 회원번호 일치
			
			//ip 등록
			vo.setRe_ip(request.getRemoteAddr());
			//댓글 수정
			boardService.updateReply(vo);
			mapAjax.put("result", "success");
		}else {
			//로그인 회원번호와 작성자 회원번호 불일치
			mapAjax.put("result", "wrongAccess");
		}
		
		return mapAjax;
	}
	
}








