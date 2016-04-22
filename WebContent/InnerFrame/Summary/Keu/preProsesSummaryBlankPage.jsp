<!DOCTYPE html>
<head>
<%@ page import="beans.setting.*" %>
<%
String targetPage = request.getParameter("targetPage");
String x = request.getParameter("x"); // startAt
String y = request.getParameter("y"); // range
String filterTgl = request.getParameter("filterTgl"); 
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>


</head>
<body>
<h2 align="center">Sedang Memproses Data, Harap Menunggu Sebentar</h2>
<%
if(targetPage!=null) {
	if(!targetPage.equalsIgnoreCase("listKui")) {
%>
	<meta http-equiv="refresh" content="1;url=view.summary?targetPage=<%=targetPage%>">
<%	
	}
	else {
%>
	<meta http-equiv="refresh" content="1;url=view.listKui?x=<%=x%>&y=<%=y%>&filterTgl=<%=filterTgl%>">
<%
	}
}	
%>
</body>
</html>