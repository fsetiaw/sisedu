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
<%@ page import="beans.dbase.classPoll.*" %>
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v= null; 
String listTipeUjian = (String)session.getAttribute("listTipeUjian");
Vector vPengajuan = (Vector)session.getAttribute("vPengajuan");
StringTokenizer st = null;
//String opr_id = ""+;
//String id_obj=""+request.getParameter("idobj");
//String nmm=""+request.getParameter("nmm");
//String npm=""+request.getParameter("npm");
//String obj_lvl=""+request.getParameter("obj_lvl");
//String kdpst=""+request.getParameter("kdpst");
//String cmd=""+request.getParameter("cmd");
//request.removeAttribute("listTipeUjian");
//String atMenu = request.getParameter("atMenu");
String your_role =""+request.getParameter("your_role");
String id =""+request.getParameter("id");
String thsms =""+request.getParameter("thsms");
String kdpst =""+request.getParameter("kdpst");
String npmhs =""+request.getParameter("npmhs");
String status_akhir =""+request.getParameter("status_akhir");
String skedul_date =""+request.getParameter("skedul_date");
String realisasi_date =""+request.getParameter("realisasi_date");
String kdkmk =""+request.getParameter("kdkmk");
String file_name =""+request.getParameter("file_name");
String updtm =""+request.getParameter("updtm");
String skedul_time =""+request.getParameter("skedul_time");
String judul =""+request.getParameter("judul");
String show_owner =""+request.getParameter("show_owner");
String show_approvee =""+request.getParameter("show_approvee");
String show_monitoree =""+request.getParameter("show_monitoree");
String idobj =""+request.getParameter("idobj");
String tkn_id_approvee = ""+request.getParameter("tkn_id_approvee");
String tkn_show_approvee = ""+request.getParameter("tkn_show_approvee");
String nmmhs =""+request.getParameter("nmmhs");
String rule_tkn_approvee_id = ""+request.getParameter("rule_tkn_approvee_id");
String urutan=""+request.getParameter("urutan");
String tkn_approvee_nickname=""+request.getParameter("tkn_approvee_nickname");
String final_stat = "";
String info_vote_sdh_diwakilkan = "";
String err_msg = ""+request.getParameter("err_msg");
Vector vRiwayatPengajuan = (Vector)session.getAttribute("vRiwayatPengajuan");
session.removeAttribute("vRiwayatPengajuan");
String who_missing = Getter.getSiapaYgBlumNgasihTindakanPengajuanUa(vRiwayatPengajuan,rule_tkn_approvee_id,tkn_approvee_nickname);
//System.out.println("who_missing="+who_missing);
info_vote_sdh_diwakilkan = Getter.isTindakanPengajuanUaSdhDiwakilkan(vRiwayatPengajuan, rule_tkn_approvee_id,  validUsr.getIdObj());
st = new StringTokenizer(info_vote_sdh_diwakilkan,"/");
boolean vote_sdh_diwakilkan = Boolean.parseBoolean(st.nextToken());
String diwakilkan_oleh = st.nextToken();
String riwayat_penolakan = ""+request.getParameter("riwayat_penolakan");
//System.out.println("riwayat-penolakan="+riwayat_penolakan);
//System.out.println("vote_sdh_diwakilkan="+vote_sdh_diwakilkan);
//System.out.println("diwakilkan_oleh="+diwakilkan_oleh);
//String status_akhir_updated = Getter.getStatusPengajuanUa(vRiwayatPengajuan, rule_tkn_approvee_id, tkn_approvee_nickname);
%>

</head>
<body>
<div id="header">
<ul>
	<!--  li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}InnerFrame/Pengajuan/DashUjianAkhir_part1.jsp?id_obj=id_obj%>&nmm=nmm%>&npm=npm %>&obj_lvl=obj_lvl %>&kdpst=kdpst %>&cmd=ua" target="_self">UJIAN<span>AKHIR</span></a></li -->
	<li><a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">BACK<span>&nbsp</span></a></li> 
