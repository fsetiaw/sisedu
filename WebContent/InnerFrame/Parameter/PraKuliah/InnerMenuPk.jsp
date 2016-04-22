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
<!--  body onload="location.href='#'" -->
<body>
<ul>
<%
String target = Constants.getRootWeb()+"/InnerFrame/Parameter/dashParameter.jsp";
String uri = request.getRequestURI();
String url = PathFinder.getPath(uri, target);
%>
	<li><a href="<%=url %>">BACK <span><b style="color:#eee">---</b></span></a></li>
<%
//hasMenuParamPraKul
if(validUsr.isAllowTo("paramKlsKuliah")>0 ) {
target = Constants.getRootWeb()+"/InnerFrame/Parameter/PraKuliah/KelasKuliah/formParamPengajuanKelas.jsp";
uri = request.getRequestURI();
url = PathFinder.getPath(uri, target);
	if(atMenu!=null && atMenu.equalsIgnoreCase("kelasKuliah")) {
%>		
	<li><a href="<%=url+"?atMenu=kelasKuliah&scopeKampusCmd=paramKlsKuliah" %>" target="_self" class="active">PENGAJUAN<span>KELAS PERKULIAHAN</b></span></a></li>
<%
	}
	else {
%>		
	<li><a href="<%=url+"?atMenu=kelasKuliah&scopeKampusCmd=paramKlsKuliah" %>" target="_self">PENGAJUAN<span>KELAS PERKULIAHAN</b></span></a></li>
<%	
	}
}
%>
</ul>

</body>
</html>