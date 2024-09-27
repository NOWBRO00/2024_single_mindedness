<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="description" content="Sona Template">
<meta name="keywords" content="Sona, unica, creative, html">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>고래밥</title>

<!-- Google Font -->
<link
	href="https://fonts.googleapis.com/css?family=Lora:400,700&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Cabin:400,500,600,700&display=swap"
	rel="stylesheet">

<!-- Css Styles -->
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="css/font-awesome.min.css" type="text/css">
<link rel="stylesheet" href="css/elegant-icons.css" type="text/css">
<link rel="stylesheet" href="css/flaticon.css" type="text/css">
<link rel="stylesheet" href="css/owl.carousel.min.css" type="text/css">
<link rel="stylesheet" href="css/nice-select.css" type="text/css">
<link rel="stylesheet" href="css/jquery-ui.min.css" type="text/css">
<link rel="stylesheet" href="css/magnific-popup.css" type="text/css">
<link rel="stylesheet" href="css/slicknav.min.css" type="text/css">
<link rel="stylesheet" href="css/style.css" type="text/css">
<style>
.search-container {
	text-align: center;
	margin-top: 50px;
}

.search-box {
	display: flex;
	justify-content: center;
	align-items: center;
}

.search-input {
	padding: 10px;
	font-size: 16px;
	border: 2px solid #ccc;
	border-radius: 5px 0 0 5px;
	width: 300px;
	outline: none;
}

.search-button {
	padding: 10px 20px;
	font-size: 16px;
	border: 2px solid #ccc;
	border-left: none;
	border-radius: 0 5px 5px 0;
	background-color: #1F3BB3;
	color: white;
	cursor: pointer;
}

.search-button:hover {
	background-color: #e0f7fa;
}

.file-display {
	position: relative;
	z-index: 1;
	background-color: #f8f8f8;
	border: 1px solid #ccc;
	border-radius: 4px;
	padding: 10px;
	min-height: 30px;
	line-height: 30px;
	overflow: hidden;
}

.nice-select {
	margin-right: 10px;
}

body {
	color: black; /* 전체 글씨 색상 검정으로 변경 */
	background-color: #f8f8f8; /* 배경색을 밝은 회색으로 설정 */
}

