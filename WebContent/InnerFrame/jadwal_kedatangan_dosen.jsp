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
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/js/accordion/accordion.css" media="screen" />
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"></script>
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/accordion/accordion-center.js"></script>
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/css/bootstrap.min.css">
<script src="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
if(validUsr==null) {
	response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
}
%>
</head>
<body>
<div id="header">
	<ul>
		<li><a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self"> 
		<!--  a href="get.notifications" target="inner_iframe" -->GO<span>BACK</span></a></li>
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<!-- ===============INFO KEHADIRAN DOSEN ======================  -->	
	
<%
	//info kehadiran dosen
	String info_kehadiran_dosen = (String) request.getParameter("info_dosen_arrival");
	//out.print(""+info_kehadiran_dosen);
	if(validUsr.getObjNickNameGivenObjId().contains("MHS") && (info_kehadiran_dosen!=null && !Checker.isStringNullOrEmpty(info_kehadiran_dosen))) {
%>	

	
					<div class="accordion">
    					<div class="accordion-section">
        					<a class="accordion-section-title-center" href="#accordion-1">INFORMASI JADWAL PERKULIAHAN</a>
         					<div id="accordion-1" class="accordion-section-content">
         					<ul>
         					<div class="container1" >
  		            
  		<table class="table" align="left">
    	<thead>

      	<tr>
	        <th>Matakuliah</th>
    	    <th>Nama Dosen</th>
    	    <th>Tanggal</th>
        	<th>Status</th>
      	</tr>
    	</thead>
    	<tbody>
      	
      	<%
      	StringTokenizer st = new StringTokenizer(info_kehadiran_dosen,"`");
      	while(st.hasMoreTokens()) {
      		//info_kls = info_kls+cuid+"`"+kdkmk+"`"+kdpst+"`"+nakmk+"`"+batal+"`"+delay_time+"`"+tgl_tm+"`";
      		// or
			//info_kls = info_kls+cuid+"`"+kdkmk+"`"+kdpst+"`"+nakmk+"`null`";
      		String cuid = st.nextToken();
      		String kdkmk = st.nextToken();
      		String kdpst = st.nextToken();
      		String nakmk = st.nextToken();
      		String batal = st.nextToken();
      		String delay_time = "";
      		String tgl_tm = "";
      		String nmmdos = "";
      		String npmdos = "";
      		String nmmasdos = "";
      		String npmasdos = "";
      	%>
      	<tr class="info">
        	<td><%=kdkmk %> - <%=nakmk %></td>
        	
      	<%	
      		if(batal.equalsIgnoreCase("null")) {

      	%>
      		<td>N/A</td>
      		<td colspan="2" style="text-align:center">Belum Ada Informasi</td>
      	<%		
      		}
      		else {
      			if(batal.equalsIgnoreCase("true")) {
      				nmmdos =  st.nextToken();
      				npmdos =  st.nextToken();
      				nmmasdos =  st.nextToken();
      				npmasdos =  st.nextToken();
      				delay_time = st.nextToken();
          			tgl_tm = st.nextToken();
      			
      	%>
      		<td><%=nmmdos %></td>
      	    <td>
      	    <%
      	    if(tgl_tm!=null && !Checker.isStringNullOrEmpty(tgl_tm)) {
      	    	out.print(Converter.reformatSqlDateToTglBlnThn(tgl_tm));
      	    }
      	  %></td>
       		<td>Batal / Dipindahkan ke lain tanggal</td>
      	<%	
      			}
      			else {    
      				nmmdos =  st.nextToken();
      				npmdos =  st.nextToken();
      				nmmasdos =  st.nextToken();
      				npmasdos =  st.nextToken();
      				delay_time = st.nextToken();
          			tgl_tm = st.nextToken();
       	%>
       		<td><%=nmmdos %></td>
       		<td>
       		<%
       		if(tgl_tm!=null && !Checker.isStringNullOrEmpty(tgl_tm)) {
      	    	out.print(Converter.reformatSqlDateToTglBlnThn(tgl_tm));
      	    }
       	 %>
			</td>
       		<td>
       		<%
       		try {
       			if(Integer.parseInt(delay_time)>0) {
       		%>
       			Terlambat <%=delay_time %> menit
       		<%		
       			}
       			else {
       		%>
           		Tepat Waktu
           	<%		
       			}
       		} catch (Exception e) {
       		%>
       			Belum Ada Informasi
       		<%	
       		}
       		%>
       		
       		</td>
        <% 
      			}
      		}
      	
      	%>
      
        	
        	
	   	</tr>
	   	<%
	   	}
	   	%>
    	</tbody>
  	</table>
	</div>
         					</ul>
        					
						</div>
					</div>
		 		</div>
		 		
		 		
	
	<%
	}
	%>
	<!-- ===============END INFO KEHADIRAN DOSEN ======================  -->
		<!-- bagian yg menerima reques -->
		

	</div>
</div>		
</body>
</html>