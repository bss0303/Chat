<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">
if("${sessionScope.admin}" == null || "${sessionScope.admin}"=="") location.href="login.jsp";
	var mess = "${message}";
	if(mess != null && mess!=""){
		alert(mess);
	}
</script>

<form id="form" name="form" action="AdminUserServlet?method=update&id=${us.id }" method="post">
	<div>
		用户名：<input type="text" name="username" id="username" value="${us.username}" maxlength="15"/><span id="username0"></span>
	</div>
	<div>
		密码：<input type="password" name="pwd" id="pwd" maxlength="16" value="${us.pwd}"/>
	</div>
	<div>
		用户等级：<input type="radio" name="pri" value="1" id="S"><span>超级管理员</span><br>
			<input type="radio" name="pri" value="0" id="A"><span>普通用户</span>
	</div>
	<div>
		<button type="submit">提  交</button>
	</div>
</form>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.4.1.js"></script>
<script>
$(document).ready(function(){
	if("${us.privilege}" == "1"){
		$("#S").attr("checked", "checked");
	}else {
		$("#A").attr("checked", "checked");
	}
});

$("#username").keyup(function(){
	var x = $("#username").val().charAt(0);
	if(x<='9' && x >= '0'){
		$("#username0").text("用户名首字母不能是数字");
	}else{
		$("#username0").text("");
	}
});
	$("#form").submit(function(){
		var name = $("#username").val();
		if(name==null || name== ""){
			alert("用户名不能为空");
			return false;
		}
		var x = name.charAt(0);
		if(x<='9' && x >= '0'){
			alert("用户名首字符不能是数字");
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