<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- webapp/view/mainLMS/updateUserForm.jsp -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자 정보 수정</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body class="bg-light p-4">

<div class="container">
    <h4 class="mb-4">사용자 정보 수정</h4>

    <form action="updateuser" method="post" class="bg-white p-4 rounded shadow-sm">

        <!-- 유저번호 (수정 불가, readonly 처리) -->
        <div class="form-group">
            <label>유저번호(아이디)</label>
            <input type="text" name="user_no" class="form-control" value="${user.user_no}" readonly>
        </div>

        <div class="form-group">
            <label>이름</label>
            <input type="text" name="user_name" class="form-control" value="${user.user_name}" required>
        </div>

        <div class="form-group">
            <label>구분</label>
            <select name="role" class="form-control" required>
                <option value="1" ${user.role == 1 ? 'selected' : ''}>학생</option>
                <option value="2" ${user.role == 2 ? 'selected' : ''}>교수</option>
            </select>
        </div>

        <div class="form-group">
            <label>성별</label><br>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="gender" value="1" ${user.gender == 1 ? 'checked' : ''}>
                <label class="form-check-label">남</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="gender" value="2" ${user.gender == 2 ? 'checked' : ''}>
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
		<c:if test="${user.role == 1 }">
        <div class="form-group">
            <label>학년</label>
            <select name="user_grade" class="form-control">
                <option value="">-- 선택하세요 --</option>
                <option value="1" ${user.user_grade == 1 ? 'selected' : ''}>1학년</option>
                <option value="2" ${user.user_grade == 2 ? 'selected' : ''}>2학년</option>
                <option value="3" ${user.user_grade == 3 ? 'selected' : ''}>3학년</option>
                <option value="4" ${user.user_grade == 4 ? 'selected' : ''}>4학년</option>
            </select>
        </div>
        </c:if>

        <div class="form-group">
            <label>이메일</label>
            <input type="email" name="email" class="form-control" value="${user.email}">
        </div>

        <div class="form-group">
            <label>전화번호</label>
            <input type="text" name="tel" class="form-control" value="${user.tel}">
        </div>
        
         <div class="form-group">
            <label>비밀번호 (확인을 위해 입력)</label>
            <input type="password" name="password" class="form-control" required>
        </div>

        <div class="text-right">
            <button type="submit" class="btn btn-dark">수정 완료</button>
            <a href="adminForm" class="btn btn-light btn-outline-secondary">취소</a>
        </div>
    </form>
</div>

</body>
</html>