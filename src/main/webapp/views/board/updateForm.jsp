<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}" scope="application" />

<%-- 이동원 --%>
<!DOCTYPE html>
<html>
<head>
<!-- CSS -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.css" rel="stylesheet">

<!-- JS -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.js"></script>

<meta charset="UTF-8">
<title>${arti.arti_title}</title>
</head>
<body>
<h2>${board_name} 게시판/${arti.arti_title} 수정하기</h2>
<form action="update" method="post" enctype="multipart/form-data" name="updateForm">
	<input type="hidden" name="arti_no" value="${arti.arti_no}">
	<input type="hidden" name="file2" value="${arti.file}">
		<table class="table">
		<tr><td>제목</td><td><input name="title" value="${arti.arti_title}" class="form-control"></td></tr>
		<tr><td colspan="2">
			<textarea rows="10" name="content" class="form-control" id="summernote">
			${arti.arti_content}</textarea>
		</td></tr>
		<tr><td>첨부파일</td>
		<td><c:if test="${!empty arti.file}">
				<div id="file_desc">${arti.file}
			  <a href="javascript:file_delete()">[첨부파일 삭제]</a></div>
			</c:if>    
			<input type="file" name="file1"></td></tr>
		<tr><td colspan="2" class="text-center">
			<a href="javascript:chkInput()" class="btn-light btn-outline-secondary">게시물수정</a>
		</td></tr>
	</table>
</form>
<script>
function chkInput() {
	const f = document.updateForm;
	
	if(f.title.value.trim()=="") {
		 alert("제목을 입력하세요");
		 f.title.focus();
		 return;
		}
	if($('#summernote').summernote('code').trim()=="") {
		 alert("내용을 입력하세요");
		 $('#summernote').summernote('focus');
		 return;
	}
	f.submit(); //submit 발생=> form의 action 페이지로 요청
}
</script>
<script>
function file_delete() { 
 document.updateForm.file2.value=""; 
 file_desc.style.display="none"; 
}
</script>
<script type="text/javascript">
$(function() {
 $("#summernote").summernote({
  height:300,
  callbacks : {
   //onImageUpload : 이미지 업로드 이벤트 발생.
   //files : 한개이상의 이미지 업로드 가능. 배열
   onImageUpload : function(files) {
	   for(let i=0;i < files.length;i++) {
		   sendFile(files[i]); //하나씩 ajax 이용하여 서버로 파일 전송
	   }
   }
  }
 })
})
function sendFile(file) {
 let data = new FormData(); //폼데이터를 수집하고 전송가능한 객체. 파일업로드에 사용됨.
 data.append("file",file); //file : 이미지 파일
 $.ajax({
  url : "${path}/uploadImage",
  type : "post",
  data : data,
  processData : false,
  contentType : false,
  success : function(url) {
   //url : 업로드된 이미지의 접근 url 정보
   $("#summernote").summernote("insertImage",url);
     // <img src="url" > 변경.
  },
  error : function(e) {
   alert("이미지 업로드 실패 :" + e.status);
  }
 })
}
</script>
</body>
</html>