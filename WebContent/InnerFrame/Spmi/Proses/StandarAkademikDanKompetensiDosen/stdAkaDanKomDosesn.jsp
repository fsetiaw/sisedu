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
	String tipeForm = request.getParameter("formType");
	Vector vDwn = validUsr.getScopeUpd7des2012("hasDownloadMenu");
	
%>
</head>
<body>
<div id="header">
	<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/subMenu.jsp" />
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Proses/StandarAkademikDanKompetensiDosen/standarKualifikasiAkademikDanKompetensiDosen.htm" />
	</div>
</div>		
</body>
</html>