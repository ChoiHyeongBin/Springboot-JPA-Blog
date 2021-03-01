<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="layout/header.jsp"%>

<div class="container">

<c:forEach var="board" items="${boards.content}">	<!-- Controller 의 model 에 넣어둔 key 값을 items 가 받아옴 (EL 표현식에 넣음) -->
	<div class="card m-2">
		<div class="card-body">
			<h4 class="card-title">${board.title}</h4>
			<a href="/board/${board.id}" class="btn btn-primary">상세보기</a>
		</div>
	</div>
</c:forEach>

<ul class="pagination justify-content-center">	<!-- justify-content-center : Booststrap 에서 flex 로 정렬할 때 사용하는 문법 -->
	<c:choose>
		<c:when test="${boards.first}">		<!-- true 가 되면 disable 실행-->
			<li class="page-item disabled"><a class="page-link" href="?page=${boards.number - 1}">Previous</a></li>
		</c:when>
		<c:otherwise>
			<li class="page-item"><a class="page-link" href="?page=${boards.number - 1}">Previous</a></li>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${boards.last}">
			<li class="page-item disabled"><a class="page-link" href="?page=${boards.number + 1}">Next</a></li>
		</c:when>
		<c:otherwise>
			<li class="page-item"><a class="page-link" href="?page=${boards.number + 1}">Next</a></li>
		</c:otherwise>
	</c:choose>
</ul>

</div>

<%@ include file="layout/footer.jsp"%>
