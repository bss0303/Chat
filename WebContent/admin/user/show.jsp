<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	table , td{
		border:1px solid black;
	}
	div{
		border: 1px solid blue;
	}
</style>
</head>
<body>
<script>
if("${sessionScope.admin}" == null || "${sessionScope.admin}"=="") location.href="login.jsp";
	var mess = "${requestScope.message}";
	if(mess != null && mess != ""){
		alert(mess);
	}
</script>

<div>
	<table>
		<tr>
			<td>id</td>
			<td>username</td>
			<td>pwd</td>
			<td>privilege</td>
			<td></td>
			<td></td>
			</tr>
		<c:forEach items="${us}" var="item">
			<tr>
				<td>${item.id }</td>
				<td>${item.username }</td>
				<td>${item.pwd }</td>
				<td>${item.privilege }</td>
				<td><a href="AdminUserServlet?method=gOBI&id=${item.id }">修改</a></td>
				<td><a  onclick="return check()" href="AdminUserServlet?method=del&id=${item.id }&p=${requestScope.page}">删除</a></td>
				</tr>
		</c:forEach>
	</table>
</div>
<br>
<div>
	<a id="up"><span>上一页</span></a>&nbsp;
	<span>${requestScope.page}</span>&nbsp;
	<a id="down"><span>下一页</span></a>&nbsp;
	<span>共:${requestScope.pageCount}页</span>
</div>

<script src="${pageContext.request.contextPath}/js/jquery-3.4.1.js"></script>
<script>
	$(document).ready(function(){
		var pc = parseInt("${requestScope.pageCount}");
		var p = parseInt("${requestScope.page}");
		var method = "${requestScope.method}";
		var name = "${requestScope.username}";
		
		if(p == 1) $("#up").removeAttr("href");
		else $("#up").attr("href", "AdminUserServlet?method="+method+"&p="+(p-1)+"&name="+name);
		
		if(p == pc) $("#down").removeAttr("href");
		else $("#down").attr("href", "AdminUsersServlet?method="+method+"&p="+(p+1)+"&name="+name);
	});
	function check(){
		if(confirm("确定要删除吗？"))
			return true;
		else return false;
	}
</script>
</body>
</html>