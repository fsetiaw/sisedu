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

Vector v= null; 
//String id_obj = request.getParameter("id_obj");
//String nmm = request.getParameter("nmm");
//String npm = request.getParameter("npm");
//String obj_lvl= request.getParameter("obj_lvl");
//String kdpst=request.getParameter("kdpst");
//String cmd=request.getParameter("cmd");
%>

</head>
<body onload="location.href='#'">
<div id="header">
<%@ include file="innerMenu.jsp"%>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<%
		if(validUsr.isUsrAllowTo("allowCetakIjazah", v_npmhs, v_obj_lvl)) {
		%>
		<table width="500px" align="center" style="background:#d9e1e5;color:#000;">
			<tr>
				<td style="width:50%;text-align:center">
					<div style="font-size:2 em;font-weight:bold;" >
					DOWNLOAD IJAZAH SEKARANG? 
					</div>
				</td>
			</tr>
			<tr>		
				<td style="width:50%;text-align:center">
					<form action="go.downloadIjazah">
						<input type="hidden" value="<%=v_id_obj %>" name="id_obj"/>
						<input type="hidden" value="<%=v_nmmhs %>" name="nmm"/>
						<input type="hidden" value="<%=v_npmhs %>" name="npm"/>
						<input type="hidden" value="<%=v_obj_lvl %>" name="obj_lvl"/>
						<input type="hidden" value="<%=v_kdpst %>" name="kdpst" />
						<input type="hidden" value="percetakan" name="cmd" />
						<input type="submit" style="width:150px;height:50px;text-align:center;font-size:1.5em" value="Ya"/>
					</form>
				</td>
				<td style="width:50%;text-align:center">
					<form action="get.profile" >
						<input type="hidden" value="<%=v_id_obj %>" name="id_obj"/>
						<input type="hidden" value="<%=v_obj_lvl %>" name="obj_lvl"/>
						<input type="hidden" value="<%=v_nmmhs %>" name="nmm"/>
						<input type="hidden" value="<%=v_npmhs %>" name="npm"/>
						<input type="hidden" value="<%=v_kdpst %>" name="kdpst" />
						<input type="hidden" value="dashboard" name="cmd" />
						<input type="submit" style="width:150px;height:50px;text-align:center;font-size:1.5em" value="Tidak"/>
					</form>
				</td>
			</tr>
			<tr>		
				<td style="width:100%;text-align:center" colspan="2">
					<br />
					<form action="get.profile" >
						<input type="hidden" value="<%=v_id_obj %>" name="id_obj"/>
						<input type="hidden" value="<%=v_obj_lvl %>" name="obj_lvl"/>
						<input type="hidden" value="<%=v_nmmhs %>" name="nmm"/>
						<input type="hidden" value="<%=v_npmhs %>" name="npm"/>
						<input type="hidden" value="<%=v_kdpst %>" name="kdpst" />
						<input type="hidden" value="dashboard" name="cmd" />
						<input type="submit" style="width:400px;height:50px;text-align:center;font-size:1.5em" value="Back To Main"/>
					</form>
					<br />
				</td>
			</tr>
		</table>	
			<%
		}
		%>
	</div>
</div>	
</body>
</html>