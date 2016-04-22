<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.*" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.login.*" %>
<%@ page import="beans.tmp.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>


<ul>
<h1>KEBIJAKAN & MANUAL STANDAR</h1>
	<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	Vector v_cf = validUsr.getScopeUpd7des2012("hasSpmiMenu");
	if(v_cf!=null && v_cf.size()>0) {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Kebijakan/kebijakanSpmi.jsp" target="inner_iframe">KEBIJAKAN<span>SPMI</span></a></li>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Penetapan/penetapanSpmi.jsp" target="inner_iframe">MANUAL<span>PENETAPAN STD</span></a></li>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Pelaksanaan/pelaksanaanSpmi.jsp" target="inner_iframe">MANUAL<span>PELAKSANAAN STD</span></a></li>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Kontrol/controlSpmi.jsp" target="inner_iframe">MANUAL<span>PENGENDALIAN STD</span></a></li>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Evaluasi/peningkatanSpmi.jsp" target="inner_iframe">MANUAL<span>PENINGKATAN STD</span></a></li>
	<%
	}
	%>
</ul>

<br/><br/><br/><br/><br/><br/>

<div style="background-color:#369">
<h2 style="color:white;text-align:center">LIST STANDAR</h2>
</div>

<ul>
	<h3 align="left">1. STANDAR ISI</h3>
	<%
//	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	v_cf = validUsr.getScopeUpd7des2012("hasSpmiMenu");
	if(v_cf!=null && v_cf.size()>0) {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Kurikulum/stdIsi.jsp" target="inner_iframe">ISI<span>KURIKULUM</span></a></li>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Kurikulum/stdEvaluasi.jsp" target="inner_iframe">PENINGKATAN<span>KURIKULUM</span></a></li>
	
	<%
	}
	%>
</ul>
<br/><br/><br/>
<ul>
	<h3 align="left">2. STANDAR PROSES</h3>
	<%
//	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	v_cf = validUsr.getScopeUpd7des2012("hasSpmiMenu");
	if(v_cf!=null && v_cf.size()>0) {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Proses/StandarAkademikDanKompetensiDosen/stdAkaDanKomDosesn.jsp" target="inner_iframe">STD AKADEMIK DAN<span>KOMPETENSI DOSEN</span></a></li>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Proses/standarPembimbingAkademik/stdBimbingAkademik.jsp" target="inner_iframe">STD AKADEMIK DAN<span>KOMPETENSI DOSEN</span></a></li>
	
	<%
	}
	%>
</ul>
<br/><br/><br/><br/><br/><br/><br/><br/><br/>
<div style="background-color:#369">
<h2 style="color:white;text-align:center">LIST MANUAL</h2>
</div>
<ul>
	<%
//	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	v_cf = validUsr.getScopeUpd7des2012("hasSpmiMenu");
	if(v_cf!=null && v_cf.size()>0) {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Kurikulum/manEvaluasi.jsp" target="inner_iframe">MANUAL EVALUASI<span>KURIKULUM</span></a></li>
	<%
	}
	%>
</ul>
<br/><br/><br/><br/><br/><br/><br/><br/>
<div style="background-color:#369">
<h2 style="color:white;text-align:center">DOWNLOAD DOKUMEN</h2>
</div>
<br/>
<a href="file.DownloadForm?spmi_form=<%=getServletContext().getRealPath("/")%><%=Constants.getSpmiFormGbpp() %>"  target="_self">FORM GBPP</a>

