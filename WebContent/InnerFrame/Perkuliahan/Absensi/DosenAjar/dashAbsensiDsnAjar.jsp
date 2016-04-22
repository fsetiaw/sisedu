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
<%@ page import="beans.dbase.classPoll.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.sistem.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%

beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");

Vector v= null; 
String listTipeUjian = (String)session.getAttribute("listTipeUjian");
String targetThsms  = request.getParameter("target_thsms");
if(targetThsms==null || Checker.isStringNullOrEmpty(targetThsms)) {
	targetThsms = Checker.getThsmsNow();
}
Vector vListKelasPerProdi = (Vector) session.getAttribute("vListKelasPerProdi");
session.removeAttribute("vListKelasPerProdi");
String tknScopeKampus = request.getParameter("tknScopeKampus");
Vector vInfoKehadiranDosen = (Vector) session.getAttribute("vInfoKehadiranDosen");
session.removeAttribute("vInfoKehadiranDosen");
//request.removeAttribute("listTipeUjian");
//String atMenu = request.getParameter("atMenu");
%>
<script type="text/javascript">
</script>
</head>
<body>
<div id="header">
<%@ include file="../../innerMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
					<br/>
				<%
/*
if(vKlsAjar!=null && vKlsAjar.size()>0) {
	
}
					*/
					
					 
