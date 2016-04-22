<!DOCTYPE html>
<head>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<%@ page import="beans.tools.*" %>
<%@ page import="java.io.File" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>	
<%@ page import="beans.tools.*" %>

<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
	
	<!--  link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/css/folderIcon/folderStyle.css" media="screen" / -->
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"> </script>	
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/css/bootstrap.min.css">
<script src="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/css/folderIcon/folderStyleVer2.css" media="screen" />
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
	Vector v = null;
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	//File[] listFileFolder = (File[])session.getAttribute("listFileFolder");
	String list_tipe_personal_folder= request.getParameter("list_tipe_personal_folder");
	String root_individual_folder = request.getParameter("root_individual_folder");
	String list_folder_at_root_dir = request.getParameter("list_folder_at_root_dir");
	String list_hidden_folder = request.getParameter("list_hidden_folder");
	//System.out.println("list_folder_at_root_dir="+list_folder_at_root_dir);
	//System.out.println("list_hidden_folder="+list_hidden_folder);
	//String nmm = request.getParameter("nmm");
	//Vector v_nm_alm_access = (Vector) session.getAttribute("v_nm_alm_access");
%>

</head>
<body>
<div id="header">
<%@ include file="../../innerMenu.jsp"%>
</div>
<%
boolean akses_to_hidden = false;
if(validUsr.isUsrAllowTo_updated("hfd", npm)) {
	akses_to_hidden = true;
}
if(!akses_to_hidden) {
	//tidak boleh mengakses hidden
	//maka list_folder_at_root_dir harus di periksa dan remove folder hidden
	if(list_folder_at_root_dir!=null && !Checker.isStringNullOrEmpty(list_folder_at_root_dir)) {
		
		StringTokenizer stt = new StringTokenizer(list_folder_at_root_dir,"`");
		list_folder_at_root_dir = "";
		while(stt.hasMoreTokens()) {
			String folder_nm = stt.nextToken();
			StringTokenizer sth = new StringTokenizer(list_hidden_folder,"`");
			boolean match = false;
			while(sth.hasMoreTokens() && !match) {
				String hd_folde = sth.nextToken();
				if(hd_folde.equalsIgnoreCase(folder_nm)) {
					match = true;
				}
			}
			if(!match) {
				//berarti bukan folder hidden jadi boleh dilihat
				list_folder_at_root_dir=list_folder_at_root_dir+"`"+folder_nm;
			}
		}
	}
}
%>
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
<br/>	
<div class="container">
	<br/>

 	<div class="row">
    	
<!--  table style="table-layout: fixed;" width="100%"  cellpadding="10">
	<tr -->
		<!-- Column 1 start -->
<%
//if(list_tipe_personal_folder!=null && !Checker.isStringNullOrEmpty(list_tipe_personal_folder)) {
if(list_folder_at_root_dir!=null && !Checker.isStringNullOrEmpty(list_folder_at_root_dir)) {

	  		
	int j = 0;
	//ListIterator li = v_nm_alm_access.listIterator();
	StringTokenizer st = new StringTokenizer(list_folder_at_root_dir,"`");
	while(st.hasMoreTokens()) {
		
		//String nama_folder = st.nextToken();
		//StringTokenizer st = new StringTokenizer("","`");
		String keter_folder = st.nextToken();
		String alamat_folder = root_individual_folder+"/"+keter_folder;
		StringTokenizer st1 = new StringTokenizer(list_tipe_personal_folder,"`");
		boolean match = false;
		while(st1.hasMoreTokens() && !match) {
			if(keter_folder.equalsIgnoreCase(st1.nextToken())) {
				match = true;
			}
		}
		String nick_and_hak = "";
		if(match) {
			nick_and_hak = "r`e`i`d";
		}
		else { //kalo ngga match berarti foldernya read only karena ada interface lainnya
			nick_and_hak = "r";
		}
		if(validUsr.getObjNickNameGivenObjId().contains("MHS")||validUsr.getObjNickNameGivenObjId().contains("mhs")) {
			//KALO MHS HANYA READ ONLY
			nick_and_hak = "r";
		}
	//only show directory
		File file = new File(alamat_folder);
		if (file.isDirectory()) {
		//if(true) {	
			//j++;
			//if(j%4==0) {
			StringTokenizer sth = new StringTokenizer(list_hidden_folder,"`");
			boolean hidden_folder = false;
			while(sth.hasMoreTokens() && !match) {
				String hd_folde = sth.nextToken();
				if(hd_folde.equalsIgnoreCase(keter_folder)) {
					hidden_folder = true;
				}
			}		
%>
	<!--  /tr>		
	<tr-->
<%				
			//}
            //out.print("directory:"+listFileFolder[i].getName()+"<br/>");
            //
%>
	<div class="col-sm-4">
	<%
		if(hidden_folder) {
			if(akses_to_hidden) {
				nick_and_hak ="r-e-i-d"; 
			}
	
	%>
	  <a href="get.folderContent_v1?hidden_folder=true&list_hidden_folder=<%=list_hidden_folder %>&list_tipe_personal_folder=<%=list_tipe_personal_folder %>&list_folder_at_root_dir=<%=list_folder_at_root_dir %>&id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=arsip&root_dir=<%=root_individual_folder %>&keter=<%=keter_folder %>&alm=<%=alamat_folder %>&hak=<%=nick_and_hak.replace("`", "-") %>" class="docFolderRed"></a><div style="text-align:left"><%= keter_folder%></div>
	<%
		}
		else {
	%>
      <a href="get.folderContent_v1?hidden_folder=false&list_hidden_folder=<%=list_hidden_folder %>&list_tipe_personal_folder=<%=list_tipe_personal_folder %>&list_folder_at_root_dir=<%=list_folder_at_root_dir %>&id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=arsip&root_dir=<%=root_individual_folder %>&keter=<%=keter_folder %>&alm=<%=alamat_folder %>&hak=<%=nick_and_hak %>" class="docFolderBlue"></a><div style="text-align:left"><%= keter_folder%></div>
    <%
		}
    %>
    </div>
	<!--  td width="200px" style="word-wrap: break-word"-->

    
    <!--  /td-->
<%            
            
        //} else {
        	//j++;
			//if(j%4==0) {
%>
	<!--  /tr>
	<tr -->
<%				
			//}        
%>
		<!--  td width="200px" style="word-wrap: break-word" -->
    		<!--  a href="#" class="docIcon"></a><div style="text-align:left"><%= "none"%></div -->
    	<!--  /td -->
<%
        }

	}
}	
%>		

	
		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>