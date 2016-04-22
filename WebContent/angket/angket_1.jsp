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
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"> </script>
<script type="text/javascript">


$(document).ready(function()
{
	$("#somebutton").click(function()	
	{
		//$.get('go.uploadFile2', function(responseText) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
            //             // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
        $.post('go.validateForm?somebutton_String_Opt='+document.getElementById('somebutton').value, $('#formUpload1').serialize(), function(data) {
        	document.getElementById('div_msg').style.visibility='visible';
        	$('#div_msg').html(); 
        	$('#div_msg').html(data); 
        });
	});

});			
</script>

<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");

%>
</head>
<body onload="location.href='#'">
<%
%>
<div id="header">
</div>
<div class="colmask fullpage">
	<div class="col1">
		<!-- Column 1 start -->
		<br />
		<h2 align="center">FORM INFORMASI DATA TERKINI</h2>
		<%
		if(true){
		//	if(validUsr.isAllowTo("editDataPribadi")>0) {
		%>
			<form name="formUpload1" id="formUpload1" method="post">
			<input type="hidden" value="<%=validUsr.getNpm() %>" name="Npmhs_String_Wajib" />
			<input type="hidden" value="update.angket1" name="StringfwdPageIfValid_String_Opt" />
		
			<p>
			<table align="center" border="1" style="background:#d9e1e5;color:#000;width:700px">	
				
				<tr>
					<td align="center" colspan="2" bgcolor="#369" style="color:#fff" padding-left="2px"><b>DATA PRIBADI</b></td><td align="center" colspan="2" bgcolor="#369" style="color:#fff" padding-left="2px"><b>ALAMAT RUMAH</b></td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Nama </td><td align="left" width="250px" style="font-weight:bold"><%=Checker.pnn(validUsr.getNmmhs(validUsr.getNpm())) %></td><td align="center" style="padding-left:2px" colspan="2" rowspan="3"><textarea name="Alamat-Rumah_String_Wajib" style="width:98%;height:95%;valign:middle" placeholder="Wajib diisi"></textarea></td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Gender</td><td align="center" width="250px">
					<%
					//kdjek
					String[]gender = Constants.getOptionGender();
					//System.out.println("v_kdjek = "+v_kdjek);
					%>
					<select name="Gender_Huruf_Wajib" style="width:98%">
						<%
						
						for(int i=0;i<gender.length;i++) {
							String opt = gender[i];
							StringTokenizer st = new StringTokenizer(opt);
							String val = st.nextToken();
							String ket = st.nextToken();
							if(val.equalsIgnoreCase("----")) {
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
					<select name="Agama_Huruf_Wajib" style="width:98%">
						<%
						
						for(int i=0;i<agama.length;i++) {
							String opt = agama[i];
							if(opt.equalsIgnoreCase("-----")) {
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
					<select name="Status_Huruf_Wajib" style="width:98%">
						<%
						for(int i=0;i<nikah.length;i++) {
							String opt = nikah[i];
							if(opt.equalsIgnoreCase("=====")) {
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
					<td align="left" width="100px" style="padding-left:2px">Kota </td><td align="center" width="250px"><input type="text" placeholder="Wajib diisi" name="Kota-Tempat-Tinggal_String_Wajib" style="width:98%" /></td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Email</td><td align="center" width="250px"><input type="Text" placeholder="Wajib diisi" name="Email_Email_Wajib" style="width:98%" /></td><td align="left" width="100px" style="padding-left:2px">Kode Pos </td><td align="center" width="250px"><input type="number"  name="Kode-Pos_String_Opt" style="width:98%" /></td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">No HP</td><td align="center" width="250px"><input type="Text" placeholder="Wajib diisi" name="No-Hape_Hape_Wajib" style="width:98%" /></td><td align="left" width="100px" style="padding-left:2px">Telp Rumah</td><td align="center" width="250px"><input type="number" placeholder="Wajib diisi" name="Telp-Rumah_Telp_Wajib" style="width:98%" /></td>
				</tr>
				
				<tr>
					<td align="center" colspan="2" bgcolor="#369" style="color:#fff" padding-left="2px"><b>INFO KELAHIRAN</b></td><td align="center" colspan="2" bgcolor="#369" style="color:#fff" padding-left="2px"><b>ASAL SMA</b></td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Kota</td><td align="center" width="250px"><input type="text" placeholder="Wajib diisi" name="Kota-Kelahiran_String_Wajib" style="width:98%" /></td><td align="left" width="100px" style="padding-left:2px">Nama SMA</td><td align="center" width="250px"><input type="text" placeholder="Wajib diisi" name="Nama-SMA_String_Wajib" style="width:98%" /></td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Negara</td>
					<td align="center" width="250px">
						<select name="Negara-Kelahiran_Huruf_Wajib" style="width:98%">
			<%
							String negara = Constants.getListNegara();
							StringTokenizer st = new StringTokenizer(negara,",");
							 
							while(st.hasMoreTokens()) {
								String ctry = st.nextToken();
								if(ctry.equalsIgnoreCase("INDONESIA")) {
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
					<td align="left" width="100px" style="padding-left:2px">Kota</td><td align="center" width="250px"><input type="text" placeholder="Wajib diisi" name="Kota-SMA_String_Wajib" style="width:98%" /></td>
				</tr>
				<tr>
					<td align="left" width="100px" style="padding-left:2px">Tgl Lahir</td><td align="center" width="250px"><input type="text" placeholder="tgl/bln/tahun" name="Tgl-Lahir_Date_Wajib" style="width:98%" /></td><td align="left" width="100px" style="padding-left:2px">Tahun Lulus</td><td align="center" width="250px"><input type="text" placeholder="Wajib diisi" name="Tahun-Lulus_Int_Wajib" style="width:98%" /></td>
				</tr>
			</table>
			</p>
			<p>
			<div id="btn" style="text-align:center;height:45px;visibility:visible" ><input type="button" id="somebutton" name="somebutton" value="Update Data" style="width:700px;height:35px;font-weght:bold;margin:5px"/></div>
			</form>
			</p>
			
		<%	
			//}
		}
		%>
<br/>
		<table align="center">
		<tr>
		<td>
		<div id="div_msg" style="font-style:italic;color:red;text-align:left;background-color:#d9e1e5;width:700px;height:100%;visibility:hidden" ></div>
		</td></tr>
		</table>

</body>
</html>