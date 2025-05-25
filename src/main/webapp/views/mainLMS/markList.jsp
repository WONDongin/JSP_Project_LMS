<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- markList 오예록 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${login.user_name}님학점조회</title>
</head>
<body>
	<h2>${login.user_name}님학점조회</h2>
	<form action="markList" method="get" style="display: flex; gap: 8px; align-items: center;">
		<select name="type" class="form-control" style="width: 150px;">
			<option value="class_name">강의명</option>
			<option value="user_name">교수명</option>
			<option value="year_term">수강학기</option>
		</select>
		<input type="text" name="fine" value="${fine}" placeholder="Search" class="form-control" style="width: 230px;">
		<button type="submit" class="btn btn-light btn-outline-secondary">검색</button>
	</form>
	<br>
	<table class="table table-bordered" style="text-align: center">
		<thead class="thead-light" >
			<tr>
				<th>번호</th>
				<th>수강학기</th>
				<th>강의코드-반</th>
				<th>강의명</th>
				<th>교수</th>
				<th>이수학점</th>
				<th>이수학점</th>
				<th>학점</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="cls" items="${classesList}" varStatus="stat">
				<tr>
					<td>${stat.index+1}</td>
					<td>${cls.year}-${cls.term}</td>
					<td>${cls.class_no}-${cls.ban}</td>
					<td>${cls.class_name}</td>
					<td>${cls.prof}</td>
					<td>${cls.credit}</td>
					<td>${cls.mark}</td>
					<td><c:choose>
					<c:when test="${cls.mark >= 95}">A+</c:when>
					<c:when test="${cls.mark >= 90}">A0<</c:when>
					<c:when test="${cls.mark >= 85}">B+</c:when>
					<c:when test="${cls.mark >= 80}">B0</c:when>
					<c:when test="${cls.mark >= 75}">C+</c:when>
					<c:when test="${cls.mark >= 70}">C0</c:when>
					<c:when test="${cls.mark >= 65}">D+</c:when>
					<c:when test="${cls.mark >= 60}">D0</c:when>
					<c:otherwise>F</c:otherwise>
				</c:choose></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</body>
</html>