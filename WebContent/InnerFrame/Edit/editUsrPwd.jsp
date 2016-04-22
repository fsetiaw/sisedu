<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
	
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String tkn_usr_pwd = validUsr.getUserPwd();
	String msg = request.getParameter("msg");
	String usr_name = null;
	String usr_pwd = null;
	if(tkn_usr_pwd!=null) {
		StringTokenizer st = new StringTokenizer(tkn_usr_pwd);
		usr_name = st.nextToken();
		usr_pwd = st.nextToken();
	}
%>
</head>
<body onload="location.href='#'">

<div class="colmask fullpage">
	<div class="col1">
		<!-- Column 1 start -->
		<br />
	<center>
		<h3>FORM CHANGE USER DAN PASSWORD</h3>
			<form action="go.updateUsrPwd">
			<table  border="1" style="background:#d9e1e5;color:#000;width:500px;">					
				<tr>
					<td align="left" bgcolor="#369" style="color:#fff" padding-left="2px"><b>NEW USER NAME</b></td><td align="center"  padding-left="2px"><input type="text" value="<%=usr_name%>" name="uname" style="width:98%"/></td>
				</tr>
				<tr>
					<td align="left" bgcolor="#369" style="color:#fff" padding-left="2px"><b>NEW PASSWORD (min 5 char)</b></td><td align="center"  padding-left="2px"><input type="password" value="" name="pwd1" style="width:98%"/></td>
				</tr>
				<tr>
					<td align="left" bgcolor="#369" style="color:#fff" padding-left="2px"><b>CONFIRM PASSwORD</b></td><td align="center"  padding-left="2px"><input type="password" name="pwd2" value=""  style="width:98%"/></td>
				</tr>
				<tr>
					<td align="center" colspan="2" bgcolor="#369" style="color:#fff" padding-left="2px"><input type="submit" value="UPDATE USER & PASSWORD" style="width:90%" /></td>
				</tr>
			</table>
			
	
			<p>
			<%
			if(msg!=null) {
				out.print("<i>"+msg+"</i>");
			}
			%>
			</p>
			<br />
			</form>
			</center>
		</div>
	</div>		
</body>
</html>