<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%-- webapp/view/users/idForm.jsp  김기흔--%>
<html>
	<head>
	<meta charset="UTF-8">
	<title>아이디 찾기</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
	</head>
	<body>
	<div class="d-flex justify-content-center align-items-center vh-100">
	<form action="id" method="post" name="f" onsubmit="return input_check(this)">
		<table class="table">
	        <tr><td colspan="2"><h3 align="center">아이디 찾기</h3></td></tr>
			<tr><th>이름</th><td><input type="text" name="user_name" class="form-control"></td></tr>
			<tr><th>이메일</th><td><input type="text" name="email" class="form-control"></td></tr>
			<tr><td colspan="2" align="center"><input type="submit" value="아이디 찾기" class="btn btn-light btn-outline-secondary"></td></tr>
		</table>
	</div>
	</form>
	
	<script type="text/javascript">
			function input_check(f){
				if(f.user_name.value.trim() == ""){
					alert("이름을 입력하세요");
					f.user_name.focus();
					return false; 
				}
				if(f.email.value.trim() == ""){
					alert("이메일을 입력하세요");
					f.email.focus();
					return false; 
				}
				return true;
			}
		</script>
	</body>
</html>