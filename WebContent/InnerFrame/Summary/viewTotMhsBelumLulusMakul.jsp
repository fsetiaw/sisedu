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
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
//	Vector v_kdkmk_tknMhs = (Vector)request.getAttribute("v_kdkmk_tknMhs"); 
//	request.removeAttribute("v_kdkmk_tknMhs"); 
	//String thsms = (String)request.getAttribute("target_thsms");
	//request.removeAttribute("target_thsms"); 
//	String thsms = request.getParameter("target_thsms");
String kdpst_nmpst = request.getParameter("kdpst_nmpst");
	
String kdpst=null,nmpst=null;
if(kdpst_nmpst!=null) {
		StringTokenizer st = new StringTokenizer(kdpst_nmpst,",");
		kdpst = st.nextToken();
		nmpst = st.nextToken();
}	
	//String atMenu=request.getParameter("atMenu");
//	System.out.println("v_kdkmk_tknMhs size ="+v_kdkmk_tknMhs.size());
Vector v = (Vector)session.getAttribute("vInfoMhsYgBelumAmbilMk");
session.removeAttribute("vInfoMhsYgBelumAmbilMk");
String lisKrklm = (String)request.getAttribute("lisKrklm");
request.removeAttribute("lisKrklm");
String listShift = (String)request.getAttribute("listShift");//uniqKeter+"#"+shift+"#"+hari+"#"+tkn_kdjen+"#"+keterKonversi+"#";
int shiftCounter = 0;
StringTokenizer st = new StringTokenizer(listShift,"#");
String colspan = ""+st.countTokens()+1;
shiftCounter = st.countTokens()/5;
request.removeAttribute("listShift");
//System.out.println("v size="+v.size());
//System.out.println("kdpst="+kdpst);
//System.out.println("nmpst="+nmpst);
int mkColLeng = 300;
int shiftColLeng = 125;
%>


