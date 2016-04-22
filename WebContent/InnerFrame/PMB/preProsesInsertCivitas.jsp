<!DOCTYPE html >
<html>
<head>
<%@ page import="java.util.Vector" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="java.util.Collections" %>
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/ToUnivSatyagama/forms/simplePmb.css" media="screen" />
<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String obj_id = null;
	String kdpst = null;
	String[]listUjian = request.getParameterValues("listUjian");
	//System.out.println("preposesinscivi");
	String tknUjian = "";
	if(listUjian!=null) {
		for(int i=0;i<listUjian.length;i++) {
			tknUjian = tknUjian+listUjian[i];
			if(i<listUjian.length) {
				tknUjian=tknUjian+"||";
			}
			//System.out.println(i+"."+listUjian[i]);
		}
	}
	//System.out.println("tknUjian=="+tknUjian);
	String objid_kdpst = request.getParameter("objid_kdpst");
	if(objid_kdpst!=null) {
		StringTokenizer st = new StringTokenizer(objid_kdpst,"-");
		obj_id = st.nextToken();
		kdpst = st.nextToken();
	}	
	String smawl = request.getParameter("smawl");
	//System.out.println("preProses smawl = "+smawl);
	String nama = request.getParameter("nama");
	String nim = request.getParameter("nim");
	String shiftKls = request.getParameter("shiftKls");
	String kdjek = request.getParameter("kdjek");
	String stpid = request.getParameter("stpid");
	String agama = request.getParameter("agama");
	String tplhr = request.getParameter("tplhr");
	String nglhr = request.getParameter("nglhr");
	String tglhr = request.getParameter("tglhr");
	
	String email = request.getParameter("email");
	String noHp = request.getParameter("hp");
	String aspti = request.getParameter("aspti");
	
	String pesan = request.getParameter("pesan");
	
%>
</head>
<body>
<h2 align="center">Sedang Memproses Data, Harap Menunggu</h2>
<% 
if(pesan==null) {
%>
	<meta http-equiv="refresh" content="2;url=simple.insertCivitasSimple?callerPage=preProsesInsertCivitas&objid_kdpst=<%=objid_kdpst %>&smawl=<%=smawl %>&nama=<%=nama %>&nim=<%=nim %>&kdjek=<%=kdjek %>&stpid=<%=stpid %>&agama=<%=agama %>&tplhr=<%=tplhr %>&nglhr=<%=nglhr %>&tglhr=<%=tglhr %>&email=<%=email %>&hp=<%=noHp %>&aspti=<%=aspti %>&shiftKls=<%=shiftKls%>&tknUjian=<%=tknUjian%>">
<%
}
else {
	if(pesan.equalsIgnoreCase("sukses")) {
%>
		<h2 align="center">Data "<%=nama %>" BERHASIL DI INPUT / UPDATE</h2>
		<meta http-equiv="refresh" content="2;url=<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/PMB/pmb_index.jsp?atMenu=insCiv">
<%
	}
	else {
		if(pesan.equalsIgnoreCase("gagal")) {
			%>
				<h2 align="center">ERROR : Data "<%=nama %>" GAGAL di-input</h2>
				<meta http-equiv="refresh" content="2;url=<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/PMB/pmb_index.jsp?atMenu=insCiv">
			<%
		}
		else {
			if(pesan.equalsIgnoreCase("suksesdosen")) {
				%>
				<h2 align="center">Data DOSEN BERHASIL DI INPUT / UPDATE</h2>
				<meta http-equiv="refresh" content="2;url=<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/PMB/pmb_index.jsp?atMenu=insCiv">
				<%
			}
			else {
			%>
				<h2 align="center">ERROR : Data "<%=nama %>" GAGAL di-input<br/>
				<%=pesan %></h2>
				<meta http-equiv="refresh" content="5;url=<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/PMB/pmb_index.jsp?atMenu=insCiv">
			<%
			}
		}
	}
}
%>
</body>
</html>