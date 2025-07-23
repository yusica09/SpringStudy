package kr.spring.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.board.vo.BoardFavVO;
import kr.spring.board.vo.BoardReplyVO;
import kr.spring.board.vo.BoardVO;

public interface BoardMapper {
	//부모글
	public List<BoardVO> selectList(Map<String,Object> map);
	//@Param 어노테이션을 이용한 다중 파라미터 처리
	public Integer selectRowCount(@Param("keyfield") String keyfield, 
			                      @Param("keyword") String keyword);
	/* MyBatis는 null 파라미터 전달 시 JDBC 타입을 모르면 오류 발생
	 * Java 타입			JDBC 타입(jdbcType)
	 * int,Integer		INTEGER
	 * long,LONG		BIGINT
	 * String			VARCHAR
	 * boolean,Boolean	BOOLEAN
	 * Date,LocalDate등	DATE,TIMESTAMP 등
	 */
	@Insert("INSERT INTO slpboard (board_num,title,content,filename,ip,mem_num) VALUES (slpboard_seq.nextval,#{title},#{content},#{filename,jdbcType=VARCHAR},#{ip},#{mem_num})")
	public void insertBoard(BoardVO board);
	@Select("SELECT * FROM slpboard JOIN slpmember USING(mem_num) WHERE board_num=#{board_num}")
	public BoardVO selectBoard(Long board_num);
	@Update("UPDATE slpboard SET hit=hit+1 WHERE board_num=#{board_num}")
	public void updateHit(Long board_num);
	public void updateBoard(BoardVO board);
	@Update("UPDATE slpboard SET filename='' WHERE board_num=#{board_num}")
	public void deleteFile(Long board_num);
	@Delete("DELETE FROM slpboard WHERE board_num=#{board_num}")
	public void deleteBoard(Long board_num);
	//부모글 좋아요
	@Select("SELECT * FROM slpboard_fav WHERE board_num=#{board_num} and mem_num=#{mem_num}")
	public BoardFavVO selectFav(BoardFavVO fav);
	@Select("SELECT COUNT(*) FROM slpboard_fav WHERE board_num=#{board_num}")
	public Integer selectFavCount(Long board_num);
	@Insert("INSERT INTO slpboard_fav (board_num,mem_num) VALUES (#{board_num},#{mem_num})")
	public void insertFav(BoardFavVO fav);
	@Delete("DELETE FROM slpboard_fav WHERE board_num=#{board_num} AND mem_num=#{mem_num}")
	public void deleteFav(BoardFavVO fav);
	public void deleteFavByBoardNum(Long board_num);
	//댓글
	public List<BoardReplyVO> selectListReply(Map<String,Object> map);
	@Select("SELECT COUNT(*) FROM slpboard_reply WHERE board_num=#{board_num}")
	public Integer selectRowCountReply(Map<String,Object> map);
	@Insert("INSERT INTO slpboard_reply (re_num,re_content,re_ip,board_num,mem_num) VALUES (slpreply_seq.nextval,#{re_content},#{re_ip},#{board_num},#{mem_num})")
	public void insertReply(BoardReplyVO boardReply);
	@Update("UPDATE slpboard_reply SET re_content=#{re_content},re_ip=#{re_ip},re_mdate=SYSDATE WHERE re_num=#{re_num}")
	public void updateReply(BoardReplyVO boardReply);
	public void deleteReply(Long re_num);
	public void deleteReplyByBoardNum(Long board_num);
	
	//댓글 좋아요
}











