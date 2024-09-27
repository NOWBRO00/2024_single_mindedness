<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고래밥</title>

<!-- Google Font 추가 -->
<link
   href="https://fonts.googleapis.com/css2?family=Noto+Sans:wght@400;700&display=swap"
   rel="stylesheet">
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

<script src="js/jquery-3.3.1.min.js"></script>

<!-- 스타일 추가 -->
<style type="text/css">
#wishlist_count {
   font-family: 'Noto Sans', sans-serif;
   font-size: 16px;
   color: #09022b;
   display: block;
   margin-bottom: 20px;
}

.count-bold {
   font-size: 16px;
   color: #09022b;
   font-weight: bold; /* 숫자에만 bold 적용 */
}
</style>

</head>
<body>

   <!-- 헤더 연결 -->
   <c:import url="header.jsp" />

   <!-- 탐색경로 섹션 시작 -->
   <div class="breadcrumb-section">
      <div class="container">
         <div class="row">
            <div class="col-lg-12">
               <div class="breadcrumb-text">
                  <h2>위시리스트</h2>
                  <div class="bt-option">
                     <span>나의 찜 목록</span>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </div>
   <!-- 탐색경로 섹션 종료 -->

   <!-- 위시리스트 목록 섹션 시작 -->

   <!-- 빈 위시리스트 요소는 항상 존재하지만, wishlist가 비어있지 않다면 숨김 처리 -->
   <div class="container">
      <div class="empty-wishlist"   style="<c:if test='${not empty wishlist}'>display:none;</c:if>">
         <p>찜한 상품이 없습니다.</p>
         <a href="productListPage.do" class="btn-primary">상품 목록 보기</a>
      </div>
   </div>

   <!-- 만약 위시리스트 파일이 비어있지 않다면 -->
   <c:if test="${not empty wishlist}">
      <section class="wishlist-section blog-page spad">
         <div class="container">
            <div class="row">
               <div class="col-lg-12">
                  <!-- 전체 위시리스트 개수 표시 -->
                  <span id="wishlist_count">전체 <span class="count-bold">${wishlist_count}</span>개
                  </span>
               </div>
               <%-- forEach 사용하여 위시리스트 항목 출력해준다. --%>
               <c:forEach var="datas" items="${wishlist}">
                  <div class="col-md-4">
                     <!-- 게시판 태그 -->
                     <mytag:wishlist wishlist="${datas}" wishlist_page_count="${wishlist_page_count}"/>
                  </div>
               </c:forEach>
            </div>
         </div>
      </section>
   </c:if>

   <!-- 페이지네이션 -->
   <c:if test="${not empty wishlist}">
	   <div class="col-lg-12">
	       <div class="pagination">
	           <!-- 현재 페이지가 1보다 크면 '이전' 링크 표시 -->
	           <c:if test="${current_page > 1}">
	               <a href="wishListPage.do?current_page=${current_page - 1}">이전</a>
	           </c:if>
	           <c:if test="${current_page == 1}">
	               <a href="wishListPage.do?current_page=${current_page - 1}" disabled>이전</a>
	           </c:if>
	   
	           <!-- 전체 페이지 수에 따른 페이지 번호 링크 생성 -->
	           <c:forEach var="i" begin="1" end="${wishlist_page_count}">
	               <c:if test="${i == current_page}">
	                   <strong>${i}</strong>
	                   <!-- 현재 페이지 강조 -->
	               </c:if>
	               <c:if test="${i != current_page}">
	                   <a href="wishListPage.do?current_page=${i}">${i}</a>
	               </c:if>
	           </c:forEach>
	   
	           <!-- 현재 페이지가 전체 페이지 수보다 작으면 '다음' 링크 표시 -->
	           <c:if test="${current_page < wishlist_page_count}">
	               <a href="wishListPage.do?current_page=${current_page + 1}">다음</a>
	           </c:if>
	           <c:if test="${current_page == wishlist_page_count}">
	               <a href="wishListPage.do?current_page=${current_page + 1}" disabled>다음</a>
	           </c:if>
	       </div>
	   </div>
   </c:if>
   <!-- 위시리스트 목록 섹션 종료 -->

	<script type="text/javascript">
	/**
	 * 위시리스트 삭제 기능
	 * 사용자가 위시리스트에서 찜 해제 버튼(삭제)을 누르면 ajax를 이용해 C에게 요청
	 * 비동기처리
	 */

	// 페이지가 완전히 로드된 후 코드를 실행하게 함
	$(document).ready(function(){ 
		// 버튼 클릭시 실행될 함수
		console.log("Document is ready"); // 로그
		
		$(".wishlist").on('click','.btn-remove',function(){
			// 클릭한 버튼을 jQuery 객체로 만들기
			var button = $(this); 
			// 버튼의 data-num 속성값을 가지고 온다.
			var wishlist_num = button.data("num");

			//ajax
			$.ajax({
				type : "POST", // 요청 방식
				url : "deleteWishList", // 요청할 URL
				data : {wishlist_num : wishlist_num}, // key-value
				success : function(data){ // 성공시 콜백함수
					var wishlist_page_count  = $("#page_count").data('wishlist-page-count');
					console.log("위시리스트 삭제에 대한 wishlist_num : " + wishlist_num);
					console.log("위시리스트 삭제에 대한 data : " + data);
					console.log("현재 페이지 수: " + wishlist_page_count);
					// 삭제 성공시 true, 아니라면 false 반환
					if(data=='true'){ // 삭제 성공시 (true 반환시)
						// 해당 위시리스트 삭제
						button.closest(".col-md-4").remove();
						// 현재 개수(wishlist_count 요소에서 선택)를 가져와서 숫자만 추출한다.
						var current_count =  parseInt($("#wishlist_count").text().replace(/\D/g, ''));
						console.log(current_count);
						// 가져온 개수에서 -1 한다.
						var new_count = current_count - 1; 
						
						
							// 위시리스트 전체 개수가 0이하일 때
							console.log("로그 new_count:" + new_count);
							if(new_count<=0){
								$(".wishlist-section").hide() // 위시리스트 목록 숨김
								$(".pagination").hide() // 페이지네이션 숨김
								$(".empty-wishlist").show(); // 빈 위시리스트 상태의 메세지 표시
							}
							else{						
								// 개수를 "전체 개수 : N개" 형식으로 업데이트
								$("#wishlist_count").html('전체 <span class="count-bold">' + new_count + '</span>개');
								console.log("위시리스트 개수 변경 else");
								alert("위시리스트에서 삭제되었습니다.");
								if(wishlist_page_count > 1){
									
									wishlist_page_count = wishlist_page_count-1;
									if(wishlist_page_count <=0){
										wishlist_page_count = 1
									} 
									
									// 페이지 수가 2 이상일 때
									window.location.href = "./wishListPage.do?current_page="+wishlist_page_count;
								}
							}
					}
					else{ // 실패시 (false 반환시)
						// 실패 안내
						alert("삭제에 실패했습니다.");
					}
				},
				error : function(){ // 실패시 콜백함수
					console.log("error : " + error);
					alert("에러가 발생했습니다. 다시 시도해주세요.");
				}
			});
		});
	});
	
	
	</script>

   <!-- 푸터 연결 -->
   <c:import url="footer.jsp"></c:import>

   <!-- Js Plugins -->
   <!-- <script src="js/jquery-3.3.1.min.js"></script>-->
   <script src="js/bootstrap.min.js"></script>
   <script src="js/jquery.magnific-popup.min.js"></script>
   <script src="js/jquery.nice-select.min.js"></script>
   <script src="js/jquery-ui.min.js"></script>
   <script src="js/jquery.slicknav.js"></script>
   <script src="js/owl.carousel.min.js"></script>
   <script src="js/main.js"></script>

</body>
</html>