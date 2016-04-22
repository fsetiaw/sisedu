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
%>

<script type="text/javascript">
	var objId="none";
	var nmm="none";
	var npm="none";
	var obj_lvl="none";
	var kdpst="none";
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
        window.location.replace("proses.updateBuktiPembayaran?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&atMenu=riwayatBayaran");
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

	<li><a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">BACK <span><b style="color:#eee">---</b></span></a></li>
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
	
			
	%>
		</b><br/>
	</div>	

<form id="form1" enctype="multipart/form-data" method="post" action="go.processUploadFile">
	<div class="row" >
    	<div style="font-size:1.5em;font-weight:bold;text-align:center;background-color:#a6bac4;width:700px;">PILIH FILE BUKTI SETORAN UNTUK DIUPLOAD</div>
    	<label for="fileToUpload" style="font-size:1.5em;color:#900505;font-weight:bold;background-color:#d9e1e5;width:700px;"></label>
      	<input type="file" name="fileToUpload" id="fileToUpload" onchange="fileSelected();" style="font-size:1.5em;color:#900505;font-weight:bold;background-color:#d9e1e5;width:700px;"/>
	</div>
    <div id="fileName"></div>
    <div id="fileSize"></div>
    <div id="fileType"></div>
    <div class="row" style="font-size:1.5em;color:#900505;font-weight:bold;background-color:#d9e1e5;width:700px;text-align:center">
    	<input type="button" onclick="uploadFile()" value="UPLOAD BUKTI SETORAN" style="font-size:1em;font-weight:bold;width:300px;"/>
    </div>    
</form>
 


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

  %>

		<!-- Column 1 end -->
	</div>
</div>
<br/>



</body>
</html>