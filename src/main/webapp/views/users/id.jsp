<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- webapp/views/users/id.jsp 김기흔 --%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>아이디 찾기</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <style>
        body {
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            }
    </style>
</head>
<body>
        <div class="text-center">
            <h5 class="card-title mb-4">아이디 찾기 결과</h5>
            <p class="card-text">귀하의 아이디는</p>
            <h4 >${user_no}</h4>

            <hr>

            <button class="btn btn-light btn-outline-secondary" onclick="idsend('${user_no}')">아이디 전송</button>
        </div>
    </div>

    <script type="text/javascript">
        function idsend(user_no) {
            opener.document.f.user_no.value = user_no;
            self.close();
        }
    </script>

</body>
</html>