</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->

	
	<br/>
	<br/>	
	<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:650px"> 
    <tr> 
    	<td style="background:#369;color:#fff;text-align:center;text-size:1.5em" colspan="2"><label><B>PENGAJUAN UJIAN <%=kdkmk.toUpperCase() %></B> </label></td> 
  	</tr> 
  	
    <tr> 
   		<td style="background:#a6bac4;color:#fff;text-align:left;width:250px;padding:0 5px 0 5px"><b>NPM - NAMA</b></td>
   		<td style="text-align:left;width:400px;padding:0 5px 0 5px"><%=npmhs+"<br/>"+nmmhs %></td>
    </tr>
    <tr> 
   		<td style="background:#a6bac4;color:#fff;text-align:left;width:250px;padding:0 5px 0 5px"><b>JUDUL</b></td>
   		<td style="text-align:left;width:400px;padding:0 5px 0 5px">
   		<%
   		//judulChecker
   		
   		%>
   		<a style="color:#369" href="go.downloadFileAsIs?root_dir=null&keter=null&alm=<%=Checker.getRootFolderIndividualMhs(npmhs)+"/UA/"+kdkmk%>&namaFile=<%=file_name%>&hak=null"><%=judul %></a>
   		</td>
    </tr>
    <tr> 
   		<td style="background:#a6bac4;color:#fff;text-align:left;width:250px;padding:0 5px 0 5px"><b>STATUS PENGAJUAN</b></td>
   		<td style="text-align:left;width:400px;padding:0 5px 0 5px">
   		<%
   		out.print(status_akhir);
   		/*
   		if(status_akhir_updated.equalsIgnoreCase("terima")) {
   			out.print("Diterima");	
   		}
   		else if(status_akhir_updated.equalsIgnoreCase("tolak")) {
   			out.print("Ditolak");	
   		}
   		else if(status_akhir_updated.equalsIgnoreCase("proses")) {
   			out.print("Sedang Dalam Proses");	
   		}
   		*/
   		
   		%>
   		</td>
    </tr>
    <tr>
    	<td style="background:#369;color:#fff;text-align:left;width:250px;padding:0 5px 0 5px" colspan="2"><b>RIWAYAT STATUS PENGAJUAN</b></td>
    <tr>
    <tr>
    	<td style="text-align:left;width:250px;padding:0 5px 0 5px" colspan="2">
    	<%
    boolean passed = true;
    if(!Checker.isStringNullOrEmpty(riwayat_penolakan))	{
    	out.print("<p style=\"color:red\">"+riwayat_penolakan+"</p>");	
    }
    
		
    //String menunggu_approval_from = new String(tkn_approvee_nickname);
    if(vRiwayatPengajuan!=null && vRiwayatPengajuan.size()>0) {
    	ListIterator li = vRiwayatPengajuan.listIterator();
    	String tmp_sisa_approval = "";
    	//boolean first = true;
    	while(li.hasNext()) {
    		String brs = (String)li.next();
    		//System.out.println("baris = "+brs);
    		//lif.add(id_ri+"`"+id+"`"+npm_approvee+"`"+status+"`"+updtm+"`"+komen+"`"+approvee_id+"`"+approvee_nickname);
    		st = new StringTokenizer(brs,"`");
    		String id_ri1 = st.nextToken();
    		String id1 = st.nextToken();
    		String npm_approvee1 = st.nextToken();
    		String status1 = st.nextToken();
    		String updtm1 = st.nextToken();
    		String komen1 = st.nextToken();
    		if(Checker.isStringNullOrEmpty(komen1)) {
    			komen1="";
    		}
    		else {
    			komen1 = "Alasan : "+komen1;
    		}
    		String approvee_id1 = st.nextToken();
    		String approvee_nickname1 = st.nextToken();
    		//String riwayat_pengajuan = st.nextToken();
    		
    		
    		if(status1.equalsIgnoreCase("TOLAK")) {
    			final_stat = "TOLAK";
    			passed = false;
    			out.print("<p style=\"color:red\">Pengajuan Ujian Ditolak oleh "+approvee_nickname1+"  ("+updtm1+")<br/>"+komen1+
    					"<br/>** Harap melakukan pengajuan ulang kembali setelah alasan penolakan telah terpenuhi</p>");
    		}
    		else {
    			out.print("<p style=\"color:black\">Pengajuan Ujian Disetujui oleh "+approvee_nickname1+"  ("+updtm1+")<br/>"+komen1+"</p>");
    		}
    		/*
    		System.out.println("rule_tkn_approvee_id="+rule_tkn_approvee_id);
    		System.out.println("approvee_id1="+approvee_id1);
    		
    		if(rule_tkn_approvee_id.contains("/"+approvee_id1+"/")) {
    			System.out.println("yes it contains");g
    			StringTokenizer st1 = new StringTokenizer(rule_tkn_approvee_id,"-");
    			StringTokenizer st2 = new StringTokenizer(menunggu_approval_from,"-");
    			String tkn_apr_id = st1.nextToken();
    			String tkn_apr_nick = st2.nextToken();
    			//tmp_sisa_approval
    			System.out.println("tkn_apr_id="+tkn_apr_id);
    			System.out.println();
    			if(tkn_apr_id.contains("/"+approvee_id1+"/")) {
    				//cek apa do tkn ini id anda ada juga
    				System.out.println("tkn_apr_id.contains(opr_id) = "+opr_id);
    				System.out.println("approvee_id1.contains(opr_id) = "+approvee_id1);
    				if(tkn_apr_id.contains("/"+opr_id+"/") && !("/"+opr_id+"/").equalsIgnoreCase("/"+approvee_id1+"/")) {
    					vote_sdh_diwakilkan = true;
    				}
    			}
    			else {
    				tmp_sisa_approval = tmp_sisa_approval+tkn_apr_nick+"-";
    			}
    			
    		}
    		
    	}
    	if(!Checker.isStringNullOrEmpty(tmp_sisa_approval) && !final_stat.equalsIgnoreCase("TOLAK")) {
    		st = new StringTokenizer(tmp_sisa_approval,"-");
        	while(st.hasMoreTokens()) {
        		String brs = st.nextToken();
        		StringTokenizer stt = new StringTokenizer(brs,"/");
        		if(stt.countTokens()==1) {
        			out.print("<br/>- Menunggu persetujuan dari "+stt.nextToken()+"<br/>");
        		}
        		else {
        			String tmp="";
        			while(stt.hasMoreTokens()) {
        				tmp = tmp+stt.nextToken();
        				if(stt.hasMoreTokens()) {
        					tmp = tmp +" atau ";
        				}
        			}
        			out.print("<br/>- Menunggu persetujuan dari "+tmp+"<br/>");
        		}
        	}
        	out.print("<br/>");
        	*/
    	}
    }
    //out.print("<br/>");
    //if(who_missing!=null && (status_akhir_updated!=null && !status_akhir_updated.equalsIgnoreCase("tolak"))) {
    if(who_missing!=null && (status_akhir!=null && !status_akhir.equalsIgnoreCase("Ditolak"))) {	
    	st = new StringTokenizer(who_missing,"-");
    	while(st.hasMoreTokens()) {
    		String brs = st.nextToken();
    		//bisa beberapa obj 
    		StringTokenizer st1 = new StringTokenizer(brs,"/");
    		String tmp = "";
    		while(st1.hasMoreTokens()) {
    			tmp = tmp+st1.nextToken();
    			if(st1.hasMoreTokens()) {
    				tmp = tmp + " atau ";
    			}
    		}
    		out.print("<p style=\"color:#D05800\">- Menunggu Persetujuan "+tmp+"</p>");
    	}
    }
    //else if()
    /*
    else {
    	st = new StringTokenizer(menunggu_approval_from,"-");
    	while(st.hasMoreTokens()) {
    		String brs = st.nextToken();
    		StringTokenizer stt = new StringTokenizer(brs,"/");
    		if(stt.countTokens()==1) {
    			out.print("<br/>- Menunggu persetujuan dari "+stt.nextToken()+"<br/>");
    		}
    		else {
    			String tmp="";
    			while(stt.hasMoreTokens()) {
    				tmp = tmp+stt.nextToken();
    				if(stt.hasMoreTokens()) {
    					tmp = tmp +" atau ";
    				}
    			}
    			out.print("<br/>- Menunggu persetujuan dari "+tmp+"<br/>");
    		}
    	}
    	out.print("<br/>");
    }
    */
    	%>
    	</td>
    </tr>
