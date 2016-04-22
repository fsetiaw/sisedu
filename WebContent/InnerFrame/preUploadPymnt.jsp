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

<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/css/progressBar/normalize.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/css/progressBar/style.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/css3_tabbed_nav/css/style.css">
<!--  script src="<%=Constants.getRootWeb() %>/css3_tabbed_nav/js/script.js" type="text/javascript"></script -->

<script src="<%=Constants.getRootWeb() %>/js/jquery.js" type="text/javascript"></script>
<script src="<%=Constants.getRootWeb() %>/js/modernizr.js" type="text/javascript"></script>
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");

Vector v= null; 
String cmd = request.getParameter("cmd");
String msg = request.getParameter("msg");
String objId = request.getParameter("id_obj");
String nmm = request.getParameter("nmm");
String npm = request.getParameter("npm");
String kdpst = request.getParameter("kdpst");
String kdjen = Converter.getKdjen(kdpst);
String obj_lvl =  request.getParameter("obj_lvl");
String shiftMhs = null;
//localhost:8080/com.otaku.rest/api/v1/mhs/5730112100007/shift
JSONArray jsoaShiftMhs = Getter.readJsonArrayFromUrl("/v1/mhs/"+npm+"/shift");
if(jsoaShiftMhs!=null && jsoaShiftMhs.length()>0) {
	JSONObject jobShift = jsoaShiftMhs.getJSONObject(0);
	shiftMhs = jobShift.getString("KETERANGAN");
	shiftMhs = shiftMhs.replace("&#x2f;", "/");
	if(shiftMhs==null || Checker.isStringNullOrEmpty(shiftMhs) || shiftMhs.equalsIgnoreCase("N/A")) {
		shiftMhs = Constants.getDefaultShift(kdjen);
	}
}
else {
	shiftMhs = Constants.getDefaultShift(kdjen);
}


String pos_dan_nickname = null;
//System.out.println("-->"+kdjen+"/"+shiftMhs);
JSONArray jsoaPosRev = Getter.readJsonArrayFromUrl("/v1/pos_biaya/pymnt_type/tunai_dan_bank/"+kdjen+"/"+shiftMhs);
//JSONArray jsoaPosRev = Getter.readJsonArrayFromUrl("/v1/pos_biaya/pymnt_type/tunai_dan_bank/"+kdjen+"/EKSEKUTIF%20PASCA");
if(jsoaPosRev!=null && jsoaPosRev.length()>0) {
	for(int i=0;i<jsoaPosRev.length();i++) {
		JSONObject jobPos = jsoaPosRev.getJSONObject(i);
		String tmp = jobPos.getString("ID_POS");
		if(pos_dan_nickname==null) {
			pos_dan_nickname = "\""+tmp+"\"";
		}
		else {
			pos_dan_nickname = pos_dan_nickname+",\""+tmp+"\"";
		}
		tmp = jobPos.getString("NAMA_MASTER_POS");
		pos_dan_nickname = pos_dan_nickname+",\""+tmp+"\"";
		tmp = jobPos.getString("NICKNAME_POS");
		pos_dan_nickname = pos_dan_nickname+",\""+tmp+"\"";
		//System.out.println("-->pos_dan_nickname = "+pos_dan_nickname);
	}	
}

String opt_val="";
JSONArray jsoaBea = Getter.readJsonArrayFromUrl("/v1/bea");
if(jsoaBea!=null && jsoaBea.length()>0) {
	for(int i=0;i<jsoaBea.length();i++) {
		JSONObject jobPos = jsoaBea.getJSONObject(i);
		String value = jobPos.getString("IDPAKETBEASISWA");
		String keter = jobPos.getString("NAMAPAKET");
		opt_val = opt_val+"<option value="+value+","+keter.replace(" ", "_")+">"+keter.toUpperCase()+"</option>";
	}	
}


