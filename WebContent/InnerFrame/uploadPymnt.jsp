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

<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/css/progressBar/normalize.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/css/progressBar/style.css">
<script src="<%=Constants.getRootWeb() %>/js/jquery.js" type="text/javascript"></script>
<script src="<%=Constants.getRootWeb() %>/js/modernizr.js" type="text/javascript"></script>
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");

Vector v= null;
///String tipeForm = request.getParameter("tipeForm");
//=============form 2=============================
///String namaPenyetor=request.getParameter("namaPenyetor");
///String besaran=request.getParameter("besaran");
///String tglTrans=request.getParameter("tglTrans");
///String angsuranKe=request.getParameter("angsuranKe");
///String gelombangKe=request.getParameter("gelombangKe");
//============end form2======================

///String objId = request.getParameter("objId");
///String nmm = request.getParameter("nmm");
///String npm = request.getParameter("npm");
///String kdpst = request.getParameter("kdpst");
///String obj_lvl =  request.getParameter("obj_lvl");
///System.out.println("tipeForm->"+tipeForm);
///System.out.println("nmm->"+nmm);
///System.out.println("npm->"+npm);

/*
* deklare all form variable here !!!
* SAMA HARUS DIDEKLARE DI BuktiSetoran.java
*/

String tipeForm = null;
String fwdPg=null;
//=============form 1=============================
String tglTransCash = null;
String biayaSelect1 = null;
String biayaSelect2 = null;
String biayaSelect3 = null;
String biayaSelect4 = null;
String biayaSelect5 = null;
String biayaSelect6 = null;
String biayaSelect7 = null;
String biayaSelect8 = null;
String biayaSelect9 = null;
String select1 = null;
String select2 = null;
String select3 = null;
String select4 = null;
String select5 = null;
String select6 = null;
String select7 = null;
String select8 = null;
String select9 = null;
//=============end form 1=============================
//=============form 2=============================
String namaPenyetor = null;
String besaran = null;
String tglTrans = null;
String angsuranKe = null;
String gelombangKe = null;
String biayaJaket = null;
String sumberDana = null;
//============end form2======================
//=============form 3=============================
String bppKe = null;
String besaranBpp = null;
String pendaftaranSmsKe = null;
String besaranHeregistrasi = null;
String totSks = null;
String sksSmsKe = null;
String biayaSks = null;
String biayaBinaan = null;
String biayaDkm = null;
String biayaPraktik = null;
String biayaBimbinganSkripsi = null;
String biayaUjianSkripsi = null;
String biayaSumbanganBuku = null;
String biayaJurnal = null;
String biayaIjazah = null;
String biayaWisuda = null;
String biayaKp = null; 
String biayaAdmBank = null; //untuk beasiswa s1
//String sumberDana = null;//sama form2 jadi di diklare di form 2 aja- dipake juga di form4
//=============end form 3=============================
String objId = null;
String nmm = null;
String npm = null;
String kdpst = null;
String obj_lvl = null;
//System.out.println("tipeForm->"+tipeForm);
//System.out.println("nmm->"+nmm);
//System.out.println("npm->"+npm);


String targett = Constants.getRootWeb()+"/get.histPymnt";
String fieldAndValue = (String)session.getAttribute("fieldAndValue");
//System.out.println("fieldAndValue@uploadPymnt=="+fieldAndValue);
if(fieldAndValue!=null) {
	StringTokenizer st = new StringTokenizer(fieldAndValue,"||");
%>	
	
	<%@include file="listNamaFieldFormPembayaran.jsp"%>
<%		
}

//System.out.println("fieldAndValue->"+fieldAndValue);
//session.removeAttribute("fieldAndValue");
/*
*/

if(biayaJaket==null || Checker.isStringNullOrEmpty(biayaJaket)) {
	biayaJaket = "0";
}
Vector v_bak = (Vector) request.getAttribute("v_bak");
%>


<script type="text/javascript">
	var objId="<%=objId%>";
	var nmm="<%=nmm%>";
	var npm="<%=npm%>";
	var obj_lvl="<%=obj_lvl%>";
	var kdpst="<%=kdpst%>";
	
      function fileSelected() {
        var file = document.getElementById('fileToUpload').files[0];
      
        if (file) {
          var fileSize = 0;
          if (file.size > 1024 * 1024)
            fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
          else
            fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';

          document.getElementById('fileName').innerHTML = 'Name: ' + file.name;
          document.getElementById('fileSize').innerHTML = 'Size: ' + fileSize;
          document.getElementById('fileType').innerHTML = 'Type: ' + file.type;
          document.getElementById('row').style.visibility='visible';
        }
        
      }

      function uploadFile() {
        var fd = new FormData();
        fd.append("fileToUpload", document.getElementById('fileToUpload').files[0]);
        var xhr = new XMLHttpRequest();
        xhr.upload.addEventListener("progress", uploadProgress, false);
        xhr.addEventListener("load", uploadComplete, false);
        xhr.addEventListener("error", uploadFailed, false);
        xhr.addEventListener("abort", uploadCanceled, false);
        xhr.open("POST", "go.processUploadFile");
        xhr.send(fd);
      }

      function uploadProgress(evt) {
        if (evt.lengthComputable) {
          var percentComplete = Math.round(evt.loaded * 100 / evt.total);
          document.getElementById('progressNumber').innerHTML = percentComplete.toString() + '%';
          document.getElementById('pb').style.visibility='visible';
          //$('.progress').style.visibility = "visible";;
          $('.progress').val(percentComplete.toString());
          //$('.progress-value').html(percentComplete.toString() + '%');
        }
        else {
          document.getElementById('progressNumber').innerHTML = 'unable to compute';
        }
      }

      function uploadComplete(evt) {
        /* This event is raised when the server send back a response */
        //alert(evt.target.responseText);
        <%
        	System.out.println("pos11111111");
        %>
        window.location.replace("proses.updateBuktiPembayaran?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&atMenu=riwayatBayaran");
        <%
    	//System.out.println("pos2");
    	%>
      }

      function uploadFailed(evt) {
        alert("Bukti Setoran Gagal Di Upload");
      	window.location.replace("get.histPymnt?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&atMenu=riwayatBayaran");
      }

      function uploadCanceled(evt) {
        alert("Proses Upload Dibatalkan Oleh Pengguna atau Koneksi Internet Terputus");
      	window.location.replace("get.histPymnt?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&atMenu=riwayatBayaran");
      }
    </script>

