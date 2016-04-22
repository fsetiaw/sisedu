<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="java.util.Vector" %>
	<%@ page import="java.util.ListIterator" %>
<meta http-equiv="Content-Language" content="en-us">
<title>Selamat Datang di Situs Resmi Universitas Satyagama</title>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/2colsLayout/screen.css" media="screen" />
	<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/breakIframe/breakIframe.js"></script>



<script src="<%=Constants.getRootWeb() %>/js/jquery.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="<%=Constants.getRootWeb() %>/css/popup/style.css">
<link rel="stylesheet" type="text/css" media="all" href="<%=Constants.getRootWeb() %>/css/popup/general.css">
<script src="<%=Constants.getRootWeb() %>/css/popup/popup.js" type="text/javascript"></script>
<style>
<!--
.style2 {font-weight: bold; text-align: left; font-style: italic; ; color: #333399; font-family: Verdana, Arial, Helvetica, sans-serif;}
.style13 {font-size: 12px; font-family: Geneva, Arial, Helvetica, sans-serif; }
.style14 {
	font-size: 16pt;
	font-weight: bold;
	color: #996600;
}
.style23 {font-size: 12pt; color: #0033FF; }
.style20 {font-size: 12px}
.style24 {font-style: italic}
-->
</style>
<script language="JavaScript" src="mm_menu.js"></script>
</head>

<body topmargin="0" style="scrollbar-face-color:blue; scrollbar-highlight-color:yellow; scrollbar-3dlight-color:lightgreen; scrollbar-darkshadow-color:purple; scrollbar-shadow-color:white; scrollbar-arrow-color:cyan; scrollbar-track-color:lightskyblue" onLoad="FP_preloadImgs(/*url*/'button/button6B.jpg', /*url*/'button/button6C.jpg', /*url*/'button/button7A.jpg', /*url*/'button/button7B.jpg', /*url*/'button/button7D.jpg', /*url*/'button/button7E.jpg', /*url*/'button/button83.jpg', /*url*/'button/button84.jpg', /*url*/'button/button89.jpg', /*url*/'button/button8A.jpg', /*url*/'button/button8F.jpg', /*url*/'button/button90.jpg', /*url*/'button/button92.jpg', /*url*/'button/button93.jpg', /*url*/'button/button95.jpg', /*url*/'button/button96.jpg', /*url*/'button/button98.jpg', /*url*/'button/button99.jpg', /*url*/'button/button9B.jpg', /*url*/'button/button9C.jpg', /*url*/'button/button9E.jpg', /*url*/'button/button9F.jpg', /*url*/'button/buttonA1.jpg', /*url*/'button/buttonA2.jpg', /*url*/'button/buttonA4.jpg', /*url*/'button/buttonA5.jpg', /*url*/'button/buttonA7.jpg', /*url*/'button/buttonA8.jpg', /*url*/'button/buttonAA.jpg', /*url*/'button/buttonAB.jpg', /*url*/'buttonAD.jpg', /*url*/'buttonAE.jpg', /*url*/'button/buttonB0.jpg', /*url*/'button/buttonB1.jpg', /*url*/'button/button5.jpg', /*url*/'button/button6.jpg')">
<script language="JavaScript1.2">mmLoadMenus();</script>
<center>
              <div id="popupContact">
		<a id="popupContactClose">x</a>
		  <h1>HUBUNGI KAMI</h1>
		    <p id="contactArea">
		      <form class="form" action="http://118.96.57.189:8182/ToUnivSatyagama/go.getMsgTamu">
		        <p class="name">
	                  <input type="text" name="name" id="name" placeholder="isi nama lengkap" />
			  <label for="name">Nama</label>
		        </p>
		        <p class="email">
			  <input type="text" name="email" id="email" placeholder="mail@example.com" />
			  <label for="email">Email</label>
		        </p>
                        <p>
                          <input type="radio" name="kepada" value="non-pasca">Program Sarjana & Diploma<br/>
                          <input type="radio" name="kepada value="pasca">Program Pasca Sarjana<br/>
                        </p>
		        <p class="text">
			  <textarea name="text" placeholder="pertanyaan / informasi yang dibutuhkan, terima kasih telah menghubungi kami" /></textarea>
		        </p>
                        <p class="submit">
			  <input type="submit" value="Kirim Pesan" />
		        </p>
	              </form>
		    </p>
	       </div>
	       <div id="backgroundPopup"></div>
</center>
</body>
</html>