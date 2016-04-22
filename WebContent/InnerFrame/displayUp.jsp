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
<%@ page import="beans.dbase.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v= null;  
String tknUsrPwd = (String)request.getAttribute("tknUsrPwd");
request.removeAttribute("tknUsrPwd");
String usrnmm=null;
String usrpwd=null;
if(tknUsrPwd!=null) {
	StringTokenizer st = new StringTokenizer(tknUsrPwd);
	usrnmm = st.nextToken();
	usrpwd = st.nextToken();
}
String objId=request.getParameter("id_obj");
String nmm=request.getParameter("nmm");
String npm=request.getParameter("npm");
System.out.println("nmm & npm = "+nmm+","+npm);
String kdpst=request.getParameter("kdpst");
String objLvl=request.getParameter("obj_lvl");
String backTo=request.getParameter("backTo");
String cmd=request.getParameter("cmd");
String msg = request.getParameter("msg");
String atMenu = request.getParameter("atMenu");
//String kdjen = Converter.getKdjen(kdpst);
//Vector v_list_shift = Converter.getPilihanShiftYgAktif(kdjen);
%>

</head>
<body onload="location.href='#'">
<div id="header">
	<ul>
		<li><a href="get.profile?id_obj=<%=objId%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=objLvl %>&kdpst=<%=kdpst %>&cmd=dashboard" target="inner_iframe">GO<span>BACK</span></a></li>
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<br/>
		<h3 align="center">USER NAME: <%=usrnmm %></h3><br/>
		<h3 align="center">PASSWORD : <%=usrpwd %></h3>
		
		<form action="file.downloadUsrPwd">
			<input type="hidden" name="nmm" value="<%=nmm %>" />
			<input type="hidden" name="npm" value="<%=npm %>" />
			<input type="hidden" name="usr" value="<%=usrnmm %>" />
			<input type="hidden" name="pwd" value="<%=usrpwd %>" />
			<input type="submit" value="Download File"/>
		</form>
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>