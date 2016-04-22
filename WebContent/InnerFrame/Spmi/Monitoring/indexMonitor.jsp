<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
<head>
	<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="java.util.Vector" %>
	<%@ page import="java.util.ListIterator" %>
	<%@ page import="org.codehaus.jettison.json.JSONArray" %>
	<%@ page import="org.codehaus.jettison.json.JSONObject" %>
	<%@ page import="org.codehaus.jettison.json.JSONException" %>
	<%@ page import="org.owasp.esapi.ESAPI" %>
	<%@ page import="org.codehaus.jettison.json.JSONArray" %>
	<%@ page import="java.util.StringTokenizer" %>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%	
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String atMenu = request.getParameter("atMenu");
%>
   <meta charset="utf-8" />
   <!-- Always force latest IE rendering engine (even in intranet) & Chrome Frame -->
   <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
   <!-- Mobile viewport optimized: h5bp.com/viewport -->
   <meta name="viewport" content="width=device-width">

   

   <meta name="robots" content="noindex, nofollow">
   <meta name="description" content="BootMetro : Simple and complete web UI framework to create web apps with Windows 8 Metro user interface." />
   <meta name="keywords" content="bootmetro, modern ui, modern-ui, metro, metroui, metro-ui, metro ui, windows 8, metro style, bootstrap, framework, web framework, css, html" />
   <meta name="author" content="AozoraLabs by Marcello Palmitessa"/>
   <link rel="publisher" href="https://plus.google.com/117689250782136016574">

   <!-- remove or comment this line if you want to use the local fonts -->
   <!--  link href='http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700' rel='stylesheet' type='text/css' -->

   <link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/bootmetro/assets/css/bootmetro.css">
   <link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/bootmetro/assets/css/bootmetro-responsive.css">
   <link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/bootmetro/assets/css/bootmetro-icons.css">
   <link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/bootmetro/assets/css/bootmetro-ui-light.css">
   <link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/bootmetro/assets/css/datepicker.css">

   <!--  these two css are to use only for documentation -->
   <link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/bootmetro/assets/css/demo.css">

   <!-- Le fav and touch icons -->
   <link rel="shortcut icon" href="<%=Constants.getRootWeb() %>/bootmetro/assets/ico/favicon.ico">
   <link rel="apple-touch-icon-precomposed" sizes="144x144" href="<%=Constants.getRootWeb() %>/bootmetro/assets/ico/apple-touch-icon-144-precomposed.png">
   <link rel="apple-touch-icon-precomposed" sizes="114x114" href="<%=Constants.getRootWeb() %>/bootmetro/assets/ico/apple-touch-icon-114-precomposed.png">
   <link rel="apple-touch-icon-precomposed" sizes="72x72" href="<%=Constants.getRootWeb() %>/bootmetro/assets/ico/apple-touch-icon-72-precomposed.png">
   <link rel="apple-touch-icon-precomposed" href="<%=Constants.getRootWeb() %>/bootmetro/assets/ico/apple-touch-icon-57-precomposed.png">
  
   <!-- All JavaScript at the bottom, except for Modernizr and Respond.
      Modernizr enables HTML5 elements & feature detects; Respond is a polyfill for min/max-width CSS3 Media Queries
      For optimal performance, use a custom Modernizr build: www.modernizr.com/download/ -->
   <script src="<%=Constants.getRootWeb() %>/bootmetro/assets/js/modernizr-2.6.2.min.js"></script>

   <script type="text/javascript">
      var _gaq = _gaq || [];
      _gaq.push(['_setAccount', 'UA-3182578-6']);
      _gaq.push(['_trackPageview']);
      (function() {
         var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
         ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
         var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
      })();
   </script>
</head>

<body>
   <!--[if lt IE 7]>
   <p class="chromeframe">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> or <a href="http://www.google.com/chromeframe/?redirect=true">activate Google Chrome Frame</a> to improve your experience.</p>
   <![endif]-->
<div id="header">
	<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/subTopMenu.jsp" >
	<jsp:param name="atMenu" value="<%=atMenu %>"/>

	</jsp:include>
</div>
   	
   
      
   
      <!--<div id="metro-container" class="-container">-->
         <!--<div class="row">-->
            <!--<div id="hub" class="metro">-->
	<div class="metro panorama">
    	<div class="panorama-sections">
   
<%




//if(newMsgOnMonitoredInbox!=null && newMsgOnMonitoredInbox.equalsIgnoreCase("true")) {
%>
<%
//}

