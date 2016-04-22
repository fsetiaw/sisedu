<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
	
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.sistem.*" %>
<%@ page import="java.text.SimpleDateFormat" %>

<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");

	Vector v= null; 
	String tknError = request.getParameter("tknError");
%>
<style type="text/css">
img.imgInsetShadowGray { padding:10px; -moz-box-shadow:inset 0 0 10px #000000; -webkit-box-shadow:inset 0 0 10px #000000; box-shadow:inset 0 0 10px #000000; }
</style>

</head>
<body onload="location.href='#'">
<div id="header">
<%@ include file="innerMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<!-- tracer study page akan berisikan track record bagi mahasiswa dan civitas mengenai
			heregistrasi,kelulusan, tempat pekerjaan dll
			Mhs dapat mengedit - data pekerjaan mereka sendiri
			pegaewai dapt mengedit data spt kelulusan dan heregistrasi
		 -->
		 <%
	
		Vector vInfoIja = (Vector)request.getAttribute("vInfoIja");
		boolean editable = Boolean.valueOf((String)request.getAttribute("editable")).booleanValue();
		boolean cetakable = Boolean.valueOf((String)request.getAttribute("cetakable")).booleanValue();
		String availNoIja = (String)request.getAttribute("availNoIja");
		String availNoNirl = (String)request.getAttribute("availNoNirl");
		String nmpst = (String)request.getAttribute("nmpst");
		String nmjen = (String)request.getAttribute("nmjen");
		
		String id = "";
 		String nonirl="";
		String noija ="";
		String noskr ="";
 		String tglre ="";
 		String nmmija="";
 		String nimija="";
 		String tptglhr="";
 		String tgctk ="";
 		String tgctkstr="";
 		String status="";
 		String note ="";
 		String pemeriksa="";
 		String pencetak="";
 		String diserahkan="";
 		String penerima="";
 		String tgterima="";
 		String editable_ = "";
 		String cetakable_ = "";
		String tplhr = "";
		String tglhr ="";
		
		boolean allowEditIja = false;
		
		//if sudah ada data ijazah
 		if(vInfoIja!=null && vInfoIja.size()>0) {
 			
 			ListIterator li = vInfoIja.listIterator();
 			id = (String)li.next();
 	 		nonirl= (String)li.next();
 			noija = (String)li.next();
 	 		noskr = (String)li.next();
 	 		tglre = (String)li.next();
 	 		nmmija= (String)li.next();
 	 		nimija= (String)li.next();
 	 		tptglhr= (String)li.next();
 	 		StringTokenizer st = new StringTokenizer(tptglhr,",");
 	 		if(st.countTokens()>1) {
 	 			tplhr = st.nextToken();
 	 			tglhr = st.nextToken();
 	 		}
 	 		tgctk = (String)li.next();
 	 		tgctkstr= (String)li.next();
 	 		status= (String)li.next();
 	 		note = (String)li.next();
 	 		pemeriksa= (String)li.next();
 	 		pencetak= (String)li.next();
 	 		diserahkan= (String)li.next();
 	 		penerima= (String)li.next();
 	 		tgterima= (String)li.next();
 	 		editable_ = (String)li.next();
 	 		cetakable_ = (String)li.next();
 	 	
 			if(validUsr.isUsrAllowTo("allowEditIjazah", v_npmhs, v_obj_lvl)) {
 				if(Boolean.valueOf(editable_).booleanValue()) {
 					allowEditIja = true;
 				}
 				else {
 					allowEditIja = false;
 				}
 			}	
 		}
 		else {
 			//belum ada data jadi belum pernah dicetak
 			if(validUsr.isUsrAllowTo("allowEditIjazah", v_npmhs, v_obj_lvl)) {
 				allowEditIja = true;
 			}	
 		}
 		
		%>
		<form action="goto.validasiCetakIjazah1" method="post">
		<input type="hidden" name="msg" value=<%=msg %> />
		<input type="hidden" name="id_obj" value=<%=v_id_obj %> />
		<input type="hidden" name="nmm" value=<%=v_nmmhs %> />
		<input type="hidden" name="npm" value=<%=v_npmhs %> />
		<input type="hidden" name="kdpst" value=<%=v_kdpst %> />
		<input type="hidden" name="obj_lvl" value=<%=v_obj_lvl %> />
		
		<table align="center" border="1" style="background:#d9e1e5;color:#000;width:800px">	
			<tr>
				<td align="center" colspan="4" bgcolor="#369" style="color:#fff" padding-left="2px"><b>INFO IJAZAH</b></td>
			</tr>
			<tr>	
				<td align="left" width="150px" style="padding-left:2px">Nama </td><td align="center" width="250px">
				<%
				if(allowEditIja) {
					if(Checker.isStringNullOrEmpty(nmmija)) {
					%>
						<input type="text" name="namaIja" value="<%=Tool.capFirstLetterInWord(v_nmmhs) %>" style="width:98%;text-align:center"/>
					<%
					}
					else {
					%>
						<input type="text" name="namaIja" value="<%=nmmija %>" />
					<%
					}
				}
				else {
					if(Checker.isStringNullOrEmpty(nmmija)) {
						out.print(v_nmmhs);
					}
					else {
						out.print(nmmija);
					}
				}
				%>
				</td>
				<td align="left" width="150px" style="padding-left:2px">Gelar</td><td align="center" width="250px">
				<%
				if(allowEditIja) {
					if(Checker.isStringNullOrEmpty(v_kdpst)) {
						out.print("ERROR");
					}
					else {
				%>	
						<input type="text" name="namaJen" value="<%=Tool.capFirstLetterInWord(nmjen) %> <%=Tool.capFirstLetterInWord(nmpst) %>" style="width:98%;text-align:center"/>
				<% 
					}
				}
				else {
					if(Checker.isStringNullOrEmpty(v_kdpst)) {
						out.print("error");
					}
					else {
						out.print(Tool.capFirstLetterInWord(nmjen)+" "+Tool.capFirstLetterInWord(nmpst));
					}
				}
				%>
				</td>
			</tr>
			<tr>	
				<td align="left" width="150px" style="padding-left:2px">N P M </td><td align="center" width="250px">
				<%
				if(Checker.isStringNullOrEmpty(v_npmhs)) {
					out.print("ERROR");
				}
				else {
					out.print(v_npmhs);
					%>
					<input type="hidden" name=npmhs value=<%=v_npmhs %> />
					<%
				}
				%>
				</td>
				<td align="left" width="150px" style="padding-left:2px">N I M</td><td align="center" width="250px">
				<%

				if(Checker.isStringNullOrEmpty(nimija)) {
					out.print(v_nimhs);
					%>
					<input type="hidden" name=nimija value=<%=v_nimhs %> />
					<%
				}
				else {
					out.print(nimija);
					%>
					<input type="hidden" name=nimija value=<%=nimija %> />
					<%
				}
				%>
				</td>
			</tr>
			<tr>
				<td align="left" width="150px" style="padding-left:2px">Tempat Lahir </td><td align="center" width="250px">
				<%
				if(allowEditIja) {
					if(Checker.isStringNullOrEmpty(tplhr)) {
					%>
						<input type="text" name="tplhrIja" value="<%=Tool.capFirstLetterInWord(v_tplhr) %>" style="width:98%;text-align:center"/>
					<%
					}
					else {
					%>
						<input type="text" name="tplhrIja" value="<%=tplhr %>" />
					<%
					}
				}
				else {
					if(Checker.isStringNullOrEmpty(tplhr)) {
						out.print(v_tplhr);
					}
					else {
						out.print(tplhr);
					}
				}
				%>
				</td>
				<td align="left" width="150px" style="padding-left:2px">Tgl Lahir (tgl-bln-thn)</td><td align="center" width="250px">
				<%
				if(allowEditIja) {
					if(Checker.isStringNullOrEmpty(tglhr)) {
						
				
						out.print(DateFormater.createInputDate("0","0","0"));
						%>
						<!--  input type="text" name=tgl valu -->
						<!-- input type="text" name="tglhrIja" value="<%=Converter.convertFormatTanggalKeFormatDeskriptif(v_tglhr) %>" style="width:98%;text-align:center"/ -->
					<%
					}
					else {
						System.out.println("tglhr = "+v_tglhr);
						FormatDateInUse fdiu = new FormatDateInUse(v_tglhr);
						String tglFormated = fdiu.getFormatedStringDate();
						//System.out.println(fdiu.getTgl()+"/"+fdiu.getNamaBulan()+"/"+fdiu.getThn());
						
						out.print(DateFormater.createInputDate(fdiu.getTgl(),fdiu.getBln(),fdiu.getThn()));
							
						%>	
						<!--  input type="text" name="tglhrIja" value="<%=Converter.convertFormatTanggalKeFormatDeskriptif(v_tglhr) %>" / -->
					<%
					}
				}
				else {
					if(Checker.isStringNullOrEmpty(tglhr)) {
						out.print(Converter.convertFormatTanggalKeFormatDeskriptif(v_tglhr));
					}
					else {
						out.print(tglhr);
					}
				}
				%>
				</td>
			</tr>
			<tr>
				<td align="left" width="150px" style="padding-left:2px">No Ijazah </td><td align="center" width="250px">
				<%
				
				if(Checker.isStringNullOrEmpty(noija)) {
				%>
					<%=availNoIja %><input type="hidden" name="noSeriIja" value="<%=availNoIja %>" style="width:98%;text-align:center"/>
				<%
				}
				else {
				%>
					<%=noija %><input type="hidden" name="noSeriIja" value="<%=noija %>" />
				<%
				}

				%>
				</td>
				<td align="left" width="150px" style="padding-left:2px">No NIRL</td><td align="center" width="250px">
				<%
				if(Checker.isStringNullOrEmpty(nonirl)) {
				%>
					<%=availNoNirl %><input type="hidden" name="noSeriNirl" value="<%=availNoNirl %>" style="width:98%;text-align:center"/>
				<%
				}
				else {
				%>
					<%=nonirl %><input type="hidden" name="noSeriNirl" value="<%=nonirl %>" />
				<%
				}
				%>
				</td>
			</tr>
			<tr>
				<td align="left" width="150px" style="padding-left:2px">No SK Rektor </td><td align="center" width="250px">
				<%
				if(allowEditIja) {
					if(Checker.isStringNullOrEmpty(noskr)) {
						%>
							<input type="text" name="noskr" value="" style="width:98%;text-align:center"/>
						<%
					}
					else {
						%>
							<input type="text" name="noskr" value="" />
						<%
					}
				}
				else {
					if(Checker.isStringNullOrEmpty(noskr)) {
						out.print("N/A");
					}
					else {
						out.print(noskr);
					}
				}
				%>
				</td>
				<td align="left" width="150px" style="padding-left:2px">Tgl SK Rektor</td><td align="center" width="250px">
				<%
				if(allowEditIja) {
					if(Checker.isStringNullOrEmpty(tglre)) {
						%>
							<input type="text" name="tglre" value="" style="width:98%;text-align:center"/>
						<%
					}
					else {
						%>
							<input type="text" name="tglre" value="" />
						<%
					}
				}
				else {
					if(Checker.isStringNullOrEmpty(tglre)) {
						out.print("N/A");
					}
					else {
						out.print(tglre);
					}
				}
				%>
				</td>
			</tr>
			<!--  tr>
				<td align="left" width="150px" style="padding-left:2px">Tgl Ujian Akhir </td><td align="center" width="250px"><%="" %></td>
				<td align="left" width="150px" style="padding-left:2px">Tgl Wisuda</td><td align="center" width="250px"><%="" %></td>
			</tr>
			<tr>
				<td align="left" width="150px" style="padding-left:2px">Dicetak oleh </td><td align="center" width="250px"><%="" %></td>
				<td align="left" width="150px" style="padding-left:2px">Tgl Cetak</td><td align="center" width="250px"><%="" %></td>
			</tr>
			<tr>
				<td align="left" width="150px" style="padding-left:2px">Diperiksa oleh </td><td align="center" width="250px"><%="" %></td>
				<td align="left" width="150px" style="padding-left:2px">Diserahkan oleh</td><td align="center" width="250px"><%="" %></td>
			</tr>
			<tr>
				<td align="left" width="150px" style="padding-left:2px">Diterima oleh</td><td align="center" width="250px"><%="" %></td>
				<td align="left" width="150px" style="padding-left:2px">Tgl terima</td><td align="center" width="250px"><%="" %></td>
			</tr>
			<tr>
				<td align="left" width="150px" style="padding-left:2px" rowspan="3" >Note </td><td align="center" rowspan="3" colspan="3"><%="" %></td>
			</tr -->
			<tr>
				<%
				if(allowEditIja) {
				%>
				<td colspan="4" bgcolor="#369" style="text-align:right"><input type=submit value="Lanjut" style="text-align:center;width:30%;height:35px;font-weight:bold"></td>
				<%
				}
				else {
				%>
					<td colspan="4" bgcolor="#369" style="text-align:center;color:#fff;font-weight:bold">IJAZAH SUDAH DICETAK - DATA TIDAK DAPAT DI EDIT</td>
				<%	
				}
				%>			
			</tr>
		</table>
		<%
		if(tknError!=null) {
			StringTokenizer st_err = new StringTokenizer(tknError,",");
			%>
			<table align="center" border="0" style="width:800px;text-align:left">
			<%
			while(st_err.hasMoreTokens()) {
				%>
				<tr>
					<td>*
				<%
				out.print(st_err.nextToken()+"<br />");
				%>
					</td>
				</tr>	
				<%
			}
			%>
			</table>
			<%
		}
		%>
		
		</form>	
	</div>
</div>		
</body>
</html>