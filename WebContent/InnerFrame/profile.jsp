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
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/css/bootstrap.min.css" media="screen" />
<script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
<script src="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>

<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String listKurAndSelected = ""+request.getParameter("listKurAndSelected");
Vector v= null;  
String curPa = (String)request.getAttribute("curPa");
Vector vHisBea = (Vector) session.getAttribute("vHisBea");
session.removeAttribute("vHisBea");
if(curPa!=null && !Checker.isStringNullOrEmpty(curPa)) {
	StringTokenizer std = new StringTokenizer(curPa,"|");
	String npmPa = std.nextToken();
	String nmmPa = std.nextToken();
	curPa = nmmPa+" ("+npmPa+")";
	if(curPa.equalsIgnoreCase("null (null)")) {
		curPa = "Belum Ditentukan";
	}
}
else {
	curPa = "Belum Ditentukan";
}
//System.out.println("11curPacurPa="+curPa);
request.removeAttribute("curPa");
%>
<!--  style type="text/css">
img.imgInsetShadowGray { padding:10px; -moz-box-shadow:inset 0 0 10px #000000; -webkit-box-shadow:inset 0 0 10px #000000; box-shadow:inset 0 0 10px #000000; }

img {
    max-width: 100%;
    max-height: 100%;

}

.portrait {
    height: 320px;
    width: 120px;
    display: block;
    margin-left: auto;
    margin-right: auto;
}

.landscape {
    height: 30px;
    width: 80px;
}

.square {
    height: 75px;
    width: 75px;
}
</style -->

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
		<div class="container" align="center">
     <%
			
			String pic_file = request.getParameter("picfile");
			if(pic_file==null || Checker.isStringNullOrEmpty(pic_file)) {
				pic_file = new String(v_npmhs+".jpg");
			}
			%>    
  			<img src="show.passPhoto?picfile=<%=pic_file %>&npmhs=<%=v_npmhs %>" class="img-thumbnail" alt="Cinque Terre" width="204" height="136"> 
			<br/>
    		<%
			//if(validUsr.isAllowTo("editDataPribadi")>0) {
			if(false) { //pinda pake tab baru	
		%>				
			<form action="get.profile" align="center">
				<input type="hidden" value="<%=listKurAndSelected %>" name="listKurAndSelected"/>
				<input type="hidden" value="<%=v_npmhs %>" name="npm"/><input type="hidden" value="<%=v_nimhs %>" name="nim"/>
				<input type="hidden" value="<%=v_kdpst %>" name="kdpst" /><input type="hidden" value="<%=v_id_obj %>" name="id_obj" />
				<input type="hidden" value="<%=v_obj_lvl %>" name="obj_lvl" /><input type="hidden" value="edit" name="cmd" />
				<input type="submit" value="Edit Data" style="width:200px;height:50px;text-align:center" formtarget="_self"/>
			</form>
		<%
			}
			if(validUsr.isAllowTo("editProfileKeu")>0) {
			%>				
			<form action="get.getProfileBeasiswa" align="center">
				<input type="hidden" value="<%=listKurAndSelected %>" name="listKurAndSelected"/>
				<input type="hidden" value="<%=v_npmhs %>" name="npm"/><input type="hidden" value="<%=v_nimhs %>" name="nim"/>
				<input type="hidden" value="<%=v_kdpst %>" name="kdpst" /><input type="hidden" value="<%=v_id_obj %>" name="id_obj" />
				<input type="hidden" value="<%=v_obj_lvl %>" name="obj_lvl" /><input type="hidden" value="editProfileKeu" name="cmd" />
				<input type="submit" value="Edit Profile Keuangan" style="width:200px;height:50px;text-align:center" formtarget="_self"/>
			</form><br/>
			<%
			}
			
		%>
		</div>
		<!--   /div  pindah ke ln 86--> 
		<%
		if(v.size()>0){
			
				//System.out.println("profile.jsp objek level = "+v_obj_lvl);
		%>
		<table align="center" border="1" style="background:#d9e1e5;color:#000;width:700px">	
				
				<tr>
					<td align="center" colspan="4" bgcolor="#369" style="color:#fff" padding-left="2px"><b>DATA PRIBADI</b></td>
				</tr>
				<tr>	
					<td align="left" width="100px" style="padding-left:2px">Nama </td><td align="center" width="250px"><%=Checker.pnn(v_nmmhs) %></td>
					<td align="left" width="100px" style="padding-left:2px">Agama </td><td align="center" width="250px"><%=Checker.pnn(v_agama) %></td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Gender</td><td align="center" width="250px"><%=Converter.convertKdjek(v_kdjek) %></td>
					<td align="left" width="100px" style="padding-left:2px">Status</td><td align="center" width="250px"><%=v_stmhs %></td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Email</td><td align="center" width="250px"><%=Checker.pnn(v_email) %></td>
					<td align="left" width="100px" style="padding-left:2px">No HP</td><td align="center" width="250px"><%=Checker.pnn(v_nohpe) %></td>
				</tr>
				<tr>
					<td align="center" width="100px" style="padding-left:2px;color:#fff" colspan="2" bgcolor="#369" ><b>DATA KELAHIRAN</b></td><td align="center" width="100px" style="padding-left:2px;color:#fff" colspan="2" bgcolor="#369" ><b>ALAMAT RUMAH</b></td>
				</tr>
				<tr>
					<td align="center" colspan="2" rowspan="4" style="padding-left:2px;valign:middle" ><%=Checker.pnn(v_tglhr)+"<br/>" %><%=Checker.pnn(v_tplhr)+", " %><%=Checker.pnn(v_neglh)%></td><td align="center" style="padding-left:2px;valign:middle" colspan="2" rowspan="4"><%=Checker.pnn(v_almrm) %><%="<br/>"+Checker.pnn(v_kotrm) %><%="-"+Checker.pnn(v_posrm)+"<br/>" %><%="Tel : "+Checker.pnn(v_telrm) %></td>
				</tr>
				<tr>		
				</tr>
				<tr>
				</tr>
				<tr>
				</tr>
			</table>
		</p>
		<br/>
			
		<p>
			<table align="center" border="1" style="background:#d9e1e5;color:#000;width:700px">		
				<tr>
					<td align="center" colspan="4" bgcolor="#369" style="color:#fff" padding-left="2px"><b>DATA KEMAHASISWAAN</b></td>
				</tr>
				<tr>	
					<td align="left" width="100px" style="padding-left:2px">NPM</td><td align="center" width="250px"><%=Checker.pnn(v_npmhs)%> </td>
					<td align="left" width="100px" style="padding-left:2px">NIM</td><td align="center" width="250px"><%=Checker.pnn(v_nimhs)%></td>
				</tr>
				<tr>	
					<td align="left" width="100px" style="padding-left:2px">Status</td><td align="center" width="250px"><%=Checker.pnn(v_stmhs)%> </td>
					<td align="left" width="100px" style="padding-left:2px">Angkatan</td><td align="center" width="250px">
					<%
					String angk=Converter.convertThsmsKeterOnly(v_smawl);
					
					out.print(Checker.pnn(angk));
					%>
					</td>
				</tr>
				<tr>	
					<td align="left" width="100px" style="padding-left:2px">Batas-Studi</td><td align="center" width="250px"><%=Checker.pnn(v_btstu)%></td>
					<td align="left" width="100px" style="padding-left:2px">Tipe | Shift</td><td align="center" width="250px"><%=Converter.convertStpid(Checker.pnn(v_stpid))%> | <%=Checker.pnn(Getter.getKodeKonversiShift(v_shift))%></td>
				</tr>
				<tr>	
					<td align="left" width="100px" style="padding-left:2px">Pembimbing Akademik</td><td align="center" width="250px"><%=Checker.pnn(curPa)%></td>
					<td align="left" width="100px" style="padding-left:2px">Kurikulum</td><td align="center" width="250px">
					<%
					StringTokenizer st = new StringTokenizer(listKurAndSelected,"__");
					boolean selected = false;
					while(st.hasMoreTokens()&&!selected) {
						String idkurA = st.nextToken();
						String nmkurA = st.nextToken();
						nmkurA=nmkurA.replace("tandaKoma", ",");
						nmkurA=nmkurA.replace("tandaDan", "&");
						nmkurA=nmkurA.replace("tandaGb", "_");
						String sksttA = st.nextToken();
						String smsttA = st.nextToken();
						String statusSelected = st.nextToken();
						if(statusSelected.equalsIgnoreCase("selected")) {
							selected = true;
							out.print(nmkurA);
						}
					}
					%>
					
					</td>
				</tr>
				<tr>	
					<td align="left" width="100px" style="padding-left:2px">Sks Transfer</td><td align="center" width="250px"></td>
					<td align="left" width="100px" style="padding-left:2px">Sks Semester</td><td align="center" width="250px"></td>
				</tr>
				<tr>	
					<td align="left" width="100px" style="padding-left:2px">Sks total</td><td align="center" width="250px"></td>
					<td align="left" width="100px" style="padding-left:2px">Tgl Lulus</td><td align="center" width="250px"></td>
				</tr>
		</table>
			</p>
			<br />
			<p>
			<table align="center" border="1" style="background:#d9e1e5;color:#000;width:700px">	
				
				<tr>
					<td align="center" colspan="2" bgcolor="#369" style="color:#fff" padding-left="2px"><b>DATA PEKERJAAN</b></td><td align="center" colspan="2" bgcolor="#369" style="color:#fff" padding-left="2px"><b>ALAMAT KANTOR</b></td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Nama Kantor</td><td align="center" width="250px"><%=Checker.pnn(v_nmpek) %><%=Checker.pnn(v_ptpek) %></td><td align="center" style="padding-left:2px" colspan="2" rowspan="4"><%=Checker.pnn(v_almkt) %><br/><%=Checker.pnn(v_kotkt) %><%="-"+Checker.pnn(v_poskt) %><br/><%="Tel : "+Checker.pnn(v_telkt) %></td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Jabatan</td><td align="center" width="250px"><%=Checker.pnn(v_jbtkt) %></td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Bidang Usaha</td><td align="center" width="250px"><%=Checker.pnn(v_bidkt) %></td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Jenis </td><td align="center" width="250px"><%=Checker.pnn(v_jenkt) %></td>
				</tr>
			</table>
			</p>
			<br />
			<%
			if(vHisBea!=null && vHisBea.size()>0) {
				ListIterator lib = vHisBea.listIterator();
				int norut=0;
				
			%>
			<p>
			<table align="center" border="1" style="background:#d9e1e5;color:#000;width:700px">	
				
				<tr>
					<td align="center" colspan="5" bgcolor="#369" style="color:#fff" padding-left="2px"><b>DATA BEASISWA</b></td>
				</tr>
				<tr>
					<td align="center" width="25px" style="padding-left:2px;color:#fff" bgcolor="#369">No.</td><td align="center" width="50px" bgcolor="#369" style="color:#fff">Tahun-sms</td><td align="center" width="175px" bgcolor="#369" style="color:#fff">Jenis Beasiswa</td><td align="center" width="250px" bgcolor="#369" style="color:#fff">Paket Beasiswa</td><td align="center" width="250px" bgcolor="#369" style="color:#fff">Besaran</td>
				</tr>
				<%
				while(lib.hasNext()) {
					norut++;
					String brs = (String)lib.next();
					//li.add(thsms+"`"+kdpst+"`"+npmhs+"`"+namaPaket+"`"+namaBank+"`"+noRekBank+"`"+nmPemilik+"`"+jenisBea);
					StringTokenizer st1 = new StringTokenizer(brs,"`");
					//System.out.println("barisan="+brs);
					String thsms_ = st1.nextToken();
					String kdpst_ = st1.nextToken();
					String npmhs_ = st1.nextToken();
					String namaPaket_ = st1.nextToken();
					String namaBank_ = st1.nextToken();
					String noRekBank_ = st1.nextToken();
					String nmPemilik_ = st1.nextToken();
					String jenisBea_ = st1.nextToken();
					String jumDana = "0";
					String unit = null;
					String nmmSponsor = null;
					String jenisSponsor = null;
					String syarat = null;
					String aktif = null;
					
					if(st1.hasMoreTokens()) {
						jumDana = st1.nextToken();
						unit = st1.nextToken();
						nmmSponsor = st1.nextToken();
						jenisSponsor = st1.nextToken();
						syarat = st1.nextToken();
						aktif = st1.nextToken();
					}
				%>
				<tr>
					<td align="left" width="25px" style="padding-left:2px"><%=norut %></td><td align="center" width="50px"><%=thsms_ %></td><td align="center" width="175px"><%=jenisBea_ %></td><td align="center" width="250px"><%=namaPaket_ %></td><td align="center" width="250px"><%=NumberFormater.indoNumberFormat(jumDana)+"/"+unit %></td>
				</tr>
				<tr>
					<td align="left" colspan="5">
					<%
					out.print("Nama Sponsor: "+nmmSponsor+" ("+jenisSponsor+")<br/>");
					out.print("Keterangan :<br/>");
					out.print(syarat);
					%></td>
				</tr>
				<%
				}
				%>
			</table>
			</p>
			<%
			}
			%>
		<%	
		}
		%>
</body>
</html>