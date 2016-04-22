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
%>

</head>
<body>
<div id="header">
<%@ include file="krsKhsSubMenu.jsp" %>
<%
/* yang bisa di edit hanya @ thsms krs
*  atau list dari whitelist
*/ 
//Checker.getIsOperatorAllowEditNilaiAllThsms()
String thsms_krs = Checker.getThsmsKrs();
boolean allowEditAllThsms = Checker.getIsOperatorAllowEditNilaiAllThsms(); //sudah tidak digunakan
SearchDb sdb = new SearchDb();
String nmpst = sdb.getNmpst(kdpst);
String idkur = sdb.getIndividualKurikulum(kdpst, npm);
Vector vMk=null;
if(idkur!=null) {
	vMk = sdb.getListMatakuliahDalamKurikulum(kdpst,idkur);
}	
Vector vSelectMk = new Vector();
ListIterator liSmk = vSelectMk.listIterator();
if(vMk!=null && vMk.size()>0) {
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
		if(msg!=null) {
			//kurikulum blm ditentukan
			Vector v_cf = validUsr.getScopeUpd7des2012("hasEditMkKurikulum");
			if(v_cf.size()>0) {
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
		if(allowViewKrs) {
				
				
			Vector vHistKrsKhs = (Vector)request.getAttribute("vHistKrsKhs");
				
				
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
				String prev_cuid ="";
				String prev_npmdos ="";
				String prev_nodos ="";
				String prev_npmasdos ="";
				String prev_noasdos ="";
				String prev_nmmdos ="";
				String prev_nmmasdos =""; 
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
					String cuid =(String)lih.next();
					String npmdos =(String)lih.next();
					String nodos =(String)lih.next();
					String npmasdos =(String)lih.next();
					String noasdos =(String)lih.next();
					String nmmdos =(String)lih.next();
					String nmmasdos =(String)lih.next(); 
					
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
					prev_kodeKampus = ""+kodeKampus;
					prev_cuid =""+cuid;
					prev_npmdos =""+npmdos;
					prev_nodos =""+nodos;
					prev_npmasdos =""+npmasdos;
					prev_noasdos =""+noasdos;
					prev_nmmdos =""+nmmdos;
					prev_nmmasdos =""+nmmasdos;	 
						//System.out.println("kdkmk="+kdkmk);
						//System.out.println("cuid="+cuid);
					if(prev_thsms.equalsIgnoreCase(thsms_krs)) {
					%>
					<form action="proses.updKlsKrs" method="post" >	
					<%	
					}
						%>
					<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:750px">
        			<tr>
        			<%
        			session.setAttribute("vHistoKrs", vHistKrsKhs);
        				%>
        				<input type="hidden" name="thsmsKrs" value="<%=prev_thsms %>" />
        				<input type="hidden" name="id_obj" value="<%=objId %>" />
        				<input type="hidden" name="nmm" value="<%=nmm %>" />
        				<input type="hidden" name="npm" value="<%=npm %>" />
        				<input type="hidden" name="kdpst" value="<%=kdpst %>" />
        				<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
        				
        				<td style="background:#369;color:#fff;text-align:center" colspan="3"><label><B><%=Converter.convertThsmsKeterOnly(prev_thsms) %></B> </label></td>
        			</tr>
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center;width:60px"><label><B>KODE</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:left;width:300px"><label><B>MATAKULIAH</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:center;width:390px"><label><B>KELAS</B> </label></td></td>
        				
        			</tr>
        			<tr>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_kdkmk %></B> </label></td></td>
        				<td style="color:#000;text-align:left;"><label><B><%=prev_nakmk %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B>
        			<%
        			if(prev_thsms.equalsIgnoreCase(thsms_krs)) {
        			%>	
        				<select name="kls_info" style="width:99%">
        			<%		
        			}
        			else {
        				%>	
        				<select name="kls_info" style="width:99%" disabled>
        			<%	
        			}
        			
        			StringTokenizer st = new StringTokenizer(prev_cuid,"`");
        			if(st.countTokens()<=1) {
        					//ngga ada kelas yg dibuka, harusnya ngga mungkin bisa begini kalo udah pake CLASSPOOL
        				%>
        					<option value="null">TIDAK ADA PILIHAN KELAS</option>
        				<%	
        			}
        			else {
        				if (st.hasMoreTokens()) {
        						//cuid+"`"+kdpst_+"`"+shift_+"`"+nopll_+"`"+npmdos_+"`"+nodos_+"`"+npmasdos_+"`"+noasdos_+"`"+kmp_+"`"+nmmdos_+"`"+nmmasdos_+"`"+sub_keter_kdkmk_+"`"+cuid_;
        						//dimana CUID tkn pertama adalah init cuid
        					String cuid_awal=st.nextToken();
        					do {
    							String kdpst_=st.nextToken();
        						String shift_=st.nextToken();
        						String nopll_=st.nextToken();
        						String npmdos_=st.nextToken();
        						String nodos_=st.nextToken();
        						String npmasdos_=st.nextToken();
        						String noasdos_=st.nextToken();
        						String kmp_=st.nextToken();
        						String nmmdos_=st.nextToken();
        						String nmmasdos_=st.nextToken();
        						String sub_keter_kdkmk_=st.nextToken();
        						String cuid_=st.nextToken();
        						if(cuid_.equalsIgnoreCase(cuid_awal)) {	
            	            			%>
            	            <option value="<%=cuid_awal+"`"+kdpst_+"`"+shift_+"`"+nopll_+"`"+npmdos_+"`"+nodos_+"`"+npmasdos_+"`"+noasdos_+"`"+kmp_+"`"+nmmdos_+"`"+nmmasdos_+"`"+sub_keter_kdkmk_+"`"+cuid_%>" title="Prodi: <%=Converter.getNamaKdpst(kdpst_)+", Dosen: "+nmmdos_+", Shift: "+ shift_+" ("+nopll_+")" %>" selected="selected"><%=nmmdos_+" ("+shift_+" - "+nopll_+") - "+Converter.getNamaKdpst(kdpst_) %></option>
            	                    	<%				
            	            	}
            	            	else {
            	        				%>
            	        	<option value="<%=cuid_awal+"`"+kdpst_+"`"+shift_+"`"+nopll_+"`"+npmdos_+"`"+nodos_+"`"+npmasdos_+"`"+noasdos_+"`"+kmp_+"`"+nmmdos_+"`"+nmmasdos_+"`"+sub_keter_kdkmk_+"`"+cuid_%>" title="Prodi: <%=Converter.getNamaKdpst(kdpst_)+", Dosen: "+nmmdos_+", Shift: "+ shift_+" ("+nopll_+")" %>"><%=nmmdos_+" ("+shift_+" - "+nopll_+") - "+Converter.getNamaKdpst(kdpst_) %></option>
            	        				<%
            	            	}        					
        					}
        					while(st.hasMoreTokens());
        				}	
        			}
        				%>
        				</select>
        				</B> </label></td></td>
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
						cuid =(String)lih.next();
						npmdos =(String)lih.next();
						nodos =(String)lih.next();
						npmasdos =(String)lih.next();
						noasdos =(String)lih.next();
						nmmdos =(String)lih.next();
						nmmasdos =(String)lih.next(); 

						
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
							prev_cuid =""+cuid;
							prev_npmdos =""+npmdos;
							prev_nodos =""+nodos;
							prev_npmasdos =""+npmasdos;
							prev_noasdos =""+noasdos;
							prev_nmmdos =""+nmmdos;
							prev_nmmasdos =""+nmmasdos; 	
								
							%>
					<tr>
						<td style="color:#000;text-align:center;"><label><B><%=prev_kdkmk %></B> </label></td></td>
        				<td style="color:#000;text-align:left;"><label><B><%=prev_nakmk %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B>
        				<%
        					if(prev_thsms.equalsIgnoreCase(thsms_krs)) {
        			%>	
        				<select name="kls_info" style="width:99%">
        			<%		
        					}
        					else {
        				%>	
        				<select name="kls_info" style="width:99%" disabled>
        			<%	
        					}
        				
        					st = new StringTokenizer(prev_cuid,"`");
        					if(st.countTokens()<=1) {
        					//ngga ada kelas yg dibuka, harusnya ngga mungkin bisa begini kalo udah pake CLASSPOOL
        				%>
        					<option value="null">TIDAK ADA PILIHAN KELAS</option>
        				<%	
        					}
        					else {
        						if (st.hasMoreTokens()) {
        						//cuid+"`"+kdpst_+"`"+shift_+"`"+nopll_+"`"+npmdos_+"`"+nodos_+"`"+npmasdos_+"`"+noasdos_+"`"+kmp_+"`"+nmmdos_+"`"+nmmasdos_+"`"+sub_keter_kdkmk_+"`"+cuid_;
        						//dimana CUID tkn pertama adalah init cuid
        							String cuid_awal=st.nextToken();
        							do {
        								String kdpst_=st.nextToken();
            							String shift_=st.nextToken();
            							String nopll_=st.nextToken();
            							String npmdos_=st.nextToken();
            							String nodos_=st.nextToken();
            							String npmasdos_=st.nextToken();
            							String noasdos_=st.nextToken();
            							String kmp_=st.nextToken();
            							String nmmdos_=st.nextToken();
            							String nmmasdos_=st.nextToken();
            							String sub_keter_kdkmk_=st.nextToken();
            							String cuid_=st.nextToken();
            							if(cuid_.equalsIgnoreCase(cuid_awal)) {
            	            			%>
            	          	<option value="<%=cuid_awal+"`"+kdpst_+"`"+shift_+"`"+nopll_+"`"+npmdos_+"`"+nodos_+"`"+npmasdos_+"`"+noasdos_+"`"+kmp_+"`"+nmmdos_+"`"+nmmasdos_+"`"+sub_keter_kdkmk_+"`"+cuid_%>" title="Prodi: <%=Converter.getNamaKdpst(kdpst_)+", Dosen: "+nmmdos_+", Shift: "+ shift_+" ("+nopll_+")" %>" selected="selected"><%=nmmdos_+" ("+shift_+" - "+nopll_+") - "+Converter.getNamaKdpst(kdpst_) %></option>
            	                    	<%				
            	            			}
            	            			else {
            	        				%>
            	        	<option value="<%=cuid_awal+"`"+kdpst_+"`"+shift_+"`"+nopll_+"`"+npmdos_+"`"+nodos_+"`"+npmasdos_+"`"+noasdos_+"`"+kmp_+"`"+nmmdos_+"`"+nmmasdos_+"`"+sub_keter_kdkmk_+"`"+cuid_%>" title="Prodi: <%=Converter.getNamaKdpst(kdpst_)+", Dosen: "+nmmdos_+", Shift: "+ shift_+" ("+nopll_+")" %>"><%=nmmdos_+" ("+shift_+" - "+nopll_+") - "+Converter.getNamaKdpst(kdpst_) %></option>
            	        				<%
            	            			}
        							}
        							while(st.hasMoreTokens());
        						}	
        					}
        				%>
        				</select>
        				</B> </label></td></td>
        			</tr>
        			<%
						}
						else {
							if(!prev_thsms.equalsIgnoreCase(thsms)) {
								//ganti tahun
								//1. tutup table sebelumnya dengan ngasih nilai ipk dan ips
					//if(allowEditKrs && allowBatalTambahMk && (prev_thsms.equalsIgnoreCase(thsms_krs)||allowEditAllThsms)) {
								if(prev_thsms.equalsIgnoreCase(thsms_krs)) {
									
					%>
					<tr>
						<td colspan="3" style="background:#369;padding:5px 5px">
							<input type="submit" value="Update Kelas" style="width:99%;height:30px" />
						</td>
					</tr>	
					<%	
								}
				
	        		%>	
				
					<!--  
					peralihan ganti table thsms 
					-->
        		</table>
        		<%
        						if(prev_thsms.equalsIgnoreCase(thsms_krs)) {
					%>
				</form>	
					<%	
								}
				%>
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
								prev_kodeKampus =""+kodeKampus;
								prev_cuid =""+cuid;
								prev_npmdos =""+npmdos;
								prev_nodos =""+nodos;
								prev_npmasdos =""+npmasdos;
								prev_noasdos =""+noasdos;
								prev_nmmdos =""+nmmdos;
								prev_nmmasdos =""+nmmasdos; 
						//System.out.println("kdkmk="+kdkmk);
						//System.out.println("cuid="+cuid);
					%>
				<%
        						if(prev_thsms.equalsIgnoreCase(thsms_krs)) {
					%>
				<form action="proses.updKlsKrs" method="post" >	
					<%	
								}
				%>	
				<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:750px">
        			<tr>
        			<%
        						session.setAttribute("vHistoKrs", vHistKrsKhs);
        				%>
        				<input type="hidden" name="id_obj" value="<%=objId %>" />
        				<input type="hidden" name="nmm" value="<%=nmm %>" />
        				<input type="hidden" name="npm" value="<%=npm %>" />
        				<input type="hidden" name="kdpst" value="<%=kdpst %>" />
        				<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
        				<input type="hidden" name="thsmsKrs" value="<%=prev_thsms %>" />
        				<td style="background:#369;color:#fff;text-align:center" colspan="3"><label><B><%=Converter.convertThsmsKeterOnly(prev_thsms) %></B> </label></td>
        			</tr>
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center;width:60px"><label><B>KODE</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:left;width:300px"><label><B>MATAKULIAH</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:center;width:390px"><label><B>KELAS</B> </label></td></td>
        			
        			</tr>
        			<tr>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_kdkmk %></B> </label></td></td>
        				<td style="color:#000;text-align:left;"><label><B><%=prev_nakmk %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B>
        				<%
        							if(prev_thsms.equalsIgnoreCase(thsms_krs)) {
        			%>	
        				<select name="kls_info" style="width:99%">
        			<%		
        							}
        							else {
        				%>	
        				<select name="kls_info" style="width:99%" disabled>
        			<%	
        							}
        							st = new StringTokenizer(prev_cuid,"`");
        							if(st.countTokens()<=1) {
        					//ngga ada kelas yg dibuka, harusnya ngga mungkin bisa begini kalo udah pake CLASSPOOL
        				%>
        					<option value="null">TIDAK ADA PILIHAN KELAS</option>
        				<%	
        							}
        							else {
        								if (st.hasMoreTokens()) {
        						//cuid+"`"+kdpst_+"`"+shift_+"`"+nopll_+"`"+npmdos_+"`"+nodos_+"`"+npmasdos_+"`"+noasdos_+"`"+kmp_+"`"+nmmdos_+"`"+nmmasdos_+"`"+sub_keter_kdkmk_+"`"+cuid_;
        						//dimana CUID tkn pertama adalah init cuid
        									String cuid_awal=st.nextToken();
        									do {
        										String kdpst_=st.nextToken();
        										String shift_=st.nextToken();
        	            						String nopll_=st.nextToken();
        	            						String npmdos_=st.nextToken();
        	            						String nodos_=st.nextToken();
        	            						String npmasdos_=st.nextToken();
        	            						String noasdos_=st.nextToken();
        	            						String kmp_=st.nextToken();
        	            						String nmmdos_=st.nextToken();
        	            						String nmmasdos_=st.nextToken();
        	            						String sub_keter_kdkmk_=st.nextToken();
        	            						String cuid_=st.nextToken();
        	            						if(cuid_.equalsIgnoreCase(cuid_awal)) {
            			%>
                    		<option value="<%=cuid_awal+"`"+kdpst_+"`"+shift_+"`"+nopll_+"`"+npmdos_+"`"+nodos_+"`"+npmasdos_+"`"+noasdos_+"`"+kmp_+"`"+nmmdos_+"`"+nmmasdos_+"`"+sub_keter_kdkmk_+"`"+cuid_%>" title="Prodi: <%=Converter.getNamaKdpst(kdpst_)+", Dosen: "+nmmdos_+", Shift: "+ shift_+" ("+nopll_+")" %>" selected="selected"><%=nmmdos_+" ("+shift_+" - "+nopll_+") - "+Converter.getNamaKdpst(kdpst_) %></option>
                    	<%				
            									}
            									else {
        				%>
        					<option value="<%=cuid_awal+"`"+kdpst_+"`"+shift_+"`"+nopll_+"`"+npmdos_+"`"+nodos_+"`"+npmasdos_+"`"+noasdos_+"`"+kmp_+"`"+nmmdos_+"`"+nmmasdos_+"`"+sub_keter_kdkmk_+"`"+cuid_%>" title="Prodi: <%=Converter.getNamaKdpst(kdpst_)+", Dosen: "+nmmdos_+", Shift: "+ shift_+" ("+nopll_+")" %>"><%=nmmdos_+" ("+shift_+" - "+nopll_+") - "+Converter.getNamaKdpst(kdpst_) %></option>
        				<%
            									}
        					
        									}
        									while(st.hasMoreTokens());
        								}	
        							}
        				%>
        				</select>
        				</B> </label></td></td>
        			</tr>
        		<% 	
								}
							}
						}//end while
					/*
					*tutup table setelah end while
					*/
					//if(allowEditKrs && allowBatalTambahMk && (prev_thsms.equalsIgnoreCase(thsms_krs)||allowEditAllThsms)) {	
				
        				if(prev_thsms.equalsIgnoreCase(thsms_krs)) {
					%>
					<tr>
						<td colspan="3" style="background:#369;padding:5px 5px">
							<input type="submit" value="Update Kelas" style="width:99%;height:30px" />
						</td>
					</tr>	
					<%	
						}
				
	        		%>	
					
        		</table>
        		<%
        				if(prev_thsms.equalsIgnoreCase(thsms_krs)) {
					%>
				</form>	
					<%	
						}
				%>
        		<%
					}
				}
				else {
					
				%>
				<h1 style="text-align:center">BELUM ADA REKORD KRS/KHS</h1>
				<%
				}
			}//end if(size>0)
			else {
				%>
				<h2 align="center"><b>Anda Tidak Mempunyai Hak Akses Untuk Data ini</b></h2>
				<%
			}
		//cretae session forceto
		//String forceTo = "get.histKrs?id_obj="+v_id_obj+"&nmm="+v_nmmhs+"&npm="+v_npmhs+"&obj_lvl="+v_obj_lvl+"&kdpst="+v_kdpst+"&cmd=editKrs";
		session.removeAttribute("forceBackTo");
		
		
		%>
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>