package kr.spring.member.service;

import java.util.List;
import java.util.Map;

import kr.spring.member.vo.MemberVO;

public interface MemberService {
	//회원관리 - 일반회원
	//회원가입
	public void insertMember(MemberVO member);
	//아이디 중복 및 로그인 체크
	public MemberVO selectCheckMember(String id);
	//회원상세
	public MemberVO selectMember(Long mem_num);
	//회원 정보 수정
	public void updateMember(MemberVO member);
	//비밀번호 수정
	public void updatePassword(MemberVO member);
	//회원탈퇴
	public void deleteMember(Long mem_num);
	//프로필 이미지 업데이트
	public void updateProfile(MemberVO member);

	//회원관리 - 관리자
	//회원목록
	public Integer selectRowCount(Map<String,Object> map);
	public List<MemberVO> selectList(Map<String,Object> map);
	//회원 권한 및 정보 수정
	public void updateByAdmin(MemberVO member);
}








