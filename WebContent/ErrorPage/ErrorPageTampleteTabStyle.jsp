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
	String alasan = request.getParameter("alasan");
 	String objId=request.getParameter("objId");
 	String nmm=request.getParameter("nmm");
 	String npm=request.getParameter("npm");
 	String kdpst=request.getParameter("kdpst");
 	String objLvl=request.getParameter("objLvl");
 	String backTo=request.getParameter("backTo");
 	String cmd=request.getParameter("cmd");
	String msg = request.getParameter("msg");
	String atMenu = request.getParameter("atMenu");
	String tipe = request.getParameter("tipe");
	//out.print("objId="+objId);
	/*
	if(backTo!=null) {
		backTo = backTo.replace("tandaDan","&");
		backTo = backTo.replace("tandaKurungBuka","<%=");
		backTo = backTo.replace("tandaKurungTutup","% >");
		backTo = backTo.replace(" ","");
		backTo = backTo.replace("tandaTanya","?");
	}
	*/
	//backTo = backToreplace("tandaDan","&");
	//backTo = backToreplace("tandaDan","&");
	//System.out.print(backTo);
%>
</head>
<body>
<div id="header">
	<ul>
		<li><a href="get.histKrs?id_obj=<%=objId%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=objLvl %>&kdpst=<%=kdpst %>&cmd=histKrs" target="inner_iframe">GO<span>BACK</span></a></li>
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<%
		
		%>
 		<div style="text-align:center;font-size:2em"><%=msg %></div>	
 		<%
 		if(msg.contains("KRS") && msg.contains("TERKUNCI") && (tipe==null || !tipe.equalsIgnoreCase("msgonly"))) {
 		%>
 		<BR/>
 		ALASAN PENGAJUAN PENGISIAN KRS BARU:
 		<form action="ask.reqUnlockInsKrsForm">
 		<input type="hidden" name="objId" value="<%=objId%>" />
 		<input type="hidden" name="nmm" value="<%=nmm%>" />
 		<input type="hidden" name="npm" value="<%=npm%>" />
 		<input type="hidden" name="kdpst" value="<%=kdpst%>" />
 		<input type="hidden" name="objLvl" value="<%=objLvl%>" />
 		<input type="hidden" name="msg" value="<%=msg%>"/>
 		<TEXTAREA rows="4" style="width:98%" name="alasan">
 		<%
 			if(alasan!=null && !Checker.isStringNullOrEmpty(alasan)) {
 				out.print(alasan);
 			}
 			
 		%>
 		
 		</TEXTAREA>
 		<br/>
 		<input type="submit" value="Klik Untuk Mengajukan Permohonan KRS Baru" style="width:98%"/>
 		</form>
 		<%	
 		} 		
 		%>			
	</div>
</div>		
</body>
</html>