<%
String atMenu = request.getParameter("atMenu");

%>
<ul>
	<%

	
	if(validUsr.isAllowTo("hasAkademikPengajuanMenu")>0 ) {
		String target = Constants.getRootWeb()+"/InnerFrame/Akademik/dashPengajuan.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		%>		
	<li><a href="<%=url %>" target="_self">PENGAJUAN RANCANGAN<span>KELAS PERKULIAHAN</b></span></a></li>
		<%
	}
	
	if(validUsr.isAllowTo("pba")>0 ) {
		//String target = Constants.getRootWeb()+"/InnerFrame/Akademik/dashPengajuan.jsp";
		//String uri = request.getRequestURI();
		//String url = PathFinder.getPath(uri, target);
		if(atMenu!=null && atMenu.equalsIgnoreCase("pba")) {
		%>		
	<li><a href="get.kelasYgDiajar?atMenu=pba" target="_self" class="active">PENGAJUAN RANCANGAN<span>BAHAN AJAR</b></span></a></li>
		<%		
		}
		else {
		%>		
	<li><a href="get.kelasYgDiajar?atMenu=pba" target="_self">PENGAJUAN RANCANGAN<span>BAHAN AJAR</b></span></a></li>
		<%
		}
	}
	%>
</ul>