</head>

<!--  body onload="location.href='#'" -->
<body>
<div id="header">
<ul>

	<li><a href="preUploadPymnt.jsp?id_obj=<%=objId%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&atMenu=uploadPymnt&cmd=payment" >BACK <span><b style="color:#eee">---</b></span></a></li>
</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<%
		/*
		cek apa ada besaran pembayaran dari form sebelumnya, ini dipakai krn contoh pada form 2 semua field
		adalah optional jadi kalo besaran biaya pada dpp & jaket baru terdeteknya disini.
		*/
	boolean noSelection = true;	
	boolean nominalNol = false, error_bilanga = false;
	if(tipeForm.equalsIgnoreCase("form1")) {
		try {
			if(select1!=null && !Checker.isStringNullOrEmpty(select1) && noSelection) {
				noSelection = false;
			}
			if(select2!=null && !Checker.isStringNullOrEmpty(select2) && noSelection) {
				noSelection = false;
			}
			if(select3!=null && !Checker.isStringNullOrEmpty(select3) && noSelection) {
				noSelection = false;
			}
			if(select4!=null && !Checker.isStringNullOrEmpty(select4) && noSelection) {
				noSelection = false;
			}
			if(select5!=null && !Checker.isStringNullOrEmpty(select5) && noSelection) {
				noSelection = false;
			}
			if(select6!=null && !Checker.isStringNullOrEmpty(select6) && noSelection) {
				noSelection = false;
			}
			if(select7!=null && !Checker.isStringNullOrEmpty(select7) && noSelection) {
				noSelection = false;
			}
			if(select8!=null && !Checker.isStringNullOrEmpty(select8) && noSelection) {
				noSelection = false;
			}
			if(select9!=null && !Checker.isStringNullOrEmpty(select9) && noSelection) {
				noSelection = false;
			}
			
			if(biayaSelect1==null || Checker.isStringNullOrEmpty(biayaSelect1)) {
				biayaSelect1 = "0";
			}
			if(biayaSelect2==null || Checker.isStringNullOrEmpty(biayaSelect2)) {
				biayaSelect2 = "0";
			}
			if(biayaSelect3==null || Checker.isStringNullOrEmpty(biayaSelect3)) {
				biayaSelect3 = "0";
			}
			if(biayaSelect4==null || Checker.isStringNullOrEmpty(biayaSelect4)) {
				biayaSelect4 = "0";
			}
			if(biayaSelect5==null || Checker.isStringNullOrEmpty(biayaSelect5)) {
				biayaSelect5 = "0";
			}
			if(biayaSelect6==null || Checker.isStringNullOrEmpty(biayaSelect6)) {
				biayaSelect6 = "0";
			}
			if(biayaSelect7==null || Checker.isStringNullOrEmpty(biayaSelect7)) {
				biayaSelect7 = "0";
			}
			if(biayaSelect8==null || Checker.isStringNullOrEmpty(biayaSelect8)) {
				biayaSelect8 = "0";
			}
			if(biayaSelect9==null || Checker.isStringNullOrEmpty(biayaSelect9)) {
				biayaSelect9 = "0";
			}
			
			//	System.out.println("besaran)+Double.parseDouble(biayaJaket)="+besaran+","+biayaJaket);
			double test = Double.parseDouble(biayaSelect1)+Double.parseDouble(biayaSelect2)+Double.parseDouble(biayaSelect3)+Double.parseDouble(biayaSelect4)+Double.parseDouble(biayaSelect5)+Double.parseDouble(biayaSelect6)+Double.parseDouble(biayaSelect7)+Double.parseDouble(biayaSelect8)+Double.parseDouble(biayaSelect9);
			
			if(test<1) {
				nominalNol = true;
			}
			
			
		
		}	
		catch(Exception e) {
			error_bilanga = true;
		}			
	}	
	else if(tipeForm.equalsIgnoreCase("form2")) {
		try {
			if(besaran==null || Checker.isStringNullOrEmpty(besaran)) {
				besaran = "0";
			}
			if(biayaJaket==null || Checker.isStringNullOrEmpty(biayaJaket)) {
				biayaJaket = "0";
			}
			//	System.out.println("besaran)+Double.parseDouble(biayaJaket)="+besaran+","+biayaJaket);
			double test = Double.parseDouble(besaran)+Double.parseDouble(biayaJaket);
			if(test<1) {
				nominalNol = true;
			}
		}
		catch(Exception e) {
			error_bilanga = true;
		}			
	}
	else if(tipeForm.equalsIgnoreCase("form3")) {
		try {
			if(besaranBpp==null || Checker.isStringNullOrEmpty(besaranBpp)) {
				besaranBpp = "0";
			}
			if(besaranHeregistrasi==null || Checker.isStringNullOrEmpty(besaranHeregistrasi)) {
				besaranHeregistrasi = "0";
			}
			if(totSks==null || Checker.isStringNullOrEmpty(totSks)) {
				totSks = "0";
			}
			if(sksSmsKe==null || Checker.isStringNullOrEmpty(sksSmsKe)) {
				sksSmsKe = "0";
			}
			if(biayaSks==null || Checker.isStringNullOrEmpty(biayaSks)) {
				biayaSks = "0";
			}
			if(biayaDkm==null || Checker.isStringNullOrEmpty(biayaDkm)) {
				biayaDkm = "0";
			}
			if(biayaPraktik==null || Checker.isStringNullOrEmpty(biayaPraktik)) {
				biayaPraktik = "0";
			}
			if(biayaBimbinganSkripsi==null || Checker.isStringNullOrEmpty(biayaBimbinganSkripsi)) {
				biayaBimbinganSkripsi = "0";
			}
			if(biayaUjianSkripsi==null || Checker.isStringNullOrEmpty(biayaUjianSkripsi)) {
				biayaUjianSkripsi = "0";
			}
			if(biayaSumbanganBuku==null || Checker.isStringNullOrEmpty(biayaSumbanganBuku)) {
				biayaSumbanganBuku = "0";
			}
			if(biayaJurnal==null || Checker.isStringNullOrEmpty(biayaJurnal)) {
				biayaJurnal = "0";
			}
			if(biayaIjazah==null || Checker.isStringNullOrEmpty(biayaIjazah)) {
				biayaIjazah = "0";
			}
			if(biayaWisuda==null || Checker.isStringNullOrEmpty(biayaWisuda)) {
				biayaWisuda = "0";
			}
			if(biayaBinaan==null || Checker.isStringNullOrEmpty(biayaBinaan)) {
				biayaBinaan = "0";
			}
			if(biayaKp==null || Checker.isStringNullOrEmpty(biayaKp)) {
				biayaKp = "0";
			}
			if(biayaAdmBank==null || Checker.isStringNullOrEmpty(biayaAdmBank)) {
				biayaAdmBank = "0";
			}
			
			
			//	System.out.println("besaran)+Double.parseDouble(biayaJaket)="+besaran+","+biayaJaket);
			double test = Double.parseDouble(besaranBpp)+Double.parseDouble(besaranHeregistrasi)+Double.parseDouble(totSks)+Double.parseDouble(sksSmsKe)+Double.parseDouble(biayaSks)+Double.parseDouble(biayaDkm)+Double.parseDouble(biayaPraktik)+Double.parseDouble(biayaBimbinganSkripsi)+Double.parseDouble(biayaUjianSkripsi)+Double.parseDouble(biayaSumbanganBuku)+Double.parseDouble(biayaJurnal)+Double.parseDouble(biayaIjazah)+Double.parseDouble(biayaWisuda)+Double.parseDouble(biayaBinaan)+Double.parseDouble(biayaKp)+Double.parseDouble(biayaAdmBank);
			
			if(test<1) {
				nominalNol = true;
			}
		}
		catch(Exception e) {
			error_bilanga = true;
		}
	}
	else if(tipeForm.equalsIgnoreCase("form4")) {
		try {
			if(select1!=null && !Checker.isStringNullOrEmpty(select1) && noSelection) {
				noSelection = false;
			}
			if(select2!=null && !Checker.isStringNullOrEmpty(select2) && noSelection) {
				noSelection = false;
			}
			if(select3!=null && !Checker.isStringNullOrEmpty(select3) && noSelection) {
				noSelection = false;
			}
			if(select4!=null && !Checker.isStringNullOrEmpty(select4) && noSelection) {
				noSelection = false;
			}
			if(select5!=null && !Checker.isStringNullOrEmpty(select5) && noSelection) {
				noSelection = false;
			}
			if(select6!=null && !Checker.isStringNullOrEmpty(select6) && noSelection) {
				noSelection = false;
			}
			if(select7!=null && !Checker.isStringNullOrEmpty(select7) && noSelection) {
				noSelection = false;
			}
			if(select8!=null && !Checker.isStringNullOrEmpty(select8) && noSelection) {
				noSelection = false;
			}
			if(select9!=null && !Checker.isStringNullOrEmpty(select9) && noSelection) {
				noSelection = false;
			}
			
			if(biayaSelect1==null || Checker.isStringNullOrEmpty(biayaSelect1)) {
				biayaSelect1 = "0";
			}
			if(biayaSelect2==null || Checker.isStringNullOrEmpty(biayaSelect2)) {
				biayaSelect2 = "0";
			}
			if(biayaSelect3==null || Checker.isStringNullOrEmpty(biayaSelect3)) {
				biayaSelect3 = "0";
			}
			if(biayaSelect4==null || Checker.isStringNullOrEmpty(biayaSelect4)) {
				biayaSelect4 = "0";
			}
			if(biayaSelect5==null || Checker.isStringNullOrEmpty(biayaSelect5)) {
				biayaSelect5 = "0";
			}
			if(biayaSelect6==null || Checker.isStringNullOrEmpty(biayaSelect6)) {
				biayaSelect6 = "0";
			}
			if(biayaSelect7==null || Checker.isStringNullOrEmpty(biayaSelect7)) {
				biayaSelect7 = "0";
			}
			if(biayaSelect8==null || Checker.isStringNullOrEmpty(biayaSelect8)) {
				biayaSelect8 = "0";
			}
			if(biayaSelect9==null || Checker.isStringNullOrEmpty(biayaSelect9)) {
				biayaSelect9 = "0";
			}
			
			//	System.out.println("besaran)+Double.parseDouble(biayaJaket)="+besaran+","+biayaJaket);
			double test = Double.parseDouble(biayaSelect1)+Double.parseDouble(biayaSelect2)+Double.parseDouble(biayaSelect3)+Double.parseDouble(biayaSelect4)+Double.parseDouble(biayaSelect5)+Double.parseDouble(biayaSelect6)+Double.parseDouble(biayaSelect7)+Double.parseDouble(biayaSelect8)+Double.parseDouble(biayaSelect9);
			
			if(test<1) {
				nominalNol = true;
			}
			
			
		
		}	
		catch(Exception e) {
			error_bilanga = true;
		}			
	}	
	if((!nominalNol && !error_bilanga && !tipeForm.equalsIgnoreCase("form1")) || (!nominalNol && !error_bilanga && tipeForm.equalsIgnoreCase("form1") && !noSelection)) {	
	%>
	<div style="font-size:2em;font-weight:bold;text-align:center;background-color:#a6bac4;width:700px;">
	
	<%
		if(tipeForm.equalsIgnoreCase("form1")) {//form tunai
			out.print("FORM PEMBAYARAN TUNAI");
		}
		else {
			out.print("FORM UPLOAD BAYARAN (Part:2/2)");
		}
	%>
	</div>
	<div style="font-size:1.5em;background-color:#d9e1e5;width:700px;">
	<%
	/*
	// cek kalo ngga ada jumlah nominal
	*/
	
	%>
	Tanggal Transaksi&nbsp 
	<%
	if(tipeForm.equalsIgnoreCase("form1")) {//form tunai
		out.print("Tunai : <b>"+DateFormater.keteranganDate(tglTransCash));
	}
	else {
		out.print("@bank : <b>"+DateFormater.keteranganDate(tglTrans));
		//DateFormater.keteranganDate(tglTrans);
	}
	 
	%></b> <br/>
	<%
		if(!Checker.isStringNullOrEmpty(namaPenyetor)) {
	%>
		Disetorkan oleh <b><%=namaPenyetor.toUpperCase() %></b><br/>
	<%
		}

		int norutItem=0;
		if(tipeForm.equalsIgnoreCase("form2")) {
	%>
		Pembayaran atas nama <b><%=nmm %></b>, sebesar Rp. <b><%=NumberFormater.indoNumberFormat((Double.parseDouble(besaran)+Double.parseDouble(biayaJaket))+"") %> 
		<br/>
			Sumber Dana : 	
	<%
			if(sumberDana!=null  && !Checker.isStringNullOrEmpty(sumberDana)) {
				StringTokenizer stt = new StringTokenizer(sumberDana,",");
				stt.nextToken();
				out.print("("+stt.nextToken().toUpperCase()+")");		
			}
	%>	
		</b> <br/>
		untuk pembayaran : <br/>
	<%	
			if(besaran!=null && !Checker.isStringNullOrEmpty(besaran) && !besaran.equalsIgnoreCase("0")) {
				norutItem++;
	%>	
		<b><%=norutItem %>. Biaya DPP Angsuran ke-<%=angsuranKe%>, Gelombang ke-<%=gelombangKe %><br/>
	<%
			}
			if(biayaJaket!=null && !Checker.isStringNullOrEmpty(biayaJaket) && !biayaJaket.equalsIgnoreCase("0")) {
				norutItem++;
	%>	
		<b><%=norutItem %>. Jaket Almamater<br/>
	<%		
			}	
		}
		else if(tipeForm.equalsIgnoreCase("form3")) {
	%>
			Pembayaran atas nama <b><%=nmm %></b>, sebesar Rp. <b><%=NumberFormater.indoNumberFormat((Double.parseDouble(besaranBpp)+Double.parseDouble(besaranHeregistrasi)+Double.parseDouble(biayaSks)+Double.parseDouble(biayaDkm)+Double.parseDouble(biayaPraktik)+Double.parseDouble(biayaBimbinganSkripsi)+Double.parseDouble(biayaUjianSkripsi)+Double.parseDouble(biayaSumbanganBuku)+Double.parseDouble(biayaJurnal)+Double.parseDouble(biayaIjazah)+Double.parseDouble(biayaWisuda)+Double.parseDouble(biayaBinaan)+Double.parseDouble(biayaKp)+Double.parseDouble(biayaAdmBank))+"") %>
			<br/>
			Sumber Dana : 
			<%
			if(sumberDana!=null  && !Checker.isStringNullOrEmpty(sumberDana)) {
				StringTokenizer stt = new StringTokenizer(sumberDana,",");
				stt.nextToken();
				out.print("("+stt.nextToken().toUpperCase()+")");		
			}
	%>	
			</b> <br/>
			untuk pembayaran :
			<table>
	<%	
			if(besaranBpp!=null && !Checker.isStringNullOrEmpty(besaranBpp) && !besaranBpp.equalsIgnoreCase("0")) {
				norutItem++;
	%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%= "Pembayaran BPP semester ke-"+bppKe+", sebesar "%>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(besaranBpp)%>
				</td>
			</tr>
	<%
			}
			if(besaranHeregistrasi!=null && !Checker.isStringNullOrEmpty(besaranHeregistrasi) && !besaranHeregistrasi.equalsIgnoreCase("0")) {
				norutItem++;
	%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%= "Pembayaran Daftar Ulang semester ke-"+pendaftaranSmsKe+", sebesar "%>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(besaranHeregistrasi)%>
				</td>
			</tr>
<%
			}
			if(biayaSks!=null && !Checker.isStringNullOrEmpty(biayaSks) && !biayaSks.equalsIgnoreCase("0")) {
				norutItem++;
	%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%= "Pembayaran SKS semester ke-"+sksSmsKe+", total sks :"+totSks+", sebesar "%>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaSks)%>
				</td>
			</tr>
<%
			}
			if(biayaDkm!=null && !Checker.isStringNullOrEmpty(biayaDkm) && !biayaDkm.equalsIgnoreCase("0")) {
				norutItem++;
	%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%= "Dana Kemahasiswaan,  sebesar "%>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaDkm)%>
				</td>
			</tr>
<%
			}
			if(biayaPraktik!=null && !Checker.isStringNullOrEmpty(biayaPraktik) && !biayaPraktik.equalsIgnoreCase("0")) {
				norutItem++;
	%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%= "Paktikum Sebesar, sebesar "%> 
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaPraktik)%>
				</td>
			</tr>
