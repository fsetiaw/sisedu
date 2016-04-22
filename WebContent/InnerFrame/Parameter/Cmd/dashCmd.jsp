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
	//System.out.println("okeh");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	//String tipeForm = request.getParameter("formType");
	//Vector vDwn = validUsr.getScopeUpd7des2012("hasDownloadMenu");
	//Vector v_cf = validUsr.getScopeUpd7des2012("hasEditMkKurikulum");
%>


</head>
<!--  body onload="location.href='#'" -->
<body>
<div id="header">

<%
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
else {
	//redirect = no access at alll
}

//String inner_menu = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0.jsp";
//out.print(inner_menu);
//out.print(url);
boolean editable = false;
if(hak_akses.contains("e") || hak_akses.contains("i")) {
	editable = true;
}
else if(!hak_akses.contains("r")) {
	//redirect - tidak punya akses baca
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
		String cmd_code = "";
		String cmd_keter = "";
		String use_by = "";
		String cmd_dependecy = "";
		String pilihan_value = "";
		String pilihan_value_hak_akses = "";
		String pilihan_value_scope_kmp = "";
		String obj_lvl_allow_to_set = "";
		
		JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/search_cmd/list_cmd");
		//System.out.println("jsoa="+jsoa);
		if(jsoa!=null && jsoa.length()>0) {
			int no = 1;
			if(editable)  {
				
%>
<br/>
<form action="go.updCmd" method="post">
	<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px">
	<tr>
		<td style="background:#369;color:#fff;text-align:center;valign:middle;width:100%" colspan="4">
			<h3> 
			FORM PENAMBAHAN COMMAND BARU
			</h3>
		</td>
	</tr>
	<tr>	
		<td style="width:250px;padding:3px"" >KODE PERINTAH</td>
		<td colspan="3" >
			<textarea rows="1" style="width:99%" name="inp_cmd_code"></textarea>			
		</td>
	</tr>
	<tr>	
		<td style="width:250px;padding:3px"" >KETERANGAN PERINTAH</td>
		<td colspan="3" >
			<textarea rows="4" style="width:99%" name="inp_cmd_keter"></textarea>			
		</td>
	</tr>
	<tr>	
		<td style="width:250px;padding:3px"" >PRE-REQ PERINTAH</td>
		<td colspan="3" >
			<textarea rows="1" style="width:99%" name="inp_cmd_dependency"></textarea>			
		</td>
	</tr>
	<tr>	
		<td style="width:250px;padding:3px"" >PENGGUNA</td>
		<td colspan="3" >
			<textarea rows="1" style="width:99%" name="inp_cmd_use_by"></textarea>			
		</td>
	</tr>
	<tr>	
		<td style="width:250px;padding:3px"" >VALUE OPTION</td>
		<td colspan="3" >
			<textarea rows="2" style="width:99%" name="inp_cmd_pilihan_value" placeholder="yes, jika hanya menampilkan menu, otw kosongin aja"></textarea>			
		</td>
	</tr>
	<tr>	
		<td style="width:250px;padding:3px"" >SCOPE KAMPUS</td>
		<td colspan="3" >
			<textarea rows="2" style="width:99%" name="inp_cmd_scope_kmp" placeholder="masih ngga tau perlu diisi apa ngga??"></textarea>			
		</td>
	</tr>
	<tr>	
		<td style="width:250px;padding:3px"" >OBJ LEVEL PENGAMPU</td>
		<td colspan="3" >
			<textarea rows="1" style="width:99%" name="inp_cmd_obj_lvl_pengampu" placeholder="default = #0#"></textarea>			
		</td>
	</tr>
	<tr>
			<td style="background:#369;color:#fff;text-align:center;height:35px" colspan="5">
				<input type="submit" name="submit" value="INSERT" style="width:70%;height:30px;cellpadding:5px" />
			</td>
		</tr>
	</table>
	
	<br/>
	<br/>
	<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px">
<%
			}			
			for(int i=0;i<jsoa.length();i++) {
				JSONObject job = jsoa.getJSONObject(i);
				// brs = job.t;
				//int brs = 0;
				try {
					
					//cmd_code = ""+(Integer)job.getInt("CMD_CODE");//1
					if(job.isNull("CMD_CODE")) {
						cmd_code = "null";
					}
					else {	
						cmd_code = ""+(String)job.get("CMD_CODE");
					}
					//System.out.println(i+".cmd code="+cmd_code);
					//2. kdpst = (String)job.get("KDPST");
					if(job.isNull("CMD_KETER")) {
						cmd_keter = "null";
					}
					else {	
						cmd_keter = ""+(String)job.get("CMD_KETER");
					}
					
					//3.thsms_sms = (String)job.get("THSMS");
					if(job.isNull("USE_BY")) {
						use_by = "null";
					}
					else {
						
						use_by = ""+(String)job.get("USE_BY");
					}
					
					
					//4.thsms_pmb = (String)job.get("THSMS_PMB");
					if(job.isNull("CMD_DEPENDENCY")) {
						cmd_dependecy = "null";
					}
					else {
						
						cmd_dependecy = ""+(String)job.get("CMD_DEPENDENCY");
					}
					
					//5.thsms_reg = (String)job.get("THSMS_HEREGISTRASI");
					if(job.isNull("PILIHAN_VALUE")) {
						pilihan_value = "null";
					}
					else {
						
						pilihan_value = ""+(String)job.get("PILIHAN_VALUE");
					}
					
					
					//6.thsms_buka_kls = (String)job.get("THSMS_BUKA_KELAS");
					if(job.isNull("PILIHAN_VALUE_HAK_AKSES")) {
						pilihan_value_hak_akses = "null";
					}
					else {
						
						pilihan_value_hak_akses = ""+(String)job.get("PILIHAN_VALUE_HAK_AKSES");
					}
					
					
					//7.thsms_nilai = (String)job.get("THSMS_INP_NILAI_MK");
					if(job.isNull("PILIHAN_VALUE_SCOPE_KAMPUS")) {
						pilihan_value_scope_kmp = "null";
					}
					else {
						
						pilihan_value_scope_kmp = ""+(String)job.get("PILIHAN_VALUE_SCOPE_KAMPUS");
					}
					
					
					//8.thsms_krs = (String)job.get("THSMS_KRS");
					if(job.isNull("OBJECT_LEVEL_ALLOWED_TO_SET")) {
						obj_lvl_allow_to_set = "null";
					}
					else {
						
						obj_lvl_allow_to_set = ""+(String)job.get("OBJECT_LEVEL_ALLOWED_TO_SET");
					}
%>					
		<tr>
			<td style="width:25px;background:#369;color:#fff;text-align:center;" valign="top" rowspan="4" align="center"><h3><%=no++ %>.</h3></td>
			<td style="background:#369;color:#fff;text-align:center;width:100%" colspan="4">
				<h3> 
				<input type="hidden" name="cmd_code" value="<%=cmd_code%>"/>
				<%=cmd_code%>
				</h3>
			</td>
		</tr>

		<tr>	
			<td style="width:100px;padding:3px"" >KETERANGAN PERINTAH</td>
			<td colspan="3" >
				<%
					if(editable) {
				%>
				<textarea rows="4" style="width:99%" name="cmd_keter"><%=cmd_keter%></textarea>
				<%	
					}
					else {
						out.print(cmd_code);
					}
				%>
			</td>
		</tr>
		<tr>
			<td style="width:100px;padding:3px" >PENGGUNA</td>
			<td style="width:337px;" >
				<%
					if(editable) {
				%>
				<textarea rows="4" style="width:98%" name="use_by"><%=use_by%></textarea>
				<%	
					}
					else {
						out.print(use_by);
					}
				%>
			</td>
			<td style="width:100px;padding:3px"" >PERINTAH TERKAIT</td>
			<td style="width:337px;" >
				<%
					if(editable) {
				%>
				<input type="text" name="cmd_dependecy" style="width:100%;height:100%" value="<%=cmd_dependecy%>"/>
				<%	
					}
					else {
						out.print(cmd_dependecy);
					}
				%>
			</td>
		</tr>
		
		<!--  tr>
			<td style="width:100px;padding:3px" >VALUE / SCOPE LVL CIVITAS</td>
			<td style="width:337px;" >
				<%
					if(editable) {
						if(pilihan_value==null || Checker.isStringNullOrEmpty(pilihan_value)) {
							%>
				<textarea rows="4" style="width:98%" name="pilihan_value" placeholder="list scope objek" ></textarea>
							<%	
						}
						else {
				%>
				<textarea rows="4" style="width:98%" name="pilihan_value" ><%=pilihan_value%>	</textarea>
				<%	
						}
				%>
			
				<%	
					}
					else {
						out.print(pilihan_value);
					}
				%>
			</td>
			<td style="width:100px;padding:3px" >SCOPE EDIT</td>
			<td style="width:337px;" >
			ignore = pilihannya r,e,i,d dan ditentukan pada edit objeck
			</td>
		</tr -->
		<tr>
			<td style="width:100px;padding:3px" >SCOPE KAMPUS</td>
			<td style="width:337px;" >
				<%
					if(editable) {
						if(pilihan_value_scope_kmp==null || Checker.isStringNullOrEmpty(pilihan_value_scope_kmp)) {
							%>
				<textarea rows="4" style="width:98%" name="hak_akses_scope_kmp" placeholder="PST,JST,MPR"></textarea>
							<%	
						}
						else {
							%>
				<textarea rows="4" style="width:98%" name="hak_akses_scope_kmp"><%=pilihan_value_scope_kmp%></textarea>
							<%	
						}
				
					}
					else {
						out.print(pilihan_value_scope_kmp);
					}
				%>
			</td>
			<td style="width:100px;padding:3px" >PENGAMPU PERINTAH</td>
			<td style="width:337px;" >
				<%
					if(editable) {
				%>
				<input type="text" name="obj_lvl_allow_to_set" style="width:100%;height:100%" value="<%=obj_lvl_allow_to_set%>"/>
				<%	
					}
					else {
						out.print(obj_lvl_allow_to_set);
					}
				%>
			</td>
		</tr>
<%					

		   		}
		   		catch(JSONException e) {
		   			out.println("<br/>"+i+".ada error<br/>");	
		   		}//ignore
		   		
			}	
%>
   		<tr>
			<td style="background:#369;color:#fff;text-align:center;height:35px" colspan="5">
				<input type="submit" name="submit" value="UPDATE" style="width:70%;height:30px;cellpadding:5px" />
			</td>
		</tr>	
	</table>
<%
			if(editable) {
%>    	
</form>   		
<%
			}
		
		}//end if jsoa!=null
 
%>
 
		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>