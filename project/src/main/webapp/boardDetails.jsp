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
<link rel="stylesheet" href="css/starPlugin.css" type="text/css">
<style type="text/css">

/* 공통 버튼 스타일 */
#likeBtn, .likeButton {
	background-color: #87CEEB; /* 하늘색 */
	border: none;
	color: white;
	padding: 10px 20px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	margin: 5px;
	cursor: pointer;
	border-radius: 5px;
	transition: background-color 0.3s ease, transform 0.2s ease;
}

/* 좋아요 버튼 */
#likeBtn {
	font-size: 14px;
}

#likeBtn:hover {
	background-color: #00BFFF; /* 더 밝은 하늘색 */
	transform: scale(1.05);
}

/* 삭제 버튼 */
#editButton {
	background-color: #87CEEB; /* 하늘색 */
}

#editButton:hover {
	background-color: #00BFFF; /* 더 밝은 하늘색 */
	transform: scale(1.05);
}

/* 수정 버튼 */
#reportButton {
	background-color: #87CEEB; /* 하늘색 */
}

#reportButton:hover {
	background-color: #00BFFF; /* 더 밝은 하늘색 */
	transform: scale(1.05);
}

/* 아이콘 스타일 */
.icon_thumb_up, .icon_warning {
	margin-right: 5px;
}
</style>
</head>

