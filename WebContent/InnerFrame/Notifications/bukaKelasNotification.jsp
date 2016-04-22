<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector vsc = validUsr.getScopeCommandLikeProdiOnly("BukaKelas");//list seluruh scope kdpst prodi only
Vector vSum = (Vector) request.getAttribute("vSum");
Vector vKampus = (Vector) request.getAttribute("vKampus");
String listKdpstBk = (String) request.getAttribute("listKdpstBk");
String kode_kampus_req = (String) request.getParameter("kode_kampus");
String nick_kampus_req = (String) request.getParameter("nick_kampus");
//String infoKdpstNoPengajuan = (String) request.getParameter("infoKdpstNoPengajuan");
//ListIterator liSum = vSum.listIterator();
//while(liSum.hasNext()) {
//	System.out.println("lisum="+(String)liSum.next());
//}

String kdjenKdpstNmpstNoPengajuan = request.getParameter("infoKdpstNoPengajuan");
String thsmsBukaKelas = request.getParameter("thsmsBukaKelas");
//String thsmsBukaKelas = Constants.getThsmsBukaKelas();
//System.out.println("infoKdpstNoPengajuan="+kdjenKdpstNmpstNoPengajuan);
Vector vBk = (Vector) session.getAttribute("vBukaKelas");
String cmd1 = null;
String idkur1 = null;
String idkmk1 = null;
String thsms1 = null;
String kdpst1 = null;
String shift1 = null;
String noKlsPll1 = null;
String initNpmInput1 = null;
String latestNpmUpdate1 = null;
String latestStatusInfo1 = null;
String locked1 = null;
String npmdos1 = null;
String nodos1 = null;
String npmasdos1 = null;
String noasdos1 = null;
String cancel = null;
String kodeKelas1 = null;
String kodeRuang1 = null;
String kodeGedung1 = null;
String kodeKampus1 = null;
String tknHrTime1 = null;
String nmmdos1 = null;
String nmmasdos1 = null;
String enrolled1 = null;
String maxEnrol1 = null;
String minEnrol1 = null;
String subKeterMk1 = null;
String initReqTime1 = null;
String tknNpmApproval1 = null;
String tknApprovalTime1 = null;
String targetTotMhs1 = null;
String passed1 = null;
String rejected1 = null;
String initKodeKampus = null;
String kode_kampus = null;
String nick_kampus = null;
String konsen1 = null;

//ListIterator liBk = vBk.listIterator();
//while(liBk.hasNext()) {
//	System.out.println("liBk="+(String)liBk.next());
//}
//System.out.println("vkm="+vBk.size());


%>
</head>
<body>
<div id="header">
	<ul>
		<li><a href="get.notifications" target="inner_iframe">GO<span>BACK</span></a></li>
