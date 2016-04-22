<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body onload="document.createElement('form').submit.call(document.getElementById('login'))" >


<form action="http://localhost/moodle/login/index.php" method="post" name="form" id="login">
<%

beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
if(validUsr.getObjNickNameGivenObjId().contains("ADMIN") || validUsr.getNpm().equalsIgnoreCase("0000812100004")) {
%>
  <p><input type="hidden" name="username" size="15" value="admin_edu"/></p>
  <p><input type="hidden" name="password" size="15" value="M00dle_dud3"/></p>
  <!--  p><input type="submit" name="Submit" value="Login" /></p-->
<%
}
else {
	%>
	  <p><input type="hidden" name="username" size="15" value="<%=validUsr.getNpm()+"_Usr"%>"/></p>
	  <p><input type="hidden" name="password" size="15" value="<%=validUsr.getNpm()+"_Usr_pwd"%>"/></p>
	  <!--  p><input type="submit" name="Submit" value="Login" /></p-->
	<%
}
%>  
</form>
</body>
</html>