<%
			}			
			if(biayaBimbinganSkripsi!=null && !Checker.isStringNullOrEmpty(biayaBimbinganSkripsi) && !biayaBimbinganSkripsi.equalsIgnoreCase("0")) {
				norutItem++;
	%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%= "Bimbingan Skripsi, sebesar "%>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaBimbinganSkripsi)%>
				</td>
			</tr>
<%
			}
			if(biayaBinaan!=null && !Checker.isStringNullOrEmpty(biayaBinaan) && !biayaBinaan.equalsIgnoreCase("0")) {
				norutItem++;
	%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%= "Biaya Pembinaan, sebesar "%>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaBinaan)%>
				</td>
			</tr>
<%
			}
			if(biayaUjianSkripsi!=null && !Checker.isStringNullOrEmpty(biayaUjianSkripsi) && !biayaUjianSkripsi.equalsIgnoreCase("0")) {
				norutItem++;
	%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%= "Ujian Skripsi, sebesar "%>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaUjianSkripsi)%>
				</td>
			</tr>
<%
			}	

			if(biayaSumbanganBuku!=null && !Checker.isStringNullOrEmpty(biayaSumbanganBuku) && !biayaSumbanganBuku.equalsIgnoreCase("0")) {
				norutItem++;
	%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%= "Sumbangan Buku Perpustakaan, sebesar "%>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaSumbanganBuku)%>
				</td>
			</tr>
