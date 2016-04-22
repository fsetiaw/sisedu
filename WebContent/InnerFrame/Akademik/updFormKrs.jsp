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
String targetThsms = request.getParameter("targetThsms");
String whitelistValue = request.getParameter("whitelist");
boolean whiteList = false;
if(whitelistValue!=null && !Checker.isStringNullOrEmpty(whitelistValue)) {
	whiteList = Boolean.parseBoolean(whitelistValue);
}
//System.out.println("whiteList="+whiteList);
Vector vSdh =(Vector) request.getAttribute("vSdh");
Vector vBlm =(Vector) request.getAttribute("vBlm");
Vector vCp =(Vector) request.getAttribute("vCp");
boolean cpMode = false; //mulai sekarang harus cpmode atau boleh via whitelist
if(vCp!=null && vCp.size()>0) {
	cpMode = true;
}
//Vector vSdhCp =(Vector) request.getAttribute("vSdhCp");
//Vector vBlmCp =(Vector) request.getAttribute("vBlmCp");

//if(vCp!=null) {
//	System.out.println("vCp.size = "+vCp.size());
//	if(vSdhCp!=null) {
//		System.out.println("vSdhCp.size = "+vSdhCp.size());
//	}
//	if(vBlmCp!=null) {
//		System.out.println("vBlmCp.size = "+vBlmCp.size());
//	}
//	ListIterator liCp = vCp.listIterator();
	
//}
Vector v = null;
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
//boolean readOnlyMode = false;
//if(validUsr.isAllowTo("insertKrsReadOnlyMode")>0) {
//	readOnlyMode = true;
//}
%>


