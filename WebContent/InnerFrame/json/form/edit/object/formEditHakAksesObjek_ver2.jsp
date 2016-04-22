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
/*
 * 
$(document).ready(function(){
  $("button").click(function(){
    $.getJSON("http://localhost:8080/com.otaku.rest/api/v1/status/usg_db/listMhs",function(result){
      $.each(result, function(){
        $('#div_msg').append(result[0].NMMHSMSMHS + " ");
      });
    });
  });
});
 */
</script>
<%

 %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>version 2</title>
<%

	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String targetObjId = request.getParameter("targetObjId");
	Vector vInfoKomplitObj = (Vector) request.getAttribute("vInfoKomplitObj");
	System.out.println("tob1="+targetObjId);
	JSONObject jobTarget = null;
	jobTarget = (JSONObject)session.getAttribute("job");
	//session.removeAttribute("job");
	System.out.println("job="+jobTarget.toString());
	String tknAkses = null;
	String tknAksesValue = null;
	String tknHakAkses = null;
	String tknScopeKampus = null;
	//String val = targetObj.replace("tandaKutip", "\"");
	//val = val.replace("tandaPagar", "#");
	//if(targetObj!=null) {
	if(true) {
		try {
			//String val = targetObj.replace("tandaKutip", "\"");
			//val = val.replace("tandaPagar", "#");
			
			//jobTarget=new JSONObject(val);
			tknAkses = jobTarget.getString("ACCESS_LEVEL");
			System.out.println("tknAkses="+tknAkses);
		}
		catch(JSONException e) {
			tknAkses = "";
			e.printStackTrace();
		}
		
		try {
			//String val = targetObj.replace("tandaKutip", "\"");
			//val = val.replace("tandaPagar", "#");
			
			//jobTarget=new JSONObject(val);
			tknAksesValue = jobTarget.getString("ACCESS_LEVEL_CONDITIONAL");
			System.out.println("tknAksesValue="+tknAksesValue);
		}
		catch(JSONException e) {
			tknAksesValue = "";
			e.printStackTrace();
		}
		
		try {
			//String val = targetObj.replace("tandaKutip", "\"");
			//val = val.replace("tandaPagar", "#");
			
			//jobTarget=new JSONObject(val);
			tknHakAkses = jobTarget.getString("HAK_AKSES");
			System.out.println("tknHakAkses="+tknHakAkses);
		}
		catch(JSONException e) {
			tknHakAkses = "";
			e.printStackTrace();
		}
		
		try {
			//String val = targetObj.replace("tandaKutip", "\"");
			//val = val.replace("tandaPagar", "#");
			
			//jobTarget=new JSONObject(val);
			tknScopeKampus = jobTarget.getString("SCOPE_KAMPUS");
			
		}
		catch(JSONException e) {
			tknScopeKampus = "";
			e.printStackTrace();
		}
		
		
	}
	String aksesValue = "";
	String hakBacaTulisValue = "";
	String scopeKampusValue = "";
	JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/search_cmd/list_cmd");
	JSONArray jsoaObj = Getter.readJsonArrayFromUrl("/v1/search_obj_type");
	//String atMenu= request.getParameter("atMenu");
	//String backTo= request.getParameter("backTo");
	//JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/search_obj_type/"+validUsr.getIdObj()+"/hak_akses/"+scope);
	//JSONArray jsoa = (JSONArray) session.getAttribute("jsoa");
	//session.removeAttribute("jsoa");
%>
</head>

<body>

<div id="header">
	<ul>
		<li>
		<%
		String target = Constants.getRootWeb()+"/InnerFrame/json/tamplete/select/selectObj.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		%>
		<a href="<%=url %>?scope=editObjParam&atMenu=editObj&backTo=<%="/InnerFrame/Parameter/dashObjekParam.jsp" %>" target="_self">GO<span>BACK</span></a>
		<!--  a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">GO<span>BACK</span></a -->
		</li>
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />

<%
//System.out.println("---------------------------<br/>");

//out.println(jobTarget.getString("OBJ_DESC")+"<br/>");
//System.out.println("---------------------------<br/>");


if(jsoa!=null && jsoa.length()>0) {
%>

<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:750px">
    <tr>	
   		<td style="background:#369;color:#fff;text-align:center;width:750px" colspan="1"><%=jobTarget.getString("OBJ_DESC") %></td>
   	</tr>
</table>
 		
<form action="update.objParam_ver2" method="post" >
<input type="hidden" name="targetObj" value="<%=jobTarget.toString().replace("\"", "tandaKutip") %>" />

