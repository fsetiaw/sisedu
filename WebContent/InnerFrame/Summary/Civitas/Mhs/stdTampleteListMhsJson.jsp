<!DOCTYPE html>
<html>
<head>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>
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
	System.out.println("list mhs");
//	String thsms_pmb = Checker.getThsmsPmb();
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	Vector v = (Vector)session.getAttribute("vJsoa");
	String norut = request.getParameter("norut");
	ListIterator li = v.listIterator();
	JSONObject job = null;
	JSONArray jsoa = null;
	for(int i=0;i<Integer.parseInt(norut);i++) {
		String brs = (String) li.next();
		jsoa = (JSONArray) li.next();
		//job = jsoa.getJSONObject(0)
	}
	
//	if(target_thsms==null || target_thsms.equalsIgnoreCase("null")) {
//		target_thsms = ""+thsms_pmb;
//	}
//	Vector vListThsms = Tool.returnTokensListThsms("20001", thsms_pmb);
	
%>


</head>
<body>
<div id="header">
<%@include file="subSummaryMhsMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
	<%
	

	%>
		
	<table border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:550px">
		<tr>
        	<td style="background:#369;color:#fff;text-align:center" colspan="9"><p style="font-weight:bold">LIST MHS </p></td>
        </tr>	
        <tr>
        	<td style="background:#369;color:#fff;text-align:center;width:25px"><label><B>NO.</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center;width:150px"><label><B>N P M</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center;width:375px"><label><B>NAMA MAHASISWA</B> </label></td>
        </tr>
		<%
		for(int i=0;i<jsoa.length();i++) {
			job = jsoa.getJSONObject(i);
			//System.out.println(job.toString());
		%>
		<tr>
			<td style="background:#d9e1e5;color:#000;text-align:center"><label><B><%=i+1 %></B></label></td>
			<td style="background:#d9e1e5;color:#000;text-align:center"><label><B><%=job.getString("NPMHSMSMHS") %></B></label></td>
			<td style="background:#d9e1e5;color:#000;text-align:left"><label><B><%=job.getString("NMMHSMSMHS") %></B></label></td>

		</tr>
		<%

		}

		%>
	</table>
	</div>
</div>	
</body>
</html>