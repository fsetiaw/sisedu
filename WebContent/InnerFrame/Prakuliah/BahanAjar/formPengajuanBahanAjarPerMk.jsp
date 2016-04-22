<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="java.io.File" %>
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
	boolean addTipe = false;
	boolean addBahan = true;
	///ToUnivSatyagama/WebContent/InnerFrame/Prakuliah/innerMenu.jsp
	String target1 = Constants.getRootWeb()+"/InnerFrame/Prakuliah/innerMenu.jsp"; 
	String uri1 = request.getRequestURI(); 
	String url1 = PathFinder.getPath(uri1, target1);
	Vector vListKategori = (Vector) session.getAttribute("vListKategori");
	session.removeAttribute("vListKategori");
	//String atMenu= request.getParameter("atMenu");
	Vector vAssigned = (Vector) session.getAttribute("vAssigned");
	session.removeAttribute("vAssigned");
	

	String nakmk= request.getParameter("nakmk");
	String kdkmk= request.getParameter("kdkmk");
	String kdpst= request.getParameter("kdpst");
	String idkur= request.getParameter("idkur");
	String shift= request.getParameter("shift");
	String unique_id = request.getParameter("unique_id");
	String target_kategori = request.getParameter("target_kategori");
	ListIterator lia=null;
	//String cmd = request.getParameter("cmd");
	//String atMenu = request.getParameter("atMenu");
%>
	<script type="text/javascript" src="<%=Constants.getRootWeb() %>/responsive-css-tabs/js/prefixfree.min.js"></script>
	<link rel="stylesheet" type="text/css" media="all" href="<%=Constants.getRootWeb()%>/responsive-css-tabs/css/styles.css">

