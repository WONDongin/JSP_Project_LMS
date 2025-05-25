<%@ page language="java" contentType="text/html; charset=UTF-8"

    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>과제 추가</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
</head>
<body class="bg-light p-4">

<div class="container">
    <h4 class="mb-4">과제 추가</h4>

    <form action="addAssignment" method="post" class="bg-white p-4 rounded shadow-sm">
        <div class="form-group">
            <label>과제명</label>
            <input type="text" name="as_name" class="form-control" placeholder="과제명을 입력하세요" required>
        </div>

        <div class="form-group">
            <label>과제내용</label>
            <textarea name="as_content" class="form-control" rows="5" placeholder="과제 설명을 입력하세요" required></textarea>
        </div>

        <div class="form-row">
            <div class="form-group col-md-6">
                <label>제출 시작일 (YYYY-MM-DD HH:mm)</label>
                <input type="datetime-local" name="as_s_date" class="form-control" required>
            </div>
            <div class="form-group col-md-6">
                <label>제출 마감일 (YYYY-MM-DD HH:mm)</label>
                <input type="datetime-local" name="as_e_date" class="form-control" required>
            </div>
        </div>

        <div class="form-group">
            <label>과제 배점</label>
            <input type="number" name="as_point" class="form-control" placeholder="예: 100" required>
        </div>

        <div class="form-row">
            <div class="form-group col-md-3">
                <label>강의 코드</label>
                <input type="text" name="class_no" class="form-control" value="${class1.class_no}" required readonly>
            </div>
            <div class="form-group col-md-3">
                <label>반</label>
                <input type="text" name="ban" class="form-control" value="${class1.ban}" required readonly>
            </div>
            <div class="form-group col-md-3">
                <label>년도</label>
                <input type="text" name="year" class="form-control" value="${class1.year}" required readonly>
            </div>
            <div class="form-group col-md-3">
                <label>학기</label>
                <input name="term" class="form-control" value="${class1.term}" required readonly>
            </div>
        </div>

        <div class="text-right">
            <button type="submit" class="btn btn-dark">과제 등록</button>
            <a href="manageAs" class="btn btn-outline-secondary">취소</a>
        </div>
    </form>
</div>
</body>
</html>