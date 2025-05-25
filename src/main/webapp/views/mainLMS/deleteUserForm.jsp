<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자 삭제 확인</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body class="bg-light p-4">

<div class="container">
    <h4 class="mb-4">사용자 삭제 확인</h4>
    <div class="bg-white p-4 rounded shadow-sm mb-4">
        <ul class="list-group">
            <li class="list-group-item"><strong>아이디(유저번호):</strong> ${user.user_no}</li>
            <li class="list-group-item"><strong>이름:</strong> ${user.user_name}</li>
            <li class="list-group-item"><strong>구분:</strong>
                <c:choose>
                    <c:when test="${user.role == 1}">학생</c:when>
                    <c:when test="${user.role == 2}">교수</c:when>
                    <c:when test="${user.role == 3}">관리자</c:when>
                    <c:otherwise>알 수 없음</c:otherwise>
                </c:choose>
            </li>
            <li class="list-group-item"><strong>성별:</strong>
                <c:choose>
                    <c:when test="${user.gender == 1}">남</c:when>
                    <c:when test="${user.gender == 2}">여</c:when>
                    <c:otherwise>기타</c:otherwise>
                </c:choose>
            </li>
            <li class="list-group-item"><strong>학과:</strong>
                <c:choose>
                    <c:when test="${user.major_no == '1000'}">컴퓨터공학과(${user.major_no})</c:when>
                    <c:when test="${user.major_no == '2000'}">기계공학과(${user.major_no})</c:when>
                    <c:when test="${user.major_no == '3000'}">건축공학과(${user.major_no})</c:when>
                    <c:otherwise>${user.major_no}</c:otherwise>
                </c:choose>
            </li>
            <li class="list-group-item"><strong>학년:</strong>
                <c:choose>
                    <c:when test="${user.user_grade == 1}">1학년</c:when>
                    <c:when test="${user.user_grade == 2}">2학년</c:when>
                    <c:when test="${user.user_grade == 3}">3학년</c:when>
                    <c:when test="${user.user_grade == 4}">4학년</c:when>
                    <c:otherwise>-</c:otherwise>
                </c:choose>
            </li>
            <li class="list-group-item"><strong>이메일:</strong> ${user.email}</li>
            <li class="list-group-item"><strong>전화번호:</strong> ${user.tel}</li>
        </ul>
    </div>

    <form action="deleteuser" method="post" class="bg-white p-4 rounded shadow-sm">
        <!-- 요청객체 전달용 -->
        <input type="hidden" name="user_no" value="${user.user_no}">
        <div class="form-group">
            <label>비밀번호 입력 (삭제 확인용)</label>
            <input type="password" name="password" class="form-control" required>
        </div>

        <div class="text-right">
            <button type="submit" class="btn btn-danger">삭제</button>
            <a href="adminForm" class="btn btn-outline-secondary">취소</a>
        </div>
    </form>
</div>

</body>
</html>