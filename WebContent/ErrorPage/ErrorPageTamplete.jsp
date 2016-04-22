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
	String forceBackTo = request.getParameter("forceBackTo");
	String atMenu = request.getParameter("atMenu");
%>


</head>
<body>
<div id="header">
	<ul>
	<%
	if(forceBackTo!=null && Checker.isStringNullOrEmpty(forceBackTo)) {
	%>	
		<li><a href="<%=Constants.getRootWeb() %><%=forceBackTo %>"  target="_self">BACK <span><b style="color:#eee">---</b></span></a></li>
	<%
	}
	else {
	%>
	<li><a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">BACK <span><b style="color:#eee">---</b></span></a></li>
	<%
	}
	%>
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<h1 align="center"><%=msg %></h1>
		<!--  META HTTP-EQUIV="refresh" CONTENT="5; URL=<%=forceBackTo%>?atMenu=<%=atMenu%>" -->
		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>