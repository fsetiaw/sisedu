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
	String atPage = ""+request.getAttribute("atPage");
	request.removeAttribute("atPage");
	
	String kdpst_nmpst = request.getParameter("kdpst_nmpst");
	String idkur_ = ""+request.getParameter("idkur_");
	StringTokenizer st = new StringTokenizer(kdpst_nmpst,",");
	String kdpst = st.nextToken();
	String nmpst = "";
	while(st.hasMoreTokens()) {
		nmpst = nmpst+st.nextToken();
		if(st.hasMoreTokens()) {
			nmpst = nmpst+" ";
		}
	}
	/*
	
	*/
	
	
	 %>
	 </head>
<body>

	<%
	String target = null;
	String uri = null;
	String url = null;
	if(validUsr.isAllowTo("hasEditMkKurikulum")>0 ) {
		target = Constants.getRootWeb()+"/InnerFrame/Akademik/get.listScope";
		uri = request.getRequestURI();
		url = PathFinder.getPath(uri, target);
	}	
	%>
<ul>	
	<li><a href="<%=url %>?scope=hasEditMkKurikulum&callerPage=<%=Constants.getRootWeb()%>/InnerFrame/Akademik/dashKurikulum.jsp" target="_self">BACK <span><b style="color:#eee">---</b></span></a></li>
	<%		
	if(atPage.equalsIgnoreCase("listKelas")) {
	%>
	<li><a href="get.listKelas?kdpst_nmpst=<%=kdpst_nmpst %>" target="inner_iframe" class="active">TAMBAH / EDIT<span>MATAKULIAH</span></a></li>
	<%
	}
	else {
	%>
	<li><a href="get.listKelas?kdpst_nmpst=<%=kdpst_nmpst %>" target="inner_iframe" >TAMBAH / EDIT<span>MATAKULIAH</span></a></li>
	<%
	}

	if(atPage.equalsIgnoreCase("listKurikulum")) {
	%>
	<li><a href="get.listKurikulum?kdpst_nmpst=<%=kdpst_nmpst %>" target="inner_iframe" class="active">TAMBAH / EDIT<span>KURIKULUM</span></a></li>
	<%
	}
	else {
	%>
	<li><a href="get.listKurikulum?kdpst_nmpst=<%=kdpst_nmpst %>" target="inner_iframe" >TAMBAH / EDIT<span>KURIKULUM</span></a></li>
	<%
	}
	if(atPage.equalsIgnoreCase("listMakur")) {
	%>
	<li><a href="get.listMakur?kdpst_nmpst=<%=kdpst_nmpst %>" target="inner_iframe" class="active">TAMBAH / EDIT<span>MATAKULIAH KURIKULUM</span></a></li>
	<%
	}
	else {
	%>
	<li><a href="get.listMakur?kdpst_nmpst=<%=kdpst_nmpst %>" target="inner_iframe" >TAMBAH / EDIT<span>MATAKULIAH-KURIKULUM</span></a></li>
	<%
	}
	if(atPage.equalsIgnoreCase("editTargetKurikulum")) {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Akademik/editTargetKurikulum.jsp?kdpst_nmpst=<%=kdpst_nmpst %>" target="inner_iframe" class="active">EDIT TARGET/USER<span>KURIKULUM</span></a></li>
	<%
	}
	else {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Akademik/editTargetKurikulum.jsp?kdpst_nmpst=<%=kdpst_nmpst %>" target="inner_iframe" >EDIT TARGET/USER<span>KURIKULUM</span></a></li>
	<%
	}
	%>
</ul>	
</body>
</html>