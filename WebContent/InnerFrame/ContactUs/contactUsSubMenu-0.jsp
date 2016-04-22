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
	validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String callerPage = request.getParameter("callerPage");
	String atMenu = request.getParameter("atMenu");
	if(atMenu==null || atMenu.equalsIgnoreCase("null")) {
		atMenu = (String)request.getAttribute("atMenu");
	}
	//String cmd = request.getParameter("cmd");
	//caller page buat menentukan class-aktif
%>
<script type="text/javascript">
function goBack()
  {
  window.history.back()
  }
</script>

</head>
<body>
<ul>
<%

%>
<!--  li><a href="javascript:history.go(-1)" >BACK <span><b style="color:#eee">---</b></span></a></li -->
<%
//allowContactBAA#allowContactBAK#allowContactTU#allowContactSPMI#
if(validUsr.isAllowTo("allowContactBAA")>0) {
	String atMenuAndRedirectUrl = Constants.redirectObjectNickname(null,"allowContactBAA");
	//System.out.println("atMenuAndRedirectUrl1="+atMenuAndRedirectUrl);
	StringTokenizer st = new StringTokenizer(atMenuAndRedirectUrl,"||");
	String tgtMenu = st.nextToken();
	String redirectUrl = st.nextToken();
	String objNickname = st.nextToken(); //ditch - nanti overide di step berikutnya
	//String target = "goto.dashContactBaa";
	
	String target = ""+redirectUrl;
	String uri = request.getRequestURI();
	String url = PathFinder.getPath(uri, target);
	if(atMenu!=null && atMenu.equalsIgnoreCase(tgtMenu)) {
		%>		
		<li><a href="<%=target %>?atMenu=<%=tgtMenu %>&targetObjNickName=<%=objNickname%>" target="_self" class="active">BIRO ADMINISTRASI<span>AKADEMIK</b></span></a></li>
<%		
	}
	else {
%>		
		<li><a href="<%=target %>?atMenu=<%=tgtMenu %>&targetObjNickName=<%=objNickname%>" target="_self">BIRO ADMINISTRASI<span>AKADEMIK</b></span></a></li>
<%
	}
}
if(validUsr.isAllowTo("allowContactTU")>0) {
	String atMenuAndRedirectUrl = Constants.redirectObjectNickname(null,"allowContactTU");
	//System.out.println("atMenuAndRedirectUrlTU="+atMenuAndRedirectUrl);
	StringTokenizer st = new StringTokenizer(atMenuAndRedirectUrl,"||"); //goto.dashContactTu
	String tgtMenu = st.nextToken();
	String redirectUrl = st.nextToken();
	String objNickname = st.nextToken(); //ditch - nanti overide di step berikutnya
	//String target = "goto.dashContactBaa";
	//System.out.println(redirectUrl+"");
	String target = ""+redirectUrl;
	String uri = request.getRequestURI();
	String url = PathFinder.getPath(uri, target);
	if(atMenu!=null && atMenu.equalsIgnoreCase(tgtMenu)) {
		%>		
		<li><a href="<%=target %>?atMenu=<%=tgtMenu %>&targetObjNickName=<%=objNickname%>" target="_self" class="active">TATA USAHA<span>FAKULTAS</b></span></a></li>
<%		
	}
	else {
%>		
		<li><a href="<%=target %>?atMenu=<%=tgtMenu %>&targetObjNickName=<%=objNickname%>" target="_self">TATA USAHA<span>FAKULTAS</b></span></a></li>
<%
	}
}

//bak
if(validUsr.isAllowTo("allowContactBAK")>0) {
	String atMenuAndRedirectUrl = Constants.redirectObjectNickname(null,"allowContactBAK");
	//System.out.println("atMenuAndRedirectUrlTU="+atMenuAndRedirectUrl);
	StringTokenizer st = new StringTokenizer(atMenuAndRedirectUrl,"||"); //goto.dashContactXX
	String tgtMenu = st.nextToken();
	String redirectUrl = st.nextToken();
	String objNickname = st.nextToken(); //ditch - nanti overide di step berikutnya
	//String target = "goto.dashContactBaa";
	
	String target = ""+redirectUrl;
	String uri = request.getRequestURI();
	String url = PathFinder.getPath(uri, target);
	if(atMenu!=null && atMenu.equalsIgnoreCase(tgtMenu)) {
		%>		
		<li><a href="<%=target %>?atMenu=<%=tgtMenu %>&targetObjNickName=<%=objNickname%>" target="_self" class="active">BIRO ADMINISTRASI<span>KEUANGAN</b></span></a></li>
<%		
	}
	else {
%>		
		<li><a href="<%=target %>?atMenu=<%=tgtMenu %>&targetObjNickName=<%=objNickname%>" target="_self">BIRO ADMINISTRASI<span>KEUANGAN</b></span></a></li>
<%
	}
}


