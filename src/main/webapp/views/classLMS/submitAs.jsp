<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- 원동인 --%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>과제 제출</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	</head>
	<body>
		<jsp:include page="classTopTable.jsp" />
		
		<h3 class="my-4">과제 제출</h3>
		<table class="table table-bordered"> 
			<thead class="thead-light">
				<tr>
					<th style="text-align: center">no</th>
					<th style="text-align: center">과제목록</th>
					<th style="text-align: center">기한</th>
					<th style="text-align: center">제출 여부</th>
					<th style="text-align: center">제출한 과제 다운로드</th>
				</tr>
			</thead>	
			<tbody>
			<c:forEach var="as" items="${asList}" varStatus="status">
			<c:if test="${class1.class_no == as.class_no}">		
				<tr>
					<td style="text-align: center">${status.index + 1}</td>
					<td><a href="submitassignment?as_no=${as.as_no}" style="text-align: center">${as.as_name}</a></td>
					<td><fmt:formatDate value="${as.as_s_date}" pattern="yyyy/MM/dd HH:mm" /> 
					  ~ <fmt:formatDate value="${as.as_e_date}" pattern="yyyy/MM/dd HH:mm" /> </td>
					<td style="text-align: center">
						<c:if test="${filelist[status.index] != null and !filelist[status.index].trim().equals('')}">
						제출</td><td>
						<div class="d-flex justify-content-center">
							<a href="../files/${filelist[status.index]}" class="btn btn-dark" role="button" style="text-align: center" download>다운로드</a>
						</div>
						</c:if>
						<c:if test="${filelist[status.index] == null and filelist[status.index].trim().equals('')}">
						미제출</td><td>
						</c:if>						
					</td>
				</tr>
			</c:if>
			</c:forEach>
			</tbody>
		</table>
	</body>
</html>