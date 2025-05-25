<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<%-- webapp/view/users/resetPwForm.jsp 김기흔 --%>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호초기화</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body>
	<div class="d-flex justify-content-center align-items-center vh-100">
	<form action="resetpw" method="post"onsubmit="return input_check(this)">
 		<table class="table">
		    <tr><td colspan="2"><h3 align="center">비밀번호초기화</h3></td></tr>
     		<tr><th>아이디</th><td><input type="text" name="user_no"  class="form-control"></td></tr>
     		<tr><th>이메일</th><td><input type="text" name="email"  class="form-control"></td></tr>
     		<tr><td colspan="2" align="center"><input type="submit" value="비밀번호 초기화"  class="btn btn-light btn-outline-secondary"></td></tr>
		</table>
	</form>
   </div>

<script type="text/javascript">

	function input_check(f) {
		if(f.user_no.value.trim() == ""){
			alert("아이디를 입력하세요");
			f.user_no.focus();
			return false;
		}
		
		if(f.email.value.trim() == ""){
			alert("이메일을 입력하세요");
			f.email.focus();
			return false;
		}
		
		
		if(!isValidEmail(f.email.value.trim())){
			alert("이메일 형식이 아닙니다.");
			f.email.focus();
			return false;
		}
	}
	
	function isValidEmail(email){
		const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

		return regex.test(email);
	}
	
</script>

</body>
</html>