package kr.spring.board.dao;

import java.util.List;
import java.util.Map;

import kr.spring.board.vo.BoardVO;

public interface BoardMapper {
	public void insertBoard(BoardVO board);
	public Integer selectBoardCount();
	//원래 인자가 한개만 넣을 수 있었기때문에 Map 으로 한 객체로 묶어버림(최근에야 여러 인자가능해짐)
	public List<BoardVO> selectBoardList(Map<String,Object> map);
	//초기에는 객체처리로 인해 Integer이 무조건이였으나 현재 int도 가능 - 상관 X
	public BoardVO selectBoard(Integer num); 
	public void updateBoard(BoardVO board);
	public void deleteBoard(Integer num);
}
