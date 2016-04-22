<!DOCTYPE html>
<html>
<head>

<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.owasp.esapi.ESAPI" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.Vector" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="java.util.StringTokenizer" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--  link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/css/iconWithNotofication/style1.css" media="screen" /-->

<title>Insert title here</title>
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
/*
ubah menggunakan session value
*/

String tknKrsNotifications = (String)session.getAttribute("tknKrsNotifications");
//String tknKrsNotifications = request.getParameter("tknKrsNotifications");
String tknKrsNotificationsForSender = (String)session.getAttribute("tknKrsNotificationsForSender");
//String tknKrsNotificationsForSender = request.getParameter("tknKrsNotificationsForSender");
Vector vBk = (Vector) session.getAttribute("vBukaKelas");
JSONArray jsoaPymntReq = (JSONArray) session.getAttribute("jsoaPymntReq");
String newMsgOnMonitoredInbox= request.getParameter("newMsgOnMonitoredInbox");
String newMsgOnOwnInbox = request.getParameter("newMsgOnOwnInbox");
String listKdpstBk = (String) request.getAttribute("listKdpstBukaKelas");
String kdjenKdpstNmpstNoPengajuan = (String) request.getAttribute("kdjenKdpstNmpstNoPengajuan");
String hasBukaKelasCmd = (String)request.getAttribute("hasBukaKelasCmd");
String ada_pengajuan = (String)session.getAttribute("ada_pengajuan");
//System.out.println("tknKrsNotifications="+tknKrsNotifications);
//System.out.println("tknKrsNotificationsForSender="+tknKrsNotificationsForSender);
/*
*==================remove session object ===================================
*/
session.removeAttribute("goto_approval");
session.removeAttribute("job");
session.removeAttribute("vHisBea");
session.removeAttribute("vListNamaPaketBea");
session.removeAttribute("vMhsUnHeregContainment");
session.removeAttribute("kodeKampus");
session.removeAttribute("dataForRouterAfterUpload");
session.removeAttribute("hakAksesUsrUtkFolderIni");
session.removeAttribute("v_nm_alm_access");
session.removeAttribute("target_kelas_info");
session.removeAttribute("vMhsContainer");
session.removeAttribute("kelasTambahan");
session.removeAttribute("forceBackTo");
session.removeAttribute("listMonitorNickname");
session.removeAttribute("fieldAndValue");
session.removeAttribute("v1v2v3");
session.removeAttribute("vInfoListKandidat");
Vector vReqAprKeu = (Vector)session.getAttribute("vReqAprKeu");
session.removeAttribute("listTipeUjian");
session.removeAttribute("validatedTransDate");
session.removeAttribute("infoKelasDosen");
session.removeAttribute("infoKelasMhs");
session.removeAttribute("vJsoa");
session.removeAttribute("saveToFolder");
session.removeAttribute("targetAkun");
session.removeAttribute("sumberDana");
session.removeAttribute("vListKelasAjar");
session.removeAttribute("vListKategori");
session.removeAttribute("vAssigned");
session.removeAttribute("vListForMhs");
session.removeAttribute("vCp");
session.removeAttribute("vInfoKehadiranDosen");
session.removeAttribute("target_npm");
session.removeAttribute("status_update");
session.removeAttribute("ada_pengajuan");
session.removeAttribute("vPengajuan");
session.removeAttribute("vRiwayatPengajuan");
%>


  	
  	
  	
  	
  	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/js/accordion/accordion.css" media="screen" />
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/accordion/accordion-center.js"></script>
	<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/css/bootstrap.min.css">
  <script src="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
  
  <link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/buttonsNotofication/css/style.css" media="screen" />
</head>
<body>
<div class="colmask fullpage">
	<div class="col1">
		<!--  div class="container-fluid" -->
			<!--  div class="row" -->
<!--
===================================================================================
	NOTE: home.jsp HARUS SELALU DIPANGGIL VIA get.notifications
======================================================================================	
-->
<!-- 
<div class="notifications">
<div class="new-message">
    <a href="#" target="_top">6</a>
</div>
<div class="messages">
    <a href="#" target="_top">Messages</a>
</div>
</div>
 -->
<table >
	<tr>
	<!--  
		<td width="50px">
			<div id="content_icon">
  			<div id="pastille" style="display:1;">29</div>
  			<section id="icone_click">
    			<h1></h1>
  			</section>
  			
		</td>
	-->	
