<!DOCTYPE html>
<head>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<%@ page import="beans.tools.*" %>
<%@ page import="java.io.File" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/css/folderIcon/folderStyleVer2.css" media="screen" />
	<!--  link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/css/folderIcon/folderStyle.css" media="screen" / -->
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"> </script>	
<script type="text/javascript">
//setTimeout(popup, 3000); // Setting time 3s to popup login form
function popup() {
	$("#logindiv").css("display", "inline");
}

$("#onclick").click(function() {
	$("#contactdiv").css("display", "inline");
});
	
$(document).ready(function() {
//	setTimeout(popup, 3000);
	function popup() {
		$("#logindiv").css("display", "inline");
	}
	$("#login #cancel").click(function() {
		$(this).parent().parent().hide();
	});
	
	
	// Login form popup login-button click event.
	$("#loginbtn").click(function() {
		var nameFolder = $("#username").val();
	//var password = $("#password").val();
	//if (username == "" || password == ""){
		if (nameFolder == ""){
			alert("Nama Folder Kosong atau Sudah Digunakan");
		}
		else{
			//$("#logindiv").css("display", "inline");
			$.post('file.createNuFolder', $('#login').serialize(), function(data) {

			});
		}
	});
});	
</script>


<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	//File[] listFileFolder = (File[])session.getAttribute("listFileFolder");
	Vector v_nm_alm_access = (Vector) session.getAttribute("v_nm_alm_access");
%>

</head>
<body>
<div id="header">

</div>

<!--  div class="colmask fullpage">
	<div class="col1">
		<div id="mainform">
<h2>jQuery Popup Form Example</h2>
<!-- Required Div Starts Here 
<div class="form" id="popup">
<b>1.Onload Popup Login Form</b><br/><hr/>
<span>Wait for 3 second.Login Popup form Will appears.</span><br/><br/><br/>
<b>2.Onclick Popup Contact Form</b><hr/>
<p id="onclick">Popup</p>
</div 
</div-->
<!-- Contact Form -->
<div id="contactdiv">
</form>
</div>
<!--Login Form -->
<div id="logindiv">
<form class="form" action="#" id="login" name="login"  method="post">
	<img src="<%=Constants.getRootWeb() %>/css/folderIcon/Cancel3.png" class="img" id="cancel"/>
	<label>Buat Folder Baru </label><br/>
	<input type="text" id="username" name="foldername" placeholder="Nama Folder Baru"/>
	<input type="hidden" name="currentFolder" value="/home/usg/USG/ARSIP"/>
	<input type="button" id="loginbtn" name="loginbtn" value="Buat Folder"/>
<!--  input type="button" id="cancel" value="Batal"/-->
<!--  label>password : </label>
<input type="text" id="password" placeholder="************"/>
<input type="button" id="loginbtn" value="Login"/>
<input type="button" id="cancel" value="Cancel"/ -->
</form>
</div>
		
		
		<br />
<%
if(false) {
%>		
<div id="mainform">
	<a href="javascript:popup()"><img style="border:0;" src="<%=Constants.getRootWeb() %>/css/folderIcon/Grey_Add_Folder.png" alt="Folder Baru" ></a>
</div>	
<%
}
%>	

<br/>		
<table style="table-layout: fixed;" width="100%"  cellpadding="10">
	<tr>
		<!-- Column 1 start -->
<%
//if(listFileFolder!=null && listFileFolder.length>0) {
  if(v_nm_alm_access!=null && v_nm_alm_access.size()>0) {		
	int j = 0;
	ListIterator li = v_nm_alm_access.listIterator();
	for(int i=0;i<v_nm_alm_access.size();i++) {
		
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		String keter_folder = st.nextToken();
		String alamat_folder = st.nextToken();
		//String nick_and_hak = st.nextToken();
		String hak_akses = st.nextToken();
	//only show directory	
		//if (listFileFolder[i].isDirectory()) {
		if(true) {	
			j++;
			if(j%4==0) {
%>
	</tr>		
	<tr>
<%				
			}
            //out.print("directory:"+listFileFolder[i].getName()+"<br/>");
%>
	
	<td width="200px" style="word-wrap: break-word">

    <a href="get.folderContent?root_dir=<%=alamat_folder %>&keter=<%=keter_folder %>&alm=<%=alamat_folder %>&hak=<%=hak_akses %>" class="docFolder"></a><div style="text-align:left"><%= keter_folder%></div>
    </td>
<%            
            
        } else {
        	j++;
			if(j%4==0) {
%>
	</tr>
	<tr>
<%				
			}        
%>
		<td width="200px" style="word-wrap: break-word">
    		<a href="#" class="docIcon"></a><div style="text-align:left"><%= "none"%></div>
    	</td>
<%
        }

	}
}	
%>		
	</tr>
</table>
	</div>
		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>