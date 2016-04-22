<!DOCTYPE html>
<head>
	<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="java.util.Vector" %>
	<%@ page import="java.util.ListIterator" %>
	<title>The Perfect 2 Column Liquid Layout (left menu): No CSS hacks. SEO friendly. iPhone compatible.</title>
	<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8" />
	<meta name="description" content="" />
	<meta name="keywords" content="" />
	<meta name="robots" content="index, follow" />
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/2colsLayout/screen.css" media="screen" />
	<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/breakIframe/breakIframe.js"></script>
	<script type="text/javascript">
    if (top.location!= self.location) {
        top.location = self.location.href
                   //or you can use your logout page as
                   //top.location='logout.jsp' here...
    }
	</script>
	<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	//System.out.println("valid usr ="+validUsr);
	//System.out.println("id_obj = "+validUsr.getIdObj());
	/*
	 * variable dari hasil pencarian
	*/		
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
	session.removeAttribute("vHistKrsKhsForEdit");
	session.removeAttribute("vTrnlpForEdit");
	session.removeAttribute("vf");
	request.removeAttribute("v_profile");
	request.removeAttribute("v_trnlm");
	request.removeAttribute("v_trnlp");
	session.removeAttribute("vHistTmp");
	request.removeAttribute("vHistoKrs");
	session.removeAttribute("vSummaryPMB");

	
	//khusus atTampleteUjian
	String RealDateTimeStart=request.getParameter("RealDateTimeStart");
	String nmmtest=request.getParameter("nmmtest");
	String keterTest=request.getParameter("keterTest");
	String totalSoal=request.getParameter("totalSoal");
	String totalWaktu=request.getParameter("totalWaktu");
	String passingGrade=request.getParameter("passingGrade");
	
	String idSoal = request.getParameter("id");
	String tknSoal = request.getParameter("tknSoal");
	String atSoal = request.getParameter("atSoal");
	String atChapter = request.getParameter("atChapter");
	String tokenKodeGroupAndListSoal = request.getParameter("tokenKodeGroupAndListSoal");			
	String idJdwlTest = request.getParameter("idJdwlTest");
	String idCivJdwlBridge = request.getParameter("idCivJdwlBridge");
	String idOnlineTest = request.getParameter("idOnlineTest");
	String jawaban = request.getParameter("jawaban");
	String status_cancel_done_inprogress_pause = request.getParameter("status_cancel_done_inprogress_pause");
	%>
	<script language="JavaScript">
	<!--
	function resize_iframe()
	{

		var height=window.innerWidth;//Firefox
		if (document.body.clientHeight)
		{
			height=document.body.clientHeight;//IE
		}
		//resize the iframe according to the size of the
		//window (all these should be on the same line)
		document.getElementById("glu").style.height=parseInt(height-
		document.getElementById("glu").offsetTop-8)+"px";
	}
	// this will resize the iframe every
	// time you change the size of the window.
	window.onresize=resize_iframe; 
	//Instead of using this you can use: 
	//	<BODY onresize="resize_iframe()">
	//-->

	
</script>

