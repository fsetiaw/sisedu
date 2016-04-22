<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<title>Insert title here</title>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>	
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.dbase.pengajuan.ua.*" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/js/accordion/accordion.css" media="screen" />
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/accordion/accordion-center.js"></script>
	<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/css/bootstrap.min.css">
  <script src="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>

<%
////System.out.println("siop");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");

Vector v= null;
SearchDbUa sdbu = new SearchDbUa(validUsr.getNpm());
 
%>

</head>
<body>
<div id="header">
<%@ include file="innerMenu.jsp"%>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
	<%	
			if(validUsr.getObjNickNameGivenObjId().contains("ADMIN")) {
				String stm = (String)session.getAttribute("status_akhir_mahasiswa");
	%>
	
	<%="curr_stmhs="+stm %>
	<%
	}
	%>
		<!-- Column 1 start -->
		
		<div class="container-fluid">
			<div class="row">
    
    <%
		Vector v_riwayat_pengajuan_ujian = sdbu.getRiwayatUaForDashboardMhs(v_npmhs);
		if(v_riwayat_pengajuan_ujian!=null && v_riwayat_pengajuan_ujian.size()>0) {
		%>
				<div class="col-sm-4" >
					<div class="accordion">
    					<div class="accordion-section">
        					<a class="accordion-section-title-center" href="#accordion-1">RIWAYAT UJIAN SIDANG</a>
         					<div id="accordion-1" class="accordion-section-content">
         					<ul>
        	<%
		
			ListIterator liu = v_riwayat_pengajuan_ujian.listIterator();
        	int norut = 1;
			if(liu.hasNext()) {
				%>
				
				<%
				do {
					String brs = (String)liu.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					//li.add(kdkmk+"`"+date+"`"+time+"`"+status);
					String kdkmk = st.nextToken();
					String date = st.nextToken();
					String time = st.nextToken();
					String status = st.nextToken();
					out.println("<li>Ujian "+kdkmk+" telah dilaksanakan pada tanggal "+Converter.convertFormatTanggalKeFormatDeskriptif(date)+" - "+time+"</li>");
					norut++;
				}
				while(liu.hasNext());
			}
		
		%>
							</ul>
        					</div>
						</div>
					</div>
		 		</div>
		<%
			}

		
		if(validUsr.isUsrAllowTo("deleteMhs", npm, obj_lvl)){
			//request.setAttribute("v_profile",v);
		%>
				<div class="col-sm-4" >
					<div class="accordion">
    					<div class="accordion-section">
        					<a class="accordion-section-title-center" href="#accordion-1">HAPUS MAHASISWA INI</a>
         					<div id="accordion-1" class="accordion-section-content">
								<form action="validasiHapusMhs.jsp">
									<input type="submit" value="HAPUS MAHASISWA" style="color:red;font-weight:bold" />
									<input type="hidden" name="nmm" value="<%=nmm %>" />
									<input type="hidden" name="npm" value="<%=npm %>" />
									<input type="hidden" name="objId" value="<%=objId %>" />
									<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
									<input type="hidden" name="kdpst" value="<%=kdpst %>" />
									<input type="hidden" name="backTo" value="dashHomeMhs.jsp" />	
								</form>
							</div>
						</div>
					</div>
		 		</div>
		 		
		 		<div class="col-sm-4" >
					<div class="accordion">
    					<div class="accordion-section">
        					<a class="accordion-section-title-center" href="#accordion-1">AKSES MOODLE</a>
         					<div id="accordion-1" class="accordion-section-content">
								<form action="go.PrepFormMoodle">
									<input type="submit" value="PROSES AKSES" style="color:red;font-weight:bold" />
									<input type="hidden" name="nmm" value="<%=nmm %>" />
									<input type="hidden" name="npm" value="<%=npm %>" />
									<input type="hidden" name="objId" value="<%=objId %>" />
									<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
									<input type="hidden" name="kdpst" value="<%=kdpst %>" />
									<input type="hidden" name="backTo" value="dashHomeMhs.jsp" />	
								</form>
							</div>
						</div>
					</div>
		 		</div>
		<%
		}
		%>
    			<div class="col-sm-4" > </div>
  			</div>
		</div>		
	</div>
</div>	

</body>
</html>