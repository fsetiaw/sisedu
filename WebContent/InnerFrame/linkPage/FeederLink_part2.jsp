<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
	String target_thsms = request.getParameter("id_smt");
%>
</head>
<body onload="document.createElement('form').submit.call(document.getElementById('forlap'))">
<form accept-charset="UTF-8" action="http://118.96.57.189:8883/login" method="POST" name="forlap" id="forlap">
	<input type="hidden" placeholder="Password"  value="<%=target_thsms %>" name="id_smt">						
	<input type="hidden" placeholder="Username"  value="031031" name="username" autocomplete="off">
	<input type="hidden" placeholder="Password"  value="usg2020" name="password">
	<input type="hidden" name="act" value="login" />
</form>
</body>
</html>