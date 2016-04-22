<%@ page import="beans.setting.*" %>
<%@ page import="java.util.*" %>
<%@ page import="beans.login.*" %> 
<%
//beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String atMenu = ""+request.getParameter("atMenu");
	//request.removeAttribute("atMenu");
%>
<ul>
	<%	
//	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String cmd = "";
//	if(backTo==null || backTo.equalsIgnoreCase("null")) {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/indexSummaryCivitasMenu.jsp?backTo=dashSummary.jsp" target="_self">BACK <span><b style="color:#eee">---</b></span></a></li>
	<%
//	}
	Vector vTmp = validUsr.getScopeUpd7des2012("summaryPMB");
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
	//Vector vTtMhs = validUsr.getScopeUpd7des2012("showSummaryTTmhs");
	Vector vTtMhs = validUsr.getScopeUpd7des2012ProdiOnly("allowGetMhsAktif");
	System.out.println("vTtMhs="+vTtMhs);
	if(vTtMhs!=null && vTtMhs.size()>0) {
	//viewSummaryTTmhs.jsp
		if(atMenu.equalsIgnoreCase("mhsAktif")) {
		%>
		<li><a href="get.listMhsAktif?atMenu=mhsAktif" target="inner_iframe" class="active">MAHASISWA<span>AKTIF</span></a></li>
		<%
		}
		else {
		%>
		<li><a href="get.listMhsAktif?atMenu=mhsAktif" target="inner_iframe">MAHASISWA<span>AKTIF</span></a></li>
		<%	
		}
	}	
	%>
	
</ul>
