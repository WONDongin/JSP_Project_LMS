<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>비밀번호 재설정</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <style>
        .form-container {
            max-width: 500px;
            margin: 60px auto;
        }
        .form-title {
            font-size: 1.3rem;
            font-weight: bold;
            text-align: center;
            margin-bottom: 20px;
        }
    </style>
</head>
<body class="bg-light">

<div class="container form-container">
    <div class="card shadow-sm">
        <div class="card-body">
            <div class="form-title">비밀번호 재설정</div>
            <form action="updatepw" method="post" name="f" onsubmit="return input_check(this)">
                <div class="form-group">
                    <label>현재 비밀번호</label>
                    <input type="password" name="password" class="form-control">
                </div>
                <div class="form-group">
                    <label>새로운 비밀번호</label>
                    <input type="password" name="new_password1" class="form-control">
                </div>
                <div class="form-group">
                    <label>새로운 비밀번호 재입력</label>
                    <input type="password" name="new_password2" class="form-control">
                </div>
                <div class="text-center">
                    <button type="submit" class="btn btn-light btn-outline-secondary">비밀번호 재설정</button>
                    <a href="info" class="btn btn-light btn-outline-secondary">뒤로가기</a>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    function input_check(f) {
        if (f.password.value.trim() === "") {
            alert("현재 비밀번호를 입력하세요.");
            f.password.focus();
            return false;
        }
        if (f.new_password1.value.trim() === "") {
            alert("새로운 비밀번호를 입력하세요.");
            f.new_password1.focus();
            return false;
        }
        if (f.new_password2.value.trim() === "") {
            alert("새로운 비밀번호를 다시 입력하세요.");
            f.new_password2.focus();
            return false;
        }
        if (f.new_password1.value !== f.new_password2.value) {
            alert("새로운 비밀번호가 서로 일치하지 않습니다.");
            f.new_password2.focus();
            return false;
        }
        return true;
    }
</script>

</body>
</html>