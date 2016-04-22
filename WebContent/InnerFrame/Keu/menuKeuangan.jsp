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
	validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String callerPage = request.getParameter("callerPage");
	String atMenu = request.getParameter("atMenu");
	if(atMenu==null || atMenu.equalsIgnoreCase("null")) {
		atMenu = (String)request.getAttribute("atMenu");
	}
	//caller page buat menentukan class-aktif
%>


</head>
<body>
<ul>
<%
String target = Constants.getRootWeb()+"/get.notifications";
String uri = request.getRequestURI();
String url = PathFinder.getPath(uri, target);
%>
		<li><a href="<%=url %>">BACK <span><b style="color:#eee">---</b></span></a></li>
<%
//if(validUsr.isAllowTo("hasAkademikKurikulumMenu")>0) {
target = Constants.getRootWeb()+"/InnerFrame/Keu/Beasiswa/indexBeasiswa.jsp";
uri = request.getRequestURI();
url = PathFinder.getPath(uri, target);
//get.listPaketBeasiswa?atMenu=listPaket
%>		
		<li><a href="<%=url %>" target="_self">BEASISWA <span><b style="color:#eee">---</b></span></a></li>
<%
//}

%>
</ul>

</body>
</html>