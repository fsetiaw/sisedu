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
	String tipeForm = request.getParameter("formType");
	Vector vDwn = validUsr.getScopeUpd7des2012("hasDownloadMenu");
	String atMenu = request.getParameter("atMenu");
	String target_kdpst = request.getParameter("target_kdpst");
	System.out.println("at index spmytarget_kdpst = "+target_kdpst);
	pageContext.setAttribute("prodi", target_kdpst);
%>
</head>
<body>
<div id="header">
	<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Flowchart/${prodi}/std6/subMenuStd6.jsp" />
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />

		<!-- Column 1 start -->
<%
if(atMenu!=null && atMenu.equalsIgnoreCase("std")) {
	%>
		<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Flowchart/${prodi}/std6/skl.jsp">
		<jsp:param name="target_kdpst" value="<%=target_kdpst %>"/>
		</jsp:include>
<%	
}
else if(atMenu!=null && atMenu.equalsIgnoreCase("isi")) {
%>
		<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Flowchart/${prodi}/std6/isi_std/std_prasarana.htm">
		<jsp:param name="target_kdpst" value="<%=target_kdpst %>"/>
		</jsp:include>
<%	
}
else {
	//out.println("no selection");
}
%>

		<!--  jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Flowchart/main/masterFlowChart.jsp" / -->
	</div>
</div>		
</body>
</html>