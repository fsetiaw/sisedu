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
	Vector vListInfo = (Vector) request.getAttribute("vListInfo");
	String kdkmk = request.getParameter("kdkmk");
	String atMenu = request.getParameter("atMenu");
	String nakmk = request.getParameter("nakmk");
	String sksmk = request.getParameter("sksmk");
	String kdpst_nmpst= request.getParameter("kdpst_nmpst");
	String shiftKls = request.getParameter("shiftKls");
	System.out.println("vListInfo size = "+vListInfo.size());
	System.out.println("kdkmk = "+kdkmk);
	System.out.println("sksmk = "+sksmk);
	System.out.println("nakmk = "+nakmk);
%>
</head>
<body onload="location.href='#'">
<div id="header">
<ul>
	<li><a href="go.prepMhsPerKelas?kdpst_nmpst=<%=kdpst_nmpst %>&atMenu=<%=atMenu %>" target="_self">GO<span>BACK</span></a></li>
</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<%
		
		if(vListInfo!=null && vListInfo.size()>0) {
			//tknCivitasInfo = kdpst+"#&"+npmhs+"#&"+nimhs+"#&"+nmmhs+"#&"+shift+"#&"+kdjek+"#&"+smawl;
			ListIterator li = vListInfo.listIterator();
			String brs = (String)li.next();
			StringTokenizer st = new StringTokenizer(brs,"#&");
			String kdpst = st.nextToken();
			String npmhs = st.nextToken();
			String nimhs = st.nextToken();
			String nmmhs = st.nextToken();
			String shift = st.nextToken();
			String kdjek = st.nextToken();
			String smawl = st.nextToken();
			int i=1;
		%>
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:550px">
			<tr>
	       		<td style="background:#369;color:#fff;text-align:center" colspan="5"><label><B>MHS MATAKULIAH <%=nakmk %>(<%=kdkmk %>) SHIFT <%=shiftKls %> </B> </label></td>
	       	</tr>	
			<tr>
	       		<td style="background:#369;color:#fff;text-align:center"><label><B>NO.</B> </label></td>
	       		<td style="background:#369;color:#fff;text-align:center"><label><B>NPM</B> </label></td>
	       		<td style="background:#369;color:#fff;text-align:center"><label><B>NAMA MAHASISWA</B> </label></td>
	       		<td style="background:#369;color:#fff;text-align:center"><label><B>ANGKATAN</B> </label></td>
	       		<td style="background:#369;color:#fff;text-align:center"><label><B>SHIFT</B> </label></td>
	       	</tr>
	       	<tr>
	       		<td style="color:#000;text-align:center"><label><%=i++ %></label></td>
	       		<td style="color:#000;text-align:center"><label><a href="people.search?kword=<%=npmhs %>"><%=npmhs %></a></label></td>
	       		<td style="color:#000;text-align:center"><label><%=nmmhs %></label></td>
	       		<td style="color:#000;text-align:center"><label><%=smawl %></label></td>
	       		<td style="color:#000;text-align:center"><label><%=shift %></label></td>
	       	</tr>	
	       	
		<%	
			while(li.hasNext()) {
			//tknCivitasInfo = kdpst+"#&"+npmhs+"#&"+nimhs+"#&"+nmmhs+"#&"+shift+"#&"+kdjek+"#&"+smawl;
				brs = (String)li.next();
				st = new StringTokenizer(brs,"#&");
				kdpst = st.nextToken();
				npmhs = st.nextToken();
				nimhs = st.nextToken();
				nmmhs = st.nextToken();
				shift = st.nextToken();
				kdjek = st.nextToken();
				smawl = st.nextToken();
		%>	
			<tr>
	       		<td style="color:#000;text-align:center"><label><%=i++ %></label></td>
	       		<td style="color:#000;text-align:center"><label><a href="people.search?kword=<%=npmhs %>"><%=npmhs %></a></label></td>
	       		<td style="color:#000;text-align:center"><label><%=nmmhs %></label></td>
	       		<td style="color:#000;text-align:center"><label><%=smawl %></label></td>
	       		<td style="color:#000;text-align:center"><label><%=shift %></label></td>
	       	</tr>
		<%		
			}
		%>
		</table>
		<%
		}
		else {
		%>
		<h3 style="text-align=center">JUMLAH MAHASISWA DALAM LIST = 0 mhs </h3>
		<%
		}
		%>
		<!-- Column 1 start -->
	</div>
</div>			
</body>
</html>