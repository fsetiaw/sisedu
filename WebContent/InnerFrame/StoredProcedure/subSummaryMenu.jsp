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
	String tabMenu = request.getParameter("tabMenu");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	Vector vMkMhs = validUsr.getScopeUpd7des2012("analizeMkAndMhs");
	if(vMkMhs!=null && vMkMhs.size()>0) {
		//String target = Constants.getRootWeb()+"/InnerFrame/Analisa/setTargetKdpst.jsp";
		if(tabMenu!=null && tabMenu.equalsIgnoreCase("forecast")) {
		%>
		<li><a href="forecast.jsp?tabMenu=forecast" target="inner_iframe" class="active">PERKIRAAN /<span>FORCAST</span></a></li>
		<%
		}
		else {
			%>
			<li><a href="forecast.jsp?tabMenu=forecast" target="inner_iframe">PERKIRAAN /<span>FORCAST</span></a></li>
			<%	
		}
	}

	%>
	

</body>