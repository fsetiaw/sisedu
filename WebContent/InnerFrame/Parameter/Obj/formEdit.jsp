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
	beans.login.InitSessionUsr  validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	Vector vSortObj = (Vector) request.getAttribute("vSortExistingObj");
	
%>


</head>
<body onload="location.href='#'">
<div id="header">
<%@ include file="../objekParamInnerMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<%
		ListIterator lis = vSortObj.listIterator();
		if(lis.hasNext()) {
		%>
		<form action="">
			<table>
			<tr>
				<td width="20">LEVEL</td>
				<td width="20">ID</td>
				<td width="50">KDPST</td>
				<td width="250">KETERANGAN OBJEK</td>
				<td width="10">KDJEN</td>
				<td  width="250">NICKNAME OBJEK</td>
			</tr>
		<%	
			int prev = 0;
		    boolean first = true;
			while(lis.hasNext()) {
				String brs1 = (String)lis.next();
				//System.out.println(brs1);
				StringTokenizer st = new StringTokenizer(brs1,"$");
				String objLvl1 = st.nextToken();
				String kdpst1 = st.nextToken();
				String ketObj1 = st.nextToken();
				String idObj1 = st.nextToken();
				String kdjen1 = st.nextToken();
				String niknm1 = st.nextToken();
				if(first) {
					first = false;
					prev = Integer.valueOf(objLvl1).intValue();
		%>
			<tr>
				<td style="text-align:center"><%=objLvl1 %></td>
				<td style="text-align:center"><%=idObj1 %></td>
				<td style="text-align:center"><%=kdpst1 %></td>
				<td ><%=ketObj1 %></td>
				<td style="text-align:center"><%=kdjen1 %></td>
				<td ><%=niknm1 %></td>
			</tr>
		<%				
				}
				else {
					int curr = Integer.valueOf(objLvl1).intValue();
					if((prev+1)==curr) {
		%>
			<tr>
				<td ><%=objLvl1 %></td>
				<td ><%=idObj1 %></td>
				<td ><%=kdpst1 %></td>
				<td ><%=ketObj1 %></td>
				<td ><%=kdjen1 %></td>
				<td ><%=niknm1 %></td>
			</tr>
		<%	
					}
					else {
		%>
			<tr>
				<td >sisip</td>
				<td >sisip</td>
				<td >sisip</td>
				<td >sisip</td>
				<td >alias</td>
			</tr>
			<tr>
				<td ><%=objLvl1 %></td>
				<td ><%=idObj1 %></td>
				<td ><%=kdpst1 %></td>
				<td ><%=ketObj1 %></td>
				<td ><%=kdjen1 %></td>
				<td ><%=niknm1 %></td>
			</tr>
			
					<%
						
					}
					prev = curr;
				}
			}
		%>
			</table>
		</form>
		<%
		}
		%>
		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>