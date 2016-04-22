<!DOCTYPE html>
<html>
<head>

<title>Insert title here</title>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%

beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
/*
String id_obj = request.getParameter("id_obj");
String nmm = request.getParameter("nmm");
String npm = request.getParameter("npm");
String obj_lvl = request.getParameter("obj_lvl");
String kdpst = request.getParameter("kdpst");
String cmd = request.getParameter("cmd");
*/
Vector v= null; 
%>

</head>
<body>
<div id="header">
<%@ include file="InnerMenu.jsp"%>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<br />
	<form action="go.updKrsWhitelist">
		<input type="hidden" name="id_obj" value="<%=v_id_obj%>" />
		<input type="hidden" name="nmm" value="<%=v_nmmhs%>" />
		<input type="hidden" name="npm" value="<%=v_npmhs%>" />
		<input type="hidden" name="obj_lvl" value="<%=v_obj_lvl%>" />
		<input type="hidden" name="kdpst" value="<%=v_kdpst%>" />
		<input type="hidden" name="cmd" value="<%=cmd%>" />
			
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:500px">
			<tr>
				<td colspan="2" style="background:#369;color:#fff;text-align:center" ><font size="4"><B>PENGATURAN IZIN INPUT KRS</B></font></td></td>
			</tr>
        	<tr>
				<td style="width:50%"><label>THSMS KRS YANG BISA DI-EDIT DILUAR THSMS BERJALAN</label></td>
				<td valign="bottom" align="center">
					<textarea style="width:96%;" rows="4" name="tkn_thsms_whitelist_koma">
					</textarea>
					
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center" style="background:#369;color:#fff"><input type="submit" value="UPDATE" style="width:50%;height:30px;align:center"/></td>				
			</tr>
		</table>	
	</form>   		
		<!-- Column 1 start -->
		<br />
	</div>
</div>	
</body>
</html>