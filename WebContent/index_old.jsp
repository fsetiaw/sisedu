<!DOCTYPE html>
<head>
	<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<title>The Perfect 3 Column Liquid Layout: No CSS hacks. SEO friendly. iPhone compatible.</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/pageLayout/2col.css" media="screen" />
	<!--  link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/css/html5NavCode/css/nav.css" media="screen" -->
	<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/breakIframe/breakIframe.js"></script>
	<!--  jsp:useBean id="validUsr" class="beans.login.InitSessionUsr" scope="session" / -->
	<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	System.out.println("valid usr ="+validUsr);
	String id_obj = request.getParameter("id_obj");
	String nmm = request.getParameter("nmm");
	String npm = request.getParameter("npm");
	String obj_lvl = request.getParameter("obj_lvl");
	String kdpst = request.getParameter("kdpst");
	//closing session from summary tab
	session.removeAttribute("v_totMhs");
	session.removeAttribute("vSum");
	session.removeAttribute("tknYyMm");
	session.removeAttribute("yTot");
	session.removeAttribute("vSumPsc");
	session.removeAttribute("yTotPsc");
	%>
</head>
<body>

<div id="header">
	<ul>
		<%
		String target = Constants.getRootWeb()+"/InnerFrame/home.jsp";
		//System.out.println("index target="+target);
		String uri = request.getRequestURI();
		//System.out.println("index uri="+uri);
		String url = PathFinder.getPath(uri, target);
		//System.out.println("index url="+url);
		%>
		<li><a href="<%=url %>" target="inner_iframe">HOME <span><b style="color:#369">---</b></span></a></li>
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
	
		target = Constants.getRootWeb()+"/InnerFrame/Summary/view.summary";
		uri = request.getRequestURI();
		System.out.println(target+" / "+uri);
		url = PathFinder.getPath(uri, target);
		if(validUsr.isAllowTo("hasSummaryMenu")>0) {
		%>
		<li><a href="<%=url %>" target="inner_iframe">SUMMARY<span><b style="color:#369">---</b> </span></a></li>
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
		<nav id="topNav">
        	<ul>
          		<li><a href="#" title="Nav Link 1"><b><%=validUsr.getFullname().toUpperCase() %></b></a>
                    <ul>
                    <!-- 
                    	<li><a href="#" title="Sub Nav Link 1">Profile</a></li>
                    -->  
                        <li><a href="<%=Constants.getRootWeb() %>/InnerFrame/Edit/editUsrPwd.jsp" target="inner_iframe" title="edit usr/pwd">ubah usr/password</a></li>
                        <li class="last"><a href="<%=Constants.getRootWeb() %>/Logout/go.logout" title="logout">logout</a></li>
                    </ul>        
                </li>
          </ul>
        </nav>
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
	<aside>
		<section>
		<%
		if(id_obj!=null && nmm!=null && npm!=null&&obj_lvl!=null && kdpst!=null) {
		%>
			<iframe src="get.histPymnt?id_obj=<%= id_obj%>&nmm=<%= nmm%>&npm=<%= npm%>&obj_lvl=<%= obj_lvl%>&kdpst=<%= kdpst%>" seamless="seamless" width="700px" height="800px"  name="inner_iframe"></iframe>
		<%
		}
		else {
		%>
			<iframe src="InnerFrame/home.jsp" seamless="seamless" width="755px" height="785px" name="inner_iframe"></iframe>
		<%
		}
		%>
		</section>
	</aside>
</div>
<footer>
</footer>
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"></script>
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/modernizr.js"></script>
		<script>
			(function($){
				//cache nav
				var nav = $("#topNav");
				
				//add indicator and hovers to submenu parents
				nav.find("li").each(function() {
					if ($(this).find("ul").length > 0) {
						$("<span>").text("").appendTo($(this).children(":first"));

						//show subnav on hover
						$(this).mouseenter(function() {
							$(this).find("ul").stop(true, true).slideDown();
						});
						
						//hide submenus on exit
						$(this).mouseleave(function() {
							$(this).find("ul").stop(true, true).slideUp();
						});
					}
				});
			})(jQuery);
		</script>
</body>
</html>
