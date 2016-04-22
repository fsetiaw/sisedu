<!DOCTYPE html>
<html>
<head>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>
<%@ page import="beans.dbase.*" %>
<%@ page import="beans.dbase.mhs.*" %>
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
	//String thsms_pmb = Checker.getThsmsPmb();
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");

	
%>


</head>
<body>
<div id="header">
<jsp:include page="subSummaryDosenMenu.jsp" />
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<%
		JSONArray jsoaDsn  = Getter.readJsonArrayFromUrl("/v1/search_dsn");
		if(jsoaDsn!=null && jsoaDsn.length()>0) {	
			SearchDbInfoMhs sdb = new SearchDbInfoMhs(validUsr.getNpm());
			
		//sudah sampe kesisni jsoa ngga mungkin kosong	
		%>
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:550px;">
		<!--  tr>
        	<td style="background:#369;color:#fff;text-align:center" colspan="9"><label><B>PRODI</B> </label></td>
        </tr -->
        <tr>
        	<td style="background:#369;color:#fff;text-align:center;" colspan="3"><label><B>DOSEN PENGAJAR</B> </label></td>
        </tr>
        <tr>
        	<td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>NO.</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center;width:150px"><label><B>NPM</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center;width:350px"><label><B>NAMA DOSEN</B> </label></td>
        </tr>

		<%
			int norut = 1;
			for(int j=0;j<jsoaDsn.length();j++) {
				JSONObject jobDsn = jsoaDsn.getJSONObject(j);
				String nmm = null;
				String nim = null;
				String stm = null;
				String id = null;
				String lvl = null;
				String kdpst = null;
				String nmmdos = jobDsn.getString("NMMHSMSMHS");
				String npmdos = jobDsn.getString("NPMHS");
				String basic_tkn = sdb.prepForGetProfileMhs(npmdos);
				System.out.println("basic_tkn="+basic_tkn);
				if(basic_tkn!=null && !Checker.isStringNullOrEmpty(basic_tkn)) {
					StringTokenizer st = new StringTokenizer(basic_tkn,"||");
					
					//nmmhs+"||"+nimhs+"||"+stmhs+"||"+id_obj+"||"+obj_lvl+"||"+kdpst;
					nmm = st.nextToken();
					nim = st.nextToken();
					stm = st.nextToken();
					id = st.nextToken();
					lvl = st.nextToken();
					kdpst = st.nextToken();
				}
		%>
		<tr>
			<td style="text-align:center;width:50px" ><label><B><%=norut++ %>.</B> </label></td>
        	<td style="text-align:center;width:150px"><label><B>
        	<a href="get.dataDosen?id_obj=<%=id %>&nmm=<%=nmm %>&npm=<%=npmdos %>&obj_lvl=<%=lvl %>&kdpst=<%=kdpst %>&atMenu=dataDosen"  target="_self"><%=npmdos %></a></B> </label></td>
        	<td style="text-align:left;width:350px"><label><B><%=nmmdos %></B> </label></td>
        </tr>
        <%
        	}
        %>	
	</table>
	<br/>	
	<%
		}
		else {
			out.print("TIDAK ADA DATA DOSEN PENGAJAR");
		}
	 %>

	</div>
</div>	
</body>
</html>