<body>
	<!-- 헤더 연결 -->
	<c:import url="header.jsp"></c:import>

	<!-- 글 상세페이지 글정보섹션 시작 -->
	<!-- data-set-bg : 게시판 기본 이미지 -->
	<section class="blog-details-hero set-bg"
		data-setbg="img/board/boardBasic.png">
		<div class="container">
			<div class="row">
				<div class="col-lg-10 offset-lg-1">
					<div class="bd-hero-text">
						<!-- 카테고리(바다,민물) -->
						<span>자유 게시판</span>
						<!-- 게시글 제목 -->
						<h2>${board.board_title}</h2>
						<ul>
							<!-- 게시글 작성 날짜 -->
							<li class="b-time"><i class="icon_clock_alt"></i>
								${board.board_registration_date}</li>
							<!-- 게시글 작성자 -->
							<li><i class="icon_profile"></i> <!-- 사용자 레벨에 따른 등급 --> <c:if
									test="${not empty member_id}">
									<c:import url="role.jsp"></c:import>
								</c:if> ${board.board_writer_id}</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- 글 상세페이지 글정보섹션 종료 -->

	<!-- 글 상세페이지 파일출력섹션 시작 -->
	<section class="testimonial-section spad">
		<div class="container">
			<div class="row">
				<!-- 사용자가 넣은 파일 확인유무(사진) -->
				<c:if test="${empty fileList}">
					<div>사진 없음</div>
				</c:if>
				<c:if test="${not empty fileList}">
					<div class="col-lg-8 offset-lg-2">
						<div class="testimonial-slider owl-carousel">
							<!-- 데이터로부터 사용자가 넣은 파일 반복 -->
							<c:forEach var="file" items="${fileList}">
								<div class="ts-item">
									<div class="bd-pic">
										<!-- 사용자 입력 이미지 -->
										<img
											src="${file.file_dir}"
											alt="게시글에서 사용자가 입력한 사진파일입니다.">
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</c:if>
			</div>
		</div>
	</section>
	<!-- 글 상세페이지 파일출력섹션 종료 -->

	<!-- 글 상세페이지 메인섹션 시작 -->
	<section class="blog-details-section">
		<div class="container">
			<div class="row">
				<div class="col-lg-10 offset-lg-1">
					<div class="blog-details-text">
						<div class="bd-title">
							<!-- 블로그 내용 -->
							<p>${board.board_content}</p>
						</div>
						<div class="tag-share">
							<div class="tags">
								<div class="button-container">
									<!-- 좋아요 버튼 -->
									<button id="likeBtn" class="likeButton"
			                             data-post-id="${board.board_num}"
			                             style="background-color: ${like_member == 'true' ? 'red' : '#87CEEB'};">
			                             👍 좋아요 (<span id="likeCount">${board.board_like_cnt}</span>)
		                          	</button>
									<input id="like_member" value="${member_id}"
										style="visibility: hidden;">
									<!-- 로그인한 사용자와 작성자가 같은 경우 -->
									<c:if test="${member_id == board.board_writer_id}">
										<!-- 글삭제 버튼 -->
										<button id="editButton" class="likeButton" onclick="location.href='deleteBoard.do?board_num=${board.board_num}';">❌삭제</button>
										<!-- 글수정 버튼 -->
										<!-- <button id="reportButton" class="likeButton" onclick="location.herf='deleteBoard.do?board_num=${board.board_num}'">🛠 수정</button> -->
									</c:if>
								</div>
							</div>
						</div>
						<div class="comment-option">
							<h4>댓글</h4>
							<c:if test="${empty replyList}">
								<p>작성된 댓글이 없습니다.</p>
							</c:if>
							<!-- 댓글 반복 -->
							<c:forEach var="reply" items="${replyList}">
								<div class="single-comment-item">
									<div class="sc-author">
										<!-- 댓글 작성자 프로필 이미지 -->
										<img src="img/profile/${reply.reply_member_profile}" alt="댓글 작성자 프로필 이미지">
									</div>
									<div class="sc-text">
										<!-- 댓글 작성일자 -->
										<span>${reply.reply_registration_date}</span>
										<!-- 댓글 작성자 -->
										<h5>
											<!-- 사용자 레벨에 따른 등급 -->
											<c:if test="${not empty member_id}">
												<c:import url="role.jsp"></c:import>
											</c:if>
											${reply.reply_content}

										</h5>
										<!-- 댓글 내용 -->
										<p>${reply.reply_writer_id}</p>
										<!-- 댓글 삭제 (비동기X)-->
										<c:if test="${member_id == reply.reply_writer_id}">
											<a href="deleteReply.do?reply_num=${reply.reply_num}&board_num=${board.board_num}"
												class="comment-btn">댓글삭제</a>
										</c:if>
									</div>
								</div>
							</c:forEach>
						</div>
						<div class="leave-comment">
							<h4>댓글 작성</h4>
							<form action="writeReply.do" class="comment-form">
								<div class="row">
									<div class="col-lg-12 text-center">
										<input type="hidden" name="board_num" value="${board.board_num}"/>
										<!-- 댓글 내용 입력 -->
										<textarea name="reply_content" placeholder="댓글 내용"></textarea>
										<!-- 댓글 작성 버튼 -->
										<button type="submit" class="site-btn">댓글 작성</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- 글 상세페이지 메인섹션 종료 -->


	<!-- 추천 게시판섹션 시작 -->
	<section class="recommend-blog-section spad">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<div class="section-title">
						<h2>추천 게시글</h2>
					</div>
				</div>
			</div>
			<div class="row">
				<!-- 추천 게시판 아이템 반복 -->
				<c:forEach var="board" items="${boardlist}" begin="1" end="3"
					step="1">
					<div class="col-md-4">
						<!-- 게시판 태그 -->
						<mytag:smallBoard />
					</div>
				</c:forEach>
			</div>
		</div>
	</section>
	<!-- 추천 게시판섹션 종료 -->


	<!-- 푸터 연결 -->
	<c:import url="footer.jsp"></c:import>

	<!-- 템플릿 Js 플러그인 -->
	<script src="js/jquery-3.3.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/jquery.magnific-popup.min.js"></script>
	<script src="js/jquery.nice-select.min.js"></script>
	<script src="js/jquery-ui.min.js"></script>
	<script src="js/jquery.slicknav.js"></script>
	<script src="js/owl.carousel.min.js"></script>
	<script src="js/main.js"></script>

	<!-- 추가 Js 플러그인 -->
	<script src="js/board/like.js"></script>
	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
	<script defer>
		$('#editButton').on('click', function() {
			location.href=""
		}
	</script>
</body>

</html>