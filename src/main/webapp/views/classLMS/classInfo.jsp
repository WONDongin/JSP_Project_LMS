<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 원동인 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>강의 계획서</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
	<jsp:include page="classTopTable.jsp" />

	<h3 class="my-4">강의 계획서</h3>
	<div class="d-flex mb-4" style="gap: 10px">
		<table class="table table-bordered col-sm-4 mb-0">
			<tr>
				<th style="background-color: #eee;">파일</th>
				<c:if test="${!empty class1.file or class1.file.trim().equals('')}">
				<td>${class1.class_name}_강의계획서${ext}</td> </c:if>
			</tr>
		</table>
		<a class="btn btn-dark d-flex align-items-center" href="../files/${class1.file}" download="${class1.class_name}_강의계획서${ext}"> 다운로드 </a>
	</div>
	<textarea class="form-control" style="resize: none;" rows="20" readonly>${class1.c_plan}</textarea>
</body>
</html>