Vector v_bak = (Vector) request.getAttribute("v_bak");
%>
<!--  script type="text/javascript">
      function validating() {
    	  var msg = 'validating</br>';
    	  var valid = true;
    	  var tmp = null;
    	  if(tmp = document.formUpload1.inp_amnt.value)
    	   {
    		  if(!isNaN(parseFloat(tmp)) && isFinite(tmp)) {
    			  msg = msg+'Harap Mengisi Jumlah Pembayaran dengan benar</br>';
    		  }

    	     //alert( "Please provide your name!" );
    	     document.formUpload1.inp_amnt.focus() ;
    	     //return false;
    	   }
    	  msg = msg+tmp+'</br>';
    	  document.getElementById('error_msg').innerHTML = msg;;
      }
</script-->
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"> </script>
<!--  script src="http://code.jquery.com/jquery-latest.min.js"></script-->
 <script type="text/javascript">
var pos_dan_nickname = [<%=pos_dan_nickname%>];

$(document).ready(function()
{
	$("#somebutton").click(function()	
	{
		//$.get('go.uploadFile2', function(responseText) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
            //             // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
        $.post( 'go.validateForm', $('#formUpload1').serialize(), function(data) {
        	document.getElementById('div_msg').style.visibility='visible';
        	$('#div_msg').html(); 
        	$('#div_msg').html(data); 
        });
	});
		
	$("#menu ul li a").click(function(e) {
		e.preventDefault();
		
		$("#menu ul li a").each(function() {
			$(this).removeClass("active");	
		});
		
		$(this).addClass("active");
		if($(this).attr("title")=='tipe1') {
			//document.getElementById('somebutton').style.visibility='visible';
			document.getElementById('btn').style.visibility='visible';
			var item = '<input type="hidden" name="tipeForm_String_Opt"  value="form1"/>';
			//var item = '';
			item = item + '<br/>';
			item = item + '<table align="center" border="1" style="background:#d9e1e5;color:#fff;width:600px">';
			item = item + '<tr>';
			item = item + '		<td align="center" colspan="4" bgcolor="#369" height="35px"><b>PEMBAYARAN TUNAI</b></td>';
			item = item + '</tr>';
			item = item + '<tr>';
			item = item + '		<td colspan="2" style="color:#1E3F60"><label>Nama Penyetor</label></td><td colspan="2"><input type="text" name="Nama-Penyetor_Huruf_Opt"  style="width:99%" placeholder="Harap diisi bila disetorkan oleh orang lain"/></td>';
			item = item + '</tr>';
			item = item + '<tr>';
			item = item + '		<td colspan="2" style="color:#1E3F60"><label>Tgl Transaksi</label></td><td colspan="2"><input type="text" name="Tgl-Transaksi-Cash_Date_Wajib"  style="width:99%" placeholder="tgl/bln/tahun"/></td>';
			item = item + '</tr>';
			item = item + '<table align="center" border="1" style="background:#d9e1e5;color:#fff;width:600px">';
			item = item + '<tr>';
			item = item + '		<td align="center" colspan="4" bgcolor="#369" ><b>Info Detil Transaksi</b></td>';
			item = item + '</tr>';
			
			for(k=1;k<10;k++) {
				item = item + '<tr>';
				item = item + '		<td colspan="3" style="color:#1E3F60">';
				item = item + '			'+k+'. <select name="selection'+k+'_String_Opt" style="width:95%;direction:ltl"">';
				item = item + '			<option value="null" selected=selected>-- pilihan --</option>';
				for(j=0;j<pos_dan_nickname.length;) {
					var idPos = pos_dan_nickname[j++];
					var masterPos = pos_dan_nickname[j++];
					var nickPos = pos_dan_nickname[j++];
					item = item + '			<option value="'+idPos+':'+masterPos+':'+nickPos+'">'+nickPos+'</option>';
				}
				item = item + '			</select>';
				item = item + '		</td>';
				item = item + '		<td colspan="1" style="color:#1E3F60">';
				item = item + '			<input type="text" name="Selection-'+k+'_Double_Opt" required style="width:99%" placeholder="IDR (Rupiah)"/>';
				item = item + '		</td>';
				item = item + '</tr>';
			}			
			
			
			item = item + '</table>';
			item = item + '<br/>';
			$("#main").html();
			$("#main").html(item); 
		}
		else if($(this).attr("title")=='tipe2') {
			//document.getElementById('somebutton').style.visibility='visible';
			document.getElementById('btn').style.visibility='visible';
			var item = '<input type="hidden" name="tipeForm_String_Opt"  value="form2"/>';
			//var item = '';
			item = item + '<br/>';
			item = item + '<table align="center" border="1" style="background:#d9e1e5;color:#fff;width:600px">';
			item = item + '<tr>';
			item = item + '		<td align="center" colspan="4" bgcolor="#369" height="35px"><b>BAYARAN DPP (form 1/2)</b></td>';
			item = item + '</tr>';
			item = item + '<tr>';
			item = item + '		<td colspan="2" style="color:#1E3F60"><label>Nama Penyetor</label></td><td colspan="2"><input type="text" name="Nama-Penyetor_Huruf_Opt"  style="width:99%" placeholder="Harap diisi bila disetorkan oleh orang lain"/></td>';
			item = item + '</tr>';
			item = item + '<tr>';
			item = item + '		<td colspan="2" style="color:#1E3F60"><label>Tgl Transaksi Bank</label></td><td colspan="2"><input type="text" name="Tgl-Transaksi-Bank_Date_Wajib"  style="width:99%" placeholder="tgl/bln/tahun"/></td>';
			item = item + '</tr>';
			item = item + '<tr>';
			item = item + '		<td colspan="2" style="color:#1E3F60"><label>Sumber Dana</label></td>';
			item = item + '<td colspan="2"><select name="Sumber-Dana_Huruf_Opt" style="width:100%">';
			item = item + '<%=opt_val%>';
			item = item + '</select></td>';
			item = item + '</tr>';
			item = item + '</table>';
			item = item + '<br>';
			item = item + '<table align="center" border="1" style="background:#d9e1e5;color:#fff;width:600px">';
			item = item + '<tr>';
			item = item + '		<td align="center" colspan="4" bgcolor="#369" ><b>Info Detil Transaksi </b></td>';
			item = item + '</tr>';
			item = item + '<tr>';
			item = item + '		<td colspan="3" style="color:#1E3F60">';
			item = item + '			Biaya DPP Angkusran ke: <input type="text" name="Angsuran-DPP-Ke_Int_Opt_Besaran_Wajib" style="width:30px"/> &nbsp,&nbspGelombang: <input type="text" name="Gelombang_Int_Opt_Besaran_Wajib" required style="width:30px"/> &nbsp,&nbsp&nbspsebesar :';
			item = item + '		</td>';
			item = item + '		<td colspan="1" style="color:#1E3F60">';
			item = item + '			<input type="text" name="Besaran_Double_Opt_Angsuran-DPP-Ke_Wajib" required style="width:98%" placeholder="IDR (Rupiah)"/>';
			item = item + '		</td>';
			item = item + '</tr>';		
			item = item + '<tr>';
			item = item + '		<td colspan="4" style="color:#1E3F60;vertical-align:bottom">';
			item = item + '			Pembayaran Lain-2 : (bila ada)';
			item = item + '     </td>';
			item = item + '</tr>';
			item = item + '<tr>';
			item = item + '		<td colspan="3" style="color:#1E3F60">';
			item = item + '			1. Jaket Almamater';
			item = item + '		</td>';
			item = item + '		<td colspan="1" style="color:#1E3F60">';
			item = item + '			<input type="text" name="Biaya-Jaket-Almamater_Double_Wajib_Besaran_Opt" required style="width:98%" placeholder="IDR (Rupiah)"/>';
			item = item + '		</td>';
			item = item + '</tr>';	
			item = item + '</table>';
			item = item + '<br/>';
			$("#main").html();
			$("#main").html(item); 
		}
		else if($(this).attr("title")=='tipe3') {
			//document.getElementById('somebutton').style.visibility='visible';
			document.getElementById('btn').style.visibility='visible';
			var item = '<input type="hidden" name="tipeForm_String_Opt"  value="form3"/>';
			//var item = '';
			item = item + '<br/>';
			item = item + '<table align="center" border="1" style="background:#d9e1e5;color:#fff;width:600px">';
			item = item + '<tr>';
			item = item + '		<td align="center" colspan="4" bgcolor="#369" height="35px"><b>BAYARAN BIAYA PENYELENGGARAAN PENDIDIKAN (BPP) (form 1/2)</b></td>';
			item = item + '</tr>';
			item = item + '<tr>';
			item = item + '		<td colspan="2" style="color:#1E3F60"><label>Nama Penyetor</label></td><td colspan="2"><input type="text" name="Nama-Penyetor_Huruf_Opt"  style="width:99%" placeholder="Harap diisi bila disetorkan oleh orang lain"/></td>';
			item = item + '</tr>';
			item = item + '<tr>';
			item = item + '		<td colspan="2" style="color:#1E3F60"><label>Tgl Transaksi Bank</label></td><td colspan="2"><input type="text" name="Tgl-Transaksi-Bank_Date_Wajib"  style="width:99%" placeholder="tgl/bln/tahun"/></td>';
			item = item + '</tr>';	
			item = item + '<tr>';
			item = item + '		<td colspan="2" style="color:#1E3F60"><label>Sumber Dana</label></td>';
			item = item + '<td colspan="2"><select name="Sumber-Dana_Huruf_Opt" style="width:100%">';
			item = item + '<%=opt_val%>';
			item = item + '</select></td>';
			item = item + '</tr>';
			item = item + '</table>';
			item = item + '<br>';
			item = item + '<table align="center" border="1" style="background:#d9e1e5;color:#fff;width:600px">';
			item = item + '<tr>';
			item = item + '		<td align="center" colspan="4" bgcolor="#369" ><b>Info Detil Transaksi</b></td>';
			item = item + '</tr>';
			item = item + '<tr>';
			item = item + '		<td colspan="3" style="color:#1E3F60">';
			item = item + '			Pembayaran BPP Semester Ke : <input type="text" name="Pembayaran-BPP-Semester-Ke_Int_Opt_Besaran-Biaya-BPP_Wajib" style="width:30px"/> &nbsp,&nbspsebesar :';
			item = item + '		</td>';
			item = item + '		<td colspan="1" style="color:#1E3F60">';
			item = item + '			<input type="text" name="Besaran-Biaya-BPP_Double_Opt_Pembayaran-BPP-Semester-Ke_Wajib" required style="width:98%" placeholder="IDR (Rupiah)"/>';
			item = item + '		</td>';
			item = item + '</tr>';
			item = item + '<tr>';
			item = item + '		<td colspan="3" style="color:#1E3F60">';
			item = item + '			Pembayaran Daftar Ulang Semester Ke :  <input type="text" name="Pembayaran-Daftar-Ulang-Semester-Ke_Int_Opt_Besaran-Biaya-Daftar-Ulang_Wajib" style="width:30px"/> &nbsp,&nbsp&nbsp&nbspsebesar :';
			item = item + '		</td>';
			item = item + '		<td colspan="1" style="color:#1E3F60">';
			item = item + '			<input type="text" name="Besaran-Biaya-Daftar-Ulang_Double_Opt_Pembayaran-Daftar-Ulang-Semester-Ke_Wajib" required style="width:98%" placeholder="IDR (Rupiah)"/>';
			item = item + '		</td>';
			item = item + '</tr>';
			item = item + '<tr>';
			item = item + '		<td colspan="3" style="color:#1E3F60">';
			item = item + '			Pembayaran SKS Semester Ke : <input type="text" name="Pembayaran-SKS-Semester-Ke_Int_Opt_Besaran-Biaya-SKS_Wajib" style="width:30px"/>, total sks : <input type="text" name="Tot-SKS-Diambil_Double_Opt_Besaran-Biaya-SKS_Wajib" required style="width:30px" placeholder=""/> &nbsp,&nbsp&nbsp&nbsp&nbspsebesar :';
			item = item + '		</td>';
			item = item + '		<td colspan="1" style="color:#1E3F60;vertical-align:bottom"">';
			item = item + '			<input type="text" name="Besaran-Biaya-SKS_Double_Opt_Pembayaran-SKS-Semester-Ke_Wajib" required style="width:98%" placeholder="IDR (Rupiah)"/>';
			item = item + '		</td>';
			item = item + '</tr>';
			item = item + '<tr>';
			item = item + '		<td colspan="3" style="color:#1E3F60">';
			item = item + '			1. Dana Kemahasiswaan';
			item = item + '		</td>';
			item = item + '		<td colspan="1" style="color:#1E3F60">';
			item = item + '			<input type="text" name="Besaran-Dana-Kemahasiswaan_Double_Opt" required style="width:98%" placeholder="IDR (Rupiah)"/>';
			item = item + '		</td>';
			item = item + '</tr>';
			item = item + '<tr>';
			item = item + '		<td colspan="3" style="color:#1E3F60">';
			item = item + '			2. Praktikum';
			item = item + '		</td>';
			item = item + '		<td colspan="1" style="color:#1E3F60">';
			item = item + '			<input type="text" name="Besaran-Praktikum_Double_Opt" required style="width:98%" placeholder="IDR (Rupiah)"/>';
			item = item + '		</td>';
			item = item + '</tr>';	
			item = item + '<tr>';
			item = item + '		<td colspan="3" style="color:#1E3F60">';
			item = item + '			3. Bimbingan Skripsi';
			item = item + '		</td>';
			item = item + '		<td colspan="1" style="color:#1E3F60">';
			item = item + '			<input type="text" name="Besaran-Bimbingan-Skripsi_Double_Opt" required style="width:98%" placeholder="IDR (Rupiah)"/>';
			item = item + '		</td>';
			item = item + '</tr>';	
			item = item + '<tr>';
			item = item + '		<td colspan="3" style="color:#1E3F60">';
			item = item + '			4. Pembinaan / Asistensi';
			item = item + '		</td>';
			item = item + '		<td colspan="1" style="color:#1E3F60">';
			item = item + '			<input type="text" name="Besaran-Pembinaan_Double_Opt" required style="width:98%" placeholder="IDR (Rupiah)"/>';
			item = item + '		</td>';
			item = item + '</tr>';	
			item = item + '<tr>';
			item = item + '		<td colspan="3" style="color:#1E3F60">';
			item = item + '			5. Sidang Skripsi';
			item = item + '		</td>';
			item = item + '		<td colspan="1" style="color:#1E3F60">';
			item = item + '			<input type="text" name="Besaran-Ujian-Skripsi_Double_Opt" required style="width:98%" placeholder="IDR (Rupiah)"/>';
			item = item + '		</td>';
			item = item + '</tr>';	
			item = item + '<tr>';
			item = item + '		<td colspan="3" style="color:#1E3F60">';
			item = item + '			6. Sumbangan Buku Perpustakaan';
			item = item + '		</td>';
			item = item + '		<td colspan="1" style="color:#1E3F60">';
			item = item + '			<input type="text" name="Besaran-Sumbangan-Buku_Double_Opt" required style="width:98%" placeholder="IDR (Rupiah)"/>';
			item = item + '		</td>';
			item = item + '</tr>';	
			item = item + '<tr>';
			item = item + '		<td colspan="3" style="color:#1E3F60">';
			item = item + '			7. Jurnal';
			item = item + '		</td>';
			item = item + '		<td colspan="1" style="color:#1E3F60">';
			item = item + '			<input type="text" name="Besaran-Jurnal_Double_Opt" required style="width:98%" placeholder="IDR (Rupiah)"/>';
			item = item + '		</td>';
			item = item + '</tr>';	
			item = item + '<tr>';
			item = item + '		<td colspan="3" style="color:#1E3F60">';
			item = item + '			8. Ijazah';
			item = item + '		</td>';
			item = item + '		<td colspan="1" style="color:#1E3F60">';
			item = item + '			<input type="text" name="Besaran-Ijazah_Double_Opt" required style="width:98%" placeholder="IDR (Rupiah)"/>';
			item = item + '		</td>';
			item = item + '</tr>';	
			item = item + '<tr>';
			item = item + '		<td colspan="3" style="color:#1E3F60">';
			item = item + '			9. Wisuda';
			item = item + '		</td>';
			item = item + '		<td colspan="1" style="color:#1E3F60">';
			item = item + '			<input type="text" name="Besaran-Wisuda_Double_Opt" required style="width:98%" placeholder="IDR (Rupiah)"/>';
			item = item + '		</td>';
			item = item + '</tr>';	
			item = item + '<tr>';
			item = item + '		<td colspan="3" style="color:#1E3F60">';
			item = item + '			10. Sidang Kerja Praktek';
			item = item + '		</td>';
			item = item + '		<td colspan="1" style="color:#1E3F60">';
			item = item + '			<input type="text" name="Besaran-Sidang-Kerja-Praktek_Double_Opt" required style="width:98%" placeholder="IDR (Rupiah)"/>';
			item = item + '		</td>';
			item = item + '</tr>';	
			item = item + '<tr>';
			item = item + '		<td colspan="3" style="color:#1E3F60">';
			item = item + '			11. Biaya Administrasi';
			item = item + '		</td>';
			item = item + '		<td colspan="1" style="color:#1E3F60">';
			item = item + '			<input type="text" name="Besaran-Biaya-Administrasi_Double_Opt" required style="width:98%" placeholder="IDR (Rupiah)"/>';
			item = item + '		</td>';
			item = item + '</tr>';	

			item = item + '</table>';
			item = item + '<br/>';
			$("#main").html();
			$("#main").html(item); 
			//$("#main").html();
			//$("#main").html($(this).attr("title"));
		}
		else if($(this).attr("title")=='tipe4') {
			//document.getElementById('somebutton').style.visibility='visible';
			document.getElementById('btn').style.visibility='visible';
			var item = '<input type="hidden" name="tipeForm_String_Opt"  value="form4"/>';
			//var item = '';
			item = item + '<br/>';
			item = item + '<table align="center" border="1" style="background:#d9e1e5;color:#fff;width:600px">';
			item = item + '<tr>';
			item = item + '		<td align="center" colspan="4" bgcolor="#369" height="35px"><b>TRANSAKSI VIA BANK</b></td>';
			item = item + '</tr>';
			item = item + '<tr>';
			item = item + '		<td colspan="2" style="color:#1E3F60"><label>Nama Penyetor</label></td><td colspan="2"><input type="text" name="Nama-Penyetor_Huruf_Opt"  style="width:99%" placeholder="Harap diisi bila disetorkan oleh orang lain"/></td>';
			item = item + '</tr>';
			item = item + '<tr>';
			item = item + '		<td colspan="2" style="color:#1E3F60"><label>Tgl Transaksi Bank</label></td><td colspan="2"><input type="text" name="Tgl-Transaksi-Bank_Date_Wajib"  style="width:99%" placeholder="tgl/bln/tahun"/></td>';
			item = item + '</tr>';
			item = item + '<tr>';
			item = item + '		<td colspan="2" style="color:#1E3F60"><label>Sumber Dana</label></td>';
			item = item + '<td colspan="2"><select name="Sumber-Dana_Huruf_Opt" style="width:100%">';
			item = item + '<%=opt_val%>';
			item = item + '</select></td>';
			item = item + '</tr>';
			item = item + '<table align="center" border="1" style="background:#d9e1e5;color:#fff;width:600px">';
			item = item + '<tr>';
			item = item + '		<td align="center" colspan="4" bgcolor="#369" ><b>Info Detil Transaksi</b></td>';
			item = item + '</tr>';
			
			for(k=1;k<10;k++) {
				item = item + '<tr>';
				item = item + '		<td colspan="3" style="color:#1E3F60">';
				item = item + '			'+k+'. <select name="selection'+k+'_String_Opt" style="width:95%;direction:ltl"">';
				item = item + '			<option value="null" selected=selected>-- pilihan --</option>';
				for(j=0;j<pos_dan_nickname.length;) {
					var idPos = pos_dan_nickname[j++];
					var masterPos = pos_dan_nickname[j++];
					var nickPos = pos_dan_nickname[j++];
					item = item + '			<option value="'+idPos+':'+masterPos+':'+nickPos+'">'+nickPos+'</option>';
				}
				item = item + '			</select>';
				item = item + '		</td>';
				item = item + '		<td colspan="1" style="color:#1E3F60">';
				item = item + '			<input type="text" name="Selection-'+k+'_Double_Opt" required style="width:99%" placeholder="IDR (Rupiah)"/>';
				item = item + '		</td>';
				item = item + '</tr>';
			}			
			
			
			item = item + '</table>';
			item = item + '<br/>';
			$("#main").html();
			$("#main").html(item); 
		}
	});
	
});	
// $('#formu').submit( function() {
//	$.post( 'go.uploadFile2', $('#formu').serialize(), function(data) {
//		$('#somediv').text(responseText);
	
	      // 'json' // I expect a JSON response
