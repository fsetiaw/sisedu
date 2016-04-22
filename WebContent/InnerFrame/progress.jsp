<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ page import="beans.folder.file.*" %>
<%
TestProgressListener tpl = (TestProgressListener) session.getAttribute("testProgressListener");
%>
</head>
<body onload="location.href='#'">
progress status : <br/>
<%
out.println(tpl.getMessage());
%>
<meta http-equiv="refresh" content="1;url=progress.jsp">
</body>
</html>