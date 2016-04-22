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
	String tipeForm = request.getParameter("formType");
	Vector vDwn = validUsr.getScopeUpd7des2012("hasDownloadMenu");
	Vector v_cf = validUsr.getScopeUpd7des2012("hasEditMkKurikulum");
	String errMsg = request.getParameter("errMsg");
	boolean atFormPengajuanBukaKelasTahap1 = false;
	boolean atFormPengajuanBukaKelasTahap2 = false;
	String kdpst_nmpst = null;
	String backward2 = null;
	session.removeAttribute("klsInfo");
	session.removeAttribute("totKls");
	String msg = request.getParameter("msg");
	String pb = request.getParameter("pb");
	String alihkan =request.getParameter("alihkan");
	Vector v_list_prodi_no_class = (Vector)request.getAttribute("v_list_prodi_no_class"); //PST`57301`MANAJEMEN INFORMATIKA`D`117
	request.removeAttribute("v_list_prodi_no_class");
%>

<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
</head>
<body>
<div id="header">
<%@ include file="IndexAkademikPengajuanSubMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->

		<%
		if(pb!=null && pb.equalsIgnoreCase("yes")) {
			%>
			<%@ include file= "../../progressBar/progressBar.jsp" %>
			<%
		}
		else {
			if(errMsg!=null && !Checker.isStringNullOrEmpty(errMsg)) {
			%>
			<div style="font-size:2em;font-style:bold;text-align:center"><%=errMsg %></div>
			<%
			}
			else {
				if(msg!=null && !Checker.isStringNullOrEmpty(msg)) {
					
			%>
			<div style="font-size:1.5em;font-style:bold;text-align:center;color:blue"><%=msg %></div>
				
			<%
					if(alihkan!=null & !Checker.isStringNullOrEmpty(alihkan)) {
						if(alihkan.contains("jsp")) {
							if(alihkan.contains("home.jsp")) {
								alihkan="get.notifications";
							}
						%>	
							<meta http-equiv='Refresh' content='3, URL=<%=alihkan%>'>
						<%
						}
					}
				}
			}	
		}
		
		//msg
		if(v_list_prodi_no_class!=null && v_list_prodi_no_class.size()>0) {
			ListIterator li = v_list_prodi_no_class.listIterator();
			String thsms_kelas = Checker.getThsmsBukaKelas();
			%>
			<div style="font-size:1.5em;font-style:bold;text-align:left;padding:0 0 0 50px">
			Harap melakukan pengajuan kelas perkuliahan untuk thn/sms <%=Converter.convertThsmsKeterOnlyFromatThnAkademik(thsms_kelas) %>, bila ada<br/> 
		 	untuk program studi berikut: <br/><br/>	
		 	</div>
			<%
			int i = 1;
			do {
				String brs = (String)li.next();
				StringTokenizer st = new StringTokenizer(brs,"`");
				String kmp = st.nextToken();
				String kdpst = st.nextToken();
				String nmpst = st.nextToken();
				String kdjen = st.nextToken();
				String idobj = st.nextToken();
				
			%>
				<div style="font-size:1.2em;font-style:italic;text-align:left;padding:0 0 0 50px">
				<%=i++ %>. <%=Converter.getNamaKdpst(kdpst) %> untuk jenjang <%=Converter.getDetailKdjen(kdjen) %> @ Kampus <%=kmp %><br/>
				</div>
			<%	
			}
			while(li.hasNext());
			%>
			
			<%
		}
		%>
		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>