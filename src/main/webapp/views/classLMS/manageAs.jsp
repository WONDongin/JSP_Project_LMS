<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- 원동인 --%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>과제 관리</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	</head>
	<body>
		<jsp:include page="classTopTable.jsp" />
	
		<h3 class="my-4">컴퓨터 공학과 과제관리</h3>
        		
		<h3 class="my-4">과제 목록
			<a href="addAssignmentForm" class="btn btn-dark" role="button">과제추가</a>
			</h3>
	
		<table class="table table-bordered"> 
			<thead class="thead-light">
				<tr>
					<th style="text-align: center">과제명</th>
					<th style="text-align: center">기한</th>
					<th style="text-align: center">과제내용</th>
					<th style="text-align: center">배점</th>
					<th style="text-align: center">제출현황</th>
					<th style="text-align: center">수정/삭제</th>
				</tr>
			</thead>
			

			<c:forEach var="as" items="${asList}">
			<tbody>
				<tr>
					<td style="text-align: center"><a href="manageAs?as_no=${as.as_no}">${as.as_name}</a></td>
					<td><fmt:formatDate value="${as.as_s_date}" pattern="yyyy/MM/dd HH:mm" />
						~<fmt:formatDate value="${as.as_e_date}" pattern="yyyy/MM/dd HH:mm" /></td>
					<td style="text-align: center">${as.as_content}</td>
					<td style="text-align: center">${as.as_point}</td>
					<td style="text-align: center">${as.submittedCount} / ${class1.now_p}</td>
					<td>
						<a href="updateAssignmentForm?as_no=${as.as_no}" class="btn btn-dark" role="button">과제수정</a>
						<a href="deleteAssignmentForm?as_no=${as.as_no}" class="btn btn-danger" role="button">과제삭제</a>
						<a href="downloadAs?as_no=${as.as_no}" onclick="return false" class="btn btn-dark" role="button">다운로드</a>
					</td>
				</tr>
			</tbody>
			</c:forEach>
		</table>
		<hr>
		<c:if test="${!empty as_no}">
		<a href="download_asXLSX?as_no=${as_no}" class="btn btn-dark" role="button">
		양식 다운로드 xlsx</a><h3 align="center">${asName}</h3>
		<hr>
	  <form action="upload_asCSV" method="post" enctype="multipart/form-data">
	  	<input type="hidden" name="as_no" value="${as_no}">
	  	<button type="submit" onclick="if(!this.form.file.value) { alert('파일을 선택하세요.'); return false; }" class="btn btn-dark">
	  		csv 업로드</button>
	  	<input type="file" name="file" accept=".csv" />
		</form>
	</c:if>


		<table class="table table-bordered"> 
			<thead class="thead-light">
				<tr>
					<th style="text-align: center">학번</th>
					<th style="text-align: center">학년</th>
					<th style="text-align: center">학생명</th>
					<th style="text-align: center">과제점수</th>
					<th style="text-align: center">제출현황</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="subAs" items="${subAsList}">
					<tr>
						<td style="text-align: center">${subAs.user_no}</td>
						<td style="text-align: center">${subAs.user_grade}</td>
						<td style="text-align: center">${subAs.user_name}</td>
						<td style="text-align: center">${subAs.as_score}</td>
						<td style="text-align: center">
							<c:if test="${!empty subAs.file}"><strong>제출완료</strong></c:if>
							<c:if test="${empty subAs.file or subAs.file.trim() == ''}">
								<span style="color: red;">미제출</span></c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</body>
</html>