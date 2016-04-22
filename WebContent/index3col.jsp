<!DOCTYPE html>
<head>
	<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<title>The Perfect 3 Column Liquid Layout: No CSS hacks. SEO friendly. iPhone compatible.</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/pageLayout/3col.css" media="screen" />
	
	<!--  jsp:useBean id="validUsr" class="beans.login.InitSessionUsr" scope="session" / -->
	<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String id_obj = request.getParameter("id_obj");
	String nmm = request.getParameter("nmm");
	String npm = request.getParameter("npm");
	String obj_lvl = request.getParameter("obj_lvl");
	String kdpst = request.getParameter("kdpst");
	%>
</head>
<body>

<div id="header">
<h3>header</h3>

	<ul>
		<%
		String target = Constants.getRootWeb()+"/InnerFrame/home.jsp";
		System.out.println("index target="+target);
		String uri = request.getRequestURI();
		System.out.println("index uri="+uri);
		String url = PathFinder.getPath(uri, target);
		System.out.println("index url="+url);
		%>
		<li><a href="<%=url %>" target="inner_iframe">HOME <span>------</span></a></li>
		<%	
		target = Constants.getRootWeb()+"/InnerFrame/PMB/pmb_index.jsp";
		uri = request.getRequestURI();
		System.out.println(target+" / "+uri);
		url = PathFinder.getPath(uri, target);
		if(validUsr.isAllowTo("iciv")>0) {
		%>
		<li><a href="<%=url %>" target="inner_iframe">INSERT <span>CIVITAS BARU</span></a></li>
		<%
		}
		%>
<!-- 
		<li><a href="http://matthewjamestaylor.com/blog/perfect-2-column-left-menu.htm">2 Column <span>Left Menu</span></a></li>
		<li><a href="http://matthewjamestaylor.com/blog/perfect-2-column-right-menu.htm">2 Column <span>Right Menu</span></a></li>
		<li><a href="http://matthewjamestaylor.com/blog/perfect-2-column-double-page.htm">2 Column <span>Double Page</span></a></li>
		<li><a href="http://matthewjamestaylor.com/blog/perfect-full-page.htm">1 Column <span>Full Page</span></a></li>
		<li><a href="http://matthewjamestaylor.com/blog/perfect-stacked-columns.htm">Stacked <span>columns</span></a></li>
 -->
	</ul>
</div>
<div id="content">
	<div id="leftContent">
		<section>
		<p>
		Login User,<br/>
		<b><%=validUsr.getFullname().toUpperCase() %></b>
		</p>
		<%
		if(validUsr.isAllowTo("s")>0) {
		%>
		<p>
			<form action="people.search" target="inner_iframe">
				<table>
					<tr>
						<td>Keyword</td>
					</tr>
					<tr>	
						<td><input type="text" name="kword"/> </td>
					</tr>
					<tr>
						<td><input type="submit" /> </td>
					</tr>
				</table>
			</form>
		</p>
		<%
		}
		%>
		</section>
	</div>
	<div id="mainContent">
		<section>
		<%
		if(id_obj!=null && nmm!=null && npm!=null&&obj_lvl!=null && kdpst!=null) {
		%>
			<iframe src="get.histPymnt?id_obj=<%= id_obj%>&nmm=<%= nmm%>&npm=<%= npm%>&obj_lvl=<%= obj_lvl%>&kdpst=<%= kdpst%>" seamless="seamless" width="530px" height="600px"  name="inner_iframe"></iframe>
		<%
		}
		else {
		%>
			<iframe src="InnerFrame/home.jsp" seamless="seamless" width="530px" height="600px" name="inner_iframe"></iframe>
		<%
		}
		%>
		</section>
	</div>
	<aside>
		<section>
			<ul>
				<li><a href="#">Lorem ipsum dolor</a></li>
				<li><a href="#">Sit amet consectetur</a></li>
				<li><a href="#">Adipisicing elit sed</a></li>
				<li><a href="#">Do eiusmod tempor</a></li>
				<li><a href="#">Incididunt ut labore</a></li>
			</ul>
		</section>
	</aside>
</div>
<footer>
</footer>
</body>
</html>
