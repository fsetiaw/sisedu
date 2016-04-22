<!DOCTYPE html>
<head>
<%@page import="beans.setting.Constants"%>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");

Vector v= null; 
String nimhs = request.getParameter("nim");
String kuiid = request.getParameter("kuiid");
String pymtp = request.getParameter("pymtp");
String noacc = request.getParameter("noacc");
String keter = request.getParameter("keter");
String opnpm = request.getParameter("opnpm");
String opnmm = request.getParameter("opnmm");
String setor = request.getParameter("setor");
String nonpm = request.getParameter("nonpm");
String voidd = request.getParameter("voidd");
String tgtrs = request.getParameter("tgtrs");
String norut = request.getParameter("norut");
String tgkui = request.getParameter("tgkui");
String amont = request.getParameter("amont");
String nokod = request.getParameter("nokod");
String payee = request.getParameter("payee");



Vector v_bak = (Vector) request.getAttribute("v_bak");
%>


</head>
<body onload="location.href='#'">
<div id="header">
<%@ include file="innerMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
        <form action="go.voidKuitansi" >
        <input type="hidden" value="<%=v_id_obj%>" name="id_obj" />
		<input type="hidden" name="obj_lvl" value="<%=v_obj_lvl %>" />
		<input type="hidden" name="kdpst" value="<%=v_kdpst %>" />
		<input type="hidden" value="<%=v_npmhs %>" name="npm" />
		<input type="hidden" value="<%=v_nmmhs %>" name="nmm" />
        <input type="hidden" value="<%=kuiid %>" name="kuiid" />
        <table align="center" border="1" style="background:#d9e1e5;color:#fff;width:600px">	
			<tr>
				<td align="center" bgcolor="#369"><b>FORM PEMBATALAN KUITANSI NO: <%=norut %></b></td>
			</tr>	
			<tr>
				<td align="left" style="color:#000;font-weight:bold"><label>KETERANGAN PEMBATALAN :</label></td>
			</tr>
			<tr>	
				<td ><textarea name="void_keter" rows="5" cols=70"> </textarea></td>
			</tr>
			<tr>
				<td align="center" bgcolor="#369"><input type="submit" value="VOID KUITANSI" style="width:70%;color:red;font-weight:bold"/></td>
			</tr>
		</table>
		</form>		
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>