<%
			}				

			if(biayaJurnal!=null && !Checker.isStringNullOrEmpty(biayaJurnal) && !biayaJurnal.equalsIgnoreCase("0")) {
				norutItem++;
	%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%= "Jurnal, sebesar "%>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaJurnal)%>
				</td>
			</tr>
<%
			}				

			if(biayaIjazah!=null && !Checker.isStringNullOrEmpty(biayaIjazah) && !biayaIjazah.equalsIgnoreCase("0")) {
				norutItem++;
	%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%= "Ijazah, sebesar "%>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaIjazah)%>
				</td>
			</tr>
<%
			}				

			if(biayaWisuda!=null && !Checker.isStringNullOrEmpty(biayaWisuda) && !biayaWisuda.equalsIgnoreCase("0")) {
				norutItem++;
	%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%= "Wisuda, sebesar "%>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaWisuda)%>
				</td>
			</tr>
<%
			}
			if(biayaKp!=null && !Checker.isStringNullOrEmpty(biayaKp) && !biayaKp.equalsIgnoreCase("0")) {
				norutItem++;
	%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%= "Biaya Kerja Praktek, sebesar "%>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaKp)%>
				</td>
			</tr>
<%
			}
			if(biayaAdmBank!=null && !Checker.isStringNullOrEmpty(biayaAdmBank) && !biayaAdmBank.equalsIgnoreCase("0")) {
				norutItem++;
	%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%= "Administrasi, sebesar "%>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaAdmBank)%>
				</td>
			</tr>
