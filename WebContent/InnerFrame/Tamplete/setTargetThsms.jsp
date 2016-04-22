<!DOCTYPE html>
<head>
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
	String kdpst=kdpst+"&npm="+npm+"&cmd="+cmd;
%>


</head>
<body onload="location.href='#'">
<div id="header">
	<%
	//Vector vSc = validUsr.getScopeUpd7des2012("allowViewKurikulum");
	Vector vSc = validUsr.getScopeUpd7des2012(scope);
	if(callerPage!=null && !Checker.isStringNullOrEmpty(callerPage)) {
	%>
	<ul>
		<li><a href="<%=callerPage %>" target="inner_iframe">GO<span>BACK</span></a></li>
	</ul>
	<%
	}
	%>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
 		<form action="">
			<table align="center" border="1" style="background:#d9e1e5;color:#000;">	
				<tr>
					<td align="center" bgcolor="#369" style="color:#fff" padding-left="2px"><b>PILIH TAHUN SEMESTER</b></td>
				</tr>	
				<tr>	
					<td align="left" width="100px" style="padding-left:2px">
							<select name="kdpst_nmpst">
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:700px">
		<tr>
			<td style="background:#369;color:#fff;text-align:center;height:30px;font-size:25px" colspan="6"><label><B>PENGISIAN KRS/KSH</B></label>
			<%
			//v = Constants.getListThsms();
			v = Constants.getListThsmsFromSmawl(v_npmhs);
			if(v!=null) {
				if(v.size()>0) {
					ListIterator li1 = v.listIterator();
					String baseThsms = (String)li1.next();
					%>
					<select name="thsms" style="font-size:20px;">
					<%
						while(li1.hasNext()) {
							String tmp = (String)li1.next();
								String keter_thsms = "N/A";
								String value_thsms = "N/A";
							if(Tool.isThsmsEqualsSmawl(tmp,baseThsms)) {
							//if(tmp.equalsIgnoreCase(baseThsms)) {
								String keter_thsms_and_value = Converter.convertThsms(tmp);
								StringTokenizer stt = new StringTokenizer(keter_thsms_and_value,"#&");
								keter_thsms = stt.nextToken();
								value_thsms = stt.nextToken();
						%>
						<option value="<%=value_thsms %>" selected="selected"><%=keter_thsms%></option>
						<%
							}
							else {
								String keter_thsms_and_value = Converter.convertThsms(tmp);
								StringTokenizer stt = new StringTokenizer(keter_thsms_and_value,"#&");
								keter_thsms = stt.nextToken();
								value_thsms = stt.nextToken();
							%>
							<option value="<%=value_thsms %>"><%=keter_thsms%></option>
							<%	
							}
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
		
	</div>
</div>		
</body>
</html>