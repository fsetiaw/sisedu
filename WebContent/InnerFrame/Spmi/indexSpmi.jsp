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
	String target_kdpst = "65001";
	String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
	StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"`");
	String kdpst = st.nextToken();
	String nmpst = st.nextToken();
	String kd_kmp = st.nextToken();
	//System.out.println("at index spmy = "+atMenu);
%>
</head>
<body>
<div id="header">
	<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/subTopMenu.jsp" >
	<jsp:param name="atMenu" value="<%=atMenu %>"/>

	</jsp:include>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />

		<!-- Column 1 start -->
		<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Flowchart/main/masterFlowChart.jsp" >
		<jsp:param name="target_kdpst" value="<%=kdpst %>"/>
		</jsp:include>
	</div>
</div>		
</body>
</html>