//web
if(validUsr.isAllowTo("allowContactWEB")>0) {
	String atMenuAndRedirectUrl = Constants.redirectObjectNickname(null,"allowContactWEB");
	StringTokenizer st = new StringTokenizer(atMenuAndRedirectUrl,"||"); //goto.dashContactXX
	String tgtMenu = st.nextToken();
	String redirectUrl = st.nextToken();
	String objNickname = st.nextToken(); //ditch - nanti overide di step berikutnya
	
	String target = ""+redirectUrl;
	String uri = request.getRequestURI();
	String url = PathFinder.getPath(uri, target);
	if(atMenu!=null && atMenu.equalsIgnoreCase(tgtMenu)) {
		%>		
		<li><a href="<%=target %>?atMenu=<%=tgtMenu %>&targetObjNickName=<%=objNickname%>" target="_self" class="active">WEB<span>DEVELOPMENT</b></span></a></li>
<%		
	}
	else {
%>		
		<li><a href="<%=target %>?atMenu=<%=tgtMenu %>&targetObjNickName=<%=objNickname%>" target="_self">WEB<span>DEVELOPMENT</b></span></a></li>
<%
	}
}


//mhs
if(validUsr.isAllowTo("allowContactMHS")>0) {
	String atMenuAndRedirectUrl = Constants.redirectObjectNickname(null,"allowContactMHS");
	StringTokenizer st = new StringTokenizer(atMenuAndRedirectUrl,"||"); //goto.dashContactXX
	String tgtMenu = st.nextToken();
	String redirectUrl = st.nextToken();
	String objNickname = st.nextToken(); //ditch - nanti overide di step berikutnya
	
	String target = ""+redirectUrl;
	String uri = request.getRequestURI();
	String url = PathFinder.getPath(uri, target);
	if(atMenu!=null && atMenu.equalsIgnoreCase(tgtMenu)) {
		%>		
		<li><a href="<%=target %>?atMenu=<%=tgtMenu %>&targetObjNickName=<%=objNickname%>" target="_self" class="active">MAHASISWA<span><b style="color:#eee">-</b> </span></a></li>
<%		
	}
	else {
%>		
		<li><a href="<%=target %>?atMenu=<%=tgtMenu %>&targetObjNickName=<%=objNickname%>" target="_self">MAHASISWA<span><b style="color:#eee">-</b> </span></a></li>
<%
	}
}


//tamu
if(validUsr.isAllowTo("allowContactTAMU")>0) {
	String atMenuAndRedirectUrl = Constants.redirectObjectNickname(null,"allowContactTAMU");
	StringTokenizer st = new StringTokenizer(atMenuAndRedirectUrl,"||"); //goto.dashContactXX
	String tgtMenu = st.nextToken();
	String redirectUrl = st.nextToken();
	String objNickname = st.nextToken(); //ditch - nanti overide di step berikutnya
	
	String target = ""+redirectUrl;
	String uri = request.getRequestURI();
	String url = PathFinder.getPath(uri, target);
	if(atMenu!=null && atMenu.equalsIgnoreCase(tgtMenu)) {
		%>		
		<li><a href="<%=target %>?atMenu=<%=tgtMenu %>&targetObjNickName=<%=objNickname%>" target="_self" class="active">TAMU &<span>CALON MHS</b></span></a></li>
<%		
	}
	else {
%>		
		<li><a href="<%=target %>?atMenu=<%=tgtMenu %>&targetObjNickName=<%=objNickname%>" target="_self">TAMU &<span>CALON MHS</b></span></a></li>
<%
	}
}

//rek
if(validUsr.isAllowTo("allowContactREK")>0) {
	String atMenuAndRedirectUrl = Constants.redirectObjectNickname(null,"allowContactREK");
	StringTokenizer st = new StringTokenizer(atMenuAndRedirectUrl,"||"); //goto.dashContactXX
	String tgtMenu = st.nextToken();
	String redirectUrl = st.nextToken();
	String objNickname = st.nextToken(); //ditch - nanti overide di step berikutnya
	
	String target = ""+redirectUrl;
	String uri = request.getRequestURI();
	String url = PathFinder.getPath(uri, target);
	if(atMenu!=null && atMenu.equalsIgnoreCase(tgtMenu)) {
		%>		
		<li><a href="<%=target %>?atMenu=<%=tgtMenu %>&targetObjNickName=<%=objNickname%>" target="_self" class="active">REKTOR<span><b style="color:#eee">-</b></b></span></a></li>
<%		
	}
	else {
%>		
		<li><a href="<%=target %>?atMenu=<%=tgtMenu %>&targetObjNickName=<%=objNickname%>" target="_self">REKTOR<span><b style="color:#eee">-</b></span></a></li>
<%
	}
}

