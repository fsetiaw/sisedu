<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="java.io.File" %>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>	
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.dbase.pengajuan.ua.*" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>

<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	Vector vListMakul = (Vector)request.getAttribute("vListMakul"); 
	String infoKur = request.getParameter("infoKur"); 
	
	String kdpst_mask = request.getParameter("kdpst_mask"); //sebagai triger kalo ini dari jalur search
	
	
	Vector v = null;
	boolean addTipe = false;
	boolean addBahan = true;
	boolean iamMhs = false;
	String tkn_tipe_path = null;
	if(validUsr.getObjNickNameGivenObjId().contains("MHS")||validUsr.getObjNickNameGivenObjId().contains("mhs")) {
		iamMhs = true;
		
	}
	Vector vListForMhs = (Vector)session.getAttribute("vListForMhs");
	session.removeAttribute("vListForMhs");
	//String cmd = request.getParameter("cmd");
	//String atMenu = request.getParameter("atMenu");
%>
	<script type="text/javascript" src="<%=Constants.getRootWeb() %>/responsive-css-tabs/js/prefixfree.min.js"></script>
	<link rel="stylesheet" type="text/css" media="all" href="<%=Constants.getRootWeb()%>/responsive-css-tabs/css/styles.css">

</head>
<body>

<div id="header">
<!--  a href="prep.bahanAjarGivenMk?kdkmk=<prev_kdkmk %>&kdpst=<prev_kdpst %>&idkur=idkur %>"><prev_nakmk %></a></B> </label -->
<%

//String uploadToFolder = Checker.getRootPathDir("ARSIP DOSEN")+"/bahan_ajar";

String target_kdkmk = request.getParameter("kdkmk");
String target_nakmk = request.getParameter("nakmk");
String target_kdpst = request.getParameter("kdpst");
String target_idkur = request.getParameter("idkur");
String kdpst_usr = request.getParameter("kdpst_mask");
String target_obj_lvl = request.getParameter("obj_lvl");
String target_npm = request.getParameter("npm");
String target_nmm = request.getParameter("nmm");
String target_id_obj = request.getParameter("id_obj");

if(target_nmm==null || Checker.isStringNullOrEmpty(target_nmm)) {
	target_nmm = new String(validUsr.getNmmhs(validUsr.getNpm()));
}




String atMenu = request.getParameter("atMenu");
boolean showSapGbpp = false;
boolean readOnlySapGbpp = true;
if(validUsr.isAllowTo("sap")>0) {
	/*
	CEK APAKAH USER ADA COMMAND `SAP`
	*/
	if(validUsr.isUsrAllowTo("sap", target_kdpst)) {
		/*
		CEK APAKAH USER BOLEH UPDATE UNTUK TARGET KDPST TERKAIT
		
		*/
		showSapGbpp = true;
		if(!validUsr.isHakAksesReadOnly("sap")){
			readOnlySapGbpp = false;
		}
		else {
			readOnlySapGbpp = false;
		}
	}
	else {
		/*
		CEK APAKAH USER BOLEH UPDATE UNTUK TARGET KDPST TERKAIT
		- BILA TIDAK MAKA STATUSNYA READ ONLY
		CONTOH CASE:
			BILA UNTUK SEMUA TU MEMILIKI COMD `SAP` DGN HAK AKSES REID
			TAPI KALO DIA BUKAN TU KDPST TERKAIT MAKA STATUSNYA DARI REID JADI R
		*/	
		readOnlySapGbpp = true;
		showSapGbpp = true;
	}
}


if(validUsr.isUsrAllowTo("TBA", target_kdpst)) {
	addTipe = true;
	addBahan = true;
}
else if(validUsr.isHakAksesReadOnly("mba")){
	addBahan = false;
}
if(iamMhs) {
	showSapGbpp = false;
	readOnlySapGbpp = false;
	addTipe = false;
	addBahan = false;
}
//request.getRequestDispatcher(url).forward(request,response);
//System.out.println("url1="+url1);
//System.out.println("Prep form bahan ajar jsp");
//System.out.println("target_idkur = "+target_idkur);
//String list_cat_bhn_ajar = Getter.getListKategoriBahanAjar(target_idkur);
String list_cat_bhn_ajar = Getter.getListKategoriBahanAjar(target_kdpst);
//System.out.println("list_cat_bhn_ajar="+list_cat_bhn_ajar);
//*.processUploadGenerikFile
//System.out.println("ini");
//String target1 = Constants.getRootWeb()+"/InnerFrame/Akademik/IndexAkademikSubMenu0.jsp"; //default
//String uri1 = request.getRequestURI(); 
//String url1 = PathFinder.getPath(uri1, target1);

