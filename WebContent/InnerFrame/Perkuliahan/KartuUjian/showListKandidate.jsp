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
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v= null; 
//System.out.println("samapai sini");
String listTipeUjian = (String)request.getParameter("listTipeUjian");
listTipeUjian = listTipeUjian.replace("tandaPagar", "#");
String thsms_aktif = (String)request.getParameter("thsms_aktif");
String atMenu = request.getParameter("atMenu");//=namaujan
Vector vInfoListKandidat= (Vector)session.getAttribute("vInfoListKandidat");
session.removeAttribute("vInfoListKandidat"); 
String cetakMod = (String)request.getAttribute("cetakMod");
String no_urut_utk_jump_scroll = request.getParameter("no_urut_utk_jump_scroll");
int jump_to=0;
if(no_urut_utk_jump_scroll!=null && !Checker.isStringNullOrEmpty(no_urut_utk_jump_scroll)) {
	jump_to = Integer.parseInt(no_urut_utk_jump_scroll);
	jump_to = jump_to -2;
	if(jump_to < 0) {
		jump_to = 0;
	}
}
//li.set(kdpst_+"||"+npmhs_+"||"+tknApr+"||"+tknVer+"||"+tot+"||"+nimhs+"||"+nmmhs+"||"+shift+"||"+smawl+"||"+stpid+"||"+gel);
%>
<script type="text/javascript">

function function1(){
    document.all.kesini.scrollIntoView(true);
}


</script>
</head>
<body onLoad="function1()">
<div id="header">
<%

