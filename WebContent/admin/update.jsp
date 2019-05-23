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

<form id="form" name="form" action="AdminServlet?method=update&id=${rm.rid }" method="post">
	<div>
		房间名：<input type="text" name="rname" id="rname" value="${rm.rname }" maxlength="15"/>
	</div>
	<div>
		创建日期：<input type="text" name="cT" id="cT" maxlength="16" value="${rm.createTime }"/>
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
			return false;
		}
		var x = name.charAt(0);
		if(x<='9' && x >= '0'){
			alert("房间名首字符不能是数字");
			return false;
		}
		var cT = $("#cT").val();
		if(cT==null || cT == ""){
			alert("请填写创建日期");
			return false;
		}
		return true;
	});
</script>
</body>
</html>