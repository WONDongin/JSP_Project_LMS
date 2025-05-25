<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>수정 전 화면 조회</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <style>
        .form-container {
            max-width: 700px;
            margin: 50px auto;
        }
        .form-title {
            font-size: 1.3rem;
            font-weight: bold;
        }
    </style>
</head>
<body class="bg-light">

<div class="container form-container">
    <div class="card">
        <div class="card-header form-title">내 정보 수정</div>
        <div class="card-body">
            <form action="update" method="post" onsubmit="return inputcheck(this)" name="f">
                <div class="form-group">
                    <label>아이디(학번)</label>
                    <input type="text" name="user_no" value="${login.user_no}" class="form-control" readonly>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label>이름</label>
                        <input type="text" name="user_name" value="${login.user_name}" class="form-control">
                    </div>
                    <c:if test="${login.role == 3 }">
                    <div class="form-group col-md-6">
                        <label>학과</label>
                        <input type="text" name="major" value="${login.major_no}" class="form-control">
                    </div>
                    </c:if>
                </div>

                <div class="form-group">
                    <label>성별</label><br>
                    <div class="form-check form-check-inline">
                        <input type="radio" name="gender" value="1" ${login.gender == 1 ? "checked" : ""} class="form-check-input">
                        <label class="form-check-label">남</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input type="radio" name="gender" value="2" ${login.gender == 2 ? "checked" : ""} class="form-check-input">
                        <label class="form-check-label">여</label>
                    </div>
                </div>

                <c:if test="${login.role == 1}">
                    <div class="form-group">
                        <label>학년</label>
                        <select name="grade" class="form-control">
                            <option value="choose" disabled selected>-- 선택하세요 --</option>
                            <option value="1" ${login.user_grade == 1 ? "selected" : ""}>1학년</option>
                            <option value="2" ${login.user_grade == 2 ? "selected" : ""}>2학년</option>
                            <option value="3" ${login.user_grade == 3 ? "selected" : ""}>3학년</option>
                            <option value="4" ${login.user_grade == 4 ? "selected" : ""}>4학년</option>
                        </select>
                    </div>
                </c:if>

                <div class="form-group">
                    <label>전화번호</label>
                    <input type="text" name="tel" value="${login.tel}" class="form-control">
                </div>

                <div class="form-group">
                    <label>이메일</label>
                    <input type="text" name="email" value="${login.email}" class="form-control">
                </div>

                <div class="form-group">
                    <label>비밀번호 확인</label>
                    <input type="password" name="password" class="form-control">
                </div>

                <div class="text-right">
                    <button type="submit" class="btn btn-dark">내 정보 수정</button>
                    <a href="info" class="btn btn-light btn-outline-secondary">뒤로가기</a>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    function inputcheck(f) {
        if (f.password.value.trim() === "") {
            alert("비밀번호를 입력하세요");
            f.password.focus();
            return false;
        }
        return true;
    }
</script>

</body>
</html>