//if(validUsr.isAllowTo("pymntApprovee")>0 && vReqAprKeu!=null && vReqAprKeu.size()>0) {

//System.out.println("ada pengajuan = "+ada_pengajuan);

%>	

                     
                     
   
   
    		<div class="panorama-section tile-span-6">
  			<h2>&nbsp</h2>
  			            <%
  	//======kehadiran dosen==================
  	String info_dosen_arrival = (String)request.getAttribute("info_kehadiran_dosen");
	//System.out.println("info_dosen_arrival="+info_dosen_arrival);  			     
  	if(false) {
  		%>

        <a class="tile app bg-color-orange" href="<%=Constants.getRootWeb() %>/InnerFrame/jadwal_kedatangan_dosen.jsp?info_dosen_arrival=<%=info_dosen_arrival %>" target="inner_iframe">
        	<div class="image-wrapper">
            	<span class="icon icon-clock-2"></span>
            </div>
           
            <div class="app-label">Info Kehadiran Dosen</div>
     	</a>        			
                	<%	
  	}
  	//======end kehadiran dosen==================
  			            
	//if(newMsgOnOwnInbox!=null && newMsgOnOwnInbox.equalsIgnoreCase("true")) {
	if(false) {	
                        	%>

                <a class="tile app bg-color-blue" href="get.msgInbox?sta_index=0&range=<%=Constants.getRangeMgsInbox()%>&show=unread" target="inner_iframe">
                	<div class="image-wrapper">
                    	<span class="icon icon-comment"></span>
                    </div>
                   
                    <div class="app-label">Unread Msg</div>
             	</a>        			
                        	<%
                        	//
	}
// ====================================krs start====================================================
   						
   						/*
   						<bagian penerima>
   						*/
   	if(true) {
   		
   							//System.out.println("1."+tknKrsNotifications);
   						%>
   				<a class="tile app bg-color-orange" href="monitor.pengisianKrs?atMenu=monitor" target="inner_iframe">
                	<div class="image-wrapper">
                    	<span class="icon icon-text"></span>
                  	</div>
                    
                    	<div class="text"></div>
                    	<div class="app-label"><%="" %> &nbsp Pengisian KRS</div>
                    
                    
             	</a>

   						<%
	}
   						/*
   						<bagian pengirim>
   						*/
   	if(false) {
   							//System.out.println("ini0="+tknKrsNotificationsForSender);
		
   						%>
   				<a class="tile wide imagetext bg-color-blueDark" href="<%=Constants.getRootWeb() %>/InnerFrame/Notifications/krsNotification.jsp" target="inner_iframe">
                	<div class="image-wrapper">
                		<span class="icon icon-chat-2"></span>
                	</div>
                	<div class="column-text">
                		<div class="text4">Pengajuan KRS</div>
                	</div>
                	<div class="app-label"><%="1" %></div>
             	</a>
   						<%
	}
   	if(false) {
   		if(false) {
   							
   						%>		
   				<a class="tile app bg-color-purple" href="process.statusRequestBukaKelas" target="inner_iframe">
                	<div class="image-wrapper">
                    	<span class="icon icon-progress-3"></span>
                   	</div>
                    <div class="app-label">Kelas Perkuliahan</div>
              	</a>
   						

   						<%
   		}
	}
	// ====================bukti pembayaran===============================================
	if(false) {
		%>
				<a class="tile app bg-color-red" href="<%=Constants.getRootWeb() %>/InnerFrame/Keu/requestKeuAprovalForm.jsp" target="inner_iframe">
                	<div class="image-wrapper">
                    	<span class="icon icon-calculate"></span>
                   	</div>
                    <div class="column-text">
                    	
             		</div>
                    <span class="app-label">Bukti Pembayaran</span>
               	</a>
			
		<%	
	}
	//======================pengajuan ujian sidang=======================================
	if(false) {
		
		String alm = "";
		if(validUsr.getObjNickNameGivenObjId().contains("MHS")||validUsr.getObjNickNameGivenObjId().contains("mhs")) {
			alm = "get.pengajuanUa?atMenu=ua";
		}
		else {
			alm = "get.pengajuanUa?atMenu=ua";
		}
	%>	
		<a class="tile wide imagetext bg-color-green" href="<%=alm %>" target="inner_iframe">
        	<div class="image-wrapper">
            	<span class="icon icon-calendar-3"></span>
            </div>
            <div class="text">Pengajuan Ujian</div>
            <div class="text">Sidang</div>
            <div class="app-label"></div>
        </a>
		
	<%
	}
