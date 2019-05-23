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

<br>
<br>
<div>
	<table>
		<tr>
			<td>id</td>
			<td>room name</td>
			<td>create time</td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			</tr>
		<c:forEach items="${rm}" var="item">
			<tr>
				<td>${item.rid }</td>
				<td>${item.rname }</td>
				<td>${item.createTime }</td>
				<td><a onclick="addUser(${item.rid})">添加用户</a></td>
				<td><a onclick="delUser(${item.rid})">删除用户</a></td>
				<td><a href="AdminServlet?method=gOBI&id=${item.rid }">修改</a></td>
				<td><a  onclick="return check()" href="AdminServlet?method=del&id=${item.rid }&p=${requestScope.page}">删除</a></td>
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
		var name = "${requestScope.rname}";
		
		if(p == 1) $("#up").removeAttr("href");
		else $("#up").attr("href", "AdminServlet?method="+method+"&p="+(p-1)+"&name="+name);
		
		if(p >= pc) $("#down").removeAttr("href");
		else $("#down").attr("href", "AdminServlet?method="+method+"&p="+(p+1)+"&name="+name);
	});
	function check(){
		if(confirm("确定要删除吗？"))
			return true;
		else return false;
	}
	function addUser(rid){
		var input = prompt("请输入要添加用户的ID 或者 用户名");
		if(input != null && input !=""){
			var xmlhttp;
			if(window.XMLHttpRequest){
				xmlhttp = new XMLHttpRequest();
			}else{
				xmlhttp = new ActiveXObject("Microsoft.XMLHttp");
			}
			xmlhttp.onreadystatechange=function(){
				if(xmlhttp.readyState==4 && xmlhttp.status==200){
					alert(xmlhttp.responseText);
				}
			}
			xmlhttp.open("GET", "AdminServlet?method=addUser&rid="+rid+"&input="+input, true);
			xmlhttp.send();
		}
	}
	
	function delUser(rid){
		var input = prompt("请输入要删除用户的ID 或者 用户名");
		if(input != null && input !=""){
			var xmlhttp;
			if(window.XMLHttpRequest){
				xmlhttp = new XMLHttpRequest();
			}else{
				xmlhttp = new ActiveXObject("Microsoft.XMLHttp");
			}
			xmlhttp.onreadystatechange=function(){
				if(xmlhttp.readyState==4 && xmlhttp.status==200){
					alert(xmlhttp.responseText);
				}
			}
			xmlhttp.open("GET", "AdminServlet?method=delUser&rid="+rid+"&input="+input, true);
			xmlhttp.send();
		}
	}
</script>
</body>
</html>