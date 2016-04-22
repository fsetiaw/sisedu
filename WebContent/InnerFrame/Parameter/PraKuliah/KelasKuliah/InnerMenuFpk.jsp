<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<%
	validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String callerPage = request.getParameter("callerPage");
	String atMenu = request.getParameter("atMenu");
	String scopeKampusCmd = request.getParameter("scopeKampusCmd");
	
	if(atMenu==null || atMenu.equalsIgnoreCase("null")) {
		atMenu = (String)request.getAttribute("atMenu");
	}
	//caller page buat menentukan class-aktif
%>


</head>
<!--  body onload="location.href='#'" -->
<body>
<ul>
<%

%>
	<li><a href="<%=Constants.getRootWeb()+"/InnerFrame/Parameter/PraKuliah/dashParameterPk.jsp" %>"  target="_self">BACK <span><b style="color:#eee">---</b></span></a></li>
<%
//parameter scope kampus only
//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/PraKuliah/KelasKuliah/formParamPengajuanKelas.jsp";
String target = "go.prepFormParamPk";
String uri = request.getRequestURI();
String url = PathFinder.getPath(uri, target);
if(scopeKampusCmd!=null) {
	String scopeKampus = validUsr.getScopeKampus(scopeKampusCmd);
	StringTokenizer st = new StringTokenizer(scopeKampus,",");
	if(st.countTokens()>0) {
		Vector v = new Vector();
		ListIterator li = v.listIterator();
		while(st.hasMoreTokens()) {
			String brs = st.nextToken();
			li.add(brs);
		}
		Collections.sort(v);
		li = v.listIterator();
		boolean first = true;
		while(li.hasNext()) {
			String kampus = (String)li.next();
			if((atMenu==null || Checker.isStringNullOrEmpty(atMenu)) && first) {
				atMenu = ""+kampus;
				first = false;

%>		
	<li><a href="<%=target+"?atMenu="+kampus+"&scopeKampusCmd=paramKlsKuliah" %>" target="_self" class="active">KAMPUS<span><%=kampus.toUpperCase() %></b></span></a></li>
<%				
			}
			else {
				if(atMenu.equalsIgnoreCase(kampus)) {
%>		
	<li><a href="<%=target+"?atMenu="+kampus+"&scopeKampusCmd=paramKlsKuliah" %>" target="_self" class="active">KAMPUS<span><%=kampus.toUpperCase() %></b></span></a></li>
<%						
				}
				else {
%>		
	<li><a href="<%=target+"?atMenu="+kampus+"&scopeKampusCmd=paramKlsKuliah" %>" target="_self" >KAMPUS<span><%=kampus.toUpperCase() %></b></span></a></li>
<%					
				}
			}
		}
	}
}

%>
</ul>

</body>
</html>