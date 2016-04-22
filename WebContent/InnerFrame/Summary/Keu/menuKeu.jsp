<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.*" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.login.*" %>
<%@ page import="beans.tmp.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%
	String atPage = null;
	atPage = request.getParameter("atPage");
	if(atPage==null || Checker.isStringNullOrEmpty("atPage")) {
		atPage = ""+request.getAttribute("atPage");
		request.removeAttribute("atPage");
	}	
	Vector v_cf = null;
%>
<ul>
		<%
	//if(atPage!=null && (atPage.equalsIgnoreCase("cashFlow")||atPage.equalsIgnoreCase("showListKuiAll"))) {
		%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/dashSummary.jsp" target="_self">BACK <span><b style="color:#eee">---</b></span></a></li>
		<%
	//}
	v_cf = validUsr.getScopeUpd7des2012("showCFmhs");
	if(v_cf!=null && v_cf.size()>0) {
		if(atPage.equalsIgnoreCase("cashFlow")) {
		%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/Keu/preProsesSummaryBlankPage.jsp?targetPage=cashFlow" target="inner_iframe" class="active">CASH<span>FLOW</span></a></li>
		<%
		}
		else {
		%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/Keu/preProsesSummaryBlankPage.jsp?targetPage=cashFlow" target="inner_iframe">CASH<span>FLOW</span></a></li>
		<%	
		}
	}
	
	
	Vector vGen = validUsr.getScopeUpd7des2012("showListKuiAll");
	if(vGen!=null && vGen.size()>0) {
		if(atPage.equalsIgnoreCase("listKuitansi")) {
		%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/Keu/preProsesSummaryBlankPage.jsp?targetPage=listKui&x=0&y=11&filterTgl=tglkui" target="inner_iframe" class="active">LIST<span>KUITANSI</span></a></li>
		<%
		}
		else {
		%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/Keu/preProsesSummaryBlankPage.jsp?targetPage=listKui&x=0&y=11&filterTgl=tglkui" target="inner_iframe">LIST<span>KUITANSI</span></a></li>
		<%	
		}
	}
	
	vGen = validUsr.getScopeUpd7des2012("monitorKeu");
	if(vGen!=null && vGen.size()>0) {
		if(atPage.equalsIgnoreCase("monitor")) {
		%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/Keu/dashMonitorKeu.jsp?atPage=monitor" target="inner_iframe" class="active">MONITORING<span>& EVALUASI</span></a></li>
		<%
		}
		else {
		%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/Keu/dashMonitorKeu.jsp?atPage=monitor" target="inner_iframe">MONITORING<span>& EVALUASI</span></a></li>
		<%	
		}
	}
	%>
	
</ul>