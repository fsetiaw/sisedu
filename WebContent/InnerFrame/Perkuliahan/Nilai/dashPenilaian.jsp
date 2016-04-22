<!DOCTYPE html>
<%
//try {
%>
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
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%

beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
//String scopeHakAkses= request.getParameter("scopeHakAkses");
//String scopeKmp=request.getParameter("scopeKmp");
//Vector vListKelas = (Vector) request.getAttribute("vListKelas");


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
		
	<br/><br/>
		<%
		if(vKlsPenilaian!=null && vKlsPenilaian.size()>0) {
			String thsms_inp_nilai = Checker.getThsmsInputNilai();
			//System.out.println("thsms_inp_nilai="+thsms_inp_nilai);
			int j=0;
		%>
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px">
	    	<tr>
	    		<td style="background:#369;color:#fff;text-align:center;font-size:1.7em" colspan="8"><label><B>LIST PERKULIAHAN <%=Converter.convertThsmsKeterOnly(thsms_inp_nilai) %></B> </label></td>
	    	</tr>
	    	<tr>
    		    <td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>NO.</B> </label></td>
        		<td style="background:#369;color:#fff;text-align:left;width:300px"><label><B>MATAKULIAH</B> </label></td>
        		<td style="background:#369;color:#fff;text-align:center;width:150px"><label><B>PRODI</B> </label></td>
        		<td style="background:#369;color:#fff;text-align:center;width:100px"><label><B>INFO KELAS</B> </label></td>
        		<td style="background:#369;color:#fff;text-align:center;width:200px"><label><B>NAMA DOSEN</B> </label></td>
    		</tr>
		<%	
			ListIterator li = vKlsPenilaian.listIterator();
			while(li.hasNext()) {
				String brs = (String) li.next();
				StringTokenizer st = new StringTokenizer(brs,"`");
				//kdpst+"`"+nakmk+"`"+idkur+"`"+idkmk+"`"+shift+"`"+thsms+"`"+nopll+"`"+initNpmInp+"`"+lasNpmUpd+"`"+lasStatInf+"`"+curAvailStat+"`"+locked+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+batal+"`"+kodeKls+"`"+kodeRuang+"`"+kodeGdg+"`"+kodeKmp+"`"+tknDayTime+"`"+nmmDos+"`"+nmmAsdos+"`"+totEnrol+"`"+maxEnrol+"`"+minEnrol+"`"+subKeterKdkmk+"`"+initReqTime+"`"+tknNpmApr+"`"+tknAprTime+"`"+targetTtmhs+"`"+passed+"`"+rejected+"`"+kodeGabung+"`"+kodeGabungUniv+"`"+cuid+"`"+kdkmk);
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
				/*
				String idkur = st.nextToken();
				String idkmk = st.nextToken();
				String thsms_inp_nilai = st.nextToken();
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
				*/
		%>
			<tr>
        		<td style="color:#000;text-align:center;"><label><B><%= ++j %></B> </label></td>
        		<td style="color:#000;text-align:left;"><label><B>
        		<%
        		//if(bolehEdit) {
        			//getListMhs(String thsms, String idkmk,String uniqueId, String kdkmk, String nakmk, String shift)
        		%>
        			<a href="get.listMhsUtkPenilaian?thsms=<%=thsms_inp_nilai %>&idkmk=<%=idkmk %>&uniqueId=<%=cuid %>&kdkmk=<%=kdkmk %>&nakmk=<%=nakmk %>&shift=<%=shift %>&noKlsPll=<%=nopll %>&kode_kelas=<%=kodeKls %>&kode_gedung=<%=kodeGdg %>&kode_kampus=<%=kodeKmp %>&kode_gabung_kls=<%=kodeGabung %>&kode_gabung_kmp=<%=kodeGabungUniv %>&skstm=<%="0" %>&skspr=<%="0" %>&skslp=<%="0" %>&bolehEdit=<%=bolehEditNilai%>&nmmdos=<%=nmmDos %>&npmdos=<%=npmdos %>"><%= kdkmk+" - "+nakmk%></a>
        		<%	
        		//}
        		//else {
        		//	out.print(kdkmk+" - "+nakmk);
        		//}	
        		%>
        		</B> </label></td>
        		<td style="color:#000;text-align:center;"><label><B><%=Converter.getNamaKdpst(kdpst) %></B> </label></td>
        		<td style="color:#000;text-align:center;"><label><B><%=shift %></B> </label></td>
        		<td style="color:#000;text-align:center;"><label><B><%=nmmDos %></B> </label></td>
        	</tr>	
		<%		
			}
		%>
		</table>
		<%
		}
		%>

		<!-- Column 1 end -->
	</div>
</div>

</body>
<%
//}
//catch(Exception e) {
//	out.println("ADA ERROR @nilai.dashPenilaian.jsp");
//}
%>
</html>