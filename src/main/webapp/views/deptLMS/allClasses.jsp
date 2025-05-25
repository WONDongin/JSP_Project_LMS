<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%-- 이동원 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자용 모든 강의 조회</title>
</head>
<%!private static String[] s_period = {"09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00",
			"17:00"};
	private static String[] e_period = {"09:50", "10:50", "11:50", "12:50", "13:50", "14:50", "15:50", "16:50",
			"17:50"};%>
<body>
<table class="table table-bordered">
		<thead class="thead-light" style="text-align: center;">
			<tr>
				<th style="width: 3%;">No</th>
				<th style="width: 5%;">강의코드</th>
				<th style="width: 18%;">강의명</th>
				<th style="width: 8%;">교수명</th>
				<th style="width: 5%;">학년</th>
				<th style="width: 8%;">이수학점</th>
				<th style="width: 10%;">강의시간</th>
        		<th style="width: 5%;">강의실</th>
				<th style="width: 8%;">정원</th>
				<th style="width: 11%;">개강</th>
				<th style="width: 11%;">종강</th>
			</tr>
		</thead>
		<c:forEach var="cls" items="${classList}" varStatus="stat">
			<fmt:formatDate value="${cls.s_date}" pattern="yyyy-MM-dd" var="sDate" />
			<fmt:formatDate value="${cls.e_date}" pattern="yyyy-MM-dd" var="eDate" />
				<tr class="text-center">
					<td>${stat.index + 1}</td>
					<td>${cls.class_no}</td>
					<td><a href="../classLMS/classInfo?class_no=${cls.class_no}&ban=${cls.ban}&year=${cls.year}&term=${cls.term}"> ${cls.class_name}</a></td>
					<td>${login.user_name}</td>
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
					<td>${sDate}</td>
					<td>${eDate}</td>
				</tr>
		</c:forEach>
	</table>
</body>
</html>