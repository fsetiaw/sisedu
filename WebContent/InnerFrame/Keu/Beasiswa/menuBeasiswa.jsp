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
	//validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String callerPage = request.getParameter("callerPage");
	String atMenu = request.getParameter("atMenu");
	if(atMenu==null || atMenu.equalsIgnoreCase("null")) {
		atMenu = (String)request.getAttribute("atMenu");
	}
	//System.out.println("atMenu2="+atMenu);
	//caller page buat menentukan class-aktif
%>


</head>
<body>
<ul>
<%
//System.out.println("atMenu2a="+atMenu);
//String target = Constants.getRootWeb()+"/get.notifications";
//String uri = request.getRequestURI();
//String url = PathFinder.getPath(uri, target);
%>
		<li><a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">BACK <span><b style="color:#eee">---</b></span></a></li>
<%
//if(validUsr.isAllowTo("hasAkademikKurikulumMenu")>0) {
// kalo dah sampe sini minimal bisa liat  list paket yg ada
//System.out.println("atMenu2b="+atMenu);
String target = null;
String uri = null;
String url = null;
if(atMenu!=null && atMenu.equalsIgnoreCase("listPaket")) {
	//System.out.println("atMenu2c="+atMenu);

%>		
	<li><a href="get.listPaketBeasiswa?atMenu=listPaket" target="_self" class="active">LIST PAKET <span>BEASISWA</span></a></li>
<%		
}
else {
%>		
	<li><a href="get.listPaketBeasiswa?atMenu=listPaket" target="_self">LIST PAKET <span>BEASISWA</span></a></li>
<%
}

//addPaketBea
if(validUsr.isAllowTo("addPaketBea")>0) {
	target = "prosess.addPaketBeasiswa";
	if(atMenu!=null && atMenu.equalsIgnoreCase("addPaketBea")) {
%>		
	<li><a href="<%=target %>?atMenu=addPaketBea&scope=addPaketBea" target="_self" class="active">TAMBAH PAKET<span>BEASISWA</span></a></li>
<%		
	}
	else {
%>		
	<li><a href="<%=target %>?atMenu=addPaketBea&scope=addPaketBea" target="_self">TAMBAH PAKET<span>BEASISWA</span></a></li>
<%
	}
}

if(validUsr.isAllowTo("addJenisBea")>0) {
	//System.out.println("atMenu2d="+atMenu);
	target = Constants.getRootWeb()+"/InnerFrame/Keu/Beasiswa/formAddJenisBeasiswa.jsp";
	System.out.println("target="+target);
	uri = request.getRequestURI();
	url = PathFinder.getPath(uri, target);
	if(atMenu!=null && atMenu.equalsIgnoreCase("addJenisBea")) {
		System.out.println("atMenu2e="+atMenu);
%>		
	<li><a href="<%=target %>?atMenu=addJenisBea" target="_self" class="active">TAMBAH JENIS<span>BEASISWA</span></a></li>
<%		
	}
	else {
%>		
	<li><a href="<%=target %>?atMenu=addJenisBea" target="_self">TAMBAH JENIS<span>BEASISWA</span></a></li>
<%
	}
}

%>
</ul>

</body>
</html>