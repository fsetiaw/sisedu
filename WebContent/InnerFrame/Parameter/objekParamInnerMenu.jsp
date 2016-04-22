
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

		<li><a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">BACK <span><b style="color:#eee">---</b></span></a></li>
<%
String target = null;
String uri = null;
String url = null;
//paramCalendar#paramObject#

if(validUsr.isAllowTo("createNuObj")>0) {
target = Constants.getRootWeb()+"/InnerFrame/Parameter/Obj/go.prepDataForCreation";
uri = request.getRequestURI();
url = PathFinder.getPath(uri, target);
	if(atMenu!=null && atMenu.equalsIgnoreCase("createObj")) {
%>		
		<li><a href="<%=url %>?scope=createNuObj&atMenu=createObj" target="_self" class="active">BUAT TIPE<span>OBJEK BARU</span></a></li>
<%	
	}
	else {
%>		
		<li><a href="<%=url %>?scope=createNuObj&atMenu=createObj" target="_self">BUAT TIPE<span>OBJEK BARU</span></a></li>
<%
	}
}
if(validUsr.isAllowTo("editObjParam")>0 ) {
//target = Constants.getRootWeb()+"/InnerFrame/Parameter/Obj/go.prepDataForEdit";
/* 
*Diganti tidak lagi melalui versi json, karena ada limit length,
jadi dialihkan ke servlet seperti biasa
target = Constants.getRootWeb()+"/InnerFrame/json/tamplete/select/selectObj.jsp";
uri = request.getRequestURI();
url = PathFinder.getPath(uri, target);
*/

	if(atMenu!=null && atMenu.equalsIgnoreCase("editObj")) {
%>		
		<li><a href="get.tampleteAvailableSelection?scope=editObjParam&atMenu=editObj&backTo=<%="/InnerFrame/Parameter/dashObjekParam.jsp" %>" target="_self" class="active">EDIT TIPE<span>OBJEK</span></a></li>
<%	
	}
	else {
%>		
		<li><a href="get.tampleteAvailableSelection?scope=editObjParam&atMenu=editObj&backTo=<%="/InnerFrame/Parameter/dashObjekParam.jsp" %>" target="_self">EDIT TIPE<span>OBJEK</span></a></li>
<%
	}
}



%>
</ul>

</body>
</html>