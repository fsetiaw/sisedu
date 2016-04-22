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
boolean viewBahanAjar = false;
if(cmd.equalsIgnoreCase("vba")) {
	viewBahanAjar = true;
}
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
	if(v_cf!=null && v_cf.size()>0 && !viewBahanAjar) {
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

				
				////System.out.println("point 1");
	if(vHistKrsKhs!=null && vHistKrsKhs.size()>0) {			
		ListIterator lih = vHistKrsKhs.listIterator();
		boolean first = true;
		while(lih.hasNext()) {
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
			
			if(thsms.equalsIgnoreCase(thsms_now)) {
				if(first) {
					first = false;			
					%>
					<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:650px">
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center" colspan="3"><label><B>
        				<%
        			String keter_thsms_and_value = Converter.convertThsms(thsms);
					StringTokenizer stt = new StringTokenizer(keter_thsms_and_value,"#&");
					String keter_thsms = stt.nextToken();
					String value_thsms = stt.nextToken();
        			out.print(keter_thsms); 
        				%></B> </label></td>
        			</tr>
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center;width:60px"><label><B>KODE</B> </label></td>
        				<td colspan="2" style="background:#369;color:#fff;text-align:left;"><label><B>MATAKULIAH</B> </label></td>
        				
        				
        			</tr>
        			<tr>
        			
        				<td style="color:#000;text-align:center;"><label><B><%=kdkmk %></B> </label></td>
        				<td style="color:#000;text-align:left;width:450px"><label><B><%=nakmk %></B> </label></td>
        				<td style="color:#000;text-align:center;width:140"><label><B>
        				<%
        				//prep.bahanAjarGivenMk?atMenu=mba&nakmk=<%=prev_nakmk &kdkmk=<%=prev_kdkmk &kdpst=<%=prev_kdpst &idkur=<%=idkur "><%=prev_nakmk </a></B> </label>
        				%>
        				<form action="prep.bahanAjarGivenMk">
        					<input type="hidden" name="atMenu" value="vba"/>
        					<input type="hidden" name="nakmk" value="<%=nakmk%>"/>
        					<input type="hidden" name="kdkmk" value="<%=kdkmk%>"/>
        					<input type="hidden" name="kdpst" value="<%=kdpst%>"/>
        					<input type="hidden" name="idkur" value="<%=idkur%>"/>
        					<input type="hidden" name="target_thsms" value="<%=thsms_now%>"/>
        					<input type="hidden" name="shift" value="<%=shift%>"/>
        					<input type="submit" name="submit" value="Cari Bahan Ajar" />
        				</form>
        				</B> </label></td>
        				
        			</tr>
        			<%
				}
				else {
				%>
				<tr>
        			
        				<td style="color:#000;text-align:center;"><label><B><%=kdkmk %></B> </label></td>
        				<td style="color:#000;text-align:left;"><label><B><%=nakmk %></B> </label></td>
        				<td style="color:#000;text-align:center;"><label><B>
        				<form action="prep.bahanAjarGivenMk">
        					<input type="hidden" name="atMenu" value="vba"/>
        					<input type="hidden" name="nakmk" value="<%=nakmk%>"/>
        					<input type="hidden" name="kdkmk" value="<%=kdkmk%>"/>
        					<input type="hidden" name="kdpst" value="<%=kdpst%>"/>
        					<input type="hidden" name="idkur" value="<%=idkur%>"/>
        					<input type="hidden" name="target_thsms" value="<%=thsms_now%>"/>
        					<input type="hidden" name="shift" value="<%=shift%>"/>
        					<input type="submit" name="submit" value="Cari Bahan Ajar" />
        				</form>
						</B> </label></td>
        				
        			</tr>
					<%
				}
			}
		}	
	}
}	
        			%>
        </table>			
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>