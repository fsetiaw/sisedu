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

	Vector v = null;
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String objId=(String)request.getParameter("id_obj");
	String objLvl=(String)request.getParameter("obj_lvl");
	String nmm=(String)request.getParameter("nmm");
	String kdpst=(String)request.getParameter("kdpst");
	String npm=(String)request.getParameter("npm");
	String cmd=(String)request.getParameter("cmd");
	String tkn_thsms_whiteList = (String)request.getParameter("tkn_thsms_whiteList");
	int tkn_thsms_whiteList_count = 0;
	StringTokenizer st = null;
	if(tkn_thsms_whiteList!=null) {
		st = new StringTokenizer(tkn_thsms_whiteList,",");
		tkn_thsms_whiteList_count = st.countTokens();
	}
	
//	Vector vSdh =(Vector) request.getAttribute("vSdh");
//	Vector vBlm =(Vector) request.getAttribute("vBlm");
//	Vector vCp =(Vector) request.getAttribute("vCp");
//	session.setAttribute("vSdh", vSdh);
//	session.setAttribute("vBlm", vBlm);
//	session.setAttribute("vCp", vCp);
//	String fwdTo=(String)request.getParameter("fwdTo");
	
	//String cmd = request.getParameter("cmd");
	//request.setAttribute("cmd", cmd);
	
	//String msg = request.getParameter("msg");
	
%>


</head>
<!--  body onload="location.href='#'" -->
<body>
<div id="header">
	<ul>
		<li><a href="get.histKrs?id_obj=<%=objId%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=objLvl %>&kdpst=<%=kdpst %>&cmd=histKrs" target="inner_iframe">GO<span>BACK</span></a></li>
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
 		<!--  form action="${PageContext.ServletContext.ContextPath}fwdTo%>"-->
 		
 		<!--  form action="go.filterUpdFormKrsType" -->
 
 		<form action="go.updateKrsKhs">
 			<!--  input type="hidden" value="fwdTo%>" name="fwdTo" / -->
 			<input type="hidden" value="<%= cmd%>" name="cmd" />
 			<input type="hidden" value="<%= objId%>" name="id_obj" />
 			<input type="hidden" value="<%=nmm %>" name="nmm" />
 			<input type="hidden" value="<%=npm %>" name="npm" />
 			<input type="hidden" value="<%=kdpst %>" name="kdpst" />
 			<input type="hidden" value="<%=objLvl %>" name="obj_lvl" />
			<table align="center" border="1" style="background:#d9e1e5;color:#000;">	
				<tr>
					<td align="center" bgcolor="#369" style="color:#fff" padding-left="2px"><b>PILIH TAHUN SEMESTER</b></td>
				</tr>	
				<tr>	
					<td align="left" width="100px" style="padding-left:2px">
			<%		
					if(tkn_thsms_whiteList==null || Checker.isStringNullOrEmpty(tkn_thsms_whiteList)) {
						v = Constants.getListThsmsFromSmawl(npm);
						if(v!=null) {
							if(v.size()>0) {
								ListIterator li1 = v.listIterator();
								String baseThsms = (String)li1.next();
						%>
						<select name="targetThsms" style="font-size:20px;">
						<%
								while(li1.hasNext()) {
									String tmp = (String)li1.next();
									String keter_thsms = "N/A";
									String value_thsms = "N/A";
									//if(Tool.isThsmsEqualsSmawl(tmp,baseThsms)) {
									if(tmp.equalsIgnoreCase(Checker.getThsmsKrs())) {	
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
				<%	
							}
						}
					}
					else {
						//tkn_thsms_whiteList!=null 
						%>
						<select name="targetThsms" style="font-size:20px;">
						<%
						while(st.hasMoreTokens()) {
							String tmp = st.nextToken();
							String keter_thsms_and_value = Converter.convertThsms(tmp);
							StringTokenizer stt = new StringTokenizer(keter_thsms_and_value,"#&");
							String keter_thsms = stt.nextToken();
							String value_thsms = stt.nextToken();
									
								%>
							<option value="<%=value_thsms %>"><%=keter_thsms%></option>
								<%		
						}
						%>
						</select>		
					<%			
					}
					%>
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