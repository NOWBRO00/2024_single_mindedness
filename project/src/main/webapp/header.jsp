<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="description" content="Sona Template">
<meta name="keywords" content="Sona, unica, creative, html">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title> 고래밥 </title>

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
    </style>

</head>
<body>
    <!-- 모바일 메뉴섹션 시작 -->
    <div class="offcanvas-menu-overlay"></div>
    <div class="canvas-open">
        <i class="icon_menu"></i>
    </div>
    <div class="offcanvas-menu-wrapper">
        <div class="canvas-close">
            <i class="icon_close"></i>
        </div>
        <div class="header-configure-area">
            <!-- 링크를 통해 모바일 메뉴에서 검색 기능으로 이동 -->
            <a href="#" class="bk-btn">검색하기</a>
        </div>
        <nav class="mainmenu mobile-menu">
            <ul>
                <!-- 모바일 메뉴 항목 시작 -->
                <li><a href="productListPage.do?product_searchContent=null&searchKeyword=null">낚시장소</a>
                    <ul class="dropdown">
                        <!-- 드롭다운 메뉴 항목 -->
                        <li><a href="productListPage.do?product_searchContent=null&searchKeyword=null">바다</a></li>
                        <li><a href="productListPage.do?product_searchContent=null&searchKeyword=null">민물</a></li>
                    </ul>
                </li>
                <li><a href="productListPage.do?product_searchContent=null&searchKeyword=null">낚시유형</a>
                    <ul class="dropdown">
                        <li><a href="productListPage.do?product_searchContent=null&searchKeyword=null">낚시터</a></li>
                        <li><a href="productListPage.do?product_searchContent=null&searchKeyword=null">낚시배</a></li>
                    </ul>
                </li>
                <li>마이 메뉴
                    <ul class="dropdown">
                        <li><a href="myReservationListPage.do">내 예약</a></li>
                        <li><a href="wishListPage.do">위시리스트</a></li>
                    </ul>
                </li>
                <li><a href="boardListPage.do?searchContent=BOARD_ALL">게시판</a>
                    <ul class="dropdown">
                        <li><a href="boardListPage.do?searchContent=BOARD_ALL">자유 게시판</a></li>
                    </ul>
                </li>
            </ul>
        </nav>
        <div class="nav-right search-switch">
            <i class="icon_search"></i>
        </div>
        <div id="mobile-menu-wrap"></div>
        <div class="top-social">
            <!-- 소셜 미디어 링크 -->
            <a href="#"><i class="fa fa-facebook"></i></a> 
            <a href="#"><i class="fa fa-twitter"></i></a> 
            <a href="#"><i class="fa fa-tripadvisor"></i></a> 
            <a href="#"><i class="fa fa-instagram"></i></a>
        </div>
        <ul class="top-widget">
            <li><i class="fa fa-phone"></i> (12) 345 67890</li>
            <li><i class="fa fa-envelope"></i> info.colorlib@gmail.com</li>
        </ul>
    </div>
    <!-- 모바일 메뉴섹션 종료 -->

    <!-- 헤더 섹션 시작 -->
    <header class="header-section">
        <div class="top-nav">
            <div class="container">
                <div class="row">
                    <div class="col-lg-6">
                        <ul class="tn-left">
                            <!-- 헤더의 왼쪽 부분: 연락처 정보 -->
                            <li><i class="fa fa-phone"></i>1588-5890</li>
                            <li>
                               <!-- 사용자 레벨에 따른 등급 -->
                               <c:if test="${empty member_id}">
                                  <c:import url="role.jsp"></c:import>
                               </c:if>
                               ${member.member_nickname}
                            </li>
                        </ul>
                    </div>
                    <div class="col-lg-6">
                        <div class="tn-right">
                            <div class="top-social">
                                <!-- 소셜 미디어 링크 -->
                                <a href="https://koreaisacademy.com/"><i class="fa fa-facebook"></i></a> 
                                <a href="https://koreaisacademy.com/"><i class="fa fa-twitter"></i></a>
                                <a href="https://koreaisacademy.com/"><i class="fa fa-instagram"></i></a>
                            </div>
                            <!-- 사용자 로그인 태그 -->
                            <mytag:login />
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="menu-item">
            <div class="container">
                <div class="row">
                    <div class="col-lg-2">
                        <div class="logo">
                            <!-- 사이트 로고 -->
                            <a href="main.do"> <img src="img/logo.png" alt="로고입니다."></a>
                        </div>
                    </div>
                    <div class="col-lg-10">
                        <div class="nav-menu">
                            <nav class="mainmenu">
                                <ul>
                                    <!-- 내비게이션 메뉴 시작 -->
                                    <li><a href="productListPage.do?product_searchContent=null&product_searchKeyword=null">낚시장소</a>
                                        <ul class="dropdown">
                                            <!-- 드롭다운 메뉴 항목 -->
                                            <li><a href="productListPage.do?product_location=바다">바다</a></li>
                                            <li><a href="productListPage.do?product_location=민물">민물</a></li>
                                        </ul>
                                    </li>
                                    <li><a href="productListPage.do?product_searchContent=null&searchKeyword=null">낚시유형</a>
                                        <ul class="dropdown">
                                            <li><a href="productListPage.do?product_category=낚시터">낚시터</a></li>
                                            <li><a href="productListPage.do?product_category=낚시배">낚시배</a></li>
                                        </ul>
                                    </li>
                                    <li><a>마이 메뉴</a>
                                   <ul class="dropdown">
                                       <li><a href="myReservationListPage.do">내 예약</a></li>
                                       <li><a href="wishListPage.do">위시리스트</a></li>
                                   </ul>
                               </li>
                                    <li><a href="boardListPage.do">게시판</a>
                                        <ul class="dropdown">
                                            <li><a href="boardListPage.do">자유 게시판</a></li>
                                        </ul>
                                    </li>
                                    <li>
                                        <!-- 검색창 포함 -->
                                        <div class="search-container">
                                            <div class="search-box">
                                                <!-- 검색폼 -->
                                                <form action="productListPage.do">
                                                    <input type="text" class="search-input" name="searchKeyword" placeholder="상품검색...">
                                                    <button class="search-button" type="submit">검색</button>
                                                </form>
                                            </div>
                                        </div>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </header>
    <!-- 헤더 섹션 종료 -->
    
</body>
</html>