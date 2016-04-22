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
/*
* viewKurikulumStdTamplete (based on)
*/
	System.out.println("lanjutan opt");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
 	//String kelasTambahan = (String) session.getAttribute("kelasTambahan");
 	
	String infoKur=request.getParameter("infoKur");
	//System.out.println("infoKur="+infoKur);
	infoKur = infoKur.replace("#&","||");
	String kdpst_nmpst = request.getParameter("kdpst_nmpst");
	String[] infoKelasDosen = (String[])session.getAttribute("infoKelasDosen");
	String[] infoKelasMhs = (String[])session.getAttribute("infoKelasMhs");
	session.removeAttribute("infoKelasDosen");
	session.removeAttribute("infoKelasMhs");
	//for(int i=0;i<infoKelasDosen.length;i++) {
	//	out.print(infoKelasDosen[i]+"<br/>");
	//}
	boolean atFormPengajuanBukaKelasTahap1 = false;
	boolean atFormPengajuanBukaKelasTahap2 = false;
%>


</head>
<body>
<div id="header">
<%@ include file="IndexAkademikPengajuanSubMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		okke
		<%
		%>
	<form action="process.updClassPoolTable" method="post">
	
	<input type="hidden" name="kdpst_nmpst" value="<%=kdpst_nmpst %>" />	
	<input type="hidden" name="infoKur" value="<%=infoKur %>" />	
	<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:750px">
		<tr>
	     	<td style="background:#369;color:#fff;text-align:center" colspan="3"><label><B>FORM INFO TAMBAHAN KELAS PERKULIAHAN</B> </label></td>
	    </tr>	
		<tr>
	        <td style="background:#369;color:#fff;text-align:center;width:100px"><label><B>KODE MK</B> </label></td>
	        <td style="background:#369;color:#fff;text-align:center;width:650px"><label><B>MATAKULIAH / SHIFT / DOSEN</B> </label></td>
	            
	    </tr>
	    <%
	    for(int i=0;i<infoKelasDosen.length;i++) {
			if(infoKelasDosen[i].contains("yesketer")) {
				StringTokenizer st = new StringTokenizer(infoKelasDosen[i],"||");
				String kdkmk = st.nextToken();
				String nakmk = st.nextToken();
				String shift = st.nextToken();
				String idkmk = st.nextToken();
				String norut = st.nextToken();
				String nodos = st.nextToken();
				String nmdos = st.nextToken();
				String optKeter = st.nextToken();
				String listOptKeter = st.nextToken();
				st = new StringTokenizer(listOptKeter,",");
		%>
		<tr>
			<td style="color:#000;text-align:center"><label><B><%=kdkmk %></B> </label></td>
			<td style="color:#000;text-align:left">&nbsp
			<%
				out.print(nakmk);
			%>
			<select name="infoKelasDosen">
			<%
				while(st.hasMoreTokens()) {
					String val = st.nextToken();
			%>
				<option value="<%=kdkmk%>||<%=nakmk%>||<%=shift%>||<%=idkmk%>||<%=norut%>||<%=nodos%>||<%=nmdos%>||<%=optKeter%>||<%=val %>"><%= val%></option>
			<%	
				} 
			%>
			</select>
			<p style="color:#000000;font-style:italic">
			&nbsp&nbsp&nbsp&nbsp&nbsp JUMLAH TARGET MHS : &nbsp<input type="number" name="infoKelasMhs" value="<%=infoKelasMhs[i] %>" style="text-align:center;width:39px"/>
			</p>
			<p style="color:#369;font-style:italic">
			&nbsp&nbsp&nbsp&nbsp&nbsp SHIFT : &nbsp<%=shift %>
			</p>
			<p style="color:#000000;font-style:italic">
			&nbsp&nbsp&nbsp&nbsp&nbsp DOSEN : &nbsp<%=nmdos %>
			</p>
			
			
			</td>
			
		</tr>
		<%		
			}
			else {
		%>
			<input type="hidden" name="infoKelasDosen" value="<%=infoKelasDosen[i] %>" />
			<input type="hidden" name="infoKelasMhs" value="<%=infoKelasMhs[i] %>" />
		<%		
			}
		}
	    %>
	   
	</table>
	<br/>
		<div style="text-align:center"><input type="submit" value="Next" style="width:500px;height:35px"/></div>
	</form>
	<%	
		%>


		    
		<!-- Column 1 start -->
	</div>
</div>			
</body>
</html>