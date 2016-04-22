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
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Flowchart/<%=target_kdpst %>/std1/indexStd1.jsp?atMenu=std1&target_kdpst=<%=target_kdpst %>" target="inner_iframe">BACK<span>&nbsp</span></a></li>
<%

		
		if(atMenu!=null && atMenu.equalsIgnoreCase("p")) {
			%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Flowchart/<%=target_kdpst %>/std1/manual/indexManual.jsp?atMenu=p&target_kdpst=<%=target_kdpst %>" target="inner_iframe" class="active">MANUAL PENETAPAN<span>STANDAR</span></a></li>
			<%
		}
		else {
			%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Flowchart/<%=target_kdpst %>/std1/manual/indexManual.jsp?atMenu=p&target_kdpst=<%=target_kdpst %>" target="inner_iframe">MANUAL PENETAPAN<span>STANDAR</span></a></li>
			<%		
		}
		if(atMenu!=null && atMenu.equalsIgnoreCase("d")) {
			%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Flowchart/<%=target_kdpst %>/std1/manual/indexManual.jsp?atMenu=d&target_kdpst=<%=target_kdpst %>" target="inner_iframe" class="active">MANUAL PELAKSANAAN<span>STANDAR</span></a></li>
			<%
		}
		else {
			%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Flowchart/<%=target_kdpst %>/std1/manual/indexManual.jsp?atMenu=d&target_kdpst=<%=target_kdpst %>" target="inner_iframe">MANUAL PELAKSANAAN<span>STANDAR</span></a></li>
			<%		
		}
		if(atMenu!=null && atMenu.equalsIgnoreCase("s")) {
			%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Flowchart/<%=target_kdpst %>/std1/manual/indexManual.jsp?atMenu=s&target_kdpst=<%=target_kdpst %>" target="inner_iframe" class="active">MANUAL EVALUASI<span>& PENGENDALIAN STANDAR</span></a></li>
			<%
		}
		else {
			%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Flowchart/<%=target_kdpst %>/std1/manual/indexManual.jsp?atMenu=s&target_kdpst=<%=target_kdpst %>" target="inner_iframe">MANUAL EVALUASI<span>& PENGENDALIAN STANDAR</span></a></li>
			<%		
		}
		if(atMenu!=null && atMenu.equalsIgnoreCase("a")) {
			%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Flowchart/<%=target_kdpst %>/std1/manual/indexManual.jsp?atMenu=a&target_kdpst=<%=target_kdpst %>" target="inner_iframe" class="active">MANUAL PENINGKATAN<span>STANDAR</span></a></li>
			<%
		}
		else {
			%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Flowchart/<%=target_kdpst %>/std1/manual/indexManual.jsp?atMenu=a&target_kdpst=<%=target_kdpst %>" target="inner_iframe">MANUAL PENINGKATAN<span>STANDAR</span></a></li>
			<%	
		}
	%>

</ul>
	<%
	
	}
	%>


</div>