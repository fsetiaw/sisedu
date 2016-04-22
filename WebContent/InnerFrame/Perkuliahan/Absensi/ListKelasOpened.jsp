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
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%
//System.out.println("yap1");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v= null;
//li.add(idkur+"`"+idkmk+"`"+kdpst+"`"+shift+"`"+noKlsPll+"`"+npm_pertama_input+"`"+npm_terakhir_updat+"`"+status_akhir+"`"+curr_avail_status+"`"+locked_or_editable+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+canceled+"`"+kode_kelas+"`"+kode_ruang+"`"+kode_gedung+"`"+kode_kampus+"`"+tkn_day_time+"`"+nmmdos+"`"+nmmasdos+"`"+enrolled+"`"+max_enrolled+"`"+min_enrolled+"`"+sub_keter_kdkmk+"`"+init_req_time+"`"+tkn_npm_approval+"`"+tkn_approval_time+"`"+target_ttmhs+"`"+passed+"`"+rejected+"`"+kode_gabung_kls+"`"+kode_gabung_kmp+"`"+unique_id+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp);
Vector vListKelasDibuka = (Vector)session.getAttribute("vListKelasDibuka");
//String kode_kampus_req = (String) request.getParameter("kode_kampus");
//String nick_kampus_req = (String) request.getParameter("nick_kampus");
//Vector vKampus = (Vector) request.getAttribute("vKampus");
//ListIterator li1 = vKampus.listIterator();
String target_kmp = request.getParameter("target_kmp");//TIDAK mungkin NULL kalo NULL berarti pada tabel OBJECT belum ada kode kampus domisilinya 
String target_thsms = request.getParameter("target_thsms");
if(target_thsms==null || Checker.isStringNullOrEmpty(target_thsms)) {
	target_thsms = Checker.getThsmsNow();
}
//while(li1.hasNext()) {
//	System.out.println("nama kampus = "+(String)li1.next());
//}
//li1 = vKampus.listIterator();
String initKodeKampus = null;
String kode_kampus = null;
String nick_kampus = null;

String tknScopeKampus = validUsr.getScopeKampus("viewAbsen");
//System.out.println("tknScopeKampus="+tknScopeKampus);
//request.removeAttribute("listTipeUjian");
//String atMenu = request.getParameter("atMenu");
%>
<script type="text/javascript">
</script>
</head>
<body>
<div id="header">
	<ul>
	<%
	//String target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/DashPerkuliahan.jsp";
	//String 	uri = request.getRequestURI();
	//System.out.println("target=="+target+" / "+uri);
	//String url = PathFinder.getPath(uri, target);
	//System.out.println("url=="+url);
	%>
		<li><a href="DashPerkuliahan.jsp" target="inner_iframe">GO<span>BACK</span></a></li>
<%
//if(kode_kampus_req==null) {
	
if(tknScopeKampus!=null && !Checker.isStringNullOrEmpty(tknScopeKampus)) {
	StringTokenizer st = new StringTokenizer(tknScopeKampus,",");
	while(st.hasMoreTokens()) {
		
			
		kode_kampus = new String(st.nextToken());
		nick_kampus = new String(Tool.getTokenKe(Checker.getNickNameKampus(kode_kampus), 2, "`"));
		if(target_kmp.equalsIgnoreCase(kode_kampus)) {
		%>
		<li><a href="get.getListOpenedClass?target_kmp=<%=kode_kampus %>&cmd=viewAbsen&atMenu=absensi&targetThsms=<%=Checker.getThsmsNow() %>" target="inner_iframe" class="active">KAMPUS<span><%=nick_kampus.replace("KAMPUS ", "") %></span></a></li>
		<%		
		}
		else {
		%>
		<li><a href="get.getListOpenedClass?target_kmp=<%=kode_kampus %>&cmd=viewAbsen&atMenu=absensi&targetThsms=<%=Checker.getThsmsNow() %>" target="inner_iframe" >KAMPUS<span><%=nick_kampus.replace("KAMPUS ", "") %></span></a></li>
		<%
		}
	}
}
	
