package kr.spring.board.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kr.spring.board.vo.BoardVO;

@Repository
public class BoardMapperImpl implements BoardMapper{

	private static final String INSERT_SQL = "INSERT INTO aboard (num,writer,title,passwd,content,reg_date) VALUES (aboard_seq.nextval,?,?,?,?,SYSDATE)";
	private static final String SELECT_COUNT_SQL = "SELECT COUNT(*) FROM aboard";
	private static final String SELECT_LIST_SQL =
			"SELECT * FROM (SELECT a.*, rownum rnum "
			+ "FROM (SELECT * FROM aboard ORDER BY "
			+ "reg_date DESC)a) "
			+ "WHERE rnum >= ? AND rnum <= ?";
	private static final String SELECT_DETAIL_SQL = "SELECT * FROM aboard WHERE num=?";
	private static final String UPDATE_SQL = "UPDATE aboard SET writer=?,title=?,content=? WHERE num=?";
	private static final String DELETE_SQL = "DELETE FROM aboard WHERE num=?";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public void insertBoard(BoardVO board) {
		sqlSession.insert("insertBoard", board);
		
	}
	@Override
	public Integer selectBoardCount() {
		//mapper xml에 명시된 SQL 식별 아이디
		return sqlSession.selectOne("selectBoardCount");
	}
	@Override
	public List<BoardVO> selectBoardList(Map<String, Object> map) {
		return sqlSession.selectList("selectBoardList",map);
	}
	@Override
	public BoardVO selectBoard(Integer num) {
		return sqlSession.selectOne("selectBoard",num);
	}
	@Override
	public void updateBoard(BoardVO board) {
		sqlSession.update("updateBoard",board);
		
	}
	@Override
	public void deleteBoard(Integer num) {
		sqlSession.delete("deleteBoard", num);
		
	}
	
	

}
