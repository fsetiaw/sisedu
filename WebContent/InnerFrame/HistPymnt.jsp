<!DOCTYPE html>
<head>
<%@page import="beans.setting.Constants"%>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");

Vector v= null; 
//update 1215 vector dipisah jdi msg getAttribut bak dan profle
//v = (Vector) request.getAttribute("v_bak");
//ListIterator li = v.listIterator();
//Vector v_profile = (Vector)li.next();
//Vector v_bak = (Vector)li.next();
/*
*/

Vector v_bak = (Vector) request.getAttribute("v_bak");
/*
String v_id_obj = (String)request.getAttribute("v_id_obj");
String v_nmmhs=(String)request.getAttribute("v_nmmhs");
String v_npmhs=(String)request.getAttribute("v_npmhs");
String v_nimhs=(String)request.getAttribute("v_nimhs");
String v_obj_lvl=(String)request.getAttribute("v_obj_lvl");
String v_kdpst=(String)request.getAttribute("v_kdpst");
String v_kdjen=(String)request.getAttribute("v_kdjen");
String v_smawl=(String)request.getAttribute("v_smawl");
String v_aspti=(String)request.getAttribute("v_aspti");
String v_aspst=(String)request.getAttribute("v_aspst");
String v_btstu=(String)request.getAttribute("v_btstu");
String v_kdjek=(String)request.getAttribute("v_kdjek");
String v_nmpek=(String)request.getAttribute("v_nmpek");
String v_ptpek=(String)request.getAttribute("v_ptpek");
String v_stmhs=(String)request.getAttribute("v_stmhs");
String v_stpid=(String)request.getAttribute("v_stpid");
*/

%>

