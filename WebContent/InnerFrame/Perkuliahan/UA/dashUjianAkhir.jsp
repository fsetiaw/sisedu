<!DOCTYPE html>
<head>
<%@page import="beans.setting.Constants"%>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/js/accordion/accordion.css" media="screen" />
	<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/accordion/accordion.js"></script>
	
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
boolean stu = validUsr.iAmStu();
//request.removeAttribute("listTipeUjian");
//String atMenu = request.getParameter("atMenu");
%>
<script type="text/javascript">

</script>
<style>

</style>

</script>
</head>
<body>
<div id="header">
<%@ include file="../innerMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		
		
<%
if(vPengajuan!=null && vPengajuan.size()>0) {
%>		
	<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:750px"> 
    <tr> 
    	<td style="background:#369;color:#fff;text-align:center" colspan="7"><label><B>DAFTAR PENGAJUAN UJIAN AKHIR</B> </label></td> 
  	</tr> 
  	
    <tr> 
   		<td style="background:#369;color:#fff;text-align:center;width:25px">NO</td>
        <td style="background:#369;color:#fff;text-align:center;width:150px">PRODI</td>
        <td style="background:#369;color:#fff;text-align:center;width:250px">NPM - NAMA</td>
        <td style="background:#369;color:#fff;text-align:center;width:125px">UJIAN</td>
    <%
    if(stu) {
    %>  
        <td style="background:#369;color:#fff;text-align:center;width:100px" colspan="3">STATUS PENGAJUAN</td>
    <%
    }
    else {
    %>  
    	<td style="background:#369;color:#fff;text-align:center;width:100px">STATUS PENGAJUAN</td>
        <td style="background:#369;color:#fff;text-align:center;width:50px">TINDAKAN</td>
        <td style="background:#369;color:#fff;text-align:center;width:50px">READ</td>
    </tr> 
	<%
    }
    
  	ListIterator lip = vPengajuan.listIterator();
	int norut = 0;
	while(lip.hasNext()) {
		boolean tindakan = false;
		boolean show = true;
		norut++;
		String brs = (String)lip.next();
		//System.out.println(brs);
		st = new StringTokenizer(brs,"`");
		//(role+"`"+id+"`"+thsms+"`"+kdpst+"`"+npmhs+"`"+status_akhir+"`"+skedul_date+"`"+realisasi_date+"`"+kdkmk+"`"+file_name+"`"+updtm+"`"+skedul_time+"`"+judul+"`"+show_owner+"`"+show_approvee+"`"+show_monitoree+"`"+idobj+"`"++"`"+tkn_id_approvee+"`"+tkn_show_approvee+""+nmmhs);
		String your_role = st.nextToken();
		String id = st.nextToken();
		String thsms = st.nextToken();
		String kdpst = st.nextToken();
		String npmhs = st.nextToken();
		String status_akhir = st.nextToken();
		String skedul_date = st.nextToken();
		String realisasi_date = st.nextToken();
		String kdkmk = st.nextToken();
		String file_name = st.nextToken();
		String updtm = st.nextToken();
		String skedul_time = st.nextToken();
		String judul = st.nextToken();
		String show_owner = st.nextToken();
		//String show_approvee = st.nextToken();
		String show_monitoree = st.nextToken();
		String idobj = st.nextToken();
		String tkn_id_approvee = st.nextToken();
		String tkn_show_approvee = st.nextToken();
		if(tkn_show_approvee.contains("/"+validUsr.getIdObj()+"-no")) {
			show = false;
		}
		if(tkn_id_approvee.contains("/"+validUsr.getIdObj()+"-")) {
			tindakan = true;
		}
		String nmmhs = st.nextToken();
		String rule_tkn_approvee_id = st.nextToken();
		String urutan = st.nextToken();
		String tkn_approvee_nickname = st.nextToken();
		//done+"`"+time+"`"+riwayat)
		String done_so_hidden = st.nextToken();
		String realisasi_time = st.nextToken();
		String riwayat_penolakan = st.nextToken();
		
	%>
	<tr>
		<td style="background:#369;color:#fff;text-align:center;width:25px;"><%=norut %>.</td>
        <td style="text-align:center;"><%=Converter.getNamaKdpst(kdpst) %></td>
        <td style="text-align:left;padding:0 3px 0 2px">
        <%
        //if(your_role.equalsIgnoreCase("approve")) {
        %>
        	<a href="go.prepFormApprovalUa?your_role=<%= your_role%>&id=<%= id%>&thsms=<%= thsms%>&kdpst=<%= kdpst%>&npmhs=<%= npmhs%>&status_akhir=<%= status_akhir%>&skedul_date=<%= skedul_date%>&realisasi_date=<%= realisasi_date%>&kdkmk=<%= kdkmk%>&file_name=<%= file_name%>&updtm=<%= updtm%>&skedul_time=<%= skedul_time%>&judul=<%= judul%>&show_owner=<%= show_owner%>&tkn_show_approvee=<%= tkn_show_approvee%>&tkn_id_approvee=<%= tkn_id_approvee%>&show_monitoree=<%= show_monitoree%>&idobj=<%= idobj%>&nmmhs=<%= nmmhs%>&rule_tkn_approvee_id=<%=rule_tkn_approvee_id%>&urutan=<%=urutan%>&tkn_approvee_nickname=<%=tkn_approvee_nickname%>"><%=npmhs+"<br/>"+nmmhs %></a>	
        <%	
        //}
        //else {
        //	out.print(npmhs+"<br/>"+nmmhs);	
        //}
         
        %></td>
        <td style="text-align:center;"><%=kdkmk %></td>
        <%
        if(!stu) {
        	if(status_akhir.equalsIgnoreCase("ditolak")) {
        %>
        <td style="text-align:center;color:red">
        <%	
        	}
        	else {
        %>
        <td style="text-align:center;">
        <%	
        	}
        	out.print(status_akhir.toUpperCase()); 
        %>
        </td>
        <td style="text-align:center;font-size:1.5em">
        <%
        	if(tindakan) {
        		out.print("&#9745");
        	}
        	else {
        		out.print("&#9744");
        	}
       
        %></td>
        <td style="text-align:center;;font-size:1.5em">
        <%
        	if(show) {
        		out.print("&#9744");
        	}
        	else {
        		out.print("&#9745");
        	} 
        %>
        </td>
        <%
        }
        else {//student
        	if(status_akhir.equalsIgnoreCase("ditolak")) {
                %>
        <td style="text-align:center;color:red" colspan="3">
                <%	
            }
            else {
                %>
        <td style="text-align:center;" colspan="3">
                <%	
            }
            out.print(status_akhir.toUpperCase()); 
                %>
        </td>
        <%
        }
        %>
    </tr>    
        <%
        boolean isOprMonitoree = validUsr.isUsrAllowTo_updated("ua", npmhs);
        if(status_akhir.equalsIgnoreCase("diterima") && isOprMonitoree ) {
        %>
	<tr>
		<td colspan="7">
			<div class="accordion">
    		<div class="accordion-section">
    		<%
        	if(!stu) { 
        	%>
        	<a class="accordion-section-title" href="#accordion-1">FORM JADWAL WAKTU UJIAN</a>
         	<%
         	}
        	else {
        		%>
            <a class="accordion-section-title" href="#accordion-1">JADWAL UJIAN</a>
             	<%
        	}
         	%>
        	<div id="accordion-1" class="accordion-section-content">
        	<%
        	if(!stu) { 
        	%>
        	<form action="go.updJadwalUa?id_ua=<%=id %>" method="post" />
        	<%
        	}
        	%>
            <table style="width:95%" align="center">
            	<tr>
            		<!-- td style="color:#8B0000;font-weight:bold" -->
            	<%
            	if(!stu) {
            	%>
            		<td style="color:#8B0000;font-weight:bold">
            			RENCANA Waktu & Tanggal Ujian  :
            		</td>
            		<td>
            		<%
            		if(Checker.isStringNullOrEmpty(skedul_date)) {
            		%>	
            			<input type="text" placeholder="tgl/bln/thn" name="sked_dt"/> - 
            		<%
            		}
            		else {
            		%>	
            			<input type="text" placeholder="tgl/bln/thn" value="<%= Converter.reformatSqlDateToTglBlnThn(skedul_date) %>" name="sked_dt"/> -
            		<%
            		}
            		if(Checker.isStringNullOrEmpty(skedul_time)) {
                		%>	
                		<input type="text" placeholder="13:00" name="sked_tm"/>
                		<%
                	}
                	else {
                		%>	
                		<input type="text" placeholder="13:00" name="sked_tm" value="<%=skedul_time %>"/>
                		<%
                	}
            	}
            	else if(stu) {
            		%>
            		<td style="color:#8B0000;font-weight:bold;text-align:center">
            	<%	

            		if(Checker.isStringNullOrEmpty(skedul_date)) {
            	            			out.print("Tanggal Ujian Belum Ditentukan / Sedang Dalam Proses penjadwalan");
            		}
            		else {
            			out.print("Ujian di jadwalkan pada tgl "+Converter.reformatSqlDateToTglBlnThn(skedul_date)+" ");
            			if(!Checker.isStringNullOrEmpty(skedul_time)) {
            				out.print("pada jam "+skedul_time+"<br/> Semoga Berhasil & Sukses Selalu");
            			}
            		}
            	}
            		%>
            		</td>
            	</tr>
            	<%
            	if(!stu) {
            	%>
            	<tr>
            		<td style="font-weight:bold">
            			REALISASI Waktu & Tanggal Ujian  :
            		</td>
            		<td>
            		<%
            		if(Checker.isStringNullOrEmpty(realisasi_date)) {
            		%>	
            			<input type="text" placeholder="tgl/bln/thn" name="real_dt"/> - 
            		<%
            		}
            		else {
            		%>	
            			<input type="text" placeholder="tgl/bln/thn" value="<%= Converter.reformatSqlDateToTglBlnThn(realisasi_date) %>" name="real_dt"/> -
            		<%
            		}
            		if(Checker.isStringNullOrEmpty(realisasi_time)) {
                		%>	
                		<input type="text" placeholder="13:00" name="real_tm"/>
                		<%
                	}
                	else {
                		%>	
                		<input type="text" placeholder="13:00" name="real_tm" value="<%=realisasi_time %>"/>
                		<%
                	}
            		%>
            		</td>
            	</tr>
            	<tr>
            		<td style="font-weight:bold" colspan="2" align="center"><br/>
            			<input type="submit" value="Update Jadwal Ujian" style="width:50%;height:30px;font-size:1.2em"/>
            		</td>
            	</tr>
            	<%
            	}
            	%>
            </table>
            <%
        	if(!stu) { 
        	%>
            </form>
            <%
            }
            %>
        	</div><!--end .accordion-section-content-->
    		</div><!--end .accordion-section-->
			</div><!--end .accordion-->
		</td>
	</tr>	
<%	
        }
	}
%>
	</table>
<%	
}
else {
	if(stu) {
		out.println("<br/><h2 style=\"text-align:center\">Silahkan Menghubungi TU Untuk Pengajuan Ujian Sidang Dengan Membawa Softcopy & Hardcopy Naskah Yang Akan Diujikan</h2>");
	}
	else {
		out.println("<br/><h2 style=\"text-align:center\">TIDAK ADA PENGAJUAN UJIAN</h2>");	
	}
	
}

%> 
		
		
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>