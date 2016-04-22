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
	Vector vDwn = validUsr.getScopeUpd7des2012("hasDownloadMenu");
	Vector v_cf = validUsr.getScopeUpd7des2012("hasEditMkKurikulum");
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
		<input type="hidden" name="StringfwdPageIfValid_String_Opt" value="update.addJenisBea" />
		<input type="hidden" name="AsalForm_String_Opt" value="formAddJenisBeasiswa" />
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:600px">
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center" colspan="2"><label><B>FORM TAMBAH JENIS BEASISWA</B> </label></td>
        			</tr>
        			<tr>
        				<td style="background:#d9e1e5;color:#369;text-align:left;width:150px"><label><B>NAMA JENIS BEASISWA BARU</B> </label></td>
        				<td style="color:#000;text-align:center;width:450px"><input type="text" name="Nama-Jenis_String_Wajib"  style="width:98%;height:25px" /></td>
        			</tr>
        			<tr>
        				<td style="background:#d9e1e5;color:#369;text-align:left;width:150px"><label><B>BERLAKU PADA KAMPUS</B> </label></td>
        				<td style="color:#000;text-align:center;width:450px"><input type="text" name="Berlaku-pada-Kampus_String_Wajib"  style="width:98%;height:25px" /></td>
        			</tr>
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center" colspan="2"><input type="button" id="somebutton" value="INPUT DATA" style="width:50%;height:25px;margin:5px;"/></td>
        			</tr>
        </table>
        </form>	
        <table align="center">
		<tr><td>
		<div id="div_msg" style="font-style:italic;color:red;text-align:left;background-color:#d9e1e5;width:600px;height:100%;visibility:hidden;margin:0px 0 0 0px;" ></div>
		</td></tr>
		</table>		
		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>