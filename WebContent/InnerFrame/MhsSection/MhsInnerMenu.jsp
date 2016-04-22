<%
String atMenu = request.getParameter("atMenu");
Vector vtmp = null;
%>
<ul>

<%
String target = Constants.getRootWeb()+"/InnerFrame/home.jsp";
String uri = request.getRequestURI();
String url = PathFinder.getPath(uri, target);
 %>		
	<!--  li><a href="<%=url %>" target="_self" >GO <span>BACK</span></a></li -->
	<li><a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">GO <span>BACK</span></a></li>
<%
if(atMenu!=null && atMenu.equalsIgnoreCase("listTest")) {
%>
	<li><a href="go.cekOnlineTest?atMenu=listTest" target="_self" class="active">LIST TEST<span>ONLINE</span></a></li>
<%
}
else {
%>
	<li><a href="go.cekOnlineTest?atMenu=listTest" target="_self">LIST TEST<span>ONLINE</span></a></li>
<%	
}

target = Constants.getRootWeb()+"/InnerFrame/Ujian/dashboard.jsp";
uri = request.getRequestURI();
url = PathFinder.getPath(uri, target);
vtmp = validUsr.getScopeUpd7des2012("fullAksesJadwalOnlineTest"); 
if(vtmp==null) {
	vtmp = validUsr.getScopeUpd7des2012("editOnlyJadwalOnlineTest"); 
}
if(vtmp!=null && vtmp.size()>0) {
	//go.getAccsessibleOnlineTest
	if(atMenu!=null && atMenu.equalsIgnoreCase("pengaturanJadwal")) {
%>
	<li><a href="<%=url %>?atMenu=pengaturanJadwal" target="_self" class="active">EDIT JADWAL<span>TEST ONLINE</span></a></li>
<%
	}
	else {
	%>
	<li><a href="<%=url %>?atMenu=pengaturanJadwal" target="_self">EDIT JADWAL<span>TEST ONLINE</span></a></li>
	<%		
	}
}
%>	
</ul>

