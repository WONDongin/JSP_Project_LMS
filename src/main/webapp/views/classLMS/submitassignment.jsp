<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- 원동인 --%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>과제 추가</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	</head>
	
	<body>
		<h3 class="my-4">과제 제출</h3>
		<form action="upload" method="post" enctype="multipart/form-data">
		<table class="table table-bordered mb-0">
	       		<tr>
	       			<th style="background-color: #eee;">과제제목</th>
	       			<td>${as.as_name}</td>
	       		</tr>
	       		<tr>
	       			<th style="background-color: #eee;">제출기한</th>
	       			<td><fmt:formatDate value="${as.as_s_date}" pattern="yyyy/MM/dd HH:mm" />
	       			  ~ <fmt:formatDate value="${as.as_e_date}" pattern="yyyy/MM/dd HH:mm" /> </td>
	       		</tr>
	       		<tr>
	       			<th style="background-color: #eee;">내용</th>
	       			<td>${as.as_content}</td>
	       		</tr>
	       		<tr>
	       			<th style="background-color: #eee;">과제 제출</th>
					<td>
    				<input type="hidden" name="as_no" value="${param.as_no}" />
    				<input type="file" name="file" required />
					</td>
       		</table>
       			<div class="d-flex justify-content-end">
    				<button type="submit" class="btn btn-dark" style="align-items : right">과제 제출</button>    				
    			</div>
    		</form>
	</body>
</html>