if(vInfoKehadiranDosen!=null && vInfoKehadiranDosen.size()>0) {
	int no = 1;
%>
		<form action="update.statusKehadiranDosen" method="post" >
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px"> 
        <tr> 
        	<td style="background:#369;color:#fff;text-align:center;font-size:1.7em" colspan="8"><label><B>FORM STATUS KEHADIRAN DOSEN AJAR <%=targetThsms %></B> </label></td> 
        </tr> 
        <tr> 
        	<td style="background:#369;color:#fff;text-align:center;width:20px;padding:0 2px">NO</td>
        	<td style="background:#369;color:#fff;text-align:center;width:80px;padding:0 2px">PRODI</td>
        	<td style="background:#369;color:#fff;text-align:left;width:300px;padding:0 2px">KODE & NAMA MATAKULIAH</td>
        	<td style="background:#369;color:#fff;text-align:center;width:100px;padding:0 2px">TANGGAL <br/>&nbsp</td>
        	<td style="background:#369;color:#fff;text-align:center;width:100px;padding:0 2px">JAM <br/>/ SHIFT</td>
        	<td style="background:#369;color:#fff;text-align:center;width:30px;padding:0 2px">NO <br/>KLS</td>
        	<td style="background:#369;color:#fff;text-align:center;width:135px;padding:0 2px">PERKIRAAN WAKTU <br/>KETERLAMBATAN<br/> (Menit)</td>
        	<td style="background:#369;color:#fff;text-align:center;width:15px;padding:0 2px">BATAL</td> 
        </tr>
<%
	ListIterator lit = vInfoKehadiranDosen.listIterator();
	int norut = 0;
	while(lit.hasNext()) {
		String brs = (String)lit.next();
		String brs2 = (String)lit.next(); //li.add(cuid+"`"+tgl+"`"+batal+"`"+delay_time+"`"+real_start+"`"+replace_date+"`"+usrNpm+"`"+superNpm+"`"+thsms);
		//System.out.println("brs22="+brs2);
		StringTokenizer st = new StringTokenizer(brs,"`");
		String cuid2 = null;
		String tgl2  = null;
		String batal2 = null;
		String delay_time2 = null;
		String real_start2 = null;
		String replace_date2 = null;
		String usrNpm2 = null;
		String superNpm2 = null;
		String thsms2 = null;
		if(brs2!=null && !Checker.isStringNullOrEmpty(brs2)) {
			StringTokenizer stt = new StringTokenizer(brs2,"`");
			cuid2 = stt.nextToken();
			tgl2  = stt.nextToken();
			batal2 = stt.nextToken();
			delay_time2 = stt.nextToken();
			real_start2 = stt.nextToken();
			replace_date2 = stt.nextToken();
			usrNpm2 = stt.nextToken();
			superNpm2 = stt.nextToken();
			thsms2 = stt.nextToken();
		}
		String kdpst = st.nextToken();
		String nakmk = st.nextToken();
		String idkur = st.nextToken();
		String idkmk = st.nextToken();
		String shift = st.nextToken();
		String thsms = st.nextToken();
		
		String nopll = st.nextToken();
		String initNpmInp = st.nextToken();
		String lasNpmUpd = st.nextToken();
		String lasStatInf = st.nextToken();
		String curAvailStat = st.nextToken();
		String locked = st.nextToken();
		String npmdos = st.nextToken();
		String nodos = st.nextToken();
		String npmasdos = st.nextToken();
		String noasdos = st.nextToken();
		String batal = st.nextToken();
		String kodeKls = st.nextToken();
		String kodeRuang = st.nextToken();
		String kodeGdg = st.nextToken();
		String kodeKmp = st.nextToken();
		String tknDayTime = st.nextToken();
		String nmmDos = st.nextToken();
		String nmmAsdos = st.nextToken();
		String totEnrol = st.nextToken();
		String maxEnrol = st.nextToken();
		String minEnrol = st.nextToken();
		String subKeterKdkmk = st.nextToken();
		String initReqTime = st.nextToken();
		String tknNpmApr = st.nextToken();
		String tknAprTime = st.nextToken();
		String targetTtmhs = st.nextToken();
		String passed = st.nextToken();
		String rejected = st.nextToken();
		String kodeGabung = st.nextToken();
		String kodeGabungUniv = st.nextToken();
		String cuid = st.nextToken();
		String kdkmk = st.nextToken();
%>
		<tr> 
        	<td style="text-align:center;width:20px;padding:0 2px"><%=no++ %></td>
        	<td style="text-align:center;width:80px;padding:0 2px"><%=Converter.getNamaKdpst(kdpst) %></td>
        	<td style="text-align:left;width:300px;padding:0 2px">(<%=kdkmk %>) - <%=nakmk %><%="<br/>DOSEN: "+nmmDos %></td>
        	<td style="text-align:center;width:100px;"><input type="hidden" name="cuid" value="<%=cuid %>"/>
        	<%
        	if(brs2==null || Checker.isStringNullOrEmpty(brs2)) {
        	%>
        		<input type="text" style="width:99%;height:100%;text-align:center" name="tgl_ttm" placeholder="tgl/bln/tahun"/>
        	<%	
        	}
        	else {
        	%>
        		<input type="text" style="width:99%;height:100%;text-align:center" name="tgl_ttm" placeholder="tgl/bln/tahun" value="<%= Converter.reformatSqlDateToTglBlnThn(tgl2) %>" />
        	<%
        	}
        	%></td>
        	<td style="text-align:center;width:100px;padding:0 2px"><%=shift %></td>
        	<td style="text-align:center;width:30px;padding:0 2px"><%=nopll %></td>
        	<td style="text-align:center;width:135px;padding:0 2px">
			<%
        	if(brs2==null || Checker.isStringNullOrEmpty(brs2)) {
        	%>
        		<input type="text" style="width:99%;height:100%;text-align:center" name="delay_tm" />
        	<%	
        	}
        	else {
        	%>
        		<input type="text" style="width:99%;height:100%;text-align:center" name="delay_tm" value="<%=delay_time2%>"/>
        	<%
        	}
        	%>
			</td>
        	<td style="text-align:center;width:15px;padding:0 2px">
        	<%
        	if(brs2==null || Checker.isStringNullOrEmpty(brs2)) {
        	%>
        		<input type="checkbox" name="batal" value="<%=norut++%>">
        	<%	
        	}
        	else {
        		if(batal2!=null && batal2.equalsIgnoreCase("true")) {
        			%>
            	<input type="checkbox" name="batal" value="<%=norut++%>" checked="checked">
            	<%		
        		}
        		else {
        			%>
            	<input type="checkbox" name="batal" value="<%=norut++%>">
            	<%
        		}
        	}
        	%>
        	</td> 
        </tr>
<%		
	}
%>   
		<tr>
			<td style="background:#369;color:#fff;text-align:center" colspan="8"><input type="submit" value="UPDATE DATA" style="width:50%"/></td>
		</tr>     
        </table>
        </form>
<%
}
else {
	out.print("<h2 align=\"center\"> INFORMASI BELUM TERSEDIA </h2>");
}
%>         					
		<!-- Column 1 end -->
		
	</div>
</div>

</body>
</html>