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
String target = null;
String uri = null;
String url = null;

	validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String callerPage = request.getParameter("callerPage");
	String atMenu = request.getParameter("atMenu");
	if(atMenu==null || atMenu.equalsIgnoreCase("null")) {
		atMenu = (String)request.getAttribute("atMenu");
	}
	String backTo = ""+request.getParameter("backTo");
	//if(Checker.isStringNullOrEmpty(backTo)) {
	//	backTo = ""+request.getParameter("callerPage");
	//}
	//System.out.println("back-to = "+backTo);
	//caller page buat menentukan class-aktif
%>


</head>
<body>
<ul>
<%
//String target = Constants.getRootWeb()+"/InnerFrame/home.jsp";
//String uri = request.getRequestURI();
//String url = PathFinder.getPath(uri, target);
/*
String target = Constants.getRootWeb()+"/InnerFrame/Akademik/indexAkademik.jsp";
String uri = request.getRequestURI();
String url = PathFinder.getPath(uri, target);
*/
//overide back to value
if(atMenu!=null && atMenu.equalsIgnoreCase("jumMhsPerMk")) {
	backTo = "get.listScope?scope=hasSubMenuAkdmkPerkuliahan&atMenu=jumMhsPerMk&cmd=viewMhsPerKelas&callerPage="+Constants.getRootWeb()+"/InnerFrame/Summary/dashKelasPerkuliahan.jsp";
}
if(Checker.isStringNullOrEmpty(backTo)) {
	backTo = "indexSummaryInfoPerkuliahanMenu.jsp";
}
//System.out.println("back-to = "+backTo);
%>
<li><a href="<%=backTo %>" target="inner_iframe">BACK <span><b style="color:#eee">---</b></span></a></li>
<%
if(validUsr.isAllowTo("hasSubMenuAkdmkPerkuliahan")>0) {
/*
target = Constants.getRootWeb()+"/InnerFrame/Akademik/get.listScope";
uri = request.getRequestURI();
url = PathFinder.getPath(uri, target);
*/
	if(atMenu!=null && atMenu.equalsIgnoreCase("jumMhsPerMk")) {
%>		
		<li><a href="get.listScope?scopeType=prodiOnly&scope=hasSubMenuAkdmkPerkuliahan&atMenu=jumMhsPerMk&cmd=viewMhsPerKelas&callerPage=<%=Constants.getRootWeb()%>/InnerFrame/Summary/dashKelasPerkuliahan.jsp" target="_self" class="active">TOTAL MHS PER-<span>KELAS PERKULIAHAN</span></a></li>
<%
		
	}
	else {
%>		
		<li><a href="get.listScope?scopeType=prodiOnly&scope=hasSubMenuAkdmkPerkuliahan&atMenu=jumMhsPerMk&cmd=viewMhsPerKelas&callerPage=<%=Constants.getRootWeb()%>/InnerFrame/Summary/dashKelasPerkuliahan.jsp" target="_self">TOTAL MHS PER-<span>KELAS PERKULIAHAN</span></a></li>
<%
	}
}
if(validUsr.isAllowTo("hasSubMenuAkdmkRamalTotMhsPerMk")>0) {
	if(atMenu!=null && atMenu.equalsIgnoreCase("blmAmbilMk")) {
%>		
		<!--  li><a href="get.totMhsBlmAmbilMk?atMenu=blmAmbilMk&backTo=blm.jsp" target="_self" class="active">TOTAL MHS BELUM<span>MENGAMBIL MATAKULIAH</span></a></li -->
		<li><a href="get.listScope?scopeType=prodiOnly&scope=sumMhsBlmAmbilMk&atMenu=blmAmbilMk&fwdPage=get.totMhsBlmAmbilMk&callerPage=<%=Constants.getRootWeb()%>/InnerFrame/Summary/dashKelasPerkuliahan.jsp" target="_self" class="active">TOTAL MHS BELUM<span>MENGAMBIL MATAKULIAH</span></a></li>
<%
		
	}
	else {
%>		
			<!--  li><a href="get.totMhsBlmAmbilMk?atMenu=blmAmbilMk&backTo=blm.jsp" target="_self">TOTAL MHS BELUM<span>MENGAMBIL MATAKULIAH</span></a></li -->
			<li><a href="get.listScope?scopeType=prodiOnly&scope=sumMhsBlmAmbilMk&atMenu=blmAmbilMk&fwdPage=get.totMhsBlmAmbilMk&callerPage=<%=Constants.getRootWeb()%>/InnerFrame/Summary/dashKelasPerkuliahan.jsp" target="_self">TOTAL MHS BELUM<span>MENGAMBIL MATAKULIAH</span></a></li>
<%
	}
}

%>
</ul>

</body>
</html>