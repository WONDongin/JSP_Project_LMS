<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- webapp/view/mainLMS/addUserForm.jsp 김기흔 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자 추가</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body class="bg-light p-4">

<div class="container">
    <h4 class="mb-4">사용자 추가</h4>

    <form action="adduser" method="post" class="bg-white p-4 rounded shadow-sm">

        <div class="form-group">
            <label>유저번호(아이디)</label>
            <input type="text" name="user_no" class="form-control" required>
        </div>

        <div class="form-group">
            <label>이름</label>
            <input type="text" name="user_name" class="form-control" required>
        </div>

        <div class="form-group">
            <label>비밀번호</label>
            <input type="password" name="password" class="form-control" required>
        </div>

        <div class="form-group">
            <label>구분</label>
            <select name="role" class="form-control" required>
                <option value="">-- 선택하세요 --</option>
                <option value="1">학생</option>
                <option value="2">교수</option>
            </select>
        </div>

        <div class="form-group">
            <label>성별</label><br>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="gender" value="1" checked>
                <label class="form-check-label">남</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="gender" value="2">
                <label class="form-check-label">여</label>
            </div>
        </div>

        <div class="form-group">
            <label>학과</label>
            <select type="text" name="major_no" class="form-control">
            <option value="">-- 선택하세요 --</option>
                <option value="1000">컴퓨터공학과</option>
                <option value="2000">기계공학과</option>
                <option value="3000">건축공학과</option>
              </select>
        </div>
        <div class="form-group">
            <label>학년</label>
            <select name="grade" class="form-control">
                <option value="">-- 선택하세요 --</option>
                <option value="1">1학년</option>
                <option value="2">2학년</option>
                <option value="3">3학년</option>
                <option value="4">4학년</option>
            </select>
        </div>

        <div class="form-group">
            <label>이메일</label>
            <input type="email" name="email" class="form-control">
        </div>

        <div class="form-group">
            <label>전화번호</label>
            <input type="text" name="tel" class="form-control">
        </div>

        <div class="text-right">
            <button type="submit" class="btn btn-dark">사용자 추가</button>
            <a href="adminForm" class="btn btn-light btn-outline-secondary">뒤로가기</a>
        </div>
    </form>
</div>

</body>
</html>