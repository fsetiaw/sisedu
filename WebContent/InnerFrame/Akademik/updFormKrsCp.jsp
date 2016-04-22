<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.SearchDb" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@page import="beans.setting.Constants"%>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%
//////System.out.println("kkkkk");
ListIterator liSdh = null;
ListIterator liBlm = null;
ListIterator liCp = null;
Vector vSeataraYgDibuka = (Vector)request.getAttribute("vSeataraYgDibuka");
////System.out.println("vSeataraYgDibukaAupdFormKrsCp size = "+vSeataraYgDibuka.size());
Vector vSdh =(Vector) request.getAttribute("vSdh");
String kelasSesuaiShift = request.getParameter("sesuaiShift");
////System.out.println("kelasSesuaiShift0="+kelasSesuaiShift);
//String kelasSemuaShift = request.getParameter("semuaShift"); //sudah tidak dipakai lagi
String targetThsms = request.getParameter("targetThsms");
boolean notThsmsKrs = !targetThsms.equalsIgnoreCase(Checker.getThsmsKrs());
boolean whiteList = false;
boolean usrMhs = false;

String whiteListMode = request.getParameter("whiteListMode"); // urusan thsms, input krs diluar thsms krs
if(whiteListMode.equalsIgnoreCase("true")) { //urusan thsms, input krs diluar thsms krs
	whiteList = true;
}
//////System.out.println("aturan1="+kelasSesuaiShift);
//////System.out.println("aturan2="+kelasSemuaShift);
if(vSdh!=null && vSdh.size()>0) {
	liSdh = vSdh.listIterator();
}
else {
	vSdh =(Vector) session.getAttribute("vSdh");
	session.removeAttribute("vSdh");
	if(vSdh!=null && vSdh.size()>0) {
		liSdh = vSdh.listIterator();
	}
	else {
		liSdh = new Vector().listIterator();
	}	
}
Vector vBlm =(Vector) request.getAttribute("vBlm");
if(vBlm!=null && vBlm.size()>0) {
	liBlm = vBlm.listIterator();
}
else {
	vBlm =(Vector) session.getAttribute("vBlm");
	session.removeAttribute("vBlm");
	if(vBlm!=null && vBlm.size()>0) {
		liBlm = vBlm.listIterator();
	}
	else {
		liSdh = new Vector().listIterator();
	}	
}
Vector vCp =(Vector) request.getAttribute("vCp");
if(vCp!=null && vCp.size()>0) {
	liCp = vCp.listIterator();
}
else {
	vCp =(Vector) session.getAttribute("vCp");
	session.removeAttribute("vCp");
	if(vCp!=null && vCp.size()>0) {
		liCp = vCp.listIterator();
	}
	else {
		liCp = new Vector().listIterator();
	}
}
Vector v = null;
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
boolean cpMode = false;
if(vCp!=null && vCp.size()>0) {
	cpMode = true;
}

%>


