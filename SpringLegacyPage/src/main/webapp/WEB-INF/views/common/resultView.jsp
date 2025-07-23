<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${accessTitle}</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/layout.css">
</head>
<body>
<div class="page-one">
	<h2>${accessTitle}</h2>
	<div class="result-display">
		<div class="align-center">
			${accessMsg}
			<p>
			<button onclick="location.href='${accessUrl}'">${accessBtn}</button>
		</div>
	</div>
</div>
</body>
</html>


































