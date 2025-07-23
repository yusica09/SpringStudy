package kr.spring.member.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.member.vo.MemberVO;

public interface MemberMapper {
	//회원관리 - 일반회원
	//회원가입
	@Select("SELECT slpmember_seq.nextval FROM dual")
	public Long selectMem_num();
	@Insert("INSERT INTO slpmember (mem_num,id) VALUES (#{mem_num},#{id})")
	public void insertMember(MemberVO member);
	@Insert("INSERT INTO slpmember_detail (mem_num,name,passwd,phone,email,zipcode,address1,address2) VALUES (#{mem_num},#{name},#{passwd},#{phone},#{email},#{zipcode},#{address1},#{address2})")
	public void insertMember_detail(MemberVO member);
	//아이디 중복 및 로그인 체크
	@Select("SELECT mem_num,id,auth,passwd,nick_name,photo,email FROM slpmember LEFT OUTER JOIN slpmember_detail USING(mem_num) WHERE id=#{id}")
	public MemberVO selectCheckMember(String id);
	//회원상세
	@Select("SELECT * FROM slpmember JOIN slpmember_detail USING(mem_num) WHERE mem_num=#{mem_num}")
	public MemberVO selectMember(Long mem_num);
	//회원 정보 수정
	@Update("UPDATE slpmember SET nick_name=#{nick_name} WHERE mem_num=#{mem_num}")
	public void updateNick_name(MemberVO member);
	@Update("UPDATE slpmember_detail SET name=#{name},phone=#{phone},email=#{email},zipcode=#{zipcode},address1=#{address1},address2=#{address2},modify_date=SYSDATE WHERE mem_num=#{mem_num}")
	public void updateMember(MemberVO member);
	//비밀번호 수정
	@Update("UPDATE slpmember_detail SET passwd=#{passwd} WHERE mem_num=#{mem_num}")
	public void updatePassword(MemberVO member);
	//회원탈퇴
	@Update("UPDATE slpmember SET auth=0 WHERE mem_num=#{mem_num}")
	public void deleteMember(Long mem_num);
	@Delete("DELETE FROM slpmember_detail WHERE mem_num=#{mem_num}")
	public void deleteMember_detail(Long mem_num);
	//프로필 이미지 업데이트
	@Update("UPDATE slpmember_detail SET photo=#{photo},photo_name=#{photo_name} WHERE mem_num=#{mem_num}")
	public void updateProfile(MemberVO member);
	
	//회원관리 - 관리자
	//회원목록
	public Integer selectRowCount(Map<String,Object> map);
	public List<MemberVO> selectList(Map<String,Object> map);
	//회원 권한 수정
	@Update("UPDATE slpmember SET auth=#{auth} WHERE mem_num=#{mem_num}")
	public void updateByAdmin(MemberVO member);
	//회원 정보 수정
	@Update("UPDATE slpmember_detail SET name=#{name},phone=#{phone},email=#{email},zipcode=#{zipcode},address1=#{address1},address2=#{address2} WHERE mem_num=#{mem_num}")
	public void updateDetailByAdmin(MemberVO member);
	
}