if(listTipeUjian!=null) {
	StringTokenizer st = new StringTokenizer(listTipeUjian,"#");
	if(st.countTokens()>0) {
%>
	<ul>
	
	
<%			
		String target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/DashPerkuliahan.jsp";
		//String uri = request.getRequestURI();
		//String url = PathFinder.getPath(uri, target);
		%>
		<li><a href="<%=target %>" target="_self">BACK<span><b style="color:#eee">---</b></span></a></li>
		<%	
		while(st.hasMoreTokens()) {
			String namaTest = st.nextToken();
			if(atMenu!=null && atMenu.equalsIgnoreCase(namaTest)) {
				%>
		<li><a href="view.listCalonDptKartuUjian?atMenu=<%=namaTest.toUpperCase() %>" target="_self" class="active"><%=namaTest.toUpperCase() %><span><b style="color:#eee">---</b></span></a></li>
				<%	
			}
			else {
			%>
		<li><a href="view.listCalonDptKartuUjian?atMenu=<%=namaTest.toUpperCase() %>" target="_self"><%=namaTest.toUpperCase() %><span><b style="color:#eee">---</b></span></a></li>
<%	
			}	
		}
%>
	</ul>
<%
	}
}
//kartuUjianApproval#cetakKartuUjian
%>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
<%
if(vInfoListKandidat!=null && vInfoListKandidat.size()>0) {
	int i=0;
	ListIterator li = vInfoListKandidat.listIterator();
	while(li.hasNext()) {
	//li.set(kdpst_+"||"+npmhs_+"||"+tknApr+"||"+tknVer+"||"+tot+"||"+nimhs+"||"+nmmhs+"||"+shift+"||"+smawl+"||"+stpid+"||"+gel);
%>
<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:750px">
        <tr>
	    	<td style="background:#369;color:#fff;text-align:center" colspan="8"><label><B>INFO STATUS KARTU UJIAN <%=atMenu %></B> </label></td>
	    </tr>
    	<tr>
    		<td style="background:#369;color:#fff;text-align:center;width:20px">NO.</td><td style="background:#369;color:#fff;text-align:center;width:100px">PRODI</td><td style="background:#369;color:#fff;text-align:center;width:50px">SMAWL</td><td style="background:#369;color:#fff;text-align:center;width:25px">GEL</td><td style="background:#369;color:#fff;text-align:center;width:130px">TOT BAYARAN</td><td style="background:#369;color:#fff;text-align:center;width:125px">NPM/NIM</td><td style="background:#369;color:#fff;text-align:center;width:225px">NAMA</td><td style="background:#369;color:#fff;text-align:center;width:75px">STATUS</td>
    	</tr>
<%	
	//ListIterator li = vInfoListKandidat.listIterator();
	//while(li.hasNext()) {
		String brs = (String)li.next();
		//System.out.println("--"+brs);
		StringTokenizer st = new StringTokenizer(brs,"||");
		//if(brs.contains("2020100000119")) {
			//System.out.println("ount = "+st.countTokens());	
		//}
		
		String kdpst=st.nextToken();
		String npmhs=st.nextToken();
		//System.out.println("npmhs = "+npmhs);	
		String tknApr=st.nextToken();
		String tknVer=st.nextToken();
		String tknKartuUjian = st.nextToken();
		String tknApprKartuUjian = st.nextToken();
		String tknStatus = st.nextToken();
		String tknRulesApproveeKartu = st.nextToken();
		String tot=st.nextToken();
		String nimhs=st.nextToken();
		String nmmhs=st.nextToken();
		String shift=st.nextToken();
		String smawl=st.nextToken();
		String stpid=st.nextToken();
		String gel=st.nextToken();
		String status=st.nextToken();
		//if(npmhs.equalsIgnoreCase("2020100000119")) {
			//System.out.println("--npmhs="+npmhs);
			//System.out.println("--"+brs);
		//}
		
		String statusAkhir = "";
		//cek tknKartuUjian apa sdh ada rekord untuk ujian ini = atMenu
		boolean noPrevRec = true,match = false;
		int noKe=0;
		if(tknKartuUjian.toUpperCase().contains(atMenu.toUpperCase())) {
			noPrevRec = false;
			//cek nourut ke berapa
			st = new StringTokenizer(tknKartuUjian,",");//pemisah koma, sesuai yg ada di SearchDbInfoMhs; jadi jgn dirubah
			while(st.hasMoreTokens() && !match) {
				noKe++;
				String namaUjian = st.nextToken();
				if(namaUjian.equalsIgnoreCase(atMenu)) {
					match = true;
				}
			}
			
			//tkn status
			//statusAkhir = "";
			st = new StringTokenizer(status,"#");//pemisah koma, sesuai yg ada di SearchDbInfoMhs; jadi jgn dirubah
			//System.out.println("--status="+status);
			for(int j=0;st.hasMoreTokens() && j<noKe;j++) {
				statusAkhir = st.nextToken();
			}
		}
		else {
		//	//blum ada record
		}
		//System.out.println("--statusAkhir="+statusAkhir);

		if(i==jump_to) {
%>
		<tr id="kesini">
<%			
		}
		else {
%>
		<tr>
<%
		}
%>		
    		<td style="text-align:center;"><%=++i %></td><td style="text-align:left;"><%
    		String prodi = Converter.getDetailKdpst(kdpst);
    		out.print(prodi.substring(0,prodi.length()-3));
    		%></td><td style="text-align:center;"><%=smawl %></td><td style="text-align:center;">
    		<%
    		if(Checker.isStringNullOrEmpty(gel)) {
    			out.print("N/A");
    		}
    		else {
    			out.print(gel);
    		}
    		%></td><td style="text-align:right;">Rp. <%=NumberFormater.indoNumberFormat(tot) %></td><td style="text-align:center;"><%=npmhs %><br/><%=nimhs %></td><td style="text-align:left;"><%=nmmhs.toUpperCase() %></td><td style="text-align:center;">
    		<%
    		if(noPrevRec) {
    			out.print(status);
    		}
    		else {
    			if(statusAkhir.toUpperCase().contains("tolak".toUpperCase())) {
    				//hanya tampilkan row tambahan bila ditolak
    				//System.out.println("statusAkhir-5="+statusAkhir);
    				st = new StringTokenizer(statusAkhir,"$");
    				
    				String status_ = "";
    				String alasan_ = "Harap hubungi bagian keuangan";
    				if(st.countTokens()>1) {
    					status_ = st.nextToken();
    					alasan_ = st.nextToken();
    				}
    				else {
    					status_ = st.nextToken();
    				}
    				
    		%>
    		<div style="color:red;font-weight:bold"><%=status_ %></div></td>
    		</tr>
    		<tr>
    			<td colspan="2" style="text-align:center;color:red;font-weight:bold;margin:5px">Alasan Penolakan</td>
    			<td colspan="6" style="text-align:left;color:red;font-weight:bold;margin:5px"><%=alasan_ %>
    		<%		
    			}
    			else {
    				if(status.toUpperCase().contains("CETAK")) {
    					//System.out.println("statusAkhir-6="+statusAkhir);
    				
    					//cetakKartuUjian
    					if(cetakMod!=null && cetakMod.equalsIgnoreCase("true")) {
    		%>
    					<form action="file.downloadKartu<%=Tool.capFirstLetterInWord(atMenu) %>" method="post">
    						<input type="hidden" name="thsms_aktif" value="<%=thsms_aktif %>" />
							<input type="hidden" name="brs" value="<%=brs %>" />
							<input type="hidden" name="targetUjian" value="<%=atMenu %>" />
    						<input type="submit" value="CETAK" style="width:90%;font-weight:bold;height:40px" />
    					</form>
    		<%				
    					}
    					else {
    						out.print(status);			
    					}
    				}
    				else {
    					
    					//System.out.println("statusAkhir-7="+statusAkhir);
    					out.print(status);
    				}	
    			}
    			
    		}
    		%></td>
    	</tr>
    	
<%
		if(validUsr.isAllowTo("kartuUjianApprovee")>1) {
%>
		<form action="update.statusKartuUjian" method="post">
		<input type="hidden" name="thsms_aktif" value="<%=thsms_aktif %>" />
		<input type="hidden" name="brs" value="<%=brs %>" />
		<input type="hidden" name="targetUjian" value="<%=atMenu %>" />
		<input type="hidden" name="listTipeUjian" value="<%=listTipeUjian %>" />
		<input type="hidden" name="no_urut_utk_jump_scroll" value="<%=i %>" />
		
		
		<tr>
			<td colspan="2" style="background:#a6bac4;text-align:center"><input type="submit" name="verdict" value="Tolak" style="color:red;font-weight:bold;width:90%;height:40px;margin:2px;"/></td>
			<td colspan="6" style="background:#a6bac4;text-align:center"><textarea name="alasan" rows="2" style="display:inline;width:95%;margin:10px" placeholder="isi alasan penolakan"></textarea></td>
		</tr>
		<tr>
			<td colspan="6" style="background:#a6bac4;text-align:center">Bayaran Mahasiswa telah memenuhi syarat untuk ujian</td>
			<td colspan="2" style="background:#a6bac4;text-align:center"><input type="submit" name="verdict" value="Ya" style="font-size:1.25em;color:#369;font-weight:bold;width:90%;height:40px;margin:2px;"/></td>
		</tr>
		</form>
<%			
		}
//System.out.println("end");
%>
	</table>
	<br/>
<%
	}//end while
}
else {
	out.print("TIDAK ADA KANDIDAT PENERIMA KARTU UJIAN");
}

%>
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>