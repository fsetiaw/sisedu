<%
String atMenu = request.getParameter("atMenu");
String target = "";
String uri = "";
//System.out.println(target+" / "+uri);
String url = "";
%>
<ul>
	<%
	if(false) {
	%>
		<li><a href="get.notifications" target="_self" class="active">BACK <span><b style="color:#eee">---</b></span></a></li>
	<%
	}
	else {
	%>
		<li><a href="get.notifications" target="_self">BACK <span><b style="color:#eee">---</b></span></a></li>
	<%
	}
	if(validUsr.isAllowTo("iciv")>0) {
		if(atMenu!=null && atMenu.equalsIgnoreCase("insCiv")) {
			//target = Constants.getRootWeb()+"/InnerFrame/PMB/pmb_index.jsp";
			//uri = request.getRequestURI();
			//System.out.println(target+" / "+uri);
			//url = PathFinder.getPath(uri, target);
			%>
			<li><a href="pmb_index.jsp?atMenu=insCiv" target="_self" class="active">INSERT<span>CIVITAS BARU</span></a></li>
			<%	
		}
		else {
			%>
			<li><a href="pmb_index.jsp?atMenu=insCiv" target="_self">INSERT<span>CIVITAS BARU</span></a></li>
			<%	
		}
		
	}
	
	%>
</ul>

