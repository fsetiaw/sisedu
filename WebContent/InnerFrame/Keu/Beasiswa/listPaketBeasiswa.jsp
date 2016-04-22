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
	Vector vListNamaPaketBea = (Vector) session.getAttribute("vListNamaPaketBea");
	session.removeAttribute("vListNamaPaketBea");
%>


</head>
<body>
<div id="header">
<%@ include file="menuBeasiswa.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<%
		//li.add(nmmPaket+"`"+idJenis+"`"+jumDana+"`"+unitPeriode+"`"+namaInstansi+"`"+jenisInstansi+"`"+keter+"`"+jenisBea+"`"+scopeKampus);
		if(vListNamaPaketBea!=null && vListNamaPaketBea.size()>0) {
		%>
		
		<%	
			ListIterator li = vListNamaPaketBea.listIterator();
			while(li.hasNext()) {
				String brs = (String)li.next();
				StringTokenizer st = new StringTokenizer(brs,"`");
				String nmmPaket = st.nextToken();
				String idJenis = st.nextToken();
				String jumDana = st.nextToken();
				String unitPeriode = st.nextToken();
				String namaInstansi = st.nextToken();
				String jenisInstansi = st.nextToken();
				String syarat = st.nextToken();
				String jenisBea = st.nextToken();
				String scopeKampus = st.nextToken();
		%>
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:750px">
        	<tr>
        		<td style="background:#369;color:#fff;text-align:center" colspan="4"><label><B><%=nmmPaket.toUpperCase() %></B> </label></td>
        	</tr>
        	<tr>
        		<td style="background:#d9e1e5;color:#369;text-align:left;width:125px"><label><B>JENIS BEASISWA</B> </label></td>
        		<td style="color:#000;text-align:center;width:250px"><label><B><%=jenisBea %></B> </label></td>
        		<td style="background:#d9e1e5;color:#369;text-align:left;width:125px"><label><B>BERLAKU PADA KAMPUS</B> </label></td>
        		<td style="color:#000;text-align:center;width:250px"><label><B><%=scopeKampus %></B> </label></td>
        	</tr>
        	<tr>
        		<td style="background:#d9e1e5;color:#369;text-align:left;width:125px"><label><B>NAMA INSTANSI</B> </label></td>
        		<td style="color:#000;text-align:center;width:250px"><label><B><%=namaInstansi %></B> </label></td>
        		<td style="background:#d9e1e5;color:#369;text-align:left;width:125px"><label><B>JENIS INSTANSI</B> </label></td>
        		<td style="color:#000;text-align:center;width:250px"><label><B><%=jenisInstansi %></B> </label></td>
        	</tr>
        	<tr>
        		<td style="background:#d9e1e5;color:#369;text-align:left;width:125px"><label><B>BESARAN DANA / PERIODE</B> </label></td>
        		<td style="color:#000;text-align:center;width:250px"><label><B><%=NumberFormater.indoNumberFormat(jumDana) %></B> </label></td>
        		<td style="background:#d9e1e5;color:#369;text-align:left;width:125px"><label><B>UNIT PERIODE</B> </label></td>
        		<td style="color:#000;text-align:center;width:250px"><label><B><%=unitPeriode.toUpperCase() %></B> </label></td>
        	</tr>
        	<tr>
        		<td style="background:#369;color:#fff;text-align:center" colspan="4"><label><B>PERSYARATAN</B> </label></td>
        	</tr>
        	<tr>
        		<td style="color:#000;text-align:left;width:250px" colspan="4"><label><B><%=syarat %></B> </label></td>
        	</tr>
        </table>			
        <br/>
		<%		
			}
		%>
		
		<%	
		}
		%>
		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>