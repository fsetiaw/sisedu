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
<%@ page import="beans.dbase.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v= null;  
boolean usrMhs = false;
if(validUsr.getObjNickNameGivenObjId().contains("MHS") || validUsr.getObjNickNameGivenObjId().contains("mhs") ) {
	usrMhs = true;
}
//System.out.println("continus system");
%>

</head>
<body>
<div id="header">
<%@ include file="krsKhsSubMenu.jsp" %>
<%
//String thsmsPmb=Checker.getThsmsPmb();
String thsms_now = Checker.getThsmsNow();
String thsmsKrs=Checker.getThsmsKrs();
SearchDb sdb = new SearchDb();
String nmpst = sdb.getNmpst(kdpst);
String idkur = sdb.getIndividualKurikulum(kdpst, npm);

//String msg = request.getParameter("msg");
Vector vMk=null;
if(idkur!=null) {
	vMk = sdb.getListMatakuliahDalamKurikulum(kdpst,idkur);
}	
String infoKrsNotificationAtPmb = (String) request.getAttribute("infoKrsNotificationAtPmb");
String idNotif = null;
String kstegoriNotif = null;
String thsmsNotif = null;
String beritaNotif = null;
String npmSenderNotif = null;
String nmmSenderNotif = null;
String npmReceiverNotif = null;
String nmmReceiverNotif = null;
String kdpstReceiverNotif = null;
String smawlReceiverNotif = null;
String hiddenAtSenderNotif = null;
String hiddenAtReceiverNotif = null;
String delBySenderNotif = null;
String delByReceiverNotif = null;
String approvedNotif = null;
String declinedNotif = null;
String unlockApprovedNotif = null;
String unlockDeclinedNotif = null;
String noteNotif = null;
String updtmNotif = null;
//////System.out.println("infoKrsNotificationAtPmb@historykrs.jsp="+infoKrsNotificationAtPmb);
if(infoKrsNotificationAtPmb!=null && !Checker.isStringNullOrEmpty(infoKrsNotificationAtPmb)) {
	StringTokenizer st = new StringTokenizer(infoKrsNotificationAtPmb,"#");
	idNotif = st.nextToken();
	kstegoriNotif = st.nextToken();
	thsmsNotif = st.nextToken();
	beritaNotif = st.nextToken();
	npmSenderNotif = st.nextToken();
	nmmSenderNotif = st.nextToken();
	npmReceiverNotif = st.nextToken();
	nmmReceiverNotif = st.nextToken();
	kdpstReceiverNotif = st.nextToken();
	smawlReceiverNotif = st.nextToken();
	hiddenAtSenderNotif = st.nextToken();
	hiddenAtReceiverNotif = st.nextToken();
	delBySenderNotif = st.nextToken();
	delByReceiverNotif = st.nextToken();
	approvedNotif = st.nextToken();
	declinedNotif = st.nextToken();
	unlockApprovedNotif = st.nextToken();
	unlockDeclinedNotif = st.nextToken();
	noteNotif = st.nextToken();
	updtmNotif = st.nextToken();
}
String tknPaInfo = (String)request.getAttribute("tknPaInfo");
String currentPa = (String)request.getAttribute("currentPa");
String curNpmPa = "N/A";
String curNmmPa = "N/A";
if(currentPa!=null) {
	StringTokenizer st = new StringTokenizer(currentPa,",");
	curNpmPa = st.nextToken();
	curNmmPa = st.nextToken();
}
request.removeAttribute("infoKrsNotificationAtPmb");
request.removeAttribute("tknPaInfo");
request.removeAttribute("currentPa");
////System.out.println("tknPaInfo="+tknPaInfo);
////System.out.println("currentPa="+currentPa);
////System.out.println("ini3");
Vector vSelectMk = new Vector();
ListIterator liSmk = vSelectMk.listIterator();
if(vMk!=null && vMk.size()>0) {
	////System.out.println("ini5");
	ListIterator liMk = vMk.listIterator();
	while(liMk.hasNext()) {
		String idkmk=(String)liMk.next();
		String kdkmk=(String)liMk.next();
		String nakmk=(String)liMk.next();
		String skstm=(String)liMk.next();
		String skspr=(String)liMk.next();
		String skslp=(String)liMk.next();
		String kdwpl=(String)liMk.next();
		String jenis=(String)liMk.next();
		String stkmk=(String)liMk.next();
		String nodos=(String)liMk.next();
		String semes=(String)liMk.next();
		liSmk.add(idkmk+","+kdkmk+","+nakmk);
	}	
}

%>

