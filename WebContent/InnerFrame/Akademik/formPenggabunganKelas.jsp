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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
/*
* viewKurikulumStdTamplete (based on)
*/
	String tmp ="";
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String hak_akses = validUsr.getHakAkses("reqGabungKelasFak");
	boolean read_only = false;
	if(hak_akses!=null && hak_akses.contains("`")) {
		if(hak_akses.contains("r")&&!hak_akses.contains("i")&&!hak_akses.contains("e")&&!hak_akses.contains("d")) {
			read_only = true;
		}
	}
	//String listMkNonKelompok = request.getParameter("listMkNonKelompok");
	//String listMkKelompok = request.getParameter("listMkKelompok");
	String listMkNonKelompok = (String)session.getAttribute("listMkNonKelompok");
	String listMkKelompok = (String)session.getAttribute("listMkKelompok");
	if(listMkNonKelompok!=null) {
		listMkNonKelompok=listMkNonKelompok.replace("tandaDan", "&");
	}
	if(listMkKelompok!=null) {
		listMkKelompok=listMkKelompok.replace("tandaDan", "&");
	}
	int i=0;
	String thsmsTarget = request.getParameter("thsmsTarget");
%>


</head>
<body>
<div id="header">
<%@ include file="IndexAkademikPengajuanSubMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->

		<%
		try {
		if(listMkNonKelompok!=null || listMkKelompok!=null) {
			//system.out.println("start");
		%>
		<form action="go.gabungKelas" method="POST">
		<input type="hidden" name="thsmsTarget" value="<%=thsmsTarget %>" />
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px">
        	<tr>
	    		<td style="background:#369;color:#fff;text-align:center" colspan="10"><label><B>DAFTAR KELAS PERKULIAHAN</B> </label></td>
	    	</tr>
    		<tr>
    			<td style="background:#369;color:#fff;text-align:center;width:25px">NO</td>
    			<td style="background:#369;color:#fff;text-align:center;width:200px">PRODI</td>
    			<td style="background:#369;color:#fff;text-align:center;width:200px">MATAKULIAH</td>
    			<td style="background:#369;color:#fff;text-align:center;width:50px">KODE MK</td>
    			<td style="background:#369;color:#fff;text-align:center;width:50px">KAMPUS</td>
    			<td style="background:#369;color:#fff;text-align:center;width:75px">SHIFT</td>
    			<td style="background:#369;color:#fff;text-align:center;width:50px">KLS PLL</td>
    			<td style="background:#369;color:#fff;text-align:center;width:50px"># MHS</td>
    			<td style="background:#369;color:#fff;text-align:center;width:50px">KELOMPOK</td>
    			<td style="background:#369;color:#fff;text-align:center;width:50px">KELAS INTI</td>
    		</tr>
		<%	
			if(listMkKelompok!=null) {
			//if(false) {
				//kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+kdkmk1+"$"+nakmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1);
				StringTokenizer st = new StringTokenizer(listMkKelompok,"$");
				
				while(st.hasMoreTokens()) {
					//system.out.println("-listMkKelompok- "+listMkKelompok);
					//lig.set(kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+kdkmk1+"$"+nakmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+nmpst1);
					String kodeGabungan=st.nextToken();
					String idkmk1=st.nextToken();
					
					//System.out.println(kodeGabungan+"--"+idkmk1);
					String idkur1=st.nextToken();
					String kdkmk1=st.nextToken();
					String nakmk1=st.nextToken();
					//System.out.println("nakmk="+nakmk1);
					String thsms1=st.nextToken();
					//tmp = ""+idkmk1+"$"+kdkmk1+"$"+nakmk1+"$"+thsms1;
					String kdpst1=st.nextToken();
					String shift1=st.nextToken();
					String norutKlsPll1=st.nextToken();
					String initNpmInput1=st.nextToken();
					String latestNpmUpdate1=st.nextToken();
					String latesStatusInfo1=st.nextToken();
					String currAvailStatus1=st.nextToken();
					String locked1=st.nextToken();
					String npmdos1=st.nextToken();
					String nodos1=st.nextToken();
					String npmasdos1=st.nextToken();
					String noasdos1=st.nextToken();
					String canceled1=st.nextToken();
					String kodeKelas1=st.nextToken();
					String kodeRuang1=st.nextToken();
					String kodeGedung1=st.nextToken();
					String kodeKampus1=st.nextToken();
					String tknHrTime1=st.nextToken();
					String nmdos1=st.nextToken();
					String nmasdos1=st.nextToken();
					String enrolled1=st.nextToken();
					String maxEnrolled1=st.nextToken();
					String minEnrolled1=st.nextToken();
					String subKeterKdkmk1=st.nextToken();
					String initReqTime1=st.nextToken();
					String tknNpmApr1=st.nextToken();
					String tknAprTime1=st.nextToken();
					String targetTtmhs1=st.nextToken();
					String passed1=st.nextToken();
					String rejected1=st.nextToken();
					String konsen1=st.nextToken();
					String nmpst1=st.nextToken();
					String cuid1=st.nextToken();
					String listMhs=st.nextToken();
					//String listMhsHer=st.nextToken();
					
					
					
					int ttmhs1=0;
					if(listMhs!=null && !Checker.isStringNullOrEmpty(listMhs)) {
						StringTokenizer stt = new StringTokenizer(listMhs,",");
						ttmhs1 = stt.countTokens();
					}
					//int ttmhsher1=0;
					//if(listMhsHer!=null && !Checker.isStringNullOrEmpty(listMhsHer)) {
					//	StringTokenizer stt = new StringTokenizer(listMhsHer,",");
					//	ttmhsher1 = stt.countTokens();
					//}
					//String ttmhs1=st.nextToken();
					//tmp=(kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+nakmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+nmpst1+"$"+ttmhs1);
			%>
			<tr>
    			<td style="text-align:center;"><%=++i %></td>
    			<td style="text-align:center;">
    		
    			<%
    			out.print(nmpst1);
    			if(!Checker.isStringNullOrEmpty(konsen1)) {
    				out.print("<br/>Konsentrasi<br/>"+konsen1.toUpperCase());
    			}
    			%></td>
    			<td style="text-align:left;"><%=nakmk1+"<br/>Dosen: "+nmdos1 %></td>
    			<td style="text-align:center;"><%=kdkmk1 %></td>
    			<td style="text-align:center;"><%=kodeKampus1 %></td>
    			<td style="text-align:center;"><%=shift1 %></td>
    			<td style="text-align:center;"><%=norutKlsPll1 %></td>
    			<td style="text-align:center;"><a href="go.prepForTampleteListMhs?listNpmhs=<%=listMhs%>"><%=ttmhs1 %></a><!--  a href="go.prepForTampleteListMhs?listNpmhs=listMhsHer%>">ttmhsher1 %></a --></td>
    			<td style="text-align:center;">
					<input type="hidden" name="infoGroup" value="<%=kdpst1 %>$<%=idkmk1 %>$<%=shift1 %>$<%=norutKlsPll1 %>$<%=idkur1 %>$<%=cuid1 %>" />
				<%
					if(read_only) {
				%>	
					<%=Checker.pnn(kodeGabungan) %>
				<%	
					}
					else {
						if(kodeGabungan!=null && !Checker.isStringNullOrEmpty(kodeGabungan)) {
				%>
					<input type="text" name="kodeGroup" maxlength="3" value="<%=kodeGabungan %>" style="width:50px;text-align:center"/>
				<%	
						}
						else {
				%>
					<input type="text" name="kodeGroup" maxlength="3" style="width:50px;text-align:center"/>
				<%
						}
					}
				%>	
				</td>	
    			<td style="text-align:center;">
    			<%
    				if(read_only) {
    					if(canceled1.equalsIgnoreCase("false")) {
    			%>	
    						&#9745;
    			<%
						}
    					else {
    			%>	
    					&#9744;
    			<%	
    					}	
    				}
    				else {
    					if(canceled1.equalsIgnoreCase("false")) {
    			%>	
    				<input type="checkbox" name="kelasInti" value="<%=kdpst1 %>$<%=idkmk1 %>$<%=shift1 %>$<%=norutKlsPll1 %>$<%=idkur1 %>$<%=cuid1 %>" checked>
    			<%
						}
    					else {
    			%>	
    				<input type="checkbox" name="kelasInti" value="<%=kdpst1 %>$<%=idkmk1 %>$<%=shift1 %>$<%=norutKlsPll1 %>$<%=idkur1 %>$<%=cuid1 %>">
    			<%	
    					}
    				}	
    			//System.out.println("done");
    			%>
				</td>
    		</tr>
			<%		
				}
			}
			//system.out.println("end");
			//system.out.println("start");
			if(listMkNonKelompok!=null) {
			//if(false) {
				
			//kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+nakmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1);
				StringTokenizer st = new StringTokenizer(listMkNonKelompok,"$");
				while(st.hasMoreTokens()) {
					//system.out.println("-listMkNonKelompok- "+listMkNonKelompok);
					String kodeGabungan=st.nextToken();
					String idkmk1=st.nextToken();
					String idkur1=st.nextToken();
					String kdkmk1=st.nextToken();
					String nakmk1=st.nextToken();
					String thsms1=st.nextToken();
					String kdpst1=st.nextToken();
					String shift1=st.nextToken();
					String norutKlsPll1=st.nextToken();
					String initNpmInput1=st.nextToken();
					String latestNpmUpdate1=st.nextToken();
					String latesStatusInfo1=st.nextToken();
					String currAvailStatus1=st.nextToken();
					String locked1=st.nextToken();
					String npmdos1=st.nextToken();
					String nodos1=st.nextToken();
					String npmasdos1=st.nextToken();
					String noasdos1=st.nextToken();
					String canceled1=st.nextToken();
					String kodeKelas1=st.nextToken();
					String kodeRuang1=st.nextToken();
					String kodeGedung1=st.nextToken();
					String kodeKampus1=st.nextToken();
					String tknHrTime1=st.nextToken();
					String nmdos1=st.nextToken();
					String nmasdos1=st.nextToken();
					String enrolled1=st.nextToken();
					String maxEnrolled1=st.nextToken();
					String minEnrolled1=st.nextToken();
					String subKeterKdkmk1=st.nextToken();
					String initReqTime1=st.nextToken();
					String tknNpmApr1=st.nextToken();
					String tknAprTime1=st.nextToken();
					String targetTtmhs1=st.nextToken();
					String passed1=st.nextToken();
					String rejected1=st.nextToken();
					String konsen1=st.nextToken();
					String nmpst1=st.nextToken();
					String cuid1=st.nextToken();
					String listMhs=st.nextToken();
					//String listMhsHer=st.nextToken();
					int ttmhs1=0;
					if(listMhs!=null && !Checker.isStringNullOrEmpty(listMhs)) {
						StringTokenizer stt = new StringTokenizer(listMhs,",");
						ttmhs1 = stt.countTokens();
					}
					//int ttmhsher1=0;
					//if(listMhsHer!=null && !Checker.isStringNullOrEmpty(listMhsHer)) {
					//	StringTokenizer stt = new StringTokenizer(listMhsHer,",");
					//	ttmhsher1 = stt.countTokens();
					//}

			%>
			<tr>
				<td style="text-align:center;"><%=++i %></td>
				<td style="text-align:center;">
				
				<%
				
    			out.print(nmpst1);
    			if(!Checker.isStringNullOrEmpty(konsen1)) {
    				out.print("<br/>Konsentrasi<br/>"+konsen1.toUpperCase());
    			}
    			%></td>
				<td style="text-align:left;"><%=nakmk1+"<br/>Dosen: "+nmdos1 %></td>
				<td style="text-align:center;"><%=kdkmk1 %></td>
				<td style="text-align:center;"><%=kodeKampus1 %></td>
				<td style="text-align:center;"><%=shift1 %></td>
				<td style="text-align:center;"><%=norutKlsPll1 %></td>
				<td style="text-align:center;"><a href="go.prepForTampleteListMhs?listNpmhs=<%=listMhs%>"><%=ttmhs1 %></a><!--  a href="go.prepForTampleteListMhs?listNpmhs=<listMhsHer%>"><ttmhsher1 %></a --></td>
				<td style="text-align:center;">
					<input type="hidden" name="infoGroup" value="<%=kdpst1 %>$<%=idkmk1 %>$<%=shift1 %>$<%=norutKlsPll1 %>$<%=idkur1 %>$<%=cuid1 %>" />
				<%
					if(read_only) {
					%>	
						<%=Checker.pnn(kodeGabungan) %>
					<%	
					}
					else {
						if(kodeGabungan!=null && !Checker.isStringNullOrEmpty(kodeGabungan)) {
				%>
					<input type="text" name="kodeGroup" maxlength="3" value="<%= kodeGabungan %>" style="width:50px;text-align:center"/>
				<%	
						}
						else {
				%>
					<input type="text" name="kodeGroup" maxlength="3" style="width:50px;text-align:center"/>
				<%
						}
					}	
				%>	
					
				</td>
				<td style="text-align:center;">
				<%
					if(read_only) {
						if(canceled1.equalsIgnoreCase("false") && (kodeGabungan!=null && !Checker.isStringNullOrEmpty(kodeGabungan))) {
    			%>	
    					&#9745;
    			<%
						}
    					else {
    			%>	
    					&#9744;
    			<%	
    					}	
    				}
					else {
    					if(canceled1.equalsIgnoreCase("false") && (kodeGabungan!=null && !Checker.isStringNullOrEmpty(kodeGabungan))) {
    			%>	
    				<input type="checkbox" name="kelasInti" value="<%=kdpst1 %>$<%=idkmk1 %>$<%=shift1 %>$<%=norutKlsPll1 %>$<%=idkur1 %>$<%=cuid1 %>" checked>
    			<%
						}
    					else {
    			%>	
    				<input type="checkbox" name="kelasInti" value="<%=kdpst1 %>$<%=idkmk1 %>$<%=shift1 %>$<%=norutKlsPll1 %>$<%=idkur1 %>$<%=cuid1 %>">
    			<%	
    					}
					}
    			%>
				</td>
			</tr>
			<%		
				}
			}
		%>
		</table>
		<br/>
		<div align="center">
		<%
			//system.out.println("selesai");
			if(!read_only) {
		%>
		<input type="submit" value="UPDATE GABUNG KELAS" name="perintah" style="width:500px;"/><br/><br/>
		<%
			}
		
			if(validUsr.isAllowTo("!reqGabungKelasFak")>0) {
		%>		
		<input type="submit" value="PROSES PENGGABUNGAN SELESAI & AJUKAN PENGGABUNGAN" name="perintah" style="width:500px;"/>
		<%
			}
		%>
		</div>
		</form>
		
		<%	
		}
		}
		catch (Exception e){
			//System.out.println("tmp="+tmp);
		}
		%>	
		<!-- Column 1 start -->
	</div>
</div>			
</body>
</html>