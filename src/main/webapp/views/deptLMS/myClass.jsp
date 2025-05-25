<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- myClass 강의 관리 오예록 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>강의 관리</title>
<script type="text/javascript">
	function win_open_classList(page) {
		open(page, "", "width=1200, height=800, left=300, top=50");
	}
</script>
</head>
<%!private static String[] s_period = {"09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00",
			"17:00"};
	private static String[] e_period = {"09:50", "10:50", "11:50", "12:50", "13:50", "14:50", "15:50", "16:50",
			"17:50"};%>
<body>
	<c:if test="${not empty msg}">
		<script type="text/javascript">
			alert('${msg}');
		</script>
	</c:if>
	<div class="d-flex justify-content-between align-items-center mb-2">
		<h2>강의 관리</h2>
		<div class="ml-auto btn-group" role="group">
			<button type="button" class="btn btn-light btn-outline-secondary" onclick="win_open_classList('../deptLMS/addClassList')">전체 강의 목록</button>
			<button type="button" class="btn btn-light btn-outline-secondary" onclick="location.href='addClass'">강의 추가</button>
		</div>
	</div>
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
				<th style="width: 8%;">수정/삭제</th>
			</tr>
		</thead>
		<c:forEach var="cls" items="${classesList}" varStatus="stat">
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
					<td><a href="updateClass?class_no=${cls.class_no}&ban=${cls.ban}&year=${cls.year}&term=${cls.term}" class="btn btn-dark">수정</a> <a
							href="deleteClass?class_no=${cls.class_no}&ban=${cls.ban}&year=${cls.year}&term=${cls.term}" class="btn btn-outline-danger" onclick="return confirm('정말 이 강의를 삭제하시겠습니까?');"
						>삭제</a></td>
				</tr>
		</c:forEach>
	</table>

</body>

</html>