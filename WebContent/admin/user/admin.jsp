<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.left, .right{
	margin-top:5px;
}
.top{
	width:100%;
}
.top2{
	overflow:hidden;
}
#main{
	width:100%;
	height:480px;//每行40
}
.a{
	font-size:20px;
}
div{
	border:1px solid red;
	margin:20px;
}
</style>
</head>
<body>
<script>
	if("${sessionScope.admin}" == null || "${sessionScope.admin}"=="") location.href="login.jsp";
</script>
<div class="top">
	<p style="text-align:center; font-size:30px;">用户管理</p>
	<span>欢迎您! </span>
</div>

<div class="top2">
	<div class="top2_left" style="float:left; margin-top:20px;">
		<button id="add"><span>添加新用户</span></button>
	</div>
	<div class="top2_center" style="float:right;">
		<span class="a">输入&nbsp;用户名&nbsp;或者&nbsp;id&nbsp;：</span>
		<input type="text" maxlength="15" id="input" />
		<button id="search">搜索</button>
	</div>
</div>

<iframe id="main">

</iframe>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.4.1.js"></script>
<script>
	$(document).ready(function(){
		$("#main").attr("src", "../../AdminUserServlet?method=show&p=1");
	});
	$("#add").click(function(){
		$("#main").attr("src", "add.jsp");
	});
	$("#search").click(function(){
		var input = $("#input").val();

		if(input == null || input ==""){
			$("#main").attr("src", "../../AdminUserServlet?method=show&p=1");
		}else if(input.charAt(0) >='0' && input.charAt(0) <= '9'){
			$("#main").attr("src", "../../AdminUserServlet?method=searchId&id="+input);
		}else{
			$("#main").attr("src", "../../AdminUserServlet?method=searchName&name="+input+"&p=1");
		}
	});
</script>
</body>
</html>