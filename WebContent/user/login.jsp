<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>chat login</title>
<link rel="stylesheet" type="text/css" href="../css/userLogin.css"/>
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
	<h2>welcome to chat</h2>
	<form id="form" method="post" action="${pageContext.request.contextPath}/UserServlet?method=login">
		<input type="text" name="username" id="username" maxlength="15" placeholder="请输入用户名"/>
			<input type="password" name="pwd" id="pwd" maxlength="16" placeholder="请输入密码"/>
		<button type="submit" id="submit">登  录</button>
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