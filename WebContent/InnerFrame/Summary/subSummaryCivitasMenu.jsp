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
	//request.removeAttribute("atMenu");
	Vector vTmp = null;
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
/*
	vTmp = validUsr.getScopeUpd7des2012("summaryPMB");
	if(vTmp!=null && vTmp.size()>0) {		
		if(atMenu.equalsIgnoreCase("summaryPmbMenu")) {
			%>
			<li><a href="get.summaryPmb" target="inner_iframe" class="active">PENERIMAAN<span>MHS BARU</span></a></li>
			<%
		}
		else {
			%>
			<li><a href="get.summaryPmb" target="inner_iframe">PENERIMAAN<span>MHS BARU</span></a></li>
			<%	
		}
	}
*/	
	Vector vTtMhs = validUsr.getScopeUpd7des2012("showSummaryTTmhs");
	if(vTtMhs!=null && vTtMhs.size()>0) {
	//viewSummaryTTmhs.jsp
		if(atMenu.equalsIgnoreCase("civitas")) {
		%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/Civitas/Mhs/indexSummaryMhsMenu.jsp?targetPage=totMhs" target="inner_iframe" class="active">SUMMARY<span>MAHASISWA</span></a></li>
		<%
		}
		else {
		%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/Civitas/Mhs/indexSummaryMhsMenu.jsp?targetPage=totMhs" target="inner_iframe">SUMMARY<span>MAHASISWA</span></a></li>
		<%	
		}
	}	
	//summary dosen
	Vector v_cf = validUsr.getScopeUpd7des2012("hasSummaryDsnMenu");
	if(v_cf!=null && v_cf.size()>0) {
		if(atMenu.equalsIgnoreCase("dosenAjar")) {
			//Constants.getRootWeb()+"/" ${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/summaryDosen.jsp?atPage=dosen
		%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/Civitas/Dosen/listDosenPengajar.jsp?atPage=dosenAjar" target="inner_iframe" class="active">SUMMARY<span>DOSEN</span></a></li>
		<%
		}
		else {
		%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/Civitas/Dosen/listDosenPengajar.jsp?atPage=dosenAjar" target="inner_iframe">SUMMARY<span>DOSEN</span></a></li>
		<%	
		}
	}
	
	%>
	
</ul>
