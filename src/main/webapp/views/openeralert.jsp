<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
	alert("${msg}");
	opener.location.href="${url}";
	self.close();
</script>