//	});
//});
           // $(document).ready(function() {                        // When the HTML DOM is ready loading, then execute the following function...
            //    $('#somebutton').click(function() {  
             //   	var data = $('formu').serialize();
              //  	$.post('go.uploadFile2', data);// Locate HTML DOM element with ID "somebutton" and assign the following function to its "click" event...
                    //$.get('go.uploadFile2', function(responseText) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
                    //             // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
                    //});
               // });
            //});
</script>
</head>
<body>
<div id="header">
<ul>
	<li><a href="javascript:history.go(-1)" >BACK <span><b style="color:#eee">---</b></span></a></li>
</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<div id="wrapper">
			<!--  h1>Sweet tabbed navigation<small>using CSS3</small></h1>
			<h2><a href="http://www.marcofolio.net/" title="Visit Marcofolio.net">Marcofolio.net</a></h2 -->
			<div id="content">
				<div id="menu">
					<ul>
					<%
					/*
					* nanti di servlet ato bean nya di filter lagi...mhs ngga boleh ada transaksi tunai
					*/
					JSONArray joa = Getter.readJsonArrayFromUrl("/v1/search_obj_type/"+validUsr.getIdObj()+"/nickname");
					JSONObject job = null;
					if(joa!=null && joa.length()>0) {
						job = joa.getJSONObject(0);
						System.out.println("job="+job.toString());
					}
					if(job!=null && !job.getString("OBJ_NICKNAME").contains("MHS") && !job.getString("OBJ_NICKNAME").contains("mhs")) {
					%>
						<li><a href="#" title="tipe1"><span>Transaksi</span>Tunai</a></li>
					<%
					}
					if(!kdjen.equalsIgnoreCase("A") && !kdjen.equalsIgnoreCase("B") ) {
					%>	
						<li><a href="#" title="tipe2" class="active"><span>Form DPP</span>& Jaket</a></li>
						<li><a href="#" title="tipe3"><span>Form BPP</span>& Lain2</a></li>
					<%
					}
					%>	
					<%
					if(kdjen.equalsIgnoreCase("A") || kdjen.equalsIgnoreCase("B") ) {
					%>
						<li><a href="#" title="tipe4"><span>Transaksi</span>Bank</a></li>
					<%
					}
					%>	
					</ul>
				</div>
				
				<form name="formUpload1" id="formUpload1">
				<input type="hidden" name="StringfwdPageIfValid_String_Opt" value="uploadPymnt.jsp" />
				<input type="hidden" name="idObj_Int_Opt" value="<%=objId%>" />
				<input type="hidden" name="objLvl_Int_Opt" value="<%=obj_lvl%>" />
				<input type="hidden" name="kdpst_String_Opt" value="<%=kdpst%>" />
				<input type="hidden" name="nmm_String_Opt" value="<%=nmm%>" />
				<input type="hidden" name="npm_String_Opt" value="<%=npm%>" />
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
				<div id="main">
				</div>	
				<br/>
				<div id="btn" style="text-align:center;background-color:#a6bac4;width:700px;height:35px;visibility:hidden" ><input type="button" id="somebutton" value="Next" style="width:50%;height:25px;margin:5px;"/></div>
	
				</form>
				<br/>
			</div>
		</div>
		<div id="div_msg" style="font-style:italic;color:red;text-align:left;background-color:#d9e1e5;width:700px;height:100%;visibility:hidden;margin:0px 0 0 105px;" ></div>	
		
			
			<!--  form name="form1" id="form1" target="iframe_dummy">
			<input type="text" name="minor" value="deuaalt"/>
			<input type="button" id="somebutton" value="press here" />
			</form>
        <div id="somediv"></div-->
		<!-- Column 1 end -->
	</div>
</div>


</body>
</html>