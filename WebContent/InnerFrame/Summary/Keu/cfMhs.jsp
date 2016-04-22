<!DOCTYPE html>
<head>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.*" %>
<%@ page import="beans.setting.*" %>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%
	request.setAttribute("atPage", "cashFlow");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	SearchDb sdb = new SearchDb();
	Vector v = (Vector)session.getAttribute("vSum");
	String tknYyMm = (String)session.getAttribute("tknYyMm");
	Double[]yTot = (Double[])session.getAttribute("yTot");
	Vector vPsc = (Vector)session.getAttribute("vSumPsc");
	Double[]yTotPsc = (Double[])session.getAttribute("yTotPsc");
	//System.out.println("yTot length="+yTot.length);
	//Vector vtm = (Vector) session.getAttribute("v_totMhs");
	//session.removeAttribute("v_totMhs");
	//session.removeAttribute("vSum");
	//session.removeAttribute("tknYyMm");
	//session.removeAttribute("yTot");
	//session.removeAttribute("vSumPsc");
	//session.removeAttribute("yTotPsc");
	//ListIterator li = v.listIterator();
%>


</head>
<body>
<div id="header">
<%
String target = Constants.getRootWeb()+"/InnerFrame/Summary/subSummaryMenu.jsp";
String uri = request.getRequestURI();
String url = PathFinder.getPath(uri, target);
%>
<%@ include file="menuKeu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
<%
StringTokenizer st1 = null;
ListIterator li = null;
if(vPsc!=null && vPsc.size()>0) { 
%>
	<table border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:90%">
		<tr>
       		<td style="background:#369;color:#fff;text-align:center" colspan="15"><label><B>PROGRAM PASCA SARJANA</B> </label></td>
       	</tr>	
       	<tr>
       		<td style="background:#369;color:#fff;text-align:center" rowspan="2"><label><B>PRODI</B> </label></td><td colspan="13" style="background:#369;color:#fff;text-align:center">TAHUN/BULAN</td><td style="background:#369;color:#fff;text-align:center" rowspan="2"><b>TOTAL</b></td>
       	</tr>
       	<tr>
       		<%
       		st1 = new StringTokenizer(tknYyMm);
       		while(st1.hasMoreTokens()) {
       			String yymm = st1.nextToken();
       			yymm = yymm.substring(0,4)+"/"+yymm.substring(4,yymm.length());
       			%>
       			<td style="background:#62B1FF;text-align:center"><b><%=yymm %></b></td>
       			<%
       		}
       		%>
       		
       	</tr>
       	<tr>
       		<%
       		
       		li = vPsc.listIterator();
			while(li.hasNext()) {
				String baris = (String)li.next();
				StringTokenizer st = new StringTokenizer(baris);
				if(st.countTokens()==1) {
				%>
					<td style="background:#62B1FF"><b><%=sdb.getNmpst(baris) %></b></td>	
				<%
				}
				else {
					if(st.countTokens()==2) {
						 st.nextToken();
						 String total = st.nextToken();
						 %>
							<td style="background:WHITE;color:BLACK;text-align:right"><%=NumberFormater.indoNumberFormat(total) %></td></tr>
						 <%
					}
					else {
						if(st.countTokens()==3) {
							String thn = st.nextToken();
							String bln = st.nextToken();
							String jum = st.nextToken();
							%>
							<td style="text-align:right"><%=NumberFormater.indoNumberFormat(jum) %></td>
							<%
						}
					}
						
				}
				//out.print("</tr>");
			}
	 		%>
       	<tr>
       		<td style="background:#369;color:#fff;text-align:center"><label><B>TOTAL</B> </label></td>
       		<%
       		for(int i=0;i<yTotPsc.length;i++) {
       			
       			%>
       			<td style="background:WHITE;color:BLACK;text-align:right"><%=NumberFormater.indoNumberFormat(""+yTotPsc[i].doubleValue()) %></td>
       			<%
       		}
       		%>
       	</tr>	
	</table>
<%
}
%>
	<br />
	<br />
	<table border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:90%">
		<tr>
       		<td style="background:#369;color:#fff;text-align:center" colspan="15"><label><B>PROGRAM NON PASCA</B> </label></td>
       	</tr>	
       	<tr>
       		<td style="background:#369;color:#fff;text-align:center" rowspan="2"><label><B>PRODI</B> </label></td><td colspan="13" style="background:#369;color:#fff;text-align:center">TAHUN/BULAN</td><td style="background:#369;color:#fff;text-align:center" rowspan="2"><b>TOTAL</b></td>
       	</tr>
       	<tr>
       		<%
       		st1 = new StringTokenizer(tknYyMm);
       		while(st1.hasMoreTokens()) {
       			String yymm = st1.nextToken();
       			yymm = yymm.substring(0,4)+"/"+yymm.substring(4,yymm.length());
       			%>
       			<td style="background:#62B1FF;text-align:center"><b><%=yymm %></b></td>
       			<%
       		}
       		%>
       		
       	</tr>
       	<tr>
       		<%
       		
       		li = v.listIterator();
			while(li.hasNext()) {
				String baris = (String)li.next();
				StringTokenizer st = new StringTokenizer(baris);
				if(st.countTokens()==1) {
				%>
					<td style="background:#62B1FF"><b><%=sdb.getNmpst(baris) %></b></td>	
				<%
				}
				else {
					if(st.countTokens()==2) {
						 st.nextToken();
						 String total = st.nextToken();
						 %>
							<td style="background:WHITE;color:BLACK;text-align:right"><%=NumberFormater.indoNumberFormat(total) %></td></tr>
						 <%
					}
					else {
						if(st.countTokens()==3) {
							String thn = st.nextToken();
							String bln = st.nextToken();
							String jum = st.nextToken();
							%>
							<td style="text-align:right"><%=NumberFormater.indoNumberFormat(jum) %></td>
							<%
						}
					}
						
				}
				//out.print("</tr>");
			}
	 		%>
       	<tr>
       		<td style="background:#369;color:#fff;text-align:center"><label><B>TOTAL</B> </label></td>
       		<%
       		for(int i=0;i<yTot.length;i++) {
       			
       			%>
       			<td style="background:WHITE;color:BLACK;text-align:right"><%=NumberFormater.indoNumberFormat(""+yTot[i].doubleValue()) %></td>
       			<%
       		}
       		%>
       	</tr>	
        
	</table>  
  	</div>
</div>  	
</body>
</html>