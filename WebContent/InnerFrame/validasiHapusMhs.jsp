<!DOCTYPE html>
<html>
<head>

<title>Insert title here</title>
	
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String objId = request.getParameter("id_obj");
String nmm = request.getParameter("nmm");
String npm = request.getParameter("npm");
String kdpst = request.getParameter("kdpst");
String obj_lvl =  request.getParameter("obj_lvl");
String backTo =  request.getParameter("backTo");
Vector v= null; 
%>

</head>
<body onload="location.href='#'">
<div id="header">
<ul>
	<li><a href="" target="_self" >HOME <span><b style="color:#eee">---</b></span></a></li>
</ul>	
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<br />
		<%
		if(validUsr.isUsrAllowTo("deleteMhs", npm, obj_lvl)){

		%>
		JADi HAPUS? <%=npm %>-<%=nmm %>-<%=kdpst %>
		<form action="go.deleteMhs">
			
			<input type="hidden" name="nmm" value="<%=nmm %>" />
			<input type="hidden" name="npm" value="<%=npm %>" />
			<input type="hidden" name="kdpst" value="<%=kdpst %>" />
			<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />	
			<input type="submit" value="HAPUS MAHASISWA" style="color:red;font-weight:bold" />
		</form>
		<%
		}
		%>
	</div>
</div>	
</body>
</html>