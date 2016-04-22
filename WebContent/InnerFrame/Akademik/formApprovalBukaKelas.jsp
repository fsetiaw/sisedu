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
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector vBk = (Vector) session.getAttribute("vBukaKelas");
Vector vTmp = (Vector) session.getAttribute("vTmp");
//String needAprBy = (String)request.getAttribute("needAprBy");
String infoTarget = request.getParameter("infoTarget");
StringTokenizer st = new StringTokenizer(infoTarget,"||");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdjen = st.nextToken();
String needAprBy = st.nextToken();
String right = st.nextToken();
String tknApr = st.nextToken();
boolean approvee =  Boolean.valueOf(st.nextToken()).booleanValue();
request.removeAttribute("needAprBy");
String brs = null;
//System.out.println(vBk.size()+","+vTmp.size());
%>


</head>
<body>
<div id="header">
<%@ include file="../Tamplete/goBackOnlySubMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->

<%
if(vTmp!=null && vTmp.size()>0) {
	String thsms1 = null;
	String kdpst1 = null;
	String locked1 = null;
%>		
		<form action="process.approvalBukaKelas" method="post">
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:825px">
				<tr>
					<td style="background:#369;color:#fff;text-align:center;width:125px"><label><B>PRODI</B> </label></td>
	        		<td style="background:#369;color:#fff;text-align:center;width:275px"><label><B>MATAKULIAH (MK)</B> </label></td>
	        		<td style="background:#369;color:#fff;text-align:center;width:25px"><label><B>SMS MK</B> </label></td>
	        		<td style="background:#369;color:#fff;text-align:center;width:150px"><label><B>SHIFT</B> </label></td>
	        		<td style="background:#369;color:#fff;text-align:center;width:25px"><label><B>KLS PLL</B> </label></td>
	        		<td style="background:#369;color:#fff;text-align:center;width:200px"><label><B>DOSEN</B> </label></td>
	        		<td style="background:#369;color:#fff;text-align:center;width:25px"><label><B># MHS</B> </label></td>
	        	</tr>	
<%
	ListIterator liTmp = vTmp.listIterator();
	while(liTmp.hasNext()) {
		brs = (String)liTmp.next();
		StringTokenizer st1 = new StringTokenizer(brs,"||");
		String nakmk1 = st1.nextToken();
		String smsmk1 = st1.nextToken();
		String cmd1 = st1.nextToken();
		String idkur1 = st1.nextToken();
		String idkmk1 = st1.nextToken();
		thsms1 = st1.nextToken();
		kdpst1 = st1.nextToken();
		String shift1 = st1.nextToken();
		String noKlsPll1 = st1.nextToken();
		String initNpmInput1 = st1.nextToken();
		String latestNpmUpdate1 = st1.nextToken();
		String latestStatusInfo1 = st1.nextToken();
		locked1 = st1.nextToken();
		String npmdos1 = st1.nextToken();
		String nodos1 = st1.nextToken();
		String npmasdos1 = st1.nextToken();
		String noasdos1 = st1.nextToken();
		String cancel = st1.nextToken();
		String kodeKelas1 = st1.nextToken();
		String kodeRuang1 = st1.nextToken();
		String kodeGedung1 = st1.nextToken();
		String kodeKampus1 = st1.nextToken();
		String tknHrTime1 = st1.nextToken();
		String nmmdos1 = st1.nextToken();
		String nmmasdos1 = st1.nextToken();
		String enrolled1 = st1.nextToken();
		String maxEnrol1 = st1.nextToken();
		String minEnrol1 = st1.nextToken();
		String subKeterMk1 = st1.nextToken();
		String initReqTime1 = st1.nextToken();
		String tknNpmApproval1 = st1.nextToken();
		String tknApprovalTime1 = st1.nextToken();
		String targetTotMhs1 = st1.nextToken();
		String passed1 = st1.nextToken();
		String rejected1 = st1.nextToken();
		String konsen1 = st1.nextToken();
		String kdkmk1 = st1.nextToken();
%>	        	
	        	<tr>
	        		<td style="text-align:center"><label>
	        		<%
	        		out.print(Converter.getNamaKdpst(kdpst1));
	        		if(!Checker.isStringNullOrEmpty(konsen1)) {
	        			out.print("<br/>konsentrasi "+konsen1.toUpperCase());
	        		}
	        		%>
	        		</label></td>
	        		<td style="text-align:left"><label>
	        		<%=kdkmk1+"<br/>" %>
	        		<%
	        		if(!Checker.isStringNullOrEmpty(subKeterMk1)) {
	        			out.print(nakmk1+" "+subKeterMk1);
	        		}
	        		else {
	        			out.print(nakmk1);
	        		}
	        		
	        		%></label></td>
	        		<td style="text-align:center"><label><%=smsmk1 %></label></td>
	        		<td style="text-align:center"><label><%=shift1 %></label></td>
	        		<td style="text-align:center"><label><%=noKlsPll1 %></label></td>
	        		<td style="text-align:center"><label><%=nmmdos1%></label></td>
	        		<td style="text-align:center"><label><%=targetTotMhs1 %></label></td>
	        	</tr>
<%
	}
%>	        	
	    </table>
	    
	    <br/>
<%
	if(approvee && !locked1.equalsIgnoreCase("true")) {
		//System.out.println("brs==+"+brs);
		session.setAttribute("brs", brs);
%>	    
	    <P style="text-align:center">
	    	Keterangan Penolakan:<br/>
	    	
	    	<input type="hidden" value="<%=infoTarget %>" name="infoTarget" />
	    	<input type="hidden" value="<%=needAprBy %>" name="needAprBy" />
	    	<input type="hidden" value="<%=kdpst1 %>" name="kdpst" />
	    	<input type="hidden" value="<%=thsms1 %>" name="thsmsPmb" />
	    	<textarea rows="5" style="width:700px" name="alasan"></textarea><br/>
	    	<input type="submit" name="submit" value="Tolak" style="width:300px;height:30px;color:red;font-weight:bold"/>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input type="submit" name="submit" value="Setuju" style="width:300px;height:30px;font-weight:bold"/>
	    </P>
<%
	}
%>	    
	    </form>
<%

}
%>	    
	    <br/>  
		<!-- Column 1 start -->
	</div>
</div>			
</body>
</html>