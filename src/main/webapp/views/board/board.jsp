<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%-- board (공통)게시판 이동원 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${board_name} 게시판</title>
</head>
<body>
<c:if test="${lms.equals('class')}">
	<jsp:include page="../classLMS/classTopTable.jsp" />
</c:if>
<h2>${board_name} 게시판</h2>
	<form action="board" method="post" name="searchForm">
		<input type="hidden" name="pageNum" value="1">
		<input type="hidden" name="board_id" value="${board_id}">
		<select name="column">
			<option value="arti_title">제목</option>
			<option value="u.user_name">작성자</option>
			<option value="arti_content">내용</option>
		</select>
		<c:if test="${!empty param.column}">
			<script>document.searchForm.column.value='${param.column}'</script>
		</c:if>
		<input placeholder="검색어를 입력하세요." name="keyword" value="${param.keyword}">
		<button type="submit" class="btn btn-light btn-outline-secondary">검색</button>
		<c:if test="${board_id != 9999 || sessionScope.login.user_no == '999'}">
				<a href="writeForm?board_id=${board_id}" class="btn btn-dark">글쓰기</a>
		</c:if>
	</form>
	<c:if test="${artiCount == 0}">
		등록된 게시글이 없습니다.
	</c:if>
	<c:if test="${artiCount > 0}"> <%-- 게시글 목록 --%>
		<table class="table"><thead class="thead-light">
			<tr><th width="8%">번호</th>
			<th width="53%">제목</th><th width="8%">첨부파일</th>
			<th width="14%">작성자</th><th width="17%">등록일</th>
			</tr></thead>
				<c:forEach var="arti" items="${artiList}">
					<tr><td>${artiIndex}</td><c:set var="artiIndex" value="${artiIndex-1}" />
							<td><a href="article?arti_no=${arti.arti_no}">${arti.arti_title}
								<c:if test="${arti.commCount > 0}">[${arti.commCount}]</c:if>
							</a></td>
							<td><c:if test="${!empty arti.file}">O</c:if></td>
							<td>${arti.user_name}</td>
							<td><fmt:formatDate value="${arti.arti_date}" pattern="yyyy-MM-dd HH:mm" /></td>							
					</tr>
				</c:forEach>
			<%-- 하단 페이지 처리하기 --%>
			<tr><td colspan="5" style="text-align:center;">
				<c:if test="${pageNum <= 1}">[이전]</c:if>
				<c:if test="${pageNum > 1}">
					<a href="javascript:boardSubmit(${pageNum-1})">[이전]</a>
				</c:if>
				<c:forEach var="i" begin="${startPage}" end="${endPage}">
					<c:if test="${i == pageNum}">[${i}]</c:if>
					<c:if test="${i != pageNum}">
						<a href="javascript:boardSubmit(${i})">[${i}]</a>
					</c:if>
				</c:forEach>
				<c:if test="${pageNum >= maxPage}">[다음]</c:if>
				<c:if test="${pageNum < maxPage}">
					<a href="javascript:boardSubmit(${pageNum+1})">[다음]</a>
				</c:if>
			</td></tr>
		</table>
	</c:if>
<script>
	function boardSubmit(page) {
		f = document.searchForm;
		f.pageNum.value = page;
		f.submit();
	}
</script>
</body>
</html>