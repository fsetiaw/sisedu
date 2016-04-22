<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<title>Insert title here</title>

<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/css/progressBar/normalize.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/css/progressBar/style.css">
<script src="<%=Constants.getRootWeb() %>/js/jquery.js" type="text/javascript"></script>
<script src="<%=Constants.getRootWeb() %>/js/modernizr.js" type="text/javascript"></script>
<%
//System.out.println("siop");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v = null;
//long millis = System.currentTimeMillis();
String fieldAndValue = (String)session.getAttribute("fieldAndValue");

	String tipe_ua = "";
	String judul = "";
	String idkmk = ""; 
	String kdkmk = "";
	String nakmk = "";
	String nmmhs = "";
	String objId = "";
	String obj_lvl = "";
	String cmd = "";
	String kdpst = "";
	String npmhs = "";
	
	
if(fieldAndValue!=null) {
	//String fieldAndValue = (String) session.getAttribute("fieldAndValue");

	StringTokenizer st = new StringTokenizer(fieldAndValue,"||");
	while(st.hasMoreTokens()) {
		String fieldNmm = st.nextToken();
		//System.out.println("fieldNmm=="+fieldNmm);
	    String fieldval = st.nextToken();
	    if(fieldNmm.contains("nmmhs")) {
	    	nmmhs = new String(fieldval); 
	    }
	    else if(fieldNmm.contains("idobj")) {
    		objId = new String(fieldval); 
    	}
    	else if(fieldNmm.contains("objlv")) {
    		obj_lvl = new String(fieldval); 
    	}
    	else if(fieldNmm.contains("cmd")) {
    		cmd = new String(fieldval); 
    	}
    	else if(fieldNmm.contains("npmhs")) {
    		npmhs = new String(fieldval); 
    	}
    	else if(fieldNmm.contains("kdpst")) {
    		kdpst = new String(fieldval); 
    	}
    	else if(fieldNmm.contains("Tipe-Ujian")) {
    		tipe_ua = new String(fieldval); 
    	}
    	else if(fieldNmm.contains("Judul-Naskah")) {
    		judul = new String(fieldval); 
    	}
	    
    }
	st = new StringTokenizer(tipe_ua,"`");
    idkmk = st.nextToken(); 
	kdkmk = st.nextToken();
	nakmk = st.nextToken();
}
%>


<script type="text/javascript">

	var objId="<%=objId%>";
	var nmm="<%=nmmhs%>";
	var npm="<%=npmhs%>";
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
          document.getElementById('buttonSubmit').style.visibility='visible';
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
        xhr.open("POST", "go.processUploadFile_v1");
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
        	//System.out.println("pos11111111");
        %>
        window.location.replace("proses.updateBuktiNaskahUa");
        
        <%
    	//System.out.println("pos2");
    	%>
      }

      function uploadFailed(evt) {
        alert("Naskah Ujian Gagal Di Upload");
      	window.location.replace("get.histPymnt?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&atMenu=riwayatBayaran");
      }

      function uploadCanceled(evt) {
        alert("Proses Upload Dibatalkan Oleh Pengguna atau Koneksi Internet Terputus");
      	window.location.replace("get.histPymnt?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&atMenu=riwayatBayaran");
      }
</script>      
</head>
<body>
<div id="header">
<ul>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}InnerFrame/Pengajuan/DashUjianAkhir_part1.jsp?id_obj=<%=objId%>&nmm=<%=nmmhs%>&npm=<%=npmhs %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=" target="_self" >BACK<span><div style="color:#eee">&nbsp</div></span></a></li>
</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		
		<!-- Column 1 start -->
		
<% 
//get session value fieldname value



//out.println("milis="+millis);
//session.setAttribute("target_npm", v_npmhs);
Vector vTipeUa= Getter.getListJenisMakulUjian(kdpst); 
if(vTipeUa==null || vTipeUa.size()<1) {
	out.print("belum ada jenis");
}
else {
	//Vector vInfo_jenis_makul = Getter.getListJenisMakul();
	//format(id+"`"+keter+"`"+kode);
	
			//System.out.println("id_obj="+v_id_obj);
			//System.out.println("v_nmmhs="+v_nmmhs);
			//System.out.println("npm="+v_npmhs);
			//System.out.println("v_obj_lvl="+v_obj_lvl);
			//System.out.println("kdpst="+kdpst);
			//System.out.println("cmd="+cmd);
%>
	<!--  form id="form1" enctype="multipart/form-data" method="post" action="go.processUploadFileTamplete" -->
	
	

	

	<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:500px"> 
        <tr> 
        	<td style="background:#369;color:#fff;text-align:center" colspan="5"><label><B>PENGAJUAN UJIAN / SIDANG</B> </label></td> 
        </tr> 
        <tr> 
            <td style="background:#369;color:#fff;text-align:center;width:50%">Tipe Ujian Akhir</td>
            <td style="text-align:center;width:250px"><%=nakmk %></td> 
        </tr> 
        <tr> 
            <td style="background:#369;color:#fff;text-align:center;width:50%">Judul Naskah Ujian</td>
            <td style="text-align:center;width:250px"><%=judul %> </td>
        </tr>
        <tr>
        <td colspan="5">
        <!--  tr id="part1_tr" style="display:table-row" -->
 	
	 
	<form id="form1" enctype="multipart/form-data" method="post" action="go.processUploadFile_v1">
    <div class="row" >
    	<div style="font-size:1.5em;font-weight:bold;text-align:center;background-color:#a6bac4;width:700px;">PILIH FILE NASKAH UJIAN UNTUK DIUPLOAD</div>
      	<label for="fileToUpload" style="font-size:1.5em;color:#900505;font-weight:bold;background-color:#d9e1e5;width:700px;"></label>
      	<input type="file" name="fileToUpload" id="fileToUpload" onchange="fileSelected();" style="font-size:1.5em;color:#900505;font-weight:bold;background-color:#d9e1e5;width:700px;"/>
	</div>
    <div id="fileName"></div>
    <div id="fileSize"></div>
    <div id="fileType"></div>
    <center>
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
  	</center>
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
      <input type="button" id="buttonSubmit" onclick="uploadFile()" value="UPLOAD BUKTI SETORAN" style="font-size:1em;font-weight:bold;width:300px;visibility:hidden"/>
    </div>
    
  </form>
  </td>
  </tr>
  </table>
  
		<%
}
		%>
		
	

		
		
	</div>
</div>	

</body>
</html>