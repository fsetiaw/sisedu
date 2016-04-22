<!DOCTYPE html>
<html>
<head>

<title>Insert title here</title>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");

Vector v= null; 
%>

</head>
<body onload="location.href='#'">
<div id="header">
<%@ include file="InnerMenu.jsp"%>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<br />
	</div>
</div>	
</body>
</html>