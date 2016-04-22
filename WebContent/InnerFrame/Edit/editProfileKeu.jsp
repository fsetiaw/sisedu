<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
//String listKurAndSelected = ""+request.getParameter("listKurAndSelected");
//System.out.println("edit listKurAndSelected="+listKurAndSelected);
boolean profilePage = false;
boolean dashPage = false;
boolean keuPage = false;
Vector v= null; //dipake ma inner menu
Vector vHisBea = (Vector) session.getAttribute("vHisBea");
Vector vJenisBea = (Vector) session.getAttribute("vJenisBea");
Vector vListPaket = (Vector) session.getAttribute("vListPaket");
String ext_form = request.getParameter("ext_form");
session.removeAttribute("vHisBea");
session.removeAttribute("vListPaket");
session.removeAttribute("vJenisBea");

/*
v = (Vector) request.getAttribute("v_profile");
String v_id_obj = (String)request.getAttribute("v_id_obj");
String v_nmmhs=(String)request.getAttribute("v_nmmhs");
String v_npmhs=(String)request.getAttribute("v_npmhs");
String v_nimhs=(String)request.getAttribute("v_nimhs");
String v_obj_lvl=(String)request.getAttribute("v_obj_lvl");
String v_kdpst=(String)request.getAttribute("v_kdpst");
String v_kdjen=(String)request.getAttribute("v_kdjen");
String v_smawl=(String)request.getAttribute("v_smawl");
String v_tplhr=(String)request.getAttribute("v_tplhr");
String v_tglhr=(String)request.getAttribute("v_tglhr");
String v_aspti=(String)request.getAttribute("v_aspti");
String v_aspst=(String)request.getAttribute("v_aspst");
String v_btstu=(String)request.getAttribute("v_btstu");
String v_kdjek=(String)request.getAttribute("v_kdjek");
String v_nmpek=(String)request.getAttribute("v_nmpek");
String v_ptpek=(String)request.getAttribute("v_ptpek");
String v_stmhs=(String)request.getAttribute("v_stmhs");
String v_stpid=(String)request.getAttribute("v_stpid");
String v_sttus=(String)request.getAttribute("v_sttus");
String v_email=(String)request.getAttribute("v_email");
String v_nohpe=(String)request.getAttribute("v_nohpe");
String v_almrm=(String)request.getAttribute("v_almrm");
String v_kotrm=(String)request.getAttribute("v_kotrm");
String v_posrm=(String)request.getAttribute("v_posrm");
String v_telrm=(String)request.getAttribute("v_telrm");
String v_almkt=(String)request.getAttribute("v_almkt");
String v_kotkt=(String)request.getAttribute("v_kotkt");
String v_poskt=(String)request.getAttribute("v_poskt");
String v_telkt=(String)request.getAttribute("v_telkt");
String v_jbtkt=(String)request.getAttribute("v_jbtkt");
String v_bidkt=(String)request.getAttribute("v_bidkt");
String v_jenkt=(String)request.getAttribute("v_jenkt");
String v_nmmsp=(String)request.getAttribute("v_nmmsp");
String v_almsp=(String)request.getAttribute("v_almsp");
String v_possp=(String)request.getAttribute("v_possp");
String v_kotsp=(String)request.getAttribute("v_kotsp");
String v_negsp=(String)request.getAttribute("v_negsp");
String v_telsp=(String)request.getAttribute("v_telsp");
String v_neglh=(String)request.getAttribute("v_neglh");
String v_agama=(String)request.getAttribute("v_agama");
*/
%>
</head>
<body>
<div id="header">
<%@ include file="../innerMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<!-- Column 1 start -->
		
		<br />
		
		
		<!--  input type="hidden" value="<%=v_id_obj %>" name="v_id_obj" /><input type="hidden" value="<%=v_obj_lvl %>" name="v_obj_lvl" / -->
		<input type="hidden" value="<%=v_npmhs %>" name="v_npmhs" /><input type="hidden" value="<%=v_kdpst %>" name="v_kdpst" />
		<p>
		<table align="center" border="1" style="background:#d9e1e5;color:#000;width:700px">	
			
			<tr>
				<td align="center" colspan="2" bgcolor="#369" style="color:#fff" padding-left="2px"><b>DATA PRIBADI</b></td><td align="center" colspan="2" bgcolor="#369" style="color:#fff" padding-left="2px"><b>ALAMAT RUMAH</b></td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">Nama </td><td align="center" width="250px"><input type="text" value="<%=Checker.pnn(v_nmmhs) %>" name="v_nmmhs" style="width:98%" readonly/></td><td align="center" style="padding-left:2px" colspan="2" rowspan="6"><textarea name="v_almrm" style="width:98%;height:95%;valign:middle" readonly><%=Checker.pnn(v_almrm) %></textarea></td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">Gender</td><td align="center" width="250px">
				<%
				//kdjek
				String[]gender = Constants.getOptionGender();
				//System.out.println("v_kdjek = "+v_kdjek);
				%>
				<select disabled name="v_kdjek" style="width:98%" read>
					<%
					
					for(int i=0;i<gender.length;i++) {
						String opt = gender[i];
						StringTokenizer st = new StringTokenizer(opt);
						String val = st.nextToken();
						String ket = st.nextToken();
						if(val.equalsIgnoreCase(v_kdjek)) {
							%>
							<option value="<%=val %>" selected><%=ket %></option>
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
				</td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">Agama</td><td align="center" width="250px">
				<%
				//kdjek
				String[]agama = Constants.getOptionAgama();
				//System.out.println("v_kdjek = "+v_kdjek);
				%>
				<select disabled name="v_agama" style="width:98%">
					<%
					
					for(int i=0;i<agama.length;i++) {
						String opt = agama[i];
						if(opt.equalsIgnoreCase(v_agama)) {
							%>
							<option value="<%=opt.toUpperCase() %>" selected><%=opt %></option>
						<%	
						}
						else {
						%>
							<option value="<%=opt %>"><%=opt %></option>
						<%	
						}
					}
					%>
				</select>
				</td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">Status</td><td align="center" width="250px">
				<%
				//kdjek
				String[]nikah = Constants.getOptionStatusNikah();
				%>
				<select disabled name="v_sttus" style="width:98%">
					<%
					for(int i=0;i<nikah.length;i++) {
						String opt = nikah[i];
						if(opt.equalsIgnoreCase(v_sttus)) {
							%>
							<option value="<%=opt %>" selected><%=opt %></option>
						<%	
						}
						else {
						%>
							<option value="<%=opt %>"><%=opt %></option>
						<%	
						}
					}
					%>
				</select>
				</td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">Email</td><td align="center" width="250px"><input type="email" value="<%=Checker.pnn(v_email) %>" name="v_email" style="width:98%" readonly/></td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">No HP</td><td align="center" width="250px"><input type="number" value="<%=Checker.pnn(v_nohpe) %>" name="v_nohpe" style="width:98%" readonly/></td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px" colspan="2">INFO KELAHIRAN</td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">Kota</td><td align="center" width="250px"><input type="text" value="<%=Checker.pnn(v_tplhr) %>" name="v_tplhr" style="width:98%" readonly/></td><td align="left" width="100px" style="padding-left:2px">No Telp </td><td align="center" width="250px"><input type="number" value="<%=Checker.pnn(v_telrm) %>" name="v_telrm" style="width:98%" readonly/></td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">Negara</td>
				<td align="center" width="250px">
					<select disabled name="v_neglh" style="width:98%">
		<%
						String negara = Constants.getListNegara();
						StringTokenizer st = new StringTokenizer(negara,",");
						while(st.hasMoreTokens()) {
							String ctry = st.nextToken();
							if(ctry.equalsIgnoreCase(v_neglh)) {
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
				</td><td align="left" width="100px" style="padding-left:2px">Kode Pos </td><td align="center" width="250px"><input type="number" value="<%=Checker.pnn(v_posrm) %>" name="v_posrm" style="width:98%" readonly/></td>
			</tr>
			<tr>
				<td align="left" width="100px" style="padding-left:2px">Tgl Lahir</td><td align="center" width="250px">
				<%
				if(v_tglhr!=null && !Checker.isStringNullOrEmpty(v_tglhr)) {
					out.print(DateFormater.keteranganDate(Checker.pnn(v_tglhr)));  	
				}
				
				%></td><td align="left" width="100px" style="padding-left:2px">Kota </td><td align="center" width="250px"><input type="text" value="<%=Checker.pnn(v_kotrm) %>" name="v_kotrm" style="width:98%" readonly/></td>
			</tr>
		</table>
		</p>
		<br />
		
		<h2 align="center">DATA BEASISWA</h2>

<%		
		String jenisBea = request.getParameter("jenisBea");
		System.out.println("jenisBea=="+jenisBea);
		//if(vHisBea!=null && vHisBea.size()>0) {
		if(false) {
			//ada
		}
		else {
			//empty langsung form input status beasiswa
			if(vJenisBea==null || vJenisBea.size()<1) {
				//error msg harap input paket beasiswa
			}
			else {
				ListIterator li = vJenisBea.listIterator();//li.add(idJenis+"`"+namaPaket);
				//if(ext_form!=null && !Checker.isStringNullOrEmpty(ext_form)) {

		%>
	<form action="go.getProfileBeasiswa" align="center">
		<input type="hidden" value="<%=v_npmhs %>" name="npm"/><input type="hidden" value="<%=v_nimhs %>" name="nim"/>
		<input type="hidden" value="<%=v_kdpst %>" name="kdpst" /><input type="hidden" value="<%=v_id_obj %>" name="id_obj" />
		<input type="hidden" value="<%=v_obj_lvl %>" name="obj_lvl" /><input type="hidden" value="editProfileKeu" name="cmd" />
		
		<table align="center" border="1" style="background:#d9e1e5;color:#000;width:700px">	
			
			<tr>
				<td align="center" colspan="4" bgcolor="#369" style="color:#fff" padding-left="2px"><b>FORM DATA BEASISWA</b></td>
			</tr>
			<tr>
				<td align="left" style="padding-left:2px" colspan="2">Nama Jenis Beasiswa</td>
				<td align="center" bgcolor="white" style="padding-left:2px" colspan="2">
					<select name="jenisBea" onchange='this.form.submit()' style="width:99%">
						<option value="null">-------  Harap Pilih Jenis Beasiswa  -------</option>
		<%
					boolean jenisBeaExist = false;
					while(li.hasNext()) {
						String brs = (String)li.next();
						StringTokenizer stt = new StringTokenizer(brs,"`");
						stt.nextToken();
						String nmmJenis = stt.nextToken();
						//System.out.println(brs+" vs "+jenisBea);
						if(brs.equalsIgnoreCase(jenisBea)) {
							jenisBeaExist=true;
		%>	
						<option value="<%=brs%>" selected=selected><%=nmmJenis%></option>
		<%					
						}
						else {
		%>	
						<option value="<%=brs%>"><%=nmmJenis%></option>
		<%				
						}
					}
		%>				
					</select>
				</td>
			</tr>
		<%
		
					if(vListPaket!=null && vListPaket.size()>0) {
						String paketBea = request.getParameter("paketBea");
		%>	
			<tr>
				<td align="left" style="padding-left:2px" colspan="2">Nama Paket Beasiswa</td>
				<td align="center" bgcolor="white" style="padding-left:2px" colspan="2">
					<select name="paketBea" onchange='this.form.submit()' style="width:99%">
						<option value="null">-------  Harap Pilih Paket Beasiswa  -------</option>
		<%
					//boolean paketBeaMatch = false;
						String infoPaket = null;
						li = vListPaket.listIterator();
						while(li.hasNext()) {
							String brs = (String)li.next();
							StringTokenizer stt = new StringTokenizer(brs,"`");
							String nmmPaket = stt.nextToken();
						
				//System.out.println(brs+" vs "+jenisBea);
							if(brs.equalsIgnoreCase(paketBea)) {
					//paketBeaMatch=true;
								infoPaket=""+brs;
		%>	
						<option value="<%=brs%>" selected=selected><%=nmmPaket%></option>
		<%					
							}
							else {
		%>	
						<option value="<%=brs%>"><%=nmmPaket%></option>
		<%				
							}
						}
					
		%>				
					</select>
				</td>
			</tr>
		<%
						if(infoPaket!=null && !Checker.isStringNullOrEmpty(infoPaket)) {
							StringTokenizer stt = new StringTokenizer(infoPaket,"`");
						//li.add(nmmPaket+"`"+idJenis+"`"+jumDana+"`"+unitPeriode+"`"+namaInstansi+"`"+jenisInstansi+"`"+keter+"`"+jenisBea+"`"+scopeKampus);
							String nmmPaket_=stt.nextToken();
							String idJenis_=stt.nextToken();
							String jumDana_=stt.nextToken();
							String unitPeriode_=stt.nextToken();
							String namaInstansi_=stt.nextToken();
							String jenisInstansi_=stt.nextToken();
							String keter_=stt.nextToken();
							String jenisBea_=stt.nextToken();
							String scopeKampus_=stt.nextToken();
		%>
			<tr>
				<td align="center" colspan="4" bgcolor="#369" style="color:#fff" padding-left="2px"><b>INFO PAKET <%=nmmPaket_.toUpperCase() %></b></td>
			</tr>
			<tr>
				<td align="left" width="150px" style="padding-left:2px">Besaran Dana</td>
				<td align="center" bgcolor="white" width="200px" style="padding-left:2px"><%=NumberFormater.indoNumberFormat(jumDana_) %>/<%=unitPeriode_ %></td>
				<td align="left" width="150px" style="padding-left:2px">Jenis Beasiswa</td>
				<td align="center" bgcolor="white" width="200px" style="padding-left:2px"><%=jenisBea_ %></td>
			</tr>
			<tr>
				<td align="left" width="150px" style="padding-left:2px">Nama Sponsor</td>
				<td align="center" bgcolor="white" width="200px" style="padding-left:2px"><%=namaInstansi_ %></td>
				<td align="left" width="150px" style="padding-left:2px">Jenis Instansi Sponsor</td>
				<td align="center" bgcolor="white" width="200px" style="padding-left:2px"><%=jenisInstansi_ %></td>
			</tr>
			<tr>
				<td align="left" colspan="4" style="padding-left:2px">
				<%
							out.print("KETERANGAN & PERSYARATAN:<br/><br/>");
							out.print(keter_+"<br/><br/>");
				%></td>
			</tr>
		<%
				
							if(ext_form!=null && !Checker.isStringNullOrEmpty(ext_form)) {
		%>
			<tr>
				<td align="center" colspan="4" bgcolor="#369" style="color:#fff" padding-left="2px"><b>INFO AKUN BANK</b></td>
			</tr>
			<tr>
				<td align="left" width="150px" style="padding-left:2px">Nama Bank</td>
				<td align="center" bgcolor="white" width="200px" style="padding-left:2px"><input type="text" name="namaBank" placeholder="*Wajib diisi" style="width:98%"></td>
				<td align="left" width="150px" style="padding-left:2px">Nama Pemilik Rekening</td>
				<td align="center" bgcolor="white" width="200px" style="padding-left:2px"><input type="text" name="namaDiRek" placeholder="*Wajib diisi" style="width:98%"></td>
			</tr>
			<tr>
				<td align="left" width="150px" style="padding-left:2px">No Rekening Bank</td>
				<td align="center" bgcolor="white" width="200px" style="padding-left:2px"><input type="text" name="noRek" placeholder="*Wajib diisi" style="width:98%"></td>
				<td align="left" width="150px" style="padding-left:2px">Periode</td>
				<td align="center" bgcolor="white" width="200px" style="padding-left:2px"><input type="text" name="periode" value="<%=Checker.getThsmsNow() %>" placeholder="*Wajib diisi" style="width:98%" readonly="readonly" /></td>
			</tr>
		<%	
							}
		%>	
			<tr>
				<td align="center" colspan="4" bgcolor="#369" style="color:#fff" padding-left="2px">
		<%
							if(ext_form!=null && !Checker.isStringNullOrEmpty(ext_form)) {
		%>		
				<input type="hidden" name="submitInfoPaket" value="<%=infoPaket%>" />
				<input type="submit" name="submit" value="Update Data Beasiswa"/>
		<%
							}
							else {
		%>		
				<input type="hidden" name="submitInfoPaket" value="<%=infoPaket%>" />
				<input type="submit" name="submit" value="Pilih/Gunakan Paket Beasiswa Ini"/>
		<%		
							}
		%>		
				</td>
			</tr>
		<%				
						}
					}
					else {
						if(jenisBeaExist) {
								
					
		%>
			<tr>
				<td align="center" colspan="4" style="color:red" padding-left="2px">
				<b>Belum ada paket beasiswa untuk Jenis Beasiswa Diatas<br/>
				<a href="prosess.addPaketBeasiswa?atMenu=addPaketBea&scope=addPaketBea">Klik Disini Untuk Membuat Paket Beasiswa Baru</a>
				</b></td>
			</tr>
		<%
					
						}
					}
		
		%>	
		</table>	
	</form>
		<%	
			
			}
		}		
%>
		

</body>
</html>