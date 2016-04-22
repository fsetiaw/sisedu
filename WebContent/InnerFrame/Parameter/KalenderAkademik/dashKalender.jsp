<!DOCTYPE html>
<head>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
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
	//String tipeForm = request.getParameter("formType");
	//Vector vDwn = validUsr.getScopeUpd7des2012("hasDownloadMenu");
	//Vector v_cf = validUsr.getScopeUpd7des2012("hasEditMkKurikulum");
%>


</head>
<!--  body onload="location.href='#'" -->
<body>
<div id="header">

<%
String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
String uri = request.getRequestURI();
String url = PathFinder.getPath(uri, target);
JSONArray jsoa_info_akses = (JSONArray) request.getAttribute("jsoa");
String target_param = null;
String scope_prodi = null;
String scope_kmp = null;
String hak_akses = null;
if(jsoa_info_akses!=null && jsoa_info_akses.length()>0) {
	for(int i=0;i<jsoa_info_akses.length();i++) {
		JSONObject job = jsoa_info_akses.getJSONObject(i);
		//String brs = "";
		try {
			target_param = (String)job.get("ACCESS_LEVEL");
			scope_prodi = (String)job.get("ACCESS_LEVEL_CONDITIONAL"); 	
			hak_akses = (String)job.get("HAK_AKSES"); 	
			scope_kmp = (String)job.get("SCOPE_KAMPUS"); 	
			
   		}
   		catch(JSONException e) {}//ignore
   		//out.println(i+"."+brs+"<br/>");
	}	
}
//String inner_menu = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0.jsp";
//out.print(inner_menu);
//out.print(url);
boolean editable = false;
if(hak_akses.contains("e") || hak_akses.contains("i")) {
	editable = true;
}
%>


