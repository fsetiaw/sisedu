<!DOCTYPE html>
<head>

<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.*" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.login.*" %>
<%@ page import="beans.tmp.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>


</head>
<body onload="location.href='#'">

	<%	
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	Vector vMkMhs = validUsr.getScopeUpd7des2012("viewForecastMhsPerMk");
	if(vMkMhs!=null && vMkMhs.size()>0) {
		//String target = Constants.getRootWeb()+"/InnerFrame/Analisa/setTargetKdpst.jsp";
		%>
		<li><a href="prep.viewForecastMakulMhs" target="inner_iframe">INFO MHS YG BELUM<span> AMBIL MATAKULIAH</span></a></li>
		<%
	}

	%>
	

</body>