%>

   
	</div>
   <!--  
                     <div class="panorama-section tile-span-4">
   
                        <h2>Group two</h2>
   
                        
   
                        <a class="tile wide imagetext bg-color-blueDark" href="./base-css.html">
                           <div class="image-wrapper">
                              <img src="assets/img/Coding app.png" />
                           </div>
                           <div class="column-text">
                              <div class="text">Typography</div>
                              <div class="text">Tables</div>
                              <div class="text">Forms</div>
                              <div class="text">Buttons</div>
                           </div>
                           <span class="app-label">BASE CSS</span>
                        </a>
   
                        <a class="tile app bg-color-orange" href="#">
                           <div class="image-wrapper">
                              <img src="assets/img/RegEdit.png" alt="" />
                           </div>
                           <span class="app-label">COMPONENTS</span>
                        </a>
   
                        <a class="tile app bg-color-red" href="./javascript.html">
                           <div class="image-wrapper">
                              <img src="assets/img/Devices.png" alt="" />
                           </div>
                           <span class="app-label">JAVASCRIPT</span>
                        </a>
   
                     </div>
   -->
                  </div>
               </div>
               <a id="panorama-scroll-prev" href="#"></a>
               <a id="panorama-scroll-next" href="#"></a>
               <div id="panorama-scroll-prev-bkg"></div>
               <div id="panorama-scroll-next-bkg"></div>
            <!--</div>-->
         <!--</div>-->
      <!--</div>-->
   
   </div>
   <div id="charms" class="win-ui-dark slide">
   
      <div id="theme-charms-section" class="charms-section">
         <div class="charms-header">
            <a href="#" class="close-charms win-backbutton"></a>
            <h2>Settings</h2>
         </div>
   
         <div class="row-fluid">
            <div class="span12">
   
               <form class="">
                  <label for="win-theme-select">Change theme:</label>
                  <select id="win-theme-select" class="">
                     <option value="metro-ui-light">Light</option>
                     <option value="metro-ui-dark">Dark</option>
                  </select>
               </form>
   
            </div>
         </div>
      </div>
   
   </div>

   <!-- Grab Google CDN's jQuery. fall back to local if necessary -->
   <script src="//code.jquery.com/jquery-1.10.0.min.js"></script>
   <script>window.jQuery || document.write("<script src='assets/js/jquery-1.10.0.min.js'>\x3C/script>")</script>

   <!--[if IE 7]>
   <script type="text/javascript" src="scripts/bootmetro-icons-ie7.js">
   <![endif]-->

   <script type="text/javascript" src="<%=Constants.getRootWeb() %>/bootmetro/assets/js/min/bootstrap.min.js"></script>
   <script type="text/javascript" src="<%=Constants.getRootWeb() %>/bootmetro/assets/js/bootmetro-panorama.js"></script>
   <script type="text/javascript" src="<%=Constants.getRootWeb() %>/bootmetro/assets/js/bootmetro-pivot.js"></script>
   <script type="text/javascript" src="<%=Constants.getRootWeb() %>/bootmetro/assets/js/bootmetro-charms.js"></script>
   <script type="text/javascript" src="<%=Constants.getRootWeb() %>/bootmetro/assets/js/bootstrap-datepicker.js"></script>

   <script type="text/javascript" src="<%=Constants.getRootWeb() %>/bootmetro/assets/js/jquery.mousewheel.min.js"></script>
   <script type="text/javascript" src="<%=Constants.getRootWeb() %>/bootmetro/assets/js/jquery.touchSwipe.min.js"></script>

   <script type="text/javascript" src="<%=Constants.getRootWeb() %>/bootmetro/assets/js/holder.js"></script>
   <!--<script type="text/javascript" src="<%=Constants.getRootWeb() %>/bootmetro/assets/js/perfect-scrollbar.with-mousewheel.min.js"></script>-->
   <script type="text/javascript" src="<%=Constants.getRootWeb() %>/bootmetro/assets/js/demo.js"></script>


   <script type="text/javascript">

      $('.panorama').panorama({
         //nicescroll: false,
         showscrollbuttons: true,
         keyboard: true,
         parallax: true
      });

//      $(".panorama").perfectScrollbar();

      $('#pivot').pivot();

   </script>
</body>
</html>
