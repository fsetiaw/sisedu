<!DOCTYPE html >
<html>
<head>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>
<%@ page import="java.util.Collections" %>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<!--  link rel="stylesheet" type="text/css" href="/ToUnivSatyagama/forms/simplePmb.css" media="screen" / -->
<%
	//System.out.println("pmb1");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	ListIterator li=null;
	String stpid_feedback = request.getParameter("stpid");
	String infodos_feedback = request.getParameter("namadsn");
	String nmdos = null;
	String nodos = null;
	if(infodos_feedback!=null&&!Checker.isStringNullOrEmpty(infodos_feedback)) {
		StringTokenizer st = new StringTokenizer(infodos_feedback,"__");
		nodos = st.nextToken();
		nmdos = st.nextToken();
	}
	String tknInfoUjian  = (String)request.getAttribute("tknInfoUjian");
	String kdpstObjToBeInput = null;
	//System.out.println("stpid_feedback="+stpid_feedback);
	//System.out.println("tknInfoUjian="+tknInfoUjian);
%>
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"> </script>
<script type="text/javascript">
$(document).ready(function()
{
	$("#somebutton").click(function()	
	{
        $.post( 'go.validateForm', $('#formUpload1').serialize(), function(data) {
        	document.getElementById('div_msg').style.visibility='visible';
        	$('#div_msg').html(); 
        	$('#div_msg').html(data); 
        });
	});
});	
</script>		
</head>
<!--  body onload="location.href='#'" -->
<body>
<div id="header">
<%@ include file="innerMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
	<br />
<%

