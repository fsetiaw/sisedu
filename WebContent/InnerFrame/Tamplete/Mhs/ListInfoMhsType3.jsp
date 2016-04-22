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
Vector vListMhs = (Vector) session.getAttribute("vListMhs");
//System.out.println("vListMhs size = "+vListMhs.size());
String cuid = request.getParameter("cuid");
String nakmk = request.getParameter("nakmk");
String kdkmk = request.getParameter("kdkmk");
String shift = request.getParameter("shift");
String nopll= request.getParameter("nopll");
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
		if(vListMhs!=null && vListMhs.size()>0) {
			ListIterator li = vListMhs.listIterator();	
			String nmmdos = (String)li.next();
		%>
		<table align="center" border="1px" bordercolor="369" style="background:#d9e1e5;color:#000;width:750px">
        	<tr>
        		<td style="background:#369;color:#fff;text-align:center" colspan="5">
        		<B>  MATA KULIAH : <%=kdkmk%> - <%=nakmk %>
        		<br/>NAMA DOSEN  :  <%=nmmdos %> 
        		<br/>SHIFT  :  <%=shift %> 
        		<br/>NO KLS PARALEL : <%=nopll %>
        		</B> </td>
        	</tr>
        	<tr>
        		<td style="background:#369;color:#fff;text-align:center;width:25px"><B>NO.</B> </td>
        		<td style="background:#369;color:#fff;text-align:center;width:200px"><B>PRODI</B> </td>
        		<td style="background:#369;color:#fff;text-align:center;width:75px"><B>ANGKATAN</B> </td>
        		<td style="background:#369;color:#fff;text-align:center;width:150px"><B>NPM</B> </td>
        		<td style="background:#369;color:#fff;text-align:center;width:300px"><B>NAMA</B> </td>
        	</tr>
        <%
        	int i=0;
        	do {
        		i++;;
        		String brs = (String) li.next();
        		//li.set(nmmhs+"`"+npmhs+"`"+kdpst+"`"+smawl+"`"+id_obj+"`"+obj_lvl);
        		StringTokenizer st = new StringTokenizer(brs,"`");
        		String nmmhs = st.nextToken();
        		String npmhs = st.nextToken();
        		String kdpst = st.nextToken();
        		String smawl = st.nextToken();
        		String id_obj = st.nextToken();
        		String obj_lvl = st.nextToken();
        %>
        	<tr>
        		<td style="text-align:center;"><B><%=i %></B> </td>
        		<td style="text-align:center;"><B><%=Converter.getNamaKdpst(kdpst) %></B> </td>
        		<td style="text-align:center;"><B><%=smawl %></B> </td>
        		<td style="text-align:center"><B>
        		<a href="get.histKrs?id_obj=<%=id_obj%>&nmm=<%=nmmhs%>&npm=<%=npmhs %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=histKrs&backTo=history" target="_self" >
        		<%=npmhs %>
        		</a>
        		</B> </td>
        		<td style="text-align:left;padding:0 0 0 5px"><B><%=nmmhs %></B> </td>
        	</tr>
        <%		
        	}
        	while(li.hasNext());
        %>
        </table>
        <%
        }
		else {
		%>
			<h3>ERROR : EMPTY RESULT</h3>
		<%	
		}
        %>
        <!-- Column 1 end -->
	</div>
</div>

</body>
</html>
