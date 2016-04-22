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
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/indexSummaryCivitasMenu.jsp?backTo=dashSummary.jsp" target="_self">BACK <span><b style="color:#eee">---</b></span></a></li>
<%
	//summary dosen
	//v_cf = validUsr.getScopeUpd7des2012("showListDsnAjar");
	if(true) {
		if(atPage.equalsIgnoreCase("dosenAjar")) {
		%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/Civitas/Dosen/listDosenPengajar.jsp?atPage=dosenAjar" target="inner_iframe" class="active">LIST DOSEN<span>PENGAJAR</span></a></li>
		<%
		}
		else {
		%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/Civitas/Dosen/listDosenPengajar.jsp?atPage=dosenAjar" target="inner_iframe">LIST DOSEN<span>PENGAJAR</span></a></li>
		<%	
		}
	}
	
	//v_cf = validUsr.getScopeUpd7des2012("showCFmhs");
	if(true) {
		if(atPage.equalsIgnoreCase("sebaranDosen")) {
		%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/Civitas/Dosen/summaryDosen.jsp?atPage=sebaranDosen" target="inner_iframe" class="active">SEBARAN<span>DOSEN</span></a></li>
		<%
		}
		else {
		%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/Civitas/Dosen/summaryDosen.jsp?atPage=sebaranDosen" target="inner_iframe">SEBARAN<span>DOSEN</span></a></li>
		<%	
		}
	}

	%>
	
</ul>