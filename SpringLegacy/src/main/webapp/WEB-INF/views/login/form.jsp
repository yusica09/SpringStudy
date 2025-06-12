<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
<form:form action="login.do" modelAttribute="loginVO">
	<!-- 기본적으로 span 태그에 에러메시지를 명시하는데
			element="div" 로 명시하면 div 태그에 에러메시지 표시 -->
	<form:errors element="div"/>
	아이디 : <form:input path="userId"/>
			   <form:errors path="userId"/><br>
	비밀번호 : <form:input path="password"/>
			   	 <form:errors path="password"/><br>
	<form:button>전송</form:button>
</form:form>
</body>
</html>