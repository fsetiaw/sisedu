<!DOCTYPE html>
<head>
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
	String tipeForm = request.getParameter("formType");
	Vector vDwn = validUsr.getScopeUpd7des2012("hasDownloadMenu");
	request.setAttribute("atPage", "dash");
	
%>
</head>
<body>
<div id="header">
	<ul>
	<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Download/innerMenu.jsp" />
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<!-- 
		<p>
			<form action="file.upload" method="POST" enctype="multipart/form-data" target="_self">
			<table border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;">
					<tr>
        				<td style="background:#369;color:#fff;text-align:center" colspan="2"><label><B>UPLOAD FILE</B> </label></td>
        			</tr>
					<tr>
						<td><input type="file" name="fileSelect" value="Pilih File" /></td>
					</tr>
            		<tr>
						<td style="background:#369;color:#fff;text-align:right"><input type="submit" value="Upload File" style="width:35%" /></td>
					</tr>
			</table>		
        	</form>
		</p>
		<p>
			<form action="file.DownloadForm"  target="_self">
				<table border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;">
					<tr>
        				<td style="background:#369;color:#fff;text-align:center" colspan="2"><label><B>MASTER DOKUMEN DOWNLOAD</B> </label></td>
        			</tr>
					<tr>
						<td style="width:150px">JENIS DOKUMEN</td>
						<td>
							<select name="formType" onchange='this.form.submit()' style="width:100%">
								<%
								String[]tipe = Constants.getListFormType();
								boolean match = false;
								for(int i=0;i<tipe.length;i++) {
									if(tipeForm!=null && tipeForm.equalsIgnoreCase(tipe[i])) {
										match = true;
									%>	
										<option value="<%=tipe[i] %>" selected><%=tipe[i] %></option> 
									<%	
									}
									else {
									%>	
										<option value="<%=tipe[i] %>"><%=tipe[i] %></option> 
									<%
									}
								}
								if(!match) {
								%>	
									<option value="" selected></option> 
								<%
								}
								%>
							</select>
						</td>
					</tr>
				<%
				if(tipeForm!=null) {
					ListIterator liDwn = vDwn.listIterator();
				%>
					<tr>
						<td>PROGRAM STUDI</td>
						<td><select name="kdpst_keter" style="width:100%">
						<%
						while(liDwn.hasNext()) {
							String baris = (String)liDwn.next();
							StringTokenizer st = new StringTokenizer(baris);
							String idObj = st.nextToken();
							String kdpst = st.nextToken();
							String keter = st.nextToken();
							keter = keter.replaceAll("_"," ");
							keter = keter.replaceAll("MHS", "");
							String objLvl = st.nextToken();
						%>
							<option value="<%=kdpst+"_"+keter%>" ><%=keter %></option>
						<%	
						}
					%>
						</select></td>
					</tr>	
					<tr>
						<td style="background:#369;color:#fff;text-align:right" colspan="2"><input type="submit" value="Download Form" style="width:25%" /></td>
					</tr>
				<%
				}
				%>	
				</table>
			</form>
		</p>
		 -->
	</div>
</div>		
</body>
</html>