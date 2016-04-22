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
String target_thsms = Checker.getThsmsNow();
//String target_thsms = "20142";
String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp";
String uri = request.getRequestURI();
String url = PathFinder.getPath(uri, target);
Vector vScope_cmd = (Vector)session.getAttribute("vScope_cmd");
session.removeAttribute("vScope_cmd");
String tkn_tipe_ujian = "UTS UAS";
StringTokenizer st = new StringTokenizer(tkn_tipe_ujian);
int total_tipe_kartu_ujian = st.countTokens();
int norut = 0;
%>


<jsp:include page="<%= url %>" flush="true" />
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<br/>
<form action="go.updParamKartuUjianRules" method="post">
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
if(vScope_cmd!=null && vScope_cmd.size()>0) {
	String id_obj = "null";
	String kdpst = "null";
	String nmpst = "null";
	String obj_lvl = "null";
	String kdjen = "null";
	String kd_kmp = "null";
	String tipe_ujian = "null";
	String tkn_ver = "null";
	String urutan = "null";
%>
	<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px">
	<tr>		
		<td style="background:#369;color:#fff;text-align:center;width:100%" colspan="6">
		<h3> FORM SETINGAN DAFTAR ULANG THSMS <%=target_thsms %></h3>
	</tr>
	<tr>
				
		<td style="width:35px;padding:3px"" >NO.</td>
		<td style="width:75px;padding:3px" >KDPST</td>
		<td style="width:50px;padding:3px" >TIPE UJIAN</td>
		<td style="width:465px;padding:3px"  align="center">LIST VERIFICATOR</td>
		<td style="width:75px;padding:3px" >URUTAN</td>
		<td style="width:100px;padding:3px" >KODE KAMPUS</td>
	</tr>
<%	
	ListIterator li = vScope_cmd.listIterator();
	
	while(li.hasNext()) {
		String tipe = "";
		String brs = (String)li.next();
		//122 22201 MHS_TEKNIK_SIPIL_KAMPUS_JAMSOSTEK 122 C JST
		
		
		

		st = new StringTokenizer(brs);
		if(st.countTokens()==6) {
			id_obj = st.nextToken();
			kdpst = st.nextToken();
			nmpst = st.nextToken();
			obj_lvl = st.nextToken();
			kdjen = st.nextToken();
			kd_kmp = st.nextToken();
			norut = 1;
		}
		else {
			//count == 1
			st = new StringTokenizer(brs,"`");		
			tipe_ujian = st.nextToken();
			tkn_ver = st.nextToken();
			urutan = st.nextToken();
%>
<tr>
	<td style="width:35px;padding:3px" ><%=norut++ %>.</td>
	<td style="width:75px;padding:3px" >
	<input type="text" value="<%=kdpst%>" name="kdpst" readonly="readonly" style="width:99%"/>  
	</td>
	<td style="width:50px;padding:3px" >
		<input type="text" value="<%=tipe_ujian%>" name="tipe_ujian" style="width:99%" readonly="readonly"/>
	</td>
	<td style="width:465px;padding:3px"  align="center">
	<%
			if(tkn_ver.equalsIgnoreCase("null")) {
	%>
	<input type="text" value="" name="tkn_ver" placeholder="Object Nickname seperated by koma" style="width:99%"/>
	<% 	
			}
			else {
	%>
	<input type="text" value="<%=tkn_ver %>" name="tkn_ver" style="width:99%"/>
	
	<% 	
			}
	%>
	
	</td>
	
	<td style="width:75px;padding:3px" >
	<%
			if(Checker.isStringNullOrEmpty(urutan)) {
	%>
	<input type="text" value="false" name="urutkah" style="width:99%"/>
	<%
			}
			else {
	%>
	<input type="text" value="<%=urutan %>" name="urutkah" style="width:99%"/>
	<%
			}
	%>
	</td>
	<td style="width:100px;padding:3px" ><input type="text" value="<%=kd_kmp %>" name="kode_kmp" style="width:99%" readonly="readonly" /></td>
</tr>		
<%							
			
		}
		
			
		
	}
%>
<tr>
			<td style="background:#369;color:#fff;text-align:center;height:35px" colspan="6">
				<input type="submit" name="submit" value="UPDATE" style="width:70%;height:30px;cellpadding:5px" />
			</td>
		</tr>
	</table>
<%	
}
else {
	out.print("BELUM ADA DATA");
}
%>		
		

		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>