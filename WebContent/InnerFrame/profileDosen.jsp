<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>	
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %> 
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	Vector v = null;
	
%>
<style type="text/css">
img.imgInsetShadowGray { padding:10px; -moz-box-shadow:inset 0 0 10px #000000; -webkit-box-shadow:inset 0 0 10px #000000; box-shadow:inset 0 0 10px #000000; }
</style>

</head>
<body>
<div id="header">
<!--   include file="innerMenu.jsp" % -->
<%@ include file="profileInnerMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
				<br/>
		<%
String kdpstDs=null,npmds=null,nodos=null,gelar_dpn=null,gelar_end=null,nidnn=null,tipe_id=null,no_id=null,status=null;
String	pt_s1=null,jur_s1=null,kdpst_s1=null,bidil_s1=null,noija_s1=null,tglls_s1=null,file_ija_s1=null,judul_s1=null,gelar_s1=null;
String	pt_s2=null,jur_s2=null,kdpst_s2=null,bidil_s2=null,noija_s2=null,tglls_s2=null,file_ija_s2=null,judul_s2=null,gelar_s2=null;
String	pt_s3=null,jur_s3=null,kdpst_s3=null,bidil_s3=null,noija_s3=null,tglls_s3=null,file_ija_s3=null,judul_s3=null,gelar_s3=null;
String	pt_gb=null,jur_gb=null,kdpst_gb=null,bidil_gb=null,noija_gb=null,tglls_gb=null,file_ija_gb=null,judul_gb=null,gelar_gb=null;
String total_kum=null,jabatan_akademik=null,jabatan_akademik_loco=null,jabatan_struk=null,tipe_ikatan_kerja=null;
String tgl_kerja=null,tgl_keluar=null,serdos=null,kdpti_base=null,kdpst_base=null,email_loco=null,pangkat_gol=null,riwayat=null;
String tipe_ktp=null,no_ktp=null,kdjek=null;
		
		JSONArray jsoaDsn = (JSONArray)session.getAttribute("jsoaDsn");
		JSONArray jsoaPst = (JSONArray)session.getAttribute("jsoaPst");
		if(jsoaDsn!=null && jsoaDsn.length()>0) {
			JSONObject jobDsn = jsoaDsn.getJSONObject(0);
			try {
				kdpstDs = jobDsn.getString("KDPST");
			}
			catch(JSONException je) {
				////je.printStackTrace();
			}
			try {
				kdjek = jobDsn.getString("KDJEKMSMHS");
			}
			catch(JSONException je) {
				////je.printStackTrace();
			}
			try {
				npmds = jobDsn.getString("NPMHS");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				nodos = jobDsn.getString("NODOS_LOCAL");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				gelar_dpn = jobDsn.getString("GELAR_DEPAN");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {	
				gelar_end = jobDsn.getString("GELAR_BELAKANG");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {	
				nidnn = jobDsn.getString("NIDNN");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {	
				tipe_id = jobDsn.getString("TIPE_ID");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {	
				no_id = jobDsn.getString("NOMOR_ID");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {	
				status = jobDsn.getString("STATUS");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {	
				pt_s1 = jobDsn.getString("PT_S1");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {	
				jur_s1 = jobDsn.getString("JURUSAN_S1");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				kdpst_s1 = jobDsn.getString("KDPST_S1");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				gelar_s1 = jobDsn.getString("GELAR_S1");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {	
				bidil_s1 = jobDsn.getString("TKN_BIDANG_KEAHLIAN_S1");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {	
				noija_s1 = jobDsn.getString("NOIJA_S1");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {	
				tglls_s1 = jobDsn.getString("TGLLS_S1");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}

			try {	
				file_ija_s1 = jobDsn.getString("FILE_IJA_S1");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {	
				judul_s1 = jobDsn.getString("JUDUL_TA_S1");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {	
				pt_s2 = jobDsn.getString("PT_S2");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {	
				jur_s2 = jobDsn.getString("JURUSAN_S2");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {	
				kdpst_s2 = jobDsn.getString("KDPST_S2");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				gelar_s2 = jobDsn.getString("GELAR_S2");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {	
				bidil_s2 = jobDsn.getString("TKN_BIDANG_KEAHLIAN_S2");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {	
				noija_s2 = jobDsn.getString("NOIJA_S2");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {	
				tglls_s2 = jobDsn.getString("TGLLS_S2");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {	
				file_ija_s2 = jobDsn.getString("FILE_IJA_S2");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {	
				judul_s2 = jobDsn.getString("JUDUL_TA_S2");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {	
				pt_s3 = jobDsn.getString("PT_S3");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				jur_s3 = jobDsn.getString("JURUSAN_S3");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				kdpst_s3 = jobDsn.getString("KDPST_S3");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				gelar_s3 = jobDsn.getString("GELAR_S3");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				bidil_s3 = jobDsn.getString("TKN_BIDANG_KEAHLIAN_S3");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				noija_s3 = jobDsn.getString("NOIJA_S3");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {	
				tglls_s3 = jobDsn.getString("TGLLS_S3");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				file_ija_s3 = jobDsn.getString("FILE_IJA_S3");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				judul_s3 = jobDsn.getString("JUDUL_TA_S3");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				pt_gb = jobDsn.getString("PT_PROF");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				jur_gb = jobDsn.getString("JURUSAN_PROF");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				kdpst_gb = jobDsn.getString("KDPST_PROF");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				gelar_gb = jobDsn.getString("GELAR_PROF");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				bidil_gb = jobDsn.getString("TKN_BIDANG_KEAHLIAN_PROF");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				noija_gb = jobDsn.getString("NOIJA_PROF");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {	
				tglls_gb = jobDsn.getString("TGLLS_PROF");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				file_ija_gb = jobDsn.getString("FILE_IJA_PROF");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				judul_gb = jobDsn.getString("JUDUL_TA_PROF");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				total_kum = jobDsn.getString("TOTAL_KUM");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				jabatan_akademik = jobDsn.getString("JABATAN_AKADEMIK_DIKTI");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				jabatan_akademik_loco = jobDsn.getString("JABATAN_AKADEMIK_LOCAL");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				jabatan_struk = jobDsn.getString("JABATAN_STRUKTURAL");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				tipe_ikatan_kerja = jobDsn.getString("IKATAN_KERJA_DOSEN");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				tgl_kerja = jobDsn.getString("TANGGAL_MULAI_KERJA");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				tgl_keluar = jobDsn.getString("TANGGAL_KELUAR_KERJA");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				serdos = jobDsn.getString("SERDOS");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				kdpti_base = jobDsn.getString("KDPTI_HOMEBASE");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				kdpst_base = jobDsn.getString("KDPST_HOMEBASE");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				email_loco = jobDsn.getString("EMAIL_INSTITUSI");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				pangkat_gol = jobDsn.getString("PANGKAT_GOLONGAN");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				riwayat = jobDsn.getString("CATATAN_RIWAYAT");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				tipe_ktp = jobDsn.getString("TIPE_KTP");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			try {
				no_ktp = jobDsn.getString("NO_KTP");
			}
			catch(JSONException je) {
				//je.printStackTrace();
			}
			
			
		}
		System.out.println("done");
		/*
		
(`KDPST`,`NPMHS`,`NODOS_LOCAL`,`GELAR_DEPAN`,`GELAR_BELAKANG`,`NIDNN`,`TIPE_ID`,`NOMOR_ID`,`STATUS`,
`PT_S1`,`JURUSAN_S1`,`KDPST_S1`,`TKN_BIDANG_KEAHLIAN_S1`,`NOIJA_S1`,`FILE_IJA_S1`,`JUDUL_TA_S1`,
`PT_S2`,`JURUSAN_S2`,`KDPST_S2`,`TKN_BIDANG_KEAHLIAN_S2`,`NOIJA_S2`,`FILE_IJA_S2`,`JUDUL_TA_S2`,
`PT_S3`,`JURUSAN_S3`,`KDPST_S3`,`TKN_BIDANG_KEAHLIAN_S3`,`NOIJA_S3`,`FILE_IJA_S3`,`JUDUL_TA_S3`,
`PT_PROF`,`JURUSAN_PROF`,`KDPST_PROF`,`TKN_BIDANG_KEAHLIAN_PROF`,`NOIJA_PROF`,`FILE_IJA_PROF`,`JUDUL_TA_PROF`
`TOTAL_KUM`,`JABATAN_AKADEMIK_DIKTI`,`JABATAN_AKADEMIK_LOCAL`,`JABATAN_STRUKTURAL`,`IKATAN_KERJA_DOSEN`,
`TANGGAL_MULAI_KERJA`,`TANGGAL_KELUAR_KERJA`,`SERDOS`,`KDPTI_HOMEBASE`,`KDPST_HOMEBASE`,`EMAIL_INSTITUSI`,
`PANGKAT_GOLONGAN`,`CATATAN_RIWAYAT`)

		*/

		if(allowInsDataDosen) {
		%>
		<form action="update.updDataDosen" method="post" >
			<input type="hidden" value="<%=v_kdpst %>" name="kdpst_dsn" />
			<input type="hidden" value="<%=v_npmhs %>" name="npm_dsn" />
			<input type="hidden" value="<%=v_obj_lvl %>" name="obj_lvl" />
			<input type="hidden" value="<%=v_id_obj %>" name="id_obj" />
			<input type="hidden" value="<%=v_nmmhs %>" name="nmm" />
		<%	
		}
		%>
		<p>
		<table align="center" border="1" style="background:#d9e1e5;color:#000;width:700px">	
				
			<tr>
				<td align="center" colspan="4" bgcolor="#369" style="color:#fff" padding-left="2px"><b>DATA DOSEN</b></td>
			</tr>
			<tr>	
				<td align="left" width="100px" style="padding-left:2px">Nama </td>
				<td align="center" width="250px">
				<%
				out.print(v_nmmhs);
				%>
				</td>
				<td align="left" width="100px" style="padding-left:2px">Status </td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
					String[]statOpt = Constants.getStatusDosen();
				%>
					<select name="statusDsn" style="width:98%">
						<option value="null">----Wajib diisi----</option>
				<%
						for(int i=0;i<statOpt.length;i++) {
							StringTokenizer st = new StringTokenizer(statOpt[i],"-");
							String kode = st.nextToken();
							String keter = st.nextToken();
							if(status!=null && status.equalsIgnoreCase(kode)) {
				%>
						<option value="<%= kode%>" selected="selected"><%=keter %></option>
				<%				
							}
							else {
				%>
						<option value="<%= kode%>"><%=keter %></option>
				<%			
							}
						}	
				%>	
					</select>
				<%	
				}
				else {
					out.print(status);
				}
				%>
				</td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">Gelar Depan</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
				%>
				<input type="text" name="frontTitle" style="width:98%;text-align:left" value="<%=Checker.pnn(gelar_dpn)%>">
				<%
				}
				else {
					out.print(Checker.pnn(gelar_dpn));
				}
				%>
				</td>
				<td align="left" width="100px" style="padding-left:2px">Gelar Belakang</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
				%>
				<input type="text" name="endTitle" style="width:98%;text-align:left" value="<%=Checker.pnn(gelar_end)%>">
				<%
				}
				else {
					out.print(Checker.pnn(gelar_end));
				}
				%>
				</td>
			</tr>
			<tr>	
				<td align="left" width="100px" style="padding-left:2px">Tanda Pengenal</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {	
					String[]listTandaPengenal = Constants.getListTandaPengenal();
		%>
					<select name="tipeKtp" style="width:98%">
		<%
					for(int i=0;i<listTandaPengenal.length;i++) {
						String id = listTandaPengenal[i];
						if(tipe_ktp!=null && tipe_ktp.equalsIgnoreCase(id)) {
		%>
						<option value="<%=id %>" selected="selected"><%=id %></option>
		<%					
						}
						else {
		%>
						<option value="<%=id %>"><%=id %></option>
		<%	
						}
					}
		%>
					</select>
		<%
				}
				else {
					out.print(Checker.pnn(tipe_ktp));
				}
		%>			
				</td>
				<td align="left" width="100px" style="padding-left:2px">No Tanda Pengenal</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {	
				%>	
					<input type="text" name="noKtp" style="width:98%" value="<%=Checker.pnn(no_ktp)%>"/>
				<%
				}
				else {
					out.print(Checker.pnn(no_ktp));
				}
				%>
				</td>
			</tr>
			<tr>	
				<td align="left" width="100px" style="padding-left:2px">Jenis Kelamin </td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {	
					String[]gender = Constants.getOptionGender();
		%>
					<select name="kdjek" style="width:98%">
		<%
					for(int i=0;i<gender.length;i++) {
						String baris = gender[i];
						StringTokenizer st = new StringTokenizer(baris);
						String val = st.nextToken();
						String ket = st.nextToken();
						if(kdjek!=null && kdjek.equalsIgnoreCase(val)) {
		%>
						<option value="<%=val %>" selected="selected"><%=ket %></option>
		<%	
						}
						else {
		%>
						<option value="<%=val %>"><%=ket %></option>
		<%	
						}
					}
		%>
					</select>
		<%
				}
				else {
					out.print("jk");
				}
		%>			
				</td>
				<td align="left" width="100px" style="padding-left:2px">No Nidnn </td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {	
				%>	
					<input type="text" name="noNidnn" style="width:98%" value="<%=Checker.pnn(nidnn)%>"/>
				<%
				}
				else {
					out.print(nidnn);
				}
				%>
				</td>
			</tr>	
			<tr>	
				<td align="left" width="100px" style="padding-left:2px">Tipe ID Dosen</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {	
					String[]tipeId = Constants.getListTipeNoIdDosen();
		%>
					<select name="tipeIdDsn" style="width:98%">
		<%
					for(int i=0;i<tipeId.length;i++) {
		%>
						<option value="<%=tipeId[i] %>"><%=tipeId[i] %></option>
		<%	
					}
		%>
					</select>
		<%
				}
				else {
					out.print(tipe_id);
				}
		%>			
				</td>
				<td align="left" width="100px" style="padding-left:2px">No ID Dosen</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {	
				%>	
					<input type="text" name="noIdDsn" style="width:98%" value="<%=Checker.pnn(no_id)%>"/>
				<%
				}
				else {
					out.print(no_id);
				}
				%>
				</td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">Tipe Dosen</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
					String[]tipeIkaOpt = Constants.getTipeIkatanKerjaDosen();
				%>
					<select name="tipeIkaDsn" style="width:98%">
						<option value="null">----Pilih----</option>
				<%
						for(int i=0;i<tipeIkaOpt.length;i++) {
							StringTokenizer st = new StringTokenizer(tipeIkaOpt[i],"-");
							String kode = st.nextToken();
							String keter = st.nextToken();
							if(tipe_ikatan_kerja!=null && tipe_ikatan_kerja.equalsIgnoreCase(kode)) {
				%>
						<option value="<%= kode%>" selected="selected"><%=keter %></option>
				<%				
							}
							else {
				%>
						<option value="<%= kode%>"><%=keter %></option>
				<%			
							}
						}	
				%>	
					</select>
				<%	
				}
				else {
					out.print(tipe_ikatan_kerja);
				}
				%>
				</td>
				<td align="left" width="100px" style="padding-left:2px">Jabatan Struktural</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
				%>
				<input type="text" name="jabStruk" style="width:98%;text-align:left" value="<%=Checker.pnn(jabatan_struk)%>">
				<%
				}
				else {
					out.print(Checker.pnn(jabatan_struk));
				}
				%>
				</td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">Homebase PT</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
					//String tmp = "";
					if(kdpti_base!=null && kdpti_base.equalsIgnoreCase(Constants.getKdpti())) {
						kdpti_base = ""+Constants.getNamaKdpti();
						//tmp = ""+Constants.getKdpti();
					}
				%>
				<input type="text" name="ptBase" style="width:98%;text-align:left" value="<%=Checker.pnn(kdpti_base)%>" placeholder="Diisi bila bukan dari Satyagama">
				<%
				}
				else {
					out.print(Checker.pnn(kdpti_base));
				}
				%>
				</td>
				<td align="left" width="100px" style="padding-left:2px">JJA Local</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
					String[]listJja = Constants.getListJenjangJabatanAkademik();
				%>
					<select name="JJA-local" style="width:98%">
						<option value="null">----Pilih----</option>
				<%
						for(int i=0;i<listJja.length;i++) {
							StringTokenizer st = new StringTokenizer(listJja[i],"-");
							String kode = st.nextToken();
							String keter = st.nextToken();
							if(jabatan_akademik_loco!=null && jabatan_akademik_loco.equalsIgnoreCase(kode)) {
				%>
						<option value="<%= kode%>" selected="selected"><%=keter %></option>
				<%				
							}
							else {
				%>
						<option value="<%= kode%>"><%=keter %></option>
				<%			
							}
						}	
				%>	
					</select>
				<%	
				}
				else {
					out.print(jabatan_akademik_loco);
				}
				%>
				</td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">Homebase Prodi</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
				%>
				<select name="baseProdi" style="width:98%">
					<option value="null">----Pilih----</option>
				<%
					for(int i=0;i<jsoaPst.length();i++) {
						JSONObject jobPst = jsoaPst.getJSONObject(i);
						String kdpro = jobPst.getString("KDPSTMSPST");
						String kdjen = jobPst.getString("KDJENMSPST");
						kdjen = Converter.getDetailKdjen(kdjen);
						String keter = jobPst.getString("NMPSTMSPST");
						if(kdpst_base!=null && kdpro.equalsIgnoreCase(kdpst_base)) {
				%>
					<option value="<%=kdpro%>" selected="selected"><%=keter %> (<%=kdjen %>)</option>
				<%	
						}
						else {
				%>
					<option value="<%=kdpro%>"><%=keter %> (<%=kdjen %>)</option>
				<%		
						}
					}
				}
				else {
					if(kdpst_base!=null && !Checker.isStringNullOrEmpty(kdpst_base)) {
						out.print(Converter.getNamaKdpst(kdpst_base));
					}
				}
				%>
				</td>
				<td align="left" width="100px" style="padding-left:2px">JJA</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
					String[]listJja = Constants.getListJenjangJabatanAkademik();
				%>
					<select name="JJA-dikti" style="width:98%">
						<option value="null">----Pilih----</option>
				<%
						for(int i=0;i<listJja.length;i++) {
							StringTokenizer st = new StringTokenizer(listJja[i],"-");
							String kode = st.nextToken();
							String keter = st.nextToken();
							if(jabatan_akademik!=null && jabatan_akademik.equalsIgnoreCase(kode)) {
				%>
						<option value="<%= kode%>" selected="selected"><%=keter %></option>
				<%				
							}
							else {
				%>
						<option value="<%= kode%>"><%=keter %></option>
				<%			
							}
						}	
				%>	
					</select>
					
				<%	
				}
				else {
					out.print(jabatan_akademik);
				}
				%>
				</td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">Pangkat/Golongan</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
				%>
				<input type="text" name="gol" style="width:98%;text-align:left" value="<%=Checker.pnn(pangkat_gol)%>">
				<%
				}
				else {
					out.print(Checker.pnn(pangkat_gol));
				}
				%>
				</td>
				<td align="left" width="100px" style="padding-left:2px">Total Kum</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
				%>
				<input type="text" name="kum" style="width:98%;text-align:left" value="<%=Checker.pnn(total_kum)%>">
				<%
				}
				else {
					out.print(Checker.pnn(total_kum));
				}
				%>
				</td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">Tanggal Masuk</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
					if(tgl_kerja!=null && !Checker.isStringNullOrEmpty(tgl_kerja)) {
						tgl_kerja = DateFormater.prepStringFromDbDateToInputFormFormat(tgl_kerja);
					}
				%>
				<input type="text" name="tglMsk" style="width:98%;text-align:left" value="<%=Checker.pnn(tgl_kerja)%>" placeholder="tgl/bln/tahun">
				<%
				}
				else {
					if(tgl_kerja!=null && !Checker.isStringNullOrEmpty(tgl_kerja)) {
						tgl_kerja = DateFormater.prepStringFromDbDateToInputFormFormat(tgl_kerja);
					}
					out.print(Checker.pnn(tgl_kerja));
				}
				%>
				</td>
				<td align="left" width="100px" style="padding-left:2px">Tanggal Keluar</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
					if(tgl_keluar!=null && !Checker.isStringNullOrEmpty(tgl_keluar)) {
						tgl_keluar = DateFormater.prepStringFromDbDateToInputFormFormat(tgl_keluar);
					}
				%>
				<input type="text" name="tglOut" style="width:98%;text-align:left" value="<%=Checker.pnn(tgl_keluar)%>" placeholder="tgl/bln/tahun">
				<%
				}
				else {
					if(tgl_keluar!=null && !Checker.isStringNullOrEmpty(tgl_keluar)) {
						tgl_keluar = DateFormater.prepStringFromDbDateToInputFormFormat(tgl_keluar);
					}
					out.print(Checker.pnn(tgl_keluar));
				}
				%>
				</td>
			</tr>
			<tr>	
				<td align="left" width="100px" style="padding-left:2px">No Serdos</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {	
				%>	
					<input type="text" name="serdos" style="width:98%" value="<%=Checker.pnn(serdos)%>"/>
				<%
				}
				else {
					out.print(Checker.pnn(serdos));
				}
				%>
				</td>
			</tr>
			<tr>
				<td align="center" width="100px" style="padding-left:2px;color:#fff" colspan="2" bgcolor="#369" ><b>DATA S-1</b></td><td align="center" width="100px" style="padding-left:2px;color:#fff" colspan="2" bgcolor="#369" ><b>DATA S-2</b></td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">Asal PT</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
				%>
				<input type="text" name="aspti1" style="width:98%;text-align:left" value="<%=Checker.pnn(pt_s1)%>">
				<%
				}
				else {
					out.print(Checker.pnn(pt_s1));
				}
				%>
				</td>
				<td align="left" width="100px" style="padding-left:2px">Asal PT</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
				%>
				<input type="text" name="aspti2" style="width:98%;text-align:left" value="<%=Checker.pnn(pt_s2)%>">
				<%
				}
				else {
					out.print(Checker.pnn(pt_s2));
				}
				%>
				</td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">Gelar</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
				%>
				<input type="text" name="gelar1" style="width:98%;text-align:left" value="<%=Checker.pnn(gelar_s1)%>">
				<%
				}
				else {
					out.print(Checker.pnn(gelar_s1));
				}
				%>
				</td>
				<td align="left" width="100px" style="padding-left:2px">Gelar</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
				%>
				<input type="text" name="gelar2" style="width:98%;text-align:left" value="<%=Checker.pnn(gelar_s2)%>">
				<%
				}
				else {
					out.print(Checker.pnn(gelar_s2));
				}
				%>
				</td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">Prodi</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
				%>
				<input type="text" name="kdpst1" style="width:98%;text-align:left" value="<%=Checker.pnn(kdpst_s1)%>">
				<%
				}
				else {
					out.print(Checker.pnn(kdpst_s1));
				}
				%>
				</td>
				<td align="left" width="100px" style="padding-left:2px">Prodi</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
				%>
				<input type="text" name="kdpst2" style="width:98%;text-align:left" value="<%=Checker.pnn(kdpst_s2)%>">
				<%
				}
				else {
					out.print(Checker.pnn(gelar_s2));
				}
				%>
				</td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">Bidang Ilmu</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
				%>
				<input type="text" name="bidil1" style="width:98%;text-align:left" value="<%=Checker.pnn(bidil_s1)%>">
				<%
				}
				else {
					out.print(Checker.pnn(bidil_s1));
				}
				%>
				</td>
				<td align="left" width="100px" style="padding-left:2px">Bidang Ilmu</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
				%>
				<input type="text" name="bidil2" style="width:98%;text-align:left" value="<%=Checker.pnn(bidil_s2)%>">
				<%
				}
				else {
					out.print(Checker.pnn(bidil_s2));
				}
				%>
				</td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">Tgl Lulus</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
					if(tglls_s1!=null && !Checker.isStringNullOrEmpty(tglls_s1)) {
						tglls_s1 = DateFormater.prepStringFromDbDateToInputFormFormat(tglls_s1);
					}
				%>
				<input type="text" name="tglls1" style="width:98%;text-align:left" value="<%=Checker.pnn(tglls_s1)%>" placeholder="tgl/bln/tahun">
				<%
				}
				else {
					if(tglls_s1!=null && !Checker.isStringNullOrEmpty(tglls_s1)) {
						tglls_s1 = DateFormater.prepStringFromDbDateToInputFormFormat(tglls_s1);
						out.print(Converter.formatDdSlashMmSlashYy(tglls_s1));
					}
					
				}
				%>
				</td>
				<td align="left" width="100px" style="padding-left:2px">Tgl Lulus</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
					if(tglls_s2!=null && !Checker.isStringNullOrEmpty(tglls_s2)) {
						tglls_s2 = DateFormater.prepStringFromDbDateToInputFormFormat(tglls_s2);
					}
				%>
				<input type="text" name="tglls2" style="width:98%;text-align:left" value="<%=Checker.pnn(tglls_s2)%>" placeholder="tgl/bln/tahun">
				<%
				}
				else {
					if(tglls_s2!=null && !Checker.isStringNullOrEmpty(tglls_s2)) {
						tglls_s2 = DateFormater.prepStringFromDbDateToInputFormFormat(tglls_s2);
						out.print(Converter.formatDdSlashMmSlashYy(tglls_s2));
					}
					
				}
				 %>
				</td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">No Ijazah</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
				%>
				<input type="text" name="noija1" style="width:98%;text-align:left" value="<%=Checker.pnn(noija_s1)%>">
				<%
				}
				else {
					out.print(Checker.pnn(noija_s1));
				}
				%>
				</td>
				<td align="left" width="100px" style="padding-left:2px">No Ijazah</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
				%>
				<input type="text" name="noija2" style="width:98%;text-align:left" value="<%=Checker.pnn(noija_s2)%>">
				<%
				}
				else {
					out.print(Checker.pnn(noija_s1));
				}
				%>
				</td>
			</tr>
			<tr>
				<td align="center" width="100px" style="padding-left:2px;color:#fff" colspan="2" bgcolor="#369" ><b>DATA S-3</b></td><td align="center" width="100px" style="padding-left:2px;color:#fff" colspan="2" bgcolor="#369" ><b>DATA GURU BESAR</b></td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">Asal PT</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
				%>
				<input type="text" name="aspti3" style="width:98%;text-align:left" value="<%=Checker.pnn(pt_s3)%>">
				<%
				}
				else {
					out.print(Checker.pnn(pt_s3));
				}
				%>
				</td>
				<td align="left" width="100px" style="padding-left:2px">Asal PT</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
				%>
				<input type="text" name="aspti4" style="width:98%;text-align:left" value="<%=Checker.pnn(pt_gb)%>">
				<%
				}
				else {
					out.print(Checker.pnn(pt_gb));
				}
				%>
				</td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">Gelar</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
				%>
				<input type="text" name="gelar3" style="width:98%;text-align:left" value="<%=Checker.pnn(gelar_s3)%>">
				<%
				}
				else {
					out.print(Checker.pnn(gelar_s3));
				}
				%>
				</td>
				<td align="left" width="100px" style="padding-left:2px">Gelar</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
				%>
				<input type="text" name="gelar4" style="width:98%;text-align:left" value="<%=Checker.pnn(gelar_gb)%>">
				<%
				}
				else {
					out.print(Checker.pnn(gelar_gb));
				}
				%>
				</td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">Prodi</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
				%>
				<input type="text" name="kdpst3" style="width:98%;text-align:left" value="<%=Checker.pnn(kdpst_s3)%>">
				<%
				}
				else {
					out.print(Checker.pnn(kdpst_s3));
				}
				%>
				</td>
				<td align="left" width="100px" style="padding-left:2px">Prodi</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
				%>
				<input type="text" name="kdpst4" style="width:98%;text-align:left" value="<%=Checker.pnn(kdpst_gb)%>">
				<%
				}
				else {
					out.print(Checker.pnn(kdpst_gb));
				}
				%>
				</td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">Bidang Ilmu</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
				%>
				<input type="text" name="bidil3" style="width:98%;text-align:left" value="<%=Checker.pnn(bidil_s3)%>">
				<%
				}
				else {
					out.print(Checker.pnn(bidil_s3));
				}
				%>
				</td>
				<td align="left" width="100px" style="padding-left:2px">Bidang Ilmu</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
				%>
				<input type="text" name="bidil4" style="width:98%;text-align:left" value="<%=Checker.pnn(bidil_gb)%>">
				<%
				}
				else {
					out.print(Checker.pnn(bidil_gb));
				}
				%>
				</td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">Tgl Lulus</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
					if(tglls_s3!=null && !Checker.isStringNullOrEmpty(tglls_s3)) {
						tglls_s3 = DateFormater.prepStringFromDbDateToInputFormFormat(tglls_s3);
					}
				%>
				<input type="text" name="tglls3" style="width:98%;text-align:left" value="<%=Checker.pnn(tglls_s3)%>" placeholder="tgl/bln/tahun">
				<%
				}
				else {
					if(tglls_s3!=null && !Checker.isStringNullOrEmpty(tglls_s3)) {
						tglls_s3 = DateFormater.prepStringFromDbDateToInputFormFormat(tglls_s3);
						out.print(Converter.formatDdSlashMmSlashYy(tglls_s3));
					}
					
				}
				 %>
				</td>
				<td align="left" width="100px" style="padding-left:2px">Tgl Lulus</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
					if(tglls_gb!=null && !Checker.isStringNullOrEmpty(tglls_gb)) {
						tglls_gb = DateFormater.prepStringFromDbDateToInputFormFormat(tglls_gb);
					}
				%>
				<input type="text" name="tglls4" style="width:98%;text-align:left" value="<%=Checker.pnn(tglls_gb)%>" placeholder="tgl/bln/tahun">
				<%
				}
				else {
					if(tglls_gb!=null && !Checker.isStringNullOrEmpty(tglls_gb)) {
						tglls_gb = DateFormater.prepStringFromDbDateToInputFormFormat(tglls_gb);
						out.print(Converter.formatDdSlashMmSlashYy(tglls_gb));
					}
					
				}
				 %>
				</td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">No Ijazah</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
				%>
				<input type="text" name="noija3" style="width:98%;text-align:left" value="<%=Checker.pnn(noija_s3)%>">
				<%
				}
				else {
					out.print(Checker.pnn(noija_s3));
				}
				%>
				</td>
				<td align="left" width="100px" style="padding-left:2px">No Ijazah</td>
				<td align="center" width="250px">
				<%
				if(allowInsDataDosen) {
				%>
				<input type="text" name="noija4" style="width:98%;text-align:left" value="<%=Checker.pnn(noija_gb)%>">
				<%
				}
				else {
					out.print(Checker.pnn(noija_gb));
				}
				%>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="4" bgcolor="#369" style="color:#fff" padding-left="2px"><b>CATATAN RIWAYAT</b></td>
			</tr>
			<tr>	
				<td align="left" colspan="4" width="100px">
				<%
				if(allowInsDataDosen) {
				%>
				<textarea rows="4" style="width:99%;text-align:left" name="note" ><%=Converter.reversePrepForInputTextToDb(Checker.pnn(riwayat)) %></textarea>
				<%
				}
				else {
					out.print(riwayat);
				}
				%> 
				</td>
			</tr>
			<%
				if(allowInsDataDosen) {
			%>
				<td align="center" colspan="4" bgcolor="#369" style="color:#fff" padding-left="2px">
					<input type="submit" value="Update Data Dosen" style="width:250px;padding:3px;height:30px" />
				</td>
			<%
			}
			%>
		</table>
		</p>
		<%
				if(allowInsDataDosen) {
		%>
		</form>
		<%
				}
		%>
		<br/>
</body>
</html>