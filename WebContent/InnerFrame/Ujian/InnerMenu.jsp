<%
String atMenu = request.getParameter("atMenu");
Vector vtmp = null;
%>
<ul>	
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/MhsSection/dashboardMhs.jsp" target="_self" >GO <span>BACK</span></a></li>
<%
vtmp = validUsr.getScopeUpd7des2012("fullAksesJadwalOnlineTest"); 
if(vtmp!=null && vtmp.size()>0) {
	if(atMenu!=null && atMenu.equalsIgnoreCase("tambahHapus")) {
%>
	<li><a href="go.getAccsessibleOnlineTest?atMenu=tambahHapus" target="_self" class="active">TAMBAH / HAPUS<span>JADWAL ONLINE</span></a></li>
<%
	}//
	else {
%>
	<li><a href="go.getAccsessibleOnlineTest?atMenu=tambahHapus" target="_self">TAMBAH / HAPUS<span>JADWAL ONLINE</span></a></li>
<%	
	}
}	

vtmp = validUsr.getScopeUpd7des2012("fullAksesJadwalOnlineTest"); 
if(vtmp==null) {
	vtmp = validUsr.getScopeUpd7des2012("editOnlyJadwalOnlineTest"); 
}
if(vtmp!=null && vtmp.size()>0) {
	if(atMenu!=null && atMenu.equalsIgnoreCase("edit")) {
%>
	<li><a href="go.getAccsessibleOnlineTest?atMenu=edit" target="_self" class="active">EDIT JADWAL<span>TEST ONLINE</span></a></li>
<%
	}
	else {
	%>
	<li><a href="go.getAccsessibleOnlineTest?atMenu=edit" target="_self">EDIT JADWAL<span>TEST ONLINE</span></a></li>
	<%		
	}
}%>	
</ul>

