<!DOCTYPE html>
<head>

<%@ page import="beans.dbase.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.login.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.tools.PathFinder"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<title>Insert title here</title>
<%
	//System.out.println("bukan jnjhkhj");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	//System.out.println("dashAkademi.jsp");
%>


</head>
<body>
<div id="header">
	<ul>	
	<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Akademik/innerMenu.jsp" />
	<!--  %@ include file="innerMenu.jsp" % -->
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		
	</div>
</div>		
</body>
	