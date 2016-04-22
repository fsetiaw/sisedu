<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
	
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");

Vector v= null; 

String id_obj = request.getParameter("id_obj");
String nmm = request.getParameter("nmm");
String npm = request.getParameter("npm");
String obj_lvl= request.getParameter("obj_lvl");
String kdpst=request.getParameter("kdpst");
String cmd=request.getParameter("cmd");
String am_i_stu = request.getParameter("am_i_stu");
//System.out.println("nonpm="+npm);

%>


</head>
<body>
<div id="header">
<%@ include file="pengajuanMhsInnerMenu.jsp"%>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		
		<%

	%>
	</div>
</div>	
</body>
</html>