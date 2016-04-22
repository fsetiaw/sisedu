<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.*" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.login.*" %>
<%@ page import="beans.tmp.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>

<div>
	<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	Vector v_cf = validUsr.getScopeUpd7des2012("hasSpmiMenu");
	String atMenu = request.getParameter("atMenu");
	String target_kdpst = request.getParameter("target_kdpst");
	
	if(v_cf!=null && v_cf.size()>0) {	
	%>
	
<ul>	
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/indexSpmi.jsp?atMenu=spmi&target_kdpst=<%=target_kdpst %>" target="inner_iframe">BACK<span>&nbsp</span></a></li>
<%
		if(atMenu!=null && atMenu.equalsIgnoreCase("isi")) {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Flowchart/<%=target_kdpst %>/std2/indexStd2.jsp?atMenu=isi&target_kdpst=<%=target_kdpst %>" target="inner_iframe" class="active">PERNYATAAN ISI<span>STANDAR</span></a></li>
	<%
		}
		else {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Flowchart/<%=target_kdpst %>/std2/indexStd2.jsp?atMenu=isi&target_kdpst=<%=target_kdpst %>" target="inner_iframe">PERNYATAAN ISI<span>STANDAR</span></a></li>
	<%		
		}
		
		if(atMenu!=null && atMenu.equalsIgnoreCase("man")) {
			%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Flowchart/<%=target_kdpst %>/std1/manual/indexManual.jsp?atMenu=manual&target_kdpst=<%=target_kdpst %>" target="inner_iframe" class="active">STANDAR<span>MANUAL</span></a></li>
			<%
		}
		else {
			%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Flowchart/<%=target_kdpst %>/std1/manual/indexManual.jsp?atMenu=manual&target_kdpst=<%=target_kdpst %>" target="inner_iframe">STANDAR<span>MANUAL</span></a></li>
			<%		
		}
%>
</ul>
	<%
	
	}
	%>


</div>