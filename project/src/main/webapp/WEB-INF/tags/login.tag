<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" href="css/style.css" type="text/css">

<c:if test="관리자라면">
   <a href="joinPage.do" class="bk-btn">관리자 페이지</a>
</c:if>
<c:if test="사장님이라면">
   <a href="joinPage.do" class="bk-btn">사장님 페이지</a>
</c:if>
<c:if test="${empty member_id}">
   <a href="joinPage.do" class="bk-btn">회원가입</a>
   <a href="loginPage.do" class="bk-btn"> 로그인 </a>
</c:if>
<c:if test="${not empty member_id}">
   <a href="mypage.do" class="bk-btn">마이페이지</a>
   <a href="logout.do" class="bk-btn">로그아웃</a><br>
</c:if>