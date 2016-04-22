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
String target = "get.notifications";
String uri = request.getRequestURI();
String url = PathFinder.getPath(uri, target);
%>
		<li><a href="<%="get.notifications" %>">BACK <span><b style="color:#eee">---</b></span></a></li>
<%
//paramCalendar#paramObject#
if(validUsr.isAllowTo("cmd0")>0) {
target = "prep.prepFormCmd";
	if(atMenu!=null && atMenu.equalsIgnoreCase("cmd0")) {
%>		
	<li><a href="<%=target %>?atMenu=cmd0" target="_self" class="active">COMMAND<span>PARAMETER</span></a></li>
<%	
	}
	else {
%>		
		<li><a href="<%=target %>?atMenu=cmd0" target="_self">COMMAND<span>PARAMETER</span></a></li>
<%
	}
}	

if(validUsr.isAllowTo("paramObject")>0 ) {
target = Constants.getRootWeb()+"/InnerFrame/Parameter/dashObjekParam.jsp";
uri = request.getRequestURI();
url = PathFinder.getPath(uri, target);
%>		
		<li><a href="<%=url %>" target="_self">OBJEK<span>PARAMETER</b></span></a></li>
<%
}


if(validUsr.isAllowTo("regrl")>0) {
target = "prep.prepParamReg";
	if(atMenu!=null && atMenu.equalsIgnoreCase("reg")) {
%>		
	<li><a href="<%=target %>?atMenu=reg" target="_self" class="active">DAFTAR ULANG<span>SETTING</span></a></li>
<%	
	}
	else {
%>		
		<li><a href="<%=target %>?atMenu=reg" target="_self">DAFTAR ULANG<span>SETTING</span></a></li>
<%
	}
}	

String scopeCmd="cr";
if(validUsr.isAllowTo(scopeCmd)>0) {
target = "prep.prepParamCuti";
	if(atMenu!=null && atMenu.equalsIgnoreCase("cutirul")) {
%>		
	<li><a href="<%=target %>?atMenu=cutirul&scopeCmd=<%=scopeCmd %>" target="_self" class="active">CUTI<span>SETTING</span></a></li>
<%	
	}
	else {
%>		
		<li><a href="<%=target %>?atMenu=cutirul&scopeCmd=<%=scopeCmd %>" target="_self">CUTI<span>SETTING</span></a></li>
<%
	}
}	

if(validUsr.isAllowTo("ckls")>0) {
target = "prep.prepParamCakupanKelas";
	if(atMenu!=null && atMenu.equalsIgnoreCase("ckls")) {
%>		
	<li><a href="<%=target %>?atMenu=ckls" target="_self" class="active">CAKUPAN<span>PILIHAN KELAS</span></a></li>
<%	
	}
	else {
%>		
		<li><a href="<%=target %>?atMenu=ckls" target="_self">CAKUPAN<span>PILIHAN KELAS</span></a></li>
<%
	}
}	

if(validUsr.isAllowTo("paramCalendar")>0) {
//target = Constants.getRootWeb()+"/InnerFrame/Parameter/KalenderAkademik/dashKalender.jsp";
//uri = request.getRequestURI();
//url = PathFinder.getPath(uri, target);
target = "prep.prepFormKalender";
	if(atMenu!=null && atMenu.equalsIgnoreCase("kalAka")) {
%>		
	<li><a href="<%=target %>?atMenu=kalAka" target="_self" class="active">KALENDER<span>AKADEMIK</span></a></li>
<%	
	}
	else {
%>		
		<li><a href="<%=target %>?atMenu=kalAka" target="_self">KALENDER<span>AKADEMIK</span></a></li>
<%
	}
}	

//hasMenuParamPraKul
if(validUsr.isAllowTo("hasMenuParamPraKul")>0 ) {
target = Constants.getRootWeb()+"/InnerFrame/Parameter/PraKuliah/dashParameterPk.jsp";
uri = request.getRequestURI();
url = PathFinder.getPath(uri, target);
%>		
		<li><a href="<%=url %>" target="_self">PARAMETER<span>PRA-KULIAH</b></span></a></li>
<%
}

Vector vScope_cmd = validUsr.getScopeObjIdFinal("pkua", true, true, false); 
session.setAttribute("vScope_cmd", vScope_cmd);
if(vScope_cmd!=null && vScope_cmd.size()>0) {
	target = "prep.prepParamKartuUjianRules";
%>		
		<li><a href="<%=target %>" target="_self">PARAMETER<span>KARTU UJIAN RULES</b></span></a></li>
<%
}
%>
</ul>

</body>
</html>