<!DOCTYPE html>
<head>
	<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="java.util.Vector" %>
	<%@ page import="java.util.ListIterator" %>
	<%@ page import="org.codehaus.jettison.json.JSONArray" %>
	<%@ page import="org.codehaus.jettison.json.JSONObject" %>
	<%@ page import="org.codehaus.jettison.json.JSONException" %>
	<%@ page import="org.owasp.esapi.ESAPI" %>
	<%@ page import="javax.jws.soap.SOAPBinding.Style" %>
	<%@ page import="javax.xml.soap.SOAPConnection.*" %>
	

	<title>UNIVERSITAS SATYAGAMA</title>
	<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8" />
	<meta name="description" content="" />
	<meta name="keywords" content="" />
	<meta name="robots" content="index, follow" />
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/2colsLayout/screen.css" media="screen" />
	<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/breakIframe/breakIframe.js"></script>
	  
  	
  	<link rel="icon" href="/favicon.ico">
  	<link rel="stylesheet" type="text/css" media="all" href="<%=Constants.getRootWeb()%>/expSearchfield/demo/styles.css">
	
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

	/*
	LIST SESSION YG JGN Di REMOVE
	1.ObjGenderAndNickname
	*/
	session.removeAttribute("vHisBea");
	session.removeAttribute("vListNamaPaketBea");
	session.removeAttribute("vMhsUnHeregContainment");
	session.removeAttribute("kodeKampus");
	session.removeAttribute("targetAkun");
	session.removeAttribute("sumberDana");
	session.removeAttribute("dataForRouterAfterUpload");
	session.removeAttribute("target_kelas_info");
	session.removeAttribute("vMhsContainer");
	session.removeAttribute("nmpw");
	session.removeAttribute("forceBackTo");
	session.removeAttribute("tknKrsNotificationsForSender");
	session.removeAttribute("tknKrsNotifications");
	session.removeAttribute("v_totMhs");
	session.removeAttribute("vSum");
	session.removeAttribute("tknYyMm");
	session.removeAttribute("yTot");
	session.removeAttribute("vSumPsc");
	session.removeAttribute("yTotPsc");
	session.removeAttribute("vHistKrsKhsForEdit");
	session.removeAttribute("vTrnlpForEdit");
	session.removeAttribute("vf");
	session.removeAttribute("vListDsn");
	session.removeAttribute("vJsoa");
	session.removeAttribute("kelasTambahan");
	session.removeAttribute("v_nm_alm_access");
	session.removeAttribute("hakAksesUsrUtkFolderIni");
	session.removeAttribute("saveToFolder");
	request.removeAttribute("v_profile");
	request.removeAttribute("v_bak");
	request.removeAttribute("v_NuBak");
	session.removeAttribute("vListKelasPerProdi");
	request.removeAttribute("v_trnlm");
	request.removeAttribute("v_trnlp");
	session.removeAttribute("vHistTmp");
	request.removeAttribute("vHistoKrs");
	session.removeAttribute("vSummaryPMB");
	session.removeAttribute("vListMakul");
	session.removeAttribute("klsInfo");
	session.removeAttribute("totKls");
	session.removeAttribute("vBukaKelas");
	session.removeAttribute("brs");
	session.removeAttribute("tmp");
	session.removeAttribute("lockedMsg");
	session.removeAttribute("vScopeBukaKelas");
	session.removeAttribute("targetObjNickName");
	session.removeAttribute("vSubTopik");
	session.removeAttribute("fieldAndValue");
	session.removeAttribute("validatedTransDate");
	session.removeAttribute("job");
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
		String target = Constants.getRootWeb()+"/InnerFrame/home.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		%>
		<li>
		<a href="get.notifications" target="inner_iframe">HOME <span><b style="color:#eee">---</b></span></a>
		<!--  a href="<%=url %>" target="inner_iframe">HOME <span><b style="color:#eee">---</b></span></a-->
		</li>
		<%	
		/*
		pinah ke CIVITAS BARU
		target = Constants.getRootWeb()+"/InnerFrame/PMB/pmb_index.jsp";
		uri = request.getRequestURI();
		//System.out.println(target+" / "+uri);
		url = PathFinder.getPath(uri, target);
		if(validUsr.isAllowTo("iciv")>0) {
		%>
		<li><a href="<%=url %>" target="inner_iframe">INSERT <span>CIVITAS BARU</span></a></li>
		<%
		}
		*/
		//CIVITAS BARU
		if(validUsr.isAllowTo("iciv")>0) {
			target = Constants.getRootWeb()+"/InnerFrame/PMB/dash_pmb.jsp";
			uri = request.getRequestURI();
			//System.out.println(target+" / "+uri);
			url = PathFinder.getPath(uri, target);
		%>
		<li><a href="<%=url %>" target="inner_iframe">PENERIMAAN <span>CIVITAS BARU</span></a></li>
		<%
		}
		
		//heregistrasi
		if(validUsr.isAllowTo("hasHeregitrasiMenu")>0) {
			%>
			<li><a href="get.whoRegister" target="inner_iframe">HEREGISTRASI <span>DAFTAR ULANG</span></a></li>
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
		//==========bidang akademik untuk mahasiswa===================
		String usrNickNm = validUsr.getObjNickNameGivenObjId();
		if(usrNickNm.startsWith("MHS")) {
			target = Constants.getRootWeb()+"/InnerFrame/Akademik/indexAkademikMhs.jsp";
			uri = request.getRequestURI();
			//System.out.println(target+" / "+uri);
			url = PathFinder.getPath(uri, target);
			%>
			<li><a href="<%=url %>?callerPage=<%=Constants.getRootWeb()+"/index.jsp" %>" target="inner_iframe">BIDANG<span>AKADEMIK</span></a></li>
			<%
		}
		vTmp = validUsr.getScopeUpd7des2012("hasMenuBagKeu");
		if(vTmp!=null && vTmp.size()>0) {
			target = Constants.getRootWeb()+"/InnerFrame/Keu/indexKeuangan.jsp";
			uri = request.getRequestURI();
			//System.out.println(target+" / "+uri);
			url = PathFinder.getPath(uri, target);
			%>
			<li><a href="<%=url %>?callerPage=<%=Constants.getRootWeb()+"/index.jsp" %>" target="inner_iframe">BIDANG<span>KEUANGAN</span></a></li>
			<%
		}	
		if(validUsr.isAllowTo("hasPerkuliahanMenu")>0) {
			target = Constants.getRootWeb()+"/InnerFrame/Prakuliah/DashPrakuliah.jsp";
			uri = request.getRequestURI();
			//System.out.println(target+" / "+uri);
			url = PathFinder.getPath(uri, target);
			%>
			<li><a href="<%=url %>" target="inner_iframe">KEGIATAN<span>PRA-KULIAH</span></a></li>
			<%
		}
				
		if(validUsr.isAllowTo("hasPerkuliahanMenu")>0) {
			target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/DashPerkuliahan.jsp";
			uri = request.getRequestURI();
			//System.out.println(target+" / "+uri);
			url = PathFinder.getPath(uri, target);
			%>
			<li><a href="<%=url %>" target="inner_iframe">KEGIATAN<span>PERKULIAHAN</span></a></li>
			<%
		}		
		//target = Constants.getRootWeb()+"/InnerFrame/Summary/view.summary";
		//uri = request.getRequestURI();
		//System.out.println(target+" / "+uri);
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
			//System.out.println(target+" / "+uri);
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
		//penempatan sementara dan hanya untuk operator kalo mhs ada dibidang akademik mhs
		if(validUsr.isAllowTo("hasEtestMenu")>0 && validUsr.getObjNickNameGivenObjId().contains("OPERATOR")) {
			%>
			<!--  li><a href="go.cekOnlineTest?backTo=<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/index.jsp" target="inner_iframe">ONLINE<span>TEST</span></a></li -->
			<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/MhsSection/dashboardMhs.jsp" target="inner_iframe">ONLINE<span>TEST</span></a></li>
			<%
		}
		
		if(validUsr.isAllowTo("hasDataMhsMenu")>0) {
			String myNpm = validUsr.getNpm();
			%>
			<!--  li><a href="go.cekOnlineTest?backTo=<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/index.jsp" target="inner_iframe">ONLINE<span>TEST</span></a></li -->
			<li><a href="get.profile?npm=<%=validUsr.getNpm() %>&cmd=dashboard&nim=<%=validUsr.getNimhs(myNpm)%>&stmhs=<%=validUsr.getStmhs(myNpm)%>&kdpst=<%=validUsr.getKdpst()%>&id_obj=<%=validUsr.getIdObj() %>&obj_lvl=<%=validUsr.getObjLevel() %>&nmm=<%=validUsr.getNmmhs(myNpm) %>" target="inner_iframe">DATA<span>MAHASISWA</span></a></li>
			<%
		}
		
		if(validUsr.isAllowTo("hasContactUsMenu")>0) {
			//String myNpm = validUsr.getNpm();
			%>
			
			<li>
				<!--  a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/ContactUs/dashContactUs.jsp" target="inner_iframe">KOTAK<span>PESAN</span></a -->
				<a href="get.msgInbox?sta_index=0&range=<%=Constants.getRangeMgsInbox() %>&show=unread" target="inner_iframe">INBOX &<span>KIRIM PESAN</span></a>
			</li>
			<%
		}
		
		if(validUsr.isAllowTo("settingParam")>0) {
			//String myNpm = validUsr.getNpm();
			%>
			
			<li>
				<a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Parameter/dashParameter.jsp" target="inner_iframe">PENGATURAN<span>PARAMETER</span></a>
			</li>
			<%
		}	

		if(validUsr.isAllowTo("hasArchiveMenu")>0) {
		%>
			<li>
				<a href="get.fileAndFolder" target="inner_iframe">ARSIP<span>USG</span></a>
			</li>
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
				<iframe id="glu" src="get.histPymnt?id_obj=<%= id_obj%>&nmm=<%= nmm%>&npm=<%= npm%>&obj_lvl=<%= obj_lvl%>&kdpst=<%= kdpst%>" seamless="seamless" width="100%" onload="scroll(0,0);resize_iframe()" name="inner_iframe"></iframe>
			<%
			}
			else {
			%>
				<!--  iframe id="glu" src="InnerFrame/home.jsp" seamless="seamless" width="100%" onload="resize_iframe()" name="inner_iframe"></iframe -->
				<iframe id="glu" src="get.notifications" seamless="seamless" width="100%" onload="scroll(0,0);resize_iframe()" name="inner_iframe"></iframe>
			<%
			}
			%>
			
			<!-- Main Column 1 end -->
		</div>
		<div class="col2">
			<!-- Column 2 start -->
			
				<%
				String ls_result = "";
				  
				Call call = new Call ();
				  call.setTargetObjectURI("urn:xmlbook.chapter3");
				  call.setMethodName ("testService");
				  call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);

				  URL url = new URL ("http://localhost:8080/soap/servlet/rpcrouter");

				  Response resp = call.invoke (url, "");
					if(validUsr.isAllowTo("s")>0) {
				%>
				<p>
				<form target="_blank" action='http://www.webservicex.net/stockquote.asmx/GetQuote' method="POST">                      
                        
                          <table cellspacing="0" cellpadding="4" frame="box" bordercolor="#dcdcdc" rules="none" style="border-collapse: collapse;">
                          <tr>
	<td class="frmHeader" background="#dcdcdc" style="border-right: 2px solid white;">Parameter</td>
	<td class="frmHeader" background="#dcdcdc">Value</td>
</tr>

                        
                          <tr>
                            <td class="frmText" style="color: #000000; font-weight: normal;">symbol:</td>
                            <td><input class="frmInput" type="text" size="50" name="symbol"></td>
                          </tr>
                        
                        <tr>
                          <td></td>
                          <td align="right"> <input type="submit" value="Invoke" class="button"></td>
                        </tr>
                        </table>
                      

                    </form>
				
				</p>
				<p>
				<form id="searchform" name="searchform" method="get" action="people.search" target="inner_iframe">
      				<div class="fieldcontainer">
        			<input type="text" name="kword" id="s" class="searchfield" placeholder="Keywords..." tabindex="1">
        			<input type="submit" name="searchbtn" id="searchbtn" value=""> 
      				</div><!-- @end .fieldcontainer -->
    			</form>
				</p>
				<%
				}
				%>
				<p>
				<!--  iframe id="glu" src="LeftFrame/index.html" seamless="seamless" width="100%" onload="resize_iframe()" name="left_inner_iframe" scrolling="no"></iframe -->
				<!--  iframe id="glu" src="LeftInnerFrame/home.jsp" seamless="seamless" width="100%" onload="resize_iframe()" name="left_inner_iframe" scrolling="no" height="1000px"></iframe -->
				</p>
				<%
				
				String visi = "";
				JSONArray jsoaVisi = Getter.readJsonArrayFromUrl("/v1/search_prodi/visi/"+validUsr.getKdpst());
				//System.out.println("jsoaVisi="+jsoaVisi.length());
				if(jsoaVisi!=null && jsoaVisi.length()>0) {
					JSONObject jobVisi = jsoaVisi.getJSONObject(0);
					try {
						visi = jobVisi.getString("VISI");	
				%>
				<p style="text-align:center;color:#1E3F60;font-style:italic;font-size:0.9em;width:200px">Visi:</p>
				<p style="text-align:center;color:#1E3F60;font-style:italic;font-size:0.7em;width:200px"><%=visi %></p>
				<% 		
					}
					catch(JSONException je) {}
					
				}
				String misi = "";
				JSONArray jsoaMisi = Getter.readJsonArrayFromUrl("/v1/search_prodi/misi/"+validUsr.getKdpst());
				if(jsoaMisi!=null && jsoaMisi.length()>0) {
					JSONObject jobMisi = jsoaMisi.getJSONObject(0);
					try {
						misi = jobMisi.getString("MISI");	
				%>
				<br/>
				<p style="text-align:center;color:#1E3F60;font-style:italic;font-size:0.9em;width:200px">Misi:</p>
				<p style="text-align:center;color:#1E3F60;font-style:italic;font-size:0.7em;width:200px"><%=misi %></p>
				<% 		
					}
					catch(JSONException je) {}
					
				}
				
				%>

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
