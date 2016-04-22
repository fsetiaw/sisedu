<!DOCTYPE html>
<head>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
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
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String scope = request.getParameter("scope");
	String atMenu= request.getParameter("atMenu");
	String backTo= request.getParameter("backTo");

%>
</head>

<body>
<div id="header">
	<ul>
		<li>
		<%
		//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/dashObjekParam.jsp";
		Vector vList = (Vector) request.getAttribute("vList");
		
		String target = Constants.getRootWeb()+backTo;
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		%>
		<a href="<%=url %>" target="_self">GO<span>BACK</span></a>
		<!--  a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">GO<span>BACK</span></a -->
		</li>
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
<%
	if(vList!=null && vList.size()>0) {
%>		

	<form action="get.infoObj">
	<input type="hidden" name="scope" value="<%=scope %>" />
	<table align="center" border="1" style="background:#d9e1e5;color:#000;width:300px;">
		<tr>
			<td align="center" bgcolor="#369" style="color:#fff" padding-left="2px"><b>PILIH OBJEK</b></td>
		</tr>	
		<tr>
		<td align="left" width="100px" style="padding-left:2px">
			<select name="targetObj" style="width:100%">
<%
		ListIterator li = vList.listIterator();
		while(li.hasNext()) {
			String brs = (String)li.next();
			//116 88888 MHS_AGAMA 116 C
			StringTokenizer st = new StringTokenizer(brs);
			String objId = st.nextToken();
			String kdpst = st.nextToken();
			String objDesc = st.nextToken();		
			objDesc = objDesc.replace("MHS_", "");
			String objLvl = st.nextToken();
			String kdjen = st.nextToken();
%>
			<option value="<%=objId%>"> <%=objDesc %> </option>
<%	
		}
%>			
			</select>
		</td>
		</tr>
		<tr>
			<td align="right" bgcolor="#369"><input type="submit" value="Next" style="width:100px;" /></td>
		</tr>
	</table>	
	</form>	
<% 
	}
	else {
		out.print("ANDA TIDAK MEMILIKI HAK AKSES");
	}
	

	
%>
<!--  	
<button>Get JSON data</button>
<div id="div_msg"></div>
--> 
	</div>
</div>
		
</body>
</html>