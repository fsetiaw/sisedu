<!DOCTYPE html>
<head>
<%@page import="beans.setting.Constants"%>
<%@page import="beans.tools.*"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div id="header">
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<h1>FILE KOSONG</h1>
		<%
		String target = Constants.getRootWeb()+"/InnerFrame/Download/indexDownload.jsp";
		String uri = request.getRequestURI();
		System.out.println(target+" / "+uri);
		String url = PathFinder.getPath(uri, target);
		response.sendRedirect(url);
		%>
		<meta http-equiv="REFRESH" content="0;url=<%=url%>"></HEAD>
	</div>
</div>		
</body>
</html>