//warek
if(validUsr.isAllowTo("allowContactWRK")>0) {
	String atMenuAndRedirectUrl = Constants.redirectObjectNickname(null,"allowContactWRK");
	StringTokenizer st = new StringTokenizer(atMenuAndRedirectUrl,"||"); //goto.dashContactXX
	String tgtMenu = st.nextToken();
	String redirectUrl = st.nextToken();
	String objNickname = st.nextToken(); //ditch - nanti overide di step berikutnya
	
	String target = ""+redirectUrl;
	String uri = request.getRequestURI();
	String url = PathFinder.getPath(uri, target);
	if(atMenu!=null && atMenu.equalsIgnoreCase(tgtMenu)) {
		%>		
		<li><a href="<%=target %>?atMenu=<%=tgtMenu %>&targetObjNickName=<%=objNickname%>" target="_self" class="active">WAREK<span><b style="color:#eee">-</b></b></span></a></li>
<%		
	}
	else {
%>		
		<li><a href="<%=target %>?atMenu=<%=tgtMenu %>&targetObjNickName=<%=objNickname%>" target="_self">WAREK<span><b style="color:#eee">-</b></span></a></li>
<%
	}
}

//kaprodi
if(validUsr.isAllowTo("allowContactKPR")>0) {
	String atMenuAndRedirectUrl = Constants.redirectObjectNickname(null,"allowContactKPR");
	StringTokenizer st = new StringTokenizer(atMenuAndRedirectUrl,"||"); //goto.dashContactXX
	String tgtMenu = st.nextToken();
	String redirectUrl = st.nextToken();
	String objNickname = st.nextToken(); //ditch - nanti overide di step berikutnya
	
	String target = ""+redirectUrl;
	String uri = request.getRequestURI();
	String url = PathFinder.getPath(uri, target);
	if(atMenu!=null && atMenu.equalsIgnoreCase(tgtMenu)) {
		%>		
		<li><a href="<%=target %>?atMenu=<%=tgtMenu %>&targetObjNickName=<%=objNickname%>" target="_self" class="active">KAPRODI<span><b style="color:#eee">-</b></b></span></a></li>
<%		
	}
	else {
%>		
		<li><a href="<%=target %>?atMenu=<%=tgtMenu %>&targetObjNickName=<%=objNickname%>" target="_self">KAPRODI<span><b style="color:#eee">-</b></span></a></li>
<%
	}
}

//personalia
if(validUsr.isAllowTo("allowContactPRS")>0) {
	String atMenuAndRedirectUrl = Constants.redirectObjectNickname(null,"allowContactPRS");
	StringTokenizer st = new StringTokenizer(atMenuAndRedirectUrl,"||"); //goto.dashContactXX
	String tgtMenu = st.nextToken();
	String redirectUrl = st.nextToken();
	String objNickname = st.nextToken(); //ditch - nanti overide di step berikutnya
	
	String target = ""+redirectUrl;
	String uri = request.getRequestURI();
	String url = PathFinder.getPath(uri, target);
	if(atMenu!=null && atMenu.equalsIgnoreCase(tgtMenu)) {
		%>		
		<li><a href="<%=target %>?atMenu=<%=tgtMenu %>&targetObjNickName=<%=objNickname%>" target="_self" class="active">PERSONALIA<span><b style="color:#eee">-</b></b></span></a></li>
<%		
	}
	else {
%>		
		<li><a href="<%=target %>?atMenu=<%=tgtMenu %>&targetObjNickName=<%=objNickname%>" target="_self">PERSONALIA<span><b style="color:#eee">-</b></span></a></li>
<%
	}
}

//admisi dan promosi
if(validUsr.isAllowTo("allowContactPMB")>0) {
	String atMenuAndRedirectUrl = Constants.redirectObjectNickname(null,"allowContactPMB");
	StringTokenizer st = new StringTokenizer(atMenuAndRedirectUrl,"||"); //goto.dashContactXX
	String tgtMenu = st.nextToken();
	String redirectUrl = st.nextToken();
	String objNickname = st.nextToken(); //ditch - nanti overide di step berikutnya
	
	String target = ""+redirectUrl;
	String uri = request.getRequestURI();
	String url = PathFinder.getPath(uri, target);
	if(atMenu!=null && atMenu.equalsIgnoreCase(tgtMenu)) {
		%>		
		<li><a href="<%=target %>?atMenu=<%=tgtMenu %>&targetObjNickName=<%=objNickname%>" target="_self" class="active">ADMISI DAN<span>PROMOSI</span></a></li>
<%		
	}
	else {
%>		
		<li><a href="<%=target %>?atMenu=<%=tgtMenu %>&targetObjNickName=<%=objNickname%>" target="_self">ADMISI DAN<span>PROMOSI</span></a></li>
<%
	}
}

%>
	
	
</ul>

</body>
</html>