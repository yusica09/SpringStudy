package kr.spring.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.board.vo.BoardVO;

public interface BoardMapper {
	@Insert("INSERT INTO aboard (num,writer,title,passwd, content, reg_date) VALUES (aboard_seq.nextval, #{writer}, #{title}, #{passwd}, #{content}, SYSDATE)")
	public void insertBoard(BoardVO board);
	@Select("SELECT COUNT(*) FROM aboard")
	public Integer selectBoardCount();
	public List<BoardVO> selectBoardList(Map<String, Object> map);
	@Select("SELECT * FROM aboard WHERE num=#{num}")
	public BoardVO selectBoard(Integer num);
	@Update("UPDATE aboard SET writer=#{writer}, title=#{title}, content=#{content} WHERE num=#{num}")
	public void updateBoard(BoardVO board);
	@Delete("DELETE FROM aboard WHERE num=#{num}")
	public void deleteBoard(Integer num);
}
