<!DOCTYPE html>
<head>
<%@page import="beans.setting.Constants"%>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
	
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.dbase.classPoll.*" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.sistem.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%
//System.out.println("yap1");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v= null;
//Vector vThsmsStmhs = (Vector)session.getAttribute("vThsmsStmhs");
//String tkn_stmhs_kode =  Getter.getListStmhs();

String id_obj= request.getParameter("id_obj");
String nmm= request.getParameter("nmm");
String npm= request.getParameter("npm");
String obj_lvl= request.getParameter("obj_lvl");
String kdpst= request.getParameter("kdpst");
String target_thsms = Checker.getThsmsHeregistrasi();
String cmd= request.getParameter("cuti");
String msg= request.getParameter("msg");
String status_pengajuan_cuti = (String)session.getAttribute("status_pengajuan_cuti_"+npm);
//System.out.println("status_pengajuan_cuti="+status_pengajuan_cuti);
//session.removeAttribute("status_pengajuan_cuti_"+npm); // harus di home utk removenya
%>

</head>
<body>
<div id="header">
<ul>
	<li>
		<a href="<%=Constants.getRootWeb()+"/" %>InnerFrame/indexPengajuanMhs.jsp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=reqMhs" target="_self">BACK <span>&nbsp</span></a>
	</li>
	<li>
		<a href="<%=Constants.getRootWeb()+"/" %>InnerFrame/Pengajuan/cuti/form_cuti.jsp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=reqMhs" target="_self">FORM<span>CUTI</span></a>
	</li>
</ul>	
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		
		<%
		if(msg!=null && msg.equalsIgnoreCase("upd")) {
		%>
			<div align="right" style="font-size:0.5em">UPDATED : <%=AskSystem.getDateTime() %></div>
		<%
		}
		%>
		
		
		<%
		if(status_pengajuan_cuti==null || Checker.isStringNullOrEmpty(status_pengajuan_cuti) ||(status_pengajuan_cuti!=null && status_pengajuan_cuti.equalsIgnoreCase("ditolak")) || (status_pengajuan_cuti!=null && status_pengajuan_cuti.equalsIgnoreCase("batal"))) {
			if(status_pengajuan_cuti!=null) {
				if(status_pengajuan_cuti.equalsIgnoreCase("ditolak")) {
				%>
				<div style="text-align:center;font-size:1.5em">
				Pengajuan cuti anda untuk tahun/sms <%=target_thsms %> ditolak, silahkan ajukan kembali setelah melengkapi persyaratan<br/>
				</div>
				<br/>
				<br/>
				<%	
				}
			}	
		%>
		
		<form onsubmit="button_submit.disabled = true; return true;" action="go.ajukanCuti?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=cuti&target_thsms=<%=target_thsms %>" method="post">
		<div style="width:90%">
		Dengan ini saya, <%=nmm %>, mengajukan izin cuti / tidak aktif mengikuti kegiatan akademik <%=target_thsms %> dikarenakan :<br/>
		<br/><textarea name="alasan" style="width:88%" rows="4" required></textarea><br/><br/>
		dan telah memenuhi kewajiban / persyaratan yang telah ditentukan.
		<br/>
		<br/>
			<div style="text-align:center;width:88%">
				<input type="submit" style="width:100px;height:30px" value="Ajukan" name="button_submit"/>
			</div>
		</div>
		
			
        </form>
        <%
        }
		else if(status_pengajuan_cuti!=null && status_pengajuan_cuti.equalsIgnoreCase("inprogress")) {
		%>
		<div style="text-align:center;font-size:1.5em">
		Pengajuan cuti anda untuk tahun/sms <%=target_thsms %> sedang dalam proses persetujuan, <br/>
		Apakah anda mau membatalkan pengajuan cuti anda?<br/><br/>
		<form action="go.batalCuti?target_thsms=<%=target_thsms %>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=cuti" method="post" >
		<br/>
			<div style="text-align:center">
				<input type="submit" value="Klik untuk Batalkan Pengajuan" style="color:red;height:35px" name="button_submit" />
			</div>
		</form>
		
		</div>
		<%	
		}
		else if(status_pengajuan_cuti!=null && status_pengajuan_cuti.equalsIgnoreCase("disetujui")) {
		%>
		<div style="text-align:center;font-size:1.5em">
		Pengajuan cuti anda untuk tahun/sms <%=target_thsms %> sudah diterima dan disetujui
		</div>
		<%			
		}
        %>
		<!-- Column 1 end -->
		
	</div>
</div>

</body>
</html>