<%
			}
%>
		</table>
<%			
		}
		else if(tipeForm.equalsIgnoreCase("form1")) { //form tunai
			norutItem = 0;
			%>
			Pembayaran atas nama <b><%=nmm %></b>, sebesar Rp. <b><%=NumberFormater.indoNumberFormat((Double.parseDouble(biayaSelect1)+Double.parseDouble(biayaSelect2)+Double.parseDouble(biayaSelect3)+Double.parseDouble(biayaSelect4)+Double.parseDouble(biayaSelect5)+Double.parseDouble(biayaSelect6)+Double.parseDouble(biayaSelect7)+Double.parseDouble(biayaSelect8)+Double.parseDouble(biayaSelect9))+"") %></b> <br/>
			untuk pembayaran :
			<table>
	<%	
			if((biayaSelect1!=null && !Checker.isStringNullOrEmpty(biayaSelect1) && !biayaSelect1.equalsIgnoreCase("0")) && (select1!=null && !Checker.isStringNullOrEmpty(select1))) {
				norutItem++;
	%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%
					StringTokenizer st = new StringTokenizer(select1,":");
					String id = st.nextToken();
					String kdpos = st.nextToken();
					String ketpos = st.nextToken();
					ketpos = ketpos.toLowerCase();
					ketpos = Tool.capFirstLetterInWord(ketpos);
					%>
					<%=ketpos+", sebesar " %>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaSelect1)%>
				</td>
			</tr>
<%
			}
			if((biayaSelect2!=null && !Checker.isStringNullOrEmpty(biayaSelect2) && !biayaSelect2.equalsIgnoreCase("0")) && (select2!=null && !Checker.isStringNullOrEmpty(select2))) {
				norutItem++;
%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%
					StringTokenizer st = new StringTokenizer(select2,":");
					String id = st.nextToken();
					String kdpos = st.nextToken();
					String ketpos = st.nextToken();
					ketpos = ketpos.toLowerCase();
					ketpos = Tool.capFirstLetterInWord(ketpos);
					%>
					<%=ketpos+", sebesar " %>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaSelect2)%>
				</td>
			</tr>
		<%
			}
			if((biayaSelect3!=null && !Checker.isStringNullOrEmpty(biayaSelect3) && !biayaSelect3.equalsIgnoreCase("0")) && (select3!=null && !Checker.isStringNullOrEmpty(select3))) {
				norutItem++;
		%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%
					StringTokenizer st = new StringTokenizer(select3,":");
					String id = st.nextToken();
					String kdpos = st.nextToken();
					String ketpos = st.nextToken();
					ketpos = ketpos.toLowerCase();
					ketpos = Tool.capFirstLetterInWord(ketpos);
					%>
					<%=ketpos+", sebesar " %>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaSelect3)%>
				</td>
			</tr>
		<%
			}
			if((biayaSelect4!=null && !Checker.isStringNullOrEmpty(biayaSelect4) && !biayaSelect4.equalsIgnoreCase("0")) && (select4!=null && !Checker.isStringNullOrEmpty(select4))) {
				norutItem++;
		%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%
					StringTokenizer st = new StringTokenizer(select4,":");
					String id = st.nextToken();
					String kdpos = st.nextToken();
					String ketpos = st.nextToken();
					ketpos = ketpos.toLowerCase();
					ketpos = Tool.capFirstLetterInWord(ketpos);
					%>
					<%=ketpos+", sebesar " %>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaSelect4)%>
				</td>
			</tr>
		<%
			}
			if((biayaSelect5!=null && !Checker.isStringNullOrEmpty(biayaSelect5) && !biayaSelect5.equalsIgnoreCase("0")) && (select5!=null && !Checker.isStringNullOrEmpty(select5))) {
				norutItem++;
		%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%
					StringTokenizer st = new StringTokenizer(select5,":");
					String id = st.nextToken();
					String kdpos = st.nextToken();
					String ketpos = st.nextToken();
					ketpos = ketpos.toLowerCase();
					ketpos = Tool.capFirstLetterInWord(ketpos);
					%>
					<%=ketpos+", sebesar " %>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaSelect5)%>
				</td>
			</tr>
		<%
			}
			if((biayaSelect6!=null && !Checker.isStringNullOrEmpty(biayaSelect6) && !biayaSelect6.equalsIgnoreCase("0")) && (select6!=null && !Checker.isStringNullOrEmpty(select6))) {
				norutItem++;
		%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%
					StringTokenizer st = new StringTokenizer(select6,":");
					String id = st.nextToken();
					String kdpos = st.nextToken();
					String ketpos = st.nextToken();
					ketpos = ketpos.toLowerCase();
					ketpos = Tool.capFirstLetterInWord(ketpos);
					%>
					<%=ketpos+", sebesar " %>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaSelect6)%>
				</td>
			</tr>
