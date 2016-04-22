<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.*" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.login.*" %>
<%@ page import="beans.tmp.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%
	String atMenu = ""+request.getParameter("atMenu");
	String target = null;
	String uri = null;
	String url = null;
	//request.removeAttribute("atMenu");
%>
<ul>
	<%	
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String cmd = "";
//	if(backTo==null || backTo.equalsIgnoreCase("null")) {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/dashSummary.jsp" target="_self">BACK <span><b style="color:#eee">---</b></span></a></li>
	<%
//	}
	if(validUsr.isAllowTo("hasSubMenuAkdmkPerkuliahan")>0) {
		//target = Constants.getRootWeb()+"/InnerFrame/Akademik/dashKelasPerkuliahan.jsp";
		target = Constants.getRootWeb()+"/InnerFrame/Summary/dashKelasPerkuliahan.jsp";
		uri = request.getRequestURI();
		url = PathFinder.getPath(uri, target);
		if(atMenu!=null && atMenu.equalsIgnoreCase("kelasPerkuliahan")) {
	%>		
			<li><a href="<%=url %>?atMenu=kelasPerkuliahan&backTo=indexSummaryInfoPerkuliahanMenu.jsp" target="_self" class="active">INFO KELAS<span>PERKULIAHAN</span></a></li>
	<%
			
		}
		else {
	%>		
			<li><a href="<%=url %>?atMenu=kelasPerkuliahan&backTo=indexSummaryInfoPerkuliahanMenu.jsp" target="_self">INFO KELAS<span>PERKULIAHAN</span></a></li>
	<%
		}
	}
	

	%>
	
</ul>
