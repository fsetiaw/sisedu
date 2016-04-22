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

%>


</head>
<!--  body onload="location.href='#'" -->
<body>
<div id="header">

<%
String thsms_buka_kelas = request.getParameter("thsmsBukaKelas");

String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
String uri = request.getRequestURI();
String url = PathFinder.getPath(uri, target);
JSONArray jsoa_info_akses = (JSONArray) request.getAttribute("jsoa");
String target_param = null;
String scope_prodi = null;
String scope_kmp = null;
String hak_akses = null;
if(jsoa_info_akses!=null && jsoa_info_akses.length()>0) {
	for(int i=0;i<jsoa_info_akses.length();i++) {
		JSONObject job = jsoa_info_akses.getJSONObject(i);
		//String brs = "";
		try {
			target_param = (String)job.get("ACCESS_LEVEL");
			scope_prodi = (String)job.get("ACCESS_LEVEL_CONDITIONAL"); 	
			hak_akses = (String)job.get("HAK_AKSES"); 	
			scope_kmp = (String)job.get("SCOPE_KAMPUS"); 	
			
   		}
   		catch(JSONException e) {}//ignore
   		//out.println(i+"."+brs+"<br/>");
	}	
}
//String inner_menu = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0.jsp";
//out.print(inner_menu);
//out.print(url);
boolean editable = false;
if(hak_akses.contains("e") || hak_akses.contains("i")) {
	editable = true;
}
%>


<jsp:include page="<%= url %>" flush="true" />

</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<br/>
		<%
		String id = "";
		String kdpst = "";
		String verificator = "";
		String urut = "";
		String kode_kampus = "";

if(editable)  {		
%>
<br/>
<form action="update.updClassPoolRules">
<%
}
%>	
<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px">
	<tr>
		<td style="background:#369;color:#fff;text-align:center;width:100%" colspan="4">
		<input type="hidden" name="kdpst" value="<%=kdpst %>" />
		<input type="hidden" name="id" value="<%=id %>" />
		<input type="hidden" name="thsms_buka_kelas" value="<%=thsms_buka_kelas %>" />
		<%
		
			out.print("FORM PENGATURAN PENGAJUAN KELAS");
			out.print("<br/>");
			out.print("TAHUN / SEMESTER : "+thsms_buka_kelas);
		
		%>
		</td>
	</tr>
	<tr>
		<td style="background:#369;color:#fff;text-align:center;width:85px">KDPST</td>
		<td style="background:#369;color:#fff;text-align:center;width:600px">VERIFICATOR</td>
		<td style="background:#369;color:#fff;text-align:center;width:85px">URUT</td>
		<td style="background:#369;color:#fff;text-align:center;width:85px">KODE KAMPUS</td>
	</tr>
<%
JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/class_pool/rules/"+thsms_buka_kelas);
if(jsoa!=null && jsoa.length()>0) {
	for(int i=0;i<jsoa.length();i++) {
		JSONObject job = jsoa.getJSONObject(i);
		//out.print(job.toString());
		// brs = job.t;
		//int brs = 0;
		try {
			
			if(job.isNull("KDPST")) {
				kdpst = "null";
			}
			else {
				
				kdpst = ""+(String)job.get("KDPST");
			}
			
			//2.
			if(job.isNull("TKN_VERIFICATOR")) {
				verificator = "null";
			}
			else {
				
				verificator = ""+(String)job.get("TKN_VERIFICATOR");
			}
			
			
			//3.
			if(job.isNull("URUTAN")) {
				urut = "null";
			}
			else {
				
				urut = ""+(String)job.get("URUTAN");
			}
			
			//4
			if(job.isNull("KODE_KAMPUS")) {
				kode_kampus = "null";
			}
			else {
				
				kode_kampus = ""+(String)job.get("KODE_KAMPUS");
			}
%>
	<tr>	
   		<td valign="top" style="text-align:center;">
   			<input type="text" name="kdpst_val" style="width:98%;text-align:center" value="<%=kdpst%>"/>
   	   	</td>
   	   	
   		<td valign="top" style="text-align:center;">
   			<input type="text" name="verificator_val" style="width:99%;text-align:center" value="<%=verificator%>"/>
   	   	</td>
   	   	
   		<td valign="top" style="text-align:center;">
   			<input type="text" name="urutan_val" style="width:98%;text-align:center" value="<%=urut%>"/>
   	   	</td>
   	   	
   		<td valign="top" style="text-align:center;">
   			<input type="text" name="kode_kmp_val" style="width:98%;text-align:center" value="<%=kode_kampus%>"/>
   	   	</td>
	</tr>
<%			
			
			

   		}
   		catch(JSONException e) {
   			out.println("<br/>"+i+".ada error<br/>");	
   		}//ignore
   		
	}	
}

%>	
	
	<tr>
		
		<td style="background:#369;color:#fff;text-align:center;width:80%;height:35px" colspan="4">
			<input type="submit" style="width:90%;height:30px" name="submit" value="UPDATE"/>
		</td>
	</tr>


	<%	
	
%>			
</table>
<br/>
<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px">
	<tr>
		<td style="background:#369;color:#fff;text-align:center;width:100%" colspan="2">COPY FROM THSMS :</td>
	</tr>
	<tr>
		<td valign="middle" style="text-align:center;height:35px" >
			<input type="text" style="width:99%;height:30px;align:center" name="base_thsms" />
		</td>
		<td valign="middle" style="text-align:center;height:35px" >
			<input type="submit" style="width:90%;height:30px;" name="submit" value="COPY"/>
		</td>
	</tr>
</table>	
<%
if(editable) {
%>    	
</form>   		
<%
}
%>
 
		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>