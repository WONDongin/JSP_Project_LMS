<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%-- 원동인 --%>
<title>학사 일정 등록</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
	<body>
		<!-- 등록 Form -->
		<h3 class="mb-4 fw_b">학사 일정 등록</h3>
		<form action="event" method="post" name="f" onsubmit="return date_check(this)" class="mb-4" >
		  	<table class="table">
			    <tr>
			      <th class="col-xl-8 fw_b">일정 제목</th>
			      <td class="col-xl-4"><input class="form-control" type="text" name="event_name" required></td>
			    </tr>
			    <tr>
			      <th class="fw_b">일정 시작일</th>
			      <td><input class="form-control" type="date" name="even_s_date" required></td>
			    </tr>
			    <tr>
			      <th class="fw_b">일정 종료일</th>
			      <td><input class="form-control" type="date" name="even_e_date" required></td>
			    </tr>
		  	</table>
		
		  	<button class="btn btn-dark" type="submit" style="width:100%">일정 등록</button>
		</form>
		
		<!-- 이벤트 목록 -->
		<h3 class="mb-4 fw_b">학사 일정 목록</h3>

		<table class="table">
			<thead class="thead-light">
				<tr>
		        	<th class="fw_b text-center">N0</th>
		        	<th class="fw_b text-center">제목</th>
		        	<th class="fw_b text-center">시작일</th>
		        	<th class="fw_b text-center">종료일</th>
		        	<th class="fw_b text-center">수정</th>
		        	<th class="fw_b text-center">삭제</th>
		    	</tr>
			</thead>

		    <c:forEach var="e" items="${eventList}" varStatus="status">
		        <tr>
		            <td class="text-center">${status.index + 1}</td>
		            <td class="text-center">${e.event_name}</td>
		            <td class="text-center"><fmt:formatDate value="${e.even_s_date}" pattern="yyyy-MM-dd" /></td>
		            <td class="text-center"><fmt:formatDate value="${e.even_e_date}" pattern="yyyy-MM-dd" /></td>
		           	<td class="text-center">
                		<button class="btn btn-light btn-outline-secondary" type="button" onclick="openEditModal('${e.event_no}', '${e.event_name}', '${e.even_s_date}', '${e.even_e_date}')">수정</button>
            		</td>
            		<td class="text-center">
            			<a class="btn btn-outline-danger" href="event?delete=${e.event_no}">삭제</a>
            		</td>
		        </tr>
		    </c:forEach>
		</table>
		
		<!-- 수정 모달 -->
		<div id="editModal" style="display:none; position:fixed; width:800px; top:20%; left:30%; background:white; border:1px solid black; padding:20px;">
		    <form action="event" method="post">
		    <table class="table">
			    <tr>
				    <td>
				    	<input type="hidden" name="event_no" id="edit_event_no">
			        제목&nbsp;<input class="form-control"  type="text" name="event_name" id="edit_event_name" required>
				    </td>
			    </tr>
				<tr>
					<td>
						시작일<input class="form-control" type="date" name="even_s_date" id="edit_even_s_date" required>
					</td>
				</tr>
				<tr>
					<td>
						종료일<input class="form-control" type="date" name="even_e_date" id="edit_even_e_date" required>
					</td>
				</tr>
			</table>
		        <button class="btn btn-light btn-outline-secondary mt-2" type="submit">수정완료</button>
		        <button class="btn btn-light btn-outline-secondary mt-2" type="button" onclick="closeEditModal()">닫기</button>
		    </form>
		</div>
		
		<script type="text/javascript">
			function openEditModal(no, name, sDate, eDate) {
			    document.getElementById('edit_event_no').value = no;
			    document.getElementById('edit_event_name').value = name;
			    document.getElementById('edit_even_s_date').value = sDate.substring(0, 10);
			    document.getElementById('edit_even_e_date').value = eDate.substring(0, 10);
			    document.getElementById('editModal').style.display = 'block';
			}
			function closeEditModal() {
			    document.getElementById('editModal').style.display = 'none';
			}
			// 날짜 비교 함수
			function date_check(form) {
			    const start = new Date(form.even_s_date.value);
			    const end = new Date(form.even_e_date.value);

			    if (start > end) {
			        alert("날짜를 다시 입력하세요. (시작일이 종료일보다 늦습니다)");
			        return false; // 전송 막음
			    }
			    return true;
			}
		</script>
	</body>
</html>