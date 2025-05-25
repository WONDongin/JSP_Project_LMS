<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>과제 수정</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body class="bg-light p-4">
<div class="container">
    <h4 class="mb-4">과제 수정</h4>
    <form action="updateAssignment" method="post" class="bg-white p-4 rounded shadow-sm">
        <input type="hidden" name="as_no" value="${as.as_no}">

        <div class="form-group">
            <label>과제명</label>
            <input type="text" name="as_name" class="form-control" value="${as.as_name}" required>
        </div>

        <div class="form-group">
            <label>과제내용</label>
            <textarea name="as_content" class="form-control" rows="4" required>${as.as_content}</textarea>
        </div>

        <div class="form-group">
            <label>제출 시작 기한</label>
            <input type="datetime-local" name="as_s_date" class="form-control" value="${as_s_date}">
        </div>

        <div class="form-group">
            <label>제출 마감 기한</label>
            <input type="datetime-local" name="as_e_date" class="form-control" value="${as_e_date}">
        </div>

        <div class="form-group">
            <label>과제 배점</label>
            <input type="number" name="as_point" class="form-control" value="${as.as_point}" required>
        </div>

        <div class="form-group">
            <label>강의코드</label>
            <input type="text" name="class_no" class="form-control" value="${as.class_no}" required readonly>
        </div>

        <div class="form-group">
            <label>반</label>
            <input type="text" name="ban" class="form-control" value="${as.ban}" required readonly>
        </div>

        <div class="form-group">
            <label>년도</label>
            <input type="number" name="year" class="form-control" value="${as.year}" required readonly>
        </div>

        <div class="form-group">
            <label>학기</label>
            <select name="term" class="form-control">
                <option value="1" selected="${as.term == 1 ? 'selected' : ''}">1학기</option>
                <option value="2" selected="${as.term == 2 ? 'selected' : ''}">1학기</option>
            </select>
        </div>

        <div class="text-right">
            <button type="submit" class="btn btn-dark">수정 완료</button>
            <a href="manageAs" class="btn btn-outline-secondary">취소</a>
        </div>
    </form>
</div>
</body>
</html>
