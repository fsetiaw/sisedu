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
Vector vf = (Vector)session.getAttribute("vf");
//request.removeAttribute("vf"); //remove di index juga
//request.setAttribute("vf", vf);
System.out.println("vsize="+vf.size());
String target_kdpst = request.getParameter("kdpst");
String target_nmpst = request.getParameter("nmpst");
ListIterator lif = null;
if(vf!=null && vf.size()>0) {
	lif=vf.listIterator();
}
%>


</head>
<body onload="location.href='#'">
<div id="header">
	<ul>
		<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Analisa/subAnalisaMakulMhsMenu.jsp" />
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />

		
<%
int i = 0;
boolean first = true, pit2 = true;
if(lif.hasNext()) {
	%>
	<table align="center" border="1" style="color:#000;width:550px;background:#d9e1e5;">	
	<%
	String baris1 = (String)lif.next();
	String baris2 = (String)lif.next();
	Vector vInfoMhs = (Vector)lif.next();
	Vector vInfoAng = (Vector)lif.next();
	ListIterator lia = vInfoAng.listIterator();
	//System.out.println("1."+baris1);
	StringTokenizer st = new StringTokenizer(baris1,",");
	String prev_kdpst = st.nextToken();
	String nmpst = st.nextToken();
	nmpst = nmpst.substring(4,nmpst.length());
	st = new StringTokenizer(baris2,"#");
	String kpstmk = st.nextToken();
	String idkmk = st.nextToken();
	String kdkmk = st.nextToken();
	String nakmk = st.nextToken();
	String sksmk = st.nextToken();
	
	if(target_kdpst.equalsIgnoreCase(prev_kdpst)) {
		i++;
		if(first) {		
			first = false;
		%>
		<tr>
			<td style="background:#369;color:#fff;text-align:center;width:15px" ><b>NO</b></td>
			<td style="background:#369;color:#fff;text-align:center;width:85px" ><b>KODE MK</b></td>
			<td style="background:#369;color:#fff;text-align:left;width:430px" ><b>MATAKULIAH</b></td>
			<td style="background:#369;color:#fff;text-align:center;width:20px" ><b>SKS</b></td>
		</tr>
		<%
		}
		%>
		<tr>
			<td style="text-align:left;width:15px" ><%=i %>.</td>
			<td style="text-align:center;width:85px" ><%=kdkmk %></td>
			<td style="text-align:left;width:430px" ><%=nakmk %></td>
			<td style="text-align:center;width:20px" ><%=sksmk %></td>
		</tr>
		<%
		//out.println("FAKULTAS "+nmpst+"<br/>");
		
		//out.println("==================================="+"<br/>");
		//out.println(kdkmk+" "+nakmk+" "+sksmk+"<br/>");
	//System.out.println("3."+vInfoMhs.size());
		while(lia.hasNext()) {
			if(pit2) {
				pit2 = false;
				%>
				<tr>
					<td colspan="2" style="background:#369;color:#fff;text-align:center;" ><b>ANGKATAN</b></td>
					<td colspan="2" style="background:#369;color:#fff;text-align:center;" ><b>TOTAL MHS YG BELUM MENGAMBIL MATAKULIAH INI</b></td>
				</tr>
				<%
			}
			String tmp = (String)lia.next();
			StringTokenizer stt = new StringTokenizer(tmp);
			String smawl = stt.nextToken();
			String ttmhs = stt.nextToken();
			%>
			<tr>
				<td colspan="2" style="text-align:center;" ><b><%=smawl %></b></td>
				<td colspan="2" style="text-align:center;" ><b><%=ttmhs %></b></td>
			</tr>
			<%
			//out.println(tmp);
		}
		first = true;
		pit2 = true;
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
		//	System.out.println("===================================<br /><br />");
		//	if(target_kdpst.equalsIgnoreCase(prev_kdpst)) {
		
			prev_kdpst = ""+kdpst;
			
		//	System.out.println("===================================");
		}
		nmpst = st.nextToken();
		nmpst = nmpst.substring(4,nmpst.length());
		st = new StringTokenizer(baris2,"#");
		kpstmk = st.nextToken();
		idkmk = st.nextToken();
		kdkmk = st.nextToken();
		nakmk = st.nextToken();
		sksmk = st.nextToken();
		
		if(target_kdpst.equalsIgnoreCase(kdpst)) {
			i++;
			if(first) {
				first = false;
			%>
			<tr>
				<td style="background:#369;color:#fff;text-align:center;width:15px" ><b>NO</b></td>
				<td style="background:#369;color:#fff;text-align:center;width:85px" ><b>KODE MK</b></td>
				<td style="background:#369;color:#fff;text-align:left;width:430px" ><b>MATAKULIAH</b></td>
				<td style="background:#369;color:#fff;text-align:center;width:20px" ><b>SKS</b></td>
			</tr>
			<%
			}
			//out.println(kdkmk+" "+nakmk+" "+sksmk+"<br/>");
			%>
			<tr>
				<td style="text-align:left;width:15px" ><%=i %>.</td>
				<td style="text-align:center;width:85px" ><%=kdkmk %></td>
				<td style="text-align:left;width:430px" ><%=nakmk %></td>
				<td style="text-align:center;width:20px" ><%=sksmk %></td>
			</tr>
			
		<%
			while(lia.hasNext()) {
				if(pit2) {
					pit2 = false;
					%>
					<tr>
						<td colspan="2" style="background:#369;color:#fff;text-align:center;" ><b>ANGKATAN</b></td>
						<td colspan="2" style="background:#369;color:#fff;text-align:center;" ><b>TOTAL MHS YG BELUM AMBIL MATAKULIAH INI</b></td>
					</tr>
					<%
				}
				String tmp = (String)lia.next();
				StringTokenizer stt = new StringTokenizer(tmp);
				String smawl = stt.nextToken();
				String ttmhs = stt.nextToken();
				%>
				<tr>
					<td colspan="2" style="text-align:center;" ><b><%=smawl %></b></td>
					<td colspan="2" style="text-align:center;" ><b><%=ttmhs %></b></td>
				</tr>
				<%
			}
			first = true;
			pit2 = true;
		}
	}
	%>
	<table>
	<%
}
else {
	%>
	NO FORECAST
	<%			
}
%>


	</div>
</div>		
</body>
</html>