if(kdpst_mask==null || Checker.isStringNullOrEmpty(kdpst_mask)) {
	
	String target1 = Constants.getRootWeb()+"/InnerFrame/Akademik/IndexAkademikSubMenu0.jsp"; 
	String uri1 = request.getRequestURI(); 
	String url1 = PathFinder.getPath(uri1, target1);	
%>
	<jsp:include page="<%=url1 %>" />
<%	
}
else {

%>
	<%@ include file="../../innerMenu.jsp"%>
<%	
}

%>



</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		

		<!-- Column 1 start -->
		        <style>
      /* NOTE: The styles were added inline because Prefixfree needs access to your styles and they must be inlined if they are on local disk! */
//      @import url("http://fonts.googleapis.com/css?family=Open+Sans:400,600,700");
//@import url("http://netdna.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.css");
*, *:before, *:after {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html, body {
  height: 100%;
}

body {
  font: 14px/1 'Open Sans', sans-serif;
  color: #555;
  //background: #eee;
}

h1 {
  padding: 50px 0;
  font-weight: 400;
  text-align: center;
}

p {
  margin: 0 0 20px;
  line-height: 1.5;
}

main {
  min-width: 320px;
  max-width: 1000px;
  padding: 10px;
  margin: 0 auto;
  background: #fff;
}

section {
  display: none;
  padding: 20px 0 0;
  border-top: 1px solid #ddd;
}

input[type=radio] {
   display: none;
}
/*
input {
  display: none;
}
*/


label {
  display: inline-block;
  margin: 0 0 -1px;
  padding: 5px 15px;
  font-weight: 600;
  text-align: center;
  color: #bbb;
  border: 1px solid transparent;
}

label:before {
  font-family: fontawesome;
  font-weight: normal;
  margin-right: 10px;
}

label[for*='1']:before {
  content: '\f1cb';
}

label[for*='2']:before {
  content: '\f17d';
}

label[for*='3']:before {
  content: '\f16b';
}

label[for*='4']:before {
  content: '\f1a9';
}

label[for*='5']:before {
  content: '\f1a9';
}

label:hover {
  color: #888;
  cursor: pointer;
}

input:checked + label {
  color: #555;
  border: 1px solid #ddd;
  border-top: 2px solid orange;
  border-bottom: 1px solid #fff;
}

<%
if(addTipe && !iamMhs) {//kalo addTipe pasti addBahan
	
%>
#tab1:checked ~ #content1,
#tab2:checked ~ #content2,
#tab3:checked ~ #content3,
#tab4:checked ~ #content4,
#tab5:checked ~ #content5,
#tab6:checked ~ #content6 {
  display: block;
}
<%
} 
else if(addBahan && showSapGbpp && !iamMhs){
%>
	#tab1:checked ~ #content1,
	#tab2:checked ~ #content2,
	#tab3:checked ~ #content3,
	#tab4:checked ~ #content4,
	#tab5:checked ~ #content5 {
	  display: block;
	}
<%	
}
else if(showSapGbpp && !iamMhs){
%>	
	#tab2:checked ~ #content2,
	#tab3:checked ~ #content3,
	#tab4:checked ~ #content4,
	#tab5:checked ~ #content5 {
	  display: block;
	}
<%	
}
else {
%>	
	#tab3:checked ~ #content3,
	#tab4:checked ~ #content4,
	#tab5:checked ~ #content5 {
	  display: block;
	}
	
<%		
}

%>

@media screen and (max-width: 650px) {
  label {
    font-size: 0;
  }

  label:before {
    margin: 0;
    font-size: 18px;
  }
}
@media screen and (max-width: 400px) {
  label {
    padding: 15px;
  }
}

    </style>

