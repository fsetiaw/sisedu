<!DOCTYPE html>
<html>
<head>
<%@ page import="beans.tmp.*" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.dbase.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.login.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<title>Insert title here</title>
<%
	String thsms_now = Checker.getThsmsNow();
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	Vector v = (Vector)session.getAttribute("vJsoa");
	//out.println(v.size());
	String target_thsms = request.getParameter("target_thsms");
	if(target_thsms==null || target_thsms.equalsIgnoreCase("null")) {
		target_thsms = ""+thsms_now;
	}
	Vector vListThsms = Tool.returnTokensListThsms("20111", thsms_now);
	
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
		
	<table border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:600px" align="center">
		<form action="get.listMhsAktif">
		<tr>
        	<td style="background:#369;color:#fff;text-align:center" colspan="5">
			<p style="font-weight:bold">PILIH TAHUN/SMS<select name="target_thsms">
        	<%
        	int i=0,total=0;
        	ListIterator li1 = vListThsms.listIterator();
        	while(li1.hasNext()) {
        		String thsms_tmp = (String)li1.next();
        		if(thsms_tmp.equalsIgnoreCase(target_thsms)) {
        		%>
        		<option value=<%=thsms_tmp %> selected><%=Converter.convertThsmsKeterOnly(thsms_tmp) %></option>
        		<%
        		}
        		else {
        		%>
            	<option value=<%=thsms_tmp %>><%=Converter.convertThsmsKeterOnly(thsms_tmp) %></option>
            	<%	
        		}
        	}
        	%>
        	</select>
        	<input type="submit" value=" <Klik> untuk Hitung Ulang" style="font-style:italic;"/>
        	</p>
			</td>
        </tr>	
        </form>
        <%	
		if(v!=null && v.size()>0) {
			int daftar_tt=0,ulang_tt=0,saring_tt=0,baru_tt=0,pindahan_tt=0,laki_tt=0,wanita_tt=0;
			ListIterator li = v.listIterator();
		 %>
        <tr>
        	<td style="background:#369;color:#fff;text-align:center;width:15px"><label><B>NO.</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:left;width:300px"><label><B>NAMA PROGRAM STUDI</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>JENJANG</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center;width:100px"><label><B>KAMPUS</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center;width:35px"><label><B># MHS AKTIF</B> </label></td>
        </tr>
		
		<%
			int k = 0;
			while(li.hasNext()) {
				k++;
				String brs = (String) li.next();
				JSONArray jsoa = (JSONArray) li.next();
				total = total + jsoa.length();
				String kmp = null;
				if(jsoa!=null && jsoa.length()>0) {
					JSONObject job = jsoa.getJSONObject(0);
					kmp = job.getString("KODE_KAMPUS_DOMISILI");	
				}
				
				StringTokenizer st = new StringTokenizer(brs);
				String idObj = st.nextToken();
				String kdpst = st.nextToken();
				String keterObj = st.nextToken();
				String lvl_obj = st.nextToken();
				String kdjen = st.nextToken();
				%>
		        <tr>
		        	<td style="text-align:center;width:15px"><label><B><%=++i %></B> </label></td>
		        	<td style="text-align:left;width:300px"><label><B><%=Converter.getNamaKdpst(kdpst) %></B> </label></td>
		        	<td style="text-align:center;width:50px"><label><B><%=Converter.getDetailKdjen(kdjen) %></B> </label></td>
		        	<td style="text-align:center;width:100px"><label><B><%=kmp %></B> </label></td>
		        	<td style="text-align:center;width:35px"><label><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/Civitas/Mhs/stdTampleteListMhsJson.jsp?atMenu=mhsAktif&norut=<%=k%>"><%=jsoa.length() %></B></a> </label></td>
		        </tr>
				<%
			}
		%>
		<tr>
        	<td style="background:#369;color:#fff;text-align:center" colspan="4"><label><B>TOTAL</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center"><label><B><%=total %></B> </label></td>
        </tr>
       <%
		}
	%> 
	</table>
	
	</div>
</div>	
</body>
</html>