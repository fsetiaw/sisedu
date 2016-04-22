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
Vector vCp = (Vector) request.getAttribute("vCp");
//format vCp:
	//
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
////System.out.println("infoKrsNotificationAtPmb@historykrs.jsp="+infoKrsNotificationAtPmb);
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
//System.out.println("tknPaInfo="+tknPaInfo);
//System.out.println("currentPa="+currentPa);
//System.out.println("ini3");
Vector vSelectMk = new Vector();
ListIterator liSmk = vSelectMk.listIterator();
if(vMk!=null && vMk.size()>0) {
	//System.out.println("ini5");
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
		<br/>
		<!-- Column 1 start -->
		
		<%
		/*
		msg di pas dari HistoryKrsKhs servlet, bilai msg !=null berarti blum ditentukan kurikulumnya
		*/
		//System.out.println("sampe sini");
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
			//System.out.println("allowViewKrs="+allowViewKrs);
if(allowViewKrs) {
				
				
	Vector vHistKrsKhs = (Vector)request.getAttribute("vHistKrsKhs");
	Vector vHistTmp = (Vector)vHistKrsKhs.clone();
				//System.out.println("vHistTmp="+vHistTmp.size());
	if(cetakKrsMode||cetakKhsMode) {
		session.setAttribute("vHistTmp", vHistTmp);
	}
	//System.out.println("size1 ="+vHistKrsKhs.size());
	Vector vTrnlp = (Vector)request.getAttribute("vTrnlp");
	//System.out.println("size1a ="+vTrnlp.size());

	session.setAttribute("vHistKrsKhsForEdit",vHistKrsKhs);
	session.setAttribute("vTrnlpForEdit",vTrnlp);
	request.removeAttribute("vHistKrsKhs");
	request.removeAttribute("vTrnlp");
				//System.out.println("vHistTmp1="+vHistTmp.size());
				
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
				
				//System.out.println("point 1");
	if(vHistKrsKhs!=null && vHistKrsKhs.size()>0) {		
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
		if(allowInsertKrs && (vCp!=null && vCp.size()>0)) {
	%>
	 <form action="proses.updKrsCp" >
	 	<input type="hidden" name="continuSys" value="true" />
	 	<input type="hidden" name="id_obj" value="<%=objId %>" />
		<input type="hidden" name="nmm" value="<%=nmm %>" />
		<input type="hidden" name="npm" value="<%=npm %>" />
		<input type="hidden" name="kdpst" value="<%=kdpst %>" />
		<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
	<%		
		}
		%>
		<br/>
	<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px">
		<tr>
       		<td style="background:#369;color:#fff;text-align:center;width:60px"><label><B>KODE</B> </label></td>
       		<td style="background:#369;color:#fff;text-align:left;width:400px"><label><B>MATAKULIAH</B> </label></td>
       		<td style="background:#369;color:#fff;text-align:center;width:55px"><label><B>NILAI</B> </label></td>
       		<td style="background:#369;color:#fff;text-align:center;width:55px"><label><B>BOBOT</B> </label></td>
       		<td style="background:#369;color:#fff;text-align:center;width:30px"><label><B>SKS</B> </label></td>
       		<td style="background:#369;color:#fff;text-align:center;width:150px"><label><B>INFO KELAS</B> </label></td>
       		<td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>KODE KAMPUS</B> </label></td>
       	</tr>
		<%
		String tkn_mk_sudah_tampil = "";
		ListIterator lih = vHistKrsKhs.listIterator();
		String skstt="0";
		String nlipk="0";
		// proses eliminasi bila mata kuliah sudah pernah diambil maka hanya thsms terkinin yg tampil
		
		Vector<Vector> vit = new Vector<Vector>();
		ListIterator<Vector> lit = vit.listIterator();
		while(lih.hasNext()) {
			String thsms=(String)lih.next();//1
			String kdkmk=(String)lih.next();//2
			String nakmk=(String)lih.next();//3
			String nlakh=(String)lih.next();//4
			String bobot=(String)lih.next();//5
			String sksmk=(String)lih.next();//6
			String kelas=(String)lih.next();//7
			String sksem=(String)lih.next();//8
			String nlips=(String)lih.next();//9
			skstt=(String)lih.next();//10
			nlipk=(String)lih.next();//11
			String shift=(String)lih.next();//12
			String krsdown=(String)lih.next();//13
			String khsdown=(String)lih.next();//14
			String bakprove=(String)lih.next();//15
			String paprove=(String)lih.next();//16
			String note=(String)lih.next();//17
			String lock=(String)lih.next();//18
			String baukprove=(String)lih.next();//19
					
			String idkmk =(String)lih.next();//20
			String addReq =(String)lih.next();//21
			String drpReq  =(String)lih.next();//22
			String npmPa =(String)lih.next();//23
			String npmBak =(String)lih.next();//24
			String npmBaa =(String)lih.next();//25
			String npmBauk =(String)lih.next();//26
			String baaProve =(String)lih.next();//27
			String ktuProve =(String)lih.next();//28
			String dknProve =(String)lih.next();//29
			String npmKtu =(String)lih.next();//30
			String npmDekan =(String)lih.next();//31
			String lockMhs =(String)lih.next();//32
			String kodeKampus =(String)lih.next();//33
			String cuid =(String)lih.next();//34
			String cuid_init =(String)lih.next();//35
			String npmdos =(String)lih.next();//36
			String nodos =(String)lih.next();//37
			String npmasdos =(String)lih.next();//38
			String noasdos =(String)lih.next();//39
			String nmmdos =(String)lih.next();//40
			String nmmasdos =(String)lih.next(); //41
			if(!tkn_mk_sudah_tampil.contains(kdkmk+"-"+nakmk)) {	
				tkn_mk_sudah_tampil = tkn_mk_sudah_tampil+"`"+kdkmk+"-"+nakmk;
				Vector vtemp = new Vector();
				ListIterator litemp = vtemp.listIterator();
				litemp.add(thsms);//1
				litemp.add(kdkmk);//2
				litemp.add(nakmk);//3
				litemp.add(nlakh);//4
				litemp.add(bobot);//5
				litemp.add(sksmk);//6
				litemp.add(kelas);//7
				litemp.add(sksem);//8
				litemp.add(nlips);//9
				litemp.add(skstt);//10
				litemp.add(nlipk);//11
				litemp.add(shift);//12
				litemp.add(krsdown);//13
				litemp.add(khsdown);//14
				litemp.add(bakprove);//115
				litemp.add(paprove);//16
				litemp.add(note);//17
				litemp.add(lock);//18
				litemp.add(baukprove);//19
						
				litemp.add(idkmk);//20
				litemp.add(addReq);//21
				litemp.add(drpReq);//22
				litemp.add(npmPa);//23
				litemp.add(npmBak);//24
				litemp.add(npmBaa);//25
				litemp.add(npmBauk);//26
				litemp.add(baaProve);//27
				litemp.add(ktuProve);//28
				litemp.add(dknProve);//29
				litemp.add(npmKtu);//30
				litemp.add(npmDekan);//31
				litemp.add(lockMhs);//32
				litemp.add(kodeKampus);//33
				litemp.add(cuid);//34
				litemp.add(cuid_init);//35
				litemp.add(npmdos);//36
				litemp.add(nodos);//37
				litemp.add(npmasdos);//38
				litemp.add(noasdos);//39
				litemp.add(nmmdos);//40
				litemp.add(nmmasdos);//41 
				lit.add(vtemp);
			}
			else {
				//makul sudah pernah tampil
				int index_vit = 0;
				lit = vit.listIterator();
				boolean match = false;
				while(lit.hasNext() && !match) {
					Vector<String> vtemp = (Vector<String>)lit.next();
					ListIterator<String> litemp = vtemp.listIterator();
					while(litemp.hasNext() && !match ) {
						String vthsms = (String)litemp.next();
						String vkdkmk = (String)litemp.next();
						String vnakmk = (String)litemp.next();
						String vnlakh = (String)litemp.next();
						String vbobot = (String)litemp.next();
						String vsksmk = (String)litemp.next();
						String vkelas = (String)litemp.next();
						String vsksem = (String)litemp.next();
						String vnlips = (String)litemp.next();
						String vskstt = (String)litemp.next();
						String vnlipk = (String)litemp.next();
						String vshift = (String)litemp.next();
						String vkrsdown = (String)litemp.next();
						String vkhsdown = (String)litemp.next();
						String vbakprove = (String)litemp.next();
						String vpaprove = (String)litemp.next();
						String vnote = (String)litemp.next();
						String vlock = (String)litemp.next();
						String vbaukprove = (String)litemp.next();
						String vidkmk = (String)litemp.next();
						String vaddReq = (String)litemp.next();
						String vdrpReq = (String)litemp.next();
						String vnpmPa = (String)litemp.next();
						String vnpmBak = (String)litemp.next();
						String vnpmBaa = (String)litemp.next();
						String vnpmBauk = (String)litemp.next();
						String vbaaProve = (String)litemp.next();
						String vktuProve = (String)litemp.next();
						String vdknProve = (String)litemp.next();
						String vnpmKtu = (String)litemp.next();
						String vnpmDekan = (String)litemp.next();
						String vlockMhs = (String)litemp.next();
						String vkodeKampus = (String)litemp.next();
						String vcuid = (String)litemp.next();
						String vcuidInit = (String)litemp.next();
						String vnpmdos = (String)litemp.next();
						String vnodos = (String)litemp.next();
						String vnpmasdos = (String)litemp.next();
						String vnoasdos = (String)litemp.next();
						String vnmmdos = (String)litemp.next();
						String vnmmasdos = (String)litemp.next();
						if(vkdkmk.equalsIgnoreCase(kdkmk)) {
							match = true;
							//System.out.println("kdkmk vs vkdkmk = "+kdkmk);
							//System.out.println("thsms vs vThsms = "+thsms+" vs "+vthsms);
							//System.out.println("thsms.compareToIgnoreCase(vthsms)="+thsms.compareToIgnoreCase(vthsms));
							if(thsms.compareToIgnoreCase(vthsms)>0) {
								//System.out.println("thsms > vThsms ");
								//System.out.println("index_vit = "+index_vit);
								//lit.remove();
								//System.out.println("vit1 size- = "+vit.size());
								vtemp = new Vector<String>();
								litemp = vtemp.listIterator();
								
								litemp.add(thsms);
								litemp.add(kdkmk);
								litemp.add(nakmk);
								litemp.add(nlakh);
								litemp.add(bobot);
								litemp.add(sksmk);
								litemp.add(kelas);
								litemp.add(sksem);
								litemp.add(nlips);
								litemp.add(skstt);
								litemp.add(nlipk);
								litemp.add(shift);
								litemp.add(krsdown);
								litemp.add(khsdown);
								litemp.add(bakprove);
								litemp.add(paprove);
								litemp.add(note);
								litemp.add(lock);
								litemp.add(baukprove);
										
								litemp.add(idkmk);
								litemp.add(addReq);
								litemp.add(drpReq);
								litemp.add(npmPa);
								litemp.add(npmBak);
								litemp.add(npmBaa);
								litemp.add(npmBauk);
								litemp.add(baaProve);
								litemp.add(ktuProve);
								litemp.add(dknProve);
								litemp.add(npmKtu);
								litemp.add(npmDekan);
								litemp.add(lockMhs);
								litemp.add(kodeKampus);
								litemp.add(cuid);
								litemp.add(cuid_init);
								litemp.add(npmdos);
								litemp.add(nodos);
								litemp.add(npmasdos);
								litemp.add(noasdos);
								litemp.add(nmmdos);
								litemp.add(nmmasdos); 
								//System.out.println("vtemp size = "+vtemp.size());
								
								//vit.setElementAt(vtemp, index_vit);
								lit.set(vtemp);
							}
						}
						tkn_mk_sudah_tampil = tkn_mk_sudah_tampil+"`"+kdkmk+"-"+nakmk;
					}
					index_vit++;
				}
			}
		}	
		//replaceing old vhistkrskhs
		if(vit!=null && vit.size()>0) {
			//System.out.println("vit size = "+vit.size());
		
			vHistKrsKhs = new Vector();
			lih = vHistKrsKhs.listIterator();
		
			lit = vit.listIterator();
			while(lit.hasNext()) {
				Vector vtemp = (Vector)lit.next();
				ListIterator litemp = vtemp.listIterator();
				while(litemp.hasNext()) {
					String vthsms = (String)litemp.next();
					String vkdkmk = (String)litemp.next();
					String vnakmk = (String)litemp.next();
					String vnlakh = (String)litemp.next();
					String vbobot = (String)litemp.next();
					String vsksmk = (String)litemp.next();
					String vkelas = (String)litemp.next();
					String vsksem = (String)litemp.next();
					String vnlips = (String)litemp.next();
					String vskstt = (String)litemp.next();
					String vnlipk = (String)litemp.next();
					String vshift = (String)litemp.next();
					String vkrsdown = (String)litemp.next();
					String vkhsdown = (String)litemp.next();
					String vbakprove = (String)litemp.next();
					String vpaprove = (String)litemp.next();
					String vnote = (String)litemp.next();
					String vlock = (String)litemp.next();
					String vbaukprove = (String)litemp.next();
					String vidkmk = (String)litemp.next();
					String vaddReq = (String)litemp.next();
					String vdrpReq = (String)litemp.next();
					String vnpmPa = (String)litemp.next();
					String vnpmBak = (String)litemp.next();
					String vnpmBaa = (String)litemp.next();
					String vnpmBauk = (String)litemp.next();
					String vbaaProve = (String)litemp.next();
					String vktuProve = (String)litemp.next();
					String vdknProve = (String)litemp.next();
					String vnpmKtu = (String)litemp.next();
					String vnpmDekan = (String)litemp.next();
					String vlockMhs = (String)litemp.next();
					String vkodeKampus = (String)litemp.next();
					String vcuid = (String)litemp.next();
					String vcuid_init = (String)litemp.next();
					String vnpmdos = (String)litemp.next();
					String vnodos = (String)litemp.next();
					String vnpmasdos = (String)litemp.next();
					String vnoasdos = (String)litemp.next();
					String vnmmdos = (String)litemp.next();
					String vnmmasdos = (String)litemp.next();
					lih.add(vthsms);
					lih.add(vkdkmk);
					lih.add(vnakmk);
					lih.add(vnlakh);
					lih.add(vbobot);
					lih.add(vsksmk);
					lih.add(vkelas);
					lih.add(vsksem);
					lih.add(vnlips);
					lih.add(vskstt);
					lih.add(vnlipk);
					lih.add(vshift);
					lih.add(vkrsdown);
					lih.add(vkhsdown);
					lih.add(vbakprove);
					lih.add(vpaprove);
					lih.add(vnote);
					lih.add(vlock);
					lih.add(vbaukprove);
							
					lih.add(vidkmk);
					lih.add(vaddReq);
					lih.add(vdrpReq);
					lih.add(vnpmPa);
					lih.add(vnpmBak);
					lih.add(vnpmBaa);
					lih.add(vnpmBauk);
					lih.add(vbaaProve);
					lih.add(vktuProve);
					lih.add(vdknProve);
					lih.add(vnpmKtu);
					lih.add(vnpmDekan);
					lih.add(vlockMhs);
					lih.add(vkodeKampus);
					lih.add(vcuid);
					lih.add(vcuid_init);
					lih.add(vnpmdos);
					lih.add(vnodos);
					lih.add(vnpmasdos);
					lih.add(vnoasdos);
					lih.add(vnmmdos);
					lih.add(vnmmasdos); 
				}
			}
		}
		//System.out.println("vHistKrsKhs size = "+vHistKrsKhs.size());
		tkn_mk_sudah_tampil = "";
		lih = vHistKrsKhs.listIterator();
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
			skstt=(String)lih.next();
			nlipk=(String)lih.next();
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
			String cuid =(String)lih.next();
			String cuid_init =(String)lih.next();
			String npmdos =(String)lih.next();
			String nodos =(String)lih.next();
			String npmasdos =(String)lih.next();
			String noasdos =(String)lih.next();
			String nmmdos =(String)lih.next();
			String nmmasdos =(String)lih.next(); 
			//System.out.println("tkn_mk_sudah_tampil="+tkn_mk_sudah_tampil);
			//System.out.println(thsms+"-"+kdkmk+"-"+nakmk);
			//if(!tkn_mk_sudah_tampil.contains(kdkmk+"-"+nakmk)) {		
%>
					
       	<tr>        			
       		<td style="color:#000;text-align:center;"><label><B><%=kdkmk %></B> </label></td>
        	<td style="color:#000;text-align:left;"><label><B><%=nakmk %></B> </label></td>
        	<td style="color:#000;text-align:center;"><label><B><%=nlakh %></B> </label></td>
        	<td style="color:#000;text-align:center;"><label><B><%=bobot %></B> </label></td>
        	<td style="color:#000;text-align:center;"><label><B><%=sksmk %></B> </label></td>
        	<td style="color:#000;text-align:center;"><label><B>
        		<%
        		if(allowInsertKrs && (vCp!=null && vCp.size()>0)) {
        			//kod_gabung+"`"+nakmk+"`"+nopll+"`"+shift+"`"+idkur+"`"+idkmk+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+cancel+"`"+kdpst+"`"+kodeKampus+"||";
        		    //null`FILSAFAT DAN ETIKA PEMERINTAHAN`1`EKSEKUTIF PASCA`55`1023`0002013100003`A GURUH SUYOTO`MU10`false`65101`JST`null
					boolean pertama = true;
				%>
				<select name="kelas_info" style="width:99%">
					<!--  option value="null">PILIH KELAS</option -->
				<%	
        		//System.out.println("vCp="+vCp.size());
        			ListIterator lic = vCp.listIterator();
        			while(lic.hasNext()) {
        				String brs = (String)lic.next();
        				StringTokenizer stt = new StringTokenizer(brs,"`");
        				String kod_gabung_cp = stt.nextToken();
        				String nakmk_cp = stt.nextToken();
        				String nopll_cp = stt.nextToken();
        				String shift_cp = stt.nextToken();
        				String idkur_cp = stt.nextToken();
        				String idkmk_cp = stt.nextToken();
        				String npmdos_cp = stt.nextToken();
        				String nmmdos_cp = stt.nextToken();
        				String kdkmk_cp = stt.nextToken();
        				String cancel_cp = stt.nextToken();
        				String kdpst_cp = stt.nextToken();
        				String kodeKampus_cp = stt.nextToken();
        				String cuid_cp = stt.nextToken();
        				if(pertama) {
        					pertama = false;
        		%>
                	<option value="`null`<%=idkur%>`<%=idkmk%>`<%=thsmsKrs%>`null`null`null`null`null" selected="selected">PILIH KELAS</option>
               	<%				
        				}
        				if(idkmk.equalsIgnoreCase(idkmk_cp)) {
        					if(cuid.equalsIgnoreCase(cuid_cp)) {
        		%>
        	        <option value="<%=idkur_cp%>`<%=idkmk_cp%>`<%=thsmsKrs%>`<%=kdpst_cp%>`<%=shift_cp%>`<%=nopll_cp%>`<%=kodeKampus_cp%>`<%=cuid_cp%>" selected="selected"><%=nmmdos_cp %> (<%=shift_cp %>)</option>
        	    <%				
        					}
        					else {
        		%>
        			<option value="<%=idkur_cp%>`<%=idkmk_cp%>`<%=thsmsKrs%>`<%=kdpst_cp%>`<%=shift_cp%>`<%=nopll_cp%>`<%=kodeKampus_cp%>`<%=cuid_cp%>"><%=nmmdos_cp %> (<%=shift_cp %>)</option>
        		<%
        					}
        				}
        			}	
        		%>
        		</select>
        		<%	
        		}
        		else {
        			if(Checker.isStringNullOrEmpty(kelas)) {
    	       			out.print("N/A");
        	   		}
           			else {
    	       			out.print(kelas);
           			}	
        		}
    	    	
        			%>
        	</B> </label></td>
       		<td style="color:#000;text-align:center;"><label><B><%=Checker.pnn(kodeKampus)  %></B> </label></td>
        </tr>
        			<%
        	//	tkn_mk_sudah_tampil = tkn_mk_sudah_tampil+"`"+thsms+"`"+kdkmk+"-"+nakmk;	
			//}
		}
		/*
		*tutup table setelah end while
		*/
		%>	
		<tr>
			<td style="background:#369;color:#fff;text-align:center;" colspan="5"><label><B>SKS & IP SEMESTER</B> </label></td>
			<td style="background:#369;color:#fff;text-align:center;" colspan="1"><label><B><%="N/A" %></B> </label></td>
			<td style="background:#369;color:#fff;text-align:center;" colspan="1"><label><B><%="N/A" %></B> </label></td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:center;" colspan="5"><label><B>SKS & IP KOMULATIF</B> </label></td>
			<td style="background:#369;color:#fff;text-align:center;" colspan="1"><label><B><%=skstt %></B> </label></td>
			<td style="background:#369;color:#fff;text-align:center;" colspan="1"><label><B><%=NumberFormater.return2digit(nlipk) %></B> </label></td>
		</tr>
	<%
		if(allowInsertKrs && (vCp!=null && vCp.size()>0)) {
	%>
		<tr>
			<td style="background:#369;color:#fff;text-align:center;" colspan="8"><label>
			<B><input type="submit" value="UPDATE" style="width:35%"/></B> 
			</label></td>
		</tr>
	</form>
	<%		
		}
	%>	
	</table>
		<%
		
	}
	else {
					
				%>
				<h1 style="text-align:center">KURIKULUM UNTUK MAHASISWA INI BELUM DIISI</h1>
				<div style="text-align:center;font-style:italic">Harap diisi melalui edit profil</div>
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