</head>
<body>
<div id="header">
<%@ include file="innerMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		
		<%
		if(validUsr.isAllowTo("ksr")>0||validUsr.isAllowTo("ksrpmb")>0) {
		//if(false) {	
		%>
		<p align="center">
		<form action="get.histPymnt">
			<table align="center" border="1" style="background:#d9e1e5;color:#fff;width:700px">	
				<tr>
					<td align="center" colspan="5" bgcolor="#369"><b>FORM BAYARAN</b>
						<input type="hidden" value="preview" name="form_type" />
						<input type="hidden" value="<%=v_id_obj%>" name="id_obj" />
						<input type="hidden" name="obj_lvl" value="<%=v_obj_lvl %>" />
						<input type="hidden" name="kdpst" value="<%=v_kdpst %>" /></td>
						<%
						java.util.Date date= new java.util.Date();
						String tmp = ""+(new Timestamp(date.getTime()));
						StringTokenizer st = new StringTokenizer(tmp);
						while(st.hasMoreTokens()) {
							tmp = st.nextToken();
						}
						tmp = tmp.replaceAll(":","");
						tmp = tmp.replaceAll("\\.","");
						%>
				</tr>
				
				<tr>
					<td>
						<table align="center" border="1" style="color:#000;width:690px">
							<!-- tr>
								<td colspan="2"><label>TIPE TRANSAKSI</label></td>
								<td colspan="2"><input name="tipeTransaksi" value="cash" type="radio">&nbsp&nbsp&nbsp<label>TUNAI</label>
								<br/><input name="tipeTransaksi" value="bank" type="radio" checked = "checked">&nbsp&nbsp&nbsp<label>SETORAN BANK</label>
								</td>
							</tr  -->	
							<tr>
								<td colspan="2"><label>KODE TRANSAKSI</label></td>
								<td colspan="2"><%=tmp%><input type="hidden" value="<%=tmp%>" name="stamps" readonly style="width:95%"/></td>
							</tr>
							<tr>
								<td width="20%"><label>NAMA</label></td><td width="30%" align="center"><%=v_nmmhs%><input type="hidden" value="<%=v_nmmhs%>" name="inp_nmm" readonly style="width:95%"/>
								<td width="20%"><label>NPM</label></td><td width="30%" align="center"><%=v_npmhs %><input type="hidden" value="<%=v_npmhs %>" name="inp_npm" readonly style="width:95%" /></td>
							</tr>
							<tr>
								<td><label>KETERANGAN</label></td>
								<%
								if(validUsr.isAllowTo("ksrpmb")>0) {
								%>
								<td colspan="3" align="left"><%=Constants.getListKeterKasirPmb() %><input type="hidden" name="inp_keter" value="<%=Constants.getListKeterKasirPmb() %>" readonly style="width:98%"/></td>
								<%	
								}
								else {
								%>
								<td colspan="3" align="center"><input type="text" name="inp_keter" value="TUITION FEE" required style="width:98%"/></td>
								<%	
								}
								%>
							</tr>
							<tr>
								<td width="20%"><label>PENYETOR</label></td><td width="30%" align="center"><input type="text" value="<%=v_nmmhs%>" name="inp_payee" style="width:95%"/></td>
								<td width="20%"><label>JUMLAH</label></td><td width="30%" align="center"><input type="number" name="inp_amnt" required style="width:95%" /></td>
							</tr>
							<tr>
								<td style="color:#fff" bgcolor="#369" colspan="4" align="center"><i>KETERANGAN TAMBAHAN PEMBAYARAN VIA BANK (NON TUNAI)</i></td>
							</tr>
							<tr>
								<td width="20%"><label>TGL TRANSFER</label></td><td width="30%" align="center"><input type="date" value="" name="inp_tgtrs" style="width:95%"/></td>
								<td width="20%"><label>ACCNT BANK </label></td>
								<td width="30%" align="center"><select name="account" style="width:95%" />
								<%
  									String[]bank = beans.setting.Constants.getBankAccount();
  									for (int i = 0; i < bank.length; i++) { 
  						    			String bnk = bank[i];
  									%>
  										<option value="<%=bnk%>"><%=bnk.toUpperCase() %></option>
  									<%    
  									}
  									%>
									</select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center"><input type="submit" value="Input Bayaran" formtarget="_self" style="width:90%"/></td>
				</tr>
			</table>	
		</form>
		</p>
		<br />
		<br />
		<%
		}
		ListIterator li = v_bak.listIterator();
		if(li.hasNext()) {
			double total = 0;
			if(validUsr.isAllowTo("voidKui")>0) {
			%>
			
			<table align="center" border="1" style="background:#d9e1e5;color:#fff;width:750px">
			<%
			} 
			else {
			%>		
			<table align="center" border="1" style="background:#d9e1e5;color:#fff;width:700px">	
			<%
			}
			if(validUsr.isAllowTo("voidKui")>0) {
			%>
				<tr>
					<td align="center" colspan="8" bgcolor="#369"><b>RIWAYAT BAYARAN</b></td>
				</tr>
			<%
			}
			else {
				if(validUsr.getNpm().equalsIgnoreCase(v_npmhs)|| validUsr.isUsrAllowTo("vbak", v_npmhs, v_obj_lvl)) {
			%>
				<tr>
					<td align="center" colspan="6" bgcolor="#369"><b>RIWAYAT BAYARAN</b></td>
				</tr>
			<%		
				}
				else {	
			%>
				<tr>
					<td align="center" colspan="7" bgcolor="#369"><b>RIWAYAT BAYARAN</b></td>
				</tr>
			<%
				}
			}
			if(validUsr.isAllowTo("voidKui")>0) {
				%>	
        		<tr>
        			<td align="center" bgcolor="#369"><b>BATAL</b><td align="center" bgcolor="#369"><b>NO KUI</b></td><td align="center" bgcolor="#369"><b>KUI KODE</b></td><td align="center" bgcolor="#369"><b>AKUN</b></td><td align="center" bgcolor="#369"><b>TGL KUI / TRANS</b></td><td align="center" bgcolor="#369"><b>KETERANGAN</b></td><td align="center" bgcolor="#369"><b>JUMLAH</b></td><td align="center" bgcolor="#369"><b></b></td>
        		</tr>
        	<%		
			}
			else {
			%>	
        		<tr>
        			<td align="center" bgcolor="#369"><b>NO KUI</b></td><td align="center" bgcolor="#369"><b>KUI KODE</b></td><td align="center" bgcolor="#369"><b>AKUN</b></td><td align="center" bgcolor="#369"><b>TGL KUI / TRANS</b></td><td align="center" bgcolor="#369"><b>KETERANGAN</b></td><td align="center" bgcolor="#369"><b>JUMLAH</b></td>
        			<%
        			if(!validUsr.getNpm().equalsIgnoreCase(v_npmhs)&& validUsr.isAllowTo("noDownloadKuiMhs")<0) {
        			%>	
        			<td align="center" bgcolor="#369"><b></b></td>
        			<%
        			}
        			%>	
        		</tr>
        	<%	
			}
			do {
				
				String kuiid = (String)li.next();
				String norut = (String)li.next();
				String tgkui = (String)li.next();
				String tgtrs = (String)li.next();
				String keter = (String)li.next();
				String payee = (String)li.next();
				String amont = (String)li.next();
				String pymtp = (String)li.next();
				String noacc = (String)li.next();
				String opnpm = (String)li.next();
				String opnmm = (String)li.next();
				String setor = (String)li.next();
				String nonpm = (String)li.next();
				String voidd = (String)li.next();
				String nokod = (String)li.next();
				if(!Boolean.valueOf(voidd).booleanValue()) { //if not void
					
					if((keter.equalsIgnoreCase(Constants.getListKeterKasirPmb())&&validUsr.isAllowTo("ksrpmb")>0)||validUsr.isAllowTo("ksr")>0||validUsr.getNpm().equalsIgnoreCase(v_npmhs)||validUsr.isUsrAllowTo("vbak", v_npmhs, v_obj_lvl)) {
						total = total + Double.valueOf(amont).doubleValue();
		%>
				<tr>
        		<%
        				if(validUsr.isAllowTo("voidKui")>0) {
        		%>	
        			<td align="center">
        				<form action="prevVoidKui.jsp">
        					<input type="hidden" value="<%=v_id_obj%>" name="id_obj" />
							<input type="hidden" name="obj_lvl" value="<%=v_obj_lvl %>" />
							<input type="hidden" name="kdpst" value="<%=v_kdpst %>" />
		 	   				<input type="hidden" value="<%=v_npmhs %>" name="npm" /><input type="hidden" value="<%=v_nimhs %>" name="nim" /><input type="hidden" value="<%=v_nmmhs %>" name="nmm" />
        					<input type="hidden" value="<%=kuiid %>" name="kuiid" /><input type="hidden" value="<%=pymtp %>" name="pymtp" /><input type="hidden" value="<%=noacc %>" name="noacc" />
        					<input type="hidden" value="<%=keter %>" name="keter" /><input type="hidden" value="<%=opnpm %>" name="opnpm" /><input type="hidden" value="<%=opnmm %>" name="opnmm" />
        					<input type="hidden" value="<%=setor %>" name="setor" /><input type="hidden" value="<%=nonpm %>" name="nonpm" /><input type="hidden" value="<%=voidd %>" name="voidd" />
							<input type="hidden" value="<%=tgtrs %>" name="tgtrs" /><input type="hidden" value="<%=validUsr.getDbSchema() %>" name="schema" />
        					<input type="hidden" value="<%=norut %>" name="norut"/><input type="hidden" value="<%=tgkui %>" name="tgkui"/><input type="hidden" value="<%=NumberFormater.indoNumberFormat(amont) %>" name="amont"/>
        					<input type="hidden" value="<%=nokod %>" name="nokod"/><input type="hidden" value="<%=payee %>" name="payee"/>
        					<input type="hidden" value="payment" name="cmd"/>
        					<input type="submit" value="VOID" style="color:red;font-weight:bold" /></font></td>
        				</form>
        		<%	
        				}
    			%>	
        			<td align="center"><font color="#000"><%=norut %></font></td> 
        			<td align="center"><font color="#000"><%=nokod %></font></td>
        			<td align="center"><font color="#000"><%=noacc.toUpperCase() %></font></td>
        			<td align="center"><font color="#000">
        			<%
        				if(tgkui.equalsIgnoreCase(tgtrs)) {
        					out.print(tgkui);
        				}
        				else {
        					out.print(tgkui+" / "+tgtrs);
        				}
        			%></font></td>
        			<td><font color="#000"><%=keter.toUpperCase() %></font></td>
        			<td align="right"><font color="#000"><%=NumberFormater.indoNumberFormat(amont) %></font></td>
        			<%
        			if(!validUsr.getNpm().equalsIgnoreCase(v_npmhs) && validUsr.isAllowTo("noDownloadKuiMhs")<=0) {
        			%>
        			<td align="center" bgcolor="#369">
        				<form action="file.downloadKuitansi">
		 	   				<input type="hidden" value="<%=v_npmhs %>" name="npm" /><input type="hidden" value="<%=v_nimhs %>" name="nim" /><input type="hidden" value="<%=v_nmmhs %>" name="nmm" />
        					<input type="hidden" value="<%=kuiid %>" name="kuiid" /><input type="hidden" value="<%=pymtp %>" name="pymtp" /><input type="hidden" value="<%=noacc %>" name="noacc" />
        					<input type="hidden" value="<%=keter %>" name="keter" /><input type="hidden" value="<%=opnpm %>" name="opnpm" /><input type="hidden" value="<%=opnmm %>" name="opnmm" />
        					<input type="hidden" value="<%=setor %>" name="setor" /><input type="hidden" value="<%=nonpm %>" name="nonpm" /><input type="hidden" value="<%=voidd %>" name="voidd" />
							<input type="hidden" value="<%=tgtrs %>" name="tgtrs" /><input type="hidden" value="<%=validUsr.getDbSchema() %>" name="schema" />
        					<input type="hidden" value="<%=norut %>" name="norut"/><input type="hidden" value="<%=tgkui %>" name="tgkui"/><input type="hidden" value="<%=NumberFormater.indoNumberFormat(amont) %>" name="amont"/>
        					<input type="hidden" value="<%=nokod %>" name="nokod"/><input type="hidden" value="<%=payee %>" name="payee"/>
        					<input type="submit" value="Download"  style="font-weight:bold" />
        				</form>		
        			</td>	
        			<%
        			}
        			%>
        		</tr>	
		<!--  a href="foo.download">download foo.exe -->
		<%
					}//end if ksr-pmb
				}		
			}
			while(li.hasNext());
        	%>
        		<tr>
        		<%
        		if(validUsr.isAllowTo("voidKui")>0) {
        		%>
        			<td align="center" colspan="6" bgcolor="#369"><b>TOTAL</b></td>
        		<%
        		}
        		else {
        		%>	
        			<td align="center" colspan="5" bgcolor="#369"><b>TOTAL</b></td>
        		<%
        		}
        		%>	
        			<td align="right" bgcolor="#369"><b><%=NumberFormater.indoNumberFormat(""+total) %></b></td>
        		<%
        		if(!validUsr.getNpm().equalsIgnoreCase(v_npmhs)&& validUsr.isAllowTo("noDownloadKuiMhs")<0) {
        		%>	
        			<td align="center" bgcolor="#369"><b></b></td>
        		<%
        		}
        		%>	
        		</tr>
			</table>
	       
	        <%
		}
		else {
			System.out.println("kosong juga");
		}
		
		//pesan belum ada riwayat bayara
		if(v_bak!=null & v_bak.size()<1) {
		%>
		<div style="font-style:bold;font-size:2em;text-align:center">BELUM ADA RIWAYAT PEMBAYARAN</div>
		<%
		}
		
		/*
			bagian upload data pembayaran
		*/
		Vector vUb = validUsr.getScopeUpd7des2012("uploadBayaran");
		if(vUb!=null && vUb.size()>0 ) {
			
		%>
		<br/>
		<br/>
		<form action="preUploadPymnt.jsp">
			<input type="hidden" name="cmd" value="payment" />
			<input type="hidden" name="id_obj" value="<%=v_id_obj%>" />
			<input type="hidden" name="nmm" value="<%=v_nmmhs%>" />
			<input type="hidden" name="npm" value="<%=v_npmhs %>" />
			<input type="hidden" name="obj_lvl" value="<%=v_obj_lvl %>" />
			<input type="hidden" name="kdpst" value="<%=v_kdpst %>" />
			<table align="center" border="1" style="background:#d9e1e5;color:#fff;width:220px">
			<tr>
				<td align="center" colspan="7" bgcolor="#369"><b><input type="submit" value="UPLOAD PEMBAYARAN BARU" /></b></td>
			
		</form>
		
		
		<%
		}
		
		
		//pesan kepada mahasiswa untuk kontak bak bila ada kekurangan
		if(validUsr.getNpm().equalsIgnoreCase(v_npmhs)) {
		%>
		<div style="font-style:italic;font-size:.9em;text-align:center">Kami dalam tahap migrasi, harap hubungi bag. keuangan bila total pembayaran tidak sesuai agar dapat mendapat prioritas update</div>
		<%
		}
		%>
		
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>