</head>
<body>
<div id="header">
<%@ include file="KelasPerkuliahanSubMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<%
		if(v!=null && v.size()>1) {
			ListIterator li = v.listIterator();
			if(li.hasNext()) {
				String infoMk = (String)li.next();
				//System.out.println("-infoMk "+infoMk);
				//li.add(idkmk+"||"+kdkmk+"||"+nakmk+"||"+skstm+"||"+skspr+"||"+skslp+"||"+kdwpl+"||"+jenis+"||"+stkmk+"||"+nodos+"||"+semes);
				StringTokenizer st1 = new StringTokenizer(infoMk,"||");
				String prev_idkmk = st1.nextToken();
				String prev_kdkmk = st1.nextToken();
				String prev_nakmk = st1.nextToken();
				String prev_skstm = st1.nextToken();
				String prev_skspr = st1.nextToken();
				String prev_skslp = st1.nextToken();
				String prev_kdwpl = st1.nextToken();
				String prev_jenis = st1.nextToken();
				String prev_stkmk = st1.nextToken();
				String prev_nodos = st1.nextToken();
				String prev_semes = st1.nextToken();
				Vector vNotTakenByShift = (Vector)li.next();//prev_smawl+"||"+prev_shift+"||"+prev_npmhs+"||"+prev_idkur+"||"+keterKonversi+"||"+tot
				Vector vNotTakenBySmawl = (Vector)li.next();
				Vector vNotLulusByShift = (Vector)li.next();
				Vector vNotLulusBySmawl = (Vector)li.next();
				//ListIterator li0 = vNotTakenBySmawl.listIterator();
				//while(li0.hasNext()) {
				//	out.println((String)li0.next()+"<br/>");
				//}
			
		%>
	 	<br/>
	 	<table align="center" border="0px" style="width:<%=mkColLeng+(shiftCounter*shiftColLeng)%>px">
			<tr>
			 	<td>
	 	<div style="color:red;font-weight:bold">Jumlah Mhs yg belum pernak ambil Matakuliah</div><div style="color:#CB8502;font-weight:bold">Jumlah Mhs yg harus mengulang Matakuliah</div>
	 			</td>
	 		</tr>
	 	</table>
	 		
	 	<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:<%=mkColLeng+(shiftCounter*shiftColLeng)%>px">
			<tr>
	       		<td style="background:#369;color:#fff;text-align:center" colspan="<%=colspan%>"><label><B>SEMESTER : <%=prev_semes %>  </B> </label></td>
	       	</tr>	
			<tr>
	       		<td style="background:#369;color:#fff;text-align:center;width:<%=mkColLeng%>px"><label><B>MATAKULIAH</B> </label></td>
	       	<%
	       		st = new StringTokenizer(listShift,"#");
	       		while(st.hasMoreTokens()) {
		       		String uniqKeter = st.nextToken();
	       			String shift = st.nextToken();
	       			String hari = st.nextToken();
	       			String tkn_kdjen = st.nextToken();
	       			String keterKonversi = st.nextToken();
	       	%>
	       		<td style="background:#369;color:#fff;text-align:center;width:<%=shiftColLeng%>px"><label><B><%=keterKonversi.toUpperCase() %></B> </label></td>
	       	<%	
	       		}
	       	%>	
	       	</tr>
	       	<%
			
	       	%>
	        <tr>
	       		<td style="color:#000;text-align:LEFT"><div style="font-weight:bold;font-size:1.3em"><%=prev_kdkmk %></div><%=prev_nakmk %></td>
	       	<%
	       		st = new StringTokenizer(listShift,"#");
	       		while(st.hasMoreTokens()) {
		       		String uniqKeter = st.nextToken();
	       			String shift = st.nextToken();
	       			String hari = st.nextToken();
	       			String tkn_kdjen = st.nextToken();
	       			String keterKonversi = st.nextToken();
	       			
	       			ListIterator litmp = vNotTakenByShift.listIterator();
	       			int counter = 0;
	       			while(litmp.hasNext()) {
	       				//prev_smawl+"||"+prev_shift+"||"+prev_npmhs+"||"+prev_idkur+"||"+keterKonversi+"||"+tot
	       				String brs = (String)litmp.next();
	       				if(brs.contains(keterKonversi)) {
	       					counter++;
	       				}
	       			}
	       			
	       			litmp = vNotLulusByShift.listIterator();
	       			int counterL = 0;
	       			while(litmp.hasNext()) {
	       				//prev_smawl+"||"+prev_shift+"||"+prev_npmhs+"||"+prev_idkur+"||"+keterKonversi+"||"+tot
	       				String brs = (String)litmp.next();
	       				if(brs.contains(keterKonversi)) {
	       					counterL++;
	       				}
	       			}
	       			
	       			
	       			//sebaran angkatan belum ambil
	       			litmp = vNotTakenBySmawl.listIterator();
	       			//20131||REGULER PAGI||8888813100031||11||REGULER PAGI||14
	       			//int counterTknAng = 0;
	       			boolean first = true;
	       			String listNpm = "";
	       			String prev_smawl = "";
	       			String tot = "0";
	       			String listAngDanJum = "";
	       			while(litmp.hasNext()) {
	       				//prev_smawl+"||"+prev_shift+"||"+prev_npmhs+"||"+prev_idkur+"||"+keterKonversi+"||"+tot
	       				String brs = (String)litmp.next();
	       				//System.out.println("baris =="+brs);
	       				
	       				if(brs.contains(keterKonversi)) {
	       					StringTokenizer stt = new StringTokenizer(brs,"||");
	       					if(first) {
	       						first = false;
	       						prev_smawl = stt.nextToken();
	       						stt.nextToken();
	       						listNpm=listNpm+","+stt.nextToken();
	       						stt.nextToken();
	       						stt.nextToken();
	       						tot = stt.nextToken();
	       						listAngDanJum = ""+prev_smawl;
	       					}
	       					else {
	       						String curr_smawl = stt.nextToken();
	       						if(curr_smawl.equalsIgnoreCase(prev_smawl)) {
	       							stt.nextToken();
	       							listNpm=listNpm+","+stt.nextToken();
		       						stt.nextToken();
		       						stt.nextToken();
		       						tot = stt.nextToken();
		       					}
	       						else {
	       							//listAngDanJum=listAngDanJum+"="+tot;
	       							if(Integer.valueOf(tot).intValue()>0) {
	       								listAngDanJum=listAngDanJum+"=<a href=\"prep.tampletInfoBasedListNpm?smawl="+prev_smawl+"&kdpst="+kdpst+"&nmpst="+nmpst+"&listNpm="+listNpm+"\">"+tot+"</a>";
	       							}	
	       							listNpm="";
	       							prev_smawl = ""+curr_smawl;
	       							listAngDanJum=listAngDanJum+" "+prev_smawl;
	       							stt.nextToken();
	       							listNpm=listNpm+","+stt.nextToken();
		       						stt.nextToken();
		       						stt.nextToken();
		       						tot = stt.nextToken();
		       					}
	       						
	       					}
	       					if(!litmp.hasNext()) {
	       						//listAngDanJum=listAngDanJum+"="+tot;
	       						if(Integer.valueOf(tot).intValue()>0) {
	       							listAngDanJum=listAngDanJum+"=<a href=\"prep.tampletInfoBasedListNpm?smawl="+prev_smawl+"&kdpst="+kdpst+"&nmpst="+nmpst+"&listNpm="+listNpm+"\">"+tot+"</a>";
	       						}
	       						listNpm="";
	       					}
   						}
	       				else {
	       					if(!litmp.hasNext()) {
	       						//listAngDanJum=listAngDanJum+"="+tot;
	       						if(Integer.valueOf(tot).intValue()>0) {
	       							listAngDanJum=listAngDanJum+"=<a href=\"prep.tampletInfoBasedListNpm?smawl="+prev_smawl+"&kdpst="+kdpst+"&nmpst="+nmpst+"&listNpm="+listNpm+"\">"+tot+"</a>";
	       						}
	       						listNpm="";
	       					}
	       				}
	       			}
	       			
	       			//sebaran angkatan belum lulus
	       			litmp = vNotLulusBySmawl.listIterator();
	       			//20131||REGULER PAGI||8888813100031||11||REGULER PAGI||14
	       			//int counterTknAng = 0;
	       			listNpm = "";
	       			first = true;
	       			prev_smawl = "";
	       			tot = "0";
	       			String listAngDanJum1 = "";
	       			while(litmp.hasNext()) {
	       				//prev_smawl+"||"+prev_shift+"||"+prev_npmhs+"||"+prev_idkur+"||"+keterKonversi+"||"+tot
	       				String brs = (String)litmp.next();
	       				//System.out.println("baris =="+brs);
	       				
	       				if(brs.contains(keterKonversi)) {
	       					StringTokenizer stt = new StringTokenizer(brs,"||");
	       					if(first) {
	       						first = false;
	       						prev_smawl = stt.nextToken();
	       						stt.nextToken();
	       						listNpm=listNpm+","+stt.nextToken();
	       						stt.nextToken();
	       						stt.nextToken();
	       						tot = stt.nextToken();
	       						listAngDanJum1 = ""+prev_smawl;
	       						//if(!litmp.hasNext()) {
	       						//	listAngDanJum1=listAngDanJum1+"=("+listNpm+")"+tot;
	       						//	listNpm="";
		       					//}
	       					}
	       					else {
	       						String curr_smawl = stt.nextToken();
	       						if(curr_smawl.equalsIgnoreCase(prev_smawl)) {
	       							stt.nextToken();
	       							listNpm=listNpm+","+stt.nextToken();
		       						stt.nextToken();
		       						stt.nextToken();
		       						tot = stt.nextToken();
		       					}
	       						else {
	       							if(Integer.valueOf(tot).intValue()>0) {
	       								listAngDanJum1=listAngDanJum1+"=<a href=\"prep.tampletInfoBasedListNpm?smawl="+prev_smawl+"&kdpst="+kdpst+"&nmpst="+nmpst+"&listNpm="+listNpm+"\">"+tot+"</a>";
	       							}
	       							listNpm="";
	       							prev_smawl = ""+curr_smawl;
	       							listAngDanJum1=listAngDanJum1+" "+prev_smawl;
	       							stt.nextToken();
	       							listNpm=listNpm+","+stt.nextToken();
		       						stt.nextToken();
		       						stt.nextToken();
		       						tot = stt.nextToken();
		       					}
	       						
	       					}
	       					if(!litmp.hasNext()) {
	       						if(Integer.valueOf(tot).intValue()>0) {
	       							listAngDanJum1=listAngDanJum1+"=<a href=\"prep.tampletInfoBasedListNpm?smawl="+prev_smawl+"&kdpst="+kdpst+"&nmpst="+nmpst+"&listNpm="+listNpm+"\">"+tot+"</a>";
	       						}
	       						listNpm="";
	       					}
   						}
	       				else {
	       					if(!litmp.hasNext()) {
	       						if(Integer.valueOf(tot).intValue()>0) {
	       							listAngDanJum1=listAngDanJum1+"=<a href=\"prep.tampletInfoBasedListNpm?smawl="+prev_smawl+"&kdpst="+kdpst+"&nmpst="+nmpst+"&listNpm="+listNpm+"\">"+tot+"</a>";
	       						}
	       						listNpm="";
	       					}
	       				}
	       			}
	       			/*
	       			if(listAngDanJum1!=null) {
	       				StringTokenizer st2 = new StringTokenizer(listAngDanJum1);
	       				//System.out.println("listAngDanJum1="+listAngDanJum1);
	       				//System.out.println(st2.countTokens());
	       				if(st2.countTokens()==3) {
	       					listAngDanJum1 = "";
	       				}	
	       			}
	       			if(listAngDanJum!=null) {
	       				StringTokenizer st2 = new StringTokenizer(listAngDanJum);
	       				System.out.println("listAngDanJum="+listAngDanJum);
	       				System.out.println(st2.countTokens());
	       				if(st2.countTokens()==2) {
	       					listAngDanJum = "";
	       				}
	       			}
	       			*/
	       	%>
	       		<td style="color:#000;text-align:center">
	       			<div style="color:red;font-weight:bold"><%=counter %></div>
	       			<div style="color:#CB8502;font-weight:bold"><%=counterL %></div>
	       			<div style="color:red;font-weight:bold"><%= listAngDanJum %></div>
	       			<div style="color:#CB8502;font-weight:bold"><%=listAngDanJum1 %></div>
	       		</td>
	       		
	       	<%
	       		}
	       	%>
	       	</tr>
	       	<%	
	       		if(!li.hasNext()) {
	       			//cuma satu row - close table
	       	%>
	    </table>
	    	<%
	       		}
	       		else {
	       			while(li.hasNext()) {
						infoMk = (String)li.next();
						//System.out.println("-infoMk "+infoMk);
						//li.add(idkmk+"||"+kdkmk+"||"+nakmk+"||"+skstm+"||"+skspr+"||"+skslp+"||"+kdwpl+"||"+jenis+"||"+stkmk+"||"+nodos+"||"+semes);
						st1 = new StringTokenizer(infoMk,"||");
						String curr_idkmk = st1.nextToken();
						String curr_kdkmk = st1.nextToken();
						String curr_nakmk = st1.nextToken();
						String curr_skstm = st1.nextToken();
						String curr_skspr = st1.nextToken();
						String curr_skslp = st1.nextToken();
						String curr_kdwpl = st1.nextToken();
						String curr_jenis = st1.nextToken();
						String curr_stkmk = st1.nextToken();
						String curr_nodos = st1.nextToken();
						String curr_semes = st1.nextToken();
						vNotTakenByShift = (Vector)li.next();//prev_smawl+"||"+prev_shift+"||"+prev_npmhs+"||"+prev_idkur+"||"+keterKonversi+"||"+tot
						vNotTakenBySmawl = (Vector)li.next();
						vNotLulusByShift = (Vector)li.next();
						vNotLulusBySmawl = (Vector)li.next();
						if(!prev_semes.equalsIgnoreCase(curr_semes)) {
							prev_idkmk = ""+curr_idkmk;	
							prev_kdkmk = ""+curr_kdkmk;	
							prev_nakmk = ""+curr_nakmk;	
							prev_skstm = ""+curr_skstm;	
							prev_skspr = ""+curr_skspr;	
							prev_skslp = ""+curr_skslp;	
							prev_kdwpl = ""+curr_kdwpl;	
							prev_jenis = ""+curr_jenis;	
							prev_stkmk = ""+curr_stkmk;	
							prev_nodos = ""+curr_nodos;	
							prev_semes = ""+curr_semes;	
						
		//==========================================================1=====================================================
						//pergantian -tutup table kemudian buat table baru
			%>
			</table>
			<br/>
			<%
			//li0 = vNotTakenBySmawl.listIterator();
			//while(li0.hasNext()) {
			//	out.println((String)li0.next()+"<br/>");
			//}
			%>
			<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:<%=mkColLeng+(shiftCounter*shiftColLeng)%>px">
			<tr>
	       		<td style="background:#369;color:#fff;text-align:center" colspan="<%=colspan%>"><label><B>SEMESTER : <%=prev_semes %>  </B> </label></td>
	       	</tr>	
			<tr>
	       		<td style="background:#369;color:#fff;text-align:center;width:<%=mkColLeng%>px"><label><B>MATAKULIAH</B> </label></td>
	       	<%
		       				st = new StringTokenizer(listShift,"#");
	    	   				while(st.hasMoreTokens()) {
		       					String uniqKeter = st.nextToken();
	       						String shift = st.nextToken();
	       						String hari = st.nextToken();
	       						String tkn_kdjen = st.nextToken();
	       						String keterKonversi = st.nextToken();
	       	%>
	       		<td style="background:#369;color:#fff;text-align:center;width:<%=shiftColLeng%>px"><label><B><%=keterKonversi.toUpperCase() %></B> </label></td>
	       	<%	
	       					}
	       	%>	
	       	</tr>
	       	<tr>
	       		<td style="color:#000;text-align:LEFT"><div style="font-weight:bold;font-size:1.3em"><%=prev_kdkmk %></div><%=prev_nakmk %></td>
	       	<%
	       					st = new StringTokenizer(listShift,"#");
	       					while(st.hasMoreTokens()) {
				       			String uniqKeter = st.nextToken();
	       						String shift = st.nextToken();
	       						String hari = st.nextToken();
	       						String tkn_kdjen = st.nextToken();
	       						String keterKonversi = st.nextToken();
		       			
			       				ListIterator litmp = vNotTakenByShift.listIterator();
	    	   					int counter = 0;
	       						while(litmp.hasNext()) {
	       				//prev_smawl+"||"+prev_shift+"||"+prev_npmhs+"||"+prev_idkur+"||"+keterKonversi+"||"+tot
	       						String brs = (String)litmp.next();
	       							if(brs.contains(keterKonversi)) {
		       							counter++;
	       							}
	       						}
	       			
			       				litmp = vNotLulusByShift.listIterator();
	    		   				int counterL = 0;
	       						while(litmp.hasNext()) {
		       				//prev_smawl+"||"+prev_shift+"||"+prev_npmhs+"||"+prev_idkur+"||"+keterKonversi+"||"+tot
	       							String brs = (String)litmp.next();
	       							if(brs.contains(keterKonversi)) {
		       							counterL++;
	       							}
	       						}
	       						
	       						//sebaran angkatan belum ambil
	       		       			litmp = vNotTakenBySmawl.listIterator();
	       		       			//20131||REGULER PAGI||8888813100031||11||REGULER PAGI||14
	       		       			//int counterTknAng = 0;
	       		       			boolean first = true;
	       		       			String listNpm = "";
	       		       			String prev_smawl = "";
	       		       			String tot = "0";
	       		       			String listAngDanJum = "";
	       		       			while(litmp.hasNext()) {
	       		       				//prev_smawl+"||"+prev_shift+"||"+prev_npmhs+"||"+prev_idkur+"||"+keterKonversi+"||"+tot
	       		       				String brs = (String)litmp.next();
	       		       				
	       		       				
	       		       				if(brs.contains(keterKonversi)) {
	       		       					//System.out.println("baris =="+brs);
	       		       					StringTokenizer stt = new StringTokenizer(brs,"||");
	       		       					if(first) {
	       		       						first = false;
	       		       						prev_smawl = stt.nextToken();
	       		       						stt.nextToken();
	       		       						listNpm=listNpm+","+stt.nextToken();
	       		       						stt.nextToken();
	       		       						stt.nextToken();
	       		       						tot = stt.nextToken();
	       		       						listAngDanJum = ""+prev_smawl;
	       		       					}
	       		       					else {
	       		       						String curr_smawl = stt.nextToken();
	       		       						if(curr_smawl.equalsIgnoreCase(prev_smawl)) {
	       		       							stt.nextToken();
	       		       							listNpm=listNpm+","+stt.nextToken();
	       			       						stt.nextToken();
	       			       						stt.nextToken();
	       			       						tot = stt.nextToken();
	       		       						}
	       		       						else {
	       		       							//listAngDanJum=listAngDanJum+"="+tot;
	       		       							if(Integer.valueOf(tot).intValue()>0) {
	       		       								listAngDanJum=listAngDanJum+"=<a href=\"prep.tampletInfoBasedListNpm?smawl="+prev_smawl+"&kdpst="+kdpst+"&nmpst="+nmpst+"&listNpm="+listNpm+"\">"+tot+"</a>";
	       		       							}
	       		       							listNpm="";
	       		       							prev_smawl = ""+curr_smawl;
	       		       							listAngDanJum=listAngDanJum+" "+prev_smawl;
	       		       							stt.nextToken();
	       		       							listNpm=listNpm+","+stt.nextToken();
	       			       						stt.nextToken();
	       			       						stt.nextToken();
	       			       						tot = stt.nextToken();
	       		       						}
	       		       						
	       		       					}
	       		       					if(!litmp.hasNext()) {
	       		       						//listAngDanJum=listAngDanJum+"="+tot;
	       		       						if(Integer.valueOf(tot).intValue()>0) {
	       		       							listAngDanJum=listAngDanJum+"=<a href=\"prep.tampletInfoBasedListNpm?smawl="+prev_smawl+"&kdpst="+kdpst+"&nmpst="+nmpst+"&listNpm="+listNpm+"\">"+tot+"</a>";
	       		       						}
	       		       						listNpm="";
	       		       					}
	       		       					//counterTknAng++;
	       		       				}
	       		       				else {
	    	       						if(!litmp.hasNext()) {
	    	       							//listAngDanJum=listAngDanJum+"="+tot;
	    	       							if(Integer.valueOf(tot).intValue()>0) {
	    	       								listAngDanJum=listAngDanJum+"=<a href=\"prep.tampletInfoBasedListNpm?smawl="+prev_smawl+"&kdpst="+kdpst+"&nmpst="+nmpst+"&listNpm="+listNpm+"\">"+tot+"</a>";
	    	       							}
	    	       							listNpm="";
	    	       						}
	    	       					}
	       		       			}
	       		       			
	       		       			
	       		       	//sebaran angkatan belum lulus
	       		       			litmp = vNotLulusBySmawl.listIterator();
	       		       			//20131||REGULER PAGI||8888813100031||11||REGULER PAGI||14
	       		       			//int counterTknAng = 0;
	       		       			listNpm="";
	       		       			first = true;
	       		       			prev_smawl = "";
	       		       			tot = "0";
	       		       			String listAngDanJum1 = "";
	       		       			while(litmp.hasNext()) {
	       		       				//prev_smawl+"||"+prev_shift+"||"+prev_npmhs+"||"+prev_idkur+"||"+keterKonversi+"||"+tot
	       		       				String brs = (String)litmp.next();
	       		       				//System.out.println("baris =="+brs);
	       		       				
	       		       				if(brs.contains(keterKonversi)) {
	       		       					StringTokenizer stt = new StringTokenizer(brs,"||");
	       		       					if(first) {
	       		       						first = false;
	       		       						prev_smawl = stt.nextToken();
	       		       						stt.nextToken();
	       		       						listNpm=listNpm+","+stt.nextToken();
	       		       						stt.nextToken();
	       		       						stt.nextToken();
	       		       						tot = stt.nextToken();
	       		       						listAngDanJum1 = ""+prev_smawl;
	       		       						//if(!litmp.hasNext()) {
	       		       						//	listAngDanJum1=listAngDanJum1+"="+tot;
	       			       					//}
	       		       					}
	       		       					else {
	       		       						String curr_smawl = stt.nextToken();
	       		       						if(curr_smawl.equalsIgnoreCase(prev_smawl)) {
	       		       							stt.nextToken();
	       		       							listNpm=listNpm+","+stt.nextToken();
	       			       						stt.nextToken();
	       			       						stt.nextToken();
	       			       						tot = stt.nextToken();
	       			       					}
	       		       						else {
	       		       							//listAngDanJum1=listAngDanJum1+"="+tot;
	       		       							if(Integer.valueOf(tot).intValue()>0) {
	       		       								listAngDanJum1=listAngDanJum1+"=<a href=\"prep.tampletInfoBasedListNpm?smawl="+prev_smawl+"&kdpst="+kdpst+"&nmpst="+nmpst+"&listNpm="+listNpm+"\">"+tot+"</a>";
	       		       							}
	       		       							listNpm="";
	       		       							prev_smawl = ""+curr_smawl;
	       		       							listAngDanJum1=listAngDanJum1+" "+prev_smawl;
	       		       							stt.nextToken();
	       		       							listNpm=listNpm+","+stt.nextToken();
	       			       						stt.nextToken();
	       			       						stt.nextToken();
	       			       						tot = stt.nextToken();
	       			       					}
	       		       						
	       		       					}
	       		       					if(!litmp.hasNext()) {
	       		       						//listAngDanJum1=listAngDanJum1+"="+tot;
	       		       						if(Integer.valueOf(tot).intValue()>0) {
	       		       							listAngDanJum1=listAngDanJum1+"=<a href=\"prep.tampletInfoBasedListNpm?smawl="+prev_smawl+"&kdpst="+kdpst+"&nmpst="+nmpst+"&listNpm="+listNpm+"\">"+tot+"</a>";
	       		       						}
	       		       						listNpm="";
	       		       					}
	       	   						}
	       		       				else {
	       		       					if(!litmp.hasNext()) {
	       		       						//listAngDanJum1=listAngDanJum1+"="+tot;
	       		       						if(Integer.valueOf(tot).intValue()>0) {
	       		       							listAngDanJum1=listAngDanJum1+"=<a href=\"prep.tampletInfoBasedListNpm?smawl="+prev_smawl+"&kdpst="+kdpst+"&nmpst="+nmpst+"&listNpm="+listNpm+"\">"+tot+"</a>";
	       		       						}
	       		       						listNpm="";
	       		       					}
	       		       				}
	       		       			}
	       		       			/*
	       		       			if(listAngDanJum1!=null) {
	    	       					StringTokenizer st2 = new StringTokenizer(listAngDanJum1);
	    	       					if(st2.countTokens()==3) {
		    	       					listAngDanJum1 = "";
	    	       					}	
	    	       				}
	    	       				if(listAngDanJum!=null) {
		    	       				StringTokenizer st2 = new StringTokenizer(listAngDanJum);
	    	       					if(st2.countTokens()==3) {
		    	       					listAngDanJum = "";
	    	       					}
	    	       				}
	    	       				*/
	       	%>
	       		<td style="color:#000;text-align:center">
	       			<div style="color:red;font-weight:bold"><%=counter %></div>
	       			<div style="color:#CB8502;font-weight:bold"><%=counterL %></div>
	       			<div style="color:red;font-weight:bold"><%= listAngDanJum %></div>
	       			<div style="color:#CB8502;font-weight:bold"><%=listAngDanJum1%></div>
	       		</td>
	       	
			<%
			//==========================================================END 1=====================================================
							}
	       	%>
	       	</tr>
	       	<%				
						}	
						else {
			//==========================================================2=====================================================
							//prev semes == current		
							prev_idkmk = ""+curr_idkmk;	
							prev_kdkmk = ""+curr_kdkmk;	
							prev_nakmk = ""+curr_nakmk;	
							prev_skstm = ""+curr_skstm;	
							prev_skspr = ""+curr_skspr;	
							prev_skslp = ""+curr_skslp;	
							prev_kdwpl = ""+curr_kdwpl;	
							prev_jenis = ""+curr_jenis;	
							prev_stkmk = ""+curr_stkmk;	
							prev_nodos = ""+curr_nodos;	
							prev_semes = ""+curr_semes;	
			%>
	       	<tr>
	       		<td style="color:#000;text-align:LEFT"><div style="font-weight:bold;font-size:1.3em"><%=prev_kdkmk %></div><%=prev_nakmk %></td>
	       	<%
	       					st = new StringTokenizer(listShift,"#");
	       					while(st.hasMoreTokens()) {
				       			String uniqKeter = st.nextToken();
	       						String shift = st.nextToken();
	       						String hari = st.nextToken();
	       						String tkn_kdjen = st.nextToken();
	       						String keterKonversi = st.nextToken();
		       			
			       				ListIterator litmp = vNotTakenByShift.listIterator();
	    	   					int counter = 0;
	       						while(litmp.hasNext()) {
	       				//prev_smawl+"||"+prev_shift+"||"+prev_npmhs+"||"+prev_idkur+"||"+keterKonversi+"||"+tot
	       						String brs = (String)litmp.next();
	       							if(brs.contains(keterKonversi)) {
		       							counter++;
	       							}
	       						}
	       			
			       				litmp = vNotLulusByShift.listIterator();
	    		   				int counterL = 0;
	       						while(litmp.hasNext()) {
		       				//prev_smawl+"||"+prev_shift+"||"+prev_npmhs+"||"+prev_idkur+"||"+keterKonversi+"||"+tot
	       							String brs = (String)litmp.next();
	       							if(brs.contains(keterKonversi)) {
		       							counterL++;
	       							}
	       						}
	       						
	       						
	       					//sebaran angkatan belum ambil
	       		       			litmp = vNotTakenBySmawl.listIterator();
	       		       			//20131||REGULER PAGI||8888813100031||11||REGULER PAGI||14
	       		       			//int counterTknAng = 0;
	       		       			boolean first = true;
	       		       			String prev_smawl = "";
	       		       			String tot = "0";
	       		       			String listNpm="";
	       		       			String listAngDanJum = "";
	       		       			while(litmp.hasNext()) {
	       		       				//prev_smawl+"||"+prev_shift+"||"+prev_npmhs+"||"+prev_idkur+"||"+keterKonversi+"||"+tot
	       		       				String brs = (String)litmp.next();
	       		       				
	       		       				
	       		       				if(brs.contains(keterKonversi)) {
	       		       					//System.out.println("baris =="+brs);
	       		       					StringTokenizer stt = new StringTokenizer(brs,"||");
	       		       					if(first) {
	       		       						first = false;
	       		       						prev_smawl = stt.nextToken();
	       		       						stt.nextToken();
	       		       						listNpm=listNpm+","+stt.nextToken();
	       		       						stt.nextToken();
	       		       						stt.nextToken();
	       		       						tot = stt.nextToken();
	       		       						listAngDanJum = ""+prev_smawl;
	       		       					}
	       		       					else {
	       		       						String curr_smawl = stt.nextToken();
	       		       						if(curr_smawl.equalsIgnoreCase(prev_smawl)) {
	       		       							stt.nextToken();
	       		       							listNpm=listNpm+","+stt.nextToken();
	       			       						stt.nextToken();
	       			       						stt.nextToken();
	       			       						tot = stt.nextToken();
	       		       						}
	       		       						else {
	       		       							//listAngDanJum=listAngDanJum+"="+tot;
	       		       							if(Integer.valueOf(tot).intValue()>0) {
	       		       								listAngDanJum=listAngDanJum+"=<a href=\"prep.tampletInfoBasedListNpm?smawl="+prev_smawl+"&kdpst="+kdpst+"&nmpst="+nmpst+"&listNpm="+listNpm+"\">"+tot+"</a>";
	       		       							}
	       		       							listNpm="";
	       		       							prev_smawl = ""+curr_smawl;
	       		       							listAngDanJum=listAngDanJum+" "+prev_smawl;
	       		       							stt.nextToken();
	       		       							listNpm=listNpm+","+stt.nextToken();
	       			       						stt.nextToken();
	       			       						stt.nextToken();
	       			       						tot = stt.nextToken();
	       		       						}
	       		       						
	       		       					}
	       		       					if(!litmp.hasNext()) {
	       		       						//listAngDanJum=listAngDanJum+"="+tot;
	       		       						if(Integer.valueOf(tot).intValue()>0) {
	       		       							listAngDanJum=listAngDanJum+"=<a href=\"prep.tampletInfoBasedListNpm?smawl="+prev_smawl+"&kdpst="+kdpst+"&nmpst="+nmpst+"&listNpm="+listNpm+"\">"+tot+"</a>";
	       		       						}
	       		       						listNpm="";
	       		       					}
	       		       					//counterTknAng++;
	       		       				}
	       		       				else {
	    	       						if(!litmp.hasNext()) {
	    	       							//listAngDanJum=listAngDanJum+"="+tot;
	    	       							if(Integer.valueOf(tot).intValue()>0) {
	    	       								listAngDanJum=listAngDanJum+"=<a href=\"prep.tampletInfoBasedListNpm?smawl="+prev_smawl+"&kdpst="+kdpst+"&nmpst="+nmpst+"&listNpm="+listNpm+"\">"+tot+"</a>";
	    	       							}
	    	       							listNpm="";
	    	       						}
	    	       					}
	       		       			}
	       		       			
	       		       			
	       		       	//sebaran angkatan belum lulus
	       		       			litmp = vNotLulusBySmawl.listIterator();
	       		       			//20131||REGULER PAGI||8888813100031||11||REGULER PAGI||14
	       		       			//int counterTknAng = 0;
	       		       			first = true;
	       		       			listNpm="";
	       		       			prev_smawl = "";
	       		       			tot = "0";
	       		       			String listAngDanJum1 = "";
	       		       			while(litmp.hasNext()) {
	       		       				//prev_smawl+"||"+prev_shift+"||"+prev_npmhs+"||"+prev_idkur+"||"+keterKonversi+"||"+tot
	       		       				String brs = (String)litmp.next();
	       		       				//System.out.println("baris =="+brs);
	       		       				
	       		       				if(brs.contains(keterKonversi)) {
	       		       					StringTokenizer stt = new StringTokenizer(brs,"||");
	       		       					if(first) {
	       		       						first = false;
	       		       						prev_smawl = stt.nextToken();
	       		       						stt.nextToken();
	       		       						listNpm=listNpm+","+stt.nextToken();
	       		       						stt.nextToken();
	       		       						stt.nextToken();
	       		       						tot = stt.nextToken();
	       		       						listAngDanJum1 = ""+prev_smawl;
	       		       						//if(!litmp.hasNext()) {
	       		       						//	listAngDanJum1=listAngDanJum1+"="+tot;
	       			       					//}
	       		       					}
	       		       					else {
	       		       						String curr_smawl = stt.nextToken();
	       		       						if(curr_smawl.equalsIgnoreCase(prev_smawl)) {
	       		       							stt.nextToken();
	       		       							listNpm=listNpm+","+stt.nextToken();
	       			       						stt.nextToken();
	       			       						stt.nextToken();
	       			       						tot = stt.nextToken();
	       			       					}
	       		       						else {
	       		       							//listAngDanJum1=listAngDanJum1+"="+tot;
	       		       							if(Integer.valueOf(tot).intValue()>0) {
	       		       								listAngDanJum1=listAngDanJum1+"=<a href=\"prep.tampletInfoBasedListNpm?smawl="+prev_smawl+"&kdpst="+kdpst+"&nmpst="+nmpst+"&listNpm="+listNpm+"\">"+tot+"</a>";
	       		       							}
	       		       							listNpm="";
	       		       							prev_smawl = ""+curr_smawl;
	       		       							listAngDanJum1=listAngDanJum1+" "+prev_smawl;
	       		       							stt.nextToken();
	       		       							listNpm=listNpm+","+stt.nextToken();
	       			       						stt.nextToken();
	       			       						stt.nextToken();
	       			       						tot = stt.nextToken();
	       			       					}
	       		       						
	       		       					}
	       		       					if(!litmp.hasNext()) {
	       		       						//listAngDanJum1=listAngDanJum1+"="+tot;
	       		       						if(Integer.valueOf(tot).intValue()>0) {
	       		       							listAngDanJum1=listAngDanJum1+"=<a href=\"prep.tampletInfoBasedListNpm?smawl="+prev_smawl+"&kdpst="+kdpst+"&nmpst="+nmpst+"&listNpm="+listNpm+"\">"+tot+"</a>";
	       		       						}
	       		       						listNpm="";
	       		       					}
	       	   						}
	       		       				else {
	       		       					if(!litmp.hasNext()) {
	       		       						//listAngDanJum1=listAngDanJum1+"="+tot;
	       		       						if(Integer.valueOf(tot).intValue()>0) {
	       		       							listAngDanJum1=listAngDanJum1+"=<a href=\"prep.tampletInfoBasedListNpm?smawl="+prev_smawl+"&kdpst="+kdpst+"&nmpst="+nmpst+"&listNpm="+listNpm+"\">"+tot+"</a>";
	       		       						}
	       		       						listNpm="";
	       		       					}
	       		       				}
	       		       			}
	       		       			/*
	       		       			if(listAngDanJum1!=null) {
		    	       				StringTokenizer st2 = new StringTokenizer(listAngDanJum1);
	    	       					if(st2.countTokens()==3) {
		    	       					listAngDanJum1 = "";
	    	       					}	
	    	       				}
	    	       				if(listAngDanJum!=null) {
		    	       				StringTokenizer st2 = new StringTokenizer(listAngDanJum);
	    	       					if(st2.countTokens()==3) {
		    	       					listAngDanJum = "";
	    	       					}
	    	       				}
	    	       				*/
	       	%>
	       		<td style="color:#000;text-align:center">
	       			<div style="color:red;font-weight:bold"><%=counter %></div>
	       			<div style="color:#CB8502;font-weight:bold"><%=counterL %></div>
	       			<div style="color:red;font-weight:bold"><%= listAngDanJum %></div>
	       			<div style="color:#CB8502;font-weight:bold"><%=listAngDanJum1 %></div>
	       		</td>
	
			<%
			//==========================================================END 2=====================================================				
	       					}	
	       	%>
	      	</tr>
	       <%				
       					}
						if(!li.hasNext()) {
			%>
		</table>	
			<%
						}
	       			}//end while
	       		}//end else
			}// end if		
		}
		%>
		</form>
		<!-- Column 1 start -->
	</div>
</div>			
</body>
</html>