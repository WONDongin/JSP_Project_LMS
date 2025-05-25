<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- classList 수강 조회 오예록 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수강 조회</title>
</head>
<%!private static String[] s_period = { "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00" };
	private static String[] e_period = { "09:50", "10:50", "11:50", "12:50", "13:50", "14:50", "15:50", "16:50", "17:50" };%>
<body>
	<h2>수강 조회</h2>
	<table class="table table-bordered">
		<thead class="thead-light" style="text-align: center">
			<tr>
				<th style="width: 5%;">No</th>
				<th style="width: 8%;">강의코드</th>
				<th style="width: 15%;">강의명</th>
				<th style="width: 10%;">교수명</th>
				<th style="width: 5%;">학년</th>
				<th style="width: 8%;">이수학점</th>
				<th style="width: 15%;">강의시간</th>
				<th style="width: 10%;">강의실</th>
				<th style="width: 8%;">수강인원</th>
				<th style="width: 5%;">취소</th>
				<th style="width: 5%;">확정</th>
			</tr>
		</thead>
		<c:forEach var="cls" items="${classesList}" varStatus="stat">
			<fmt:formatDate value="${cls.s_date}" pattern="yyyy-MM-dd" var="sDate" />
		  	<fmt:formatDate value="${cls.e_date}" pattern="yyyy-MM-dd" var="eDate" />
			<tr class="text-center">
				<td>${stat.index + 1}</td>
				<td>${cls.class_no}</td>
				<td><a href="../classLMS/classInfo?class_no=${cls.class_no}&ban=${cls.ban}&year=${cls.year}&term=${cls.term}"> ${cls.class_name}</a></td>
				<td>${cls.prof}</td>
				<td>${cls.class_grade}</td>
				<td>${cls.credit}</td>

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
				<td>${cls.now_p}/${cls.max_p}</td>
				<td>
					<c:if test="${today > sDate && cls.status != 2}">
						<a href="dropClass?class_no=${cls.class_no}&ban=${cls.ban}&year=${cls.year}&term=${cls.term}" class="btn btn-outline-danger" onclick="return confirm('정말 수강 신청을 취소 하시겠습니까?');">취소</a>
					</c:if>
				</td>
				<td>
					<c:if test="${cls.status == 0}">
						<a href="confirmClass?class_no=${cls.class_no}&ban=${cls.ban}&year=${cls.year}&term=${cls.term}" class="btn btn-dark" onclick="return confirm('정말 수강 신청을 확정 하시겠습니까?');">확정</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>