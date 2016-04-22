<!DOCTYPE html>
<head>
<%@ page import="beans.dbase.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.login.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<title>Insert title here</title>
<%
	
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector vf = (Vector)request.getAttribute("vf");
//request.removeAttribute("vf");
session.setAttribute("vf", vf);

//ListIterator lif = null;
if(vf!=null && vf.size()>0) {
//	lif=vf.listIterator();
	System.out.println("vfsize = "+vf.size());
}
else {
	System.out.println("vfsize = empty");
}
%>


</head>
<body onload="location.href='#'">
<%
/*
if(lif.hasNext()) {
	//out.print("size = "+vf.size()+"<br />");
	String baris1 = (String)lif.next();
	String baris2 = (String)lif.next();
	Vector vInfoMhs = (Vector)lif.next();
	Vector vInfoAng = (Vector)lif.next();
	ListIterator lia = vInfoAng.listIterator();
	//System.out.println("1."+baris1);
	StringTokenizer st = new StringTokenizer(baris1,",");
	String prev_kdpst = st.nextToken();
	System.out.println("FAKULTAS "+prev_kdpst);
	System.out.println("===================================");
	String nmpst = st.nextToken();
	nmpst = nmpst.substring(4,nmpst.length());
	st = new StringTokenizer(baris2,"#");
	String kpstmk = st.nextToken();
	String idkmk = st.nextToken();
	String kdkmk = st.nextToken();
	String nakmk = st.nextToken();
	String sksmk = st.nextToken();
	System.out.println(kdkmk+" "+nakmk+" "+sksmk);
	//System.out.println("3."+vInfoMhs.size());
	while(lia.hasNext()) {
		String tmp = (String)lia.next();
		System.out.println(tmp);
	}
	while(lif.hasNext()) {
		baris1 = (String)lif.next();
		baris2 = (String)lif.next();
		vInfoMhs = (Vector)lif.next();
		vInfoAng = (Vector)lif.next();
		lia = vInfoAng.listIterator();
		st = new StringTokenizer(baris1,",");
		String kdpst = st.nextToken();
		if(!kdpst.equalsIgnoreCase(prev_kdpst)) {
			//beda fakultas
			System.out.println("===================================<br /><br />");
			System.out.println("FAKULTAS "+kdpst);
			prev_kdpst = ""+kdpst;
			System.out.println("===================================");
		}
		nmpst = st.nextToken();
		nmpst = nmpst.substring(4,nmpst.length());
		st = new StringTokenizer(baris2,"#");
		kpstmk = st.nextToken();
		idkmk = st.nextToken();
		kdkmk = st.nextToken();
		nakmk = st.nextToken();
		sksmk = st.nextToken();
		System.out.println(kdkmk+" "+nakmk+" "+sksmk);
		while(lia.hasNext()) {
			String tmp = (String)lia.next();
			System.out.println(tmp);
		}
	}
	System.out.println("===================================");
}
*/
%>
<div id="header">
	<ul>
		<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Analisa/subAnalisaMakulMhsMenu.jsp" />
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		dasboard viwer
	</div>
</div>		
</body>
</html>