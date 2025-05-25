<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>구디 대학교 학사관리 시스템</title>
    <!-- Bootstrap 4 CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>

    </style>
</head>
<body>
    <div class="col-sm-10 container">
    	<div class="row py-5" style="justify-content: center; gap: 10px;">
        	<div class="main_list col-sm-5 p-5">
                    <h3 class="mb-4">공지사항</h3>
                    <ul class="list-group list-group-flush">
                        <c:forEach var="m" items="${list}">
                            <li class="list-group-item">
                                <a href="list?boardid=${m.id}&info?num=${m.id}">2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)...</a>
                            </li>
                            <li class="list-group-item">
                                <a href="list?boardid=${m.id}&info?num=${m.id}">2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)...</a>
                            </li>
                            <li class="list-group-item">
                                <a href="list?boardid=${m.id}&info?num=${m.id}">2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)...</a>
                            </li>
                            <li class="list-group-item">
                                <a href="list?boardid=${m.id}&info?num=${m.id}">2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)...</a>
                            </li>
                            <li class="list-group-item">
                                <a href="list?boardid=${m.id}&info?num=${m.id}">2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)...</a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>

                <div class="main_list col-sm-5 p-5">
                    <h3 class="mb-4">캘린더</h3>
                    <p>세부 콘텐츠 내용글</p>
                </div>

                <div class="main_list col-sm-5 p-5">
                    <h3 class="mb-4">강의목록</h3>
                    <table class="table table-bordered table-hover">
                        <thead>
                            <tr>
                                <th>과목명</th>
                                <th>강의실</th>
                                <th>이름</th>
                                <th>강사명</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="m" items="${list}">
                                <tr>
                                    <a href="#">
                                        <td>과목명</td>
                                        <td>강의실</td>
                                        <td>이름</td>
                                        <td>강사명</td>
                                    </a>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div class="main_list col-sm-5 p-5">
                    <h3 class="mb-4">과제목록</h3>
                    <ul class="list-group list-group-flush">
                        <c:forEach var="m" items="${list}">
                            <li class="list-group-item">
                                <a href="list?boardid=${m.id}&info?num=${m.id}">2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)...</a>
                            </li>
                            <li class="list-group-item">
                                <a href="list?boardid=${m.id}&info?num=${m.id}">2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)...</a>
                            </li>
                            <li class="list-group-item">
                                <a href="list?boardid=${m.id}&info?num=${m.id}">2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)...</a>
                            </li>
                            <li class="list-group-item">
                                <a href="list?boardid=${m.id}&info?num=${m.id}">2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)...</a>
                            </li>
                            <li class="list-group-item">
                                <a href="list?boardid=${m.id}&info?num=${m.id}">2025-1학기 미등록자 구제등록 안내(4.24. ~ 4.30.)...</a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>        
	</div>
</body>
</html>
