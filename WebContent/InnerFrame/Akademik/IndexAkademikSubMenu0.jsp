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
	beans.login.InitSessionUsr validUsr1 = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String callerPage = request.getParameter("callerPage");
	String atMenu = request.getParameter("atMenu");
	if(atMenu==null || atMenu.equalsIgnoreCase("null")) {
		atMenu = (String)request.getAttribute("atMenu");
	}
	String mdl = request.getParameter("mdl");
	
	//caller page buat menentukan class-aktif
%>


</head>
<body>
<ul>
<%
String target = Constants.getRootWeb()+"/get.notifications";
String uri = request.getRequestURI();
String url = PathFinder.getPath(uri, target);
if(atMenu!=null && atMenu.equalsIgnoreCase("vba")) {
%>
		<li><<a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">BACK <span><b style="color:#eee">---</b></span></a></li>
<%
}
else {
%>
		<li><a href="get.notifications">BACK <span><b style="color:#eee">---</b></span></a></li>
<%
}



if(validUsr1.isAllowTo("hasAkademikKurikulumMenu")>0) {
target = Constants.getRootWeb()+"/InnerFrame/Akademik/dashKurikulum.jsp";
uri = request.getRequestURI();
url = PathFinder.getPath(uri, target);
System.out.println("target link = "+target);
System.out.println("kur link = "+url);
%>		
		<li><a href="${pageContext.request.contextPath}/InnerFrame/Akademik/dashKurikulum.jsp" target="_self">KURIKULUM <span><b style="color:#eee">---</b></span></a></li>
<%
}

if(validUsr1.isAllowTo("mba")>0) {
	target = Constants.getRootWeb()+"/InnerFrame/Akademik/dashKurikulum.jsp";
	uri = request.getRequestURI();
	url = PathFinder.getPath(uri, target);
	if(atMenu!=null && atMenu.equalsIgnoreCase("mba")) {
%>		
	<li><a href="get.listScope?scope=mba&callerPage=${pageContext.request.contextPath}/InnerFrame/Akademik/indexAkademik.jsp&atMenu=mba&cmd=mba&scopeType=prodiOnly" target="_self" class="active">BAHAN<span>AJAR</b></span></a></li>		
<%	
	}
	else {
%>		
	<li><a href="get.listScope?scope=mba&callerPage=${pageContext.request.contextPath}/InnerFrame/Akademik/indexAkademik.jsp&atMenu=mba&cmd=mba&scopeType=prodiOnly" target="_self">BAHAN<span>AJAR</b></span></a></li>		
<%
	}	
}

//if(validUsr1.isAllowTo("lms")>0) {
if((mdl!=null && !Checker.isStringNullOrEmpty(mdl)) || validUsr1.getObjNickNameGivenObjId().contains("ADMIN")) {	
	if(atMenu!=null && atMenu.equalsIgnoreCase("lms")) {
%>		
	<li><a href="go.fwdLink?linkTo=lms" target="_self" class="active">MY<span>MOODLE</b></span></a></li>		
<%	
	}
	else {
%>		
	<li><a href="go.fwdLink?linkTo=lms" target="_self">MY<span>MOODLE</b></span></a></li>		
<%
	}	
}

%>
</ul>

</body>
</html>