.bi-text {
	color: black; /* 글씨 색상 검정으로 변경 */
	background-color: white; /* 배경색을 하얀색으로 설정 */
	padding: 10px; /* 여백 추가 */
}
</style>
</head>
<body>
	<!-- 헤더 연결 -->
	<c:import url="header.jsp"></c:import>

	<!-- 탐색경로 섹션 시작 -->
	<div class="breadcrumb-section">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<div class="breadcrumb-text">
						<h2>자유 게시판</h2>
						<div class="bt-option">
							<span>포인트 소개, 월척자랑</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 탐색경로 섹션 종료 -->

	<!-- 검색창 섹션 시작 -->
	<div class="search-container">
		<div class="search-box">
			<!-- 검색폼 -->
			<form action="productListPage.do" method="GET">
				<select name="search_criteria">
					<option value="basic">기본순</option>
					<option value="popularity">좋아요 순</option>
					<option value="newest">최신순</option>
				</select> <input type="text" class="search-input" name="searchKeyword"
					placeholder="게시물검색...">
				<button class="search-button" type="submit">검색</button>
				<div class="select-option"></div>
			</form>
			  <!-- 글 작성 버튼 추가 -->
        <a href="writeBoardPage.do" class="search-button" style="margin-left: 10px;">글 작성하기</a>
		</div>
	</div>
	<br>
	<!-- 검색창 섹션 시작 -->

	<!-- 게시판 목록 섹션 시작 -->
	<!-- 게시판 목록이 비어있지 않다면 -->
	<section class="blog-section blog-page spad">
		<div class="container">
			<div class="row">
				<!-- 게시글이 없는 경우 메시지 출력 -->
				<c:if test="${empty boardList}">
					<div class="empty-boardList">
						<p>게시글이 없습니다.</p>
						<a href="main.do" class="btn-primary">메인으로 돌아가기</a>
					</div>
				</c:if>

				<!-- 게시글이 있는 경우 게시글 목록 출력 -->
				<c:if test="${not empty boardList}">
					<c:forEach var="board" items="${boardList}">
						<div class="col-md-4">
							<div class="blog-item">
								<c:if test="${not empty board.board_file_name}">
									<img src="${board.board_file_dir}" alt="게시글 이미지"
										class="img-fluid">
								</c:if>
								<c:if test="${empty board.board_file_name}">
									<img src="img/board/boardBasic.png" alt="기본 이미지"
										class="img-fluid">
								</c:if>
								<div class="bi-text">
									<span class="b-tag">자유게시판</span>
									<div class="b-tag">${board.board_like_cnt}</div>
									<h4>
										<a class="text-dark"
											href="boardDetail.do?member_id=${member_id}&board_num=${board.board_num}">${board.board_title}</a>
									</h4>
									<div class="b-time">
										<div class="text-dark">
											<i class="icon_clock_alt"></i>${board.board_registration_date}
										</div>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</c:if>

			</div>
		</div>

		<!-- 게시판 태그 -->
		<!-- c:forEach를 사용하여 블로그 항목 반복 시작 -->
		<%
		/*
		<c:forEach var="board" items="${boardList}">
		<div class="col-md-4">
			<!-- 사용자 입력 파일이 비어있지 않다면 -->
			<c:if test="${not empty boardList}">
				<div class="blog-item set-bg"
			data-setbg="${board.board_file_dir}.${board.board_file_name}.${board.board_file_extension}">
			<div class="bi-text">
				<!-- 게시판 종류 -->
				<span class="b-tag">자유게시판</span>
				<!-- 게시판 좋아요 개수 -->
				<div class="b-tag">${board.board_like_cnt}</div>
				<!-- 게시판 제목 및 링크 -->
				<!-- 멤버ID & 게시판PK 전송-->
				<h4>
					<a
						href="boardDetail.do?member_id=${board.board_writer_id}&board_num=${board.board_num}">${board.board_title}</a>
				</h4>
				<!-- 게시판 작성 날짜 -->
				<div class="b-time">
					<i class="icon_clock_alt"></i>${board.board_registration_date}</div>
			</div>
				</div>
			</c:if>

			<!-- 사용	자 입력 파일이 비어있다면 -->
			<c:if test="${empty board.board_file_name}">
				<div class="blog-item set-bg" data-setbg="img/board/boardBagic.png">
			<div class="bi-text">
				<!-- 게시판 종류 -->
				<span class="b-tag">자유게시판</span>
				<!-- 게시판 좋아요 개수 -->
				<div class="b-tag">${board.board_like_cnt}</div>
				<!-- 게시판 제목 및 링크 -->
				<!-- 멤버ID & 게시판PK 전송-->
				<h4>
					<a
						href="boardDetail.do?board_writer_id=${board.board_writer_id}&board_num=${board.board_num}">${board.board_title}</a>
				</h4>
				<!-- 게시판 작성 날짜 -->
				<div class="b-time">
					<i class="icon_clock_alt"></i>${board.board_registration_date}</div>
			</div>
				</div>
			</c:if>
		</div>
		</c:forEach>

		<!-- 검색한 상품이 없는경우 -->
		<c:if test="${empty boardList}">
		검색 결과가 없습니다.
		    	</c:if>
		*/
		%>

		<!-- 페이지네이션 섹션 시작 -->
		<div class="col-lg-12">
			<div class="pagination">
				<!-- 현재 페이지가 1보다 크면 '이전' 링크 표시 -->
				<c:if test="${currentPage > 1}">
					<a href="boardListPage.do?currentPage=${currentPage - 1}">이전</a>
				</c:if>
				<c:if test="${currentPage == 1}">
					<a href="boardListPage.do?currentPage=${currentPage - 1}" disabled>이전</a>
				</c:if>

				<!-- 전체 페이지 수에 따른 페이지 번호 링크 생성 -->
				<c:forEach var="i" begin="1" end="${board_page_count}">
					<c:if test="${i == currentPage}">
						<strong>${i}</strong>
						<!-- 현재 페이지 강조 -->
					</c:if>
					<c:if test="${i != currentPage}">
						<a href="boardListPage.do?currentPage=${i}">${i}</a>
					</c:if>
				</c:forEach>

				<!-- 현재 페이지가 전체 페이지 수보다 작으면 '다음' 링크 표시 -->
				<c:if test="${currentPage < board_page_count}">
					<a href="boardListPage.do?currentPage=${currentPage + 1}">다음</a>
				</c:if>
				<c:if test="${currentPage == board_page_count}">
					<a href="boardListPage.do?currentPage=${currentPage + 1}" disabled>다음</a>
				</c:if>
			</div>
		</div>
		<!-- 페이지네이션 섹션 종료 -->


	</section>
	<!-- 게시판 목록 섹션 종료 -->

	<!-- 푸터 연결 -->
	<c:import url="footer.jsp"></c:import>

	<!-- Js Plugins -->
	<script src="js/jquery-3.3.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/jquery.magnific-popup.min.js"></script>
	<script src="js/jquery.nice-select.min.js"></script>
	<script src="js/jquery-ui.min.js"></script>
	<script src="js/jquery.slicknav.js"></script>
	<script src="js/owl.carousel.min.js"></script>
	<script src="js/main.js"></script>
</body>
</html>