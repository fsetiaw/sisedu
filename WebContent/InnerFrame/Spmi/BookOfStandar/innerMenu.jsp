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
	String atMenu = request.getParameter("atMenu");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	Vector v_cf = validUsr.getScopeUpd7des2012("hasSpmiMenu");
	if(v_cf!=null && v_cf.size()>0) {	
		
	%>
	<!--  h2 style="color:BLACK;text-align:left">KEBIJAKAN PENJAMINAN MUTU & MANUAL PADA TIAP SIKLUS STANDAR</h2 -->
	
<ul>	
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/indexSpmi.jsp" target="inner_iframe">BACK<span>&nbsp</span></a></li>
	<%
	if(atMenu!=null && atMenu.equalsIgnoreCase("std1")) {
		%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/BookOfStandar/1/index.jsp?atMenu=std1" target="inner_iframe" class="active">STANDAR<span>1</span></a></li>
		<%
	}
	else {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/BookOfStandar/1/index.jsp?atMenu=std1" target="inner_iframe">STANDAR<span>1</span></a></li>
	<%
	}
	
	if(atMenu!=null && atMenu.equalsIgnoreCase("std2")) {
		%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/BookOfStandar/2/index.jsp?atMenu=std2" target="inner_iframe" class="active">STANDAR<span>2</span></a></li>
		<%
	}
	else {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/BookOfStandar/2/index.jsp?atMenu=std2" target="inner_iframe">STANDAR<span>2</span></a></li>
	<%
	}
	
	if(atMenu!=null && atMenu.equalsIgnoreCase("std3")) {
		%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/BookOfStandar/3/index.jsp?atMenu=std3" target="inner_iframe" class="active">STANDAR<span>3</span></a></li>
		<%
	}
	else {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/BookOfStandar/3/index.jsp?atMenu=std3" target="inner_iframe">STANDAR<span>3</span></a></li>
	<%
	}
	
	if(atMenu!=null && atMenu.equalsIgnoreCase("std4")) {
		%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/BookOfStandar/4/index.jsp?atMenu=std4" target="inner_iframe" class="active">STANDAR<span>4</span></a></li>
		<%
	}
	else {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/BookOfStandar/4/index.jsp?atMenu=std4" target="inner_iframe">STANDAR<span>4</span></a></li>
	<%
	}
	if(atMenu!=null && atMenu.equalsIgnoreCase("std5")) {
		%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/BookOfStandar/5/index.jsp?atMenu=std5" target="inner_iframe" class="active">STANDAR<span>5</span></a></li>
		<%
	}
	else {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/BookOfStandar/5/index.jsp?atMenu=std5" target="inner_iframe">STANDAR<span>5</span></a></li>
	<%
	}
	if(atMenu!=null && atMenu.equalsIgnoreCase("std6")) {
		%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/BookOfStandar/6/index.jsp?atMenu=std6" target="inner_iframe" class="active">STANDAR<span>6</span></a></li>
		<%
	}
	else {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/BookOfStandar/6/index.jsp?atMenu=std6" target="inner_iframe">STANDAR<span>6</span></a></li>
	<%
	}
	if(atMenu!=null && atMenu.equalsIgnoreCase("std7")) {
		%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/BookOfStandar/7/index.jsp?atMenu=std7" target="inner_iframe" class="active">STANDAR<span>7</span></a></li>
		<%
	}
	else {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/BookOfStandar/7/index.jsp?atMenu=std7" target="inner_iframe">STANDAR<span>7</span></a></li>
	<%
	}
	if(atMenu!=null && atMenu.equalsIgnoreCase("std8")) {
		%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/BookOfStandar/8/index.jsp?atMenu=std8" target="inner_iframe" class="active">STANDAR<span>8</span></a></li>
		<%
	}
	else {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/BookOfStandar/8/index.jsp?atMenu=std8" target="inner_iframe">STANDAR<span>8</span></a></li>
	<%
	}
	if(atMenu!=null && atMenu.equalsIgnoreCase("std9")) {
		%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/BookOfStandar/9/index.jsp?atMenu=std9" target="inner_iframe" class="active">STANDAR<span>9</span></a></li>
		<%
	}
	else {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/BookOfStandar/9/index.jsp?atMenu=std9" target="inner_iframe">STANDAR<span>9</span></a></li>
	<%
	}
	if(atMenu!=null && atMenu.equalsIgnoreCase("std10")) {
		%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/BookOfStandar/10/index.jsp?atMenu=std10" target="inner_iframe" class="active">STANDAR<span>10</span></a></li>
		<%
	}
	else {
	%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/BookOfStandar/10/index.jsp?atMenu=std10" target="inner_iframe">STANDAR<span>10</span></a></li>
	<%
	}
	%>
	
</ul>
	<%
	}
	%>


</div>