<jsp:include page="<%= url %>" flush="true" />
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<br/>
		<%
		String id = "";
		String kdpst = "";
		String thsms_sms = "";
		String thsms_pmb = "";
		String thsms_reg = "";
		String thsms_buka_kls = "";
		String thsms_krs = "";
		String tgl_start_krs = "";
		String tgl_end_krs = "";
		String thsms_nilai = "";
		String opr_allow_edit_nilai = "";
		String allow_edit_nilai_all_thsms = "";
		String mhs_allow_input_krs ="";
		String under_maintenance ="";
		String npm_allow_under_maintenance = "";
		String range_thsms = "";
		String range_thsms_sta = "";
		String range_thsms_fokus = "";
		String range_thsms_end = "";
		String approved = "";
		String tgl_orientasi = "";
		String opr_allow_ins_krs="";
		String list_thsms_for_edit = "";
		String tgl_uts_sta = "";
		String tgl_uas_sta = "";
		String tgl_kuliah_sta = "";
		String list_tgl_saring_per_gel = "";
		String tgl_kuliah_end = "";
		String list_tgl_pmb_per_gel = "";
		String list_tgl_hereg_per_gel="";
		String heregistrasi_for_ins_krs = "";
		//System.out.println("range_thsms0--"+range_thsms);
		JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/kalender/aktif");
	
		if(jsoa!=null && jsoa.length()>0) {
			for(int i=0;i<jsoa.length();i++) {
				JSONObject job = jsoa.getJSONObject(i);
				// brs = job.t;
				//int brs = 0;
				try {
					
					id = ""+(Integer)job.getInt("ID");//1
					
					//2. kdpst = (String)job.get("KDPST");
					if(job.isNull("KDPST")) {
						kdpst = "null";
					}
					else {
						
						kdpst = ""+(String)job.get("KDPST");
					}
					
					//3.thsms_sms = (String)job.get("THSMS");
					if(job.isNull("THSMS")) {
						thsms_sms = "null";
					}
					else {
						
						thsms_sms = ""+(String)job.get("THSMS");
					}
					
					
					//4.thsms_pmb = (String)job.get("THSMS_PMB");
					if(job.isNull("THSMS_PMB")) {
						thsms_pmb = "null";
					}
					else {
						
						thsms_pmb = ""+(String)job.get("THSMS_PMB");
					}
					
					//5.thsms_reg = (String)job.get("THSMS_HEREGISTRASI");
					if(job.isNull("THSMS_HEREGISTRASI")) {
						thsms_reg = "null";
					}
					else {
						
						thsms_reg = ""+(String)job.get("THSMS_HEREGISTRASI");
					}
					
					
					//6.thsms_buka_kls = (String)job.get("THSMS_BUKA_KELAS");
					if(job.isNull("THSMS_BUKA_KELAS")) {
						thsms_buka_kls = "null";
					}
					else {
						
						thsms_buka_kls = ""+(String)job.get("THSMS_BUKA_KELAS");
					}
					
					
					//7.thsms_nilai = (String)job.get("THSMS_INP_NILAI_MK");
					if(job.isNull("THSMS_INP_NILAI_MK")) {
						thsms_nilai = "null";
					}
					else {
						
						thsms_nilai = ""+(String)job.get("THSMS_INP_NILAI_MK");
					}
					
					
					//8.thsms_krs = (String)job.get("THSMS_KRS");
					if(job.isNull("THSMS_KRS")) {
						thsms_krs = "null";
					}
					else {
						
						thsms_krs = ""+(String)job.get("THSMS_KRS");
					}
					
					
					//9.tgl_start_krs = (String)job.get("TGL_MULAI_PENGAJUAN_KRS");
					if(job.isNull("TGL_MULAI_PENGAJUAN_KRS")) {
						tgl_start_krs = "null";
					}
					else {
						
						tgl_start_krs = ""+(String)job.get("TGL_MULAI_PENGAJUAN_KRS");
					}
					
					
					//10.tgl_end_krs = (String)job.get("TGL_AKHIR_PENGAJUAN_KRS");
					if(job.isNull("TGL_AKHIR_PENGAJUAN_KRS")) {
						tgl_end_krs = "null";
					}
					else {
						
						tgl_end_krs = ""+(String)job.get("TGL_AKHIR_PENGAJUAN_KRS");
					}
					
					//11.mhs_allow_input_krs = (String) job.get("MHS_ALLOW_PENGAJUAN_KRS");
					if(job.isNull("MHS_ALLOW_PENGAJUAN_KRS")) {
						mhs_allow_input_krs = "null";
					}
					else {
						
						mhs_allow_input_krs = ""+(String)job.get("MHS_ALLOW_PENGAJUAN_KRS");
					}
					
					//12.opr_allow_edit_nilai = (String)job.get("OPERATOR_ALLOW_EDIT_NILAI");
					if(job.isNull("OPERATOR_ALLOW_EDIT_NILAI")) {
						opr_allow_edit_nilai = "null";
					}
					else {
						
						opr_allow_edit_nilai = ""+(String)job.get("OPERATOR_ALLOW_EDIT_NILAI");
					}
					
					//13.opr_allow_ins_krs = (String)job.get("OPERATOR_ALLOW_INS_KRS");
					if(job.isNull("OPERATOR_ALLOW_INS_KRS")) {
						opr_allow_ins_krs = "null";
					}
					else {
						
						opr_allow_ins_krs = ""+(String)job.get("OPERATOR_ALLOW_INS_KRS");
					}
					
					//14.allow_edit_nilai_all_thsms = (String)job.get("ALLOW_EDIT_NILAI_ALL_THSMS");
					if(job.isNull("ALLOW_EDIT_NILAI_ALL_THSMS")) {
						allow_edit_nilai_all_thsms = "null";
					}
					else {
						
						allow_edit_nilai_all_thsms = ""+(String)job.get("ALLOW_EDIT_NILAI_ALL_THSMS");
					}
					
					//15.under_maintenance = (String)job.get("UNDER_MAINTENANCE");
					if(job.isNull("UNDER_MAINTENANCE")) {
						under_maintenance = "null";
					}
					else {
						
						under_maintenance = ""+(String)job.get("UNDER_MAINTENANCE");
					}
					
					//16.
					if(job.isNull("NPM_ALLOW_UNDER_MAINTENANCE")) {
						npm_allow_under_maintenance = "null";
					}
					else {
						
						npm_allow_under_maintenance = ""+(String)job.get("NPM_ALLOW_UNDER_MAINTENANCE");
					}
					
					
					
					//17.range_thsms =  (String)job.get("RANGE_LIST_THSMS");
					if(job.isNull("RANGE_LIST_THSMS")) {
						range_thsms = "null";
					}
					else {
						
						range_thsms = ""+(String)job.get("RANGE_LIST_THSMS");
					}
					
					//18.approved =  (String)job.get("APPROVED"); //belum digunakan
					if(job.isNull("APPROVED")) {
						approved = "null";
					}
					else {
						
						approved = ""+(String)job.get("APPROVED");
					}
					
					//19.list_thsms_for_edit = (String)job.get("LIST_THSMS_FOR_EDIT");
					if(job.isNull("LIST_THSMS_FOR_EDIT")) {
						list_thsms_for_edit = "null";
					}
					else {
						
						list_thsms_for_edit = ""+(String)job.get("LIST_THSMS_FOR_EDIT");
					}
					
					
				    //20.tgl_orientasi = (String)job.get("TGL_ORIENTASI");
				    if(job.isNull("TGL_ORIENTASI")) {
				    	tgl_orientasi = "null";
					}
					else {	
						tgl_orientasi = ""+(String)job.get("TGL_ORIENTASI");
					}
				    
				    //21.tgl_kuliah_sta = (String)job.get("TGL_AWAL_PERKULIAHAN");
				    if(job.isNull("TGL_AWAL_PERKULIAHAN")) {
				    	tgl_kuliah_sta = "null";
					}
					else {	
						tgl_kuliah_sta = ""+(String)job.get("TGL_AWAL_PERKULIAHAN");
					}
				    
				    //22.tgl_uts_sta = (String)job.get("TGL_UTS");
				    if(job.isNull("TGL_UTS")) {
				    	tgl_uts_sta = "null";
					}
					else {	
						tgl_uts_sta = ""+(String)job.get("TGL_UTS");
					}
				    
				    //23.tgl_uas_sta = (String)job.get("TGL_UAS");
				    if(job.isNull("TGL_UAS")) {
				    	tgl_uas_sta = "null";
					}
					else {	
						tgl_uas_sta = ""+(String)job.get("TGL_UAS");
					}
				    
				    //24.tgl_kuliah_end = (String)job.get("TGL_AKHIR_PERKULIAHAN");
				    if(job.isNull("TGL_AKHIR_PERKULIAHAN")) {
				    	tgl_kuliah_end = "null";
					}
					else {	
						tgl_kuliah_end = ""+(String)job.get("TGL_AKHIR_PERKULIAHAN");
					}
				    
					//25.list_tgl_pmb_per_gel =  (String)job.get("LIST_TGL_PMB");
					if(job.isNull("LIST_TGL_PMB")) {
						list_tgl_pmb_per_gel = "null";
					}
					else {	
						list_tgl_pmb_per_gel = ""+(String)job.get("LIST_TGL_PMB");
					}
					
					//26.list_tgl_saring_per_gel =  (String)job.get("LIST_TGL_UJIAN_PENYARINGAN");
					if(job.isNull("LIST_TGL_UJIAN_PENYARINGAN")) {
						list_tgl_saring_per_gel = "null";
					}
					else {	
						list_tgl_saring_per_gel = ""+(String)job.get("LIST_TGL_UJIAN_PENYARINGAN");
					}
					
					//27.list_tgl_hereg_per_gel =  (String)job.get("LIST_TGL_PENDAFTARAN_ULANG");
					if(job.isNull("LIST_TGL_PENDAFTARAN_ULANG")) {
						list_tgl_hereg_per_gel = "null";
					}
					else {	
						list_tgl_hereg_per_gel = ""+(String)job.get("LIST_TGL_PENDAFTARAN_ULANG");
					}
					//28.REGISTRASI_FOR_KRS
					if(job.isNull("REGISTRASI_FOR_KRS")) {
						heregistrasi_for_ins_krs = "null";
					}
					else {	
						heregistrasi_for_ins_krs = ""+(String)job.get("REGISTRASI_FOR_KRS");
					}
					System.out.println("heregistrasi_for_ins_krs="+heregistrasi_for_ins_krs);

		   		}
		   		catch(JSONException e) {
		   			out.println("<br/>"+i+".ada error<br/>");	
		   		}//ignore
		   		
			}	
		}
