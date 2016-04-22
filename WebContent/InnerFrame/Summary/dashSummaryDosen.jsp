<!DOCTYPE html>
<html>
<head>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>
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
	//String thsms_pmb = Checker.getThsmsPmb();
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String [] listKdpst = Constants.getListKdpstProdi();
	
	//Vector v = (Vector)session.getAttribute("vSummaryPMB");
	//String target_thsms = request.getParameter("target_thsms");
	//if(target_thsms==null || target_thsms.equalsIgnoreCase("null")) {
	//	target_thsms = ""+thsms_pmb;
	//}
	//Vector vListThsms = Tool.returnTokensListThsms("20001", thsms_pmb);
	
%>


</head>
<body>
<div id="header">
<jsp:include page="subSummaryDosenMenu.jsp" />
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />

	</div>
</div>	
</body>
</html>