<%
if(kode_kampus_req==null) {
	ListIterator likps = vKampus.listIterator();
	boolean first = true;

	while(likps.hasNext()) {
		String tmp = (String)likps.next();
		if(first) {
			first = false;
			initKodeKampus = ""+tmp;
			//kode_kampus_req = ""+tmp;
			StringTokenizer st = new StringTokenizer(initKodeKampus,"`");
			kode_kampus = st.nextToken();
			nick_kampus = st.nextToken();
	%>
			<li><a href="process.statusRequestBukaKelas?listKdpstBk=<%=""+listKdpstBk %>&infoKdpstNoPengajuan=<%=""+kdjenKdpstNmpstNoPengajuan %>&kode_kampus=<%=kode_kampus %>" target="inner_iframe" class="active">KAMPUS<span><%=nick_kampus.replace("KAMPUS ", "") %></span></a></li>
	<%		
		}
		else {
			StringTokenizer st = new StringTokenizer(tmp,"`");
			String kode_kampus_tmp = st.nextToken();
			String nick_kampus_tmp = st.nextToken();
	%>
			<li><a href="process.statusRequestBukaKelas?listKdpstBk=<%=""+listKdpstBk %>&infoKdpstNoPengajuan=<%=""+kdjenKdpstNmpstNoPengajuan %>&kode_kampus=<%=kode_kampus_tmp %>" target="inner_iframe">KAMPUS<span><%=nick_kampus_tmp.replace("KAMPUS ", "") %></span></a></li>
	<%			
		}	
	}
}
else {
	ListIterator likps = vKampus.listIterator();
	boolean first = true;

	while(likps.hasNext()) {
		String tmp = (String)likps.next();
		StringTokenizer st = new StringTokenizer(tmp,"`");
		String kode_kampus_tmp = st.nextToken();
		String nick_kampus_tmp = st.nextToken();
		if(kode_kampus_req.equals(kode_kampus_tmp)) {
			kode_kampus = ""+kode_kampus_tmp;
			nick_kampus = ""+nick_kampus_tmp;
	%>
			<li><a href="process.statusRequestBukaKelas?listKdpstBk=<%=""+listKdpstBk %>&infoKdpstNoPengajuan=<%=""+kdjenKdpstNmpstNoPengajuan %>&kode_kampus=<%=kode_kampus %>" target="inner_iframe" class="active">KAMPUS<span><%=nick_kampus.replace("KAMPUS ", "") %></span></a></li>
	<%	
		}
		else {
	%>
			<li><a href="process.statusRequestBukaKelas?listKdpstBk=<%=""+listKdpstBk %>&infoKdpstNoPengajuan=<%=""+kdjenKdpstNmpstNoPengajuan %>&kode_kampus=<%=kode_kampus_tmp %>" target="inner_iframe" >KAMPUS<span><%=nick_kampus_tmp.replace("KAMPUS ", "") %></span></a></li>
	<%		
		}
	}	
}

%>		
		
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
<%
ListIterator lisc1 = null;

