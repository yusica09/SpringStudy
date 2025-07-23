<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<ul>
	<li>
		<a href="${pageContext.request.contextPath}/board/list.do">게시판</a>
	</li>
	<c:if test="${!empty user && user.auth == 9}">
	<li>
		<a href="${pageContext.request.contextPath}/member/admin_list.do">회원관리</a>
	</li>
	</c:if>
	<c:if test="${!empty user}">
	<li>
		<a href="${pageContext.request.contextPath}/member/myPage.do">MY페이지</a>
	</li>
	</c:if>
</ul>