</head>
<body>
<div id="header">
<!--   include file="../innerMenu.jsp" -->
<%@ include file="../krsKhsSubMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
	<!-- Column 1 start -->

		<br />
		<%
		boolean allowedAllThsms = validUsr.isUsrAllowTo("insertKrsAllThsms", kdpst);
		/*
		liBlm = vBlm.listIterator();
		while(liBlm.hasNext()) {
			out.print((String)liBlm.next()+"<br/>");
		}
		liBlm = vBlm.listIterator();
		

		ListIterator litm = vCp.listIterator();
		while(litm.hasNext()) {
			String brs1 = (String)litm.next();
		
			if(brs1.startsWith("idkmk")) {

				out.print(brs1+"<br/>");
			}
			else {
				if(brs1.startsWith("shift")) {
					out.print(brs1+"<br/>");
					out.print("------------------------------------------------------");
					Vector vKls = (Vector)litm.next();
					ListIterator liKls = vKls.listIterator();
					while(liKls.hasNext()) {
						out.print((String)liKls.next()+"<br/>");
					}
				}
			}	
		}

		out.print("vsudah<br/>");
		while(liSdh.hasNext()) {
			String baris = (String)liSdh.next();
			out.println(baris+"<br/>");
		}
		out.print("belum<br/>");
		while(liBlm.hasNext()) {
			String baris = (String)liBlm.next();
			out.println(baris+"<br/>");
		}
*/		
		/*
		msg di pas dari HistoryKrsKhs servlet, bilai msg !=null berarti blum ditentukan kurikulumnya
		*/
		if(!Checker.isStringNullOrEmpty(msg)) {
			SearchDb sdb = new SearchDb();
			String nmpst = sdb.getNmpst(kdpst);
			//kurikulum blm ditentukan
			Vector v_cf = validUsr.getScopeUpd7des2012("hasEditMkKurikulum");
			if(v_cf.size()>0) {
		%>
		
		<h2 align="center"><br/>Harap <a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Akademik/editTargetKurikulum.jsp?kdpst_nmpst=<%=v_kdpst%>,<%=nmpst %>" target="inner_iframe" class="active">Tentukan Kurikulum Untuk <%=nmm %> </a>Terlebih Dahulu</h2>
		<br/>
		<%
			}
			else {
				%>
				<h2 align="center">Kurikulum Untuk <%=nmm %> Belum Diisi oleh Tata Usaha</h2>
				<%		
			}
		}
		else {
			
		%>
		
<P>
					
    <form action="proses.updKrsCp" >	
    <input type="hidden" name="id_obj" value="<%=v_id_obj %>" />
    <input type="hidden" name="nmm" value="<%=v_nmmhs %>" />
    <input type="hidden" name="npm" value="<%=v_npmhs %>" />
    <input type="hidden" name="kdpst" value="<%=v_kdpst %>" />
    <input type="hidden" name="kdjen" value="<%=v_kdjen %>" />
    <input type="hidden" name="obj_lvl" value="<%=v_obj_lvl %>" />	
    		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:700px">
		<tr>
			<td style="background:#369;color:#fff;text-align:center;height:30px;font-size:25px" colspan="4"><label><B>PENGISIAN KRS</B></label>
			<%
			/* 
			*untuk CP mode maka thsms PMB only **** updated diganti thsms krs
			*dapat di overider kalo boleh all thsms
			*/
					%>
				<select name="thsms" style="font-size:20px;">
					<%
					//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
					/*
					FOR FURTURE MODIFIKASI BILA OPERATOR BOLEH MENGISI KRS ALL THSMS
					*/
					if(true) {	
					//if(!allowedAllThsms) {
						
						String keter_thsms = ""+targetThsms;
						String value_thsms = ""+targetThsms;
						%>
						<option value="<%=value_thsms %>" selected="selected"><%=keter_thsms%></option>
						<%
					}
					/*
					else {
						//String keter_thsms = ""+targetThsms;
						//String value_thsms = ""+targetThsms;
						//buat range thsms - 10
						String base_thsms = new String(targetThsms);
						for(int i=0;i<10;i++) {
							base_thsms = Tool.returnPrevThsmsGivenTpAntara(base_thsms);
						}
						
						for(int i=0;i<10;i++) {
							base_thsms = Tool.returnNextThsmsGivenTpAntara(base_thsms);
							%>
						<option value="<%=base_thsms %>" selected="selected"><%=base_thsms%></option>
							<%
						}
						
					}
					*/
					%>
				</select>
			</td>
		</tr>
    	
	<%
	String prevSms = "";
	boolean adaData=false; //cuma buat flag utk submit button
	String atPage="update Form Krs";
	int i=0;
/*

AKAN ADA MASALAH PADA SAAT RESET/PEMBATALAN PENGGABUNGAN KEALAS DIMANA
TRNLM INIT_CUID AKAN KE RESET PADA ID BERDASARKAN SHIFT, JADI BILA DALAM 1 SHIFT ADA > 1 KELAS YG DIBUKA MAKA AKAN 
	KE RESET KE KELAS BERDASARKAN SHIFT, SEHINGGA HARUSNYA DI CEK BERDASARKAN SHIFT DAN NO KELAS PARALELNYA.



*/

	if(vBlm!=null && vBlm.size()>0) {
		adaData=true;
		ListIterator li = vBlm.listIterator();
		if(li.hasNext()) {
			i++;
			String baris = (String)li.next();
			////System.out.println("barisan0="+baris);
			StringTokenizer st = new StringTokenizer(baris,",");
			String idkmk = st.nextToken();
			//////System.out.println("idkmkBlm="+idkmk);
			String kdkmk = st.nextToken();
			//////System.out.println("kdkmkBlm="+kdkmk);
			String nakmk = st.nextToken();
			
			//////System.out.println("nakmkBlm="+nakmk);
			String skstm = st.nextToken();
			//////System.out.println("skstmBlm="+skstm);
			String skspr = st.nextToken();
			//////System.out.println("sksprBlm="+skspr);
			String skslp = st.nextToken();
			//////System.out.println("skslpBlm="+skslp);
			int sksmk = (Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue());
			String semes = st.nextToken();
			//////System.out.println("semesBlm="+semes);
			prevSms=""+semes;
			
	
			boolean avail = false;
			String shift = "null";
			String norutKelasParalel = "null";
			String currStatus = "null";
			String npmdos = "null";
			String npmasdos = "null";
			String canceled = "null";
			String kodeKelas = "null";
			String kodeRuang = "null";
			String kodeGedung = "null";
			String kodeKampus = "null";
			String tknDayTime = "null";
			String nmmdos = "null";
			String nmmasdos = "null";
			String enrolled = "null";
			String maxEnrolled = "null";
			String minEnrolled= "null";
			String subNakmk= "null";
			String uniqueId= "null";
			String cuid_cp =  "null";
			String idkmk_cp =  "null";		
			String kode_gab_cp =  "null";				
			
			if(st.hasMoreTokens()) {
				
				//kalo masih ada token berarti ada kelas yg di offer
				avail = true;
				////System.out.println("has moro");
			}
			
					
			
		//out.println("<br/>"+i+"."+baris);
		%>
		
		<tr>
			<td style="background:#369;color:#fff;text-align:center;width:60px"><label><B>KODE</B> </label></td>
			<td style="background:#369;color:#fff;text-align:left;width:330px"><label><B>MATAKULIAH</B> </label></td>
			<td style="background:#369;color:#fff;text-align:left;width:10px"><label><B>SKS</B> </label></td>
			<td style="background:#369;color:#fff;text-align:center;width:300px" ><label><B>INFO KELAS</B> </label></td>
			
			<!--  
			td style="background:#369;color:#fff;text-align:center;" colspan="3"><label><B>SKS</B> </label></td>
			<td style="background:#369;color:#fff;text-align:center;width:10px"><label><B>&radic;</B> </label></td>
		</tr>
		<tr> 
			<td style="background:#369;color:#fff;text-align:center;width:65px"><label><B>SKS <br/>TM/PR/LP</B> </label></td>
			<td style="background:#369;color:#fff;text-align:center;width:65px"><label><B>SHIFT</B> </label></td>
			<td style="background:#369;color:#fff;text-align:center;width:65px"><label><B>LAPANGAN</B> </label></td
			-->	
		</tr>
		<tr>
			<td style="background:#9FADB4;color:#000;text-align:center;width:60px" colspan="4"><label><B>MATAKULIAH SEMESTER :
			<%
			if(semes==null || semes.equalsIgnoreCase("null")) {
				out.print("?? - semester tiap matakuliah belum diisi di kurikulum");
			}
			else {
				out.print(semes);
			}
			//////System.out.println("semes="+semes);
			%>
			</B> </label></td>
		</tr>
		<tr>
			<td style="color:#000;text-align:center;width:60px"><label><B><%=kdkmk %></B> </label></td>
			<td style="color:#000;text-align:left;width:330px"><label><B><%=nakmk.replace("tandaKoma",",") %></B> </label></td>
			<td style="color:#000;text-align:center;width:10px"><label><B><%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %></B> </label></td>
			<!--  td style="color:#000;text-align:center;width:65px"><label><B><%=skspr %></B> </label></td>
			<td style="color:#000;text-align:center;width:65px"><label><B><%=skslp %></B> </label></td -->
			<td>
			<%
			if(!avail) {
			%>
			<div style="font-size:0.8em;text-align:center">KELAS TIDAK DI TAWARKAN PADA SEMESTER INI</div>
			<%	
			}
			else {
				if((avail && !notThsmsKrs) || whiteList) { //!notThsmsKrs = berarti thsms krs
					boolean match = false;	
			%>
			<select name="kelasSelected" style="width:300px">
				<option value="null">--PILIH KELAS--</option>
				<%
					while(st.hasMoreTokens()) {
						shift = st.nextToken();
						//////System.out.println("shift="+shift);
						norutKelasParalel = st.nextToken();
						//////System.out.println("norutKelasParalel="+norutKelasParalel);
						currStatus = st.nextToken();
						//////System.out.println("currStatus="+currStatus);
						npmdos = st.nextToken();
						//////System.out.println("npmdos="+npmdos);
						npmasdos = st.nextToken();
						//////System.out.println("npmasdos="+npmasdos);
						canceled = st.nextToken();
						//////System.out.println("canceled="+canceled);
						kodeKelas = st.nextToken();
						//////System.out.println("kodeKelas="+kodeKelas);
						kodeRuang = st.nextToken();
						//////System.out.println("kodeRuang="+kodeRuang);
						kodeGedung = st.nextToken();
						//////System.out.println("kodeGedung="+kodeGedung);
						kodeKampus = st.nextToken();
						//////System.out.println("kodeKampus="+kodeKampus);
						tknDayTime = st.nextToken();
						//////System.out.println("tknDayTime="+tknDayTime);
						nmmdos = st.nextToken();
						//////System.out.println("nmmdos="+nmmdos);
						nmmasdos = st.nextToken();
						//////System.out.println("nmmasdos="+nmmasdos);
						enrolled = st.nextToken();
						//////System.out.println("enrolled="+enrolled);
						maxEnrolled = st.nextToken();
						//////System.out.println("maxEnrolled="+maxEnrolled);
						minEnrolled= st.nextToken();
						cuid_cp= st.nextToken();//tambahan variable
						idkmk_cp= st.nextToken();//tambahan variable
						//begini karna debugging aja
						 
						subNakmk= st.nextToken();
						kode_gab_cp = st.nextToken();
						uniqueId= cuid_cp;
						/*
						updated 9 April 2015
						
						if(st.hasMoreTokens()) {
							subNakmk= st.nextToken();
						}
						else {
							subNakmk="null";
						}
						*/
						//////System.out.println("minEnrolled="+minEnrolled);
					//////System.out.println("es teh");
;
					/*
					*filter disini bila ada : !kelas canceled
					*/
						if(kelasSesuaiShift.equalsIgnoreCase("true")) {
							////System.out.println("kelasSesuaiShift1="+kelasSesuaiShift);
							if(Checker.isStringNullOrEmpty(kode_gab_cp)) {
								if(shift.equalsIgnoreCase(v_shift)) {
									match = true;
						%>
						<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,<%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %>,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
						<%
								}
							}
							else {
								//jika kelas gabungan
								if(baris.contains(v_shift)) {
									////System.out.println("v_shift="+v_shift);
									////System.out.println("abrr="+baris);
									//shift mhs ini termasuk kedalam yg digabung
									//match = true;
									//get cuid kelas sesuai v_shift
									if(!shift.equalsIgnoreCase(v_shift)) {
										//shift kelas inti != v_shift (token peerttama)
										while(st.hasMoreTokens()) {
											String shift_tmp = st.nextToken();
											String norutKelasParalel_tmp = st.nextToken();
											String currStatus_tmp = st.nextToken();
											String npmdos_tmp = st.nextToken();
											String npmasdos_tmp = st.nextToken();
											String canceled_tmp = st.nextToken();
											String kodeKelas_tmp = st.nextToken();
											String kodeRuang_tmp = st.nextToken();
											String kodeGedung_tmp = st.nextToken();
											String kodeKampus_tmp = st.nextToken();
											//////System.out.println("kodeKampus="+kodeKampus);
											String tknDayTime_tmp = st.nextToken();
											String nmmdos_tmp = st.nextToken();
											String nmmasdos_tmp = st.nextToken();
											String enrolled_tmp = st.nextToken();
											String maxEnrolled_tmp = st.nextToken();
											String minEnrolled_tmp= st.nextToken();
											String cuid_cp_tmp = st.nextToken();
											String idkmk_cp_tmp = st.nextToken();
											
											
											if(st.hasMoreTokens()) {
												String subNakmk_tmp=st.nextToken();	
											}
											else {
												String subNakmk_tmp="null";
											}
											String kode_gab_cp_tmp = st.nextToken();
											if(shift_tmp.equalsIgnoreCase(v_shift)) {
												match = true;
												//System.out.println("-1- "+kdkmk+" "+cuid_cp+" "+cuid_cp_tmp);
												%>
						<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,<%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %>,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>,<%=cuid_cp_tmp%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
												<%
											
											}
										}	
									}
									else {
										match = true;
										//match pada token pertama
										//System.out.println("-2- "+kdkmk+" "+cuid_cp+" null");
										%>
						<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,<%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %>,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>,<%=cuid_cp%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
										<%	
										while(st.hasMoreTokens()) {
											st.nextToken(); //looping biar no more tokens
										}	
									}
								}
								else {
									//v_shift tidak termasuk = kelas tidak di offer thus  match = false
								}
							}
						}
						else {
							match=true;
							if(Checker.isStringNullOrEmpty(kode_gab_cp)) {
								%>
					<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,<%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %>,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
								<%
							}
							else {
								/*
								KELAS GABUNGAN
								KARENA VALUE DIATAS ADALAH TOKEN PERTAMA MAKA ITU KELAS INTI
								SEKARANG TINGGAL DICEK APA SHIFT MHS INI TERMASUK YG DIGABUNG, BILA TIDAK INIT CUIDDI TRNLMNYA KOSONG
								,
								*/
								//karena bebas shift jadi value di token pertama / kelas inti 
								//loop abisin token 
								//System.out.println("-3- "+kdkmk+" "+cuid_cp+" null");
								%>
					<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,<%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %>,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>,<%=cuid_cp%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
								<%
								while(st.hasMoreTokens()) {
									st.nextToken();
								}
							}
						}
					}
					if(!match) {
					%>
					<option value="null" selected>KELAS TIDAK DI TAWARKAN PADA SEMESTER INI</option>
					<%
					}
				%>
				
			</select>
			
			<%
				}
			}
			%>
			</td>
		</tr>
		
		<%
			if(li.hasNext()) {
				while(li.hasNext()) {
					i++;
					baris = (String)li.next();
					////System.out.println("barisan1="+baris);
					//////System.out.println(i+".354."+baris);
					st = new StringTokenizer(baris,",");
					idkmk = st.nextToken();
					//////System.out.println("idkmk="+idkmk);
					kdkmk = st.nextToken();
					//////System.out.println("kdkmk="+kdkmk);
					nakmk = st.nextToken();
					//////System.out.println("nakmk="+nakmk);
					skstm = st.nextToken();
					//////System.out.println("skstm="+skstm);
					skspr = st.nextToken();
					//////System.out.println("skspr="+skspr);
					skslp = st.nextToken();
					//////System.out.println("skslp="+skslp);
					sksmk = (Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue());
					semes = st.nextToken();
					//////System.out.println("semes="+semes);
					
				
					avail = false;
					shift = "null";
					norutKelasParalel = "null";
					currStatus = "null";
					npmdos = "null";
					npmasdos = "null";
					canceled = "null";
					kodeKelas = "null";
					kodeRuang = "null";
					kodeGedung = "null";
					kodeKampus = "null";
					tknDayTime = "null";
					nmmdos = "null";
					nmmasdos = "null";
					enrolled = "null";
					maxEnrolled = "null";
					minEnrolled= "null";
					cuid_cp = "null";
					idkmk_cp = "null";
					subNakmk="null";
					uniqueId="null";
					kode_gab_cp = "null";
					if(st.hasMoreTokens()) {
						
						avail = true;
						
					}
					////System.out.println("avail="+avail);
					//////System.out.println("semes vs prevSms = "+semes+","+prevSms);
					if(semes.equalsIgnoreCase("null")) {
						%>
						<tr>
							<td style="color:#000;text-align:center;width:60px"><label><B><%=kdkmk %></B> </label></td>
							<td style="color:#000;text-align:left;width:330px"><label><B><%=nakmk.replace("tandaKoma",",") %></B> </label></td>
							<td style="color:#000;text-align:center;width:10px"><label><B><%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %></B> </label></td>
							<!--  td style="color:#000;text-align:center;width:65px"><label><B><%=skspr %></B> </label></td>
							<td style="color:#000;text-align:center;width:65px"><label><B><%=skslp %></B> </label></td -->
							<td>
			
							<%
							if(!avail) {
								%>
								<div style="font-size:0.8em;text-align:center">KELAS TIDAK DI TAWARKAN PADA SEMESTER INI</div>
								<!-- input type="checkbox" name="makul" value="" / -->
								<%	
								}
								else {
									if((avail && !notThsmsKrs) || whiteList) {
										boolean match = false;
								%>
								<select name="kelasSelected" style="width:300px">
									<option value="null">--PILIH KELAS--</option>
									<%
									while(st.hasMoreTokens()) {
										shift = st.nextToken();
										norutKelasParalel = st.nextToken();
										currStatus = st.nextToken();
										npmdos = st.nextToken();
										npmasdos = st.nextToken();
										canceled = st.nextToken();
										kodeKelas = st.nextToken();
										kodeRuang = st.nextToken();
										kodeGedung = st.nextToken();
										kodeKampus = st.nextToken();
										//////System.out.println("kodeKampus="+kodeKampus);
										tknDayTime = st.nextToken();
										nmmdos = st.nextToken();
										nmmasdos = st.nextToken();
										enrolled = st.nextToken();
										maxEnrolled = st.nextToken();
										minEnrolled= st.nextToken();
										cuid_cp=st.nextToken();
										idkmk_cp=st.nextToken();
										subNakmk=st.nextToken();	
										kode_gab_cp = st.nextToken();
										uniqueId=cuid_cp;	
										
										if(st.hasMoreTokens()) {
											subNakmk=st.nextToken();	
										}
										else {
											subNakmk="null";
										}
									
										/*
										*filter disini bila ada : !kelas canceled
										*/
										////System.out.println("kelasSesuaiShift2="+kelasSesuaiShift);
										if(kelasSesuaiShift.equalsIgnoreCase("true")) {
											if(Checker.isStringNullOrEmpty(kode_gab_cp)) {
												if(shift.equalsIgnoreCase(v_shift)) {
													match = true;
											%>
											<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,<%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %>,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
											<%
												}
											}
											else {
												//jika kelas gabungan
												if(baris.contains(v_shift)) {
													////System.out.println("v_shift="+v_shift);
													////System.out.println("abrr="+baris);
													//shift mhs ini termasuk kedalam yg digabung
													//match = true;
													//get cuid kelas sesuai v_shift
													if(!shift.equalsIgnoreCase(v_shift)) {
														//shift kelas inti != v_shift
														while(st.hasMoreTokens()) {
															String shift_tmp = st.nextToken();
															String norutKelasParalel_tmp = st.nextToken();
															String currStatus_tmp = st.nextToken();
															String npmdos_tmp = st.nextToken();
															String npmasdos_tmp = st.nextToken();
															String canceled_tmp = st.nextToken();
															String kodeKelas_tmp = st.nextToken();
															String kodeRuang_tmp = st.nextToken();
															String kodeGedung_tmp = st.nextToken();
															String kodeKampus_tmp = st.nextToken();
															//////System.out.println("kodeKampus="+kodeKampus);
															String tknDayTime_tmp = st.nextToken();
															String nmmdos_tmp = st.nextToken();
															String nmmasdos_tmp = st.nextToken();
															String enrolled_tmp = st.nextToken();
															String maxEnrolled_tmp = st.nextToken();
															String minEnrolled_tmp= st.nextToken();
															String cuid_cp_tmp = st.nextToken();
															String idkmk_cp_tmp = st.nextToken();
															if(st.hasMoreTokens()) {
																String subNakmk_tmp=st.nextToken();	
															}
															else {
																String subNakmk_tmp="null";
															}
															String kode_gab_cp_tmp = st.nextToken();
															if(shift_tmp.equalsIgnoreCase(v_shift)) {
																match = true;
																//System.out.println("-4- "+kdkmk+" "+cuid_cp+" "+cuid_cp_tmp);
																%>
										<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,<%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %>,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>,<%=cuid_cp_tmp%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
																<%
															
															}
														}	
													}
													else {
														//match at token pertama
														match = true;	
														//System.out.println("-5- "+kdkmk+" "+cuid_cp+" null");
														%>
										<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,<%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %>,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>,<%=cuid_cp%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
														<%	
														while(st.hasMoreTokens()) {
															st.nextToken(); //loop biar no more token
														}	
													}
												}
												else {
													//v_shift tidak termasuk = kelas tidak di offer thus  match = false
												}
											}
											
										}
										else {
											match=true;
											if(Checker.isStringNullOrEmpty(kode_gab_cp)) {
										%>
											<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,<%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %>,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
										<%
											}
											else {
												/*
												KELAS GABUNGAN
												KARENA VALUE DIATAS ADALAH TOKEN PERTAMA MAKA ITU KELAS INTI
												SEKARANG TINGGAL DICEK APA SHIFT MHS INI TERMASUK YG DIGABUNG, BILA TIDAK INIT CUIDDI TRNLMNYA KOSONG
												,
												*/
												//karena bebas shift jadi value di token pertama / kelas inti 
												//loop abisin token 
												//System.out.println("-6- "+kdkmk+" "+cuid_cp+" null");
												%>
									<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,<%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %>,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>,<%=cuid_cp%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
												<%
												while(st.hasMoreTokens()) {
													st.nextToken(); //loop biar nomore token
												}
											}
										}
									}
									if(!match) {
										%>
										<option value="null" selected>KELAS TIDAK DI TAWARKAN PADA SEMESTER INI</option>
										<%
									}
									%>
									
								</select>
			
								<%
									}
								}
						%>
							</td>
						</tr>
								<%	
					}
					else {
						//////System.out.println("508prevSms="+prevSms);
						//////System.out.println("508semes="+semes);
						if((prevSms!=null && !prevSms.equalsIgnoreCase("null") && semes!=null && !semes.equalsIgnoreCase("null")) && Integer.valueOf(Converter.prepNumberString(prevSms)).intValue()==Integer.valueOf(Converter.prepNumberString(semes)).intValue()) {
							
					%>
		<tr>
			<td style="color:#000;text-align:center;width:60px"><label><B><%=kdkmk %></B> </label></td>
			<td style="color:#000;text-align:left;width:330px"><label><B><%=nakmk.replace("tandaKoma",",") %></B> </label></td>
			<td style="color:#000;text-align:center;width:10px"><label><B><%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %></B> </label></td>
			<!--  td style="color:#000;text-align:center;width:65px"><label><B><%=skspr %></B> </label></td>
			<td style="color:#000;text-align:center;width:65px"><label><B><%=skslp %></B> </label></td -->
			<td>
			
			<%
			if(!avail) {
				%>
				<div style="font-size:0.8em;text-align:center">KELAS TIDAK DI TAWARKAN PADA SEMESTER INI</div>
				<!-- input type="checkbox" name="makul" value="" / -->
				<%	
				}
				else {
					if((avail && !notThsmsKrs) || whiteList) {
						boolean match = false;
				%>
				<select name="kelasSelected" style="width:300px">
					<option value="null">--PILIH KELAS--</option>
					<%
					while(st.hasMoreTokens()) {
						shift = st.nextToken();
						norutKelasParalel = st.nextToken();
						currStatus = st.nextToken();
						npmdos = st.nextToken();
						npmasdos = st.nextToken();
						canceled = st.nextToken();
						kodeKelas = st.nextToken();
						kodeRuang = st.nextToken();
						kodeGedung = st.nextToken();
						kodeKampus = st.nextToken();
						//////System.out.println("kodeKampus="+kodeKampus);
						tknDayTime = st.nextToken();
						nmmdos = st.nextToken();
						nmmasdos = st.nextToken();
						enrolled = st.nextToken();
						maxEnrolled = st.nextToken();
						minEnrolled= st.nextToken();
						cuid_cp=st.nextToken();
						idkmk_cp=st.nextToken();
						subNakmk=st.nextToken();	
						kode_gab_cp = st.nextToken();
						uniqueId=cuid_cp;	
						/*begini karna debugging aja
						if(st.hasMoreTokens()) {
							subNakmk=st.nextToken();	
						}
						else {
							subNakmk="null";
						}
						*/
						/*
						*filter disini bila ada : !kelas canceled
						*/
						////System.out.println("kelasSesuaiShift3="+kelasSesuaiShift);
						if(kelasSesuaiShift.equalsIgnoreCase("true")) {
							if(Checker.isStringNullOrEmpty(kode_gab_cp)) {
								if(shift.equalsIgnoreCase(v_shift)) {
									match = true;
							%>
						<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,<%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %>,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
							<%
								}	
							}
							else {
								//jika kelas gabungan
								if(baris.contains(v_shift)) {
									////System.out.println("v_shift="+v_shift);
									////System.out.println("abrr="+baris);
									//shift mhs ini termasuk kedalam yg digabung
									//match = true;
									//get cuid kelas sesuai v_shift
									if(!shift.equalsIgnoreCase(v_shift)) {
										//shift kelas inti != v_shift
										while(st.hasMoreTokens()) {
											String shift_tmp = st.nextToken();
											String norutKelasParalel_tmp = st.nextToken();
											String currStatus_tmp = st.nextToken();
											String npmdos_tmp = st.nextToken();
											String npmasdos_tmp = st.nextToken();
											String canceled_tmp = st.nextToken();
											String kodeKelas_tmp = st.nextToken();
											String kodeRuang_tmp = st.nextToken();
											String kodeGedung_tmp = st.nextToken();
											String kodeKampus_tmp = st.nextToken();
											//////System.out.println("kodeKampus="+kodeKampus);
											String tknDayTime_tmp = st.nextToken();
											String nmmdos_tmp = st.nextToken();
											String nmmasdos_tmp = st.nextToken();
											String enrolled_tmp = st.nextToken();
											String maxEnrolled_tmp = st.nextToken();
											String minEnrolled_tmp= st.nextToken();
											String cuid_cp_tmp = st.nextToken();
											String idkmk_cp_tmp = st.nextToken();
											//begini karna debugging aja
											
											if(st.hasMoreTokens()) {
												String subNakmk_tmp=st.nextToken();	
											}
											else {
												String subNakmk_tmp="null";
											}
											String kode_gab_cp_tmp = st.nextToken();
											if(shift_tmp.equalsIgnoreCase(v_shift)) {
												match = true;
												//System.out.println("-7- "+kdkmk+" "+cuid_cp+" "+cuid_cp_tmp);
												%>
						<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,<%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %>,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>,<%=cuid_cp_tmp%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
												<%
											
											}
										}	
									}
									else {
										match = true;
										//match at token 1
										//System.out.println("-8- "+kdkmk+" "+cuid_cp+" null");
										%>
						<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,<%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %>,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>,<%=cuid_cp%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
										<%	
										while(st.hasMoreTokens()) {
											st.nextToken();
										}	
									}
								}
								else {
									//v_shift tidak termasuk = kelas tidak di offer thus  match = false
								}
							}
						}
						else {
							match=true;
							if(Checker.isStringNullOrEmpty(kode_gab_cp)) {
								%>
								<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,<%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %>,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
								<%	
							}
							else {
								/*
								KELAS GABUNGAN
								KARENA VALUE DIATAS ADALAH TOKEN PERTAMA MAKA ITU KELAS INTI
								SEKARANG TINGGAL DICEK APA SHIFT MHS INI TERMASUK YG DIGABUNG, BILA TIDAK INIT CUIDDI TRNLMNYA KOSONG
								,
								*/
								//karena bebas shift jadi value di token pertama / kelas inti 
								//loop abisin token 
								//System.out.println("-9- "+kdkmk+" "+cuid_cp+" null");
								%>
					<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,<%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %>,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>,<%=cuid_cp%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
								<%
								while(st.hasMoreTokens()) {
									st.nextToken();
								}
							}
						}
					}
					if(!match) {
						%>
						<option value="null" selected>KELAS TIDAK DI TAWARKAN PADA SEMESTER INI</option>
						<%
					}
					%>
					
				</select>
			
				<%
					}
				}
			%>
			</td>
		</tr>
				<%		
						}
						else {
						//ganti makul semester
							prevSms=""+semes;
					%>
		<tr>
			<td style="background:#9FADB4;color:#000;text-align:center;width:60px" colspan="4"><label><B>MATAKULIAH SEMESTER :<%=semes %></B> </label></td>
		</tr>
		<tr>
			<td style="color:#000;text-align:center;width:60px"><label><B><%=kdkmk %></B> </label></td>
			<td style="color:#000;text-align:left;width:330px"><label><B><%=nakmk.replace("tandaKoma",",") %></B> </label></td>
			<td style="color:#000;text-align:center;width:10px"><label><B><%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %></B> </label></td>
			<!--  td style="color:#000;text-align:center;width:65px"><label><B><%=skspr %></B> </label></td>
			<td style="color:#000;text-align:center;width:65px"><label><B><%=skslp %></B> </label></td -->
			<td>
			<%
			if(!avail) {
				%>
				<div style="font-size:0.8em;text-align:center">KELAS TIDAK DI TAWARKAN PADA SEMESTER INI</div>
				
				<!-- input type="checkbox" name="makul" value="" / -->
				<%	
				}
				else {
					if((avail && !notThsmsKrs) || whiteList) {
						boolean match = false;
				%>
				<select name="kelasSelected" style="width:300px">
					<option value="null">--PILIH KELAS--</option>
					<%
					while(st.hasMoreTokens()) {
						shift = st.nextToken();
						norutKelasParalel = st.nextToken();
						currStatus = st.nextToken();
						npmdos = st.nextToken();
						npmasdos = st.nextToken();
						canceled = st.nextToken();
						kodeKelas = st.nextToken();
						kodeRuang = st.nextToken();
						kodeGedung = st.nextToken();
						kodeKampus = st.nextToken();
						//////System.out.println("kodeKampus="+kodeKampus);
						tknDayTime = st.nextToken();
						nmmdos = st.nextToken();
						nmmasdos = st.nextToken();
						enrolled = st.nextToken();
						maxEnrolled = st.nextToken();
						minEnrolled= st.nextToken();
						cuid_cp=st.nextToken();
						idkmk_cp=st.nextToken();
						subNakmk=st.nextToken();	
						kode_gab_cp = st.nextToken();
						uniqueId=cuid_cp;	
						
						/*
						*filter disini bila ada : !kelas canceled
						*/
						////System.out.println("kelasSesuaiShift4="+kelasSesuaiShift);
						if(kelasSesuaiShift.equalsIgnoreCase("true")) {
							if(Checker.isStringNullOrEmpty(kode_gab_cp)) {
								if(shift.equalsIgnoreCase(v_shift)) {
									match = true;
							%>
						<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,<%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %>,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
							<%
								}	
							}
							else {
								//jika kelas gabungan
								if(baris.contains(v_shift)) {
									////System.out.println("v_shift="+v_shift);
									////System.out.println("abrr="+baris);
									//shift mhs ini termasuk kedalam yg digabung
									//match = true;
									//get cuid kelas sesuai v_shift
									if(!shift.equalsIgnoreCase(v_shift)) {
										//shift kelas inti != v_shift
										while(st.hasMoreTokens()) {
											String shift_tmp = st.nextToken();
											String norutKelasParalel_tmp = st.nextToken();
											String currStatus_tmp = st.nextToken();
											String npmdos_tmp = st.nextToken();
											String npmasdos_tmp = st.nextToken();
											String canceled_tmp = st.nextToken();
											String kodeKelas_tmp = st.nextToken();
											String kodeRuang_tmp = st.nextToken();
											String kodeGedung_tmp = st.nextToken();
											String kodeKampus_tmp = st.nextToken();
											//////System.out.println("kodeKampus="+kodeKampus);
											String tknDayTime_tmp = st.nextToken();
											String nmmdos_tmp = st.nextToken();
											String nmmasdos_tmp = st.nextToken();
											String enrolled_tmp = st.nextToken();
											String maxEnrolled_tmp = st.nextToken();
											String minEnrolled_tmp= st.nextToken();
											String cuid_cp_tmp = st.nextToken();
											String idkmk_cp_tmp = st.nextToken();
											//begini karna debugging aja
											
											if(st.hasMoreTokens()) {
												String subNakmk_tmp=st.nextToken();	
											}
											else {
												String subNakmk_tmp="null";
											}
											String kode_gab_cp_tmp = st.nextToken();
											if(shift_tmp.equalsIgnoreCase(v_shift)) {
												match = true;
												%>
						<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,<%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %>,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>,<%=cuid_cp_tmp%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
												<%
											
											}
										}	
									}
									else {
										match = true;
										//match at token 1
										%>
						<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,<%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %>,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>,<%=cuid_cp%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
										<%	
										while(st.hasMoreTokens()) {
											st.nextToken();
										}	
									}
								}
								else {
									//v_shift tidak termasuk = kelas tidak di offer thus  match = false
								}
							}
						}
						else {
							match=true;
							if(Checker.isStringNullOrEmpty(kode_gab_cp)) {
								%>
								<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,<%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %>,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
								<%	
							}
							else {
								/*
								KELAS GABUNGAN
								KARENA VALUE DIATAS ADALAH TOKEN PERTAMA MAKA ITU KELAS INTI
								SEKARANG TINGGAL DICEK APA SHIFT MHS INI TERMASUK YG DIGABUNG, BILA TIDAK INIT CUIDDI TRNLMNYA KOSONG
								,
								*/
								//karena bebas shift jadi value di token pertama / kelas inti 
								//loop abisin token 
								%>
					<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,<%=(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %>,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>,<%=cuid_cp%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
								<%
								while(st.hasMoreTokens()) {
									st.nextToken();
								}
							}
						}
					}
					if(!match) {
						%>
						<option value="null" selected>KELAS TIDAK DI TAWARKAN PADA SEMESTER INI</option>
						<%
					}
					%>
					
				</select>
				
				<%
					}
				}
			%>
			</td>
		</tr>
					<%	
						}
					}	
					if(!li.hasNext()) {
					%>
		</table>			
					<%
					}
				}	
			}
			else {
			%>
			</table> <br>
			<%
			}
		}
	}
		
	////System.out.println("sampai kesini 2");
%>
<!--
FOR FUTURE USED UNTUK KELAS SETARA 
 -->

<!--   include file="setara.jsp" % -->


<%	
			
	////System.out.println("sampai kesini 3");
	if(vSdh!=null && vSdh.size()>0) {	
		////System.out.println("sampai kesini 4");
		adaData=true;
		ListIterator li1 = vSdh.listIterator();
	//mo di sort berdasarkan nilai terrendah
		Vector v2 = new Vector();
		ListIterator li2 = v2.listIterator();
	
		while(li1.hasNext()) {
			//29,20091,KU 1033,PENDIDIKAN KEWARGANEGARAAN,B,3.0
		
			String baris = (String)li1.next();
			////System.out.println("barisan3="+baris);
			StringTokenizer st = new StringTokenizer(baris,",");
			String idkmk=st.nextToken();
			String thsms=st.nextToken();
			String kdkmk=st.nextToken();
			String nakmk=st.nextToken();
			String nlakh=st.nextToken();
			String bobot=st.nextToken();
			String cuidh=st.nextToken();
			String cuid_inith=st.nextToken();

			boolean avail = false;
			String shift = "null";
			String norutKelasParalel = "null";
			String currStatus = "null";
			String npmdos = "null";
			String npmasdos = "null";
			String canceled = "null";
			String kodeKelas = "null";
			String kodeRuang = "null";
			String kodeGedung = "null";
			String kodeKampus = "null";
			String tknDayTime = "null";
			String nmmdos = "null";
			String nmmasdos = "null";
			String enrolled = "null";
			String maxEnrolled = "null";
			String minEnrolled= "null";
			String subNakmk = "null";
			String uniqueId = "null";
			String kode_gab_cp = "null";
			if(st.hasMoreTokens()) {
				//kasih 1 & 2 krn mo di sort biar yg avail tampil duluan
				avail = true;
				li2.add("1,"+baris);
			}
			else {
				li2.add("2,"+baris);
			}	
			
			//li2.add(bobot+","+nlakh+","+nakmk+","+kdkmk+","+thsms+","+idkmk+","+noKlsPll+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+max_enrolled+","+min_enrolled+","+avail);
		}
		Collections.sort(v2);
		li2 = v2.listIterator();
		if(li2.hasNext()){
		%>	
	<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:700px">	
		<tr>
			<td style="background:#369;color:#fff;text-align:center;font-size:25px" colspan="6"><label><B>MATAKULIAH YANG SEDANG DAN SUDAH DIAMBIL</B></label></td>
		</tr>
		<tr>
			<td style="background:#9FADB4;color:#000;text-align:center;width:60px"><label><B>KODE</B> </label></td>
			<td style="background:#9FADB4;color:#000;text-align:left;width:330px"><label><B>MATAKULIAH</B> </label></td>
			<td style="background:#9FADB4;color:#000;text-align:center;width:10px"><label><B>NILAI</B> </label></td>
			<td style="background:#9FADB4;color:#000;text-align:center;width:10px"><label><B>BOBOT</B> </label></td>
			<td style="background:#9FADB4;color:#000;text-align:center;width:10px"><label><B>THSMS</B> </label></td>
			<td style="background:#9FADB4;color:#000;text-align:center;width:280px"><label><B>PILIHAN KELAS</B> </label></td>
		</tr>
	<%	
			String baris = (String)li2.next();
			////System.out.println("bar="+baris);
			StringTokenizer st = new StringTokenizer(baris,",");
			String norut=st.nextToken();//1 or 2 if 1 maka kelas avail di sms ini
			String idkmk=st.nextToken();
			String thsms=st.nextToken();
			String kdkmk=st.nextToken();
			String nakmk=st.nextToken();
			String nlakh=st.nextToken();
			String bobot=st.nextToken();
			String cuidh=st.nextToken();
			String cuid_inith=st.nextToken();
			
			boolean avail = false;
			String shift = "null";
			String norutKelasParalel = "null";
			String currStatus = "null";
			String npmdos = "null";
			String npmasdos = "null";
			String canceled = "null";
			String kodeKelas = "null";
			String kodeRuang = "null";
			String kodeGedung = "null";
			String kodeKampus = "null";
			String tknDayTime = "null";
			String nmmdos = "null";
			String nmmasdos = "null";
			String enrolled = "null";
			String maxEnrolled = "null";
			String minEnrolled= "null";
			String subNakmk = "null";
			String uniqueId= "null";
			String cuid_cp = "null";
			String idkmk_cp = "null";
			String kode_gab_cp = "null";
			if(norut.equalsIgnoreCase("1")) {
				//kasih 1 & 2 krn mo di sort biar yg avail tampil duluan
				avail = true;
				//li2.add("1,"+baris);
			}
			%>
		<tr>
			<td style="color:#000;text-align:center;width:60px"><label><B><%=kdkmk %></B> </label></td>
			<td style="color:#000;text-align:left;width:330px"><label><B><%=nakmk.replace("tandaKoma",",") %></B> </label></td>
			<td style="color:#000;text-align:center;width:10px"><label><B><%=nlakh %></B> </label></td>
			<td style="color:#000;text-align:center;width:10px"><label><B><%=bobot %></B> </label></td>
			<td style="color:#000;text-align:center;width:10px"><label><B><%=thsms %></B> </label></td>
			<td>
			<%
			if(!avail) {
				%>
				<div style="font-size:0.8em;text-align:center">KELAS TIDAK DI TAWARKAN PADA SEMESTER INI</div>
				
				<!-- input type="checkbox" name="makul" value="" / -->
				<%	
			}
			else {
				if((avail && !notThsmsKrs) || whiteList) {
					boolean match = false;
				%>
				<select name="kelasSelected" style="width:300px">
					<option value="null">--PILIH KELAS--</option>
					<%
					while(st.hasMoreTokens()) {
						shift = st.nextToken();
						norutKelasParalel = st.nextToken();
						currStatus = st.nextToken();
						npmdos = st.nextToken();
						npmasdos = st.nextToken();
						canceled = st.nextToken();
						kodeKelas = st.nextToken();
						kodeRuang = st.nextToken();
						kodeGedung = st.nextToken();
						kodeKampus = st.nextToken();
						//////System.out.println("kodeKampus="+kodeKampus);
						tknDayTime = st.nextToken();
						nmmdos = st.nextToken();
						nmmasdos = st.nextToken();
						enrolled = st.nextToken();
						maxEnrolled = st.nextToken();
						minEnrolled= st.nextToken();
						cuid_cp = st.nextToken();
						idkmk_cp = st.nextToken();
						//begini karna debugging aja
						
						if(st.hasMoreTokens()) {
							subNakmk=st.nextToken();	
						}
						else {
							subNakmk="null";
						}
						kode_gab_cp = st.nextToken();
						uniqueId=cuid_cp;	;
						/*
						*filter disini bila ada : !kelas canceled
						*/
						if(kelasSesuaiShift.equalsIgnoreCase("true")) {
							if(Checker.isStringNullOrEmpty(kode_gab_cp)) {
								if(shift.equalsIgnoreCase(v_shift)) {
									match = true;
							%>
								<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,null,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
							<%
								}
							}
							else {
								//jika kelas gabungan
								/*
								KARENA VALUE DIATAS ADALAH TOKEN PERTAMA MAKA ITU KELAS INTI
								SEKARANG TINGGAL DICEK APA SHIFT MHS INI TERMASUK YG DIGABUNG, KALAU TIDAK BERARTI TIDAK DIBUKA
								, KARENA INI JALUR BILA HARUS SESUAI SHIFT
								*/
								if(baris.contains(v_shift)) {
									////System.out.println("v_shift="+v_shift);
									////System.out.println("abrr="+baris);
									//shift mhs ini termasuk kedalam yg digabung
									//match = true;
									//get cuid kelas sesuai v_shift
									if(!shift.equalsIgnoreCase(v_shift)) {
										//shift kelas inti != v_shift
										while(st.hasMoreTokens()) {
											String shift_tmp = st.nextToken();
											String norutKelasParalel_tmp = st.nextToken();
											String currStatus_tmp = st.nextToken();
											String npmdos_tmp = st.nextToken();
											String npmasdos_tmp = st.nextToken();
											String canceled_tmp = st.nextToken();
											String kodeKelas_tmp = st.nextToken();
											String kodeRuang_tmp = st.nextToken();
											String kodeGedung_tmp = st.nextToken();
											String kodeKampus_tmp = st.nextToken();
											//////System.out.println("kodeKampus="+kodeKampus);
											String tknDayTime_tmp = st.nextToken();
											String nmmdos_tmp = st.nextToken();
											String nmmasdos_tmp = st.nextToken();
											String enrolled_tmp = st.nextToken();
											String maxEnrolled_tmp = st.nextToken();
											String minEnrolled_tmp= st.nextToken();
											String cuid_cp_tmp = st.nextToken();
											String idkmk_cp_tmp = st.nextToken();
											//begini karna debugging aja
											
											if(st.hasMoreTokens()) {
												String subNakmk_tmp=st.nextToken();	
											}
											else {
												String subNakmk_tmp="null";
											}
											String kode_gab_cp_tmp = st.nextToken();
											if(shift_tmp.equalsIgnoreCase(v_shift)) {
												match = true;
												//cuid_inith MENGGANTIKAN cuid_cp_tmp
												if(Checker.isStringNullOrEmpty(cuid_inith)) {
													cuid_inith = new String(cuid_cp_tmp);
												}
												%>
												<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,null,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>,<%=cuid_inith%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
												<%
											}
										}	
									}
									else {
										match = true;
										//match at token 1
										if(Checker.isStringNullOrEmpty(cuid_inith)) {
											cuid_inith = new String(cuid_cp);
										}
										%>
										<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,null,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>,<%=cuid_inith%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
										<%
										while(st.hasMoreTokens()) {
											st.nextToken();
										}	
									}
									
								}
								else {
									//tidak ada shift yg sesuai, karena harus sesuai dengan shift maka no class offer
								}
							}
						}
						else {
							match=true;
							if(Checker.isStringNullOrEmpty(kode_gab_cp)) {
							%>
							<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,null,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
							<%
							}
							else {
								/*
								KELAS GABUNGAN
								KARENA VALUE DIATAS ADALAH TOKEN PERTAMA MAKA ITU KELAS INTI
								SEKARANG TINGGAL DICEK APA SHIFT MHS INI TERMASUK YG DIGABUNG, BILA TIDAK INIT CUIDDI TRNLMNYA KOSONG
								,
								*/
								//karena bebas shift jadi value di token pertama / kelas inti 
								//loop abisin token 
								if(Checker.isStringNullOrEmpty(cuid_inith)) {
									cuid_inith = new String(cuid_cp);
								}
								%>
					<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,null,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>,<%=cuid_inith%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
								<%
								while(st.hasMoreTokens()) {
									st.nextToken();
								}
							}
						}
					}
					if(!match) {
						%>
						<option value="null" selected>KELAS TIDAK DI TAWARKAN PADA SEMESTER INI</option>
						<%
					}
					%>
					
				</select>
				
				<%
					}
				}
			%>
			</td>
		</tr>		
		<% 	
			////System.out.println("7.");
			if(li2.hasNext()) {
				
				while(li2.hasNext()) {
					baris = (String)li2.next();
					////System.out.println("bar7.="+baris);
					st = new StringTokenizer(baris,",");
					norut=st.nextToken();//1 or 2 if 1 maka kelas avail di sms ini
					idkmk=st.nextToken();
					thsms=st.nextToken();
					kdkmk=st.nextToken();
					nakmk=st.nextToken();
					nlakh=st.nextToken();
					bobot=st.nextToken();
					cuidh=st.nextToken();
					cuid_inith=st.nextToken();
					
					avail = false;
					shift = "null";
					norutKelasParalel = "null";
					currStatus = "null";
					npmdos = "null";
					npmasdos = "null";
					canceled = "null";
					kodeKelas = "null";
					kodeRuang = "null";
					kodeGedung = "null";
					kodeKampus = "null";
					tknDayTime = "null";
					nmmdos = "null";
					nmmasdos = "null";
					enrolled = "null";
					maxEnrolled = "null";
					minEnrolled= "null";
					subNakmk= "null";
					uniqueId= "null";
					idkmk_cp = "null";
					cuid_cp = "null";
					kode_gab_cp = "null";
					if(norut.equalsIgnoreCase("1")) {
						//kasih 1 & 2 krn mo di sort biar yg avail tampil duluan
						avail = true;
						//li2.add("1,"+baris);
					}
		%>
		<tr>
			<td style="color:#000;text-align:center;width:60px"><label><B><%=kdkmk %></B> </label></td>
			<td style="color:#000;text-align:left;width:330px"><label><B><%=nakmk.replace("tandaKoma",",") %></B> </label></td>
			<td style="color:#000;text-align:center;width:10px"><label><B><%=nlakh %></B> </label></td>
			<td style="color:#000;text-align:center;width:10px"><label><B><%=bobot %></B> </label></td>
			<td style="color:#000;text-align:center;width:10px"><label><B><%=thsms %></B> </label></td>
			<td>
			<%
			if(!avail) {
				%>
				<div style="font-size:0.8em;text-align:center">KELAS TIDAK DI TAWARKAN PADA SEMESTER INI</div>
				
				<!-- input type="checkbox" name="makul" value="" / -->
				<%	
				}
				else {
					if((avail && !notThsmsKrs) || whiteList) {
						boolean match = false;
						
				%>
				<select name="kelasSelected" style="width:300px">
					<option value="null">--PILIH KELAS--</option>
					<%
					while(st.hasMoreTokens()) {
						shift = st.nextToken();
						norutKelasParalel = st.nextToken();
						currStatus = st.nextToken();
						npmdos = st.nextToken();
						npmasdos = st.nextToken();
						canceled = st.nextToken();
						kodeKelas = st.nextToken();
						kodeRuang = st.nextToken();
						kodeGedung = st.nextToken();
						kodeKampus = st.nextToken();
						//////System.out.println("kodeKampus");
						tknDayTime = st.nextToken();
						nmmdos = st.nextToken();
						nmmasdos = st.nextToken();
						enrolled = st.nextToken();
						maxEnrolled = st.nextToken();
						minEnrolled= st.nextToken();
						cuid_cp = st.nextToken();
						idkmk_cp = st.nextToken();
						//begini karna debugging aja
						
						if(st.hasMoreTokens()) {
							subNakmk=st.nextToken();	
						}
						else {
							subNakmk="null";
						}
						kode_gab_cp = st.nextToken();
						uniqueId=cuid_cp;	;
						
						/*
						*filter disini bila ada : !kelas canceled
						*/
						if(kelasSesuaiShift.equalsIgnoreCase("true")) {
							if(Checker.isStringNullOrEmpty(kode_gab_cp)) {
								if(shift.equalsIgnoreCase(v_shift)) {
									match = true;
							%>
								<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,null,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
							<%
								}
							}
							else {
								//jika kelas gabungan
								//jika kelas gabungan
								/*
								KARENA VALUE DIATAS ADALAH TOKEN PERTAMA MAKA ITU KELAS INTI
								SEKARANG TINGGAL DICEK APA SHIFT MHS INI TERMASUK YG DIGABUNG, KALAU TIDAK BERARTI TIDAK DIBUKA
								, KARENA INI JALUR BILA HARUS SESUAI SHIFT
								*/
								if(baris.contains(v_shift)) {
									////System.out.println("v_shift="+v_shift);
									////System.out.println("abrr="+baris);
									//shift mhs ini termasuk kedalam yg digabung
									//match = true;
									//get cuid kelas sesuai v_shift
									if(!shift.equalsIgnoreCase(v_shift)) {
										//shift kelas inti != v_shift
										while(st.hasMoreTokens()) {
											String shift_tmp = st.nextToken();
											String norutKelasParalel_tmp = st.nextToken();
											String currStatus_tmp = st.nextToken();
											String npmdos_tmp = st.nextToken();
											String npmasdos_tmp = st.nextToken();
											String canceled_tmp = st.nextToken();
											String kodeKelas_tmp = st.nextToken();
											String kodeRuang_tmp = st.nextToken();
											String kodeGedung_tmp = st.nextToken();
											String kodeKampus_tmp = st.nextToken();
											//////System.out.println("kodeKampus="+kodeKampus);
											String tknDayTime_tmp = st.nextToken();
											String nmmdos_tmp = st.nextToken();
											String nmmasdos_tmp = st.nextToken();
											String enrolled_tmp = st.nextToken();
											String maxEnrolled_tmp = st.nextToken();
											String minEnrolled_tmp= st.nextToken();
											String cuid_cp_tmp = st.nextToken();
											String idkmk_cp_tmp = st.nextToken();
											//begini karna debugging aja
											
											if(st.hasMoreTokens()) {
												String subNakmk_tmp=st.nextToken();	
											}
											else {
												String subNakmk_tmp="null";
											}
											String kode_gab_cp_tmp = st.nextToken();
											if(shift_tmp.equalsIgnoreCase(v_shift)) {
												match = true;
												if(Checker.isStringNullOrEmpty(cuid_inith)) {
													cuid_inith = new String(cuid_cp_tmp);
												}
												%>
												<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,null,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>,<%=cuid_inith%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
												<%
											}
										}	
									}
									else {
										match = true;
										if(Checker.isStringNullOrEmpty(cuid_inith)) {
											cuid_inith = new String(cuid_cp);
										}
										%>
										<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,null,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>,<%=cuid_inith%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
										<%
										while(st.hasMoreTokens()) {
											st.nextToken();
										}
									}									
								}
							}
						}
						else {
							match=true;
							if(Checker.isStringNullOrEmpty(kode_gab_cp)) {
							%>
							<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,null,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
							<%
							}
							else {
								/*
								KELAS GABUNGAN
								KARENA VALUE DIATAS ADALAH TOKEN PERTAMA MAKA ITU KELAS INTI
								SEKARANG TINGGAL DICEK APA SHIFT MHS INI TERMASUK YG DIGABUNG, BILA TIDAK INIT CUIDDI TRNLMNYA KOSONG
								,
								*/
								//karena bebas shift jadi value di token pertama / kelas inti 
								//loop abisin token 
								if(Checker.isStringNullOrEmpty(cuid_inith)) {
									cuid_inith = new String(cuid_cp);
								}
								%>
					<option value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,null,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>,<%=uniqueId%>,<%=cuid_inith%>"><%=Checker.pnn(subNakmk)+"; "%> <%=Checker.pnn(kode_gab_cp)%> (#<%=norutKelasParalel%>) <%=nmmdos %> (<%=shift%>/<%=kodeKampus %>)</option>
								<%
								while(st.hasMoreTokens()) {
									st.nextToken();
								}
							}
						}
					}
					if(!match) {
						%>
						<option value="null" selected>KELAS TIDAK DI TAWARKAN PADA SEMESTER INI</option>
						<%
					}
					%>
					
				</select>
				
				<%
					}
				}
			%>
			</td>
		</tr>	
		<%	
				}
				%>
	</table>			
				<%
			}
			else {
			%>
	</table>	
			<%
			}
		}
		
	}
	

	if((adaData && !notThsmsKrs) || whiteList)  {
	%>
	<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:700px">
	<tr>
		<td><input type="submit" value="UPDATE KRS" style="width:98%" /></td>
	</tr>
	</table>
	<%
	}
	
	%>
	</form>
	<%
	}
	%>
	<!-- Column 1 end -->
	</div>
</div>
</body>
</html>