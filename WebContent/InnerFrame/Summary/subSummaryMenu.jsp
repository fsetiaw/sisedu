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
	//beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	if(validUsr.isAllowTo("hasSummaryCivitasMenu")>0) {
		if(atPage.equalsIgnoreCase("summaryCivitasMenu")) {
			%>
			<li><a href="indexSummaryCivitasMenu.jsp?backTo=dashSummary.jsp" target="inner_iframe" class="active">INFO<span>CIVITAS</span></a></li>
			<%
		}
		else {
			%>
			<li><a href="indexSummaryCivitasMenu.jsp?backTo=dashSummary.jsp" target="inner_iframe">INFO<span>CIVITAS</span></a></li>
			<%	
		}
	}


	v_cf = validUsr.getScopeUpd7des2012("hasSummaryMenuInfoPerkuliahan");
	if(v_cf!=null && v_cf.size()>0) {
		if(atPage.equalsIgnoreCase("perkuliahan")) {
	%>
		<li><a href="indexSummaryInfoPerkuliahanMenu.jsp?backTo=dashSummary.jsp" target="inner_iframe" class="active">INFO<span>PERKULIAHAN</span></a></li>
	<%
		}
		else {
	%>
		<li><a href="indexSummaryInfoPerkuliahanMenu.jsp?backTo=dashSummary.jsp" target="inner_iframe">INFO<span>PERKULIAHAN</span></a></li>
	<%	
		}
	}
	/* pinah ke info civitas menu */
	//summary dosen
	/*
	v_cf = validUsr.getScopeUpd7des2012("hasSummaryDsnMenu");
	if(v_cf!=null && v_cf.size()>0) {
		if(atPage.equalsIgnoreCase("dosenAjar")) {
			//Constants.getRootWeb()+"/" ${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/summaryDosen.jsp?atPage=dosen
		%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/listDosenPengajar.jsp?atPage=dosenAjar" target="inner_iframe" class="active">INFO<span>DOSEN</span></a></li>
		<%
		}
		else {
		%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/listDosenPengajar.jsp?atPage=dosenAjar" target="inner_iframe">INFO<span>DOSEN</span></a></li>
		<%	
		}
	}
	*/
	
	Vector vGen = validUsr.getScopeUpd7des2012("showCFmhs");
	if(vGen==null || vGen.size()<1) {
		vGen = validUsr.getScopeUpd7des2012("showListKuiAll");
	}
	if(vGen!=null && vGen.size()>0) {
		if(atPage.equalsIgnoreCase("bagKeu")) {
		%>
		<li><a href="Keu/dashSummaryKeu.jsp?atPage=bagKeu" target="inner_iframe" class="active">INFO<span>KEUANGAN</span></a></li>
		<%
		}
		else {
		%>
		<li><a href="Keu/dashSummaryKeu.jsp?atPage=bagKeu" target="inner_iframe">INFO<span>KEUANGAN</span></a></li>
		<%	
		}
	}


	%>
	
</ul>