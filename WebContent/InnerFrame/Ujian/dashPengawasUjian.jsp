<!DOCTYPE html>
<html>
<head>

<title>Insert title here</title>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.sistem.*" %>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String idJadwalTest = request.getParameter("idJadwalTest");
String stat = request.getParameter("stat");
String reusable = request.getParameter("reusable");
System.out.println("2.stat = "+stat);
System.out.println("dasboard pengawas idJadwalTest = "+idJadwalTest);
//request.setAttribute("idJadwalTest", idJadwalTest);
String catatan = (String)request.getAttribute("catatan");
%>
<script type="text/javascript">
<!--
function getConfirmation(){
	var x;
	var r=confirm("!!!! PERHATIAN !!!! \n\n Dengan Meng-klik 'OK' maka Ujian Akan Berakhir \n Harap diisi pada kotak 'note' bila perlu, \n Setelah Meng-klik 'OK' anda tidak dapat kembali ke halaman 'MENU KONTROL UJIAN' \n\n LANJUT TUTUP UJIAN ?");
	if (r==true){
	  x="stop";
	}
	else{
		x="batal";
	}
	document.getElementById("demo").value=x;
}

//-->
</script>
</head>
<body onload="location.href='#'">
<div id="header">
<%@ include file="../MhsSection/MhsInnerMenu.jsp"%>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<p style="font-weight:bold">
			<h1 style="text-align:center;">MENU KONTROL UJIAN</h1>
		</p>
		<p style="text-align:center;font-size:30px;color:#369">
			<h1 style="text-align:center;color:#369">STATUS UJIAN :
			<%
			if(!Checker.isStringNullOrEmpty(stat)) {
				out.print("  "+stat);
			}
			%> 
			</h1>
		</p>
		<p>
		<form action="process.requestPengawasUjian">
			<input type="hidden" name="demo" id="demo" />
			<input type="hidden" name="idJadwalTest" value="<%=idJadwalTest %>" />
			<table style="width:99%">
				
				<tr>
					<%
			 		if(stat!=null && stat.equalsIgnoreCase("SESUAI JADWAL")) {
			 		%>
			 		<td style="width:33.3%;text-align:center"></td>
			 		<td style="width:33.3%;text-align:center"><input type="image" src="<%=Constants.getRootWeb()+"/images/button/start5.jpg"%>" alt="Submit" width="180" height="180" name="command" value="play"/></td>
			 		<td style="width:33.3%;text-align:center"></td>
			 		<%	
			 		}
			 		else {
			 			if(stat!=null && stat.equalsIgnoreCase("SEDANG BERJALAN")) {
			 		%>
			 		<td style="width:33.3%;text-align:center"><input type="image"  src="<%=Constants.getRootWeb()+"/images/button/pause5.jpg"%>" alt="Submit" width="180" height="180" name="command" value="pause"/></td>
			 		<td style="width:33.3%;text-align:center"></td>
			 		<td style="width:33.3%;text-align:center"><input type="image"  src="<%=Constants.getRootWeb()+"/images/button/stop5.jpg"%>" alt="Submit" width="180" height="180" name="command" value="stop"  onclick="getConfirmation()" /></td>
			 		<%	
			 			}
			 			else{
			 				if(stat!=null && stat.equalsIgnoreCase("SEDANG REHAT")) {
						 		%>
					<td style="width:33.3%;text-align:center"><input type="image"  src="<%=Constants.getRootWeb()+"/images/button/start5.jpg"%>" alt="Submit" width="180" height="180" name="command" value="replay"/></td>
					<td style="width:33.3%;text-align:center"></td>
					<td style="width:33.3%;text-align:center"><input type="image"  src="<%=Constants.getRootWeb()+"/images/button/stop5.jpg"%>" alt="Submit" width="180" height="180" name="command" value="stop"  onclick="getConfirmation()" /></td>
						 		<%	
						 	}
			 				else {
			 					if(stat!=null && stat.equalsIgnoreCase("REUSABLE")) {
			 					%>
						 		<td style="width:33.3%;text-align:center"></td>
						 		<td style="width:33.3%;text-align:center"><input type="image" src="<%=Constants.getRootWeb()+"/images/button/start5.jpg"%>" alt="Submit" width="180" height="180" name="command" value="play"/></td>
						 		<td style="width:33.3%;text-align:center"></td>
						 		<%
			 					}
			 				}
			 			}
			 		}
			 		%>
			 	</tr>
				<tr>
					<%
			 		if(stat!=null && stat.equalsIgnoreCase("SESUAI JADWAL")) {
			 		%>
					<td style="width:33.3%;text-align:center"></td>
			 		<td style="width:33.3%;text-align:center">MULAI UJIAN</td>
			 		<td style="width:33.3%;text-align:center"></td>
					<%	
			 		}
			 		else {
			 			if(stat!=null && stat.equalsIgnoreCase("SEDANG BERJALAN")) {
			 		%>
			 		<td style="width:33.3%;text-align:center">REHAT / PAUSE UJIAN</td>
			 		<td style="width:33.3%;text-align:center"></td>
			 		<td style="width:33.3%;text-align:center">UJIAN SELESAI</td>
			 		<%	
			 			}
			 			else{
			 				if(stat!=null && stat.equalsIgnoreCase("SEDANG REHAT")) {
						 		%>
						 		<td style="width:33.3%;text-align:center">LANJUTKAN UJIAN</td>
						 		<td style="width:33.3%;text-align:center"></td>
						 		<td style="width:33.3%;text-align:center">UJIAN SELESAI</td>
						 		<%	
						 	}
			 				else {
			 					if(stat!=null && stat.equalsIgnoreCase("REUSABLE")) {
			 					//jika status ujian selesai tapi reusable
			 					%>
								<td style="width:33.3%;text-align:center"></td>
						 		<td style="width:33.3%;text-align:center">MULAI UJIAN</td>
						 		<td style="width:33.3%;text-align:center"></td>
								<%	
			 					}
			 				}	
			 			}
			 		}
			 		%>
				</tr>
				<tr>
					<td colspan="3">NOTE :</td>
				</tr>
			</table>
			<br/>
			<%
			if(Checker.isStringNullOrEmpty(catatan)) {
				catatan="";
			}
			%>
			<textarea style="width:99%" rows="10" name="note"><%=catatan %></textarea>
			<!--  input id="save" type="button" onClick="if (pass = prompt('Enter your password')) { $('#password').val(pass); }" -->
			<input type="hidden" name="stat" value="<%=stat%>">
			</form>	
		</p>
		<!-- Column 1 end   -->
		<br />
	</div>
</div>	
</body>
</html>