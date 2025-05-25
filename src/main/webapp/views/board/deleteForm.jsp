<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 이동원 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 삭제하기</title>
</head>
<body>
글 제목 : ${arti.arti_title}
<br>글 작성자 : ${arti.user_name }
<c:if test="${!empty comm}">
<br>ㄴ댓글 : ${comm.comm_content}
<br>ㄴ댓글 작성자 : ${arti.user_name }
</c:if>
<br><br>삭제 후 복구가 불가능합니다.
<br>${target}을 삭제하려면 비밀번호를 입력하세요.
<form action="delete" method="post">
<input type="hidden" name="arti_no" value="${param.arti_no}">
<input type="hidden" name="comm_no" value="${param.comm_no}">
<input type="password" name="password" class="form-control">
<button type="submit" class="btn btn-outline-danger">삭제</button>
</form>
</body>
</html>