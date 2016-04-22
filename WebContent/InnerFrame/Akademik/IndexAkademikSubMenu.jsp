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
	String cmd = request.getParameter("cmd");
	if(cmd==null || cmd.equalsIgnoreCase("null")) {
		cmd = (String)request.getAttribute("cmd");
	}
	//caller page buat menentukan class-aktif
%>


</head>
<body>
<ul>
<%
String target = Constants.getRootWeb()+"/InnerFrame/Akademik/indexAkademik.jsp";
String uri = request.getRequestURI();
String url = PathFinder.getPath(uri, target);
%>
		<li><a href="<%=url %>">BACK <span><b style="color:#eee">---</b></span></a></li>
<%

if(validUsr.isAllowTo("allowViewKurikulum")>0) {
//System.out.println("yess allow view kurikulum");	
target = Constants.getRootWeb()+"/InnerFrame/Akademik/get.listScope";
uri = request.getRequestURI();
url = PathFinder.getPath(uri, target);
%>		
		<li><a href="<%=url %>?scope=allowViewKurikulum&callerPage=<%=Constants.getRootWeb()%>/InnerFrame/Akademik/dashKurikulum.jsp&cmd=viewKurikulum" target="_self">LIST <span>KURIKULUM</b></span></a></li>
<%
}
else {
	System.out.println("not allow view kurikulum");	
}

if(validUsr.isAllowTo("hasEditMkKurikulum")>0 ) {
target = Constants.getRootWeb()+"/InnerFrame/Akademik/get.listScope";
uri = request.getRequestURI();
url = PathFinder.getPath(uri, target);
%>		
		<li><a href="<%=url %>?scope=hasEditMkKurikulum&callerPage=<%=Constants.getRootWeb()%>/InnerFrame/Akademik/dashKurikulum.jsp" target="_self">EDIT KURIKULUM <span>& MATAKULIAH</b></span></a></li>
<%
}

%>
</ul>

</body>
</html>