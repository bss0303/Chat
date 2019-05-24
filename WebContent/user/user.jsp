<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>chat online</title>
<link rel="stylesheet" type="text/css" href="../css/user.css"/>
</head>
<body>
<script>
	if("${sessionScope.user.username}" == null || "${sessionScope.user.username}"=="") location.href="login.jsp";
</script>

<div id="top">
	<p>chat在线交流</p>
	欢迎您! <span>&nbsp;${sessionScope.user.username }&nbsp;</span>
	<a href="login.jsp" onclick="return check()">退出登录</a>
</div>

<div id="main">
<div id="left">
	<div>
		now&gt;&nbsp;<span id="nowRoom">无</span>
	</div>
	<hr>
	<div>
		<p>我的房间</p>
	</div>
	<div id="rooms">
	</div>
</div>
	<div id="right">
	</div>
	
	<div id="down">
		<textarea id="input" rows="4" cols="70" maxlength="500" placeholder="请输入内容"></textarea>
		<button id="sd" onclick="send()">发送</button>
	</div>

</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.4.1.js"></script>
<script>
var rname;
var flag = true;

	$(document).ready(function(){
		var xmlhttp;
		if(window.XMLHttpRequest){
			xmlhttp = new XMLHttpRequest();
		}else{
			xmlhttp = new ActiveXObject("Microsoft.XMLHttp");
		}
		xmlhttp.onreadystatechange=function(){
			if(xmlhttp.readyState==4 && xmlhttp.status==200){
				$("#rooms").html(xmlhttp.responseText);
			}
		}
		xmlhttp.open("GET", "../UserServlet?method=list&id=${sessionScope.user.id }", true);
		xmlhttp.send();
		$("#sd").attr("disabled","disabled");
	});
	function check(){
		if(confirm("确认退出登录吗"))
			return true;
		else return false;
	}
	//更新当前room
	function getNowRoom(o){
		if(flag == true){
			$("#sd").removeAttr("disabled");
			flag = false;
		}
		var x = o.innerText;
		if(x != rname){
			clearInterval(getNew);
			change();
			$("#right").html("");
			
			rname = x;
			$("#nowRoom").text(x);
			getNew();
			setInterval(getNew, 3000);
		}
	}
	
	//发送信息
	function send(){
		var input = $("#input").val();
		if(input == null || input ==""){
			alert("输入为空");
			return;
		}

		var date = new Date();
		var time = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
		var xmlhttp;
		if(window.XMLHttpRequest){
			xmlhttp = new XMLHttpRequest();
		}else{
			xmlhttp = new ActiveXObject("Microsoft.XMLHttp");
		}
		xmlhttp.onreadystatechange=function(){
			if(xmlhttp.readyState==4 && xmlhttp.status==200){
				console.log(xmlhttp.responseText);
				$("#input").show().focus();
			}
		}
		xmlhttp.open("POST", "../UserServlet?method=send", true);
		xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlhttp.send("&rname="+rname+"&id=${sessionScope.user.id }&time="+time+"&input="+input);
		
		$("#input").val("");
		$("#input").show().focus();
	}

	//显示新的消息
	function getNew(){
		var xmlhttp;
		if(window.XMLHttpRequest){
			xmlhttp = new XMLHttpRequest();
		}else{
			xmlhttp = new ActiveXObject("Microsoft.XMLHttp");
		}
		xmlhttp.onreadystatechange=function(){
			if(xmlhttp.readyState==4 && xmlhttp.status==200){
				if(xmlhttp.responseText != null && xmlhttp.responseText != ""){
					$("#right").append(xmlhttp.responseText);
					$("#right").scrollTop($("#right")[0].scrollHeight);
				}
			}
		}
		xmlhttp.open("GET", "../UserServlet?method=get&rname="+rname+"&t="+Math.random(), true);
		xmlhttp.send();
	}
	
	function change(){
		var xmlhttp;
		if(window.XMLHttpRequest){
			xmlhttp = new XMLHttpRequest();
		}else{
			xmlhttp = new ActiveXObject("Microsoft.XMLHttp");
		}
		xmlhttp.open("GET", "../UserServlet?method=change&rname="+rname+"&t="+Math.random(), true);
		xmlhttp.send();
	}

</script>

</body>
</html>