<%
			}
			if((biayaSelect7!=null && !Checker.isStringNullOrEmpty(biayaSelect7) && !biayaSelect7.equalsIgnoreCase("0")) && (select7!=null && !Checker.isStringNullOrEmpty(select7))) {
				norutItem++;
		%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%
					StringTokenizer st = new StringTokenizer(select7,":");
					String id = st.nextToken();
					String kdpos = st.nextToken();
					String ketpos = st.nextToken();
					ketpos = ketpos.toLowerCase();
					ketpos = Tool.capFirstLetterInWord(ketpos);
					%>
					<%=ketpos+", sebesar " %>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaSelect7)%>
				</td>
			</tr>
		<%
			}
			if((biayaSelect8!=null && !Checker.isStringNullOrEmpty(biayaSelect8) && !biayaSelect8.equalsIgnoreCase("0")) && (select8!=null && !Checker.isStringNullOrEmpty(select8))) {
				norutItem++;
		%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%
					StringTokenizer st = new StringTokenizer(select8,":");
					String id = st.nextToken();
					String kdpos = st.nextToken();
					String ketpos = st.nextToken();
					ketpos = ketpos.toLowerCase();
					ketpos = Tool.capFirstLetterInWord(ketpos);
					%>
					<%=ketpos+", sebesar " %>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaSelect8)%>
				</td>
			</tr>
		<%
			}
			if((biayaSelect9!=null && !Checker.isStringNullOrEmpty(biayaSelect9) && !biayaSelect9.equalsIgnoreCase("0")) && (select9!=null && !Checker.isStringNullOrEmpty(select9))) {
				norutItem++;
		%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%
					StringTokenizer st = new StringTokenizer(select9,":");
					String id = st.nextToken();
					String kdpos = st.nextToken();
					String ketpos = st.nextToken();
					ketpos = ketpos.toLowerCase();
					ketpos = Tool.capFirstLetterInWord(ketpos);
				%>
					<%=ketpos+", sebesar " %>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaSelect9)%>
				</td>
			</tr>
		<%
			}
			%>
			</table>
	<%			
		}
		else if(tipeForm.equalsIgnoreCase("form4")) { //form tunai
			norutItem = 0;
			%>
			Pembayaran atas nama <b><%=nmm %></b>, sebesar Rp. <b><%=NumberFormater.indoNumberFormat((Double.parseDouble(biayaSelect1)+Double.parseDouble(biayaSelect2)+Double.parseDouble(biayaSelect3)+Double.parseDouble(biayaSelect4)+Double.parseDouble(biayaSelect5)+Double.parseDouble(biayaSelect6)+Double.parseDouble(biayaSelect7)+Double.parseDouble(biayaSelect8)+Double.parseDouble(biayaSelect9))+"") %></b> <br/>
			
			Sumber Dana : 
			<%
				if(sumberDana!=null  && !Checker.isStringNullOrEmpty(sumberDana)) {
					StringTokenizer stt = new StringTokenizer(sumberDana,",");
					stt.nextToken();
					out.print("("+stt.nextToken().toUpperCase()+")");		
				}
			%>
			<br/>		
			untuk pembayaran :
			<table>
	<%	
			if((biayaSelect1!=null && !Checker.isStringNullOrEmpty(biayaSelect1) && !biayaSelect1.equalsIgnoreCase("0")) && (select1!=null && !Checker.isStringNullOrEmpty(select1))) {
				norutItem++;
	%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%
					StringTokenizer st = new StringTokenizer(select1,":");
					String id = st.nextToken();
					String kdpos = st.nextToken();
					String ketpos = st.nextToken();
					ketpos = ketpos.toLowerCase();
					ketpos = Tool.capFirstLetterInWord(ketpos);
					%>
					<%=ketpos+", sebesar " %>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaSelect1)%> 
				</td>
			</tr>
<%
			}
			if((biayaSelect2!=null && !Checker.isStringNullOrEmpty(biayaSelect2) && !biayaSelect2.equalsIgnoreCase("0")) && (select2!=null && !Checker.isStringNullOrEmpty(select2))) {
				norutItem++;
%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%
					StringTokenizer st = new StringTokenizer(select2,":");
					String id = st.nextToken();
					String kdpos = st.nextToken();
					String ketpos = st.nextToken();
					ketpos = ketpos.toLowerCase();
					ketpos = Tool.capFirstLetterInWord(ketpos);
					%>
					<%=ketpos+", sebesar " %>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaSelect2)%>
				</td>
			</tr>
		<%
			}
			if((biayaSelect3!=null && !Checker.isStringNullOrEmpty(biayaSelect3) && !biayaSelect3.equalsIgnoreCase("0")) && (select3!=null && !Checker.isStringNullOrEmpty(select3))) {
				norutItem++;
		%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%
					StringTokenizer st = new StringTokenizer(select3,":");
					String id = st.nextToken();
					String kdpos = st.nextToken();
					String ketpos = st.nextToken();
					ketpos = ketpos.toLowerCase();
					ketpos = Tool.capFirstLetterInWord(ketpos);
					%>
					<%=ketpos+", sebesar " %>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaSelect3)%>
				</td>
			</tr>
		<%
			}
			if((biayaSelect4!=null && !Checker.isStringNullOrEmpty(biayaSelect4) && !biayaSelect4.equalsIgnoreCase("0")) && (select4!=null && !Checker.isStringNullOrEmpty(select4))) {
				norutItem++;
		%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%
					StringTokenizer st = new StringTokenizer(select4,":");
					String id = st.nextToken();
					String kdpos = st.nextToken();
					String ketpos = st.nextToken();
					ketpos = ketpos.toLowerCase();
					ketpos = Tool.capFirstLetterInWord(ketpos);
					%>
					<%=ketpos+", sebesar " %>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaSelect4)%>
				</td>
			</tr>
		<%
			}
			if((biayaSelect5!=null && !Checker.isStringNullOrEmpty(biayaSelect5) && !biayaSelect5.equalsIgnoreCase("0")) && (select5!=null && !Checker.isStringNullOrEmpty(select5))) {
				norutItem++;
		%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%
					StringTokenizer st = new StringTokenizer(select5,":");
					String id = st.nextToken();
					String kdpos = st.nextToken();
					String ketpos = st.nextToken();
					ketpos = ketpos.toLowerCase();
					ketpos = Tool.capFirstLetterInWord(ketpos);
					%>
					<%=ketpos+", sebesar " %>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaSelect5)%>
				</td>
			</tr>
		<%
			}
			if((biayaSelect6!=null && !Checker.isStringNullOrEmpty(biayaSelect6) && !biayaSelect6.equalsIgnoreCase("0")) && (select6!=null && !Checker.isStringNullOrEmpty(select6))) {
				norutItem++;
		%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%
					StringTokenizer st = new StringTokenizer(select6,":");
					String id = st.nextToken();
					String kdpos = st.nextToken();
					String ketpos = st.nextToken();
					ketpos = ketpos.toLowerCase();
					ketpos = Tool.capFirstLetterInWord(ketpos);
					%>
					<%=ketpos+", sebesar " %>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaSelect6)%>
				</td>
			</tr>
