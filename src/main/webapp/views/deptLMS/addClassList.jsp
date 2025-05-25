<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- addClassList 수강신청 오예록 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>전체 강의</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<style>
@font-face {
	font-family: 'GmarketSansMedium';
	src:
		url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/GmarketSansMedium.woff')
		format('woff');
	font-weight: normal;
	font-style: normal;
}

* {
	margin: 0;
	padding: 0;
	font-family: 'GmarketSansMedium';
	font-weight: 500;
	letter-spacing: -0.05rem;
	font-size: 16px;
	color: #333;
	box-sizing: border-box;
}

a {
	display: block;
	color: #333;
}

img {
	display: block;
	max-width: 100%;
	margin: 0 auto;
}

.table td, .table th {
	vertical-align: middle !important;
}

.table tr td {
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	max-width: 200px;
}

.btn-dark {
	color: #fff !important;
}

.fw_b {
	font-weight: 600;
}

.nav-link {
	padding: 0;
	cursor: pointer;
	font-weight: 500;
	color: #333;
}

.nav-link:hover, .nav-link:hover span {
	color: #333;
	font-weight: 600;
}

.nav-link span {
	text-decoration: underline;
}

.main_menu li {
	height: 100px;
	line-height: 100px;
	border-bottom: 1px solid #eee;
}

.main_menu li a {
	display: block;
	font-size: 20px;
	font-weight: 600;
	text-decoration: none;
	width: 100%;
	height: 100%;
	background-color: #fff;
	color: #333;
}

.main_menu li a:hover, .main_menu li a:focus {
	background-color: #343a40;
	color: #fff !important;
	transition: 0.3s ease-out;
}

footer ul li p {
	font-size: 14px;
	line-height: 1;
	font-weight: 500;
	color: #eee;
}
</style>
</head>
<%!private static String[] s_period = { "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00" };
	private static String[] e_period = { "09:50", "10:50", "11:50", "12:50", "13:50", "14:50", "15:50", "16:50", "17:50" };%>
<body>
	<div class="container mt-4 mb-4">
		<h2>전체 강의</h2>
		<form action="addClassList" method="post" style="display: flex; gap: 8px; align-items: center;">
			<select name="type" class="form-control" style="width: 150px">
				<option value="class_name">강의명</option>
				<option value="user_name">교수명</option>
			</select>
			<input type="text" name="find" placeholder="Search" value="${param.find}" class="form-control" style="width: 230px;">
			<button type="submit" class="btn btn-light btn-outline-secondary">검색</button>
		</form>
		<br>
		<table class="table table-bordered">
			<thead class="thead-light" style="text-align: center">
				<tr>
					<th style="width: 5%;">No</th>
					<th style="width: 10%;">강의코드</th>
					<th style="width: 15%;">강의명</th>
					<th style="width: 10%;">교수명</th>
					<th style="width: 5%;">학년</th>
					<th style="width: 8%;">이수학점</th>
					<th style="width: 8%;">년도-학기</th>
					<th style="width: 15%;">강의시간</th>
					<th style="width: 10%;">강의실</th>
				</tr>
			</thead>
			<%-- <c:forEach var="classesList" items="${classesList}"></c:forEach> --%>

			<c:forEach var="cls" items="${classesList}" varStatus="stat">
				<fmt:formatDate value="${cls.s_date}" pattern="yyyy-MM-dd" var="sDate" />
				<fmt:formatDate value="${cls.e_date}" pattern="yyyy-MM-dd" var="eDate" />
				<tr class="text-center">
					<td>${stat.index + 1}</td>
					<td>${cls.class_no}</td>
					<td>${cls.class_name}- ${cls.ban}</td>
					<td>${cls.prof}</td>
					<td>${cls.class_grade}</td>
					<td>${cls.credit}</td>
					<td>${cls.year}-${cls.term}</td>
					<td><c:forEach var="d" items="${cls.days}">
							<c:choose>
								<c:when test="${d == 0}">월 </c:when>
								<c:when test="${d == 1}">화 </c:when>
								<c:when test="${d == 2}">수 </c:when>
								<c:when test="${d == 3}">목 </c:when>
								<c:when test="${d == 4}">금 </c:when>
							</c:choose>
						</c:forEach> <br> <%=s_period[((models.classes.Class1) pageContext.getAttribute("cls")).getS_time() - 1]%> ~ <%=e_period[((models.classes.Class1) pageContext.getAttribute("cls")).getE_time() - 1]%></td>
					<td>${cls.classroom}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>