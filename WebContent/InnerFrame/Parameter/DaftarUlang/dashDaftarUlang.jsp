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
String target_thsms = Checker.getThsmsHeregistrasi();
//String target_thsms = "20142";
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
		String kdpst = "";
		String tkn_verificator = "";
		String urutan = "";
		String kode_kampus = "";

		
		JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/daftar_ulang/param/rules/"+target_thsms);
		//System.out.println("jsoa="+jsoa);
		//if(jsoa!=null && jsoa.length()>0) {
		int no = 1;
		if(editable)  {
				
%>
<br/>
<form action="go.updRegRules" method="post">
<br/>
	<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px">
	<tr>		
			<td style="background:#369;color:#fff;text-align:center;width:500px;valign:middle;font-size:1.5em" >
				COPY DARI THSMS SEBELUMNYA
			</td>	
			<td style="background:#369;color:#fff;text-align:center;width:150px">
				<input type="text" name="thsms_base" style="width:150px;height:35px;"/>
			</td>
			<td style="background:#369;color:#fff;text-align:center;height:35px;width:150px" >
				<input type="submit" name="submit" value="COPY" style="width:140px;height:30px;cellpadding:5px" />
			</td>
		</tr>
	</table>
	
	<br/>
<%
		}
		if(jsoa!=null && jsoa.length()>0) {
%>	
	<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px">
	<tr>		
			<td style="background:#369;color:#fff;text-align:center;width:100%" colspan="5">
				<h3> FORM SETINGAN DAFTAR ULANG THSMS <%=target_thsms %></h3>
	</tr>
	<tr>
				
			<td style="width:35px;padding:3px"" >NO.</td>
			<td style="width:75px;padding:3px" >KDPST</td>
			<td style="width:480px;padding:3px"  align="center">LIST VERIFICATOR</td>
			<td style="width:75px;padding:3px" >URUTAN</td>
			<td style="width:135px;padding:3px" >KODE KAMPUS</td>
		</tr>				
<%
						
			for(int i=0;i<jsoa.length();i++,no++) {
				JSONObject job = jsoa.getJSONObject(i);
				// brs = job.t;
				//int brs = 0;
				try {
					
					//cmd_code = ""+(Integer)job.getInt("CMD_CODE");//1
					if(job.isNull("KDPST")) {
						kdpst = "null";
					}
					else {	
						kdpst = ""+(String)job.get("KDPST");
					}
					//System.out.println(i+".cmd code="+cmd_code);
					//2. kdpst = (String)job.get("KDPST");
					if(job.isNull("TKN_VERIFICATOR")) {
						tkn_verificator = "null";
					}
					else {	
						tkn_verificator = ""+(String)job.get("TKN_VERIFICATOR");
					}
					
					//3.thsms_sms = (String)job.get("THSMS");
					if(job.isNull("URUTAN")) {
						urutan = "null";
					}
					else {
						
						urutan = ""+(String)job.get("URUTAN");
					}
					
					
					//4.thsms_pmb = (String)job.get("THSMS_PMB");
					if(job.isNull("KODE_KAMPUS")) {
						kode_kampus = "null";
					}
					else {
						
						kode_kampus = ""+(String)job.get("KODE_KAMPUS");
					}
					
					%>					
		

			</td>
		</tr>


			<td align="center">
				<%=no %>.
			</td>
			<td align="center">
			<%
			if(editable) {
				%>
				<input type="hidden" name="kdpst_" value="<%=kdpst %>" />
				<%=kdpst %>
			<% 
			}
			else {
				out.print(kdpst);
			}
			%>
			</td>
			<td align="center">
			<%
			if(editable) {
				if(Checker.isStringNullOrEmpty(tkn_verificator)) {
					%>
				<textarea rows="2"  style="width:475px" name="tkn_verificator_" placeholder="isikan Objek Nickname dipisah dengan koma"></textarea>
						<%
				}
				else {
					%>
				<textarea rows="2" style="width:475px" name="tkn_verificator_"><%=tkn_verificator%></textarea>
					<%
				} 
			}
			else {
				out.print(tkn_verificator);
			}
			%>
			</td>
			<td align="center">
			<%
			if(editable) {
				if(Checker.isStringNullOrEmpty(urutan)) {
					%>
				<textarea rows="2"  style="width:75px" name="urutan_" placeholder="true/false"></textarea>
						<%
				}
				else {
					%>
				<textarea rows="2" style="width:75px" name="urutan_"><%=urutan%></textarea>
					<%
				} 
			}
			else {
				out.print(urutan);
			}
			%>
			</td>
			<td align="center">
			<%
			if(editable) {
				if(Checker.isStringNullOrEmpty(kode_kampus)) {
					%>
				<textarea rows="2"  style="width:135px" name="kode_kampus_" placeholder=""></textarea>
						<%
				}
				else {
					%>
				<textarea rows="2" style="width:135px" name="kode_kampus_"><%=kode_kampus%></textarea>
					<%
				} 
			}
			else {
				out.print(kode_kampus);
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