<%
			}
			if((biayaSelect7!=null && !Checker.isStringNullOrEmpty(biayaSelect7) && !biayaSelect7.equalsIgnoreCase("0")) && (select7!=null && !Checker.isStringNullOrEmpty(select7))) {
				norutItem++;
		%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%
					StringTokenizer st = new StringTokenizer(select7,":");
					String id = st.nextToken();
					String kdpos = st.nextToken();
					String ketpos = st.nextToken();
					ketpos = ketpos.toLowerCase();
					ketpos = Tool.capFirstLetterInWord(ketpos);
					%>
					<%=ketpos+", sebesar " %>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaSelect7)%>
				</td>
			</tr>
		<%
			}
			if((biayaSelect8!=null && !Checker.isStringNullOrEmpty(biayaSelect8) && !biayaSelect8.equalsIgnoreCase("0")) && (select8!=null && !Checker.isStringNullOrEmpty(select8))) {
				norutItem++;
		%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%
					StringTokenizer st = new StringTokenizer(select8,":");
					String id = st.nextToken();
					String kdpos = st.nextToken();
					String ketpos = st.nextToken();
					ketpos = ketpos.toLowerCase();
					ketpos = Tool.capFirstLetterInWord(ketpos);
					%>
					<%=ketpos+", sebesar " %>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaSelect8)%>
				</td>
			</tr>
		<%
			}
			if((biayaSelect9!=null && !Checker.isStringNullOrEmpty(biayaSelect9) && !biayaSelect9.equalsIgnoreCase("0")) && (select9!=null && !Checker.isStringNullOrEmpty(select9))) {
				norutItem++;
		%>	
			<tr>
				<td>
					<%= norutItem%>.
				</td>
				<td>
					<%
					StringTokenizer st = new StringTokenizer(select9,":");
					String id = st.nextToken();
					String kdpos = st.nextToken();
					String ketpos = st.nextToken();
					ketpos = ketpos.toLowerCase();
					ketpos = Tool.capFirstLetterInWord(ketpos);
				%>
					<%=ketpos+", sebesar " %>
				</td>
				<td style="text-align:right">
					&nbsp&nbspRp. <%= NumberFormater.indoNumberFormat(biayaSelect9)%>
				</td>
			</tr>
		<%
			}
			%>
			</table>
	<%			
		}

			
	%>
		</b><br/>
	</div>	
	<%
		if(!tipeForm.equalsIgnoreCase("form1")) {//transaksi bank
	%>
	<form id="form1" enctype="multipart/form-data" method="post" action="go.processUploadFile">
    <div class="row" >
    	<div style="font-size:1.5em;font-weight:bold;text-align:center;background-color:#a6bac4;width:700px;">PILIH FILE BUKTI SETORAN UNTUK DIUPLOAD</div>
      <label for="fileToUpload" style="font-size:1.5em;color:#900505;font-weight:bold;background-color:#d9e1e5;width:700px;"></label>
      <input type="file" name="fileToUpload" id="fileToUpload" onchange="fileSelected();" style="font-size:1.5em;color:#900505;font-weight:bold;background-color:#d9e1e5;width:700px;"/>
	  

    </div>
    <div id="fileName"></div>
    <div id="fileSize"></div>
    <div id="fileType"></div>
<%
if(validUsr.getObjNickNameGivenObjId().contains("MHS")|| validUsr.getObjNickNameGivenObjId().contains("mhs")) {
%>    
    <div  id="row"  class="row" style="visibility:hidden;font-size:1.5em;color:#900505;font-weight:bold;background-color:#d9e1e5;width:700px;text-align:center">
<%
}
else {
%>    
    <div  id="row"  class="row" style="font-size:1.5em;color:#900505;font-weight:bold;background-color:#d9e1e5;width:700px;text-align:center">
<%
}
%>    
      <input type="button" id="buttonSubmit" onclick="uploadFile()" value="UPLOAD BUKTI SETORAN" style="font-size:1em;font-weight:bold;width:300px;"/>
    </div>
    
  </form>
  <%
  		}
  		else {//transakasi tunai
  		%>
  <form method="post" action="proses.updateBuktiPembayaran?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&atMenu=riwayatBayaran">
  	<div class="row" style="font-size:1.5em;color:#900505;font-weight:bold;background-color:#d9e1e5;width:700px;text-align:center">
  		<input type="submit" value="INPUT BAYARAN TUNAI" style="font-size:1em;font-weight:bold;width:300px;"/>
  	</div>
  </form>
  	  <%		
  		}
  %>
  <table>
  	<tr>
  		<td width="150px">
  <div id="progressNumber" style="text-align:center"></div>
  		</td>
  	</tr>	
  </table>
  <div id="pb" style="visibility:hidden">
  <progress class="progress"  value="0" max="100"></progress>
  </div>
  <%
		
	}
	else {
		if(nominalNol) {
			out.print("<h3>Transaksi tidak dapat dilanjutkan, Nominal biaya belum terisi</h3>");
		}
		else if(error_bilanga) {
			out.print("<h3>Transaksi tidak dapat dilanjutkan, Nominal biaya tidak dalam bentuk angka</h3>");
		}
		else if(tipeForm.equalsIgnoreCase("form1") && noSelection) {//form1 = form tunai	
			out.print("<h3>Transaksi tidak dapat dilanjutkan, Pos biaya belum dipilih</h3>");
		}
	}
	
  %>

		<!-- Column 1 end -->
	</div>
</div>
<br/>



</body>
</html>