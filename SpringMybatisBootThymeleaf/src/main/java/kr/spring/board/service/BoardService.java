package kr.spring.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.spring.board.vo.BoardVO;

@Service
public interface BoardService {
	public void insertBoard(BoardVO board);
	public Integer getBoardCount();
	public List<BoardVO> getBoardList(Map<String,Integer> map);
	public BoardVO getBoard(Long num);
	public void updateBoard(BoardVO board);
	public void deleteBoard(Long num);
}
