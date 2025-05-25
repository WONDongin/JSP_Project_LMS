<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath }"/>

<%-- 원동인 --%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>구디 대학교 학사관리 시스템</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
	    li{list-style: none;}
	    .di_btn{text-align: center;padding-top: 2px;color: #fff;width: 50px;border-radius: 100px;background-color: #343a40;font-size: 12px;}
	    .main_list { min-width:600px; max-height:400px; background-color:#fff; border:1px solid #eee; border-radius:10px; display:flex; flex-direction:column; }
		.content-scroll { overflow-y:auto; flex-grow:1; padding-right:5px; }
	    .main_list .list-group-item{border: none; border-bottom: 1px solid #eee;}
	    .main_list .list-group-item:last-child{border: none;}
	    .main_list .list-group a{color:#333;}
    </style>
</head>
<%!private static String[] s_period = { "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00" };
	private static String[] e_period = { "09:50", "10:50", "11:50", "12:50", "13:50", "14:50", "15:50", "16:50", "17:50" };%>
<body>
	<div class="row" style="justify-content: center; gap: 10px;">
	    <div class="main_list col-sm-5 px-4 py-4 content-scroll">
	        <h3 class="mb-2 fw_b">공지사항</h3>
	        <ul class="list-group list-group-flush">
	            <%--  <c:forEach var="m" items="${list}"> --%>
	                <li class="list-group-item px-0">
	                    <a href="#" class="d-flex justify-content-between align-items-center" style="width:100%;">
	                    	2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)<span class="di_btn">></span>
	                    </a>
	                </li>
	                <li class="list-group-item px-0">
	                    <a href="#" class="d-flex justify-content-between align-items-center" style="width:100%;">
	                    	2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)<span class="di_btn">></span>
	                    </a>
	                </li>
	                <li class="list-group-item px-0">
	                    <a href="#" class="d-flex justify-content-between align-items-center" style="width:100%;">
	                    	2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)<span class="di_btn">></span>
	                    </a>
	                </li>
	                <li class="list-group-item px-0">
	                    <a href="#" class="d-flex justify-content-between align-items-center" style="width:100%;">
	                    	2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)<span class="di_btn">></span>
	                    </a>
	                </li>
	                <li class="list-group-item px-0">
	                    <a href="#" class="d-flex justify-content-between align-items-center" style="width:100%;">
	                    	2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)<span class="di_btn">></span>
	                    </a>
	                </li>
	             <%-- </c:forEach> --%>
	        </ul>
	    </div>

        <div class="main_list col-sm-5 px-4 py-4 content-scroll">
            <h3 class="mb-2 fw_b">캘린더 
            	<c:if test="${login.role == 3}">
            		<a href="${path}/mainLMS/event" class="btn btn-dark" role="button">등록</a>
            	</c:if>
            </h3>
            <form id="calendarForm" method="post" action="main">
			    <input type="hidden" name="year" id="year" value="${year}">
			    <input type="hidden" name="month" id="month" value="${month}">
			</form>
            <div class="d-flex justify-content-between align-items-center mb-3">
		        <button type="button" class="btn btn-outline-secondary" onclick="changeMonth(-1)">이전달</button>
		        <h2>${year}년 ${month}월</h2>
		        <button type="button" class="btn btn-outline-secondary" onclick="changeMonth(1)">다음달</button>
    		</div>	
			<table class="table">
		        <thead>
		            <tr>
		                <th>일</th><th>월</th><th>화</th><th>수</th><th>목</th><th>금</th><th>토</th>
		            </tr>
		        </thead>
		        <tbody>
		            <c:forEach var="cell" items="${calendarCells}" varStatus="status">
		                <c:if test="${status.index % 7 == 0}">
		                    <tr>
		                </c:if>
		                <td>
		                    <c:if test="${cell.date != null}">
		                        <div><strong>${cell.date}</strong></div>
		                        <c:forEach var="event" items="${cell.events}">
		                            <div>${event.event_name}</div>
		                        </c:forEach>
		                    </c:if>
		                </td>
		                <c:if test="${status.index % 7 == 6}">
		                    </tr>
		                </c:if>
		            </c:forEach>
		        </tbody>
		    </table>
        </div>
		<c:if test="${login.role != 3}">
	        <div class="main_list col-sm-5 px-4 py-4 content-scroll">
	            <h3 class="mb-2 fw_b">강의목록</h3>
	            <table class="table">
	                <!-- 메인 강의목록(학생)  -->
	                <c:if test="${login.role == 1}">
	                <thead class="thead-light">
	                    <tr>
							<th class="text-center">강의실</th>
							<th class="text-center">강의명</th>
							<th class="text-center">강의시간</th>
							<th class="text-center">교수명</th>
	                    </tr>
	                </thead>
	                <c:forEach var="cls" items="${classesList_main_s}" varStatus="stat">
						<tr class="text-center">
							<td>${cls.classroom}</td>
							<td><a href="../classLMS/classInfo?class_no=${cls.class_no}&ban=${cls.ban}&year=${cls.year}&term=${cls.term}"> ${cls.class_name}</a></td>
							<td>${cls.prof}</td>
							<td>
							<c:forEach var="d" items="${cls.days}">
								<c:choose>
									<c:when test="${d == 0}">월 </c:when>
									<c:when test="${d == 1}">화 </c:when>
									<c:when test="${d == 2}">수 </c:when>
									<c:when test="${d == 3}">목 </c:when>
									<c:when test="${d == 4}">금 </c:when>
								</c:choose>
							</c:forEach>&nbsp;
							<br>
							<%=s_period[((models.classes.Class1) pageContext.getAttribute("cls")).getS_time() - 1]%> ~ <%=e_period[((models.classes.Class1) pageContext.getAttribute("cls")).getE_time() - 1]%>
							</td>
						</tr>
					</c:forEach>
					</c:if>				
					<!-- 메인 강의목록(교수)  -->
	                <c:if test="${login.role == 2}">
	                <thead class="thead-light">
	                    <tr>
							<th class="text-center">강의실</th>
							<th class="text-center">강의명</th>
							<th class="text-center">강의시간</th>			
	                    </tr>
	                </thead>
	                <c:forEach var="cls" items="${classesList_main_p}" varStatus="stat">
						<tr class="text-center">
							<td>${cls.classroom}</td>
							<td><a href="../classLMS/classInfo?class_no=${cls.class_no}&ban=${cls.ban}&year=${cls.year}&term=${cls.term}"> ${cls.class_name}</a></td>
							<td>
							<c:forEach var="d" items="${cls.days}">
								<c:choose>
									<c:when test="${d == 0}">월 </c:when>
									<c:when test="${d == 1}">화 </c:when>
									<c:when test="${d == 2}">수 </c:when>
									<c:when test="${d == 3}">목 </c:when>
									<c:when test="${d == 4}">금 </c:when>
								</c:choose>
							</c:forEach>&nbsp;
							<br>
							<%=s_period[((models.classes.Class1) pageContext.getAttribute("cls")).getS_time() - 1]%> ~ <%=e_period[((models.classes.Class1) pageContext.getAttribute("cls")).getE_time() - 1]%>
							</td>
						</tr>
					</c:forEach>
					</c:if>
	            </table>
	        </div>
	        <div class="main_list col-sm-5 px-4 py-4 content-scroll">
	            <h3 class="mb-2 fw_b">과제목록</h3>
	            <c:if test="${login.role == 1}">
		            <table class="table">
		                <thead class="thead-light">
		                    <tr>
		                    
		                        <th class="fw_b text-center">과재명</th>
		                        <th class="fw_b text-center">과제내용</th>
		                    	<th class="fw_b text-center">제출 기한</th>
		                    </tr>
		                </thead>
		               <c:forEach var="assignmentMap_main" items="${assignmentMap_main}">
						  <tbody>
						    <c:forEach var="ass_main" items="${assignmentMap_main.value}">
						      <tr>
						        <td class="text-center">${ass_main.as_name}</td>
						        <td class="text-center">${ass_main.as_content}</td>
						        <td class="text-center">
						          <fmt:formatDate value="${ass_main.as_s_date}" pattern="yyyy/MM/dd" /> ~ 
						          <fmt:formatDate value="${ass_main.as_e_date}" pattern="yyyy/MM/dd" />
						        </td>
						      </tr>
						    </c:forEach>
						  </tbody>
						</c:forEach>
		            </table>
	            </c:if>
	            
	            <c:if test="${login.role == 2}">
		            <table class="table">
		                <thead class="thead-light">
		                    <tr>
		                        <th class="fw_b text-center">과재명</th>
		                        <th class="fw_b text-center">과제내용</th>
		                    	<th class="fw_b text-center">마감 기한</th>
		                    </tr>
		                </thead>
						  <tbody>
						    <c:forEach var="ass_prof" items="${assignmentMap_prof}">
						      <tr>
						        <td class="text-center">${ass_prof.as_name}</td>
						        <td class="text-center">${ass_prof.as_content}</td>
						        <td class="text-center">
						          <fmt:formatDate value="${ass_prof.as_s_date}" pattern="yyyy/MM/dd" /> ~ 
						          <fmt:formatDate value="${ass_prof.as_e_date}" pattern="yyyy/MM/dd" />
						        </td>
						      </tr>
						    </c:forEach>
						  </tbody>
		            </table>
	            </c:if>
	            
	        </div>
        </c:if>
    </div>
    
    <script>
    	// year, month 값 계산
		function changeMonth(offset) {
		    let year = +document.getElementById("year").value;
		    let month = +document.getElementById("month").value + offset;
		
		    if (month < 1) { month = 12; year--; }
		    else if (month > 12) { month = 1; year++; }
		
		    document.getElementById("year").value = year;
		    document.getElementById("month").value = month;
		    document.getElementById("calendarForm").submit();
		}
	</script>
</body>
</html>
