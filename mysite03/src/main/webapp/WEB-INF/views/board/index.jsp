<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th style="text-align:left">제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:set var="count" value="${fn:length(list) }"/>				
					<c:forEach begin="0" end="5" items="${list }" var="vo" varStatus="status">
						<tr>
						<c:if test="${count < pages.limit}">
							<td>${count-status.index }</td>
						</c:if>
						<c:if test="${count == pages.limit }">
							<td>${pages.totalBoard - (pages.currentPage-1)*pages.limit-status.index }</td>
						</c:if>
						<td style="text-align:left; padding-left:${vo.depth * 20}px">
						<c:choose>
							<c:when test="${vo.depth eq 0 }">
								<a href="${pageContext.request.contextPath }/board/view/${vo.no }">${vo.title }</a>
							</c:when>
							<c:otherwise>
							 <img src='${pageContext.request.contextPath }/assets/images/reply.png' /><a href="${pageContext.request.contextPath }/board/view/${vo.no }">${vo.title }</a>
							</c:otherwise>
						</c:choose>
						</td>
						<td>${vo.userName }</td>
						<td>${vo.hit }</td>
						<td>${vo.regDate }</td>
						<td>
						<c:if test="${authUser.no == vo.userNo }">
							<a href="${pageContext.request.contextPath }/board/delete/${vo.no}" class="del" style='background-image: url("${pageContext.request.contextPath  }/assets/images/recycle.png")'>삭제</a>
						</c:if>
						</td>
						</tr>
								
					</c:forEach>
					
				</table>
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
					<c:if test="${pages.currentPage!=1 }">
						<li><a href="${pageContext.request.contextPath }/board?p=${1 }">◀◀</a></li>
						<li><a href="${pageContext.request.contextPath }/board?p=${pages.prevPage }">◀</a></li>
					</c:if>
					<c:forEach var ="page" begin="${pages.startPage }" end="${pages.lastPage }" >
							<c:if test="${page==pages.currentPage}">
								<li class="selected">${page }</li>
							</c:if>
							<c:if test="${page <= pages.totalPage && page ne pages.currentPage }">
								<li><a href="${pageContext.request.contextPath }/board?p=${page}">${page }</a></li>
							</c:if>
							
							<c:if
								test="${page>pages.totalPage && pages.totalPage<pages.lastPage }">${page } </c:if>
					</c:forEach>
					
					<c:if test="${pages.currentPage <pages.totalPage }">
						<li><a href="${pageContext.request.contextPath }/board?p=${pages.nextPage }">▶</a></li>
						<li><a href="${pageContext.request.contextPath }/board?p=${pages.totalPage }">▶▶</a></li>
					</c:if>
					</ul>
				</div>
							
			<c:choose>
				<c:when test="${!empty authUser }">
				<div class="bottom">
					<a href="${pageContext.request.contextPath }/board/write" id="new-book">글쓰기</a>
				</div>				
				</c:when>
			</c:choose>
			
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>