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
	Vector vKur = validUsr.getScopeUpd7des2012("hasKurikulumMenu");
	ListIterator li = vKur.listIterator();
	boolean stop=false;
	/*
	*jika scope=own , break while loop
	*/
	
	while(li.hasNext() && !stop) {
		String baris = (String)li.next();
		if(baris.equalsIgnoreCase("own")) {
			stop=true;
		}
	}
	
%>


</head>
<body>
<div id="header">
	<ul>
	<%
	Vector v_cf = validUsr.getScopeUpd7des2012("hasKurikulumMenu");
	String target = Constants.getRootWeb()+"/InnerFrame/Akademik/indexKurikulum";
	String uri = request.getRequestURI();
	//System.out.println(target+" / "+uri);
	String url = PathFinder.getPath(uri, target);
	if(v_cf.size()>0) {
	%>
		<li><a href="<%=url %>" target="inner_iframe">KURIKULUM<span>OPERASIONAL</span></a></li>
	<%
	}
	target = Constants.getRootWeb()+"/InnerFrame/viewSummaryTTmhs.jsp";
	uri = request.getRequestURI();
	url = PathFinder.getPath(uri, target);
	//request.getRequestDispatcher(url).forward(request,response);
	%>	
		<li><a href="<%=url %>" target="inner_iframe">JUMLAH<span>MAHASISWA</span></a></li>
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		INDEX KURIKULUM
		<%
		v_cf = validUsr.getScopeUpd7des2012("hasKurikulumMenu");
		li = v_cf.listIterator();
		while(li.hasNext()) {
			String brs = (String)li.next();
			System.out.println(brs);
		}
		%>
	</div>
</div>		
</body>
</html>