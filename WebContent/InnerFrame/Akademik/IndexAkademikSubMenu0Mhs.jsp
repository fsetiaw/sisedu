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
/*
INI MENU UNTUK MAHASISWA KAYAKNYA
*/
	validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String callerPage = request.getParameter("callerPage");
	String atMenu = request.getParameter("atMenu");
	if(atMenu==null || atMenu.equalsIgnoreCase("null")) {
		atMenu = (String)request.getAttribute("atMenu");
	}
	//caller page buat menentukan class-aktif
%>


</head>
<body>
<ul>
<%
String target = Constants.getRootWeb()+"/get.notifications";
String uri = request.getRequestURI();
String url = PathFinder.getPath(uri, target);
String myNpm = validUsr.getNpm();

String v_id_obj=""+validUsr.getIdObj();
String v_nmmhs=validUsr.getNmmhs(myNpm);
String v_npmhs=validUsr.getNpm();
String v_obj_lvl=""+validUsr.getObjLevel();
String v_kdpst=validUsr.getKdpst();
String v_nimhs=validUsr.getNimhs(myNpm);

%>
		<li><a href="<%=url %>">BACK <span><b style="color:#eee">---</b></span></a></li>
		
<%
if(validUsr.isAllowTo("allowViewKurikulum")>0) {
	//System.out.println("yess allow view kurikulum");	
	target = Constants.getRootWeb()+"/InnerFrame/Akademik/get.listScope";
	uri = request.getRequestURI();
	url = PathFinder.getPath(uri, target);
	if(atMenu!=null && atMenu.equalsIgnoreCase("ko")) {
	%>		
		<li><a href="<%=url %>?scope=allowViewKurikulum&callerPage=<%=Constants.getRootWeb()%>/InnerFrame/Akademik/indexAkademikMhs.jsp&cmd=viewKurikulumMhs&atMenu=ko" target="_self" class="active">KURIKULUM <span>OPERASIONAL</span></a></li>
	<%	
	}
	else {
	%>		
		<li><a href="<%=url %>?scope=allowViewKurikulum&callerPage=<%=Constants.getRootWeb()%>/InnerFrame/Akademik/indexAkademikMhs.jsp&cmd=viewKurikulumMhs&atMenu=ko" target="_self">KURIKULUM <span>OPERASIONAL</span></a></li>
	<%
	}
}	

if(validUsr.iAmStu()) {
	
	%>	
	<li><a href="get.histKrs?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=histKrs" target="_self" >KRS / KHS <span>& BAHAN AJAR</span></a></li>
	<%
}

if(validUsr.isAllowTo("hasEtestMenu")>0) {
	%>
	<!--  li><a href="go.cekOnlineTest?backTo=<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/index.jsp" target="inner_iframe">ONLINE<span>TEST</span></a></li -->
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/MhsSection/dashboardMhs.jsp" target="inner_iframe">ONLINE<span>TEST</span></a></li>
	<%
}

%>
	
</ul>

</body>
</html>