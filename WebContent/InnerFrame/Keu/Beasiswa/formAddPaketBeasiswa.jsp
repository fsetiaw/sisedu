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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String tipeForm = request.getParameter("formType");
	String jenisPaket_kampusv= request.getParameter("jenisPaket_kampus");
	String scopeKampus = request.getParameter("scopeKampus");
%>

<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"> </script>
<script type="text/javascript">
$(document).ready(function()
{
	$("#somebutton").click(function()	
	{
        $.post( 'go.validateFormDb', $('#formUpload1').serialize(), function(data) {
        	document.getElementById('div_msg').style.visibility='visible';
        	$('#div_msg').html(); 
        	$('#div_msg').html(data); 
        });
	});
});	
</script>	
</head>
<body>
<div id="header">
<%@ include file="menuBeasiswa.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<br/>
		<!--  form action="update.addJenisBea" -->
		
		<form name="formUpload1" id="formUpload1">
		<input type="hidden" name="StringfwdPageIfValid_String_Opt" value="update.insPaketBea" />
		<input type="hidden" name="AsalForm_String_Opt" value="formAddPaketBeasiswa" />
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:750px">
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center" colspan="4"><label><B>FORM TAMBAH PAKET BEASISWA BARU</B> </label></td>
        			</tr>
        			<tr>
        				<td style="background:#d9e1e5;color:#369;text-align:left;width:125px" colspan="1"><label><B>NAMA PAKET</B> </label></td>
        				<td style="color:#000;text-align:center;width:250px" colspan="1"><input type="text" name="Nama-Paket_String_Wajib" value="" style="width:98%;height:25px"></td>
        				<td style="background:#d9e1e5;color:#369;text-align:left;width:125px"><label><B>JENIS BEASISWA</B> </label></td>
        				<td style="color:#000;text-align:center;width:250px">
        					<select name="Jenis-Beasiswa_String_Opt" style="width:98%;height:25px">
        					<%
        					StringTokenizer st = new StringTokenizer(jenisPaket_kampusv,"`");
        					while(st.hasMoreTokens()) {
        						String jenis = st.nextToken();
        						String scpKampus = st.nextToken();
        					%>
        						<option value="<%=jenis %>"><%=jenis %></option>
        					<%	
        					}
        					%>
        					</select>
        				</td>
        			</tr>
        			<tr>			
        				<td style="background:#d9e1e5;color:#369;text-align:left;width:125px"><label><B>JUMLAH DANA / PERIODE</B> </label></td>
        				<td style="background:#d9e1e5;color:#369;text-align:left;width:250px"><input type="text" placeholder="Rp." name="Total-Biaya_Double_Wajib" style="width:98%;height:25px"></td>
        				<td style="background:#d9e1e5;color:#369;text-align:left;width:125px"><label><B>UNIT PERIODE</B> </label></td>
        				<td style="color:#000;text-align:center;width:250px">
        					<select name="Unit-Periode_String_Opt" style="width:98%;height:25px">
        						<option value="thn">TAHUN</option>
        						<option value="sms">SEMESTER</option>
        					</select>
        				</td>
        			</tr>
        			<tr>
        				<td style="background:#d9e1e5;color:#369;text-align:left;width:125px" colspan="1"><label><B>NAMA INSTANSI</B> </label></td>
        				<td style="color:#000;text-align:center;width:250px" colspan="1"><input type="text" name="Nama-Instansi_String_Wajib" value="" style="width:98%;height:25px"></td>
        				<td style="background:#d9e1e5;color:#369;text-align:left;width:125px" colspan="1"><label><B>JENIS INSTANSI</B> </label></td>
        				<td style="color:#000;text-align:center;width:250px" colspan="1">
        					<select name="Jenis-Instansi_String_Opt" style="width:98%;height:25px">
        						<option value="PEMERINTAH">PEMERINTAH</option>
        						<option value="SWASTA LOCAL">SWASTA LOCAL</option>
        						<option value="SWASTA ASING">SWASTA ASING</option>
        						<option value="YAYASAN">YAYASAN</option>
        					</select>
						</td>
        			</tr>
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center" colspan="4"><label><B>PERSYARATAN</B> </label></td>
        			</tr>
        			<tr>			
        				<td style="background:#d9e1e5;color:#369;text-align:center;" colspan="4">
							<textarea name="Persyaratan_String_Wajib" style="width:99%" rows="20"></textarea>
						 </td>
        			</tr>
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center" colspan="4"><input type="button" id="somebutton" value="INPUT DATA" style="width:50%;height:25px;margin:5px;"/></td>
        			</tr>
        </table>
        </form>	
        <table align="center">
		<tr><td>
		<div id="div_msg" style="font-style:italic;color:red;text-align:left;background-color:#d9e1e5;width:750px;height:100%;visibility:hidden;margin:0px 0 0 0px;" ></div>
		</td></tr>
		</table>		
		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>