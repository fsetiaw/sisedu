<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
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
	String thsms_target = request.getParameter("thsms_target");
%>


</head>
<!--  body onload="location.href='#'" -->
<body>
<div id="header">
<%@ include file="InnerMenuFpk.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<%
		//Vector v = validUsr.getScopeUpd7des2012ProdiOnly(scopeKampusCmd,atMenu);
		Vector v = (Vector) request.getAttribute("vScope");
		%>
		
		<%
		if(v!=null && v.size()>0) {
		%>
		<form action="go.updClassPollRules" method="post">
		<input type="hidden" name="thsms_target" value="<%=thsms_target %>" />
		<input type="hidden" name="target_kampus" value="<%=atMenu %>" />
		<table align="center" border="1" style="background:#d9e1e5;color:#fff;width:700px">	
			<tr>
				<td align="center" colspan="3" bgcolor="#369"><b>FORM PENGATURAN PENGAJUAN KELAS PERKULIAHAN <%=thsms_target %></b></td>
			</tr>
			<tr>
				<td style="background:#369;color:#fff;text-align:center" width="200px">PRODI</td>
				<td style="background:#369;color:#fff;text-align:center" width="450px">ALUR PERIZINAN</td>
				<td style="background:#369;color:#fff;text-align:center" width="50px">URUTAN</td>
			</tr>
		<%	
			ListIterator li = v.listIterator();
			while(li.hasNext()) {
				String brs = (String)li.next();
				StringTokenizer st = new StringTokenizer(brs);
				String id = st.nextToken();
				String kdpst = st.nextToken();
				String keter = st.nextToken();
				String lvl = st.nextToken();
				String kdjen = st.nextToken();
				String tkn_verificator = "";
				String urutan = "";
				String kode_kampus = "";
				if(st.hasMoreTokens()) {
					tkn_verificator =  st.nextToken();
					urutan =  st.nextToken();
					kode_kampus =  st.nextToken();
				}
				
		%>
			<tr>
				<td style="color:#000;text-align:left;">
				<%
				keter = keter.replace("MHS_", "");
				keter = keter.replace("_", " ");
				keter = keter.toUpperCase();
				st = new StringTokenizer(keter);
				String nama_prodi = "";
				boolean stop = false,first=true;
				while(st.hasMoreTokens() && !stop) {
					String tkn = st.nextToken();
					if(tkn.equalsIgnoreCase("KAMPUS")||tkn.equalsIgnoreCase("GROUP")) {
						stop = true;
					}
					else {
						if(first) {
							first = false;
							nama_prodi=nama_prodi+tkn;
						}
						else {
							nama_prodi=nama_prodi+" "+tkn;
						}
						
					}
				}
				//keter = keter.toUpperCase().concat("GROUP");
				//keter = keter.replace("KAMPUS", "");
				//keter = keter.replace("GROUP", "");
				
				out.print(nama_prodi);
				%>
				<input type="hidden" name="kdpst" value="<%=kdpst %>" />
				</td>
				<td style="color:#000;text-align:center;">
				<input type="text" name="alur" style="width:98%;text-align:center" value="<%=tkn_verificator%>"/></td>
				<td style="color:#000;text-align:center;">
				<%
				if(urutan.equalsIgnoreCase("true")) {
				%>
				<input type="checkbox" name="urut" value="<%=kdpst %>" checked="checked" />
				<%
				}
				else {
				%>
				<input type="checkbox" name="urut" value="<%=kdpst %>" />
				<%
				}
				%>
				</td>
			</tr>
			
		<%		
			}
		%>
			<tr>
				<td align="center" colspan="3" bgcolor="#369"><input type="submit" value="Update Data" style="width:55%"/></td>
			</tr>
		</table>
		
		</form>
		<%	
		}
		%>								
		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>