<!DOCTYPE html>
<html>
<head>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.Constants" %>
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ page import="beans.tools.*" %>
</head>
<body onload="location.href='#'">
<div class="colmask fullpage">
	<div class="col1">
		<!-- Column 1 start -->
<%

beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String opnpm = validUsr.getNpm();
String opnmm = validUsr.getFullname();
String form_type=request.getParameter("form_type");
String objId=request.getParameter("id_obj");
String obj_lvl=request.getParameter("obj_lvl");
String kdpst=request.getParameter("kdpst");
String inp_nmm=request.getParameter("inp_nmm");
String inp_npm=request.getParameter("inp_npm");
String accnt=request.getParameter("account");
String inp_tgtrs=request.getParameter("inp_tgtrs");
String inp_payee=request.getParameter("inp_payee");
String inp_amnt=request.getParameter("inp_amnt");
String inp_bukti=request.getParameter("inp_bukti");
String stamps = request.getParameter("stamps");
String inp_keter = request.getParameter("inp_keter");
String tipeTransaksi = request.getParameter("tipeTransaksi");
boolean tunai = true;
try {
	java.sql.Date trs = java.sql.Date.valueOf(inp_tgtrs);
	tunai = false;
}
catch (Exception e) {}
%>
<h1>PREVIEW TRANSAKSI</h1>
<%
if(tunai) {
%>
<h3>TRANSAKSI TUNAI</h3>
<%	
}
else {
%>
<h3>TRANSAKSI BANK (NON-TUNAI)</h3>
<%		
}
%>
<form action="go.insertPymntTable">
<input type="hidden" value="<%=opnpm%>" name="opnpm" />
<input type="hidden" value="<%=opnmm%>" name="opnmm" />
<input type="hidden" value="<%=form_type%>" name="form_type" />
<input type="hidden" value="<%=objId%>" name="id_obj" />
<input type="hidden" value="<%=obj_lvl%>" name="obj_lvl" />
<input type="hidden" value="<%=kdpst%>" name="kdpst" />
<input type="hidden" value="<%=inp_nmm%>" name="inp_nmm" />
<input type="hidden" value="<%=inp_npm%>" name="inp_npm" />
<input type="hidden" value="<%=accnt%>" name="accnt" />
<input type="hidden" value="<%=inp_tgtrs%>" name="inp_tgtrs" />
<input type="hidden" value="<%=inp_payee%>" name="inp_payee" />
<input type="hidden" value="<%=inp_amnt%>" name="inp_amnt" />
<input type="hidden" value="<%=inp_bukti%>" name="inp_bukti" />
<input type="hidden" value="<%=stamps%>" name="stamps" />
<input type="hidden" value="<%=inp_keter%>" name="inp_keter" />
<input type="hidden" value="<%=tipeTransaksi%>" name="tipeTransaksi" />
<table>
	<tr>
		<td>PEMBAYARAN <%=inp_keter %> MAHASISWA : <b> <%=inp_nmm %>(<%=inp_npm %>) </b><br/>
			BESARAN : <b>Rp.<%=beans.tools.NumberFormater.indoNumberFormat(inp_amnt) %></b><br />
<% 			
		if(!tunai) {
%>
			TGL TRANSFER : <b>
			<%
			StringTokenizer st = new StringTokenizer(inp_tgtrs,"-"); 
			String yy = st.nextToken();
			String mm = st.nextToken();
			if(mm.startsWith("0")) {
				mm = mm.substring(1,mm.length());
			}
			String month = Converter.convertIntToNamaBulan(Integer.valueOf(mm).intValue());
			String dd = st.nextToken();
			out.println(dd+" "+month+" "+yy);
			%></b><br/>
			BANK TUJUAN : <b><%=accnt %></b>
<%	
		}
%>
		</td>
	</tr>
	<tr>
		<td>Klik panah ke kiri untuk edit atau <input type="submit" value="klik disini" /> untuk input transaksi</td>
	</tr>
</table>
 </form>
 	</div>
 </div>	
</body>
</html>