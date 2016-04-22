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
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/jquery/jquery.min.js"></script>
</script>
<script>
$(document).ready(function(){
  $("button").click(function(){
    $.getJSON("http://localhost:8080/com.otaku.rest/api/v1/status/usg_db/listMhs",function(result){
      $.each(result, function(){
        $('#div_msg').append(result[0].NMMHSMSMHS + " ");
      });
    });
  });
});
</script>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String scope = request.getParameter("scope");
	String atMenu= request.getParameter("atMenu");
	String backTo= request.getParameter("backTo");
	JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/search_obj_type/"+validUsr.getIdObj()+"/hak_akses/"+scope);
	//JSONArray jsoa = (JSONArray) session.getAttribute("jsoa");
	//session.removeAttribute("jsoa");
%>
</head>

<body>
<div id="header">
	<ul>
		<li>
		<%
		//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/dashObjekParam.jsp";
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
try {

	if(jsoa!=null && jsoa.length()>0) {
		//target = Constants.getRootWeb()+"/InnerFrame/json/form/edit/object/formEditHakAksesObjek_ver2.jsp";
		//uri = request.getRequestURI();
		//url = PathFinder.getPath(uri, target);
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
		for(int i=0;i<jsoa.length();i++) {
			JSONObject job = jsoa.getJSONObject(i);
			//System.out.println(job.toString().replace("\"", "tandaKutip").length());
			
%>
					<!--  option value="<%=job.toString().replace("\"", "tandaKutip") %>"><%=job.get("OBJ_DESC").toString().toUpperCase() %></option -->
					<option value="<%=job.get("ID_OBJ") %>"><%=job.get("OBJ_DESC").toString().toUpperCase() %></option>
				
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
	
	
}
catch(JSONException e) {
	System.out.println("ada err");
	e.printStackTrace();
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