</head>
<body>
<div id="header">
<!--   include file="../innerMenu.jsp" -->
<%@ include file="../krsKhsSubMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<%
		/*
		msg di pas dari HistoryKrsKhs servlet, bilai msg !=null berarti blum ditentukan kurikulumnya
		*/
		//System.out.println("sampe siniupdFormKrs");
		//System.out.println("msg="+msg);
		
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
	//System.out.println("disini2");
	//System.out.println("vblm="+vBlm.size());
	//System.out.println("targetThsms="+targetThsms);
	//System.out.println("Checker.getThsmsKrs()="+Checker.getThsmsKrs());
	
	boolean notThsmsKrs = !targetThsms.equalsIgnoreCase(Checker.getThsmsKrs());
	/*
	// proses kalo targetThsms untuk npm ini termasuk yg di white list
	*/
	if(false) {		
	/*
	if(!cpMode) {
		//blokir non class pool mode - harus pake class pool mode
		%>
		<h2 align="center"><br/>Tidak ada data kelas yang dibuka / diajukan pada tahun/sms : <%=targetThsms.substring(0,4) %>/<%=targetThsms.substring(4,5) %></h2>
		<br/>
		<%		
	}
	else if(notThsmsKrs){
		//blokir harus thsms sesuai dengan thsmskrs di tabel calenda
		%>
		<h2 align="center"><br/>Pengisian KRS tahun/sms : <%=targetThsms.substring(0,4) %>/<%=targetThsms.substring(4,5) %> sudah ditutup/belum dibuka</h2>
		<br/>
		<%
	
	*/
	}
	else {		
		%>
		
<P>
					
    <form action="proses.updKrs" >	
    <input type="hidden" name="id_obj" value="<%=v_id_obj %>" />
    <input type="hidden" name="nmm" value="<%=v_nmmhs %>" />
    <input type="hidden" name="npm" value="<%=v_npmhs %>" />
    <input type="hidden" name="kdpst" value="<%=v_kdpst %>" />
    <input type="hidden" name="obj_lvl" value="<%=v_obj_lvl %>" />	
    	<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:700px">
		<tr>
			<td style="background:#369;color:#fff;text-align:center;height:30px;font-size:25px" colspan="6"><label><B>PENGISIAN KRS/KSH</B></label>
				<select name="thsms" style="font-size:20px;">
					<%
						//String keter_thsms = Converter.convertThsmsKeterOnly(Checker.getThsmsKrs());
						//String value_thsms = Converter.convertThsmsValueOnly(Checker.getThsmsKrs());
						%>
						<option value="<%=targetThsms %>" selected="selected"><%=Converter.convertThsmsKeterOnly(targetThsms)%></option>
						<%
					%>
				</select>
			</td>
		</tr>
    	
	<%
		String prevSms = "";
		boolean adaData=false; //cuma buat flag utk submit button
		String atPage="update Form Krs";
		int i=0;

		if(vBlm!=null && vBlm.size()>0) {
			adaData=true;
			ListIterator li = vBlm.listIterator();
			if(li.hasNext()) {
				i++;
				String baris = (String)li.next();
				StringTokenizer st = new StringTokenizer(baris,",");
				String idkmk = st.nextToken();
				String kdkmk = st.nextToken();
				String nakmk = st.nextToken();
				String skstm = st.nextToken();
				String skspr = st.nextToken();
				String skslp = st.nextToken();
				int sksmk = (Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue());
				String semes = st.nextToken();
				prevSms=""+semes;
				String avail = "false";
				String noKlsPll = "null";
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
				String max_enrolled = "null";
				String min_enrolled = "null";
			
				if(st.hasMoreTokens()) {
					avail = st.nextToken();
					noKlsPll = st.nextToken();
					currStatus = st.nextToken();
					npmdos = st.nextToken();
					npmasdos = st.nextToken();
					canceled = st.nextToken();
					kodeKelas = st.nextToken();
					kodeRuang = st.nextToken();
					kodeGedung = st.nextToken();
					kodeKampus = st.nextToken();
					tknDayTime = st.nextToken();
					nmmdos = st.nextToken();
					nmmasdos = st.nextToken();
					enrolled = st.nextToken();
					max_enrolled = st.nextToken();
					min_enrolled = st.nextToken();
				}
		%>
		<tr>
			<td style="background:#369;color:#fff;text-align:center;width:60px" rowspan="2"><label><B>KODE</B> </label></td>
			<td style="background:#369;color:#fff;text-align:left;width:445px" rowspan="2"><label><B>MATAKULIAH</B> </label></td>
			<td style="background:#369;color:#fff;text-align:center;" colspan="3"><label><B>SKS</B> </label></td>
			<td style="background:#369;color:#fff;text-align:center;" rowspan="2"><label><B>*</B> </label></td>
		</tr>
		<tr>	
			<td style="background:#369;color:#fff;text-align:center;width:65px"><label><B>TATAP MUKA</B> </label></td>
			<td style="background:#369;color:#fff;text-align:center;width:65px"><label><B>PRAKTEK</B> </label></td>
			<td style="background:#369;color:#fff;text-align:center;width:65px"><label><B>LAPANGAN</B> </label></td>
		</tr>
		<tr>
			<td style="background:#9FADB4;color:#000;text-align:center;width:60px" colspan="6"><label><B>MATAKULIAH SEMESTER :
			<%
				if(semes==null || semes.equalsIgnoreCase("null")) {
					out.print("?? - semester tiap matakuliah belum diisi di kurikulum");
				}
				else {
					out.print(semes);
				}
			%>
			</B> </label></td>
		</tr>
		<tr>
			<td style="color:#000;text-align:center;width:60px"><label><B><%=kdkmk %></B> </label></td>
			<td style="color:#000;text-align:left;width:445px"><label><B><%=nakmk %></B> </label></td>
			<td style="color:#000;text-align:center;width:65px"><label><B><%=skstm %></B> </label></td>
			<td style="color:#000;text-align:center;width:65px"><label><B><%=skspr %></B> </label></td>
			<td style="color:#000;text-align:center;width:65px"><label><B><%=skslp %></B> </label></td>
			<td>
			<%
				//if(!cpMode && !readOnlyMode ) {
				if(whiteList ) {	
			%>
			<input type="checkbox" name="makul" value="<%=idkmk %>,<%=noKlsPll %>,<%= currStatus%>,<%=npmdos %>,<%=npmasdos %>" />
			<%	
				}
			/*
				else {
					if(avail.equalsIgnoreCase("avail") && !readOnlyMode) {
			%>
			<input type="checkbox" name="makul" value="<%=idkmk %>,<%=noKlsPll %>,<%= currStatus%>,<%=npmdos %>,<%=npmasdos %>" />
			<%
					}
				}
			*/
			%>
			</td>
		</tr>
		
		<%
				if(li.hasNext()) {
					while(li.hasNext()) {
						i++;
						baris = (String)li.next();
					//System.out.println(i+"."+baris);
						st = new StringTokenizer(baris,",");
						idkmk = st.nextToken();
						kdkmk = st.nextToken();
						nakmk = st.nextToken();
						skstm = st.nextToken();
						skspr = st.nextToken();
						skslp = st.nextToken();
						sksmk = (Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue());
						semes = st.nextToken();
						avail = "false";
						noKlsPll = "null";
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
    					max_enrolled = "null";
    					min_enrolled = "null";
					
					
						if(st.hasMoreTokens()) {
							avail = st.nextToken();
							noKlsPll = st.nextToken();
							currStatus = st.nextToken();
							npmdos = st.nextToken();
							npmasdos = st.nextToken();
							canceled = st.nextToken();
							kodeKelas = st.nextToken();
							kodeRuang = st.nextToken();
							kodeGedung = st.nextToken();
							kodeKampus = st.nextToken();
							tknDayTime = st.nextToken();
							nmmdos = st.nextToken();
							nmmasdos = st.nextToken();
							enrolled = st.nextToken();
	    					max_enrolled = st.nextToken();
	    					min_enrolled = st.nextToken();
						}
					
					//System.out.println("semes vs prevSms = "+semes+","+prevSms);
						if(semes.equalsIgnoreCase("null")) {
						%>
		<tr>
			<td style="color:#000;text-align:center;width:60px"><label><B><%=kdkmk %></B> </label></td>
			<td style="color:#000;text-align:left;width:445px"><label><B><%=nakmk %></B> </label></td>
			<td style="color:#000;text-align:center;width:65px"><label><B><%=skstm %></B> </label></td>
			<td style="color:#000;text-align:center;width:65px"><label><B><%=skspr %></B> </label></td>
			<td style="color:#000;text-align:center;width:65px"><label><B><%=skslp %></B> </label></td>
			<td>
							<!-- input type="checkbox" name="makul" value="<%=idkmk %>,<%=noKlsPll %>,<%= currStatus%>,<%=npmdos %>,<%=npmasdos %>" / -->
						<%
							//if(!cpMode && !readOnlyMode) {
							if(whiteList ) {		
						%>
				<input type="checkbox" name="makul" value="<%=idkmk %>,<%=noKlsPll %>,<%= currStatus%>,<%=npmdos %>,<%=npmasdos %>" />
						<%	
							}
						/*	
							else {
								if(avail.equalsIgnoreCase("avail") && !readOnlyMode) {
						%>
				<input type="checkbox" name="makul" value="<%=idkmk %>,<%=noKlsPll %>,<%= currStatus%>,<%=npmdos %>,<%=npmasdos %>" />
						<%
								}
							}
						*/	
						%>
			</td>
		</tr>
						<%	
						}
						else {
							if(Integer.valueOf(Converter.prepNumberString(prevSms)).intValue()==Integer.valueOf(Converter.prepNumberString(semes)).intValue()) {
							
					%>
		<tr>
			<td style="color:#000;text-align:center;width:60px"><label><B><%=kdkmk %></B> </label></td>
			<td style="color:#000;text-align:left;width:445px"><label><B><%=nakmk %></B> </label></td>
			<td style="color:#000;text-align:center;width:65px"><label><B><%=skstm %></B> </label></td>
			<td style="color:#000;text-align:center;width:65px"><label><B><%=skspr %></B> </label></td>
			<td style="color:#000;text-align:center;width:65px"><label><B><%=skslp %></B> </label></td>
			<td>
			<!--  input type="checkbox" name="makul" value="<%=idkmk %>,<%=noKlsPll %>,<%= currStatus%>,<%=npmdos %>,<%=npmasdos %>" / -->
			<%
								//if(!cpMode && !readOnlyMode) {
								if(whiteList ) {		
			%>
				<input type="checkbox" name="makul" value="<%=idkmk %>,<%=noKlsPll %>,<%= currStatus%>,<%=npmdos %>,<%=npmasdos %>" />
			<%	
								}
								/*
								else {
									if(avail.equalsIgnoreCase("avail") && !readOnlyMode) {
			%>
				<input type="checkbox" name="makul" value="<%=idkmk %>,<%=noKlsPll %>,<%= currStatus%>,<%=npmdos %>,<%=npmasdos %>" />
			<%
									}
								}
								*/
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
			<td style="background:#9FADB4;color:#000;text-align:center;width:60px" colspan="6"><label><B>MATAKULIAH SEMESTER :<%=semes %></B> </label></td>
		</tr>
		<tr>
			<td style="color:#000;text-align:center;width:60px"><label><B><%=kdkmk %></B> </label></td>
			<td style="color:#000;text-align:left;width:445px"><label><B><%=nakmk %></B> </label></td>
			<td style="color:#000;text-align:center;width:65px"><label><B><%=skstm %></B> </label></td>
			<td style="color:#000;text-align:center;width:65px"><label><B><%=skspr %></B> </label></td>
			<td style="color:#000;text-align:center;width:65px"><label><B><%=skslp %></B> </label></td>
			<td>
			<%
								//if(!cpMode && !readOnlyMode) {
								if(whiteList ) {		
			%>
			<input type="checkbox" name="makul" value="<%=idkmk %>,<%=noKlsPll %>,<%= currStatus%>,<%=npmdos %>,<%=npmasdos %>" />
			<%	
								}
								/*
								else {
									if(avail.equalsIgnoreCase("avail") && !readOnlyMode) {
			%>
			<input type="checkbox" name="makul" value="<%=idkmk %>,<%=noKlsPll %>,<%= currStatus%>,<%=npmdos %>,<%=npmasdos %>" />
			<%
									}
								}
								*/
			%>
			</td>
		</tr>
					<%	
							}//end else 321
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
			</table>
			<%
				}
			}
		}
		
		if(vSdh!=null && vSdh.size()>0) {	
			adaData=true;
			ListIterator li1 = vSdh.listIterator();
	//mo di sort berdasarkan nilai terrendah
			Vector v2 = new Vector();
			ListIterator li2 = v2.listIterator();
	
			while(li1.hasNext()) {
				String baris = (String)li1.next();
				StringTokenizer st = new StringTokenizer(baris,",");
				String idkmk=st.nextToken();
				String thsms=st.nextToken();
				String kdkmk=st.nextToken();
				String nakmk=st.nextToken();
				String nlakh=st.nextToken();
				String bobot=st.nextToken();
				String avail = "false";
				String noKlsPll = "null";
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
				String max_enrolled = "null";
				String min_enrolled = "null";
				if(st.hasMoreTokens()) {
					avail = st.nextToken();
					noKlsPll = st.nextToken();
					currStatus = st.nextToken();
					npmdos = st.nextToken();
					npmasdos = st.nextToken();
					canceled = st.nextToken();
					kodeKelas = st.nextToken();
					kodeRuang = st.nextToken();
					kodeGedung = st.nextToken();
					kodeKampus = st.nextToken();
					tknDayTime = st.nextToken();
					nmmdos = st.nextToken();
					nmmasdos = st.nextToken();
					enrolled = st.nextToken();
					max_enrolled = st.nextToken();
					min_enrolled = st.nextToken();
				}
				li2.add(bobot+","+nlakh+","+nakmk+","+kdkmk+","+thsms+","+idkmk+","+noKlsPll+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+max_enrolled+","+min_enrolled+","+avail);
			}
			Collections.sort(v2);
			li2 = v2.listIterator();
			if(li2.hasNext()){
				String baris = (String)li2.next();
				StringTokenizer st = new StringTokenizer(baris,",");
				String bobot=st.nextToken();
				String nlakh=st.nextToken();
				String nakmk=st.nextToken();
				String kdkmk=st.nextToken();
				String thsms=st.nextToken();
				String idkmk=st.nextToken();
				String noKlsPll = st.nextToken();
				String currStatus = st.nextToken();
				String npmdos = st.nextToken();
				String npmasdos = st.nextToken();
				String canceled = st.nextToken();
				String kodeKelas = st.nextToken();
				String kodeRuang = st.nextToken();
				String kodeGedung = st.nextToken();
				String kodeKampus = st.nextToken();
				String tknDayTime = st.nextToken();
				String nmmdos = st.nextToken();
				String nmmasdos = st.nextToken();
				String enrolled = st.nextToken();
				String max_enrolled = st.nextToken();
				String min_enrolled = st.nextToken();
				String avail=st.nextToken();
			//out.println("<br/>"+baris);
		%>
	<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:700px">
		<tr>
			<td style="background:#369;color:#fff;text-align:center;font-size:25px" colspan="6"><label><B>MATAKULIAH YANG SUDAH DIAMBIL</B></label></td>
		</tr>
		<tr>
			<td style="background:#9FADB4;color:#000;text-align:center;width:60px"><label><B>KODE</B> </label></td>
			<td style="background:#9FADB4;color:#000;text-align:left;width:445px"><label><B>MATAKULIAH</B> </label></td>
			<td style="background:#9FADB4;color:#000;text-align:center;width:65px"><label><B>NILAI</B> </label></td>
			<td style="background:#9FADB4;color:#000;text-align:center;width:65px"><label><B>BOBOT</B> </label></td>
			<td style="background:#9FADB4;color:#000;text-align:center;width:65px"><label><B>THSMS</B> </label></td>
			<td style="background:#9FADB4;color:#000;text-align:center;"><label><B></B> </label></td>
		</tr>	
		<% 	
				if(li2.hasNext()) {
					while(li2.hasNext()) {
						baris = (String)li2.next();
						st = new StringTokenizer(baris,",");
						bobot=st.nextToken();
						nlakh=st.nextToken();
						nakmk=st.nextToken();
						kdkmk=st.nextToken();
						thsms=st.nextToken();
						idkmk=st.nextToken();
						noKlsPll = st.nextToken();
						currStatus = st.nextToken();
						npmdos = st.nextToken();
						npmasdos = st.nextToken();
						canceled = st.nextToken();
						kodeKelas = st.nextToken();
						kodeRuang = st.nextToken();
						kodeGedung = st.nextToken();
						kodeKampus = st.nextToken();
						tknDayTime = st.nextToken();
						nmmdos = st.nextToken();
						nmmasdos = st.nextToken();
						enrolled = st.nextToken();
						max_enrolled = st.nextToken();
						min_enrolled = st.nextToken();
						avail=st.nextToken();
			%>
		<tr>
			<td style="color:#000;text-align:center;width:60px"><label><B><%=kdkmk %></B> </label></td>
			<td style="color:#000;text-align:left;width:445px"><label><B><%=nakmk %></B> </label></td>
			<td style="color:#000;text-align:center;width:65px"><label><B><%=nlakh %></B> </label></td>
			<td style="color:#000;text-align:center;width:65px"><label><B><%=bobot %></B> </label></td>
			<td style="color:#000;text-align:center;width:65px"><label><B><%=thsms %></B> </label></td>
			<td>
			<%
						//if(!cpMode && !readOnlyMode) {
						if(whiteList ) {		
			%>
			<input type="checkbox" name="makul" value="<%=idkmk %>,<%=noKlsPll %>,<%= currStatus%>,<%=npmdos %>,<%=npmasdos %>" />
			<%	
						}
						/*
						else {
							if(avail.equalsIgnoreCase("avail") && !readOnlyMode) {
			%>
			<input type="checkbox" name="makul" value="<%=idkmk %>,<%=noKlsPll %>,<%= currStatus%>,<%=npmdos %>,<%=npmasdos %>" />
			<%
							}
						}
						*/
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
		//if(adaData && !readOnlyMode) {
		if(adaData && whiteList) {	
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
	}//end else cpmode
}
	%>
		</div>
	</div>
</body>
</html>