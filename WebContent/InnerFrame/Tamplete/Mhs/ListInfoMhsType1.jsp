<!DOCTYPE html>
<head>
<%@page import="beans.setting.Constants"%>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v= null;  
String listNpm = (String)request.getParameter("listNpm");
if(listNpm==null || Checker.isStringNullOrEmpty(listNpm)) {
	listNpm = (String)session.getAttribute("listNpm");
}
StringTokenizer st = new StringTokenizer(listNpm,",");
StringTokenizer st1 = null;
String kdpst = (String)request.getParameter("kdpst");
String nmpst = (String)request.getParameter("nmpst");
String smawl = (String)request.getParameter("smawl");
String cmd = (String)request.getParameter("cmd");
if(cmd==null || Checker.isStringNullOrEmpty(cmd)) {
	cmd = "histKrs"; //default cmd
}
//System.out.println(listNpm);
//System.out.println(kdpst);
//System.out.println(nmpst);
%>

</head>
<body>
<div id="header">
<ul>
<%
//dibawah ini default back - jadi kalo mo cutom harus pass parameter (sekarang  belum ditentukan parameternya);
%>
	<li><a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">BACK <span><b style="color:#eee">---</b></span></a></li>
</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<%
		
		if(st.countTokens()>0) {
			int i=1;	
		
		%>
		<table align="center" border="1px" bordercolor="369" style="background:#d9e1e5;color:#000;width:660px">
        	<tr>
        		<td style="background:#369;color:#fff;text-align:center" colspan="3"><B>LIST MAHASISWA PRODI <%=nmpst.toUpperCase() %> ANGKATAN: 
        		<%
        		if(Checker.isStringNullOrEmpty(smawl)) {
        			smawl = "N/A";
        		}
        		out.print(smawl);
        		%>
        		</B> </td>
        	</tr>
        	<tr>
        		<td style="background:#369;color:#fff;text-align:center;width:10px"><B>NO.</B> </td>
        		<td style="background:#369;color:#fff;text-align:center;width:150px"><B>NPM</B> </td>
        		<td style="background:#369;color:#fff;text-align:center;width:300px"><B>NAMA</B> </td>
        	</tr>
        <%
        	while(st.hasMoreTokens()) {
        		String npm_nmm = st.nextToken();
        		st1 = new StringTokenizer(npm_nmm,"-");
        		String npmhs = st1.nextToken();
        		String nmmhs = st1.nextToken();
        	
        %>			
        			<tr>
        				
        				<td style="color:#000;text-align:center;"><B><%=i++ %></B> </td></td>
        				<td style="color:#000;text-align:center;"><B><a href="proses.nuProfileGetter?cmd=<%=cmd %>&kdpst=<%=kdpst%>&npmhs=<%=npmhs%>"><%=npmhs %></a></B> </td></td>
        				<td style="color:#000;text-align:left;"><B><%="&nbsp&nbsp&nbsp&nbsp"+nmmhs %></B> </td></td>
        				
        			</tr>
        <%
        	}
        %>			
        </table>
        <%
		}
        %>
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>