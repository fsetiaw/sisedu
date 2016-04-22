<!DOCTYPE html>
<head>
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
	Vector vDwn = validUsr.getScopeUpd7des2012("hasDownloadMenu");
	request.setAttribute("atPage", "webDev");
	
%>
</head>
<body onload="location.href='#'">
<div id="header">
	<ul>
	<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Download/innerMenu.jsp" />
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<p>
			<form action="file.uploadWebDev" method="POST" enctype="multipart/form-data" target="_self">
			<table border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;">
					<tr>
        				<td style="background:#369;color:#fff;text-align:center" colspan="2"><label><B>UPLOAD FILE</B> </label></td>
        			</tr>
					<tr>
						<td><input type="file" name="fileSelect" value="Pilih File" /></td>
					</tr>
            		<tr>
						<td style="background:#369;color:#fff;text-align:right"><input type="submit" value="Upload File" style="width:35%" /></td>
					</tr>
			</table>		
        	</form>
		</p>

	</div>
</div>		
</body>
</html>