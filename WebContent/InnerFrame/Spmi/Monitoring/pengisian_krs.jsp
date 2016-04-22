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
	
	String atMenu = request.getParameter("atMenu");
	Vector vKdpstNpm = (Vector)session.getAttribute("vKdpstNpm");
	//System.out.println("at index spmy = "+atMenu);
%>
</head>
<body>
<div id="header">
	<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/subTopMenu.jsp" >
	<jsp:param name="atMenu" value="<%=atMenu %>"/>

	</jsp:include>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<%
		if(vKdpstNpm==null || vKdpstNpm.size()<1) {
			out.print("<h2 align=\"center\"> KRS yang sudah diinput : 0 </h2>");
		}
		else {
		%>
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:750px"> 
        	<tr> 
            	<td style="background:#369;color:#fff;text-align:center" colspan="5"><label><B>DAFTAR MAHASISWA YANG MENDAFTAR ULANG</B> </label></td> 
         	</tr> 
         	<tr> 
              	<td style="background:#369;color:#fff;text-align:center;width:20px">NO</td>
              	<td style="background:#369;color:#fff;text-align:center;width:150px">PRODI</td>
              	<td style="background:#369;color:#fff;text-align:center;width:250px">TOT KRS</td>
            </tr> 
		<%		
			int counter = 0;
			int norut = 0;
			boolean first = true;
			String prev_kdpst = ""; 
			ListIterator li = vKdpstNpm.listIterator();
			do {
				String brs = (String)li.next();
				StringTokenizer st = new StringTokenizer(brs,"`");
				String kdpst = st.nextToken();
				String npmhs = st.nextToken();
				if(first) {
					first = false;
					prev_kdpst = new String(kdpst);
					counter++;
				}
				else {
					if(kdpst.equalsIgnoreCase(prev_kdpst)) {
						counter++;
					}
					else {
						//ganti prodi
						//1. print row
						//2. reset counter
						//3. set prev_kdpst dgn yg baru
					%>
			<tr> 
              	<td style="text-align:center"><%=++norut %></td>
              	<td style="text-align:center"><%=prev_kdpst %></td>
              	<td style="text-align:center"><%=counter %></td>
            </tr> 		
					<%	
						counter = 0;
						prev_kdpst = new String(kdpst);
					}
				}
				
			}
			while(li.hasNext());
			%>
			<tr> 
              	<td style="text-align:center"><%=++norut %></td>
              	<td style="text-align:center"><%=prev_kdpst %></td>
              	<td style="text-align:center"><%=counter %></td>
            </tr> 		
			<%	
		}
		%>
		
		<!-- Column 1 start -->

	</div>
</div>		
</body>
</html>