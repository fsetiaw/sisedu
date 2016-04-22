<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String msg = request.getParameter("errMsg");
	String backTo = request.getParameter("backTo");
	String atMenu = request.getParameter("atMenu");
%>


</head>
<body>
<div id="header">

</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<br/>
		<h1 align="center"><%=msg %></h1>
		<META HTTP-EQUIV="refresh" CONTENT="5; URL=http://<%=backTo%>">
		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>