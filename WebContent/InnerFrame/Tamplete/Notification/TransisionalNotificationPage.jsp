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
	String msg = request.getParameter("msg");
	String nuFwdPage = request.getParameter("nuFwdPage");//depricated
	String forceBackTo = request.getParameter("forceBackTo");
	String atMenu = request.getParameter("atMenu");
	String forceGoTo = request.getParameter("forceGoTo");
	/*
	parameter ini khusus digunakan untuk balik ke info obj / after search mhs
	*/
	String objId = request.getParameter("id_obj");
	String nmm = request.getParameter("nmm");
	String npm = request.getParameter("npm");
	String kdpst = request.getParameter("kdpst");
	String obj_lvl =  request.getParameter("obj_lvl");
	String cmd =  request.getParameter("cmd");
	
	
	//if(forceGoTo!=null && !Checker.isStringNullOrEmpty(forceGoTo)) {
	//	nuFwdPage = ""+forceGoTo;
	//}
//	String atMenu = request.getParameter("atMenu");

%>


</head>
<body>
<div id="header">
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<h1 align="center"><%=msg %></h1>
		<META HTTP-EQUIV="refresh" CONTENT="5; URL=<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}<%=nuFwdPage%>?atMenu=<%=atMenu %>&forceBackTo=<%=forceBackTo %>&id_obj=<%=objId %>&nmm=<%=nmm %>&npm=<%=npm %>&kdpst=<%=kdpst %>&obj_lvl=<%=obj_lvl %>&cmd=<%=cmd %>">
		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>