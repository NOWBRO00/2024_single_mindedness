<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예약 내역 상세 페이지</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="css/style.css" type="text/css">

<!-- jQuery 사용을 위한 연결 -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
   integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
   crossorigin="anonymous"></script>
<style type="text/css">
input{
   border: none !important;
}

h5{
   width: 120px;
   align-content: center !important;
}
</style>
</head>
<body>
   <!-- 헤더 -->
   <jsp:include page="header.jsp" />
   <br>
   <!-- 예약 내역 상세보기 -->
   <div class="container text-center">
      <div class="row">
         <div class="card">
            <br><div><h2>예약 정보</h2></div><hr>
            <div class="row g-0">
               <div class="col-md-4">
                  <br>
                  <!-- 상품 썸네일 -->
                  <img src="${reservation.reservation_product_file_dir}" class="rounded mx-auto d-block" alt="상품 사진" style="margin-left:10px;">
                  <br>
               </div>
               <div class="col-md-8">
                  <div class="card-body">
                     <!-- 상품명, 예약상태, 결제상태 -->
                     <div class="input-group mb-3" style="display: flex; align-items: flex-end; height: 40px;">
                        <a href="productDetail.do?product_num=${reservation.reservation_product_num}">
                        <h3>${reservation.reservation_product_name}</h3></a>
                        <div class="bd-hero-text"style="margin-right:3px;">
                           <span>${reservation.reservation_status}</span>
                        </div>
                        <div class="bd-hero-text">
                           <span>${reservation.reservation_payment_status}</span>
                        </div>
                     </div>
                     <!-- 예약번호 -->
                     <div class="input-group mb-3">
                        <h5>예약번호</h5>
                        <input type="text" class="form-control" value="${reservation.reservation_num}" readonly>
                     </div>
                     <span id="nameMsg"></span>
                     <!-- 예약일자 -->
                     <div class="input-group mb-3">
                        <h5>사용 예정일</h5>
                        <input type="text" class="form-control" value="${reservation.reservation_registration_date}" readonly>
                     </div>
                     <span id="nicknameMsg"></span>
                     <!-- 예약자명 -->
                     <div class="input-group mb-3">
                        <h5>예약자명</h5>
                        <input type="text" class="form-control" value="${reservation.reservation_member_name}" readonly>
                     </div>
                     <!-- 예약자 전화번호 -->
                     <div class="input-group mb-3">
                        <h5>전화번호</h5>
                        <input type="text" class="form-control" value="${reservation.reservation_member_phone}" readonly>
                     </div>
                     <!-- 결제금액, 결제방법 -->
                     <div class="input-group mb-3">
                        <h5>결제금액</h5>
                        <input type="text" class="form-control" value="${reservation.reservation_price}" readonly>
                        <div class="bd-hero-text" style="margin-left:3px; margin-top: 0.75rem;">
                           <span>${reservation.reservation_payment_method}</span>
                        </div>
                     </div>
                     <!-- 뒤로가기 버튼 -->
                     <button type="button" onclick="location.href='myReservationListPage.do'" class="btn btn-outline-secondary" style="width: 140px">뒤로가기</button>
                     <button type="button" onclick="location.href='cancelReservation.do?reservation_num=${reservation.reservation_num}'" class="btn btn-outline-secondary" style="width: 140px">취소하기</button>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </div>
   <br>
   <br>
   <br>
   <!-- 푸터 연결 -->
   <c:import url="footer.jsp"></c:import>

</body>
</html>