<!DOCTYPE html>
<html>
<head>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>
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
	//String thsms_pmb = Checker.getThsmsPmb();
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String [] listKdpst = Constants.getListKdpstProdi();
	
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
		JSONArray jsoaDs = Getter.readJsonArrayFromUrl("/v1/search_dsn");
		Vector vKdpstScope = new Vector();
		ListIterator lipst = vKdpstScope.listIterator();
		Vector v_cf = validUsr.getScopeUpd7des2012("hasSummaryDsnMenu");
		if(v_cf!=null && v_cf.size()>0) {
			ListIterator licf = v_cf.listIterator();
			while(licf.hasNext()) {
				String brs = (String)licf.next();
				StringTokenizer st = new StringTokenizer(brs);
				st.nextToken();
				String kdpst_scope = st.nextToken();
				boolean match = false;
				for(int i=0;i<listKdpst.length && !match;i++) {
					if(listKdpst[i].equalsIgnoreCase(kdpst_scope)) {
						match = true;
						lipst.add(listKdpst[i]);
					}
				}
				//out.print(brs+"<br/>");
			}
		}
		
		if(vKdpstScope!=null && vKdpstScope.size()>0) {
			
		%>
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:750px;">
		<!--  tr>
        	<td style="background:#369;color:#fff;text-align:center" colspan="9"><label><B>PRODI</B> </label></td>
        </tr -->
        <tr>
        	<td style="background:#369;color:#fff;text-align:center;width:50px" rowspan="2"><label><B>NO.</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center;width:200px" rowspan="2"><label><B>PROGRAM STUDI</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center;width:500px" colspan="<%=Constants.getTipeIkatanKerjaDosen().length%>"><label><B>TIPE DOSEN</B> </label></td>
        </tr>
		<tr>
		<%
        	String[]tipeIkaDsn = Constants.getTipeIkatanKerjaDosen();
        	for(int i=0;i<tipeIkaDsn.length;i++) {
        		StringTokenizer st = new StringTokenizer(tipeIkaDsn[i],"-");
        		String kode = st.nextToken();
        		String keter = st.nextToken();
        %>
			<td style="background:#369;color:#fff;text-align:center" style="width:100px"><label><B><%=keter.toUpperCase() %></B> </label></td>
		<%
			}
		%>
		</tr>
		<%
			int norut = 1;
			lipst = vKdpstScope.listIterator();
			while(lipst.hasNext()) {
				String kdpst = (String)lipst.next();
				String keter_prodi = Converter.getNamaKdpst(kdpst);
		%>
		<tr>
			<td style="text-align:center;width:50px" ><label><B><%=norut++ %>.</B> </label></td>
        	<td style="text-align:left;width:200px"><label><B><%=keter_prodi %></B> </label></td>
        <%
        		tipeIkaDsn = Constants.getTipeIkatanKerjaDosen();
        		for(int i=0;i<tipeIkaDsn.length;i++) {
	        		int counter = 0;
    	    		StringTokenizer st = new StringTokenizer(tipeIkaDsn[i],"-");
        			String kode = st.nextToken();
        			String keter = st.nextToken();
        			if(jsoaDs!=null && jsoaDs.length()>0) {
        				for(int k=0;k<jsoaDs.length();k++) {
        					JSONObject job = jsoaDs.getJSONObject(k);
        					String kdpst_json = null;
        					String tipeDos_json = null;
        					try {
        						kdpst_json = job.getString("KDPST_HOMEBASE");
	        					tipeDos_json = job.getString("IKATAN_KERJA_DOSEN");
        						if(tipeDos_json.contains(kode) && kdpst_json.equalsIgnoreCase(kdpst)) {
	        						counter++;
        						}
        					}
        					catch (JSONException js) {}
        				}
        				if(counter>0) {
        %>
       		<td style="text-align:center" style="width:100px"><label><B>
       		<a href="listDosen.jsp?kdpst=<%=kdpst %>&tipe_ika=<%=kode %>&atPage=sebaranDosen" style="color:#1E3F60;"><p style="text-align:center"><%=counter %></p></a>
       		</B> </label></td>
       	<%	
       					}
        				else {
        %>
        	<td style="text-align:center" style="width:100px"><label><B>0</B> </label></td>
       	<% 				
        				}
        			}	
	       			else {
       	%>
       		<td style="text-align:center" style="width:100px"><label><B>0</B> </label></td>
       	<% 				
        			}
        		}	
        %>			
		</tr>
		<%		
			}
		%>
		<tr>
			<td style="text-align:center;width:50px" ><label><B><%=norut++ %>.</B> </label></td>
        	<td style="text-align:left;width:200px"><label><B>DOSEN PT LAIN</B> </label></td>
        <%
        	tipeIkaDsn = Constants.getTipeIkatanKerjaDosen();
        	for(int i=0;i<tipeIkaDsn.length;i++) {
	        	int counter = 0;
    	    	StringTokenizer st = new StringTokenizer(tipeIkaDsn[i],"-");
        		String kode = st.nextToken();
        		String keter = st.nextToken();
        
       	%>
       		<td style="text-align:center" style="width:100px"><label><B>0</B> </label></td>
       	<% 				
        	}	
        %>			
		</tr>	
	</table>
	<br/>	
	<%
		}
		else {
			out.print("ANDA TIDAK MEMILIKI HAK AKSES UNTUK DATA INI");
		}
	
	
		//cek apa ada dosen kita yg datanya incomplete
		String listtipeIkaDsn = "";
		String []tipeIkaDsn = Constants.getTipeIkatanKerjaDosen();
		if(tipeIkaDsn!=null && tipeIkaDsn.length>0) {
			for(int l=0;l<tipeIkaDsn.length;l++) {
				StringTokenizer st = new StringTokenizer(tipeIkaDsn[l],"-");
				String kode = st.nextToken();
				String keter = st.nextToken();
				if(l==0) {
					listtipeIkaDsn = ""+kode;
				}
				else {
					listtipeIkaDsn = listtipeIkaDsn +"|"+kode;	
				}
				
			}
		}
		//scope prodi
		String listProdi = "";
		lipst = vKdpstScope.listIterator();
		boolean first = true;
		while(lipst.hasNext()) {
			String kdpst = (String)lipst.next();
			//out.print(kdpst+",");
			if(first) {
				first =false;
				listProdi = ""+kdpst;
			}
			else {
				listProdi = listProdi +"|"+kdpst;	
			}
		}
		//out.print("vKdpstScope size ="+vKdpstScope.size()+"<br/>");
		//out.print("listProdi size ="+listProdi+"<br/>");
		//System.out.println("/v1/search_dsn/data_incomplete/tkn_ika/"+listtipeIkaDsn+"/tkn_prodi/"+listProdi+"/kdpti/"+Constants.getKdpti());
		JSONArray jsoaIcomplete  = Getter.readJsonArrayFromUrl("/v1/search_dsn/data_incomplete/tkn_ika/"+listtipeIkaDsn+"/tkn_prodi/"+listProdi+"/kdpti/"+Constants.getKdpti());
		if(jsoaIcomplete!=null && jsoaIcomplete.length()>0) {
		%>
		<a href="listDosenDataIncomplete.jsp?listProdi=<%=listProdi %>&listTipeIkaDsn=<%=listtipeIkaDsn %>&atPage=sebaranDosen" style="color:red;"><p style="text-align:center">Daftar Dosen Butuh Pemutakhiran Data</p></a>
		<%	
		}

	%>
		
	<%

	
	%>
	</div>
</div>	
</body>
</html>