<%
/*
<bagian penerima>
*/
if(tknKrsNotifications!=null && !Checker.isStringNullOrEmpty(tknKrsNotifications)) {
	StringTokenizer st = new StringTokenizer(tknKrsNotifications,"||");
	application.setAttribute("tknKrsNotifications", tknKrsNotifications);
	//System.out.println("1."+tknKrsNotifications);
%>
	
		<td>
			<section class="container">
			<%
			/*
			%>
    		<a href="<%=Constants.getRootWeb() %>/InnerFrame/Notifications/krsNotification.jsp?tknKrsNotifications=<%=tknKrsNotifications.substring(0,6925) %>" class="share-btn" target="inner_iframe" -->
    		<%
			*/
			%>
    		<a href="<%=Constants.getRootWeb() %>/InnerFrame/Notifications/krsNotification.jsp" class="share-btn" target="inner_iframe">
      		<span class="share-btn-action share-btn-tweet">KRS</span>
      		<span class="share-btn-count"><%=st.countTokens() %></span>
    		</a> 
  			</section>
		</td>
<%
}
/*
<bagian pengirim>
*/
if(tknKrsNotificationsForSender!=null && !Checker.isStringNullOrEmpty(tknKrsNotificationsForSender)) {
	//System.out.println("ini0="+tknKrsNotificationsForSender);
	StringTokenizer st = new StringTokenizer(tknKrsNotificationsForSender,"||");
%>
	
		<td>
			<section class="container">
			<% 
			/*
			%>
    		<a href="<%=Constants.getRootWeb() %>/InnerFrame/Notifications/krsNotification.jsp?tknKrsNotificationsForSender=<%=tknKrsNotificationsForSender %>" class="share-btn" target="inner_iframe">
      		<% 
			*/
			%>
			<a href="<%=Constants.getRootWeb() %>/InnerFrame/Notifications/krsNotification.jsp" class="share-btn" target="inner_iframe">
      		<span class="share-btn-action share-btn-tweet">KRS</span>
      		<span class="share-btn-count"><%=st.countTokens() %></span>
    		</a> 
  			</section>
		</td>
<%
}
if(hasBukaKelasCmd!=null && hasBukaKelasCmd.equalsIgnoreCase("yes")) {
	if((listKdpstBk!=null && !Checker.isStringNullOrEmpty(listKdpstBk)) || (kdjenKdpstNmpstNoPengajuan!=null && !Checker.isStringNullOrEmpty(kdjenKdpstNmpstNoPengajuan))) {
	
%>		
		<td>
			<section class="container">
    		<!--  a href="<%=Constants.getRootWeb() %>/InnerFrame/Notifications/bukaKelasNotification.jsp?listKdpstBk=<%=listKdpstBk %>" class="share-btn" target="inner_iframe" -->
    		<a href="process.statusRequestBukaKelas?listKdpstBk=<%=""+listKdpstBk %>&infoKdpstNoPengajuan=<%=""+kdjenKdpstNmpstNoPengajuan %>" class="share-btn" target="inner_iframe">
      		<span class="share-btn-action share-btn-tweet">kuliah</span>
      		<span class="share-btn-count">Kelas</span>
    		</a> 
  			</section>
		</td>
<%
	}
}


if(newMsgOnOwnInbox!=null && newMsgOnOwnInbox.equalsIgnoreCase("true")) {
%>
		<td>
			<section class="container">
    		<!--  a href="<%=Constants.getRootWeb() %>/InnerFrame/Notifications/bukaKelasNotification.jsp?listKdpstBk=<%=listKdpstBk %>" class="share-btn" target="inner_iframe" -->
    		<a href="get.msgInbox?sta_index=0&range=<%=Constants.getRangeMgsInbox()%>&show=unread" class="share-btn" target="inner_iframe">
      		<span class="share-btn-action share-btn-tweet">Anda</span>
      		<span class="share-btn-count">Inbox</span>
    		</a> 
  			</section>
		</td>
<%
//
}
if(newMsgOnMonitoredInbox!=null && newMsgOnMonitoredInbox.equalsIgnoreCase("true")) {
%>
		<td>
			<section class="container">
    		<!--  a href="<%=Constants.getRootWeb() %>/InnerFrame/Notifications/bukaKelasNotification.jsp?listKdpstBk=<%=listKdpstBk %>" class="share-btn" target="inner_iframe" -->
    		<a href="get.msgInbox?monitoring=true" class="share-btn" target="inner_iframe">
      		<span class="share-btn-action share-btn-tweet">Monitor</span>
      		<span class="share-btn-count">Inbox</span>
    		</a> 
  			</section>
		</td>
<%
}

//if(validUsr.isAllowTo("pymntApprovee")>0 && vReqAprKeu!=null && vReqAprKeu.size()>0) {
if(validUsr.isAllowTo("pymntApprovee")>0 && jsoaPymntReq!=null && jsoaPymntReq.length()>0) {
%>
	<td>
		<section class="container">
		<a href="<%=Constants.getRootWeb() %>/InnerFrame/Keu/requestKeuAprovalForm.jsp" class="share-btn" target="inner_iframe">
  		<span class="share-btn-action share-btn-tweet">Bayaran</span>
  		<span class="share-btn-count">Bukti</span>
		</a> 
			</section>
	</td>
<%	
}
//System.out.println("ada pengajuan = "+ada_pengajuan);
if(ada_pengajuan!=null && ada_pengajuan.equalsIgnoreCase("true")) {
	
	String alm = "";
	if(validUsr.getObjNickNameGivenObjId().contains("MHS")||validUsr.getObjNickNameGivenObjId().contains("mhs")) {
		alm = "get.pengajuanUa?atMenu=ua";
	}
	else {
		alm = "get.pengajuanUa?atMenu=ua";
	}
%>	
	<td>
		<section class="container">
		<a href="<%=alm %>" class="share-btn" target="inner_iframe">
  		<span class="share-btn-action share-btn-tweet">Sidang</span>
  		<span class="share-btn-count">Ujian</span>
		</a> 
			</section>
	</td>	
<%
}
%>	
	</tr>
	</table>
	<br/>
<!-- ===============INFO KEHADIRAN DOSEN ======================  -->	
	
<%
	//info kehadiran dosen
	String info_kehadiran_dosen = (String) request.getAttribute("info_kehadiran_dosen");
	//out.print(""+info_kehadiran_dosen);
	if(validUsr.getObjNickNameGivenObjId().contains("MHS") && (info_kehadiran_dosen!=null && !Checker.isStringNullOrEmpty(info_kehadiran_dosen))) {
%>	

	<div class="col-sm-11" >
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
		 		</div>
		 		
		 		
	
	<%
	}
	%>
	<!-- ===============END INFO KEHADIRAN DOSEN ======================  -->

</div>
</div>


<div id="footer">

</div>
</body>
</html>