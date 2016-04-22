<!DOCTYPE html>
<head>
<%@page import="beans.setting.Constants"%>
<%@page import="java.io.File"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
	String targetFile = getServletContext().getRealPath("/")+Constants.getRootFolderDokSpmiInTomcat()+"/GBPP/GBPP";
	java.io.File file = new File(targetFile);
	if(file.exists()) {
		out.print("file exist");
	}
	else {
		out.print("file not exist");
		response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
		return;
	}
%>
<%= targetFile%>
</body>
</html>