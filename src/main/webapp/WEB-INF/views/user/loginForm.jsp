<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">

	<form action="/auth/loginProc" method="post">
		<div class="form-group">
			<label for="username">Username</label> 
			<input type="text" name="username" class="form-control" placeholder="Enter Username" id="username">
		</div>

		<div class="form-group">
			<label for="password">Password</label> 
			<input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
		</div>
		<button id="btn-login" class="btn btn-primary">로그인</button>
		<a href="https://kauth.kakao.com/oauth/authorize?client_id=752d31d8d512335fc564541ca5649f9d&redirect_uri=http://localhost:8000/auth/kakao/callback&response_type=code"><img height="38px" src="/image/kakao_login_button.png"></a>
	</form>
	<a href="/oauth2/authorization/google">구글 로그인</a>		<!-- 고정된 주소이기 때문에 내 맘대로 바꿔서는 안됨 -->
	<a href="/oauth2/authorization/facebook">페이스북 로그인</a>		<!-- google 과 동일 (고정) -->
	<a href="/oauth2/authorization/naver">네이버 로그인</a>

</div>

<%@ include file="../layout/footer.jsp"%>
