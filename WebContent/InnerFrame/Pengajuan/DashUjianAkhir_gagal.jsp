<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<title>Insert title here</title>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>	
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
<script src="<%=Constants.getRootWeb() %>/js/jquery.form.js"></script> 
<%
//System.out.println("siop");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v = null;
//long millis = System.currentTimeMillis();
%>


<script type="text/javascript">
	var objId="<%=""%>";
	var nmm="<%=""%>";
	var npm="<%=""%>";
	var obj_lvl="<%=""%>";
	var kdpst="<%=""%>";

	
	$(document).ready(function()
	{
		
		
		$("#buttonPart1").click(function()
		//$("#formUpload1").bind("submit",function()
				//$("#formUpload1").submit(function (event) 	
		{
			$.post( 'go.processUploadFileTamplete', $('#formUpload1').serialize(), function(data) {
				if(data && data !="") {
					document.getElementById('div_msg').style.visibility='visible';
					
					$('#div_msg').html(); 
					$('#div_msg').html(data); 
				}
				else {
					document.getElementById('div_msg').style.visibility='hidden';
					document.getElementById('part1_tr').style.display='none';
					document.getElementById('part2_tr').style.display='table-row';
					//document.getElementById('buttonPart1').style.visibility='hidden';
					//document.getElementById('div_part2').style.visibility='visible';
	
					//alert("oke");
				}
			});
					
		});	
		
		$("#somebutton").bind("click",function()
		{
			uploadFile();
		});
		
	
		//$("#somebutton").click(function()
		//{
					//$.get('go.uploadFile2', function(responseText) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
			            //             // Locate HTML DOM element with ID "somediv" and set its text content with the response text.

			//$.post( 'go.processUploadFileTamplete', $('#formUpload1').serialize(), function(data) {
			//$.post( 'go.processUploadFileTamplete', $('#formUpload1').serialize(), function(data) {
			//        document.getElementById('div_msg').style.visibility='visible';
			//        	$('#div_msg').html(); 
			 //       	$('#div_msg').html(data); 
			//});
			
		//});
					

			
	});	
					
		
		
	function uploadFile() {
        var fd = new FormData();
        fd.append("fileToUpload_String_Opt", document.getElementById('fileToUpload_String_Opt').files[0]);
        
        //masukkan juga form non file
        /*
        fd.append("Tipe-Ujian-Akhir_Selection_Wajib", document.getElementById('Tipe-Ujian-Akhir_Selection_Wajib').value );
        fd.append("Judul-Naskah-Ujian_String_Wajib", document.getElementById('Judul-Naskah-Ujian_String_Wajib').value );
        fd.append("npmhs_String_Opt", document.getElementById('npmhs_String_Opt').value );
        
       	fd.append("idobj_String_Opt", document.getElementById('idobj_String_Opt').value );
        fd.append("nmmhs_String_Opt", document.getElementById('nmmhs_String_Opt').value );
        fd.append("objlv_String_Opt", document.getElementById('objlv_String_Opt').value );
        fd.append("cmd_String_Opt", document.getElementById('cmd_String_Opt').value );
*/
        
        var xhr = new XMLHttpRequest();
        xhr.upload.addEventListener("progress", uploadProgress, false);
        xhr.addEventListener("load", uploadComplete, false);
        xhr.addEventListener("error", uploadFailed, false);
        xhr.addEventListener("abort", uploadCanceled, false);
        xhr.open("POST", "go.processUploadFileTamplete");
        xhr.send(fd);
   	}	
		
  	function fileSelected() {
     	var file = document.getElementById('fileToUpload_String_Opt').files[0];
      
        if (file) {
          var fileSize = 0;
          if (file.size > 1024 * 1024) {
            fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
          }  
          else {
            fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';
          }
          		document.getElementById('fileName').innerHTML = 'Name: ' + file.name;
          		document.getElementById('fileSize').innerHTML = 'Size: ' + fileSize;
          		document.getElementById('fileType').innerHTML = 'Type: ' + file.type;
          		document.getElementById('row').style.visibility='visible';
          
          		//document.getElementById('somebutton').style.visibility='visible';
          		
          		//show lg msg utk fwd proses
          		//document.getElementById('div_msg').style.visibility='visible';
   		}
    }
  	

      function uploadProgress(evt) {
        if (evt.lengthComputable) {
          var percentComplete = Math.round(evt.loaded * 100 / evt.total);
          document.getElementById('progressNumber').innerHTML = percentComplete.toString() + '%';
          document.getElementById('pb').style.visibility='visible';
          //document.getElementById('pb1').style.visibility='visible';
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
        //alert("selesai gimana");
        <%
        	System.out.println("pos1");
        %>
      	//location.replace("http://www.google.co.id");
        window.location.replace("get.profile?id_obj="+v_id_obj+"&nmm="+v_nmmhs+"&npm="+v_npmhs+"&obj_lvl="+v_obj_lvl+"&kdpst="+kdpst+"&cmd=dashboard");
        <%
    	System.out.println("pos2");
    	%>
      }

      function uploadFailed(evt) {
        alert("Bukti Setoran Gagal Di Upload");
      	//window.location.replace("get.histPymnt?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&atMenu=riwayatBayaran");
      }

      function uploadCanceled(evt) {
        alert("Proses Upload Dibatalkan Oleh Pengguna atau Koneksi Internet Terputus");
      	//window.location.replace("get.histPymnt?id_obj="+objId+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&atMenu=riwayatBayaran");
      }
</script>      
</head>
<body>
<div id="header">
<%@ include file="innerMenu.jsp"%>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
<% 
//out.println("milis="+millis);
//session.setAttribute("target_npm", v_npmhs);
Vector vTipeUa= Getter.getListJenisMakulUjian(kdpst); 
if(vTipeUa==null || vTipeUa.size()<1) {
	out.print("belum ada jenis");
}
else {
	//Vector vInfo_jenis_makul = Getter.getListJenisMakul();
	//format(id+"`"+keter+"`"+kode);
	
			System.out.println("id_obj="+v_id_obj);
			System.out.println("v_nmmhs="+v_nmmhs);
			System.out.println("npm="+v_npmhs);
			System.out.println("v_obj_lvl="+v_obj_lvl);
			System.out.println("kdpst="+kdpst);
			System.out.println("cmd="+cmd);
%>
	<!--  form id="form1" enctype="multipart/form-data" method="post" action="go.processUploadFileTamplete" -->
	<form id="formUpload1"  enctype="multipart/form-data" methode="POST">
		<input type="hidden" name="npmhs_String_Opt" id="npmhs_String_Opt" value="<%=v_npmhs %>" />
		<input type="hidden" name="kdpst_String_Opt" id="npmhs_String_Opt" value="<%=v_kdpst %>" />
		<input type="hidden" name="idobj_String_Opt" id="idobj_String_Opt" value="<%=v_id_obj %>" />
		<input type="hidden" name="nmmhs_String_Opt" id="nmmhs_String_Opt" value="<%=v_nmmhs %>" />
		<input type="hidden" name="objlv_String_Opt" id="objlv_String_Opt" value="<%=v_obj_lvl %>" />
		<input type="hidden" name="cmd_String_Opt" id="cmd_String_Opt" value="dashboard" />

	

		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:500px"> 
        <tr> 
        	<td style="background:#369;color:#fff;text-align:center" colspan="5"><label><B>PENGAJUAN UJIAN / SIDANG</B> </label></td> 
        </tr> 
        <tr> 
            <td style="background:#369;color:#fff;text-align:center;width:50%">Tipe Ujian Akhir</td>
            <td style="text-align:center;width:250px">
            <select id="Tipe-Ujian-Akhir_Selection_Wajib" name="Tipe-Ujian-Akhir_Selection_Wajib" style="width:99%">
            	<option value="<%="null" %>">Pilih Tipe Ujian</option>
<%
	ListIterator li = vTipeUa.listIterator();
	while(li.hasNext()) {
		String list_mk_ujian = (String)li.next();
		StringTokenizer st = new StringTokenizer(list_mk_ujian,"`");
		String idkmk = st.nextToken();
		String kdkmk = st.nextToken();
		String nakmk = st.nextToken();
%>
				<option value="<%=idkmk %>`<%=kdkmk %>`<%=nakmk %>"><%=nakmk %></option>
<%		
	}
	
%>            	
            </select>
            </td> 
        </tr> 
        <tr> 
            <td style="background:#369;color:#fff;text-align:center;width:50%">Judul Naskah Ujian</td>
            <td style="text-align:center;width:250px">
            	<input type="text" name="Judul-Naskah-Ujian_String_Wajib" id="Judul-Naskah-Ujian_String_Wajib" style="width:99%" placeholder="wajib diisi" />
            </td>
        </tr>
        
        <tr id="part1_tr" style="display:table-row">
        
        	<td colspan="2" style="text-align:right">
        		
        		<input type="button" id="buttonPart1" value="Next" style="width:50%;height:35px;margin:5px;visibility:visible"/>
        	</td>
        
        </tr>
      
        <tr id="part2_tr" style="display:none">
        	<td colspan="2"]>
        		
    			<div class="row" >
    			<div style="font-size:1.5em;font-weight:bold;text-align:center;background-color:#a6bac4;width:700px;">PILIH FILE NASKAH UJIAN ANDA</div>
      				<label for="fileToUpload" style="font-size:1.5em;color:#900505;font-weight:bold;background-color:#d9e1e5;width:700px;"></label>
      				<input type="file" name="fileToUpload_String_Opt" id="fileToUpload_String_Opt" onchange="fileSelected();" style="font-size:1.5em;color:#900505;font-weight:bold;background-color:#d9e1e5;width:700px;"/>
				</div>
    			<div id="fileName"></div>
    			<div id="fileSize"></div>
    			<div id="fileType"></div>
    			<br/>
    			
  				<br/>
<%
	if(validUsr.getObjNickNameGivenObjId().contains("MHS")|| validUsr.getObjNickNameGivenObjId().contains("mhs")) {
%>    
    			<div  id="row"  class="row" style="visibility:hidden;font-size:1.5em;color:#900505;font-weight:bold;background-color:#a6bac4;width:700px;text-align:center">
<%
	}
	else {
%>    
    			<div  id="row"  class="row" style="font-size:1.5em;color:#900505;font-weight:bold;background-color:#a6bac4;width:700px;text-align:center">
<%
	}
%>    
					<!--  input type="submit"  value="submit" style="width:50%;height:25px;margin:5px;"/ -->
					
					<!--  input type="button" id="somebutton" onclick="uploadFile()" value="Upload File" style="width:50%;height:35px;margin:5px;visibility:hidden"/ -->
					<input type="button" id="somebutton" value="Upload File" style="width:50%;height:35px;margin:5px;"/>
					<br/>
      				<!--  input type="button" id="buttonSubmit" onclick="uploadFile()" value="UPLOAD NASKAH UJIAN" style="font-size:1em;font-weight:bold;width:300px;"/ -->
    			</div>
    		</td>
    	</tr>
    	</table>
	</form> 
		<br />
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
}
		%>
		
			<div id="div_msg" style="font-style:italic;color:red;text-align:left;background-color:#d9e1e5;width:500px;height:100%;visibility:hidden;margin:0px 0 0 218px;" ></div>

		
		
	</div>
</div>	

</body>
</html>