%>
		<%

	int no = 0;
	if((vSum!=null && vSum.size()>0)||(kdjenKdpstNmpstNoPengajuan!=null && !Checker.isStringNullOrEmpty(kdjenKdpstNmpstNoPengajuan))) {
			
		
		if(vSum!=null && vSum.size()>0) {
			
			
			ListIterator li = vSum.listIterator();
		
		%>
		
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:700px">
			<tr>
		   		<td style="background:#369;color:#fff;text-align:center;width:20px"><label><B>No</B> </label></td>
		        <td style="background:#369;color:#fff;text-align:center;width:200px"><label><B>Prodi</B> </label></td>
		       	<td style="background:#369;color:#fff;text-align:left;width:300px"><label><B>Proses Aproval</B> </label></td>
		        <td style="background:#369;color:#fff;text-align:center;width:180px"><label><B>Status</B> </label></td>        
		  	</tr>
		<%  		
			while(li.hasNext()) {
				//no++;
				String brs = (String)li.next();
				//System.out.println("pbrs="+brs);
				StringTokenizer st = new StringTokenizer(brs,"||");
				String kdpst = st.nextToken();
				String nmpst = st.nextToken();
				String kdjen = st.nextToken();
				String needAprBy = st.nextToken();
				String right = st.nextToken();
				String tknApr = st.nextToken();
				boolean approvee =  Boolean.valueOf(st.nextToken()).booleanValue();
				//out.print(brs+"<br/>");
				ListIterator liBk = vBk.listIterator();
				boolean match = false;
				
				
				while(liBk.hasNext()&&!match) {
					String brs1 = (String)liBk.next();
					//System.out.println(">"+brs);
					StringTokenizer st1 = new StringTokenizer(brs1,"||");
					
					cmd1 = st1.nextToken();
					idkur1 = st1.nextToken();
					idkmk1 = st1.nextToken();
					thsms1 = st1.nextToken();
					kdpst1 = st1.nextToken();
					shift1 = st1.nextToken();
					noKlsPll1 = st1.nextToken();
					initNpmInput1 = st1.nextToken();
					latestNpmUpdate1 = st1.nextToken();
					latestStatusInfo1 = st1.nextToken();
					locked1 = st1.nextToken();
					npmdos1 = st1.nextToken();
					nodos1 = st1.nextToken();
					npmasdos1 = st1.nextToken();
					noasdos1 = st1.nextToken();
					cancel = st1.nextToken();
					kodeKelas1 = st1.nextToken();
					kodeRuang1 = st1.nextToken();
					kodeGedung1 = st1.nextToken();
					kodeKampus1 = st1.nextToken();
					tknHrTime1 = st1.nextToken();
					nmmdos1 = st1.nextToken();
					nmmasdos1 = st1.nextToken();
					enrolled1 = st1.nextToken();
					maxEnrol1 = st1.nextToken();
					minEnrol1 = st1.nextToken();
					subKeterMk1 = st1.nextToken();
					initReqTime1 = st1.nextToken();
					tknNpmApproval1 = st1.nextToken();
					tknApprovalTime1 = st1.nextToken();
					targetTotMhs1 = st1.nextToken();
					passed1 = st1.nextToken();
					rejected1 = st1.nextToken();
					konsen1 = st1.nextToken();
					//System.out.println(kodeKampus1+" vs "+kode_kampus);
					if(kdpst.equalsIgnoreCase(kdpst1) && kodeKampus1.equalsIgnoreCase(kode_kampus)) {
						match = true;
						lisc1 = vsc.listIterator();
						while(lisc1.hasNext()) {
							String bris = (String)lisc1.next();
							//System.out.println("brs0="+bris+"<br/>");
							StringTokenizer sstt = new StringTokenizer(bris);
							//li_obj.add(id_obj+" "+kdpst+" "+obj_dsc+" "+obj_level+" "+kdjen+" "+kode_kampus);
							String idObj = sstt.nextToken();
							String kdPst = sstt.nextToken();
							String objDesc = sstt.nextToken();
							String objLvl = sstt.nextToken();
							String kdJen = sstt.nextToken();
							String kdKmps = sstt.nextToken();
							if(kdpst.equalsIgnoreCase(kdPst) && kodeKampus1.equalsIgnoreCase(kdKmps)) {
								lisc1.remove();
							}
						}
					}
				}
				if(match) {
					no++;
			%>
  			<tr>
  			<%
  					if(!Checker.isStringNullOrEmpty(latestStatusInfo1)&&!locked1.equalsIgnoreCase("true")) {
  			%>
   				<td style="color:#000;text-align:center;vertical-align:text-top;" rowspan="2"><%=no %></td>
   			<%	
  					}
  					else {
  			%>
   				<td style="color:#000;text-align:center;"><%=no %></td>
   			<%
  					}
   			%>	
       		 	<td style="color:#000;text-align:center"><%=Converter.getNamaKdpst(kdpst)+"<br/> ("+Converter.getDetailKdjen(kdjen)+")" %></td>
       			<td style="color:#000;text-align:left;"><%=tknApr.replace(","," > ") %></td>
       		<%
       				if(locked1.equalsIgnoreCase("true")) {
       		%>	
        		<td style="color:#0B6121;text-align:center;"><%="<b>Approved</b> "%></td>  
        	<%	
       				}
       				else {
       		%>	
        		<td style="color:#000;text-align:center;"><%="Menunggu persetujuan "+needAprBy %></td>  
        	<%
        			}
        	%>	
        		<td style="color:#000;text-align:center;">
        		
        		<%
        		//if(approvee) {
        		%>
        		<form action="process.prepReqBukaKelaForApproval" method="post">
        		<input type="hidden" value="<%=brs %>" name="infoTarget" />
        		<input type="submit" value="Next" style="width:50px;height:30px"/>
        		</form>
        		<%
        		//}
        		%>
        		</td>
        	</tr>	 	
  	<%
  					if(!Checker.isStringNullOrEmpty(latestStatusInfo1)&&!locked1.equalsIgnoreCase("true")) {
  	%>
  			<tr>
  				<td style="color:#000;text-align:left;" colspan="3"><%=latestStatusInfo1 %></td>
  			</tr>
  	<%	
  					}
				}	
			}
		//diganti dgn lisc1
			if(vsc!=null && vsc.size()>0) {
				lisc1 = vsc.listIterator();
			//if(kdjenKdpstNmpstNoPengajuan!=null && vsc!=null && vsc.size()>0) {
				//StringTokenizer stf = new StringTokenizer(kdjenKdpstNmpstNoPengajuan,"||");
				//StringTokenizer stf = new StringTokenizer(kdjenKdpstNmpstNoPengajuan,"||");
				//while(stf.hasMoreTokens()) {
				while(lisc1.hasNext()) {	
					String bris = (String)lisc1.next();
					//System.out.println("brs="+bris+"<br/>");
					StringTokenizer sstt = new StringTokenizer(bris);
					//li_obj.add(id_obj+" "+kdpst+" "+obj_dsc+" "+obj_level+" "+kdjen+" "+kode_kampus);
					String idObj = sstt.nextToken();
					String kdPst = sstt.nextToken();
					String objDesc = sstt.nextToken();
					String objLvl = sstt.nextToken();
					String kdJen = sstt.nextToken();
					String kdKmps = sstt.nextToken();
				
					
					//String kdjen = stf.nextToken();
					//String kdpst = stf.nextToken();
					//String nmpst = stf.nextToken();
					//boolean match = false;
					//ListIterator lisc = vsc.listIterator();
					//while(lisc.hasNext()&&!match) {
					//	String baris = (String)lisc.next();
					//	StringTokenizer stt = new StringTokenizer(baris);
					//	stt.nextToken();
					//	String prodi = stt.nextToken();
					//	if(kdpst.equalsIgnoreCase(prodi)) {
					//		match = true;
					//	}
					//}
					//if(match) {
					if(kdKmps.equalsIgnoreCase(kode_kampus)) {	
						no++;
					
	%>
			<tr>
				<td style="color:#000;text-align:center;"><%=no %></td>
       		 	<td style="color:#000;text-align:center"><%=Converter.getNamaKdpst(kdPst)+"<br/> ("+Converter.getDetailKdjen(kdJen)+")" %></td>
       			<td style="color:#000;text-align:center;" colspan="2">Belum ada kelas yang diajukan untuk tahun/semester <%=thsmsBukaKelas.substring(0, 4) %>/<%=thsmsBukaKelas.substring(4, 5) %></td>

        	</tr>	
	<%			
					}
				}
			}
  	%>
  	</table>

  	<%
		}
		else {
			%>
			<div style="text-align:center;font-size:1.5em">
			<%
				if(validUsr.getObjNickNameGivenObjId().contains("MHS")) {
					out.print("KELAS PERKULIAHAN BELUM DIBUKA");
				}
				else {
					out.print("HARAP MELAKUKAN PENGAJUAN KELAS PERKULIAHAN MELALUI MENU :<br/><b> KEGIATAN PRA-KULIAH</b>");
				}
			%>
			</div>
			<%	
		}
	}	
	else {
	%>
	<div style="text-align:center;font-size:1.5em">
	<%
		if(validUsr.getObjNickNameGivenObjId().contains("MHS")) {
			out.print("KELAS PERKULIAHAN BELUM DIBUKA");
		}
		else {
			out.print("HARAP MELAKUKAN PENGAJUAN KELAS PERKULIAHAN MELALUI MENU :<br/><b> KEGIATAN PRA-KULIAH</b>");
		}
	%>
	</div>
	<%	
	}
  	%>
		<!-- Column 1 end -->
	</div>
</div>		
</body>
</html>