<%
	for(int i=0;i<jsoa.length();i++) {
		JSONObject job = jsoa.getJSONObject(i);
		aksesValue = "";
		String cmd_code = "null";
		String used_by = "null";
		String cmd_dependen = "null";
		String keter = "null";
		String value = "null";
		try {
			value = (String)job.get("PILIHAN_VALUE"); 	
   		}
   		catch(JSONException e) {}//ignore
   		//out.print(value);
		//System.out.println(job.toString());
%>	
<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:750px">
    <tr>	
   		<td style="background:#369;color:#fff;text-align:center;width:100px" colspan="1">KODE KOMANDO</td>
   		<td valign="top" style="text-align:left;width:150px" colspan="1">
   		<%
   		try {
   			cmd_code = (String)job.get("CMD_CODE"); 	
   		}
   		catch(JSONException e) {}//ignore
   		out.print(cmd_code);
   		%>
   		<input type="hidden" name="cmd" value="<%=cmd_code %>" />
   		</td>
   		<td style="background:#369;color:#fff;text-align:center;width:100px" colspan="1">DEPENDENCY</td>
   		<td style="text-align:center;width:400px" colspan="1">
   		<%
   		//cmd_dependen
   		try {
   			cmd_dependen = (String)job.get("CMD_DEPENDENCY"); 	
   		}
   		catch(JSONException e) {}//ignore
   		out.print(cmd_dependen);
   		%>
   		<input type="hidden" name="dependency" value="<%=cmd_dependen %>" />
   		</td>
   	</tr>
   	<tr>	
   		<td style="background:#369;color:#fff;text-align:center;width:150px" colspan="1">KETERANGAN</td>
   		<td style="text-align:left" colspan="3">
   		<%
   		try {
   			keter = (String)job.get("CMD_KETER"); 	
   		}
   		catch(JSONException e) {}//ignore
   		out.print(keter);	
   			 
   		%>
   		<input type="hidden" name="keter" value="<%=keter %>" />
   		</td>
   	</tr>
   	<tr>	
   		<td style="background:#369;color:#fff;text-align:center;width:150px" colspan="1">DIGUNAKAN OLEH</td>
   		<td style="text-align:left" colspan="3">
   		<%
   		try {
   			used_by = (String)job.get("USE_BY"); 	
   		}
   		catch(JSONException e) {}//ignore
   		out.print(used_by);	
   			 
   		%>
   		</td>
   	</tr>
   	<%
   		aksesValue = "";
		hakBacaTulisValue="";
		scopeKampusValue="";
   		if(tknAkses!=null) {
   			//System.out.println("tknAkses="+tknAkses);
   			//if(tknAkses.contains(job.get("CMD_CODE").toString())) {
   			if(tknAkses.contains(cmd_code)) {	
   				StringTokenizer st = new StringTokenizer(tknAkses,"#");
   				boolean match = false;
   				int urut = 0;
   				for(;!match && st.hasMoreTokens();urut++) {
   					String tkn = st.nextToken();
   					//if(tkn.equalsIgnoreCase(job.get("CMD_CODE").toString())) {
   					if(tkn.equalsIgnoreCase(cmd_code)) {	
   						match = true;
   					}
   				}
   				
   				
   				if(tknAksesValue!=null && !Checker.isStringNullOrEmpty(tknAksesValue)) {
   					st = new StringTokenizer(tknAksesValue,"#");
   	   				for(int k=0;k<urut;k++) {
   	   					aksesValue = st.nextToken();
   	   				}	
   				}
   		
   				if(tknHakAkses!=null && !Checker.isStringNullOrEmpty(tknHakAkses)) {
   					st = new StringTokenizer(tknHakAkses,"#");
   	   				for(int k=0;k<urut;k++) {
   	   					hakBacaTulisValue = st.nextToken();
   	   				}	
   				}
   		
   				if(tknScopeKampus!=null && !Checker.isStringNullOrEmpty(tknScopeKampus)) {
   					st = new StringTokenizer(tknScopeKampus,"#");
   	   				for(int k=0;k<urut;k++) {
   	   					scopeKampusValue = st.nextToken();
   	   				}	
   				}
   				
   				//out.print(aksesValue);
   			}	
   		}
   		%>
   	<tr>
   		<td style="background:#369;color:#fff;text-align:center;" colspan="1">SCOPE EDIT</td>
   		<%
   		if(hakBacaTulisValue!=null && !Checker.isStringNullOrEmpty(hakBacaTulisValue) && !hakBacaTulisValue.equalsIgnoreCase(cmd_code)) {
   		%>
   	   	<td style="text-align:center;" colspan="1"><input type="text" name="bacaTulis" style="width:98%;height:98%" placeholder="r,e,i,d" value="<%=hakBacaTulisValue%>"/></td>	
   	   	<%	
   		}
   		else {
   		%>
   		<td style="text-align:center;" colspan="1"><input type="text" name="bacaTulis" style="width:98%;height:98%" placeholder="r,e,i,d"/></td>	
   		<%
   		}
   		%>
   		<td style="background:#369;color:#fff;text-align:center;" colspan="1">SCOPE KAMPUS</td>
   		<%
   		if(scopeKampusValue!=null && !Checker.isStringNullOrEmpty(scopeKampusValue) && !scopeKampusValue.equalsIgnoreCase(cmd_code)) {
   		%>
   		<td style="text-align:center;" colspan="1"><input type="text" name="scopeKampus" placeholder="JST,MPR,PST" value="<%= scopeKampusValue%>" style="width:98%;height:98%"/></td>
   		<%	
   		}
   		else {
   		%>
   	   	<td style="text-align:center;" colspan="1"><input type="text" name="scopeKampus" placeholder="JST,MPR,PST"  style="width:98%;height:98%"/></td>
   	   	<%		
   		}
   		%>
   	</tr>	
   	<tr>	
   		<td style="background:#369;color:#fff;text-align:center;" colspan="1">VALUE / SCOPE LVL CIVITAS</td>
   		<td style="text-align:center;" colspan="3">
   		<%
   		/*
   		if(tknAkses!=null) {
   			//System.out.println("tknAkses="+tknAkses);
   			//if(tknAkses.contains(job.get("CMD_CODE").toString())) {
   			if(tknAkses.contains(cmd_code)) {	
   				StringTokenizer st = new StringTokenizer(tknAkses,"#");
   				boolean match = false;
   				int urut = 0;
   				for(;!match && st.hasMoreTokens();urut++) {
   					String tkn = st.nextToken();
   					//if(tkn.equalsIgnoreCase(job.get("CMD_CODE").toString())) {
   					if(tkn.equalsIgnoreCase(cmd_code)) {	
   						match = true;
   					}
   				}
   				
   				if(tknAksesValue!=null && !Checker.isStringNullOrEmpty(tknAksesValue)) {
   					st = new StringTokenizer(tknAksesValue,"#");
   	   				for(int k=0;k<urut;k++) {
   	   					aksesValue = st.nextToken();
   	   				}	
   				}
   				
   				if(tknHakAkses!=null && !Checker.isStringNullOrEmpty(tknHakAkses)) {
   					st = new StringTokenizer(tknHakAkses,"#");
   	   				for(int k=0;k<urut;k++) {
   	   					hakBacaTulisValue = st.nextToken();
   	   				}	
   				}
   				
   				if(tknScopeKampus!=null && !Checker.isStringNullOrEmpty(tknScopeKampus)) {
   					st = new StringTokenizer(tknScopeKampus,"#");
   	   				for(int k=0;k<urut;k++) {
   	   					scopeKampusValue = st.nextToken();
   	   				}	
   				}
   				
   				//out.print(aksesValue);
   			}	
   		}
   			*/
   		%>
   		
   		<%
   		if(value==null) {
   		%>	
   			<textarea name="aksesValue" style="rows:4;width:99%"><%=Checker.pnn(aksesValue) %></textarea>
   		<%
   		}
   		else {
   		%>
   			<input type="text" style="width:99%;height:99%" placeholder="<%=value%>" name="aksesValue" value="<%=aksesValue%>"/>
   		<%	
   		}
   		%>	
   		</td>
   	</tr>	
   	<%
   		if(value==null||Checker.isStringNullOrEmpty(value)) {
   	%>	
   	<tr>
   		<td style="text-align:center" colspan="4">
   		<table>
<% 
		
   		for(int j=0;j<jsoaObj.length();j++) {
			JSONObject jobo = jsoaObj.getJSONObject(j);
%>
			<tr>
   				<td style="background:#369;color:#fff;text-align:left;width:225px" colspan="1"><%=jobo.get("OBJ_DESC") %></td>
   				<td style="background:#369;color:#fff;text-align:center;width:25px" colspan="1"><%=jobo.get("OBJ_LEVEL") %></td>
   				<td style="background:#369;color:#fff;text-align:left;width:225px" colspan="1">
   				<%
   				j++;
   				boolean go = false;
   				if(j<jsoaObj.length()) {
   					jobo = jsoaObj.getJSONObject(j);
	   				go = true;
   					out.print(jobo.get("OBJ_DESC"));
   				}	
   				%>
   				</td>
   				<td style="background:#369;color:#fff;text-align:center;width:25px" colspan="1">
   				<%
   				if(go) {
   					out.print(jobo.get("OBJ_LEVEL"));
   				}	
				%>
				</td>
   				<td style="background:#369;color:#fff;text-align:left;width:225px" colspan="1">
   				<%
   				j++;
   				go = false;
   				if(j<jsoaObj.length()) {
   					jobo = jsoaObj.getJSONObject(j);
	   				go = true;
	   				out.print(jobo.get("OBJ_DESC"));
   				}	
   				%>
   				</td>
   				<td style="background:#369;color:#fff;text-align:center;width:25px" colspan="1">
   				<%
   				if(go) {
   					out.print(jobo.get("OBJ_LEVEL"));
   				}	
				%>
				</td>
				
   			</tr>
   		<%
   		}
		
   		%>	
   		</table>
		</td>
   	</tr>
   	<%
   	}
   	%>	
   	</table>
   	<br/>
<%
//		out.print(job.toString()+"<br/>");	
	}	
%>
<br/>
<table>
	<tr>
   		<td style="background:#369;color:#fff;text-align:center" colspan="4">
   			<input type="submit" value="UPDATE" style="width:80%;height:35px" />
   		</td>
   	</tr>
</table>
</form>
<%
}
	
		
//try {
	
//}
//catch(JSONException e) {
//	e.printStackTrace();
//}

	
%>
<!--  	
<button>Get JSON data</button>
<div id="div_msg"></div>
--> 
	</div>
</div>
		
</body>
</html>