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
String listKurAndSelected = ""+request.getParameter("listKurAndSelected");
//System.out.println("edit listKurAndSelected="+listKurAndSelected);
boolean profilePage = false;
boolean dashPage = false;
boolean keuPage = false;
Vector v= null; 
Vector vListDsn = (Vector) session.getAttribute("vListDsn");
session.removeAttribute("vListDsn");
String listTipeObj = (String)request.getAttribute("listTipeObj");
//idObj+"$"+kdpst+"$"+objName+"$"+objDesc+"$"+objLvl+"$"+acl+"$"+al+"$"+dvalue+"$"+nicknm;
request.removeAttribute("listTipeObj");
String curPa = (String)request.getAttribute("curPa");
//System.out.println("11curPacurPa="+curPa);
request.removeAttribute("curPa");
boolean objMhs = false;
if(validUsr.getObjNickNameGivenObjId().contains("MHS")||validUsr.getObjNickNameGivenObjId().contains("mhs")) {
	objMhs = true;
}
//System.out.println("objMhs ="+objMhs);
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
<%
//if(validUsr.iAmStu()) {
	//System.out.println("ilikechopim");
%>
<%@ include file="../profileInnerMenu.jsp" %>
<%	
//}
//else {
%>
<!--  %@ include file="../innerMenu.jsp" % -->
<%
//}
%>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<!-- Column 1 start -->
		<br />
		<%
		if(v.size()>0){
			
			if(validUsr.isAllowTo("editDataPribadi")>0) {
		%>
			<form action="go.updateProfile" align="center">
			<input type="hidden" value="<%=v_id_obj %>" name="v_id_obj" /><input type="hidden" value="<%=v_obj_lvl %>" name="v_obj_lvl" />
			<input type="hidden" value="<%=v_npmhs %>" name="v_npmhs" /><input type="hidden" value="<%=v_kdpst %>" name="v_kdpst" />
			<p>
			<table align="center" border="1" style="background:#d9e1e5;color:#000;width:700px">	
				
				<tr>
					<td align="center" colspan="2" bgcolor="#369" style="color:#fff" padding-left="2px"><b>DATA PRIBADI</b></td><td align="center" colspan="2" bgcolor="#369" style="color:#fff" padding-left="2px"><b>ALAMAT RUMAH</b></td>
				</tr>
				<%
				if(!objMhs) {
				%>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Nama </td><td align="center" width="250px"><input type="text" value="<%=Checker.pnn(v_nmmhs) %>" name="v_nmmhs" style="width:98%" /></td><td align="center" style="padding-left:2px" colspan="2" rowspan="6"><textarea name="v_almrm" style="width:98%;height:95%;valign:middle"><%=Checker.pnn(v_almrm) %></textarea></td>
				</tr>
				<%
				}
				else {
				%>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Nama </td>
					<td align="center" width="250px"><%=Checker.pnn(v_nmmhs) %>
					<input type="hidden" value="<%=Checker.pnn(v_nmmhs) %>" name="v_nmmhs" style="width:98%" />
					</td><td align="center" style="padding-left:2px" colspan="2" rowspan="6"><textarea name="v_almrm" style="width:98%;height:95%;valign:middle"><%=Checker.pnn(v_almrm) %></textarea></td>
				</tr>
				<%
				}
				%>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Gender</td><td align="center" width="250px">
					<%
					//kdjek
					String[]gender = Constants.getOptionGender();
					//System.out.println("v_kdjek = "+v_kdjek);
					%>
					<select name="v_kdjek" style="width:98%">
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
					<select name="v_agama" style="width:98%">
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
					<select name="v_sttus" style="width:98%">
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
					<td align="left" width="100px" style="padding-left:2px">Email</td><td align="center" width="250px"><input type="email" value="<%=Checker.pnn(v_email) %>" name="v_email" style="width:98%" /></td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">No HP</td><td align="center" width="250px"><input type="number" value="<%=Checker.pnn(v_nohpe) %>" name="v_nohpe" style="width:98%" /></td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px" colspan="2">INFO KELAHIRAN</td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Kota</td><td align="center" width="250px"><input type="text" value="<%=Checker.pnn(v_tplhr) %>" name="v_tplhr" style="width:98%" /></td><td align="left" width="100px" style="padding-left:2px">No Telp </td><td align="center" width="250px"><input type="number" value="<%=Checker.pnn(v_telrm) %>" name="v_telrm" style="width:98%" /></td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Negara</td>
					<td align="center" width="250px">
						<select name="v_neglh" style="width:98%">
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
					</td><td align="left" width="100px" style="padding-left:2px">Kode Pos </td><td align="center" width="250px"><input type="number" value="<%=Checker.pnn(v_posrm) %>" name="v_posrm" style="width:98%" /></td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Tgl Lahir</td><td align="center" width="250px"><input type="date" value="<%=Checker.pnn(v_tglhr) %>" name="v_tglhr" style="width:98%" /></td><td align="left" width="100px" style="padding-left:2px">Kota </td><td align="center" width="250px"><input type="text" value="<%=Checker.pnn(v_kotrm) %>" name="v_kotrm" style="width:98%" /></td>
				</tr>
			</table>
			</p>
			<br />
			<p>
			<table align="center" border="1" style="background:#d9e1e5;color:#000;width:700px">	
				
				<tr>
					<td align="center" colspan="2" bgcolor="#369" style="color:#fff" padding-left="2px"><b>INFO PEKERJAAN</b></td><td align="center" colspan="2" bgcolor="#369" style="color:#fff" padding-left="2px"><b>ALAMAT KANTOR</b></td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Nama Kantor</td><td align="center" width="250px"><input type="text" value="<%=Checker.pnn(v_nmpek) %><%=Checker.pnn(v_ptpek) %>" name="v_nmpek" style="width:98%" /></td><td align="center" style="padding-left:2px" colspan="2" rowspan="3"><textarea name="v_almkt" style="width:98%;height:95%;valign:middle"><%=Checker.pnn(v_almkt) %></textarea></td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Jabatan</td><td align="center" width="250px"><input type="text" value="<%=Checker.pnn(v_jbtkt) %>" name="v_jbtkt" style="width:98%" /></td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Bidang Usaha</td><td align="center" width="250px"><input type="text" value="<%=Checker.pnn(v_bidkt) %>" name="v_bidkt" style="width:98%" /></td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Jenis </td><td align="center" width="250px"><input type="text" value="<%=Checker.pnn(v_jenkt) %>" name="v_jenkt" style="width:98%" /></td><td align="left" width="100px" style="padding-left:2px">Kota </td><td align="center" width="250px"><input type="text" value="<%=Checker.pnn(v_kotkt) %>" name="v_kotkt" style="width:98%" /></td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">No Telp</td><td align="center" width="250px"><input type="number" value="<%=Checker.pnn(v_telkt) %>" name="v_telkt" style="width:98%" /></td><td align="left" width="100px" style="padding-left:2px">Kode Pos </td><td align="center" width="250px"><input type="number" value="<%=Checker.pnn(v_poskt) %>" name="v_poskt" style="width:98%" /></td>
				</tr>
			</table>
			</p>
			<br />
			<p>
			<table align="center" border="1" style="background:#d9e1e5;color:#000;width:700px">	
				
				<tr>
					<td align="center" colspan="2" bgcolor="#369" style="color:#fff" padding-left="2px"><b>INFO SPONSOR</b></td><td align="center" colspan="2" bgcolor="#369" style="color:#fff" padding-left="2px"><b>INFO KEMAHASISWAAN</b></td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Nama Sponsor</td><td align="center" width="250px"><input type="text" value="<%=Checker.pnn(v_nmmsp) %>" name="v_nmmsp" style="width:98%" /></td><td align="left" width="100px" style="padding-left:2px">Angkatan</td>
					
					<td align="center" width="250px">
					<%
					//angkatan
					//sy disable aja krn takut masalah compatibility, takutnya bila sistem didisable di browser laen lolos
					if(!objMhs) {
					%>
						<select name="v_smawl" style="width:98%">
					<%
						String smawl = Checker.getThsmsPmb();
						String thsms = ""+smawl;
						v = new Vector();
						ListIterator li = v.listIterator();
						li.add(thsms);
						for(int i=0;i<30;i++) {
							thsms = Tool.returnPrevThsmsGiven(thsms);
							li.add(thsms);
						}
						Collections.sort(v);
						li = v.listIterator();
						String keter_thsms = "N/A";
						String value_thsms = "N/A";
						while(li.hasNext()) {
							thsms = (String)li.next();
						//System.out.println("thsms vs v_smawl = "+thsms+" vs "+v_smawl);
							if(Tool.isThsmsEqualsSmawl(thsms, v_smawl)) {
								String keter_thsms_and_value = Converter.convertThsms(thsms);
								StringTokenizer stt = new StringTokenizer(keter_thsms_and_value,"#&");
								keter_thsms = stt.nextToken();
								value_thsms = stt.nextToken();
						%>
							<option value="<%=value_thsms%>" selected><%=keter_thsms %></option>
						<%
							}
							else {
								String keter_thsms_and_value = Converter.convertThsms(thsms);
								StringTokenizer stt = new StringTokenizer(keter_thsms_and_value,"#&");
								keter_thsms = stt.nextToken();
								value_thsms = stt.nextToken();
						%>
							<option value="<%=value_thsms%>"><%=keter_thsms %></option>
						<%	
							}
						}
						
					%>
					</select>
					<%
					}
					else {
						String keter_thsms_and_value = Converter.convertThsms(v_smawl);
						StringTokenizer stt = new StringTokenizer(keter_thsms_and_value,"#&");
						String keter_thsms = stt.nextToken();
						String value_thsms = stt.nextToken();
					%>
					<input type="hidden" name="v_smawl" value="<%=value_thsms %>" />
					<%=value_thsms %>
					<%	
					}
					%>
					</td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">No Telp</td><td align="center" width="250px"><input type="number" value="<%=Checker.pnn(v_telsp) %>" name="v_telsp" style="width:98%" /></td><td align="left" style="padding-left:2px">N.I.M</td><td align="center" >
					<%
					if(!objMhs) {
					%>
					<input type="text" name="v_nimhs" value="<%=Checker.pnn(v_nimhs) %>" style="width:98%"/>
					<%
					}
					else {
					%>
					<%=Checker.pnn(v_nimhs) %>
					<input type="hidden" name="v_nimhs" value="<%=Checker.pnn(v_nimhs) %>" style="width:98%"/>
					<%
					}
					%>
					</td>
					
				</tr>
				<tr>
					<td align="center" colspan="2" bgcolor="#369" style="color:#fff" padding-left="2px"><b>ALAMAT SPONSOR</b></td><td align="left" style="padding-left:2px">Shift Kelas</td><td align="center">
					<%
					if(validUsr.isUsrAllowTo("updShift", v_npmhs, v_obj_lvl)) {
					%>
					<select name="shiftKelas" style="width:99%">
					<%
					//System.out.println("edit profile");
					Vector v1 = Converter.getPilihanShiftYgAktif(v_kdjen);
					ListIterator li1 = v1.listIterator();
					while(li1.hasNext()) {
						String brs = (String)li1.next();
						//System.out.println("brs2="+brs);
						//if(!brs.startsWith("N/A")&&!brs.startsWith("n/a")) {
							st = new StringTokenizer(brs,"#&");
							String value = st.nextToken();
							String shift = st.nextToken();
							String hari = st.nextToken();
							String textTampil = st.nextToken();
							if(value.equalsIgnoreCase(v_shift)) {
							%>
						<option value="<%=value %>" selected><%=textTampil %></option>			
							<%
							}
							else {
						%>
						<option value="<%=value %>" ><%=textTampil %></option>			
						<%
							}
						//}
						//System.out.println(brs);
					}
					%>
					</select>
					<%
					}
					else {
						Vector v1 = Converter.getPilihanShiftYgAktif(v_kdjen);
						ListIterator li1 = v1.listIterator();
						boolean match = false;
						String value = "";
						String shift = "";
						String hari = "";
						String textTampil = "";
						while(li1.hasNext()&&!match) {
							String brs = (String)li1.next();
							if(!brs.startsWith("N/A")&&!brs.startsWith("n/a")) {
								st = new StringTokenizer(brs,"#&");
								value = st.nextToken();
								shift = st.nextToken();
								hari = st.nextToken();
								textTampil = st.nextToken();
								if(value.equalsIgnoreCase(v_shift)) {
									match = true;
								}
							}
						}
						if(match) {
						%>
						<input type="hidden" name="shiftKelas" value="<%=value %>"/>
						<%	
						}
						else {
						%>
						<input type="hidden" name="shiftKelas" value="N/A"/>
						<%
						}
						out.print(textTampil);
					}
					%>
					</td>
				</tr>
				<tr>
					<td align="left" style="padding-left:2px" colspan="2" rowspan="2"><textarea name="v_almsp" style="width:98%;height:95%;valign:middle"><%=Checker.pnn(v_almsp) %></textarea></td><td align="left" style="padding-left:2px">Tipe</td><td align="center">
					<%
					//stpid
					if(!objMhs) {
						String[]stpid = Constants.getTipeCivitas();
					%>
					<select name="v_stpid" style="width:98%">
						<%
						for(int i=0;i<stpid.length;i++) {
							String baris = stpid[i];
							st = new StringTokenizer(baris);
							String val = st.nextToken();
							String ket = st.nextToken();
							if(val.equalsIgnoreCase(v_stpid)) {
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
				<%
					}
					else {
				%>
					<input type="hidden" name="v_stpid" value="<%=v_stpid%>">
					<%=Converter.convertStpid(v_stpid) %>
				<%		
					}
				%>	
					</td>
				</tr>
				<tr>
					<td align="left" style="padding-left:2px">Kurikulum</td>
					<td align="center">
				<%
				if(!objMhs) {
				%>	
					<select name="krklm" style="width:98%">
					<%
					st = new StringTokenizer(listKurAndSelected,"__");
					//boolean selected = false;
					while(st.hasMoreTokens()) {
						String idkurA = st.nextToken();
						String nmkurA = st.nextToken();
						nmkurA=nmkurA.replace("tandaKoma", ",");
						nmkurA=nmkurA.replace("tandaDan", "&");
						nmkurA=nmkurA.replace("tandaGb", "_");
						String sksttA = st.nextToken();
						String smsttA = st.nextToken();
						String statusSelected = st.nextToken();
						if(statusSelected.equalsIgnoreCase("selected")) {
							%>
							<option value="<%=idkurA %>__<%=nmkurA %>__<%=sksttA %>__<%=smsttA %>" selected><%=nmkurA %> (<%=sksttA %> sks/<%=smsttA %> sms)</option>
							<%	
						}
						else {
							%>
							<option value="<%=idkurA %>__<%=nmkurA %>__<%=sksttA %>__<%=smsttA %>"><%=nmkurA %> (<%=sksttA %> sks/<%=smsttA %> sms)</option>
							<%
						}
						
					}
					
					%>
					</select>
				<%
				}
				else {
					//bila kurikulum sudah diisi = ada value selected
					if(listKurAndSelected.contains("selected")) {
						st = new StringTokenizer(listKurAndSelected,"__");
						String idkurA = st.nextToken();
						String nmkurA = st.nextToken();
						nmkurA=nmkurA.replace("tandaKoma", ",");
						nmkurA=nmkurA.replace("tandaDan", "&");
						nmkurA=nmkurA.replace("tandaGb", "_");
						String sksttA = st.nextToken();
						String smsttA = st.nextToken();
						String statusSelected = st.nextToken();
						if(statusSelected.equalsIgnoreCase("selected")) {
							%>
							<input name="krklm" type="hidden" value="<%=idkurA %>__<%=nmkurA %>__<%=sksttA %>__<%=smsttA %>" /><%=nmkurA %> (<%=sksttA %> sks/<%=smsttA %> sms)
							<%	
						}
						else {
							//ignore
						}
					}
					else {
						out.print("K.O. belum ditentukan");
					}
				}
				%>	
					</td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Kode Pos</td><td align="center" width="250px"><input type="number" value="<%=Checker.pnn(v_possp) %>" name="v_possp" style="width:98%" /></td><td align="left" style="padding-left:2px">Pembimbing Akademik</td>
					<td align="center">
					<%
					if(vListDsn==null || vListDsn.size()<1) {
						out.print("LIST DOSEN KOSONG");
					}
					else {
						ListIterator liDsn = vListDsn.listIterator();
					
						if(!objMhs) {
							
							%>
								<select name="dosenPA">
								<option value="null">Pilih Dosen</option>
							<%
							while(liDsn.hasNext()) {
								String brs = (String)liDsn.next();
								StringTokenizer std = new StringTokenizer(brs,"||");
								String id_obj=std.nextToken();
								String npmds=std.nextToken();
								String nmmds=std.nextToken();
									
								if(!Checker.isStringNullOrEmpty(curPa)) {
									std = new StringTokenizer(curPa,"|");
									String npmPa = std.nextToken();
									String nmmPa = std.nextToken();
									if(npmds.equalsIgnoreCase(npmPa)) {
							%>
									<option value="<%=brs %>" selected=selected><%=nmmds %> (<%=npmds %>)</option>
							<%	
									}
									else {
							%>
									<option value="<%=brs %>"><%=nmmds %> (<%=npmds %>)</option>
							<%			
									}
								}
								else {
							%>
									<option value="<%=brs %>"><%=nmmds %> (<%=npmds %>)</option>
							<%		
								}
							}
						
					%>	
						</select>
					<%	
						}
						else {
							while(liDsn.hasNext()) {
								String brs = (String)liDsn.next();
								StringTokenizer std = new StringTokenizer(brs,"||");
								String id_obj=std.nextToken();
								String npmds=std.nextToken();
								String nmmds=std.nextToken();
									
								if(!Checker.isStringNullOrEmpty(curPa)) {
									std = new StringTokenizer(curPa,"|");
									String npmPa = std.nextToken();
									String nmmPa = std.nextToken();
									if(npmds.equalsIgnoreCase(npmPa)) {
							%>
									<input name="dosenPA" type="hidden" value="<%=brs %>" /><%=nmmds %> (<%=npmds %>)
							<%	
									}
									else {
							//ignore
									}
								}
								else {
									out.print("P.A. belum ditentukan");
								}
							}	
						}
					}
					%>
					</td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Kota</td><td align="center" width="250px"><input type="text" value="<%=Checker.pnn(v_kotsp) %>" name="v_kotsp" style="width:98%" /></td><td align="left" style="padding-left:2px">Sks Transfer</td><td align="center"></td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Negara</td><td align="center" width="250px">
					<select name="v_negsp" style="width:98%">
			<%
							negara = Constants.getListNegara();
							st = new StringTokenizer(negara,",");
							boolean match = false;
							while(st.hasMoreTokens()) {
								String ctry = st.nextToken();
								if(ctry.equalsIgnoreCase(v_negsp)) {
									match = true;
		%>
									<option value="<%=ctry.toUpperCase() %>" selected><%=ctry.toUpperCase() %></option>
		<%	
								}
								else {
		%>
									<option value="<%=ctry.toUpperCase() %>"><%=ctry.toUpperCase() %></option>
		<%
								}
								if(!match) {
									%>
									<option value="<%=Constants.getDefaultCountry().toUpperCase() %>" selected><%=Constants.getDefaultCountry().toUpperCase() %></option>
									<%
								}
							}
		%>
						</select>
					</td><td align="left" style="padding-left:2px">Tgl Lulus</td><td align="center"></td>
				</tr>
			</table>
			<br />
			<%
			if(validUsr.isUsrAllowTo("editObject", v_npmhs, v_obj_lvl)) {
			%>
			<table align="center" border="1" style="background:#d9e1e5;color:#000;width:700px">	
				
				<tr>
					<td align="center" colspan="2" bgcolor="#369" style="color:#fff" padding-left="2px"><b>EDIT TIPE OBJEK</b></td><td align="center" colspan="2" bgcolor="#369" style="color:#fff" padding-left="2px"><b>ALAMAT RUMAH</b></td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Tipe Objek </td>
					<td align="center" width="250px">
					<select name="v_id_obj_v_obj_lvl">
					<%
					boolean first = true;
					st = new StringTokenizer(listTipeObj,"$");
					while(st.hasMoreTokens()) {
						String idObj = "null";
						String kdpstObj = "null";
						String objName = "null";
						String objDesc = "null";
						String objLvl = "null";
						String acl = "null";
						String al = "null";
						String dvalue = "null";
						String nicknm = "null";
					
						if(first) {
							first = false;
							%>
							<option value="<%=idObj%>,<%=objLvl %>" selected>N/A</option>
							<%
						}
						else {
							idObj = st.nextToken();
							kdpstObj = st.nextToken();
							objName = st.nextToken();
							objDesc = st.nextToken();
							objLvl = st.nextToken();
							acl = st.nextToken();
							al = st.nextToken();
							dvalue = st.nextToken();
							nicknm = st.nextToken();
							if(idObj.equalsIgnoreCase(v_id_obj)) {
					%>
							<option value="<%=idObj%>,<%=objLvl %>" selected><%=objDesc %></option>
					<%	
							}
							else {
						%>
							<option value="<%=idObj%>,<%=objLvl %>"><%=objDesc %></option>
						<%	
							}
						}
					}
					%>
					</select>
					</td>
					<td align="center" style="padding-left:2px" colspan="2" rowspan="6"><textarea name="v_almrm" style="width:98%;height:95%;valign:middle"><%=Checker.pnn(v_almrm) %></textarea></td>
				</tr>
			</table>
			<%
			}
			else {
				//bagian operator yg ngga bisa edit object
				boolean obj_matched = false;
				String idObj_ObjLvl = "null,null";
				boolean first = true;
				st = new StringTokenizer(listTipeObj,"$");
				while(st.hasMoreTokens()) {
					String idObj = "null";
					String kdpstObj = "null";
					String objName = "null";
					String objDesc = "null";
					String objLvl = "null";
					String acl = "null";
					String al = "null";
					String dvalue = "null";
					String nicknm = "null";
					
					if(first) {
						first = false;
					}
					else {
						idObj = st.nextToken();
						kdpstObj = st.nextToken();
						objName = st.nextToken();
						objDesc = st.nextToken();
						objLvl = st.nextToken();
						acl = st.nextToken();
						al = st.nextToken();
						dvalue = st.nextToken();
						nicknm = st.nextToken();
						if(idObj.equalsIgnoreCase(v_id_obj)) {
							obj_matched = true;
							idObj_ObjLvl = idObj+","+objLvl;
						}
					}
				}
					%>
					<input type="hidden" name="v_id_obj_v_obj_lvl" value="<%=idObj_ObjLvl%>">
			<%	
			}
			%>
			</br>
			<input type="submit" value="Update Data" style="width:200px;height:50px" formtarget="_self"/>
			</form>
			</p>
			
		<%	
			}
		}
		%>

		

</body>
</html>