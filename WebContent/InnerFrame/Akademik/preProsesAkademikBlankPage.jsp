<!DOCTYPE html>
<head>
<%@ page import="beans.setting.*" %>
<%
String targetPage = request.getParameter("targetPage");
String kdpst_nmpst = request.getParameter("kdpst_nmpst");
//System.out.println(targetPage+" "+kdpst_nmpst);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>


</head>
<body>
<h2 align="center">Sedang Memproses Data, Harap Menunggu Sebentar</h2>
<%
if(targetPage!=null) {
	if(targetPage.equalsIgnoreCase("editMakul")) {
%>
	<meta http-equiv="refresh" content="1;url=get.listKelas?kdpst_nmpst=<%=kdpst_nmpst %>">
<%	
	}
	else {
		if(targetPage.equalsIgnoreCase("editKrklm")) {
			%>
				<meta http-equiv="refresh" content="1;url=get.listKurikulum?kdpst_nmpst=<%=kdpst_nmpst %>">
			<%	
		}		
		else {
			if(targetPage.equalsIgnoreCase("editMakur")) {
				%>
					<meta http-equiv="refresh" content="1;url=get.listMakur?kdpst_nmpst=<%=kdpst_nmpst %>">
				<%	
			}
			else {
				if(targetPage.equalsIgnoreCase("editUser")) {
					%>
						<meta http-equiv="refresh" content="1;url=<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Akademik/editTargetKurikulum.jsp?kdpst_nmpst=<%=kdpst_nmpst %>">
					<%	
				}
				else {
					
				}
			}
		}
	}
}	
%>
</body>
</html>