%>		
		
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
				
<br/>
		<%
if(vListKelasDibuka!=null && vListKelasDibuka.size()>0 ) {
	boolean first = true;
	boolean match_kampus = false;
	if(target_kmp!=null && !Checker.isStringNullOrEmpty(target_kmp)) {
		ListIterator li = vListKelasDibuka.listIterator();
		int norut = 1;
		
		do {
			String brs = (String)li.next();
			StringTokenizer st = new StringTokenizer(brs,"`");
			String idkur = st.nextToken();
			String idkmk = st.nextToken();
			//thsms ignoeee
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
			String kode_kampus_ = st.nextToken();
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
			if(kode_kampus_.equalsIgnoreCase(target_kmp)) {
				if(first) {
					first = false;
					match_kampus = true;
					%>
					<br/>
					<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px">
						<!--  tr>
					        <td style="background:#369;color:#fff;text-align:center" colspan="8"><label><B>PRODI </B> </label></td>
					    </tr -->
			<tr>
	    		<td style="background:#369;color:#fff;text-align:center;font-size:1.7em" colspan="8"><label><B>LIST PERKULIAHAN <%=Converter.convertThsmsKeterOnly(target_thsms) %></B> </label></td>
	    	</tr>		    
			<tr>
				<td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>NO.</B> </label></td>
				<td style="background:#369;color:#fff;text-align:left;width:275px"><label><B>MATAKULIAH</B> </label></td>
				<td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>PRODI</B> </label></td>
				<td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>JEN</B> </label></td>
				<td style="background:#369;color:#fff;text-align:center;width:75px"><label><B>SHIFT</B> </label></td>
				<td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>NO KLS PARALEL</B> </label></td>
				<td style="background:#369;color:#fff;text-align:center;width:200px"><label><B>NAMA DOSEN</B> </label></td>
				
				<td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>LIST MHS</B> </label></td>
			</tr>
				<%					
				}
			
				%>	 	
			<tr>	
				<td style="color:#000;text-align:center;"><label><B><%=norut++ %></B> </label></td>
			    <td style="color:#000;text-align:left;"><label><B><%=kdkmk+" - "+nakmk %></B> </label></td>
			    <td style="color:#000;text-align:center;"><label><B><%=Converter.getNamaKdpst(kdpst) %></B> </label></td>
			    <td style="color:#000;text-align:center;"><label><B><%=Converter.getDetailKdjen(Converter.getKdjen(kdpst)) %></B> </label></td>
			    <td style="color:#000;text-align:center;"><label><B><%=shift %></B> </label></td>
			    <td style="color:#000;text-align:center;"><label><B><%=noKlsPll %></B> </label></td>
			    <td style="color:#000;text-align:center;"><label><B><%=nmmdos %></B> </label></td>
			    
			    <td style="color:#000;text-align:center;"><label>
			    <FORM action="get.getDaftarHadir" method="post">
			    	<input type="hidden" name="kelasInfo" value="<%=target_kmp+"`"+kdkmk+"`"+nakmk+"`"+nmmdos+"`"+shift+"`"+unique_id+"`"+idkmk+"`"+idkur+"`"+kdpst+"`"+noKlsPll+"`"+target_thsms %>" />
			        <input type="submit" value="Next" style="width:99%;"/>
			    </FORM>
			    </td>
			</tr>
		<%

			}
		}
		while(li.hasNext());
		if(match_kampus) {
	%>
		</table>
<%	
		}
		else {
			out.println("<h2 align=\"center\">BELUM ADA KELAS PERKULIAHAN YANG DIBUKA / DIAJUKAN</h2>");
		}
	}
%>	
	<br/>
<%		
}
else {
	out.print("<h1>KELAS PERKULIAHAN BELUM ADA YANG DIBUKA</h1>");
}

%>
		<!-- Column 1 end -->
		
	</div>
</div>

</body>
</html>