if(editable)  {		
%>
<form action="go.updateKalender">
<%
}
%>	
<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px">
	<tr>
		<td style="background:#369;color:#fff;text-align:center;width:100%" colspan="2">
		<input type="hidden" name="kdpst" value="<%=kdpst %>" />
		<input type="hidden" name="id" value="<%=id %>" />
		<%
		if(kdpst.equalsIgnoreCase("00000")) {
			out.print("KALENDER AKADEMIK : UNIVERSITAS SATYAGAMA");
			out.print("<br/>");
			out.print("TAHUN / SEMESTER : "+thsms_sms);
		}
		else {
			out.print("HARAP KODING UNTUK KDPST VERSION DILANJUTKAN");
		}
		%>
		</td>
	</tr>
	<tr>
	<td style="width:400px;" valign="top">
	<!-- sisi kiri -->
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:400px">
		<tr>
			<td style="background:#369;color:#fff;text-align:center;" colspan="2">MAINTENANCE STATUS</td>
		</tr>	
		<tr>
			<td style="background:#369;color:#fff;text-align:left;width:50%">UNDER MAINTENANCE</td>
   			<td valign="top" style="text-align:center;width:50%">
   			<%
   			boolean value = false;
   			if(under_maintenance.equalsIgnoreCase("1")) {
   				value = true;
   			}
   			if(editable) {
   				%>
   	   			<input type="text" name="under_maintenance" style="width:98%;text-align:center" value="<%=value%>"/>
   	   			<%		
   			}
   			else {
   				out.print(value);
   			}
   			%>
   			</td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:left;width:50%">LIST NPM ALLOW</td>
   			<td valign="top" style="text-align:center;width:50%">
   				<%
   			if(editable) {
   				if(npm_allow_under_maintenance!=null && !Checker.isStringNullOrEmpty(npm_allow_under_maintenance)) {
   				%>
   	   			<input type="text" name="npm_allow_under_maintenance" style="width:98%;text-align:center" value="<%=npm_allow_under_maintenance %>"/>
   	   			<%		
   				}
   				else {
   				%>
   	   			<input type="text" name="npm_allow_under_maintenance"style="width:98%;text-align:center" placeholder="optional"/>
   	   			<%
   				}
   				
   			}
   			else {
   				out.print(npm_allow_under_maintenance);
   			}
   			%>
   			
   			</td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:center;" colspan="2">JADWAL PERKULIAHAN</td>
		</tr>	
		<tr>
			<td style="background:#369;color:#fff;text-align:left;width:200px">THSMS SEMESTER</td>
   			<td valign="top" style="text-align:center;width:200px">
   			<%
   			if(editable) {
   				if(thsms_sms!=null && !Checker.isStringNullOrEmpty(thsms_sms)) {
   				%>
   	   			<input type="text" name="thsms" style="width:98%;text-align:center" value="<%=thsms_sms%>"/>
   	   			<%		
   				}
   				else {
   				%>
   	   			<input type="text" name="thsms" style="width:98%;text-align:center" placeholder="wajib diisi"/>
   	   			<%
   				}
   				
   			}
   			else {
   				out.print(thsms_sms);
   			}
   			%>
   			</td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:left;width:200px">THSMS PMB</td>
   			<td valign="top" style="text-align:center;width:200px">
   			<%
   			if(editable) {
   				if(thsms_pmb!=null && !Checker.isStringNullOrEmpty(thsms_pmb)) {
   				%>
   	   			<input type="text" name="thsms_pmb" style="width:98%;text-align:center" value="<%=thsms_pmb%>"/>
   	   			<%		
   				}
   				else {
   				%>
   	   			<input type="text" name="thsms_pmb"style="width:98%;text-align:center" placeholder="wajib diisi"/>
   	   			<%
   				}
   				
   			}
   			else {
   				out.print(thsms_pmb);
   			}
   			%>
   			</td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:left;width:200px">THSMS HEREGISTRASI</td>
   			<td valign="top" style="text-align:center;width:200px">
   			<%
   			if(editable) {
   				if(thsms_reg!=null && !Checker.isStringNullOrEmpty(thsms_reg)) {
   				%>
   	   			<input type="text" name="thsms_reg" style="width:98%;text-align:center" value="<%=thsms_reg%>"/>
   	   			<%		
   				}
   				else {
   				%>
   	   			<input type="text" name="thsms_reg"style="width:98%;text-align:center" placeholder="wajib diisi"/>
   	   			<%
   				}
   				
   			}
   			else {
   				out.print(thsms_reg);
   			}
   			%>
   			</td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:left;width:200px">THSMS BUKA KELAS</td>
   			<td valign="top" style="text-align:center;width:200px">
   			<%
   			if(editable) {
   				if(thsms_buka_kls!=null && !Checker.isStringNullOrEmpty(thsms_buka_kls)) {
   				%>
   	   			<input type="text" name="thsms_buka_kls" style="width:98%;text-align:center" value="<%=thsms_buka_kls%>"/>
   	   			<%		
   				}
   				else {
   				%>
   	   			<input type="text" name="thsms_buka_kls"style="width:98%;text-align:center" placeholder="wajib diisi"/>
   	   			<%
   				}
   				
   			}
   			else {
   				out.print(thsms_buka_kls);
   			}
   			%>
   			</td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:left;width:50%">TGL ORIENTASI MHS BARU</td>
   			<td valign="top" style="text-align:center;width:50%">
   			<%
   			if(editable) {
   				if(tgl_orientasi!=null && !Checker.isStringNullOrEmpty(tgl_orientasi)) {
   				%>
   	   			<input type="text" name="tgl_orientasi" style="width:98%;text-align:center" value="<%=DateFormater.prepStringFromDbDateToInputFormFormat(tgl_orientasi) %>"/>
   	   			<%		
   				}
   				else {
   				%>
   	   			<input type="text" name="tgl_orientasi"style="width:98%;text-align:center" placeholder="harap diisi"/>
   	   			<%
   				}
   				
   			}
   			else {
   				out.print(DateFormater.prepStringFromDbDateToInputFormFormat(tgl_orientasi));
   			}
   			%>
   			</td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:left;width:50%">TGL AWAL PERKULIAHAN</td>
   			<td valign="top" style="text-align:center;width:50%">
   			<%
   			if(editable) {
   				if(tgl_kuliah_sta!=null && !Checker.isStringNullOrEmpty(tgl_kuliah_sta)) {
   				%>
   	   			<input type="text" name="tgl_kuliah_sta" style="width:98%;text-align:center" value="<%=DateFormater.prepStringFromDbDateToInputFormFormat(tgl_kuliah_sta) %>"/>
   	   			<%		
   				}
   				else {
   				%>
   	   			<input type="text" name="tgl_kuliah_sta"style="width:98%;text-align:center" placeholder="harap diisi"/>
   	   			<%
   				}
   				
   			}
   			else {
   				out.print(DateFormater.prepStringFromDbDateToInputFormFormat(tgl_kuliah_sta));
   			}
   			%>
   			</td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:left;width:50%">TGL UTS</td>
   			<td valign="top" style="text-align:center;width:50%">
   			<%
   			if(editable) {
   				if(tgl_uts_sta!=null && !Checker.isStringNullOrEmpty(tgl_uts_sta)) {
   				%>
   	   			<input type="text" name="tgl_uts_sta" style="width:98%;text-align:center" value="<%=DateFormater.prepStringFromDbDateToInputFormFormat(tgl_uts_sta) %>"/>
   	   			<%		
   				}
   				else {
   				%>
   	   			<input type="text" name="tgl_uts_sta"style="width:98%;text-align:center" placeholder="harap diisi"/>
   	   			<%
   				}
   				
   			}
   			else {
   				out.print(DateFormater.prepStringFromDbDateToInputFormFormat(tgl_uts_sta));
   			}
   			%>
   			</td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:left;width:50%">TGL UAS</td>
   			<td valign="top" style="text-align:center;width:50%">
   			<%
   			if(editable) {
   				if(tgl_uas_sta!=null && !Checker.isStringNullOrEmpty(tgl_uas_sta)) {
   				%>
   	   			<input type="text" name="tgl_uas_sta" style="width:98%;text-align:center" value="<%=DateFormater.prepStringFromDbDateToInputFormFormat(tgl_uas_sta) %>"/>
   	   			<%		
   				}
   				else {
   				%>
   	   			<input type="text" name="tgl_uas_sta" style="width:98%;text-align:center" placeholder="harap diisi"/>
   	   			<%
   				}
   				
   			}
   			else {
   				out.print(DateFormater.prepStringFromDbDateToInputFormFormat(tgl_uas_sta));
   			}
   			%>
   			</td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:left;width:50%">TGL AKHIR PERKULIAHAN</td>
   			<td valign="top" style="text-align:center;width:50%">
   			<%
   			if(editable) {
   				if(tgl_kuliah_end!=null && !Checker.isStringNullOrEmpty(tgl_kuliah_end)) {
   				%>
   	   			<input type="text" name="tgl_kuliah_end" style="width:98%;text-align:center" value="<%=DateFormater.prepStringFromDbDateToInputFormFormat(tgl_kuliah_end) %>"/>
   	   			<%		
   				}
   				else {
   				%>
   	   			<input type="text" name="tgl_kuliah_end"style="width:98%;text-align:center" placeholder="harap diisi"/>
   	   			<%
   				}
   				
   			}
   			else {
   				out.print(DateFormater.prepStringFromDbDateToInputFormFormat(tgl_kuliah_end));
   			}
   			%>
   			</td>
		</tr>
		
		
		<tr>
			<td style="background:#369;color:#fff;text-align:center;" colspan="2">PENGISIAN KRS</td>
		</tr>	
		<tr>
			<td style="background:#369;color:#fff;text-align:left;width:50%">THSMS KRS</td>
			<td valign="top" style="text-align:center;width:50%">
			<%
   			if(editable) {
   				if(thsms_krs!=null && !Checker.isStringNullOrEmpty(thsms_krs)) {
   				%>
   	   			<input type="text" name="thsms_krs" style="width:98%;text-align:center" value="<%=thsms_krs%>"/>
   	   			<%		
   				}
   				else {
   				%>
   	   			<input type="text" name="thsms_krs"style="width:98%;text-align:center" placeholder="wajib diisi"/>
   	   			<%
   				}
   				
   			}
   			else {
   				out.print(thsms_krs);
   			}
   			%>
   			</td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:left;width:50%">TGL AWAL PENGAJUAN</td>
   			<td valign="top" style="text-align:center;width:50%">
   			<%
   			if(editable) {
   				if(tgl_start_krs!=null && !Checker.isStringNullOrEmpty(tgl_start_krs)) {
   				%>
   	   			<input type="text" name="tgl_sta_krs" style="width:98%;text-align:center" value="<%=DateFormater.prepStringFromDbDateToInputFormFormat(tgl_start_krs) %>"/>
   	   			<%		
   				}
   				else {
   				%>
   	   			<input type="text" name="tgl_sta_krs"style="width:98%;text-align:center" placeholder="wajib diisi"/>
   	   			<%
   				}
   				
   			}
   			else {
   				out.print(DateFormater.prepStringFromDbDateToInputFormFormat(tgl_start_krs));
   			}
   			%>
   			</td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:left;width:50%">TGL AKHIR PENGAJUAN</td>
   			<td valign="top" style="text-align:center;width:50%">
   			<%
   			if(editable) {
   				if(tgl_end_krs!=null && !Checker.isStringNullOrEmpty(tgl_end_krs)) {
   				%>
   	   			<input type="text" name="tgl_end_krs" style="width:98%;text-align:center" value="<%=DateFormater.prepStringFromDbDateToInputFormFormat(tgl_end_krs) %>"/>
   	   			<%		
   				}
   				else {
   				%>
   	   			<input type="text" name="tgl_end_krs"style="width:98%;text-align:center" placeholder="wajib diisi"/>
   	   			<%
   				}
   				
   			}
   			else {
   				out.print(DateFormater.prepStringFromDbDateToInputFormFormat(tgl_end_krs));
   			}
   			%>
   			</td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:left;width:50%">WAJIB DAFTAR ULANG</td>
			<td valign="top" style="text-align:center;width:50%">
			<%

   			value = false;
   			if(heregistrasi_for_ins_krs.equalsIgnoreCase("1")) {
   				value = true;
   			}
   			if(editable) {
   				%>
   	   			<input type="text" name="heregistrasi_for_ins_krs" style="width:98%;text-align:center" value="<%=value%>"/>
   	   			<%		
   			}
   			else {
   				out.print(value);
   			}
   			%>
   			</td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:left;width:50%">MHS INPUT KRS</td>
			<td valign="top" style="text-align:center;width:50%">
			<%

   			value = false;
   			if(mhs_allow_input_krs.equalsIgnoreCase("1")) {
   				value = true;
   			}
   			if(editable) {
   				%>
   	   			<input type="text" name="mhs_allow_input_krs" style="width:98%;text-align:center" value="<%=value%>"/>
   	   			<%		
   			}
   			else {
   				out.print(value);
   			}
   			%>
   			</td>
		</tr>
		
		<tr>
			<td style="background:#369;color:#fff;text-align:left;width:50%">OPERATOR INPUT KRS</td>
			<td valign="top" style="text-align:center;width:50%">
			<%

   			value = false;
   			if(opr_allow_ins_krs.equalsIgnoreCase("1")) {
   				value = true;
   			}
   			if(editable) {
   				%>
   	   			<input type="text" name="opr_allow_ins_krs" style="width:98%;text-align:center" value="<%=value%>"/>
   	   			<%		
   			}
   			else {
   				out.print(value);
   			}
   			%>
   			</td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:center;" colspan="4">
			MASALAH MHS INPUT KRS BEHUBUNGAN DENGAN PILIH_KELAS_RULES TABEL
			</td>
		</tr>
	
		</table>
	</td>
	<td style="width:400px;" valign="top">
	<!-- sisi kanan -->
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:400px">
		<tr>
			<td style="background:#369;color:#fff;text-align:left;width:50%">LIST TGL PMB PER GEL</td>
   			<td valign="top" style="text-align:center;width:50%">
   			<%
   			if(editable) {
   				if(list_tgl_pmb_per_gel!=null && !Checker.isStringNullOrEmpty(list_tgl_pmb_per_gel)) {
   				%>
   				<textarea name="list_tgl_pmb_per_gel" rows="3" style="width: 98%"><%=list_tgl_pmb_per_gel %></textarea>
   	   			<%
   				}
   				else {
   				%>
   	   			<textarea name="list_tgl_pmb_per_gel" rows="3" style="width: 98%"></textarea>
   	   			<%
   				}
   			}
   			else {
   				out.print(list_tgl_pmb_per_gel);
   			}
   			%>
   			</td>
		</tr>
		
		<tr>
			<td style="background:#369;color:#fff;text-align:left;width:50%">LIST TGL UJIAN PENYARINGAN PER GEL</td>
   			<td valign="top" style="text-align:center;width:50%">
   			<%
   			if(editable) {
   				if(list_tgl_saring_per_gel!=null && !Checker.isStringNullOrEmpty(list_tgl_saring_per_gel)) {
   				%>
   				<textarea name="list_tgl_saring_per_gel" rows="3" style="width: 98%"><%=list_tgl_pmb_per_gel %></textarea>
   	   			<%
   				}
   				else {
   				%>
   	   			<textarea name="list_tgl_saring_per_gel" rows="3" style="width: 98%"></textarea>
   	   			<%
   				}
   			}
   			else {
   				out.print(list_tgl_saring_per_gel);
   			}
   			%>
   			</td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:left;width:50%">LIST TGL DAFTAR ULANG PER GEL</td>
   			<td valign="top" style="text-align:center;width:50%">
   			<%
   			if(editable) {
   				if(list_tgl_hereg_per_gel!=null && !Checker.isStringNullOrEmpty(list_tgl_hereg_per_gel)) {
   				%>
   				<textarea name="list_tgl_hereg_per_gel" rows="3" style="width: 98%"><%=list_tgl_hereg_per_gel %></textarea>
   	   			<%
   				}
   				else {
   				%>
   	   			<textarea name="list_tgl_hereg_per_gel" rows="3" style="width: 98%"></textarea>
   	   			<%
   				}
   			}
   			else {
   				out.print(list_tgl_hereg_per_gel);
   			}
   			%>
   			</td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:center;" colspan="2">INPUT NILAI</td>
		</tr>	
		<tr>
			<td style="background:#369;color:#fff;text-align:left;width:200px">THSMS INPUT NILAI</td>
   			<td valign="top" style="text-align:center;width:200px">
   			<%
   			if(editable) {
   				if(thsms_nilai!=null && !Checker.isStringNullOrEmpty(thsms_nilai)) {
   				%>
   	   			<input type="text" name="thsms_nilai" style="width:98%;text-align:center" value="<%=thsms_nilai%>"/>
   	   			<%		
   				}
   				else {
   				%>
   	   			<input type="text" name="thsms_nilai"style="width:98%;text-align:center" placeholder="wajib diisi"/>
   	   			<%
   				}
   				
   			}
   			else {
   				out.print(thsms_nilai);
   			}
   			%>
   			</td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:left;width:200px">OPERATOR BOLEH EDIT NILAI???</td>
   			<td valign="top" style="text-align:center;width:200px">
   			<%
   			value = false;
   			if(opr_allow_edit_nilai.equalsIgnoreCase("1")) {
   				value = true;
   			}
   			if(editable) {
   				%>
   	   			<input type="text" name="oper_allow_edit_nilai" style="width:98%;text-align:center" value="<%=value%>"/>
   	   			<%		
   			}
   			else {
   				out.print(value);
   			}
   			%>
   			</td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:left;width:200px">BOLEH EDIT NILAI SELURUH THSM???</td>
   			<td valign="top" style="text-align:center;width:200px">
   			
   			<%
   			value = false;
   			if(allow_edit_nilai_all_thsms.equalsIgnoreCase("1")) {
   				value = true;
   			}
   			if(editable) {
   				%>
   	   			<input type="text" name="allow_edit_nilai_all_thsms" style="width:98%;text-align:center" value="<%=value%>"/>
   	   			<%		
   			}
   			else {
   				out.print(value);
   			}
   			%>
   			</td>
		</tr>
		
		<tr>
			<td style="background:#369;color:#fff;text-align:center;" colspan="2">RANGE THSMS PADA FORM </td>
			<%
			System.out.println("range_thsms1="+range_thsms);
			if(range_thsms!=null && !Checker.isStringNullOrEmpty(range_thsms)) {
				StringTokenizer st = new StringTokenizer(range_thsms,",");
				range_thsms_sta = st.nextToken(); 
				range_thsms_fokus = st.nextToken();
				range_thsms_end= st.nextToken();
			}
			%>
		</tr>	
		<tr>
			<td style="background:#369;color:#fff;text-align:left;width:50%">THSMS AWAL</td>
   			<td valign="top" style="text-align:center;width:50%">
   			<%
   			if(editable) {
   				if(range_thsms_sta!=null && !Checker.isStringNullOrEmpty(range_thsms_sta)) {
   				%>
   	   			<input type="text" name="range_thsms_sta" style="width:98%;text-align:center" value="<%=range_thsms_sta %>"/>
   	   			<%		
   				}
   				else {
   				%>
   	   			<input type="text" name="range_thsms_sta"style="width:98%;text-align:center" placeholder="wajib"/>
   	   			<%
   				}
   				
   			}
   			else {
   				out.print(range_thsms_sta);
   			}
   			%>
   			
   			</td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:left;width:50%">THSMS DEFAULT</td>
   			<td valign="top" style="text-align:center;width:50%">
   			<%
   			if(editable) {
   				if(range_thsms_fokus!=null && !Checker.isStringNullOrEmpty(range_thsms_fokus)) {
   				%>
   	   			<input type="text" name="range_thsms_fokus" style="width:98%;text-align:center" value="<%=range_thsms_fokus %>"/>
   	   			<%		
   				}
   				else {
   				%>
   	   			<input type="text" name="range_thsms_fokus"style="width:98%;text-align:center" placeholder="wajib"/>
   	   			<%
   				}
   				
   			}
   			else {
   				out.print(range_thsms_fokus);
   			}
   			%>
   			
   			</td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:left;width:50%">THSMS AKHIR</td>
   			<td valign="top" style="text-align:center;width:50%">
   			<%
   			if(editable) {
   				if(range_thsms_end!=null && !Checker.isStringNullOrEmpty(range_thsms_end)) {
   				%>
   	   			<input type="text" name="range_thsms_end" style="width:98%;text-align:center" value="<%=range_thsms_end %>"/>
   	   			<%		
   				}
   				else {
   				%>
   	   			<input type="text" name="range_thsms_end"style="width:98%;text-align:center" placeholder="wajib"/>
   	   			<%
   				}
   				
   			}
   			else {
   				out.print(range_thsms_end);
   			}
   			%>
   			
   			</td>
		</tr>
		</table>
	</td>
    </tr>
    <%
	if(editable) {
%>
	<tr>
		<td style="background:#369;color:#fff;text-align:center;height:35px" colspan="4">
			<input type="submit" value="UPDATE" style="width:70%;height:30px;cellpadding:5px" />
		</td>
	</tr>	
<%	
	}
%>			
</table>
<%
if(editable) {
%>    	
</form>   		
<%
}
%>
 
		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>