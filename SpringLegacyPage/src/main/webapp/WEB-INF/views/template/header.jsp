<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 상단 시작 -->
<h2 class="align-center">회원제 게시판</h2>
<div class="align-right">
	<%-- 프로필 사진 표시 --%>
	<c:if test="${!empty user && !empty user.photo}">
	<img alt="프사" src="${pageContext.request.contextPath}/member/photoView.do" width="25" height="25" class="my-photo">
	</c:if>
	<c:if test="${!empty user && empty user.photo}">
	<img alt="기본프사" src="${pageContext.request.contextPath}/resources/images/face.png" width="25" height="25" class="my-photo">
	</c:if>
	<%-- 아이디 또는 별명 표시 --%>
	<c:if test="${!empty user && !empty user.nick_name}">
	[<span class="user_name">${user.nick_name}</span>]
	</c:if>
	<c:if test="${!empty user && empty user.nick_name}">
	[<span class="user_name">${user.id}</span>]
	</c:if>
	
	<c:if test="${!empty user}">
	<a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
	</c:if>
	<c:if test="${empty user}">
	<a href="${pageContext.request.contextPath}/member/registerUser.do">회원가입</a>
	<a href="${pageContext.request.contextPath}/member/login.do">로그인</a>
	</c:if>
	<a href="${pageContext.request.contextPath}/main/main.do">홈으로</a>
</div>
<!-- 상단 끝 -->






































