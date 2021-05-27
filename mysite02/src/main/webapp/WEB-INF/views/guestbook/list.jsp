<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.douzone.mysite.repository.GuestbookRepository"%>
<%@page import="com.douzone.mysite.vo.GuestbookVo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	List<GuestbookVo> list = new GuestbookRepository().find();
	int count = list.size();
 %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="<%=request.getContextPath() %>/assets/css/guestbook.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<form action="<%=request.getContextPath() %>/guestbook" method="post">
					<input type="hidden" name="a" value="add">
					<table>
						<tr>
							<td>이름</td><td><input type="text" name="name"></td>
							<td>비밀번호</td><td><input type="password" name="password"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="content" id="content"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>
				<ul>
					<li>
					
					<% for(GuestbookVo vo : list) { 
						count--;
						String content = vo.getMessage().replace("\r\n", "<br>");
					%>
						<table>
							<tr>
								<td><%= count+1 %></td>
								<td><%= vo.getName() %></td>
								<td><%= df.format(vo.getRegDate()) %></td>
								<td><a href="<%=request.getContextPath()%>/guestbook?a=deleteFormAction&no=<%=vo.getNo() %>">삭제</a></td>
							</tr>
							<tr>
								<td colspan=4>
								<%=content %>	
								</td>
							</tr>
						</table>
						<br>
						<%} %>
					</li>
				</ul>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp"/>
		<jsp:include page="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>