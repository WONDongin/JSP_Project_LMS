<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>과제 삭제</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body class="bg-light p-4">
<div class="container">
    <h4 class="mb-4">과제 삭제</h4>
    <div class="bg-white p-4 rounded shadow-sm">
        <ul class="list-group mb-4" style="gap: 10px;">
            <li class="list-group-item" >
            							 ${as.year}<strong>년도</strong>
            							 ${as.term}<strong>학기</strong></li>
            <li class="list-group-item"><strong>강의코드:</strong> ${as.class_no}
            							<strong>수업명:</strong> ${class1.class_name}
            							<strong>반:</strong>${as.ban} </li>
            <li class="list-group-item"><strong>과제명:</strong> ${as.as_name}</li>
            <li class="list-group-item"><strong>내용:</strong> ${as.as_content}</li>
            <li class="list-group-item"><strong>제출 시작:</strong><fmt:formatDate value="${as.as_s_date}" pattern="yyyy/MM/dd HH:mm" /> </li>
            <li class="list-group-item"><strong>제출 마감:</strong><fmt:formatDate value="${as.as_e_date}" pattern="yyyy/MM/dd HH:mm" /> </li>
            <li class="list-group-item"><strong>배점:</strong> ${as.as_point}</li>
             <li class="list-group-item"><strong>제출 현황:</strong> ${as.submittedCount} / ${class1.now_p}</li>
        </ul>

        <form action="deleteAssignment" method="post">
            <input type="hidden" name="as_no" value="${as.as_no}">
            <div class="form-group">
                <label>교수 비밀번호 입력</label>
                <input type="password" name="password" class="form-control" required>
            </div>

            <div class="text-right">
                <button type="submit" class="btn btn-danger">과제 삭제</button>
                <a href="manageAs" class="btn btn-outline-secondary">취소</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>>