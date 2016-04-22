<!DOCTYPE html>
<head>
<%@ page import="beans.dbase.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.login.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<title>Insert title here</title>
<%
	
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
%>

</head>
<body>
<div id="header">
	<ul>
		<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/StoredProcedure/subSummaryMenu.jsp" />
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- 
		<a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/StoredProcedure/go.analisaMakulMhs?tabMenu=forecast" target="inner_iframe">Hitung Jumlah<span>Mhs</span></a>
		 -->
		<a href="alertPreForecast.jsp?tabMenu=forecast" target="inner_iframe">Hitung Jumlah<span>Mhs</span></a> 
	</div>
</div>		
</body>
	