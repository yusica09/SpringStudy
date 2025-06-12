<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>report 등록 폼</title>
</head>
<body>
<form:form action="submitReport.do" enctype="multipart/form-data" modelAttribute="report">
	<ul>
		<li>
			<form:label path="subject">주제</form:label>
			<form:input path="subject"/>
			<form:errors path="subject"/>
		</li>
		<li>
			<form:label path="reportFile">리포트파일</form:label>
			<input type="file" id="reportFile" name="reportFile">
			<form:errors path="reportFile"/>
		</li>
		<li>
			<form:button>리포트 전송</form:button>
		</li>
	</ul>
</form:form>
</body>
</html>