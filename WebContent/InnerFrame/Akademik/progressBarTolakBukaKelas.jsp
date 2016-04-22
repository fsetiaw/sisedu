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
	
	String msg = request.getParameter("msg");
	String pb = request.getParameter("pb");
	String alihkan =request.getParameter("alihkan");
%>


</head>
<body>
<div id="header">

</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->

		<%
		if(pb!=null && pb.equalsIgnoreCase("yes")) {
			session.setAttribute("tmp", "/InnerFrame/Akademik/progressBarTolakBukaKelas.jsp");
			%>
			<%@ include file= "../../progressBar/progressBarUpd.jsp" %>
			<%
		}
		else {
			if(msg!=null && !Checker.isStringNullOrEmpty(msg)) {
			%>
			<div style="font-size:1.5em;font-style:bold;text-align:center;color:blue"><%=msg %></div>
			
			<%
				if(alihkan!=null & !Checker.isStringNullOrEmpty(alihkan)) {
					if(alihkan.contains("jsp")) {
						if(alihkan.contains("home.jsp")) {
							alihkan="get.notifications";
						}
						%>	
							<meta http-equiv='Refresh' content='3, URL=<%=alihkan%>'>
						<%
		
					}
				}
			}	
		}
		%>
		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>