<main>
 <%
	//if(validUsr.isUsrAllowTo("TBA", target_kdpst)) {
	if(addBahan) {	
%> 
  <input id="tab1" type="radio" name="tabs" checked>
  <label for="tab1">Upload Form</label>
  
  <input id="tab2" type="radio" name="tabs">
  <label for="tab2">GBPP & SAP</label>
  
<%
	}
	else if(showSapGbpp){
%> 
  <input id="tab2" type="radio" name="tabs">
  <label for="tab2">GBPP & SAP</label>

<%		
	}
%>    
  
  <input id="tab3" type="radio" name="tabs">
  <label for="tab3">Silabi & Materi</label>
  
  <input id="tab4" type="radio" name="tabs">
  <label for="tab4">Tugas</label>
    
  <input id="tab5" type="radio" name="tabs">
  <label for="tab5">Ujian & Quiz</label>
<%
	//if(validUsr.isUsrAllowTo("TBA", target_kdpst)) {
	if(addTipe) {	
%>
  <input id="tab6" type="radio" name="tabs">
  <label for="tab6">Add Tipe Bahan Ajar </label>
<%		
	}
%>
  <section id="content1">
  <%
  if(addBahan) {
	  //System.out.println("bener kesinsi");
  
  %>  
    	<h3>FORM UPLOAD FILE BAHAN AJAR<br/><br/>NAMA DOSEN : <%=target_nmm.toUpperCase() %><br/>
    		MATA KULIAH: <%=target_nakmk.toUpperCase() %>
    	</h3>
    	<br/>
    	
    	<%
    	//System.out.print("list_cat_bhn_ajar="+list_cat_bhn_ajar+"<br/>");
    	if(list_cat_bhn_ajar!=null && !Checker.isStringNullOrEmpty(list_cat_bhn_ajar)) {
    		StringTokenizer st = new StringTokenizer(list_cat_bhn_ajar,"`");
    		//go.processUploadGenerikFile_v1?saveToFolder=<%=uploadToFolder
    	%>
    	<form action="go.processUploadGenerikFile_v1" method="post" enctype="multipart/form-data">
    	<input type="hidden" name="target_kdkmk" value="<%=target_kdkmk%>"/>
    	<input type="hidden" name="target_nakmk" value="<%=target_nakmk%>"/>
		<input type="hidden" name="target_kdpst" value="<%=target_kdpst%>"/>
		<input type="hidden" name="target_idkur" value="<%=target_idkur%>"/>
		<input type="hidden" name="atMenu" value="<%=atMenu%>"/>
		
		<input type="hidden" name="kdpst_usr" value="<%=kdpst_usr%>"/>
		<input type="hidden" name="target_obj_lvl" value="<%=target_obj_lvl%>"/>
		<input type="hidden" name="target_npm" value="<%=target_npm%>"/>
		<input type="hidden" name="target_nmm" value="<%=target_nmm%>"/>
		<input type="hidden" name="target_id_obj" value="<%=target_id_obj%>"/>

		
    	<table width="500px">
    		<tr>
    			<td width="50%"><b>Select Tipe Bahan Ajar:</b></td>
    			<td width="50%">
    				<select name="tipe_bahan_ajar" style="width:100%">
    	<%
    		while(st.hasMoreTokens()) {
    			
 				boolean show = false;   		
    			String tkn = st.nextToken();
    			//System.out.println("token = "+tkn);
    			if(addTipe) {//kalo addTipe pasti addBahan
    				show = true;	
    			} 
    			else if(addBahan && showSapGbpp){
    				if(tkn.equalsIgnoreCase("sap") || tkn.equalsIgnoreCase("gbpp")) {
    					if(!readOnlySapGbpp) {	
        					show = true;
        				}
    				}
    				else {
    					show = true;
    				}
    				
    			}
    			if(show) {
    	%>
    					<option value="<%=tkn.toUpperCase() %>"><%= tkn.toUpperCase() %></option>
    	
    	<%		
    			}
    		}
    	%>	
    				</select> 
    			</td>
    		</tr>
    		<tr>
    			<td valign="top"><b>File disimpan dengan nama:</b><br/>
    			<div style="font-size:.8em">Gunakan nama file yang sesuai;<br/>
    			 contoh:<br/>
    				1. Silabi Shift pagi<br/>
    				2. Materi minggu 1</div>
    			</td>
    			<td><input type="text" name="intendedFileName" style="width:99%" placeholder="optional"  />
    				<br/>
    				<div style="font-size:.8em"><sup>*</sup>&nbsp&nbspBiarkan kosong bila nama file sudah sesuai<br/></div><br> 
    				
    			</td>
    		</tr>
    		<tr>	
    			<td><b>Pilih file yang akan di-upload:</b></td>
    			<td><input type="file" name="file" size="100%" required/></td>
    		</tr>
    		<tr>
    			<td colspan="2" align="center"><input type="submit" value="Upload File" style="width:50%;height:30px"/></td>
    		</tr>
    	</table>
    	</form>

    	<%	
    	}
    	else {
    		out.print("tabel KATEGORI BAHAN AJAR belum terisi");
    	}
  }	
    	%>
		
    
  </section>
  
  <section id="content2">
    <p>
      <u>GBPP</u>
      <br/>&nbsp&nbspList Files:
    <%
    File [] gbpp = (File[])session.getAttribute("listFileGbpp");
      //System.out.println("gbpp [] = "+gbpp.length);
    if(gbpp!=null && gbpp.length>0) {
    	for(int i=0;i<gbpp.length;i++) {
    		StringTokenizer st = new StringTokenizer(gbpp[i].getName(),".");
    	%>
    	<br/><a href="go.downloadFileAsIs_v1?alm=<%=gbpp[i].getAbsolutePath() %>&hak=unUsed">&nbsp&nbsp<%=i+1 %>)&nbsp&nbsp<%=st.nextToken()%></a>
    	<%		
    	}    		
    }
    %> 
    </p>
    
    <p>
      <u>SAP</u>
      <br/>&nbsp&nbspList Files:
    <%
    File [] sap = (File[])session.getAttribute("listFileSap");
    if(sap!=null && sap.length>0) {
    	for(int i=0;i<sap.length;i++) {
    		StringTokenizer st = new StringTokenizer(sap[i].getName(),".");
    	%>
    	<br/><a href="go.downloadFileAsIs_v1?alm=<%=sap[i].getAbsolutePath() %>&hak=unUsed">&nbsp&nbsp<%=i+1 %>)&nbsp&nbsp<%=st.nextToken()%></a>
    	<%		
    	}    		
    }
    %> 
    </p>
  </section>
      
  <section id="content3">
    <p>
      <u>Silabi</u>
      <br/>&nbsp&nbspList Files:
    <%
    if(iamMhs) {
    	if(vListForMhs!=null && vListForMhs.size()>0) {
			int j = 0;    	
    		ListIterator liv = vListForMhs.listIterator();
    		while(liv.hasNext()) {
    			String brs = (String)liv.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String tipe = st.nextToken();
    			if(tipe.equalsIgnoreCase("silabi")) {
    				String path = st.nextToken();
        			st = new StringTokenizer(path,"/");
        			String nama_file = "";
        			for(;st.countTokens()!=0;) {
        				nama_file = st.nextToken();
        			}
        			st = new StringTokenizer(nama_file,".");
        			nama_file = st.nextToken();
        			%>
                	<br/><a href="go.downloadFileAsIs_v1?alm=<%=path %>&hak=unUsed">&nbsp&nbsp<%=++j %>)&nbsp&nbsp<%=nama_file%></a>
                	<%	
    			}
    		}
    	}
    }
    else {
    	File [] silabi = (File[])session.getAttribute("listFileSilabi");
        if(silabi!=null && silabi.length>0) {
        	for(int i=0;i<silabi.length;i++) {
        		StringTokenizer st = new StringTokenizer(silabi[i].getName(),".");
        	%>
        	<br/><a href="go.downloadFileAsIs_v1?alm=<%=silabi[i].getAbsolutePath() %>&hak=unUsed">&nbsp&nbsp<%=i+1 %>)&nbsp&nbsp<%=st.nextToken()%></a>
        	<%		
        	}    		
        }	
    }
    
    %> 
    </p>
    
    <p>
      <u>Materi</u> 
      <%
      if(iamMhs) {
    	  if(vListForMhs!=null && vListForMhs.size()>0) {
  			int j = 0;    	
      		ListIterator liv = vListForMhs.listIterator();
      		while(liv.hasNext()) {
      			String brs = (String)liv.next();
      			StringTokenizer st = new StringTokenizer(brs,"`");
      			String tipe = st.nextToken();
      			if(tipe.equalsIgnoreCase("materi")) {
      				String path = st.nextToken();
          			st = new StringTokenizer(path,"/");
          			String nama_file = "";
          			for(;st.countTokens()!=0;) {
          				nama_file = st.nextToken();
          			}
          			st = new StringTokenizer(nama_file,".");
          			nama_file = st.nextToken();
          			%>
                  	<br/><a href="go.downloadFileAsIs_v1?alm=<%=path %>&hak=unUsed">&nbsp&nbsp<%=++j %>)&nbsp&nbsp<%=nama_file%></a>
                  	<%	
      			}
      		}
      	}
      }
      else {
    	  File [] materi = (File[])session.getAttribute("listFileMateri");
          if(materi!=null && materi.length>0) {
          	for(int i=0;i<materi.length;i++) {
          		StringTokenizer st = new StringTokenizer(materi[i].getName(),".");
          	%>
          	<br/><a href="go.downloadFileAsIs_v1?alm=<%=materi[i].getAbsolutePath() %>&hak=unUsed">&nbsp&nbsp<%=i+1 %>)&nbsp&nbsp<%=st.nextToken()%></a>
          	<%		
          	}    		
          }  
      }
      
      %>
   	
    </p>
  </section>
    
  <section id="content4">
    <p>
      <u>Tugas</u> 
      <%
      if(iamMhs) {
    	  if(vListForMhs!=null && vListForMhs.size()>0) {
  			int j = 0;    	
      		ListIterator liv = vListForMhs.listIterator();
      		while(liv.hasNext()) {
      			String brs = (String)liv.next();
      			StringTokenizer st = new StringTokenizer(brs,"`");
      			String tipe = st.nextToken();
      			if(tipe.equalsIgnoreCase("tugas")) {
      				String path = st.nextToken();
          			st = new StringTokenizer(path,"/");
          			String nama_file = "";
          			for(;st.countTokens()!=0;) {
          				nama_file = st.nextToken();
          			}
          			st = new StringTokenizer(nama_file,".");
          			nama_file = st.nextToken();
          			%>
                  	<br/><a href="go.downloadFileAsIs_v1?alm=<%=path %>&hak=unUsed">&nbsp&nbsp<%=++j %>)&nbsp&nbsp<%=nama_file%></a>
                  	<%	
      			}
      		}
      	}	
      }
      else {
    	  File [] tugas = (File[])session.getAttribute("listFileTugas");
          if(tugas!=null && tugas.length>0) {
          	for(int i=0;i<tugas.length;i++) {
          		StringTokenizer st = new StringTokenizer(tugas[i].getName(),".");
          	%>
          	<br/><a href="go.downloadFileAsIs_v1?alm=<%=tugas[i].getAbsolutePath() %>&hak=unUsed">&nbsp&nbsp<%=i+1 %>)&nbsp&nbsp<%=st.nextToken()%></a>
          	<%		
          	}    		
          }  
      }
      
      %>
   	
    </p>
  </section>
    
  <section id="content5">
       <p>
      <u>Quiz</u>
      <br/>&nbsp&nbspList Files:
    <%
    if(iamMhs) {
    	if(vListForMhs!=null && vListForMhs.size()>0) {
			int j = 0;    	
    		ListIterator liv = vListForMhs.listIterator();
    		while(liv.hasNext()) {
    			String brs = (String)liv.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String tipe = st.nextToken();
    			if(tipe.equalsIgnoreCase("quiz")) {
    				String path = st.nextToken();
        			st = new StringTokenizer(path,"/");
        			String nama_file = "";
        			for(;st.countTokens()!=0;) {
        				nama_file = st.nextToken();
        			}
        			st = new StringTokenizer(nama_file,".");
        			nama_file = st.nextToken();
        			%>
                	<br/><a href="go.downloadFileAsIs_v1?alm=<%=path %>&hak=unUsed">&nbsp&nbsp<%=++j %>)&nbsp&nbsp<%=nama_file%></a>
                	<%	
    			}
    		}
    	}
    }
    else {
    	File [] quiz = (File[])session.getAttribute("listFileQuiz");
        if(quiz!=null && quiz.length>0) {
        	for(int i=0;i<quiz.length;i++) {
        		StringTokenizer st = new StringTokenizer(quiz[i].getName(),".");
        	%>
        	<br/><a href="go.downloadFileAsIs_v1?alm=<%=quiz[i].getAbsolutePath() %>&hak=unUsed">&nbsp&nbsp<%=i+1 %>)&nbsp&nbsp<%=st.nextToken()%></a>
        	<%		
        	}    		
        }	
    }
    
    %> 
    </p>
    

    
    <p>
      <u>Ujian Tengah Semester</u>
      <br/>&nbsp&nbspList Files:
    <%
	if(iamMhs) {
		if(vListForMhs!=null && vListForMhs.size()>0) {
			int j = 0;    	
    		ListIterator liv = vListForMhs.listIterator();
    		while(liv.hasNext()) {
    			String brs = (String)liv.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String tipe = st.nextToken();
    			if(tipe.equalsIgnoreCase("UTS")) {
    				String path = st.nextToken();
        			st = new StringTokenizer(path,"/");
        			String nama_file = "";
        			for(;st.countTokens()!=0;) {
        				nama_file = st.nextToken();
        			}
        			st = new StringTokenizer(nama_file,".");
        			nama_file = st.nextToken();
        			%>
                	<br/><a href="go.downloadFileAsIs_v1?alm=<%=path %>&hak=unUsed">&nbsp&nbsp<%=++j %>)&nbsp&nbsp<%=nama_file%></a>
                	<%	
    			}
    		}
    	}
    }
    else {
    	File [] uts = (File[])session.getAttribute("listFileUts");
        if(uts!=null && uts.length>0) {
        	for(int i=0;i<uts.length;i++) {
        		StringTokenizer st = new StringTokenizer(uts[i].getName(),".");
        	%>
        	<br/><a href="go.downloadFileAsIs_v1?alm=<%=uts[i].getAbsolutePath() %>&hak=unUsed">&nbsp&nbsp<%=i+1 %>)&nbsp&nbsp<%=st.nextToken()%></a>
        	<%		
        	}    		
        }	
    }
    
    %> 
    </p>
    
    <p>
      <u>Ujian Akhir Semester</u>
      <br/>&nbsp&nbspList Files:
    <%
	if(iamMhs) {
		if(vListForMhs!=null && vListForMhs.size()>0) {
			int j = 0;    	
    		ListIterator liv = vListForMhs.listIterator();
    		while(liv.hasNext()) {
    			String brs = (String)liv.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String tipe = st.nextToken();
    			if(tipe.equalsIgnoreCase("UAS")) {
    				String path = st.nextToken();
        			st = new StringTokenizer(path,"/");
        			String nama_file = "";
        			for(;st.countTokens()!=0;) {
        				nama_file = st.nextToken();
        			}
        			st = new StringTokenizer(nama_file,".");
        			nama_file = st.nextToken();
        			%>
                	<br/><a href="go.downloadFileAsIs_v1?alm=<%=path %>&hak=unUsed">&nbsp&nbsp<%=++j %>)&nbsp&nbsp<%=nama_file%></a>
                	<%	
    			}
    		}
    	}
    }
    else {
    	File [] uas = (File[])session.getAttribute("listFileUas");
    	   // System.out.println("uas leng = "+uas.length);  
    	if(uas!=null && uas.length>0) {
    	   	for(int i=0;i<uas.length;i++) {
    	   		StringTokenizer st = new StringTokenizer(uas[i].getName(),".");
    	    	%>
    	    	<br/><a href="go.downloadFileAsIs_v1?alm=<%=uas[i].getAbsolutePath() %>&hak=unUsed">&nbsp&nbsp<%=i+1 %>)&nbsp&nbsp<%=st.nextToken()%></a>
    	    	<%		
        	}    		
        }
    }
    
    %> 
    </p>
  </section>
  <section id="content6">
  <%
	if(addTipe) {
  %>
  	<p>
    <h3>FORM ADD TIPE BAHAN AJAR<br/></h3>
    berlaku untuk semua matakuliah<br/>	
    	
    	<br/>
    	
    	<%
    	if(list_cat_bhn_ajar!=null && !Checker.isStringNullOrEmpty(list_cat_bhn_ajar)) {
    		StringTokenizer st = new StringTokenizer(list_cat_bhn_ajar,"`");
    	%>
    	<form action="update.addTipeBahanAjar">
    	<input type="hidden" name="target_kdkmk" value="<%=target_kdkmk%>"/>
    	<input type="hidden" name="target_nakmk" value="<%=target_nakmk%>"/>
		<input type="hidden" name="target_kdpst" value="<%=target_kdpst%>"/>
		<input type="hidden" name="target_idkur" value="<%=target_idkur%>"/>
		<input type="hidden" name="atMenu" value="<%=atMenu%>"/>
    	<table width="500px">
    		<tr>
    			<td width="50%"><b>Nama Tipe Bahan Ajar:</b></td>
    			<td width="50%">
    				<input type="text" name="nu_tipe_bahan_ajar" style="width:100%" required /> 
    			</td>
    		</tr>
    		<tr>
    			<td width="50%"><b>Path Dir:</b></td>
    			<td width="50%">
    				<input type="text" name="path_bahan_ajar" style="width:100%" required /> 
    			</td>
    		</tr>
    		<tr>
    			<td valign="top"><b>Berlaku Untuk Jenjang:</b><br/></td>
    			<td><select name="kode_jen" style="width:100%">
    					<option value="all" selected="selected"> SELURUH JENJANG</option>
    	<%
    			String tkn_kdjen  = Getter.getListKdjenProdi();
    			if(tkn_kdjen!=null && !Checker.isStringNullOrEmpty(tkn_kdjen)) {
    				StringTokenizer stt = new StringTokenizer(tkn_kdjen,"`");
    				while(stt.hasMoreTokens()) {
						String kdjen = stt.nextToken();
						String keter_jen = Converter.getDetailKdjen(kdjen);
						
		%>
						<option value="<%=kdjen %>"><%=keter_jen %></option>
		<%				
					}
    			}	
    	%>	    	
    				</select>			
    			</td>
    		</tr>
    		<tr>
    			<td valign="top"><b>Berlaku Untuk Prodi:</b><br/></td>
    			<td><select name="kode_prodi" style="width:100%">
    					<option value="all" selected="selected"> SELURUH PRODI</option>
    	<%
    			v = Getter.getListProdi();
    			if(v!=null && v.size()>0) {
    				ListIterator li = v.listIterator();
    				while(li.hasNext()) {
						String brs = (String)li.next();
						StringTokenizer stt = new StringTokenizer(brs,"`");
						String kdpst = stt.nextToken();
						String kdfak = stt.nextToken();
						String kdjen = stt.nextToken();
						String nmpst = stt.nextToken();
		%>
						<option value="<%=kdpst %>"><%=nmpst.toUpperCase() %> (<%=Converter.getDetailKdjen(kdjen) %>)</option>
		<%				
					}
    				
    			}
    	%>	    	
    				</select>			
    			</td>
    		</tr>
    		<tr>
    			<td valign="top"><b>Berlaku di Kampus:</b><br/></td>
    			<td><select name="kode_kampus" style="width:100%">
    					<option value="all" selected="selected"> SELURUH KAMPUS</option>
    	<%
    			v = Getter.getListAllKampus(); 
    	//li.add(code_campus+"`"+name_campus+"`"+nickname_campus);
    			if(v!=null && v.size()>0) {
    				ListIterator li = v.listIterator();
    				while(li.hasNext()) {
						String brs = (String)li.next();
						StringTokenizer stt = new StringTokenizer(brs,"`");
						String kdkmp = stt.nextToken();
						String nmkmp = stt.nextToken();
						String nick = stt.nextToken();
						//String nmpst = stt.nextToken();
		%>
						<option value="<%=kdkmp %>"><%=nick.toUpperCase() +" / "+ nmkmp.toUpperCase() %></option>
		<%				
					}
    				
    			}
    	%>	    	
    				</select>			
    			</td>
    		</tr>
    		<tr>
    			<td colspan="2" align="center"><br/><input type="submit" value="Add Tipe Bahan Ajar" style="width:50%;height:30px"/></td>
    		</tr>
    	</table>
    	</form>

    	<%	
    	}
  	}	 

  %>
  </section>

</main>
	

	
    
			
		<!-- Column 1 start -->
	</div>
</div>			
</body>
</html>