<!DOCTYPE html>
<head>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.*" %>
<%
	String atPage = (String)request.getAttribute("atPage");
%>
</head>
<body>
	<%
	if(atPage.equalsIgnoreCase("dash")) {
	%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Download/dashDownload.jsp" target="inner_iframe" class="active">HOME<span><b style="color:#eee">-</b></span></a></li>
	<%
	}
	else {
		%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Download/dashDownload.jsp" target="inner_iframe">HOME<span><b style="color:#eee">-</b></span></span></a></li>
		<%
	}
	if(atPage.equalsIgnoreCase("webDev")) {
	%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Download/webDevelopment.jsp" target="inner_iframe" class="active">WEB DEVELOPMENT<span>FILES</span></a></li>
	<%
	}
	else {
		%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Download/webDevelopment.jsp" target="inner_iframe">WEB DEVELOPMENT<span>FILES</span></a></li>
		<%
	}

	%>
</body>

	