<%
	if(your_role.equalsIgnoreCase("approve") && !vote_sdh_diwakilkan && (status_akhir!=null && !status_akhir.equalsIgnoreCase("Ditolak") )) {
%>
	<form action="process.approvalPengajuanUa?your_role=<%= your_role%>&id=<%= id%>&thsms=<%= thsms%>&kdpst=<%= kdpst%>&npmhs=<%= npmhs%>&status_akhir=<%= status_akhir%>&skedul_date=<%= skedul_date%>&realisasi_date=<%= realisasi_date%>&kdkmk=<%= kdkmk%>&file_name=<%= file_name%>&updtm=<%= updtm%>&skedul_time=<%= skedul_time%>&judul=<%= judul%>&show_owner=<%= show_owner%>&tkn_show_approvee=<%= tkn_show_approvee%>&tkn_id_approvee=<%= tkn_id_approvee%>&show_monitoree=<%= show_monitoree%>&idobj=<%= idobj%>&nmmhs=<%= nmmhs%>&rule_tkn_approvee_id=<%=rule_tkn_approvee_id%>&urutan=<%=urutan%>&tkn_approvee_nickname=<%=tkn_approvee_nickname%>" method="post">
	<tr>
		<td style="background:#369;color:#fff;text-align:left;width:250px;padding:0 5px 0 5px" colspan="2"><b>FORM PERSETUJUAN PENGAJUAN</b></td>
	</tr>
	<tr>
		<td style="text-align:left;width:250px;padding:1px 1px 1px 1px" colspan="2"><textarea name="reason" rows="4" cols="100%" placeholder="Harap Diisi Alasan Penolakan ">
		</textarea>
	</tr>
	<tr> 
   		<td style="background:#369;color:#fff;text-align:center;width:250px;padding:0 5px 0 5px" colspan="2">
   		<input type="submit" name="approval" value="Terima Pengajuan" style="width:135px;height:35px;font-weight:bold"/>
   		&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
   		&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
   		<input type="submit" name="approval" value="Tolak Pengajuan" style="width:135px;color:red;height:35px;font-weight:bold"/></td>
    </tr>
	</form>
<%		
	}
	else {
		if((status_akhir!=null && status_akhir.equalsIgnoreCase("Ditolak") )) {
			
		}
		else {
			if(!validUsr.iAmStu()) {
		
%>
	<tr>
		<td style=";color:#white;text-align:center;width:250px;padding:0 5px 0 5px" colspan="2"><b>PERSETUJUAN PENGAJUAN SUDAH DIWAKILKAN OLEH <%=diwakilkan_oleh %></b></td>
	</tr>
<%		
			}
		}
	}	
%>    

    </table> 
    <br/>
	<div style="text-align:center;font-weight:italic;color:red;font-size:1.25em">
	<%
	if(err_msg!=null && !Checker.isStringNullOrEmpty(err_msg)) {
		out.print("* Harap Menyertakan / Diisi Alasan Penolakan Diisi");
	}
	%>
	
	</div>
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>