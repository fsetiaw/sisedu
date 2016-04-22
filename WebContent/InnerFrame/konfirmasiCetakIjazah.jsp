<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
	
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String nmmija = request.getParameter("namaIja");
String nmmjen = request.getParameter("namaJen");
String nimija = request.getParameter("nimija");
String tplhrIja = request.getParameter("tplhrIja");
String tgl = request.getParameter("tgl");
String bln = request.getParameter("bln");
String thn = request.getParameter("thn");
String tglhrIja = thn+"-"+bln+"-"+tgl;
String noSeriIja = request.getParameter("noSeriIja");
String noSeriNirl = request.getParameter("noSeriNirl");
String noskr = request.getParameter("noskr");
String tglre = request.getParameter("tglre");



Vector v= null; 

%>


</head>
<body onload="location.href='#'">
<div id="header">
<%@ include file="innerMenu.jsp"%>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->

		<h1>Verifikasi Data Pada Ijazah</h1>
		<h3>
		Nama  	:	<%=nmmija %></br>
		Gelar 	:	<%=nmmjen %></br>
		NIM		:	<%=nimija %></br>
		Tempat & Tanggal Lahir	:	<%=tplhrIja %>, <%=Converter.convertFormatTanggalKeFormatDeskriptif(tglhrIja) %></br>
		No Ijazah	:	<%=noSeriIja %></br>
		No Nirl	:	<%=noSeriNirl %></br>
		No SK Rektor	:	<%=noskr %></br>
		Tgl SK Rektor 	:	<%=tglre %></br>
		</br>
		</br>
		Apakah Data Diatas Sudah Betul?
		</h3>
		</br>

		
		<form action="go.updateDataIjazah" method="post">
		<input type="hidden" name="nmmija" value="<%=nmmija%>"/>
		<input type="hidden" name="gelarija" value="<%=nmmjen%>"/>
		<input type="hidden" name="nimija" value="<%=nimija%>"/>
		<input type="hidden" name="tplhrija" value="<%=tplhrIja%>"/>
		<input type="hidden" name="tgl" value="<%=tgl%>"/>
		<input type="hidden" name="bln" value="<%=bln%>"/>
		<input type="hidden" name="thn" value="<%=thn%>"/>
		<!--  input type="hidden" name="tglhrija" value="<%=tglhrIja%>"/ -->
		<input type="hidden" name="noseriija" value="<%=noSeriIja%>"/>
		<input type="hidden" name="noserinirl" value="<%=noSeriNirl%>"/>
		
		<input type="hidden" name="id_obj" value="<%=v_id_obj%>"/>
		<input type="hidden" name="nmm" value="<%=v_nmmhs%>"/>
		<input type="hidden" name="npm" value="<%=v_npmhs%>"/>
		<input type="hidden" name="kdpst" value="<%=v_kdpst%>"/>
		<input type="hidden" name="obj_lvl" value="<%=v_obj_lvl%>"/>
		<input type="hidden" name="cmd" value="percetakan"/>
		<input type="submit" value="Ya, Data sdh benar" /><br />
		</form>
		
		</br>
		<form action="go.cetakIjazah" >
		<input type="hidden" name="id_obj" value="<%=v_id_obj%>"/>
		<input type="hidden" name="nmm" value="<%=v_nmmhs%>"/>
		<input type="hidden" name="npm" value="<%=v_npmhs%>"/>
		<input type="hidden" name="kdpst" value="<%=v_kdpst%>"/>
		<input type="hidden" name="obj_lvl" value="<%=v_obj_lvl%>"/>
		<input type="hidden" name="cmd" value="percetakan"/>
		<input type="submit" value="Tidak, Kembali ke Halaman Sebelumnya" />
		</form>
		<br />
		    

		
	</div>
</div>	
</body>
</html>