int norut = validUsr.isAllowTo("iciv");
String kdjen = null;
Vector v_list_shift = new Vector();
if(norut>0) {
	String obj_id = request.getParameter("objid_kdpst");
%>

<%
if(obj_id!=null && obj_id.equalsIgnoreCase("70-00011") && validUsr.getNpm().equalsIgnoreCase("0000812100003")) {
%>

<form name="formUpload1" id="formUpload1">
	
<%	
}
else {
%>
<form action="simple.insertCivitasSimple" target="_self" METHOD="GET">
<%
}
%>

<table align="center"  style="background:#d9e1e5;color:#000;width:800px;border:0px">	
	<tr>
		<td colspan="3" style="font-size:2em;font-weight:bold;text-align:center;background:#369;color:#fff">FORM INPUT DATA CIVITAS BARU</td>
	</tr>
			
			
<!--  table align="center" border="1" style="background:#d9e1e5;color:#fff;width:90%" -->	
	<tr>
		<td style="width:32%;text-align:left"><label>Kategori Civitas</label></td>
		<td style="width:3%;text-align:center">:</td>
		<td style="width:65%;text-align:left">
			
			<%
			//Vector v = validUsr.listObjekYgBolehDiInsertBerdasarHakAksesUser(norut);
			//System.out.println("pmb2");
			Vector v = validUsr.getScopeUpd7des2012("iciv");
			
			//System.out.println("pmb3");
			if(v!=null && v.size()>0) {
				li = v.listIterator();
				if(li.hasNext()) {
					//dibutuhkan sehubungan sistim verificasi baru - untuk saat ini dibutuhkan hanya untuk input dosen oleh dayat
					if(obj_id!=null && obj_id.equalsIgnoreCase("70-00011") && validUsr.getNpm().equalsIgnoreCase("0000812100003")) {
						%>
					<select name="objid-kdpst_Huruf_Opt" onchange='this.form.submit()' style="width:95%">
						<%	
					}
					else {
						//old style
					%>
					<select name="objid_kdpst" onchange='this.form.submit()' style="width:95%">
					<%
					}
					%>
						<option value="null">-PILIH-</option>
					<%
					while(li.hasNext()) {
						String baris = (String)li.next();
						StringTokenizer st = new StringTokenizer(baris);
						String id_obj = st.nextToken();
						String kdpst = st.nextToken();
						String obj_dsc = st.nextToken();
						String obj_level = st.nextToken();
						if(obj_id!=null && obj_id.equalsIgnoreCase(id_obj+"-"+kdpst)) {
							String nmpst_kdjen = Converter.getDetailKdpst(kdpst);
							st = new StringTokenizer(nmpst_kdjen,"#&");
							st.nextToken();
							kdjen = st.nextToken();
							v_list_shift = Converter.getPilihanShiftYgAktif(kdjen);
							kdpstObjToBeInput = ""+kdpst;
							%>
							<option value="<%=id_obj %>-<%=kdpst %>" selected><%=obj_dsc.replaceAll("_", " ") %></option>
							<%	
						}
						else {
							%>
							<option value="<%=id_obj %>-<%=kdpst %>"><%=obj_dsc.replaceAll("_", " ") %></option>
							<%	
						}
					}
					%>
					</select>
					<%
				}
				else {
					out.print("no akses");
				}
			}
			%>
		</td>
	</tr>
	
	<%
	//===================start pemilahan -spt untuk dosen ===========================
	
	if(obj_id!=null && (kdpstObjToBeInput!=null && !kdpstObjToBeInput.contains(Constants.getKdpstDosen()))) {
	%>
	<tr>
		<td><label>Civitas Baru/Pindahan</label></td>
		<td style="width:3%;text-align:center">:</td>
		<td><select name="stpid" style="width:95%" onchange='this.form.submit()'>
			<option value="null">-PILIH-</option>
		<%
		String []opt=Constants.getTipeCivitas();
		for(int i=0;i<opt.length;i++) {
			String tmp = opt[i];
			StringTokenizer st = new StringTokenizer(tmp);
			String val = st.nextToken();
			String ket = st.nextToken();
			if(stpid_feedback==null || (stpid_feedback!=null && !val.equalsIgnoreCase(stpid_feedback))) {
		%>
			<option value="<%=val %>"><%=ket %></option>
		<%	
			}
			else {
				if(stpid_feedback!=null && val.equalsIgnoreCase(stpid_feedback)) {
		%>
			<option value="<%=val %>" selected><%=ket %></option>
		<%			
				}
			}
		}
		%></select></td>
	</tr>	
	<%
	}
	if(obj_id!=null && stpid_feedback!=null && !Checker.isStringNullOrEmpty(stpid_feedback)) {
		if(stpid_feedback!=null && stpid_feedback.equalsIgnoreCase("P")) { //proses mhs pindahabn
	%>
	<tr>
		<td style="text-align:left"><font style="italic">Asal Perguruan Tinggi </font></td>
		<td style="width:3%;text-align:center">:</td>
		<td><input type="text" style="width:93%" name="aspti" value="UNIVERSITAS SATYAGAMA" /></td>
	</tr>
	<%
		}
	%>
	<tr>
		<td><label>Angkatan</label></td>
		<td style="width:3%;text-align:center">:</td>
		<td>
			<select name="smawl" style="width:95%">
			<%
			String smawl = Checker.getThsmsPmb();
			String thsms = ""+smawl;
			v = new Vector();
			li = v.listIterator();
			li.add(thsms);
			for(int i=0;i<30;i++) {
				thsms = Tool.returnPrevThsmsGiven(thsms);
				li.add(thsms);
			}
			Collections.sort(v);
			li = v.listIterator();
			while(li.hasNext()) {
				thsms = (String)li.next();
				//String keter_thsms = "N/A";
				//String value_thsms = "N/A";
				String keter_thsms_and_value = Converter.convertThsms(thsms);
				StringTokenizer stt = new StringTokenizer(keter_thsms_and_value,"#&");
				String keter_thsms = stt.nextToken();
				String value_thsms = stt.nextToken();
				if(Tool.isThsmsEqualsSmawl(thsms,smawl)) {
					
				%>
					<option value="<%=value_thsms%>" selected><%=keter_thsms %></option>
				<%
				}
				else {
				%>
					<option value="<%=value_thsms%>"><%=keter_thsms %></option>
				<%	
				}
			}
			%>
			</select>
		</td>
	</tr>
	<tr>
		<td><label>Shift Kelas</label></td>
		<td style="width:3%;text-align:center">:</td>
		<td>
			<select name="shiftKls" style="width:95%">
			<%
			if(v_list_shift!=null) {
				ListIterator li1 = v_list_shift.listIterator();
				if(v_list_shift.size()>2) {
					while(li1.hasNext()) {
						String brs = (String)li1.next();
						StringTokenizer st= new StringTokenizer(brs,"#&");
						String keterangan = st.nextToken(); 
						keterangan = keterangan.toUpperCase();
						String shift = st.nextToken();
						String hari = st.nextToken();
						String konversi_kod = st.nextToken();
						if(keterangan.equalsIgnoreCase("N/A")) {
					%>
						<option value="<%=keterangan%>" selected>--Pilih Shift Kelas--</option>
					<%
						}
						else {
					%>
						<option value="<%=keterangan%>"><%=konversi_kod%></option>
					<%
						}
					}	
				}
				else {
					//v size cuma 1 opt = v_size()->2 -->1N/A and the other
					while(li1.hasNext()) {
						String brs = (String)li1.next();
						StringTokenizer st= new StringTokenizer(brs,"#&");
						String keterangan = st.nextToken(); 
						keterangan = keterangan.toUpperCase();
						String shift = st.nextToken();
						String hari = st.nextToken();
						String konversi_kod = st.nextToken();
						if(keterangan.equalsIgnoreCase("N/A")) {
					%>
						<option value="<%=keterangan%>" selected>--N/A--</option>
					<%
						}
						else {
					%>
						<option value="<%=keterangan%>" selected><%=konversi_kod%></option>
					<%
						}
					}
				}
			}
			else {
				%>
				<option value="N/A" selected>--DEFAULT--</option>
				<%
			}
			%>
			</select>
		</td>
	</tr>
	<tr>
		<td><label>Nama</label></td>
		<td style="width:3%;text-align:center">:</td>
		<td><input type="text" name="nama" style="width:93%"/></td>
		
	</tr>
	<tr>
		<td><label>N.I.M</label>&nbsp;<font size="1.7em">(diisi bila sudah ada)</font></td>
		<td style="width:3%;text-align:center">:</td>
		<td><input type="text" name="nim" style="width:93%"/></td>
	</tr>

	<!--  tr>
	<td colspan="2" style="text-align:center"><i>------------------- Diisi bila Mahasiswa Pindahan -------------------</i></td>
	</tr>	
	<tr>
	<td style="text-align:center"><label>Asal P.T. Pindahan </label></td>
	<td><input type="text" name="aspti" value="UNIVERSITAS SATYAGAMA" /></td>
	</tr>
	<tr>
	<td colspan="2" style="text-align:center"><i>------------------------------------------------------------------------------</i></td>
	</tr -->
	<tr>
		<td><label>Jenis Kelamin</label></td>
		<td style="width:3%;text-align:center">:</td>
		<td>
		<%
		//kdjek
			String[]gender = Constants.getOptionGender();
		//System.out.println("v_kdjek = "+v_kdjek);
		%>
			<select name="kdjek" style="width:95%">
		<%
						
			for(int i=0;i<gender.length;i++) {
				String baris = gender[i];
				StringTokenizer st = new StringTokenizer(baris);
				String val = st.nextToken();
				String ket = st.nextToken();
		%>
				<option value="<%=val %>"><%=ket %></option>
		<%	
			}
		%>
			</select>
		</td>
	</tr>
	<tr>
		<td><label>Kepercayaan</label></td>
		<td style="width:3%;text-align:center">:</td>
		<td>
		<%
		//kdjek
			String[]agama = Constants.getOptionAgama();
		//System.out.println("v_kdjek = "+v_kdjek);
		%>
			<select name="agama" style="width:95%">
		<%
						
			for(int i=0;i<agama.length;i++) {
				String ag = agama[i];
		%>
				<option value="<%=ag.toUpperCase() %>"><%=ag %></option>
		<%	
			}
		%>
			</select>
		</td>
	</tr>
	<tr>
		<td><label>Kota Kelahiran</label></td>
		<td style="width:3%;text-align:center">:</td>
		<td><input type="text" name="tplhr" value="JAKARTA" style="width:93%"/></td>
	</tr>
	<tr>
		<td><label>Negara Kelahiran</label></td>
		<td style="width:3%;text-align:center">:</td>
		<td>
		<%
		//kdneg
			String negara = Constants.getListNegara();
			StringTokenizer st = new StringTokenizer(negara,",");
		//System.out.println("v_kdjek = "+v_kdjek);
		%>
			<select name="nglhr" style="width:95%">
		<%
			while(st.hasMoreTokens()) {
				String ctry = st.nextToken();
				if(ctry.equalsIgnoreCase("indonesia")) {
		%>
					<option value="<%=ctry.toUpperCase() %>" selected><%=ctry.toUpperCase() %></option>
		<%	
				}
				else {
		%>
					<option value="<%=ctry.toUpperCase() %>"><%=ctry.toUpperCase() %></option>
		<%
				}
			}
		%>
			</select>
		</td>
	</tr>
	<tr>
	<%
	String browserType = request.getHeader("User-Agent");
	browserType = browserType.toLowerCase();
	if(browserType.indexOf("mozilla") > -1) {
		//System.out.println("browserType1=mozilla");
	}
	else {
		if(browserType.indexOf("chrome") > -1) {
			//System.out.println("browserType2=chrome");
		}
	}
	%>
		<td><label>Tanggal Lahir</label><font size="1.7em"> (format : tgl/bln/tahun)</font></td>
		<td style="width:3%;text-align:center">:</td>
		<td><input type="text" name="tglhr" placeholder="dd/mm/yyyy" style="color:grey;font-weight:italic" style="width:93%"/></td>
	</tr>
	<tr>
		<td><label>Email </label></td>
		<td style="width:3%;text-align:center">:</td>
		<td><input type="email" name="email" style="width:93%"/></td>
	</tr>
	<tr>
		<td><label>HandPhone </label></td>
		<td style="width:3%;text-align:center">:</td>
		<td><input type="number" name="hp" style="width:93%"/></td>
	</tr>
	
	<tr>
		<td colspan="3" style="font-size:1.5em;font-weight:bold;text-align:left;background:#668BB0;color:#fff">PILIH TEST YANG WAJIB DIIKUTI</td>
	</tr>
	<%
	st = new StringTokenizer(tknInfoUjian,"||");
	while(st.hasMoreTokens()) {
		String infoUjian = st.nextToken();
		StringTokenizer st1 = new StringTokenizer(infoUjian,"$$");
		String idOnlineTest = st1.nextToken();
		String kodeNamaTest = st1.nextToken();
		String keterTest = st1.nextToken();
		String totSoal = st1.nextToken();
		String totTime = st1.nextToken();
		String passGrade = st1.nextToken();
		String idJadwalTest = st1.nextToken();
		String jadwalTest = st1.nextToken();
		String rilTestTimeStart = st1.nextToken();
		String rilTestTimeEnd = st1.nextToken();
		String canceled = st1.nextToken();
		String done = st1.nextToken();
		String inprogress = st1.nextToken();
		String pause = st1.nextToken();
		String npmOper = st1.nextToken();
		String nmmOper = st1.nextToken();
		String totMhs = st1.nextToken();
		String room = st1.nextToken();
		String ipAllow = st1.nextToken();
		String npmOprAllow = st1.nextToken();
		String note = st1.nextToken();
		String reusable = st1.nextToken();
	%>
	<tr>
		<td colspan="3" style="text-align:left;">
	    	<input type="checkbox" value="<%=infoUjian.replace("||","")%>" name="listUjian" disable=disable checked style="width:2em;height:2em;"/>
			&nbsp;&nbsp;<%=keterTest.toUpperCase() %>	
		</td>
	</tr>	
	<%
	}
	
	%>
	<tr>
		<td colspan="3" style="background:#369;color:#fff;text-align:center"><input type="submit" value="INPUT DATA CIVITAS" formtarget="_self" style="width:30%;text-align:center;height:30px;margin:5px"/></td>
	</tr>	
	
<%
	}
	else {
		//=======input dosen-======
		if(kdpstObjToBeInput!=null && kdpstObjToBeInput.contains(Constants.getKdpstDosen())) {
			//System.out.print("nmdos=="+nmdos);
			Vector vDos = Getter.getListDosen();
			if(vDos!=null && vDos.size()>0) {
				if(nmdos==null || Checker.isStringNullOrEmpty(nmdos) ||(nmdos!=null && !nmdos.equalsIgnoreCase("belumada"))) {
				//selain dayata 
				if(!validUsr.getNpm().equalsIgnoreCase("0000812100003")) { 
					//depriccated input dosen
					//akan diganti dengan yg khusus untuk dayat
					boolean dipilihDariList = false;
	%>
	<tr>
		<td><label>Dosen sudah terdaftar </label></td>
		<td style="width:3%;text-align:center">?</td>
		<td>
			<select name="namadsn" style="width:95%" onchange='this.form.submit()'>
			<option value="list__list">list dosen</option>
			<option value="belumada__belumada">DOSEN BELUM TERDAFTAR</option>	
	<%			

					ListIterator lidos = vDos.listIterator();
					while(lidos.hasNext()) {
						String nodos_nmdos = (String)lidos.next();
						//System.out.println("nodos_nmdos="+nodos_nmdos);
						StringTokenizer st = new StringTokenizer(nodos_nmdos,"__");
						//System.out.println("countTokens="+st.countTokens());
						String nodoslist = st.nextToken();
						String nmdoslist = st.nextToken();
						if(nmdos!=null &&nmdos.equalsIgnoreCase(nmdoslist)) {
							dipilihDariList = true;
	%>
				<option value="<%=nodoslist%>__<%=nmdoslist%>" selected><%=nmdoslist.toUpperCase()%></option>
	<%	
						}
						else {
	%>
				<option value="<%=nodoslist%>__<%=nmdoslist%>"><%=nmdoslist.toUpperCase()%></option>
	<%		
						}
					}
	%>
				
			</select>
	<%
					if(dipilihDariList) {
	%>
			<input type="hidden" name="dipilihDariList" value="true" />		
	<%					
					}
	%>		
			
	<%			
				}
				else {
					//kusus dayat input dosen
	%>
		<tr>
			<td colspan="3">
			<input type="hidden" name="StringfwdPageIfValid_String_Opt" value="input.civitasGuru" />
			<input type="hidden" name="nuInpDsnForm_Huruf_Opt" value="nuInpDsnForm" />
			<table align="center"  style="background:#d9e1e5;color:#000;width:100%;border:0px">
				<tr>
					<td style="width:20%;text-align:left;padding:0px 5px 0px 5px;background:#a6bac4">Gelar-Nama-Gelar</td>
					<td colspan="3" style="width:30%;text-align:center;margin:0px 5px 0px 5px"><input placeholder="Gelar Depan" type="text" name="Gelar-Depan_Huruf_Opt" style="width:20%"/>
					<input type="text" name="Nama-Lengkap_Huruf_Wajib" style="width:55%" placeholder="Nama Lengkap" />
					<input type="text" name="Gelar-Belakang_Huruf_Opt" style="width:20%" placeholder="Gelar Belakang" /></td>
				</tr>
				
				<tr>
					<td style="width:20%;text-align:left;padding:0px 5px 0px 5px;background:#a6bac4">Jenis Kelamin</td>
					<td style="width:30%;text-align:center;margin:0px 5px 0px 5px">
						<input type="radio" name="kdjek_Huruf_Opt" value="P">Wanita &nbsp&nbsp
						<input type="radio" name="kdjek_Huruf_Opt" value="L" checked="checked">Pria
					</td>
					<td style="width:20%;text-align:left;padding:0px 5px 0px 5px;background:#a6bac4">Tipe Ikatan Kerja</td>
					<td style="width:30%;text-align:center;margin:0px 5px 0px 5px">
					<select name="Ikatan-Kerja_Huruf_Opt" style="width:98%">
				<%
					String[]tipeIkatanKerjaDosen = Constants.getTipeIkatanKerjaDosen();
					for(int i=0;i<tipeIkatanKerjaDosen.length;i++) {
						StringTokenizer st = new StringTokenizer(tipeIkatanKerjaDosen[i],"-");
						String kode = st.nextToken();
						String keter = st.nextToken();
				%>
						<option value="<%=kode%>">Dosen <%=keter %></option>
				<%		
					}
				%>		
					</select>
					</td>
				</tr>		
				<tr>
					<td colspan="2" style="width:20%;text-align:left;padding:0px 5px 0px 5px;background:#a6bac4">Homebase Prodi (Bila Dosen Tetap Universitas)</td>
					<td colspan="2" style="width:30%;text-align:center;margin:0px 5px 0px 5px">
						<select name="Prodi-Homebase_Huruf_Opt" style="width:98%">
							<option value="null" selected="selected">Bukan Dosen Tetap</option>
					<%
					JSONArray joa_prodi = Getter.readJsonArrayFromUrl("/v1/search_prodi");
					if(joa_prodi!=null && joa_prodi.length()>0) {
						for(int i=0;i<joa_prodi.length();i++) {
							JSONObject job_tmp = joa_prodi.getJSONObject(i);
							String kdpst_ = job_tmp.getString("KDPSTMSPST");
							String kdfak_ = job_tmp.getString("KDFAKMSPST");
							String kdjen_ = job_tmp.getString("KDJENMSPST");
							String nmpst_ = job_tmp.getString("NMPSTMSPST");
							//String val = kdpst_+"-"+kdfak_+"-"+kdjen_+"-"+nmpst_;
							String val = kdpst_+"";
					%>
							<option value="<%=val%>"><%=nmpst_ %> (<%=Converter.getDetailKdjen(kdjen_) %>)</option>
					<%		
						}
					}
					%>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="width:20%;text-align:left;margin:0px 5px 0px 5px">Memiliki &nbsp&nbsp
						<input type="radio" name="nidnn_Huruf_Opt" value="nip">NIP &nbsp&nbsp
						<input type="radio" name="nidnn_Huruf_Opt" value="nis" checked="checked">NIS &nbsp&nbsp
						<input type="radio" name="nidnn_Huruf_Opt" value="npp" checked="checked">NPP &nbsp&nbsp
						<input type="radio" name="nidnn_Huruf_Opt" value="nik" checked="checked">NIK
					</td>
					<td colspan="2" style="width:20%;text-align:left;margin:0px 5px 0px 5px">
						<input type="text" name="No-Dosen_Huruf_Opt" style="width:98%" placeholder="Nomor dosen (NIP/NIS/NPP/NIK) bila ada"/>
					</td>
				</tr>
			</table>
			</td>	
		</tr>
		<tr>
			<td colspan="4" style="background:#369;color:#fff;text-align:center"><div id="btn" style="text-align:center;background-color:#a6bac4;width:100%;height:35px;visibility:show" ><input type="button" id="somebutton" value="INPUT DATA" style="width:50%;height:25px;margin:5px;"/></div></td>
			
		</tr>
	<%				
				}
				}
				else {
					//dosen belum terdaftar
	%>
		<td><label>Nama Dosen Pengajar </label></td>
		<td style="width:3%;text-align:center">?</td>
		<td><input type="text" name="namadsn" style="width:95%"/></td>
		<input type="hidden" name="dipilihDariList" value="true" />
	<%					
				}
	%>
		</td>
	</tr>
	<%	
				if(nmdos!=null && !nmdos.equalsIgnoreCase("list")) {
					String submitMsg = "";
					if(nmdos.equalsIgnoreCase("belumada")) {
						submitMsg = "INPUT DATA DOSEN BARU";
					}
					else {
						submitMsg = "NEXT";
					}
	%>				
	<tr>
		<td colspan="3" style="background:#369;color:#fff;text-align:center"><input type="submit" value="<%=submitMsg %>" formtarget="_self" style="width:30%;text-align:center;height:30px;margin:5px"/></td>
	</tr>
	<%
				}
			}
		}
		//=======end input dosen-======
	}
%>	
</table>

</form>
</div>
</div>
<%
}
%>
<br/>
<div id="div_msg" style="font-style:italic;color:red;text-align:left;background-color:#d9e1e5;width:700px;height:100%;visibility:hidden;margin:0px 0 0 105px;" >
</div>
</body>
</html>