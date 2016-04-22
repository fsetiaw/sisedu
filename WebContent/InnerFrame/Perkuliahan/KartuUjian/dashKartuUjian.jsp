<!DOCTYPE html>
<head>
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<META HTTP-EQUIV="EXPIRES" CONTENT="Mon, 22 Jul 2002 11:12:01 GMT">
<%@page import="beans.setting.Constants"%>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v= null; 
String listTipeUjian = (String)session.getAttribute("listTipeUjian");
//request.removeAttribute("listTipeUjian");
String atMenu = request.getParameter("atMenu");
%>
<script type="text/javascript">
</script>
</head>
<body>
<div id="header">
<%
if(listTipeUjian!=null) {
	StringTokenizer st = new StringTokenizer(listTipeUjian,"#");
	listTipeUjian = listTipeUjian.replace("#", "tandaPagar");
	if(st.countTokens()>0) {
%>
	<ul>
<%		
		String target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/DashPerkuliahan.jsp";
		//String uri = request.getRequestURI();
		//String url = PathFinder.getPath(uri, target);
%>
		<li><a href="<%=target %>" target="_self">BACK<span><b style="color:#eee">---</b></span></a></li>
<%		
		while(st.hasMoreTokens()) {
			String namaTest = st.nextToken();
			if(atMenu!=null && atMenu.equalsIgnoreCase(namaTest)) {
				%>
		<li><a href="view.listCalonDptKartuUjian?atMenu=<%=namaTest.toUpperCase() %>&listTipeUjian=<%=listTipeUjian %>" target="_self" class="active"><%=namaTest.toUpperCase() %><span><b style="color:#eee">---</b></span></a></li>
				<%	
			}
			else {
			%>
		<li><a href="view.listCalonDptKartuUjian?atMenu=<%=namaTest.toUpperCase() %>&listTipeUjian=<%=listTipeUjian %>" target="_self"><%=namaTest.toUpperCase() %><span><b style="color:#eee">---</b></span></a></li>
<%	
			}	
		}
%>
	</ul>
<%
	}
}
%>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>