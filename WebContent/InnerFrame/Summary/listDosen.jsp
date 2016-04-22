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
	//String [] listKdpst = Constants.getListKdpstProdi();
	String kdpst_ds= request.getParameter("kdpst");
	String tipe_ika_ds= request.getParameter("tipe_ika");
	//beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	SearchDbInfoMhs sdm = new SearchDbInfoMhs(validUsr.getNpm());
	
	
	//Vector v = (Vector)session.getAttribute("vSummaryPMB");
	//String target_thsms = request.getParameter("target_thsms");
	//if(target_thsms==null || target_thsms.equalsIgnoreCase("null")) {
	//	target_thsms = ""+thsms_pmb;
	//}
	//Vector vListThsms = Tool.returnTokensListThsms("20001", thsms_pmb);
	
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
		JSONArray jsoaIcomplete  = Getter.readJsonArrayFromUrl("/v1/search_dsn/kdpst/"+kdpst_ds+"/tipe_ika/"+tipe_ika_ds);
	
		//sudah sampe kesisni jsoa ngga mungkin kosong	
		%>
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:550px;">
		<!--  tr>
        	<td style="background:#369;color:#fff;text-align:center" colspan="9"><label><B>PRODI</B> </label></td>
        </tr -->
        <tr>
        	<td style="background:#369;color:#fff;text-align:center;" colspan="3"><label><B>DOSEN <%=Converter.getKeterTipeIka(tipe_ika_ds).toUpperCase() %> <%=Converter.getNamaKdpst(kdpst_ds).toUpperCase() %></B> </label></td>
        </tr>
        <tr>
        	<td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>NO.</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center;width:150px"><label><B>NPM</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center;width:350px"><label><B>NAMA DOSEN</B> </label></td>
        </tr>

		<%
		int norut = 1;
		for(int j=0;j<jsoaIcomplete.length();j++) {
			JSONObject jobTmp = jsoaIcomplete.getJSONObject(j);
			String npm = jobTmp.getString("NPMHS");
			String nmm = jobTmp.getString("NMMHSMSMHS");
			String tkn_info = sdm.prepForGetProfileMhs(npm);
			StringTokenizer st = new StringTokenizer(tkn_info,"||");
			String nmmds=st.nextToken();
			String nimds=st.nextToken();
			String stmds=st.nextToken();
			String id_obj=st.nextToken();
			String obj_lvl=st.nextToken();
			String kdpst=st.nextToken();
		%>
		<tr>
			<td style="text-align:center;width:50px" ><label><B><%=norut++ %>.</B> </label></td>
        	<td style="text-align:center;width:150px"><label><B>
        	<a href="get.dataDosen?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&atMenu=dataDosen"  target="_self"><%=npm %></a></B> </label></td>
        	<td style="text-align:left;width:350px"><label><B><%=nmm %></B> </label></td>
        </tr>
        <%
        }
        %>	
	</table>
	<br/>	

	</div>
</div>	
</body>
</html>