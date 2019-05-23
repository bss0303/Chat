<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<script>
	if("${sessionScope.admin}" == null || "${sessionScope.admin}"=="") location.href="login.jsp";
</script>
<form id="form" name="form" action="../AdminServlet?method=add" method="post">
<div>
房间名：<input type="text" name="rname" id="rname" maxlength="15"/>
</div>
<div>
创建时间：<input type="text" name="cT" id="cT" maxlength="20"/>
</div>
<div>
<button type="submit">提  交</button>
</div>
</form>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.4.1.js"></script>
<script>
	$("#form").submit(function(){
		var name = $("#rname").val();
		if(name==null || name== ""){
			alert("房间名不能为空");
			$("#rname").focus();
			return false;
		}
		var x = name.charAt(0);
		if(x<='9' && x >= '0'){
			alert("房间名首字符不能是数字");
			return false;
		}
		var cT = $("#cT").val();
		if(cT==null || cT == ""){
			alert("请填写创建时间");
			return false;
		}
		return true;
	});
</script>
</body>
</html>