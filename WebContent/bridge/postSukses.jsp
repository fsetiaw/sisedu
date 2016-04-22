<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
	String email = request.getParameter("email");
	String nama =  request.getParameter("nama");
	String pesan =  request.getParameter("pesan");
%>
    
</head>
<body>
	<h2 style="text-align:center">
		<p>
		Terima Kasih telah menghubungi kami, <%=nama %>
		</p>
		<p>
		Informasi akan kami kirimkan melalui email, <%=email %>, dalam tempo 1 (satu) hari kerja.
		</p>
		
	</h2>
	<p style="text-align:center">
		 Salam kami, UNIVERSITAS SATYAGAMA
	</p> 
	<p style="text-align:center"> Harap Menunggu anda sedang dialihkan</p>
	<meta http-equiv="refresh" content="8; url=http://www.satyagama.ac.id/">
</body>
</html>