<!DOCTYPE html>
<head>
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
String id_obj = request.getParameter("id_obj");
String nmm = request.getParameter("nmm");
String npm = request.getParameter("npm");
String obj_lvl= request.getParameter("obj_lvl");
String kdpst=request.getParameter("kdpst");
String cmd=request.getParameter("cmd");
%>
<script type="text/javascript">
</script>
</head>
<body>
<div id="header">
<%@ include file="innerMenuDaftarUlang.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
<%
if(validUsr.isUsrAllowTo("regByOpr", npm, obj_lvl)) {	
%>
<form action="proses.daftarUlangByOpr" method="post">
<div>
	<input  type="hidden" name="npmhs" value="<%=npm %>" />
	<input  type="hidden" name="kdpst" value="<%=kdpst %>" />
	<input  type="hidden" name="id_obj" value="<%=id_obj %>" />
	<input  type="hidden" name="nmmhs" value="<%=nmm %>" />
	<input  type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />

	<input type="submit" value="KLIK UNTUK MELAKUKAN HEREGISTRASI <%=Checker.getThsmsHeregistrasi() %>" />
</div>
</form>
<%	
}
%>
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>