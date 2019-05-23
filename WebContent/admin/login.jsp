<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
body{
	background:url("${pageContext.request.contextPath}/img/1.jpg");
}
	.main{
		width:320px;
		margin-left:50%;
		margin-top:20%;
	}
	div{
		border:1px solid red;
	}
	.a{
		margin:5px;
	}
	span{
		color:red;
	}
	#submit{
		margin-left:40%;
	}
	.b{
		margin-left:4px;
	}
</style>
</head>
<body>
<%
	Object message = request.getAttribute("message");
	if(message!=null && !"".equals("message")){%>
		<script type="text/javascript">
			alert("<%=message%>");
		</script>
	<%}
%>


<div class="main">
<h2 style="text-align:center;"><span class="b">chat后台登录</span></h2>
<form id="form" method="post" action="${pageContext.request.contextPath}/AdminServlet?method=login">
	<div class="a">
	<span class="b">用户名：</span><input type="text" name="username" id="username" maxlength="15"/>
		</div>
	<div class="a">
	<span class="b">密码&nbsp;&nbsp;&nbsp;：</span><input type="password" name="pwd" id="pwd" maxlength="16"/>
		</div>
	<div class="a"><button type="submit" name="submit" id="submit">登  录</button></div>
	</form>
	</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.4.1.js"></script>
<script>
	$("#username").focus();
	$("#form").submit(function(){//表单验证
		var name = $("#username").val();
		if(name==null || name== ""){
			alert("用户名不能为空");
			return false;
		}
		var pwd = $("#pwd").val();
		if(pwd==null || pwd == ""){
			alert("密码不能为空");
			return false;
		}
		return true;
	});
</script>
</body>
</html>