</head>
<body>
<div id="header">
	<ul>
		<%
		//String target = Constants.getRootWeb()+"/InnerFrame/home.jsp";
		String target = Constants.getRootWeb()+"/index.jsp";
		////System.out.println("index target="+target);
		String uri = request.getRequestURI();
		////System.out.println("index uri="+uri);
		String url = PathFinder.getPath(uri, target);
		////System.out.println("index url="+url);
		%>
		<li><a href="<%=target %>" target="inner_iframe">HOME <span><b style="color:#eee">---</b></span></a></li>
		<%	
		target = Constants.getRootWeb()+"/InnerFrame/PMB/pmb_index.jsp";
		uri = request.getRequestURI();
		//System.out.println(target+" / "+uri);
		url = PathFinder.getPath(uri, target);
		if(validUsr.isAllowTo("iciv")>0) {
		%>
		<li><a href="<%=url %>" target="inner_iframe">INSERT <span>CIVITAS BARU</span></a></li>
		<%
		}
	
		Vector vTmp = validUsr.getScopeUpd7des2012("hasAkademikMenu");
		if(vTmp!=null && vTmp.size()>0) {
			target = Constants.getRootWeb()+"/InnerFrame/Akademik/indexAkademik.jsp";
			uri = request.getRequestURI();
			//System.out.println(target+" / "+uri);
			url = PathFinder.getPath(uri, target);
			%>
			<li><a href="<%=url %>?callerPage=<%=Constants.getRootWeb()+"/index.jsp" %>" target="inner_iframe">BIDANG<span>AKADEMIK</span></a></li>
			<%
		}	
		
		//target = Constants.getRootWeb()+"/InnerFrame/Summary/view.summary";
		//uri = request.getRequestURI();
		////System.out.println(target+" / "+uri);
		//url = PathFinder.getPath(uri, target);
		if(validUsr.isAllowTo("hasSummaryMenu")>0) {
		%>
		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/dashSummary.jsp" target="inner_iframe">SUMMARY<span><b style="color:#eee">-</b> </span></a></li>
		<!--  li><a href="<%=url %>" target="inner_iframe">SUMMARY<span><b style="color:#eee">---</b> </span></a></li -->
		<%
		}
		
		Vector vDwn = validUsr.getScopeUpd7des2012("hasDownloadMenu");
		if(vDwn!=null && vDwn.size()>0) {
			target = Constants.getRootWeb()+"/InnerFrame/Download/dashDownload.jsp";
			uri = request.getRequestURI();
			////System.out.println(target+" / "+uri);
			url = PathFinder.getPath(uri, target);
			%>
			<li><a href="<%=url %>" target="inner_iframe">DOWNLOAD<span>UPLOAD FILE</span></a></li>
			<%
		}	
		
		Vector vSpm = validUsr.getScopeUpd7des2012("hasSpmiMenu");
		if(vSpm!=null && vSpm.size()>0) {
			%>
			<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/indexSpmi.jsp" target="inner_iframe">PENJAMINAN<span>MUTU</span></a></li>
			<%
		}
		Vector vAna = validUsr.getScopeUpd7des2012("hasStatMenu");
		if(vAna!=null && vAna.size()>0) {
			%>
			<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Analisa/dashStat.jsp" target="inner_iframe">DATA<span>STATISTIK</span></a></li>
			<%
		}
		Vector vTool = validUsr.getScopeUpd7des2012("hasStoredProcedureMenu");
		if(vTool!=null && vTool.size()>0) {
			%>
			<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/StoredProcedure/dashStoredProces.jsp" target="inner_iframe">ALAT KALKULASI<span>DATA</span></a></li>
			<%
		}
		if(validUsr.isAllowTo("hasEtestMenu")>0) {
			%>
			<!--  li><a href="go.cekOnlineTest?backTo=<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/index.jsp" target="inner_iframe">ONLINE<span>TEST</span></a></li -->
			<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/MhsSection/dashboardMhs.jsp" target="inner_iframe">ONLINE<span>TEST</span></a></li>
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
	<p id="layoutdims">Selamat Datang, <%=validUsr.getFullname().toLowerCase() %> | <a href="<%=Constants.getRootWeb() %>/InnerFrame/Edit/editUsrPwd.jsp" target="inner_iframe" title="edit usr/pwd">ubah usr/password</a> | <a href="<%=Constants.getRootWeb() %>/Logout/go.logout" title="logout">logout</a></li>
</div>
<div class="colmask leftmenu">
	<div class="colleft">
		<div class="col1">
			<!-- Main Column 1 start -->
			<%
			if(id_obj!=null && nmm!=null && npm!=null&&obj_lvl!=null && kdpst!=null) {
			%>
				<iframe id="glu" src="get.histPymnt?id_obj=<%= id_obj%>&nmm=<%= nmm%>&npm=<%= npm%>&obj_lvl=<%= obj_lvl%>&kdpst=<%= kdpst%>" seamless="seamless" width="100%" onload="resize_iframe()" name="inner_iframe"></iframe>
			<%
			}
			else {
				target = "/MhsSection/Ujian/tampleteSoalUjian.jsp";
				//String target = Constants.getRootWeb()+"/indexAtTampleteUjian.jsp";
				uri = request.getRequestURI();
				String url_ff = PathFinder.getPath(uri, target);
			%>
				<iframe id="glu" src="<%=url_ff %>?status_cancel_done_inprogress_pause=<%=status_cancel_done_inprogress_pause %>&id=<%=idSoal %>&tknSoal=<%=tknSoal %>&atSoal=<%=atSoal %>&atChapter=<%=atChapter %>&tokenKodeGroupAndListSoal=<%=tokenKodeGroupAndListSoal.replace("#", "$$") %>&idCivJdwlBridge=<%=idCivJdwlBridge %>&idJdwlTest=<%=idJdwlTest %>&idOnlineTest=<%=idOnlineTest %>&jawaban=<%=jawaban %>&RealDateTimeStart=<%=RealDateTimeStart %>" seamless="seamless" width="100%" onload="resize_iframe()" name="inner_iframe"></iframe>
			<%
			}
			%>
			
			<!-- Main Column 1 end -->
		</div>
		<div class="col2">
			<!-- Column 2 start -->
			<b><u>
			NAMA TEST:<br/>
			</u></b>
			<%=keterTest %>
			<br/><br/>
			<b><u>
			TOTAl SOAL:<br/>
			</u></b>
			<%=totalSoal %>
			<br/><br/>
			<b><u>
			TOTAl WAKTU:<br/>
			</u></b>
			<%=totalWaktu %> menit
			<br/><br/>
			<b><u>
			NILAI MINIMAL LULUS:<br/>
			</u></b>
			<%="N/A" %>
			<br/><br/>
			<p style="font-style:italic;font-align:center">
			Selamat Mengerjakan Ujian, Semoga Sukses
			</p>
			<!-- Column 2 end -->
		</div>
	</div>
</div>
<div id="footer">
	<p>This page uses the <a href="http://matthewjamestaylor.com/blog/perfect-2-column-left-menu.htm">Perfect 'Left Menu' 2 Column Liquid Layout</a> by <a href="http://matthewjamestaylor.com">Matthew James Taylor</a>. View more <a href="http://matthewjamestaylor.com/blog/-website-layouts">website layouts</a> and <a href="http://matthewjamestaylor.com/blog/-web-design">web design articles</a>.</p>
</div>
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
