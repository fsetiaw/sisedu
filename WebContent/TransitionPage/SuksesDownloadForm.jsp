<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
	String v_id_obj = request.getParameter("id_obj");
	String v_obj_lvl = request.getParameter("obj_lvl");
	String v_nmmhs = request.getParameter("nmm");
	String v_npmhs = request.getParameter("npm");
	String v_kdpst = request.getParameter("kdpst");
	String cmd = request.getParameter("cmd");
%>
<META HTTP-EQUIV="Refresh" CONTENT="3; URL=http:get.profile?id_obj=<%=v_id_obj%>&obj_lvl=<%=v_obj_lvl%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs%>&kdpst=<%=v_kdpst%>&cmd=dashboard">
</head>
<body>
	DOWNLOAD BERHASIL....
	Harap Menunggu, anda sedang dialihkan...
	 
	
</body>
</html>