</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		continus<br/>
		<!-- Column 1 start -->
		
		<%
		/*
		msg di pas dari HistoryKrsKhs servlet, bilai msg !=null berarti blum ditentukan kurikulumnya
		*/
		////System.out.println("sampe sini");
		//System.out.println("msg ="+msg);
if(msg!=null) {
			//kurikulum blm ditentukan
	Vector v_cf = validUsr.getScopeUpd7des2012("hasEditMkKurikulum");
	if(v_cf!=null && v_cf.size()>0) {
		%>
		
		<h2 align="center">Untuk Menambah atau Edit Krs<br/>Harap <a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Akademik/editTargetKurikulum.jsp?kdpst_nmpst=<%=v_kdpst%>,<%=nmpst %>" target="inner_iframe" class="active">Tentukan Kurikulum Untuk <%=nmm %> </a>Terlebih Dahulu</h2>
		<br/>
		<%
	}
	else {
				%>
				<h2 align="center">Kurikulum Untuk <%=nmm %> Belum Diisi oleh Tata Usaha</h2>
				<%		
	}	
}
		//else {
			////System.out.println("allowViewKrs="+allowViewKrs);
if(allowViewKrs) {
				
				
	Vector vHistKrsKhs = (Vector)request.getAttribute("vHistKrsKhs");
	Vector vHistTmp = (Vector)vHistKrsKhs.clone();
				////System.out.println("vHistTmp="+vHistTmp.size());
	if(cetakKrsMode||cetakKhsMode) {
		session.setAttribute("vHistTmp", vHistTmp);
	}
	////System.out.println("size1 ="+vHistKrsKhs.size());
	Vector vTrnlp = (Vector)request.getAttribute("vTrnlp");
	////System.out.println("size1a ="+vTrnlp.size());

	session.setAttribute("vHistKrsKhsForEdit",vHistKrsKhs);
	session.setAttribute("vTrnlpForEdit",vTrnlp);
	request.removeAttribute("vHistKrsKhs");
	request.removeAttribute("vTrnlp");
				////System.out.println("vHistTmp1="+vHistTmp.size());	
	if(vTrnlp!=null && vTrnlp.size()>0) {
		ListIterator liT = vTrnlp.listIterator();
		if(liT.hasNext()) {
			String thsms=(String)liT.next();
			String kdkmk=(String)liT.next();
			String nakmk=(String)liT.next();
			String nlakh=(String)liT.next();
			String bobot=(String)liT.next();
			String kdasl=(String)liT.next();
			String nmasl=(String)liT.next();
			String sksmk=(String)liT.next();
			String totSksTransfer=(String)liT.next();
			String sksas = (String)liT.next();
			String transferred = (String)liT.next();
			if(!cetakKrsMode && !cetakKhsMode) {
					%>
					
					<P>
					<table align="center" border="1px" bordercolor="#29A329" style="background:#D6EB99;color:#000;width:800px">
        			<tr>
        				<td style="background:#29A329;color:#fff;text-align:center" colspan="5"><label><B>MATAKULIAH PINDAHAN</B> </label></td>
        			</tr>
        			<tr>
        				<td style="background:#29A329;color:#fff;text-align:center;width:60px"><label><B>KODE</B> </label></td>
        				<td style="background:#29A329;color:#fff;text-align:left;width:595px"><label><B>MATAKULIAH</B> </label></td>
        				<td style="background:#29A329;color:#fff;text-align:center;width:55px"><label><B>NILAI</B> </label></td>
        				<td style="background:#29A329;color:#fff;text-align:center;width:55px"><label><B>BOBOT</B> </label></td>
        				<td style="background:#29A329;color:#fff;text-align:center;width:35px"><label><B>SKS</B> </label></td>
        			</tr>
        			<tr>
        				
        				<td style="color:#000;text-align:center;"><label><B><%=kdkmk %></B> </label></td>
        				<td style="color:#000;text-align:left;"><label><B><%=nakmk %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=nlakh %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=bobot %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=sksmk %></B> </label></td>
        				
        			</tr>
					<%	
				while(liT.hasNext()) {
					thsms=(String)liT.next();
					kdkmk=(String)liT.next();
					nakmk=(String)liT.next();
					nlakh=(String)liT.next();
					bobot=(String)liT.next();
					kdasl=(String)liT.next();
					nmasl=(String)liT.next();
					sksmk=(String)liT.next();
					totSksTransfer=(String)liT.next();
					sksas = (String)liT.next();
					transferred = (String)liT.next();
					%>
					<tr>
        				<td style="color:#000;text-align:center;"><label><B><%=kdkmk %></B> </label></td>
        				<td style="color:#000;text-align:left;"><label><B><%=nakmk %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=nlakh %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=bobot %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=sksmk %></B> </label></td>
        			
        			</tr>
        			<%		
				}
					//tutuup table
					%>
					<tr>
        				<td style="background:#29A329;color:#fff;text-align:center;" colspan="4"><label><B>TOTAL MATAKULIAH TRANSFER / PINDAHAN</B> </label></td>
        				<td style="background:#29A329;color:#fff;text-align:center;"><label><B><%=totSksTransfer %></B> </label></td>
        			</tr>
        			</table>
        			</P>
        			<br />
					<%
			}//end if(!cetakmode)
		}
						
	}//end cetak krs moew
				
				////System.out.println("point 1");
	if(vHistKrsKhs!=null && vHistKrsKhs.size()>0) {			
		ListIterator lih = vHistKrsKhs.listIterator();
		String prev_thsms="";
		String prev_kdkmk="";
		String prev_nakmk="";
		String prev_nlakh="";
		String prev_bobot="";
		String prev_sksmk="";
		String prev_kelas="";
		String prev_sksem="";
		String prev_nlips="";
		String prev_skstt="";
		String prev_nlipk="";
		String prev_shift="";
		String prev_krsdown="";
		String prev_khsdown="";
		String prev_bakprove="";
		String prev_paprove="";
		String prev_note="";
		String prev_lock="";
		String prev_baukprove="";
					
		String prev_idkmk ="";
		String prev_addReq ="";
		String prev_drpReq  ="";
		String prev_npmPa ="";
		String prev_npmBak ="";
		String prev_npmBaa ="";
		String prev_npmBauk ="";
		String prev_baaProve ="";
		String prev_ktuProve ="";
		String prev_dknProve ="";
		String prev_npmKtu ="";
		String prev_npmDekan ="";
		String prev_lockMhs ="";
		String prev_kodeKampus ="";
		if(lih.hasNext()) {
			String thsms=(String)lih.next();
			String kdkmk=(String)lih.next();
			String nakmk=(String)lih.next();
			String nlakh=(String)lih.next();
			String bobot=(String)lih.next();
			String sksmk=(String)lih.next();
			String kelas=(String)lih.next();
			String sksem=(String)lih.next();
			String nlips=(String)lih.next();
			String skstt=(String)lih.next();
			String nlipk=(String)lih.next();
			String shift=(String)lih.next();
			String krsdown=(String)lih.next();
			String khsdown=(String)lih.next();
			String bakprove=(String)lih.next();
			String paprove=(String)lih.next();
			String note=(String)lih.next();
			String lock=(String)lih.next();
			String baukprove=(String)lih.next();
					
			String idkmk =(String)lih.next();
			String addReq =(String)lih.next();
			String drpReq  =(String)lih.next();
			String npmPa =(String)lih.next();
			String npmBak =(String)lih.next();
			String npmBaa =(String)lih.next();
			String npmBauk =(String)lih.next();
			String baaProve =(String)lih.next();
			String ktuProve =(String)lih.next();
			String dknProve =(String)lih.next();
			String npmKtu =(String)lih.next();
			String npmDekan =(String)lih.next();
			String lockMhs =(String)lih.next();
			String kodeKampus =(String)lih.next();
			prev_thsms = ""+thsms;
			prev_kdkmk=""+kdkmk;
			prev_nakmk=""+nakmk;
			prev_nlakh=""+nlakh;
			prev_bobot=""+bobot;
			prev_sksmk=""+sksmk;
			prev_kelas=""+kelas;
			prev_sksem=""+sksem;
			prev_nlips=""+nlips;
			prev_skstt=""+skstt;
			prev_nlipk=""+nlipk;
			prev_shift=""+shift;
			prev_krsdown=""+krsdown;
			prev_khsdown=""+khsdown;
			prev_bakprove=""+bakprove;
			prev_paprove=""+paprove;
			prev_note=""+note;
			prev_lock=""+lock;
			prev_baukprove=""+baukprove;
					
			prev_idkmk =""+idkmk;
			prev_addReq =""+addReq;
			prev_drpReq  =""+drpReq;
			prev_npmPa =""+npmPa;
			prev_npmBak =""+npmBak;
			prev_npmBaa =""+npmBaa;
			prev_npmBauk =""+npmBauk;
			prev_baaProve =""+baaProve;
			prev_ktuProve =""+ktuProve;
			prev_dknProve =""+dknProve;
			prev_npmKtu =""+npmKtu;
			prev_npmDekan =""+npmDekan;
			prev_lockMhs =""+lockMhs;
			prev_kodeKampus =""+kodeKampus;
			if(!cetakKrsMode && !cetakKhsMode && validUsr.isAllowTo("noCalcIp")<1 && (validApprovee==null || (validApprovee!=null && !validApprovee.equalsIgnoreCase("true")))) {
						%>
					<form action="go.calcIndividualIpk">
						
        				<input type="hidden" name="id_obj" value="<%=objId %>" />
        				<input type="hidden" name="nmm" value="<%=nmm %>" />
        				<input type="hidden" name="npm" value="<%=npm %>" />
        				<input type="hidden" name="kdpst" value="<%=kdpst %>" />
        				<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
        				<input type="hidden" name="cmd" value="histKrs" />
        			
						<center><input type="submit" value="KLIK Untuk Menghitung IP/IPK" style="height:50px" /></center>
					</form>
					    <%
			}
					    %>
					<br/>
					<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px">
					<%
			String submitButtonValue="Download";
			if(!cetakKrsMode && !cetakKhsMode) {
						
					
						// updated ditambaha validasi validApprove = memberikan akses perwalian, jadi vurNpmPa=usr.npm harusnya tidak ada pengaruh lagi
						// updated lagi - cukup valid approvee ajah, krn yg penting filternya pada krs notifikasi, bila ada di krs notifikasi berarti validApprovee
						//if(paApprover && prev_lock.equalsIgnoreCase("false") && (curNpmPa.equalsIgnoreCase(validUsr.getNpm())||(validApprovee!=null && validApprovee.equalsIgnoreCase("true"))) && prev_thsms.equalsIgnoreCase(thsmsKrs)) { //mode view krs
				if(validApprovee!=null && validApprovee.equalsIgnoreCase("true") && prev_thsms.equalsIgnoreCase(thsmsKrs)) {
    						%>
    					<tr>
    		        		<td style="background:#369;color:#fff;text-align:center" colspan="8"><label><B><%=Converter.convertThsmsKeterOnly(prev_thsms) %></B> </label></td>
    		        	</tr>
    		        	<tr>	
    		        		<td style="background:#369;color:#fff;text-align:center" colspan="8">
    		        		Catatan Penolakan / Persetujuan :<br/>
    		        		<form action="go.krsApproval">
    		        		<TEXTAREA rows="4" style="width:98%" name="catatan">
    		        		
    		        		<%
    		        		//out.print(noteNotif);
    		   		if(prev_note!=null && !Checker.isStringNullOrEmpty(prev_note)) {
    		        	out.print(prev_note);
    		        			
        			}
    		        	%>
    		        		</TEXTAREA>	<br/>
    	    	        	<%
    				if(prev_lockMhs.equalsIgnoreCase("true")) {
    					submitButtonValue = "Reset Approval";
    							%>
    	        					<input type="hidden" name="approval" value="reset" />
    	        				<%
    				}
    				else {
    					submitButtonValue = "Approved";
    							%>
    	        					<input type="hidden" name="approval" value="approved" />
    	        				<%
    				}
    	    	        	%>
							<input type="hidden" name="thsms" value="<%=prev_thsms %>" />
    						<input type="hidden" name="id_obj" value="<%=objId %>" />
    						<input type="hidden" name="nmm" value="<%=nmm %>" />
    						<input type="hidden" name="npm" value="<%=npm %>" />
    						<input type="hidden" name="kdpst" value="<%=kdpst %>" />
    						<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
    						<input type="hidden" name="beritaNotif" value="<%=beritaNotif %>" />
    						<input type="hidden" name="kategoriNotif" value="<%=kstegoriNotif %>" />
    					<%
  	  				boolean lewat = false;
    					//if(approvedNotif.equalsIgnoreCase("false")&&declinedNotif.equalsIgnoreCase("false")) {
    				if(approvedNotif!=null && approvedNotif.equalsIgnoreCase("false")&&declinedNotif!=null && declinedNotif.equalsIgnoreCase("false")) {	
    					lewat = true;    					
    					%>	
        					<input type="submit" name="statusApproval" value="Reject" style="width:35%;text-align:center;color:red"/>
        				<%
    				}
    					//if(beritaNotif.equalsIgnoreCase("PERMOHONAN BUKA KUNCI")) {
    				if(beritaNotif!=null && beritaNotif.equalsIgnoreCase("PERMOHONAN BUKA KUNCI")) {	
    					
    					if(unlockDeclinedNotif.equalsIgnoreCase("false")) {
    						lewat = true;
    						%>	
            					<input type="submit" name="statusApproval" value="Reject Buka Kunci" style="width:35%;text-align:center;color:red"/>
            				<%
    					}
    				}
					if(lewat) {
        				%>
        					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        				<%
					}
        				//if((approvedNotif.equalsIgnoreCase("true") && submitButtonValue.equalsIgnoreCase("Reset Approval"))||(approvedNotif.equalsIgnoreCase("false"))&&declinedNotif.equalsIgnoreCase("false")) {
        			if((approvedNotif!=null && approvedNotif.equalsIgnoreCase("true") && submitButtonValue!=null && submitButtonValue.equalsIgnoreCase("Reset Approval"))||((approvedNotif==null || approvedNotif.equalsIgnoreCase("false"))&&(declinedNotif==null || declinedNotif.equalsIgnoreCase("false")))) {		
        				%>
        					<input type="submit" name="statusApproval" value="<%=submitButtonValue %>" style="width:35%;text-align:center"/>
        				<%
        			}
        					%>
							</td>
        				</tr>
						<%
 		   		}
				else {
					%>
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center" colspan="8"><label><B>
        				<%
        			String keter_thsms_and_value = Converter.convertThsms(prev_thsms);
					StringTokenizer stt = new StringTokenizer(keter_thsms_and_value,"#&");
					String keter_thsms = stt.nextToken();
					String value_thsms = stt.nextToken();
        			out.print(keter_thsms); 
        				%></B> </label></td>
        			</tr>
        			<%
				}
        	}
			else {
					%>
	        		<tr>
	        			<td style="background:#369;color:#fff;text-align:center" colspan="6"><label><B><%=Converter.convertThsmsKeterOnly(prev_thsms) %></B> </label></td>
	        			<td style="background:#369;color:#fff;text-align:center" colspan="2">
	        				<%
	        				
	       		if(cetakKrsMode) {
	        					////System.out.println("in 1");
	        				%>
	        				<form action="file.downloadKrs">
	        					<%
	       		}
	       		else {
	       			if(cetakKhsMode) {
	        						////System.out.println("in 2");
	        						%>
	    	        		<form action="file.downloadKhs">
	    	        				<%
	       			}
	       		}
	        					////System.out.println("jsp vHistTmp Size 1 ="+vHistTmp.size());
	        					//session.setAttribute("vHistTmp", vHistTmp);
	        					%>
	        					<input type="hidden" name="thsms" value="<%=prev_thsms %>" />
        						<input type="hidden" name="id_obj" value="<%=objId %>" />
        						<input type="hidden" name="nmm" value="<%=nmm %>" />
        						<input type="hidden" name="npm" value="<%=npm %>" />
        						<input type="hidden" name="kdpst" value="<%=kdpst %>" />
        						<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
	        					<input type="submit" value="<%=submitButtonValue %>" style="width:99%;text-align:center"/>
	        				</form>
						</td>
	        		</tr>
	        		<%	
			}
        			%>
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center;width:60px"><label><B>KODE</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:left;width:400px"><label><B>MATAKULIAH</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;width:55px"><label><B>NILAI</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;width:55px"><label><B>BOBOT</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;width:35px"><label><B>SKS</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;width:55px"><label><B>KELAS</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;width:90px"><label><B>SHIFT</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>KODE KAMPUS</B> </label></td>
        			</tr>
        			<tr>
        			
        				<td style="color:#000;text-align:center;"><label><B><%=prev_kdkmk %></B> </label></td>
        				<td style="color:#000;text-align:left;"><label><B><%=prev_nakmk %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_nlakh %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_bobot %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_sksmk %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B>
        				<%
    	    if(Checker.isStringNullOrEmpty(prev_kelas)) {
       			out.print("N/A");
       		}
       		else {
	       		out.print(prev_kelas);
       		}
        				
        				%>
        				
        				</B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B>N/A</B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_kodeKampus %></B> </label></td>
        			</tr>
        			<%
			while(lih.hasNext()) {
				thsms=(String)lih.next();
				kdkmk=(String)lih.next();
				nakmk=(String)lih.next();
				nlakh=(String)lih.next();
				bobot=(String)lih.next();
				sksmk=(String)lih.next();
				kelas=(String)lih.next();
				sksem=(String)lih.next();
				nlips=(String)lih.next();
				skstt=(String)lih.next();
				nlipk=(String)lih.next();
				shift=(String)lih.next();
				krsdown=(String)lih.next();
				khsdown=(String)lih.next();
				bakprove=(String)lih.next();
				paprove=(String)lih.next();
				note=(String)lih.next();
				lock=(String)lih.next();
				baukprove=(String)lih.next();
						
				idkmk =(String)lih.next();
				addReq =(String)lih.next();
				drpReq  =(String)lih.next();
				npmPa =(String)lih.next();
				npmBak =(String)lih.next();
				npmBaa =(String)lih.next();
				npmBauk =(String)lih.next();
				baaProve =(String)lih.next();
				ktuProve =(String)lih.next();
				dknProve =(String)lih.next();
				npmKtu =(String)lih.next();
				npmDekan =(String)lih.next();
				lockMhs =(String)lih.next();
				kodeKampus =(String)lih.next();
						
				if(prev_thsms.equalsIgnoreCase(thsms)) {
					prev_thsms = ""+thsms;
					prev_kdkmk=""+kdkmk;
					prev_nakmk=""+nakmk;
					prev_nlakh=""+nlakh;
					prev_bobot=""+bobot;
					prev_sksmk=""+sksmk;
					prev_kelas=""+kelas;
					prev_sksem=""+sksem;
					prev_nlips=""+nlips;
					prev_skstt=""+skstt;
					prev_nlipk=""+nlipk;
					prev_shift=""+shift;
					prev_krsdown=""+krsdown;
					prev_khsdown=""+khsdown;
					prev_bakprove=""+bakprove;
					prev_paprove=""+paprove;
					prev_note=""+note;
					prev_lock=""+lock;
					prev_baukprove=""+baukprove;
					prev_idkmk = ""+idkmk;
					prev_addReq =""+addReq;
					prev_drpReq  =""+drpReq;
					prev_npmPa =""+npmPa;
					prev_npmBak =""+npmBak;
					prev_npmBaa =""+npmBaa;
					prev_npmBauk =""+npmBauk;
					prev_baaProve =""+baaProve;
					prev_ktuProve =""+ktuProve;
					prev_dknProve =""+dknProve;
					prev_npmKtu =""+npmKtu;
					prev_npmDekan =""+npmDekan;
					prev_lockMhs =""+lockMhs;
					prev_kodeKampus =""+kodeKampus;
						%>
					<tr>
					
        				<td style="color:#000;text-align:center;"><label><B><%=prev_kdkmk %></B> </label></td>
        				<td style="color:#000;text-align:left;"><label><B><%=prev_nakmk %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_nlakh %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_bobot %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_sksmk %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B>
        				<%
        			if(Checker.isStringNullOrEmpty(prev_kelas)) {
	        			out.print("N/A");
    	    		}
        			else {
        				out.print(prev_kelas);
        			}
        				
        				%></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B>N/A</B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_kodeKampus %></B> </label></td>
        			</tr>
        			<%
				}
				else {
					if(!prev_thsms.equalsIgnoreCase(thsms)) {
								//ganti tahun
								//1. tutup table sebelumnya dengan ngasih nilai ipk dan ips
					
	        		%>
					<tr>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="6"><label><B>SKS & IP SEMESTER</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="1"><label><B><%=prev_sksem %></B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="1"><label><B><%=NumberFormater.return2digit(prev_nlips) %></B> </label></td>
        			</tr>
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="6"><label><B>SKS & IP KOMULATIF</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="1"><label><B><%=prev_skstt %></B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="1"><label><B><%=NumberFormater.return2digit(prev_nlipk) %></B> </label></td>
        			</tr>
        		</table>
        		<br />	
					<%		
						prev_thsms = ""+thsms;
						prev_kdkmk=""+kdkmk;
						prev_nakmk=""+nakmk;
						prev_nlakh=""+nlakh;
						prev_bobot=""+bobot;
						prev_sksmk=""+sksmk;
						prev_kelas=""+kelas;
						prev_sksem=""+sksem;
						prev_nlips=""+nlips;
						prev_skstt=""+skstt;
						prev_nlipk=""+nlipk;
						prev_shift=""+shift;
						prev_krsdown=""+krsdown;
						prev_khsdown=""+khsdown;
						prev_bakprove=""+bakprove;
						prev_paprove=""+paprove;
						prev_note=""+note;
						prev_lock=""+lock;
						prev_baukprove=""+baukprove;
						
						prev_idkmk = ""+idkmk;
						prev_addReq =""+addReq;
						prev_drpReq  =""+drpReq;
						prev_npmPa =""+npmPa;
						prev_npmBak =""+npmBak;
						prev_npmBaa =""+npmBaa;
						prev_npmBauk =""+npmBauk;
						prev_baaProve =""+baaProve;
						prev_ktuProve =""+ktuProve;
						prev_dknProve =""+dknProve;
						prev_npmKtu =""+npmKtu;
						prev_npmDekan =""+npmDekan;
						prev_lockMhs =""+lockMhs;
						prev_kodeKampus = ""+kodeKampus;
							%>
					
				<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px">
        			<%
						if(!cetakKrsMode && !cetakKhsMode) {
						//if(paApprover && prev_lock.equalsIgnoreCase("false") && (curNpmPa.equalsIgnoreCase(validUsr.getNpm())||(validApprovee!=null && validApprovee.equalsIgnoreCase("true"))) && prev_thsms.equalsIgnoreCase(thsmsKrs)) { //mode view krs { //mode view krs
							if(validApprovee!=null && validApprovee.equalsIgnoreCase("true") && prev_thsms.equalsIgnoreCase(thsmsKrs)) {	
	    				%>
	    			<tr>
	    		     	<td style="background:#369;color:#fff;text-align:center" colspan="8"><label><B><%=Converter.convertThsmsKeterOnly(prev_thsms) %></B> </label></td>
	    		    </tr>
    		        <tr>
    		        	<td style="background:#369;color:#fff;text-align:center" colspan="8">
    		        	Catatan Penolakan / Persetujuan :<br/>
    		        	<form action="go.krsApproval">
    		        	<TEXTAREA rows="4" style="width:98%" name="catatan">
    		        	
    		        	<%
    		        	//out.print(noteNotif);
	    		      			if(prev_note!=null && !Checker.isStringNullOrEmpty(prev_note)) {
    			        			out.print(prev_note);
    		        		
    			        		}	
    		        	%>
    		        	</TEXTAREA>	<br/>
    	    	        	<%
    							if(prev_lockMhs.equalsIgnoreCase("true")) {
    								submitButtonValue = "Reset Approval";
    							%>
    	        					<input type="hidden" name="approval" value="reset" />
    	        				<%
    							}
    							else {
    								submitButtonValue = "Approved";
    								%>
    	        					<input type="hidden" name="approval" value="approved" />
    	        				<%
	    						}
    	    	        	%>
						<input type="hidden" name="thsms" value="<%=prev_thsms %>" />
    					<input type="hidden" name="id_obj" value="<%=objId %>" />
    					<input type="hidden" name="nmm" value="<%=nmm %>" />
    					<input type="hidden" name="npm" value="<%=npm %>" />
    					<input type="hidden" name="kdpst" value="<%=kdpst %>" />
    					<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
    					<input type="hidden" name="beritaNotif" value="<%=beritaNotif %>" />
    					<input type="hidden" name="kategoriNotif" value="<%=kstegoriNotif %>" />
        				<%
    							boolean lewat = false;
        					////System.out.println("approvedNotif="+approvedNotif);
        					//if(approvedNotif.equalsIgnoreCase("false")&&declinedNotif.equalsIgnoreCase("false")) {
        						if((approvedNotif==null || approvedNotif.equalsIgnoreCase("false"))&&(declinedNotif==null || declinedNotif.equalsIgnoreCase("false"))) {	
    								lewat = true;    					
    					%>	
        					<input type="submit" name="statusApproval" value="Reject" style="width:35%;text-align:center;color:red"/>
        				<%
    							}
        						if(beritaNotif!=null && beritaNotif.equalsIgnoreCase("PERMOHONAN BUKA KUNCI")) {
    								if(unlockDeclinedNotif.equalsIgnoreCase("false")) {
    									lewat = true;
    						%>	
            					<input type="submit" name="statusApproval" value="Reject Buka Kunci" style="width:35%;text-align:center;color:red"/>
            				<%
    								}
    							}
								if(lewat) {
        				%>
        					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        				<%
								}
        					//if((approvedNotif.equalsIgnoreCase("true") && submitButtonValue.equalsIgnoreCase("Reset Approval"))||(approvedNotif.equalsIgnoreCase("false"))&&declinedNotif.equalsIgnoreCase("false")) {
        						if((approvedNotif!=null && approvedNotif.equalsIgnoreCase("true") && submitButtonValue!=null && submitButtonValue.equalsIgnoreCase("Reset Approval"))||((approvedNotif==null || approvedNotif.equalsIgnoreCase("false"))&&(declinedNotif==null || declinedNotif.equalsIgnoreCase("false")))) {	
        					
        				%>
        					<input type="submit" name="statusApproval" value="<%=submitButtonValue %>" style="width:35%;text-align:center"/>
        				<%
        						}
        				%>
					</td>
	       		</tr>
					<%
	    					}
							else {
							
							////System.out.println("pint 2");
					%>
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center" colspan="8"><label><B>
						<%
	        					String keter_thsms_and_value = Converter.convertThsms(prev_thsms);
								StringTokenizer stt = new StringTokenizer(keter_thsms_and_value,"#&");
								String keter_thsms = stt.nextToken();
								String value_thsms = stt.nextToken();
        						out.print(keter_thsms); 
        				%>
						</B> </label></td>
						
						
        			</tr>
        			<%
							}
	        			}
						else {
						////System.out.println("pint 3");
					
					%>
	        		<tr>
	        			<td style="background:#369;color:#fff;text-align:center" colspan="6"><label><B><%=Converter.convertThsmsKeterOnly(prev_thsms) %></B> </label></td>
	        			<td style="background:#369;color:#fff;text-align:center" colspan="2">
	        			<%
	    	   				submitButtonValue = "Download";
	       					if(cetakKrsMode) {
	        					////System.out.println("in 1");
	        				%>
	        				<form action="file.downloadKrs">
	        					<%
	       					}
	        				else {
	        					if(cetakKhsMode) {
	        						////System.out.println("in 2");
	        						%>
	    	        		<form action="file.downloadKhs">
	    	        				<%
	        					}
	        				}
	        			
	        				    ////System.out.println("jsp vHistTmp Size 2 ="+vHistTmp.size());
	        				    
	        				    %>
        						<input type="hidden" name="thsms" value="<%=prev_thsms %>" />
        						<input type="hidden" name="id_obj" value="<%=objId %>" />
        						<input type="hidden" name="nmm" value="<%=nmm %>" />
        						<input type="hidden" name="npm" value="<%=npm %>" />
        						<input type="hidden" name="kdpst" value="<%=kdpst %>" />
        						<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
	        					<input type="submit" value="<%=submitButtonValue %>" style="width:99%;text-align:center"/>
	        				</form>
						</td>
	        		</tr>
	        		<%	
						}
        			%>
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center;width:60px"><label><B>KODE</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:left;width:400px"><label><B>MATAKULIAH</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;width:55px"><label><B>NILAI</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;width:55px"><label><B>BOBOT</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;width:35px"><label><B>SKS</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;width:55px"><label><B>KELAS</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;width:90px"><label><B>SHIFT</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>KODE KAMPUS</B> </label></td>
        			</tr>
        			<tr>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_kdkmk %></B> </label></td>
        				<td style="color:#000;text-align:left;"><label><B><%=prev_nakmk %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_nlakh %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_bobot %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_sksmk %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B>
						<%
	        			if(Checker.isStringNullOrEmpty(prev_kelas)) {
    	    				out.print("N/A");
        				}
        				else {
        					out.print(prev_kelas);
        				}
        				
        				%>
						</B> </label></td>
						<td style="color:#000;text-align:center;"><label><B>N/A</B> </label></td>
						<td style="color:#000;text-align:center;"><label><B><%=prev_kodeKampus %></B> </label></td>
        			</tr>
        		<% 	
					}
				}
			}//end while
					/*
					*tutup table setelah end while
					*/
	        		%>	
					<tr>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="6"><label><B>SKS & IP SEMESTER</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="1"><label><B><%=prev_sksem %></B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="1"><label><B><%=NumberFormater.return2digit(prev_nlips) %></B> </label></td>
        			</tr>
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="6"><label><B>SKS & IP KOMULATIF</B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="1"><label><B><%=prev_skstt %></B> </label></td>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="1"><label><B><%=NumberFormater.return2digit(prev_nlipk) %></B> </label></td>
        			</tr>
        		</table>
        		<%
		}
	}
	else {
					
				%>
				<h1 style="text-align:center">BELUM ADA REKORD KRS/KHS</h1>
				<div style="text-align:center;font-style:italic">Kami dalam tahap migrasi, harap "kirim pesan" ke tata usaha fakultas bila data riwayat studi tidak sesuai agar dapat mendapat prioritas update</div>
				<%
	}
}//end if(size>0)
else {
				%>
				<h2 align="center"><b>Anda Tidak Mempunyai Hak Akses Untuk Data ini</b></h2>
				<% 
}
		//creta force back to
		//String forceTo = "get.histKrs?id_obj="+v_id_obj+"&nmm="+v_nmmhs+"&npm="+v_npmhs+"&obj_lvl="+v_obj_lvl+"&kdpst="+v_kdpst+"&cmd=histKrs";
		//session.removeAttribute("forceBackTo");
		//session ngga diapus krn hubungan dengan force to krs approval
		%>
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>