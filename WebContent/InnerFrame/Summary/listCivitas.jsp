<!DOCTYPE html>
<html>
<head>
<%@ page import="beans.dbase.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.login.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<title>Insert title here</title>
<%
	
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	Vector v = null;
	SearchDb sdb = new SearchDb();
	String idobj = request.getParameter("idobj");
	//System.out.println(idobj);
	String smawl = request.getParameter("smawl");
	//System.out.println(""+smawl);
	if(smawl!=null && !smawl.equalsIgnoreCase("null")) {
		v = sdb.getListCivitas(idobj,smawl);
	}
	else {
		if(idobj!=null && !idobj.equalsIgnoreCase("null")) {
			v = sdb.getListCivitas(idobj);
		}
	}	
	ListIterator li = v.listIterator();
%>


</head>
<body>
<div id="header">
<jsp:include page="../Summary/subSummaryMenu.jsp" />
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
<% 
if(li.hasNext()) {	
	int i=1;
%>
	<table border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:650px">
        	<tr>
        		<td style="background:#369;color:#fff;text-align:center"><label><B>No.</B> </label></td></td>
        		<td style="background:#369;color:#fff;text-align:center"><label><B>NPM</B> </label></td></td>
        		<td style="background:#369;color:#fff;text-align:center"><label><B>NIM</B> </label></td></td>
        		<td style="background:#369;color:#fff;text-align:center"><label><B>ANGKATAN</B> </label></td></td>
        		<td style="background:#369;color:#fff;text-align:center"><label><B>NAMA</B> </label></td></td>
        		<td style="background:#369;color:#fff;text-align:center"><label><B>TOTAL BAYARAN</B> </label></td></td>
        	</tr>
<%	
	while(li.hasNext()) {
		String baris = (String)li.next();
		StringTokenizer st = new StringTokenizer(baris);
		String npm = st.nextToken();
		String nim = st.nextToken();
		smawl = st.nextToken();
		String nmm = "";
		while(st.hasMoreTokens()) {
			nmm = nmm + st.nextToken();
			if(st.hasMoreTokens()) {
				nmm = nmm + " ";
			}
		}
%>		
	<tr>
		<form action="people.search">
		<input type="hidden" name="kword" value="<%=npm %>"/>
		<td style="background:#d9e1e5;color:#000;text-align:center"><label><B><%=i++ %></B> </label></td></td>
		<td style="background:#d9e1e5;color:#000;text-align:center"><label><B><%=npm %></B> </label></td></td>
		<td style="background:#d9e1e5;color:#000;text-align:center"><label><B>
		<%
			if(nim!=null & !nim.equalsIgnoreCase("null")) {
				out.print(nim);
			}
		%></B></label></td></td>
		<td style="background:#d9e1e5;color:#000;text-align:center"><label><B><%=smawl %></B> </label></td></td>
		<td style="background:#d9e1e5;color:#000;text-align:center"><label><B><%=nmm %></B> </label></td></td>
		<td style="background:#d9e1e5;color:#000;text-align:center"><label><B><%=NumberFormater.indoNumberFormat(""+sdb.getTotPymntMhs(npm)) %></B> </label></td></td>
		<td style="background:#369;text-align:center"><input type="submit" value="Liat Profile" /></td>
		</form>
	</tr>
		
<%
	}
}	
%>
	</div>
</div>	
</body>
</html>