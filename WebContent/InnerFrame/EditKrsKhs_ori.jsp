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
String thsms_krs = Checker.getThsmsKrs();
boolean allowEditAllThsms = Checker.getIsOperatorAllowEditNilaiAllThsms();
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
		//System.out.println("sampe sini");
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
				//System.out.println("size1 ="+vHistKrsKhs.size());
				Vector vTrnlp = (Vector)request.getAttribute("vTrnlp");
				//System.out.println("size1a ="+vTrnlp.size());
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
					%>
					<P>
					<table align="center" border="1px" bordercolor="#29A329" style="background:#D6EB99;color:#000;width:700px">
        			<tr>
        				<td style="background:#29A329;color:#fff;text-align:center" colspan="5"><label><B>MATAKULIAH PINDAHAN</B> </label></td></td>
        			</tr>
        			<tr>
        				<td style="background:#29A329;color:#fff;text-align:center;width:60px"><label><B>KODE</B> </label></td></td>
        				<td style="background:#29A329;color:#fff;text-align:left;width:495px"><label><B>MATAKULIAH</B> </label></td></td>
        				<td style="background:#29A329;color:#fff;text-align:center;width:55px"><label><B>NILAI</B> </label></td></td>
        				<td style="background:#29A329;color:#fff;text-align:center;width:55px"><label><B>BOBOT</B> </label></td></td>
        				<td style="background:#29A329;color:#fff;text-align:center;width:35px"><label><B>SKS</B> </label></td></td>
        			</tr>
        			<tr>
        			<%
        			if(allowEditKrs && (thsms.equalsIgnoreCase(thsms_krs)||allowEditAllThsms)) {
        				msg = request.getParameter("msg");
        				objId = request.getParameter("id_obj");
        				nmm = request.getParameter("nmm");
        				npm = request.getParameter("npm");
        				kdpst = request.getParameter("kdpst");
        				obj_lvl =  request.getParameter("obj_lvl");
        			%>	
        				<!-- form action="proses.editKrsKhsTrnlp"  -->
        				<form action="pass.defaultVprofileRoute" >
        					<input type="hidden" name="fwdTo" value="<%=Constants.getRootWeb()+"" %>${PageContext.ServletContext.ContextPath}/InnerFrame/krsPreviewEdit.jsp" />
        					<input type="hidden" name="id_obj" value="<%=objId %>" />
        					<input type="hidden" name="nmm" value="<%=nmm %>" />
        					<input type="hidden" name="npm" value="<%=npm %>" />
        					<input type="hidden" name="kdpst" value="<%=kdpst %>" />
        					<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
        					
        					<input type="hidden" name="kdkmk" value="<%=kdkmk %>" />
        					<input type="hidden" name="nakmk" value="<%=nakmk %>" />
        					<input type="hidden" name="nlakh" value="<%=nlakh %>" />
        					<input type="hidden" name="bobot" value="<%=bobot %>" />
        					<input type="hidden" name="sksmk" value="<%=sksmk %>" />
        					<input type="hidden" name="krs_pindahan" value="krs_pindahan" />
        					<input type="hidden" name="cmd" value="editKrs" />
        			
        			<%
        			}
        			%>	
        				<td style="color:#000;text-align:center;"><label><B><%=kdkmk %></B> </label></td></td>
        				<td style="color:#000;text-align:left;"><label><B><%=nakmk %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=nlakh %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=bobot %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=sksmk %></B> </label></td></td>
        			<%
        				if(msg==null && thsms.equalsIgnoreCase(thsms_krs)) {
        			%>
        				<td style="color:#000;text-align:center;"><label><input type="submit" value="edit" name="edit"/></label></td></td>
        			<%
        				}
        			if(allowEditKrs && (thsms.equalsIgnoreCase(thsms_krs)||allowEditAllThsms)) {
        			%>	
        				</form>
        			<%
        			}
        			%>	
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
					<%
        			if(allowEditKrs && (thsms.equalsIgnoreCase(thsms_krs)||allowEditAllThsms)) {
        				
        			%>	
        			
        				<!--  form action="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/krsPreviewEdit.jsp" -->
        				<form action="pass.defaultVprofileRoute" >
        					<input type="hidden" name="fwdTo" value="<%=Constants.getRootWeb()+"" %>${PageContext.ServletContext.ContextPath}/InnerFrame/krsPreviewEdit.jsp" />
        					<input type="hidden" name="id_obj" value="<%=objId %>" />
        					<input type="hidden" name="nmm" value="<%=nmm %>" />
        					<input type="hidden" name="npm" value="<%=npm %>" />
        					<input type="hidden" name="kdpst" value="<%=kdpst %>" />
        					<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
        					
        					<input type="hidden" name="kdkmk" value="<%=kdkmk %>" />
        					<input type="hidden" name="nakmk" value="<%=nakmk %>" />
        					<input type="hidden" name="nlakh" value="<%=nlakh %>" />
        					<input type="hidden" name="bobot" value="<%=bobot %>" />
        					<input type="hidden" name="sksmk" value="<%=sksmk %>" />
        					<input type="hidden" name="krs_pindahan" value="krs_pindahan" />
        					
        					<input type="hidden" name="cmd" value="editKrs" />
        			<%
        			}
        			%>
        				<td style="color:#000;text-align:center;"><label><B><%=kdkmk %></B> </label></td></td>
        				<td style="color:#000;text-align:left;"><label><B><%=nakmk %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=nlakh %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=bobot %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=sksmk %></B> </label></td></td>
        			<%
        				if(msg==null && thsms.equalsIgnoreCase(thsms_krs)) {
        			%>	
        				<td style="color:#000;text-align:center;"><label><input type="submit" value="edit" name="edit"/></label></td></td>
        			<%
        				}
        			if(allowEditKrs && (thsms.equalsIgnoreCase(thsms_krs)||allowEditAllThsms)) {
        			%>	
        				</form>
        			<%
        			}
        			%>
        			</tr>
        			<%		
						}
					//tutuup table
					%>
					<tr>
        				<td style="background:#29A329;color:#fff;text-align:center;" colspan="4"><label><B>TOTAL MATAKULIAH TRANSFER / PINDAHAN</B> </label></td></td>
        				<td style="background:#29A329;color:#fff;text-align:center;"><label><B><%=totSksTransfer %></B> </label></td></td>
        			</tr>
        			</table>
        			</P>
        			<br />
					<%
					}	
				}
				
				
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
						System.out.println("kdkmk="+kdkmk);
						System.out.println("cuid="+cuid);
						%>
					
					<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:750px">
        			<tr>
        			<%
        			if(allowEditKrs && (thsms.equalsIgnoreCase(thsms_krs)||allowEditAllThsms)) {
        			%>
        				<form action="go.prepUpdKrsPerThsms">
        			<%
        			}
        				session.setAttribute("vHistoKrs", vHistKrsKhs);
        				%>
        				<input type="hidden" name="thsmsKrs" value="<%=prev_thsms %>" />
        				<input type="hidden" name="id_obj" value="<%=objId %>" />
        				<input type="hidden" name="nmm" value="<%=nmm %>" />
        				<input type="hidden" name="npm" value="<%=npm %>" />
        				<input type="hidden" name="kdpst" value="<%=kdpst %>" />
        				<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
        				
        				<td style="background:#369;color:#fff;text-align:center" colspan="7"><label><B><%=Converter.convertThsmsKeterOnly(prev_thsms) %></B> </label></td>
        			<%
        			if(allowEditKrs && (thsms.equalsIgnoreCase(thsms_krs)||allowEditAllThsms)) {
        			%>	
        				<td style="background:#369;color:#fff;text-align:center" rowspan="2">
        				<input type="submit" value="edit" style="height:45px;width:99%"/>
        				</td>
        				</form>
        			<%
        			}
        			%>	
        			</tr>
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center;width:60px"><label><B>KODE</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:left;width:400px"><label><B>MATAKULIAH</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:center;width:55px"><label><B>NILAI</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:center;width:55px"><label><B>BOBOT</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:center;width:35px"><label><B>SKS</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:center;width:55px"><label><B>KELAS</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:center;width:90px"><label><B>SHIFT</B> </label></td></td>
        			</tr>
        			<tr>
        			<%
        			if(allowEditKrs && (thsms.equalsIgnoreCase(thsms_krs)||allowEditAllThsms)) {
        			%>	
        				<!--  form action="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/krsPreviewEdit.jsp" -->
        				<form action="pass.defaultVprofileRoute" >
        					<input type="hidden" name="fwdTo" value="<%=Constants.getRootWeb()+"" %>${PageContext.ServletContext.ContextPath}/InnerFrame/krsPreviewEdit.jsp" />
        					<input type="hidden" name="prev_thsms" value="<%=prev_thsms %>" />
        					<input type="hidden" name="id_obj" value="<%=objId %>" />
        					<input type="hidden" name="nmm" value="<%=nmm %>" />
        					<input type="hidden" name="npm" value="<%=npm %>" />
        					<input type="hidden" name="kdpst" value="<%=kdpst %>" />
        					<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
        					
        					<input type="hidden" name="kdkmk" value="<%=kdkmk %>" />
        					<input type="hidden" name="nakmk" value="<%=nakmk %>" />
        					<input type="hidden" name="nlakh" value="<%=nlakh %>" />
        					<input type="hidden" name="bobot" value="<%=bobot %>" />
        					<input type="hidden" name="sksmk" value="<%=sksmk %>" />
        					 
        					<input type="hidden" name="cmd" value="editKrs" />
        			<%
        			}
        			%>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_kdkmk %></B> </label></td></td>
        				<td style="color:#000;text-align:left;"><label><B><%=prev_nakmk %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_nlakh %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_bobot %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_sksmk %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_kelas %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_shift %></B> </label></td></td>
        			<%
        				if(msg==null && (thsms.equalsIgnoreCase(thsms_krs)||allowEditAllThsms)) {
        					if(allowBatalTambahMk){
        			%>	
        				<td style="color:#000;text-align:center;"><B><input type="submit" value="-" name="edit" style="font-size:15px;font-weight:bold;width:94%"/></B></td>
        			<%
        					}
        					else {
        						//&#9744;<td style="color:#000;text-align:center;"><B></B></td>
        			%>	
                		
                	<%
        					}
        				}
        			if(allowEditKrs && (thsms.equalsIgnoreCase(thsms_krs)||allowEditAllThsms)) {
        			%>	
        				</form>
        			<%
        			}
        			%>
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
								System.out.println("kdkmk="+kdkmk);
								System.out.println("cuid="+cuid);
							%>
					<tr>
					<%
        			if(allowEditKrs && (prev_thsms.equalsIgnoreCase(thsms_krs)||allowEditAllThsms)) {
        			%>	
        				<!--  form action="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/krsPreviewEdit.jsp" -->
        				<form action="pass.defaultVprofileRoute" >
        					<input type="hidden" name="fwdTo" value="<%=Constants.getRootWeb()+"" %>${PageContext.ServletContext.ContextPath}/InnerFrame/krsPreviewEdit.jsp" />
        					<input type="hidden" name="prev_thsms" value="<%=prev_thsms %>" />
        					<input type="hidden" name="id_obj" value="<%=objId %>" />
        					<input type="hidden" name="nmm" value="<%=nmm %>" />
        					<input type="hidden" name="npm" value="<%=npm %>" />
        					<input type="hidden" name="kdpst" value="<%=kdpst %>" />
        					<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
        					
        					<input type="hidden" name="kdkmk" value="<%=kdkmk %>" />
        					<input type="hidden" name="nakmk" value="<%=nakmk %>" />
        					<input type="hidden" name="nlakh" value="<%=nlakh %>" />
        					<input type="hidden" name="bobot" value="<%=bobot %>" />
        					<input type="hidden" name="sksmk" value="<%=sksmk %>" />
        					 
        					<input type="hidden" name="cmd" value="editKrs" />
        			<%
        			}
        			%>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_kdkmk %></B> </label></td></td>
        				<td style="color:#000;text-align:left;"><label><B><%=prev_nakmk %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_nlakh %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_bobot %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_sksmk %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_kelas %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_shift %></B> </label></td></td>
        			<%
        				if(msg==null && (prev_thsms.equalsIgnoreCase(thsms_krs)||allowEditAllThsms)) {
        					if(allowBatalTambahMk){
        			%>	
        				<td style="color:#000;text-align:center;"><B><input type="submit" value="-" name="edit" style="font-size:15px;font-weight:bold;width:94%"/></B></td>
        			<%
        					}
        					else {
        						//&#9744;
        			%>	
         
                	<%
        					}
        				}
        			if(allowEditKrs && (prev_thsms.equalsIgnoreCase(thsms_krs)||allowEditAllThsms)) {
        			%>	
        				</form>
        			<%
        			}
        			%>
        			</tr>
        			<%
							}
							else {
								if(!prev_thsms.equalsIgnoreCase(thsms)) {
								//ganti tahun
								//1. tutup table sebelumnya dengan ngasih nilai ipk dan ips
					if(allowEditKrs && allowBatalTambahMk && (prev_thsms.equalsIgnoreCase(thsms_krs)||allowEditAllThsms)) {
					%>
					<form action="proses.updKrs" >
						<input type="hidden" name="thsms" value="<%=prev_thsms %>" />
						<input type="hidden" name="id_obj" value="<%=v_id_obj %>" />
 						<input type="hidden" name="nmm" value="<%=v_nmmhs %>" />
    					<input type="hidden" name="npm" value="<%=v_npmhs %>" />
    					<input type="hidden" name="kdpst" value="<%=v_kdpst %>" />
    					<input type="hidden" name="obj_lvl" value="<%=v_obj_lvl %>" />	
    					 
    					<input type="hidden" name="cmd" value="addMakul" />			
					<!--  tr>
	        			<td style="color:#000;text-align:center;" colspan="7">
	        			<%
	        			if(vSelectMk!=null && vSelectMk.size()>0) {
	        				%>
	        				<select name="makul" style="width:100%;height:25px">
	        					<option value="null" selected="selected">--PILIH MATAKULIAH UNTUK DITAMBAH KE KRS=--</option>
	        				<%
	        				liSmk = vSelectMk.listIterator();
	        				while(liSmk.hasNext()) {
	      String baris = (String)liSmk.next();
	        					StringTokenizer st = new StringTokenizer(baris,",");
	        					String idmk = st.nextToken();
	        					String kdmk = st.nextToken();
	        					String namk = st.nextToken();
	        				%>
	        					<option value="<%=idmk %>">(<%=kdmk %>)-<%=namk %></option>
	        				<%
	        				}
	        				%>
	        				</select>
	        				<%
	        			}
	        			else {
	        				%>
	        				--Tidak Ada Pilihan Matakuliah--
	        				<%
	        			}
	        			%>
	        			</td>
	        			<%
	        			//&& (thsms.equalsIgnoreCase(thsms_krs)||allowEditAllThsms)) {
        				if(msg==null && (prev_thsms.equalsIgnoreCase(thsms_krs)||allowEditAllThsms)) {
        				%>
	        			<td style="color:#000;text-align:center;"><label><input type="submit" value="+" name="add" style="font-size:15px;font-weight:bold;width:94%"/></label></td>
	        			<%
	        			}
	        			%>
	        		</tr> -->
	        		</form>
	        		<%
	        		}
	        		%>
					<!--  
					peralihan ganti table thsms 
					-->
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
						prev_kodeKampus =""+kodeKampus;
						prev_cuid =""+cuid;
						prev_npmdos =""+npmdos;
						prev_nodos =""+nodos;
						prev_npmasdos =""+npmasdos;
						prev_noasdos =""+noasdos;
						prev_nmmdos =""+nmmdos;
						prev_nmmasdos =""+nmmasdos; 
						System.out.println("kdkmk="+kdkmk);
						System.out.println("cuid="+cuid);
					%>
					
				<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:750px">
        			<tr>
        			<%
        			if(allowEditKrs && (thsms.equalsIgnoreCase(thsms_krs)||allowEditAllThsms)) {
        			%>
        				<form action="go.prepUpdKrsPerThsms">
        				<%
        			}	
        				session.setAttribute("vHistoKrs", vHistKrsKhs);
        				%>
        				<input type="hidden" name="id_obj" value="<%=objId %>" />
        				<input type="hidden" name="nmm" value="<%=nmm %>" />
        				<input type="hidden" name="npm" value="<%=npm %>" />
        				<input type="hidden" name="kdpst" value="<%=kdpst %>" />
        				<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
        				<input type="hidden" name="thsmsKrs" value="<%=prev_thsms %>" />
        				<td style="background:#369;color:#fff;text-align:center" colspan="7"><label><B><%=Converter.convertThsmsKeterOnly(prev_thsms) %></B> </label></td>
        			<%
        			if(allowEditKrs && (thsms.equalsIgnoreCase(thsms_krs)||allowEditAllThsms)) {
        			%>
        				<td style="background:#369;color:#fff;text-align:center" rowspan="2">
        				<input type="submit" value="edit" style="height:45px;width:99%"/>
        				</td>
        				</form>
        			<%
        			}
        			%>	
        			</tr>
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center;width:60px"><label><B>KODE</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:left;width:400px"><label><B>MATAKULIAH</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:center;width:55px"><label><B>NILAI</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:center;width:55px"><label><B>BOBOT</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:center;width:35px"><label><B>SKS</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:center;width:55px"><label><B>KELAS</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:center;width:90px"><label><B>SHIFT</B> </label></td></td>
        			</tr>
        			<tr>
        			<%
        			if(allowEditKrs && (thsms.equalsIgnoreCase(thsms_krs)||allowEditAllThsms)) {
        			%>	
        				<!--  form action="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/krsPreviewEdit.jsp" -->
        				<form action="pass.defaultVprofileRoute" >
        					<input type="hidden" name="fwdTo" value="<%=Constants.getRootWeb()+"" %>${PageContext.ServletContext.ContextPath}/InnerFrame/krsPreviewEdit.jsp" />
        					<input type="hidden" name="prev_thsms" value="<%=prev_thsms %>" />
        					<input type="hidden" name="id_obj" value="<%=objId %>" />
        					<input type="hidden" name="nmm" value="<%=nmm %>" />
        					<input type="hidden" name="npm" value="<%=npm %>" />
        					<input type="hidden" name="kdpst" value="<%=kdpst %>" />
        					<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
        					
        					<input type="hidden" name="kdkmk" value="<%=kdkmk %>" />
        					<input type="hidden" name="nakmk" value="<%=nakmk %>" />
        					<input type="hidden" name="nlakh" value="<%=nlakh %>" />
        					<input type="hidden" name="bobot" value="<%=bobot %>" />
        					<input type="hidden" name="sksmk" value="<%=sksmk %>" />
        					 
        					<input type="hidden" name="cmd" value="editKrs" />
        			<%
        			}
        			%>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_kdkmk %></B> </label></td></td>
        				<td style="color:#000;text-align:left;"><label><B><%=prev_nakmk %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_nlakh %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_bobot %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_sksmk %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_kelas %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_shift %></B> </label></td></td>
        			<%
        				if(msg==null && (thsms.equalsIgnoreCase(thsms_krs)||allowEditAllThsms)) {
        					if(allowBatalTambahMk){
        				
        			%>	
        				<td style="color:#000;text-align:center;"><label><input type="submit" value="-" name="edit" style="font-size:15px;font-weight:bold;width:94%"/></label></td>
        			<%
        					}
        					else { //&#9744;
        			%>	
    
                	<%
        					}
        				}
        			if(allowEditKrs && (thsms.equalsIgnoreCase(thsms_krs)||allowEditAllThsms)) {
        			%>	
        				</form>
        			<%
        			}
        			%>
        			</tr>
        		<% 	
								}
							}
						}//end while
					/*
					*tutup table setelah end while
					*/
					if(allowEditKrs && allowBatalTambahMk && (prev_thsms.equalsIgnoreCase(thsms_krs)||allowEditAllThsms)) {	
					%>
					<form action="proses.updKrs" >
						<input type="hidden" name="thsms" value="<%=prev_thsms %>" />
						<input type="hidden" name="id_obj" value="<%=v_id_obj %>" />
    					<input type="hidden" name="nmm" value="<%=v_nmmhs %>" />
    					<input type="hidden" name="npm" value="<%=v_npmhs %>" />
    					<input type="hidden" name="kdpst" value="<%=v_kdpst %>" />
    					<input type="hidden" name="obj_lvl" value="<%=v_obj_lvl %>" />
    					<input type="hidden" name="cmd" value="addMakul" />		
    					 
					<!--  tr>
	        			<td style="color:#000;text-align:center;" colspan="7">
	        			<%
	        			if(vSelectMk!=null && vSelectMk.size()>0) {
	        				%>
	        				<select name="makul" style="width:100%;height:25px">
	        					<option value="null" selected="selected">--PILIH MATAKULIAH UNTUK DITAMBAH KE KRS=--</option>
	        				<%
	        				liSmk = vSelectMk.listIterator();
	        				while(liSmk.hasNext()) {
	        					String baris = (String)liSmk.next();
	        					StringTokenizer st = new StringTokenizer(baris,",");
	        					String idmk = st.nextToken();
	        					String kdmk = st.nextToken();
	        					String namk = st.nextToken();
	        				%>
	        					<option value="<%=idmk %>">(<%=kdmk %>)-<%=namk %></option>
	        				<%
	        				}
	        				%>
	        				</select>
	        				<%
	        			}
	        			else {
	        				%>
	        				--Tidak Ada Pilihan Matakuliah--
	        				<%
	        			}
	        			%>
	        			</td>
	        			<%
        				if(msg==null && (prev_thsms.equalsIgnoreCase(thsms_krs)||allowEditAllThsms)) {
        				%>
	        			<td style="color:#000;text-align:center;"><label><input type="submit" value="+" name="add" style="font-size:15px;font-weight:bold;width:94%"/></label></td>
	        			<%
	        			}
	        			%>
	        		</tr> -->
	        		</form>
	        		<%
	        		}
	        		%>	
					<!--  tr>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="5"><label><B>SKS & IP SEMESTER</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="1"><label><B><%=prev_sksem %></B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="1"><label><B><%=prev_nlips %></B> </label></td></td>
        			</tr>
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="5"><label><B>SKS & IP KOMULATIF</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="1"><label><B><%=prev_skstt %></B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="1"><label><B><%=prev_nlipk %></B> </label></td></td>
        			</tr-->
        		</table>
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