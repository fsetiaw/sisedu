<!DOCTYPE html>
<head>

<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.*" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.login.*" %>
<%@ page import="beans.tmp.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>


</head>
<body onload="location.href='#'">
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Analisa/dashStat.jsp" target="inner_iframe">GO<span>BACK</span></a></li>
	<%	
	String target_kdpst = request.getParameter("kdpst");
	String target_nmpst = request.getParameter("nmpst");
	System.out.println("ini di "+target_kdpst);
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	Vector vMkMhs = validUsr.getScopeUpd7des2012("viewForecastMhsPerMk");
	if(vMkMhs!=null && vMkMhs.size()>0) {
		ListIterator limm = vMkMhs.listIterator();
		while(limm.hasNext()) {
			String baris = (String)limm.next();
			StringTokenizer st = new StringTokenizer(baris);
			String idobj = st.nextToken();
			String kdpst = st.nextToken();
			String nmpst = st.nextToken();
			if(nmpst.startsWith("MHS_")) {
				nmpst = nmpst.substring(4,nmpst.length());
				nmpst = nmpst.replace("_"," ");
				nmpst = nmpst.replaceAll("_"," ");
			}	
			String objLv = st.nextToken();
			st = new StringTokenizer(nmpst);
			System.out.println("nmpst = "+nmpst);
			System.out.println("kdpst = "+kdpst);
			if(st.countTokens()==1) {
				if(kdpst.equalsIgnoreCase(target_kdpst)) {
				%>
				<li><a href="ForecastMakulMhsViewer.jsp?kdpst=<%=kdpst %>&nmpst=<%=nmpst %>" class="active" target="inner_iframe"><%=st.nextToken() %><span><b style="color:#eee">-</b> </span></a></li>
				<%
				}
				else {
				%>
				<li><a href="ForecastMakulMhsViewer.jsp?kdpst=<%=kdpst %>&nmpst=<%=nmpst %>" target="inner_iframe"><%=st.nextToken() %><span><b style="color:#eee">-</b> </span></a></li>
				<%
				}
			}
			else {
				if(st.countTokens()==2) {
					if(kdpst.equalsIgnoreCase(target_kdpst)) {
					%>
					<li><a href="ForecastMakulMhsViewer.jsp?kdpst=<%=kdpst %>&nmpst=<%=nmpst %>" class="active" target="inner_iframe"><%=st.nextToken() %><span><%=st.nextToken() %></span></a></li>
					<%	
					}
					else {
					%>
					<li><a href="ForecastMakulMhsViewer.jsp?kdpst=<%=kdpst %>&nmpst=<%=nmpst %>" target="inner_iframe"><%=st.nextToken() %><span><%=st.nextToken() %></span></a></li>
					<%
					}
				}
				else {
					if(st.countTokens()==3) {
						if(kdpst.equalsIgnoreCase(target_kdpst)) {
						%>
						<li><a href="ForecastMakulMhsViewer.jsp?kdpst=<%=kdpst %>&nmpst=<%=nmpst %>" class="active" target="inner_iframe"><%=st.nextToken()%> <span><%=st.nextToken()+" "+st.nextToken()  %></span></a></li>
						<%	
						}
						else {
						%>
						<li><a href="ForecastMakulMhsViewer.jsp?kdpst=<%=kdpst %>&nmpst=<%=nmpst %>" target="inner_iframe"><%=st.nextToken()%> <span><%=st.nextToken()+" "+st.nextToken()  %></span></a></li>
						<%
						}
					}
					else {
						if(st.countTokens()==4) {
							if(kdpst.equalsIgnoreCase(target_kdpst)) {
							%>
							<li><a href="ForecastMakulMhsViewer.jsp?kdpst=<%=kdpst %>&nmpst=<%=nmpst %>" class="active" target="inner_iframe"><%=st.nextToken()+" "+st.nextToken()  %><span><%=st.nextToken()+" "+st.nextToken()  %></span></a></li>
							<%
							}	
							else {
							%>
							<li><a href="ForecastMakulMhsViewer.jsp?kdpst=<%=kdpst %>&nmpst=<%=nmpst %>" target="inner_iframe"><%=st.nextToken()+" "+st.nextToken()  %><span><%=st.nextToken()+" "+st.nextToken()  %></span></a></li>
							<%
							}
						}
						else {
							if(st.countTokens()==0) {
								//ignore
							}
							else {
								if(kdpst.equalsIgnoreCase(target_kdpst)) {
								%>
								<li><a href="ForecastMakulMhsViewer.jsp?kdpst=<%=kdpst %>&nmpst=<%=nmpst %>" class="active" target="inner_iframe"><%=st.nextToken()+" "+st.nextToken()+" "+st.nextToken() %><span>
								<%
								String tmp = "";
								while(st.hasMoreTokens()) {
									tmp = tmp+st.nextToken();
									if(st.hasMoreTokens()) {
										tmp=tmp+" ";
									}
								}
								out.print(tmp);
								%>
								</span></a></li>
								<%	
								}
								else {
								%>
								<li><a href="ForecastMakulMhsViewer.jsp?kdpst=<%=kdpst %>&nmpst=<%=nmpst %>" target="inner_iframe"><%=st.nextToken()+" "+st.nextToken()+" "+st.nextToken() %><span>
								<%
								String tmp = "";
								while(st.hasMoreTokens()) {
									tmp = tmp+st.nextToken();
									if(st.hasMoreTokens()) {
										tmp=tmp+" ";
									}
								}
								out.print(tmp);
								%>
								</span></a></li>
								<%
								}
							}	
						}
					}
				}
			}
		}
	}

	%>
	

</body>