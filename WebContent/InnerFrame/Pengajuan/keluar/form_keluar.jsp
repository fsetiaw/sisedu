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
////System.out.println("yap1");
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
%>
<script type="text/javascript">
</script>
</head>
<body>
<div id="header">
<ul>
	<li>
		<a href="<%=Constants.getRootWeb()+"/" %>InnerFrame/indexPengajuanMhs.jsp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=reqMhs" target="_self">BACK <span>&nbsp</span></a>
	</li>
	<li>
		<a href="<%=Constants.getRootWeb()+"/" %>InnerFrame/Pengajuan/keluar/form_keluar.jsp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=reqMhs" target="_self">FORM<span>KELUAR</span></a>
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
			<div align="center" style="font-size:0.7em">UPDATED : <%=AskSystem.getDateTime() %></div>
		<%
		}
		%>
		
		<form action="go.ajukanKeluar?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=cuti&target_thsms=<%=target_thsms %>" method="post">
		<input type="hidden" name="target_thsms_stmhs" value="<%=target_thsms+"`K" %>" />
		<div style="width:90%">
		Dengan ini saya, <%=nmm %>, mengajukan permohonan untuk keluar pada tahun/sms <%=target_thsms %>, dikarenakan :<br/>
		<br/><textarea name="alasan" style="width:88%" rows="4" required></textarea><br/><br/>
		dan telah memenuhi kewajiban / persyaratan yang telah ditentukan.
		<br/>
		<br/>
		<div style="font-size:.9em;color:red;font-weight:italic">
		* perhatikan tahun/sms pengajuan, karena proses pengajuan ini akan menghapus KRS pada tahun/sms <%=target_thsms %>.
		</div>
		<br/>
		<br/>
			<div style="text-align:center;width:88%">
				<input type="submit" style="width:100px;height:30px" value="Ajukan"/>
			</div>
		</div>
		
			
         </form>
		<!-- Column 1 end -->
		
	</div>
</div>

</body>
</html>