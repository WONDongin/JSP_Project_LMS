<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 이동원 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학점 관리</title>
</head>
<body>
	<jsp:include page="classTopTable.jsp" />
	<h2>학점 관리</h2>
	<table class="table table-bordered">
		<thead class="thead-light">
			<tr>
				<th>번호</th>
				<th>학번</th>
				<th>학년</th>
				<th>이름</th>
				<th>중간고사(30)</th>
				<th>기말고사(40)</th>
				<th>과제총점(20)</th>
				<th>출석(10)</th>
				<th>총점(100)</th>
				<th>학점</th>
			</tr>
		</thead>

		<c:set var="i" value="1" />
		<c:forEach var="ru" items="${reg_users}" varStatus="stat">
			<tr>
				<td>${stat.index + 1}</td>
				<td>${ru.user_no}</td>
				<td>${ru.user_grade}</td>
				<td>${ru.user_name}</td>
				<td>${ru.exam1_score}</td>
				<td>${ru.exam2_score}</td>
				<td>${ru.as_tot_score}</td>
				<td>${ru.att_score}</td>
				<c:set var="tot_score" value="${ru.as_tot_score + ru.exam1_score + ru.exam2_score + ru.att_score}" />
				<td>${tot_score}</td>
 				<td><c:choose>
					<c:when test="${tot_score >= 95}">A+<</c:when>
					<c:when test="${tot_score >= 90}">A0<</c:when>
					<c:when test="${tot_score >= 85}">B+</c:when>
					<c:when test="${tot_score >= 80}">B0</c:when>
					<c:when test="${tot_score >= 75}">C+</c:when>
					<c:when test="${tot_score >= 70}">C0</c:when>
					<c:when test="${tot_score >= 65}">D+</c:when>
					<c:when test="${tot_score >= 60}">D0</c:when>
					<c:otherwise>F</c:otherwise>
				</c:choose></td>
			</tr>
		</c:forEach>
	</table>
	<div style="display: flex; justify-content: flex-end; gap: 10px;">
		<a href="download_stXLSX" class="btn btn-dark">양식 다운로드</a>&nbsp;&nbsp;&nbsp;
		<form action="upload_stCSV" method="post" enctype="multipart/form-data">
			<button type="submit" onclick="if(!this.form.file.value) { alert('파일을 선택하세요.'); return false; }" class="btn btn-dark">
	  		csv 업로드</button>
	  	<input type="file" name="file" accept=".csv" />
		</form>
	</div>
</body>
</html>