</head>
<body>
<div id="header">
<!--  a href="prep.bahanAjarGivenMk?kdkmk=<prev_kdkmk %>&kdpst=<prev_kdpst %>&idkur=idkur %>"><prev_nakmk %></a></B> </label -->
<%@ include file="../innerMenu.jsp" %>
<!--  jsp:include page="<%=url1 %>" / -->
<!--  jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Akademik/IndexAkademikSubMenu0Mhs.jsp" /> -->
<%
//System.out.println("ini2");
%>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<h2 style="text-align:center">
		PENGAJUAN RANCANGAN BAHAN AJAR<br/></h2>
		<h3 style="text-align:center">
		KODE MATA KULIAH: <%=kdkmk %>
		<br/>
		NAMA MATA KULIAH: <%=nakmk %>
		<br/>
		(SHIFT <%=shift %>)
		</h3>
		<br/>
		<%
	if(vListKategori!=null && vListKategori.size()>0) {
		ListIterator li = vListKategori.listIterator();
		%>
		<form action="get.listBahanAjarDosen">
		<input type="hidden" name="atMenu" value="pba" />
		<input type="hidden" name="nakmk" value="<%=nakmk %>" />
		<input type="hidden" name="kdkmk" value="<%=kdkmk %>" />
		<input type="hidden" name="kdpst" value="<%=kdpst %>" />
		<input type="hidden" name="idkur" value="<%=idkur %>" />
		<input type="hidden" name="shift" value="<%=shift %>" />
		<input type="hidden" name="unique_id" value="<%=unique_id %>" />
		
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:500px">  
        	<tr> 
           		<td style="background:#369;color:#fff;text-align:center;width:350px">TIPE BAHAN AJAR YG DIAJUKAN</td>
           		<td style="background:#369;color:#fff;text-align:center;width:150px">
           		<select name="target_kategori" style="width:99%" onchange="this.form.submit()">
           		
           		<%
      	String selected_cat = "";
        if(target_kategori==null || Checker.isStringNullOrEmpty(target_kategori)) {
           		%>
           			<option value="null" selected=selected>-- PILIH TIPE --</option>
           		<%	
        }
        else {
           	StringTokenizer st = new StringTokenizer(target_kategori,"`");
           	selected_cat = st.nextToken();
           		%>
           			<option value="null" >-- PILIH TIPE --</option>
           		<%	
       	}
        while(li.hasNext()) {
	  		String brs = (String)li.next();
   			StringTokenizer st = new StringTokenizer(brs,"`");
      		String cat = st.nextToken();
   			String path = st.nextToken();
           	if(cat.equalsIgnoreCase(selected_cat)) {
           			%>
                   	<option value="<%=brs %>" selected="selected"><%=cat %></option>
                  	<%
           	}
           	else {
           			%>
                   	<option value="<%=brs %>"><%=cat %></option>
                   	<%			
           	}
    	}
           		%>
           		</select>
           		</td> 
         	</tr>
         </table>
         </form>
         <%
     	/*
 		getListFileInFolder("/home/usg/USG/ARSIP/arsip_dosen/bahan_ajar/"+kdpst+"/"+kdkmk+"/MATERI/"+isu.getNpm(), "listFileMateri", session);
		getListFileInFolder("/home/usg/USG/ARSIP/arsip_dosen/bahan_ajar/"+kdpst+"/"+kdkmk+"/SILABI/"+isu.getNpm(), "listFileSilabi", session);
		getListFileInFolder("/home/usg/USG/ARSIP/arsip_dosen/bahan_ajar/"+kdpst+"/"+kdkmk+"/QUIZ/"+isu.getNpm(), "listFileQuiz", session);
		getListFileInFolder("/home/usg/USG/ARSIP/arsip_dosen/bahan_ajar/"+kdpst+"/"+kdkmk+"/TUGAS/"+isu.getNpm(), "listFileTugas", session);
		//getListFileInFolder("/home/usg/USG/ARSIP/arsip_dosen/bahan_ajar/"+kdpst+"/"+kdkmk+"/UJIAN/"+isu.getNpm(), "listFileUjian", session);
		getListFileInFolder("/home/usg/USG/ARSIP/arsip_dosen/bahan_ajar/"+kdpst+"/"+kdkmk+"/UAS/"+isu.getNpm(), "listFileUas", session);
		getListFileInFolder("/home/usg/USG/ARSIP/arsip_dosen/bahan_ajar/"+kdpst+"/"+kdkmk+"/UTS/"+isu.getNpm(), "listFileUts", session);
		getListFileInFolder("/home/usg/USG/ARSIP/arsip_dosen/bahan_ajar/"+kdpst+"/"+kdkmk+"/SAP/"+isu.getNpm(), "listFileSap", session);
		getListFileInFolder("/home/usg/USG/ARSIP/arsip_dosen/bahan_ajar/"+kdpst+"/"+kdkmk+"/GBPP/"+isu.getNpm(), "listFileGbpp", session);

 		*/

       	File [] items = null;
        if(!Checker.isStringNullOrEmpty(selected_cat)) {
        	/*
        	!!!!!! PERHATIAN  !!!!!!!
        	KEDEPANNYA BISA DIGANTI AGAR VALUE TIPE DAN SESSION DIBACA DARI TABEL KATEGOR_BAHAN_AJAR
        	*/
        	if(selected_cat.equalsIgnoreCase("MATERI")) {
        		 items = (File[])session.getAttribute("listFileMateri");
        	}
        	else if(selected_cat.equalsIgnoreCase("SILABI")) {
       		 	items = (File[])session.getAttribute("listFileSilabi");    		
        	}
        	else if(selected_cat.equalsIgnoreCase("QUIZ")) {
       		 	items = (File[])session.getAttribute("listFileQuiz");    		
        	}
        	else if(selected_cat.equalsIgnoreCase("UAS")) {
       		 	items = (File[])session.getAttribute("listFileUas");    		
        	}
        	else if(selected_cat.equalsIgnoreCase("UTS")) {
       		 	items = (File[])session.getAttribute("listFileUts");    		
        	}
        	else if(selected_cat.equalsIgnoreCase("TUGAS")) {
       		 	items = (File[])session.getAttribute("listFileTugas");    		
        	}
       	%>
         <p style="text-align:center">
         PERHATIAN !!<br/>
         Bahan Ajar yang telah dicentang, dapat dilihat oleh mahasiswa sehingga harap diperhatikan waktu pencentangan <br/>
         untuk materi yang bersifat UJIAN, seperti Quiz, UAS, UTS, dll<br/>
         </p>
         <%
         boolean kosong = true;
         %>
         <form action="go.assignBahanAjar">
         	<input type="hidden" name="tipe" value="<%=selected_cat %>" />
         	<input type="hidden" name="atMenu" value="pba" />
			<input type="hidden" name="nakmk" value="<%=nakmk %>" />
			<input type="hidden" name="kdkmk" value="<%=kdkmk %>" />
			<input type="hidden" name="kdpst" value="<%=kdpst %>" />
			<input type="hidden" name="idkur" value="<%=idkur %>" />
			<input type="hidden" name="shift" value="<%=shift %>" />
			<input type="hidden" name="unique_id" value="<%=unique_id %>" />
         <table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:500px">  
        	<tr> 
           		<td style="background:#369;color:#fff;text-align:center;width:500px" colspan="3">PILIHAN <%=selected_cat.toUpperCase() %></td>
           	</tr>
           	<tr> 
           		<td style="background:#369;color:#fff;text-align:center;width:15px">No.</td>
           		<td style="background:#369;color:#fff;text-align:center;width:450px">Nama File</td>
           		<td style="background:#369;color:#fff;text-align:center;width:35px">Pilih</td>
           	</tr>
           	<%
           	if(items==null || items.length<1) {	
           		
           	%>
           	<tr>
           		<td style="text-align:center;width:500px" colspan="3">file <%=selected_cat %> tidak ditemukan</td>
           	</tr>  		
         	<%
        	}
           	else {
           		kosong = false;
           		for(int i=0;i<items.length;i++) {
           			StringTokenizer st = new StringTokenizer(items[i].getName(),".");
           			String target_path = new String(items[i].getAbsolutePath());
           	%>
            <tr>
            	<td style="text-align:center"><%=i+1 %></td>
           		<td style="text-align:left;padding:0 0 0 10px"><%=st.nextToken() %> </td>
           		<td style="text-align:center;">
           	<%
           	boolean match = false;
           //	System.out.println("vAssigned size = "+vAssigned.size());
           			if(vAssigned!=null && vAssigned.size()>0) {
           				
           				//System.out.println("match0 = "+ match);
           			 	lia = vAssigned.listIterator();
           			 	while(lia.hasNext()&&!match) {
	           				String brs = (String)lia.next();
    	       				StringTokenizer st2 = new StringTokenizer(brs,"`");
        	   				String tipe_a = st2.nextToken();
           					String path_a = st2.nextToken();
           					//System.out.println(target_path+" vs "+ path_a);
           					//System.out.println("target_path length =" +target_path.length());
           					//System.out.println("path_a length =" +path_a.length());
           					if(target_path.replace(" " , "").contains(path_a.replace(" ", ""))) {
           						match = true;
           					//	System.out.println("match = "+ match);
           					}
           					//else {
           					//	System.out.println("match = "+ !match);
           					//}	
           				}
           			}	
           			 	
           			if(!match) {
           	           	%>
           	  		<input type="checkbox" name="file_path" value="<%=items[i].getAbsolutePath() %>">
           	           	<%		
           	  		}
               		else {
                     		%>
                   	<input type="checkbox" name="file_path" value="<%=items[i].getAbsolutePath() %> " checked="checked" >
           	               	<%		
           	       	}
           			
           		
           	%>	
           		
           		</td>
            </tr>  		
            <%
           		}
           	}
           	if(!kosong) {
           	%>
           	<tr>
           		<td colspan="3" style="text-align:center;background:#369;">
           			<input type="submit" name="submit" value="Update Bahan Ajar" style="width:50%"/>
           		</td>
           	</tr>	
           	<%	
           	}
         %>
         </table>
         </form>
         <%  	
      	}
	}
	else {
		out.println("belum dada kategori harusnya sudah di redirect @servlet");
	}

		%>
		

		<!-- Column 1 start -->
	</div>
</div>			
</body>
</html>