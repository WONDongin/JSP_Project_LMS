<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>기본 정보</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <style>
        .info-container {
            max-width: 600px;
            margin: 50px auto;
        }
        .card-header {
            background-color: #f8f9fa;
            font-size: 1.25rem;
            font-weight: bold;
        }
        .info-actions {
            text-align: right;
            margin-top: 20px;
        }
    </style>
</head>
<body class="bg-light">
    <div class="container info-container">
        <div class="card">
            <div class="card-header">
                기본 정보
            </div>
            <ul class="list-group list-group-flush">
                <li class="list-group-item"><strong>아이디(학번):</strong> ${user.user_no}</li>
                <li class="list-group-item"><strong>이름:</strong> ${user.user_name}</li>
                <li class="list-group-item"><strong>성별:</strong> ${user.gender == 1 ? "남" : "여"}</li>
                <c:if test="${user.role == 1}">
                    <li class="list-group-item"><strong>학년:</strong> ${user.user_grade}</li>
                </c:if>
                <c:if test="${user.role != 3}">
                <li class="list-group-item"><strong>학과:</strong> ${user.major_no}</li>
                </c:if>
                <c:set var="tel1" value="${fn:substring(user.tel, 0, 3)}" />
                <c:set var="tel2" value="${fn:substring(user.tel, 3, 7)}" />
                <c:set var="tel3" value="${fn:substring(user.tel, 7, 11)}" />
                <li class="list-group-item"><strong>전화번호:</strong> ${tel1}-${tel2}-${tel3}</li>

                <li class="list-group-item"><strong>이메일:</strong> ${user.email}</li>
            </ul>
        </div>

        <div class="info-actions">
            <a href="updateForm?id=${login.user_no}" class="btn btn-dark">정보 수정</a>
            <a href="pwForm" class="btn btn-dark">비밀번호 재설정</a>
        </div>
    </div>
</body>
</html>