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
	if(v_cf!=null && v_cf.size()>0) {	
	%>
	<h2 style="color:BLACK;text-align:left">KEBIJAKAN PENJAMINAN MUTU & MANUAL PADA TIAP SIKLUS STANDAR</h2>
<ul>	
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/indexSpmi.jsp" target="inner_iframe">SKEMA UTAMA<span>PENJAMINAN MUTU</span></a></li>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Kebijakan/kebijakanSpmi.jsp" target="inner_iframe">KEBIJAKAN<span>SPMI</span></a></li>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/BookOfStandar/indexBos.jsp" target="inner_iframe">BUKU<span>STANDAR</span></a></li>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Penetapan/penetapanSpmi.jsp" target="inner_iframe">MANUAL<span>PENETAPAN STD</span></a></li>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Pelaksanaan/pelaksanaanSpmi.jsp" target="inner_iframe">MANUAL<span>PELAKSANAAN STD</span></a></li>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Kontrol/controlSpmi.jsp" target="inner_iframe">MANUAL<span>PENGENDALIAN STD</span></a></li>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Evaluasi/peningkatanSpmi.jsp" target="inner_iframe">MANUAL<span>PENINGKATAN STD</span></a></li>
	
</ul>
	<%
	
	}
	%>


</div>