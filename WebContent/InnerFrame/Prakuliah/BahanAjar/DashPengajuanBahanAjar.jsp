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
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector vListKelasAjar= null; 
vListKelasAjar = (Vector) session.getAttribute("vListKelasAjar");
session.removeAttribute("vListKelasAjar");
%>
<script type="text/javascript">
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
		<br/>
<%
if(vListKelasAjar!=null && vListKelasAjar.size()>0) {
	ListIterator li = vListKelasAjar.listIterator();
%>
<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:750px"> 
	<tr> 
    	<td style="background:#369;color:#fff;text-align:center" colspan="5"><label><B>PILIH KELAS YANG AKAN DIISI BAHAN AJAR</B> </label></td> 
   	</tr> 
    <tr> 
        <td style="background:#369;color:#fff;text-align:center;width:20px">NO</td>
        <td style="background:#369;color:#fff;text-align:center;width:150px">PRODI</td>
        <td style="background:#369;color:#fff;text-align:center;width:85px">KODE MK</td>
        <td style="background:#369;color:#fff;text-align:center;width:215px">NAMA MK & DOSEN</td>
        <td style="background:#369;color:#fff;text-align:center;width:150px">SHIFT</td> 
    </tr>
<%	
	int norut=1;
	while(li.hasNext()) {
		String brs = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs,"`");
		//li.add(idkur+"`"+idkmk+"`"+thsmsNow+"`"+kdpst+"`"+shift+"`"+noKlsPll+"`"+npm_pertama_input+"`"+npm_terakhir_updat+"`"+status_akhir+"`"+curr_avail_status+"`"+locked_or_editable+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+canceled+"`"+kode_kelas+"`"+kode_ruang+"`"+kode_gedung+"`"+kode_kampus+"`"+tkn_day_time+"`"+nmmdos+"`"+nmmasdos+"`"+enrolled+"`"+max_enrolled+"`"+min_enrolled+"`"+sub_keter_kdkmk+"`"+init_req_time+"`"+tkn_npm_approval+"`"+tkn_approval_time+"`"+target_ttmhs+"`"+passed+"`"+rejected+"`"+kode_gabung_kls+"`"+kode_gabung_kmp+"`"+unique_id+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp);
		String idkur = st.nextToken();
		String idkmk = st.nextToken();
		String thsmsNow = st.nextToken();
		String kdpst = st.nextToken();
		String shift = st.nextToken();
		String noKlsPll = st.nextToken();
		String npm_pertama_input = st.nextToken();
		String npm_terakhir_updat = st.nextToken();
		String status_akhir = st.nextToken();
		String curr_avail_status = st.nextToken();
		String locked_or_editable = st.nextToken();
		String npmdos = st.nextToken();
		String nodos = st.nextToken();
		String npmasdos = st.nextToken();
		String noasdos = st.nextToken();
		String canceled = st.nextToken();
		String kode_kelas = st.nextToken();
		String kode_ruang = st.nextToken();
		String kode_gedung = st.nextToken();
		String kode_kampus = st.nextToken();
		String tkn_day_time = st.nextToken();
		String nmmdos = st.nextToken();
		String nmmasdos = st.nextToken();
		String enrolled = st.nextToken();
		String max_enrolled = st.nextToken();
		String min_enrolled = st.nextToken();
		String sub_keter_kdkmk = st.nextToken();
		String init_req_time = st.nextToken();
		String tkn_npm_approval = st.nextToken();
		String tkn_approval_time = st.nextToken();
		String target_ttmhs = st.nextToken();
		String passed = st.nextToken();
		String rejected = st.nextToken();
		String kode_gabung_kls = st.nextToken();
		String kode_gabung_kmp = st.nextToken();
		String unique_id = st.nextToken();
		String kdkmk = st.nextToken();
		String nakmk = st.nextToken();
		String skstm = st.nextToken();
		String skspr = st.nextToken();
		String skslp = st.nextToken();
		String konsen = st.nextToken();
		//http://localhost:8080/ToUnivSatyagama/InnerFrame/Akademik/get.listScope?scope=mba&callerPage=/ToUnivSatyagama/InnerFrame/Akademik/indexAkademik.jsp&atMenu=mba&cmd=mba&scopeType=prodiOnly
		
%>
	<tr> 
        <td style="text-align:center;"><%=norut++ %></td>
        <td style="text-align:center;"><%=Converter.getNamaKdpst(kdpst) %>
        <%
        if(!Checker.isStringNullOrEmpty(konsen)) {
        	out.print("<br/>konsentrasi<br/>"+konsen.toUpperCase());
        }
        %>
        </td>
        <td style="text-align:center;"><%=kdkmk %></td>
        <td style="text-align:center;"><%=nakmk+"<br/>("+nmmdos+")" %></td>
        <td style="text-align:center;">
        <a href="get.listBahanAjarDosen?unique_id=<%=unique_id %>&atMenu=pba&nakmk=<%=nakmk %>&kdkmk=<%=kdkmk %>&kdpst=<%=kdpst %>&idkur=<%=idkur %>&shift=<%=shift%>"><%=shift+"<br/>"+unique_id%></a>
        </td> 
    </tr>	
<%		
		
		//out.println(brs+"<br/>");
	}
%>
</table>
<%
}
else {
%>
	<h3 style="text-align:center">ANDA TIDAK TERDAFTAR SEBAGAI PENGAJAR PADA SEMESTER INI. <br/>HARAP HUBUNGI TU FAKULTAS BILA ADA KESALAHAN</h3>
<%
}
%>		
  
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>