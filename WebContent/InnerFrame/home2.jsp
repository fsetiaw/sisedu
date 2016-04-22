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
	<%@ page import="beans.dbase.notification.*" %>
	
<%	
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
/*
ubah menggunakan session value
*/
boolean sdu = Checker.sudahDaftarUlang(session);
String curr_stmhsmsmhs = Checker.getCurrStmhs(session);
String tknKrsNotifications = (String)session.getAttribute("tknKrsNotifications");
//String tknKrsNotifications = request.getParameter("tknKrsNotifications");
String tknKrsNotificationsForSender = (String)session.getAttribute("tknKrsNotificationsForSender");
//String tknKrsNotificationsForSender = request.getParameter("tknKrsNotificationsForSender");
Vector vBk = (Vector) session.getAttribute("vBukaKelas");
JSONArray jsoaPymntReq = (JSONArray) session.getAttribute("jsoaPymntReq");
String newMsgOnMonitoredInbox= request.getParameter("newMsgOnMonitoredInbox");
String newMsgOnOwnInbox = request.getParameter("newMsgOnOwnInbox");
String listKdpstBk = (String) request.getAttribute("listKdpstBukaKelas");
String kdjenKdpstNmpstNoPengajuan = (String) request.getAttribute("kdjenKdpstNmpstNoPengajuan");
String hasBukaKelasCmd = (String)request.getAttribute("hasBukaKelasCmd");
String ada_pengajuan = (String)session.getAttribute("ada_pengajuan");

//System.out.println("tknKrsNotifications="+tknKrsNotifications);
//System.out.println("tknKrsNotificationsForSender="+tknKrsNotificationsForSender);
/*
*==================remove session object ===================================
*/
session.removeAttribute("status_pengajuan_cuti_"+validUsr.getNpm());
session.removeAttribute("status_akhir_mahasiswa");
request.removeAttribute("vApprovedHeregistrasi");
request.removeAttribute("v1v2"); 
request.removeAttribute("vListNpmOnTrnlm");
session.removeAttribute("vListMhs");
session.removeAttribute("vThsmsStmhs");
session.removeAttribute("vScope_cmd");
session.removeAttribute("vScopeObjId");
session.removeAttribute("goto_approval");
session.removeAttribute("job");
session.removeAttribute("vHisBea");
session.removeAttribute("vListNamaPaketBea");
session.removeAttribute("vMhsUnHeregContainment");
session.removeAttribute("kodeKampus");
session.removeAttribute("dataForRouterAfterUpload");
session.removeAttribute("hakAksesUsrUtkFolderIni");
session.removeAttribute("v_nm_alm_access");
session.removeAttribute("target_kelas_info");
session.removeAttribute("vMhsContainer");
session.removeAttribute("kelasTambahan");
session.removeAttribute("forceBackTo");
session.removeAttribute("listMonitorNickname");
session.removeAttribute("fieldAndValue");
session.removeAttribute("v1v2v3");
session.removeAttribute("vInfoListKandidat");
Vector vReqAprKeu = (Vector)session.getAttribute("vReqAprKeu");
session.removeAttribute("listTipeUjian");
session.removeAttribute("validatedTransDate");
session.removeAttribute("infoKelasDosen");
session.removeAttribute("infoKelasMhs");
session.removeAttribute("vJsoa");
session.removeAttribute("saveToFolder");
session.removeAttribute("targetAkun");
session.removeAttribute("sumberDana");
session.removeAttribute("vListKelasAjar");
session.removeAttribute("vListKategori");
session.removeAttribute("vAssigned");
session.removeAttribute("vListForMhs");
session.removeAttribute("vCp");
session.removeAttribute("vInfoKehadiranDosen");
session.removeAttribute("target_npm");
session.removeAttribute("status_update");
session.removeAttribute("ada_pengajuan");
session.removeAttribute("vPengajuan");
session.removeAttribute("vRiwayatPengajuan");
session.removeAttribute("vKdpstNpm");
session.removeAttribute("vListRule");
%>
	
   <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="description" content="Metro, a sleek, intuitive, and powerful framework for faster and easier web development for Windows Metro Style.">
    <meta name="keywords" content="HTML, CSS, JS, JavaScript, framework, metro, front-end, frontend, web development">
    <meta name="author" content="Sergey Pimenov and Metro UI CSS contributors">

   

   <meta name="robots" content="noindex, nofollow">
   <meta name="description" content="BootMetro : Simple and complete web UI framework to create web apps with Windows 8 Metro user interface." />
   <meta name="keywords" content="bootmetro, modern ui, modern-ui, metro, metroui, metro-ui, metro ui, windows 8, metro style, bootstrap, framework, web framework, css, html" />
   <meta name="author" content="AozoraLabs by Marcello Palmitessa"/>
   <link rel="publisher" href="https://plus.google.com/117689250782136016574">

   <!-- remove or comment this line if you want to use the local fonts -->
   <!--  link href='http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700' rel='stylesheet' type='text/css' -->
   <link rel="shortcut icon" href="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/favicon.ico">
   <link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/css/metro.css">
   <link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/css/metro-icons.css">

   <script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/jquery-2.1.3.min.js"></script>
   <script src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/js/metro.js"></script>

    <style>
        .tile-area-controls {
            position: fixed;
            right: 40px;
            top: 40px;
        }

        .tile-group {
            left: 100px;
        }

        .tile, .tile-small, .tile-sqaure, .tile-wide, .tile-large, .tile-big, .tile-super {
            opacity: 0;
            -webkit-transform: scale(.8);
            transform: scale(.8);
        }

        #charmSettings .button {
            margin: 5px;
        }

        .schemeButtons {
            /*width: 300px;*/
        }

        @media screen and (max-width: 640px) {
            .tile-area {
                overflow-y: scroll;
            }
            .tile-area-controls {
                display: none;
            }
        }

        @media screen and (max-width: 320px) {
            .tile-area {
                overflow-y: scroll;
            }

            .tile-area-controls {
                display: none;
            }

        }
    </style>

    <script>

        /*
         * Do not use this is a google analytics fro Metro UI CSS
         * */
        if (window.location.hostname !== 'localhost') {

            (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
                (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
                    m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
            })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

            ga('create', 'UA-58849249-3', 'auto');
            ga('send', 'pageview');

        }

    </script>

    <script>
        (function($) {
            $.StartScreen = function(){
                var plugin = this;
                var width = (window.innerWidth > 0) ? window.innerWidth : screen.width;

                plugin.init = function(){
                    setTilesAreaSize();
                    if (width > 640) addMouseWheel();
                };

                var setTilesAreaSize = function(){
                    var groups = $(".tile-group");
                    var tileAreaWidth = 80;
                    $.each(groups, function(i, t){
                        if (width <= 640) {
                            tileAreaWidth = width;
                        } else {
                            tileAreaWidth += $(t).outerWidth() + 80;
                        }
                    });
                    $(".tile-area").css({
                        width: tileAreaWidth
                    });
                };

                var addMouseWheel = function (){
                    $("body").mousewheel(function(event, delta, deltaX, deltaY){
                        var page = $(document);
                        var scroll_value = delta * 50;
                        page.scrollLeft(page.scrollLeft() - scroll_value);
                        return false;
                    });
                };

                plugin.init();
            }
        })(jQuery);

        $(function(){
            $.StartScreen();

            var tiles = $(".tile, .tile-small, .tile-sqaure, .tile-wide, .tile-large, .tile-big, .tile-super");

            $.each(tiles, function(){
                var tile = $(this);
                setTimeout(function(){
                    tile.css({
                        opacity: 1,
                        "-webkit-transform": "scale(1)",
                        "transform": "scale(1)",
                        "-webkit-transition": ".3s",
                        "transition": ".3s"
                    });
                }, Math.floor(Math.random()*500));
            });

            $(".tile-group").animate({
                left: 0
            });
        });

        function showCharms(id){
            var  charm = $(id).data("charm");
            if (charm.element.data("opened") === true) {
                charm.close();
            } else {
                charm.open();
            }
        }

        function setSearchPlace(el){
            var a = $(el);
            var text = a.text();
            var toggle = a.parents('label').children('.dropdown-toggle');

            toggle.text(text);
        }

        $(function(){
            var current_tile_area_scheme = localStorage.getItem('tile-area-scheme') || "tile-area-scheme-dark";
            $(".tile-area").removeClass (function (index, css) {
                return (css.match (/(^|\s)tile-area-scheme-\S+/g) || []).join(' ');
            }).addClass(current_tile_area_scheme);

            $(".schemeButtons .button").hover(
                    function(){
                        var b = $(this);
                        var scheme = "tile-area-scheme-" +  b.data('scheme');
                        $(".tile-area").removeClass (function (index, css) {
                            return (css.match (/(^|\s)tile-area-scheme-\S+/g) || []).join(' ');
                        }).addClass(scheme);
                    },
                    function(){
                        $(".tile-area").removeClass (function (index, css) {
                            return (css.match (/(^|\s)tile-area-scheme-\S+/g) || []).join(' ');
                        }).addClass(current_tile_area_scheme);
                    }
            );

            $(".schemeButtons .button").on("click", function(){
                var b = $(this);
                var scheme = "tile-area-scheme-" +  b.data('scheme');

                $(".tile-area").removeClass (function (index, css) {
                    return (css.match (/(^|\s)tile-area-scheme-\S+/g) || []).join(' ');
                }).addClass(scheme);

                current_tile_area_scheme = scheme;
                localStorage.setItem('tile-area-scheme', scheme);

                showSettings();
            });
        });

        var weather_icons = {
            'clear-day': 'mif-sun',
            'clear-night': 'mif-moon2',
            'rain': 'mif-rainy',
            'snow': 'mif-snowy3',
            'sleet': 'mif-weather4',
            'wind': 'mif-wind',
            'fog': 'mif-cloudy2',
            'cloudy': 'mif-cloudy',
            'partly-cloudy-day': 'mif-cloudy3',
            'partly-cloudy-night': 'mif-cloud5'
        };

        var api_key = 'AIzaSyDPfgE0qhVmCcy-FNRLBjO27NbVrFM2abg';

        $(function(){
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function(position){
                    var lat = position.coords.latitude, lon = position.coords.longitude;
                    var pos = lat+','+lon;
                    var latlng = new google.maps.LatLng(lat, lon);
                    var geocoder = new google.maps.Geocoder();
                    $.ajax({
                        url: '//api.forecast.io/forecast/219588ba41dedc2f1019684e8ac393ad/'+pos+'?units=si',
                        dataType: 'jsonp',
                        success: function(data){
                            //do whatever you want with the data here
                            geocoder.geocode({latLng: latlng}, function(result, status){
                                console.log(result[3]);
                                $("#city_name").html(result[3].formatted_address);
                            });
                            var current = data.currently;
                            //$('#city_name').html(response.city+", "+response.country);
                            $("#city_temp").html(Math.round(current.temperature)+" &deg;C");
                            $("#city_weather").html(current.summary);
                            $("#city_weather_daily").html(data.daily.summary);
                            $("#weather_icon").addClass(weather_icons[current.icon]);
                            $("#pressure").html(current.pressure);
                            $("#ozone").html(current.ozone);
                            $("#wind_bearing").html(current.windBearing);
                            $("#wind_speed").html(current.windSpeed);
                            $("#weather_bg").css({
                                'background-image': 'url(../images/'+current.icon+'.jpg'+')'
                            });
                        }
                    });
                });
            }
        });
    </script>

</head>
<body style="overflow-y: hidden;">
    <!--  div data-role="charm" id="charmSearch">
        <h1 class="text-light">Search</h1>
        <hr class="thin"/>
        <br />
        <div class="input-control text full-size">
            <label>
                <span class="dropdown-toggle drop-marker-light">Anywhere</span>
                <ul class="d-menu" data-role="dropdown">
                    <li><a onclick="setSearchPlace(this)">Anywhere</a></li>
                    <li><a onclick="setSearchPlace(this)">Options</a></li>
                    <li><a onclick="setSearchPlace(this)">Files</a></li>
                    <li><a onclick="setSearchPlace(this)">Internet</a></li>
                </ul>
            </label>
            <input type="text">
            <button class="button"><span class="mif-search"></span></button>
        </div>
    </div -->

    <!--  div data-role="charm" id="charmSettings" data-position="top">
        <h1 class="text-light">Settings</h1>
        <hr class="thin"/>
        <br />
        <div class="schemeButtons">
            <div class="button square-button tile-area-scheme-dark" data-scheme="dark"></div>
            <div class="button square-button tile-area-scheme-darkBrown" data-scheme="darkBrown"></div>
            <div class="button square-button tile-area-scheme-darkCrimson" data-scheme="darkCrimson"></div>
            <div class="button square-button tile-area-scheme-darkViolet" data-scheme="darkViolet"></div>
            <div class="button square-button tile-area-scheme-darkMagenta" data-scheme="darkMagenta"></div>
            <div class="button square-button tile-area-scheme-darkCyan" data-scheme="darkCyan"></div>
            <div class="button square-button tile-area-scheme-darkCobalt" data-scheme="darkCobalt"></div>
            <div class="button square-button tile-area-scheme-darkTeal" data-scheme="darkTeal"></div>
            <div class="button square-button tile-area-scheme-darkEmerald" data-scheme="darkEmerald"></div>
            <div class="button square-button tile-area-scheme-darkGreen" data-scheme="darkGreen"></div>
            <div class="button square-button tile-area-scheme-darkOrange" data-scheme="darkOrange"></div>
            <div class="button square-button tile-area-scheme-darkRed" data-scheme="darkRed"></div>
            <div class="button square-button tile-area-scheme-darkPink" data-scheme="darkPink"></div>
            <div class="button square-button tile-area-scheme-darkIndigo" data-scheme="darkIndigo"></div>
            <div class="button square-button tile-area-scheme-darkBlue" data-scheme="darkBlue"></div>
            <div class="button square-button tile-area-scheme-lightBlue" data-scheme="lightBlue"></div>
            <div class="button square-button tile-area-scheme-lightTeal" data-scheme="lightTeal"></div>
            <div class="button square-button tile-area-scheme-lightOlive" data-scheme="lightOlive"></div>
            <div class="button square-button tile-area-scheme-lightOrange" data-scheme="lightOrange"></div>
            <div class="button square-button tile-area-scheme-lightPink" data-scheme="lightPink"></div>
            <div class="button square-button tile-area-scheme-grayed" data-scheme="grayed"></div>
        </div>
    </div-->
<div class="tile-area tile-area-scheme-dark fg-white" style="height: 100%; max-height: 100% !important;">
        <!--  h1 class="tile-area-title">Start</h1 
        <div class="tile-area-controls">
            <button class="image-button icon-right bg-transparent fg-white bg-hover-dark no-border"><span class="sub-header no-margin text-light">Sergey Pimenov</span> <span class="icon mif-user"></span></button>
            <button class="square-button bg-transparent fg-white bg-hover-dark no-border" onclick="showCharms('#charmSearch')"><span class="mif-search"></span></button>
            <button class="square-button bg-transparent fg-white bg-hover-dark no-border" onclick="showCharms('#charmSettings')"><span class="mif-cog"></span></button>
            <a href="../tiles.html" class="square-button bg-transparent fg-white bg-hover-dark no-border"><span class="mif-switch"></span></a>
        </div-->
        
	<div class="tile-group quadro">
		<span class="tile-group-title">Notifikasi</span>
		<div class="tile-container">
		<%
	
	  	//======kehadiran dosen==================
	  	String info_dosen_arrival = (String)request.getAttribute("info_kehadiran_dosen");
		//System.out.println("info_dosen_arrival="+info_dosen_arrival);  			     
	  	//if(info_dosen_arrival!=null && !Checker.isStringNullOrEmpty(info_dosen_arrival)) {
	  	if(true) {
	  	%>
			<a href="<%=Constants.getRootWeb() %>/InnerFrame/jadwal_kedatangan_dosen.jsp?info_dosen_arrival=<%=info_dosen_arrival %>" target="inner_iframe" class="tile bg-darkTeal fg-white" data-role="tile">
            	<div class="padding20">
                	<p class="no-margin text-shadow">INFO <span class="text-bold">KEDATANGAN</span><br/>DOSEN</p>
				</div>
			</a>       			
	                	<%	
	  	}
	  	//======end kehadiran dosen==================
	  	//=================my inbox====================
	  	if(newMsgOnOwnInbox!=null && newMsgOnOwnInbox.equalsIgnoreCase("true")) {
                        	%>
            <a href="get.msgInbox?sta_index=0&range=<%=Constants.getRangeMgsInbox()%>&show=unread" target="inner_iframe" class="tile bg-darkOrange fg-white" data-role="tile">            	
          		<div class="tile-content iconic">
                	<span class="icon mif-envelop"></span>
                </div>
                    <span class="tile-label">New Inbox</span>
            </a>           	
<%
		}
	  	//=================my inbox====================
	  			
	  	//=================krs Approval====================
	  	// ====================================krs start====================================================
   						
   						/*
   						<bagian penerima>
   						*/
		//if(tknKrsNotifications!=null && !Checker.isStringNullOrEmpty(tknKrsNotifications)) {
		if(true) {	
   			StringTokenizer st = new StringTokenizer(tknKrsNotifications,"||");
   			application.setAttribute("tknKrsNotifications", tknKrsNotifications);
   							//System.out.println("1."+tknKrsNotifications);
   						%>
   				<a class="tile bg-indigo fg-white" data-role="tile" href="<%=Constants.getRootWeb() %>/InnerFrame/Notifications/krsNotification.jsp" target="inner_iframe">
                    <div class="tile-content iconic">
                        <span class="icon mif-file-binary"></span>
                    </div>
                    <span class="tile-label"><%=st.countTokens() %>&nbspPengajuan KRS</span>
                </a>		
   				

   						<%
		}
   						/*
   						<bagian pengirim>
   						*/
	//if(tknKrsNotificationsForSender!=null && !Checker.isStringNullOrEmpty(tknKrsNotificationsForSender)) {
   		if(true){						//System.out.println("ini0="+tknKrsNotificationsForSender);
			StringTokenizer st = new StringTokenizer(tknKrsNotificationsForSender,"||");
   						%>
   				<a class="tile bg-indigo fg-white" data-role="tile" href="<%=Constants.getRootWeb() %>/InnerFrame/Notifications/krsNotification.jsp" target="inner_iframe">
                    <div class="tile-content iconic">
                        <span class="icon mif-file-binary"></span>
                    </div>
                    <div style="margin:115px 0 0 10px">   
                    	<p id="city_weather_daily"></p> 
                        <p class="no-margin text-shadow">STATUS<br/> <span class="text-bold">PENGAJUAN KRS</span></p>
                    </div>
                    
                </a>		
   				
   						<%
		}
	  	//===============end Krs approval =================
	  	//===============buka kelas perkuliahan=============
	  	//if(hasBukaKelasCmd!=null && hasBukaKelasCmd.equalsIgnoreCase("yes")) {
		//	if((listKdpstBk!=null && !Checker.isStringNullOrEmpty(listKdpstBk)) || (kdjenKdpstNmpstNoPengajuan!=null && !Checker.isStringNullOrEmpty(kdjenKdpstNmpstNoPengajuan))) {
   		if(true) {
   			if(true) {		
   		%>	
   							
   				<div class="tile-wide bg-orange fg-white" data-role="tile">
                   	<div class="tile-content image-set">
                   		<a href="process.statusRequestBukaKelas?listKdpstBk=<%=""+listKdpstBk %>&infoKdpstNoPengajuan=<%=""+kdjenKdpstNmpstNoPengajuan %>" target="inner_iframe"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/classroom3.jpg" data-role="fitImage" data-format="fill"></a>
                    </div>
                    <div style="margin:0 0 0 160px;position: absolute;">   
                    	<p id="city_weather_daily"></p> 
                        <p class="no-margin text-shadow"><span class="text-bold">KELAS PERKULIAHAN</span></p>
                    </div>
                </div>
   			<%
   			}
		}
	  	//=============end buka kelas perkuliahan=============
	  			
	  	//=============buti pembayaran=============	
	  	//if(validUsr.isAllowTo("pymntApprovee")>0 && jsoaPymntReq!=null && jsoaPymntReq.length()>0) {
	  	if(true) {
		%>
				<div class="tile-wide bg-orange fg-white" data-role="tile">
                   	<div class="tile-content image-set">
                   		<a href="<%=Constants.getRootWeb() %>/InnerFrame/Keu/requestKeuAprovalForm.jsp" target="inner_iframe"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/payment.jpg" data-role="fitImage" data-format="fill"></a>
                    </div>
                       	<div style="margin:15px 0 0 10px;position: absolute;">   
                   	
                       		<p class="no-margin text-shadow" style="color:#74888b">VALIDASI<br/><span class="text-bold"> PEMBAYARAN</span><br/>MAHASISWA</p>
                   		</div>
                	</div>
                </div>	
			
		<%	
		}
	  	//===========end buti pembayaran=============		
	  	//======================pengajuan ujian sidang===============================
	  	//if(ada_pengajuan!=null && ada_pengajuan.equalsIgnoreCase("true")) {
		if(true) {
		String alm = "";
			//if(validUsr.getObjNickNameGivenObjId().contains("MHS")||validUsr.getObjNickNameGivenObjId().contains("mhs")) {
				alm = "get.pengajuanUa?atMenu=ua";
			//}
			//else {
				alm = "get.pengajuanUa?atMenu=ua";
			//}
			//validUsr.
			if(validUsr.iAmStu()) {
				%>
				<div class="tile-wide bg-orange fg-white" data-role="tile">
                   	<div class="tile-content image-set">
                   		<a href="<%=alm%>" target="inner_iframe"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/grad.jpg" data-role="fitImage" data-format="fill"></a>
                    </div>
                       	<div style="margin:115px 0 0 140px;position: absolute;">   
                   	
                       		<p class="no-margin text-shadow">PENGAJUAN UJIAN<br/><span class="text-bold">SKRIPSI/TESIS/DISERTASI </span><br/></p>
                   		</div>
                	</div>
                </div>
			
		<%		
			}
			else {
			%>
				<div class="tile bg-orange fg-white">
                   	<div class="tile-content">
                   		<a href="<%=alm%>" target="inner_iframe"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/appointment5.jpg" data-role="fitImage" data-format="square"></a>
                    </div>
                    <div style="margin:15px 0 0 10px;position: absolute;">   
                   			<p class="no-margin text-shadow" style="color:#555a5e">PENGAJUAN UJIAN<br/><span class="text-bold">SKRIPSI/TESIS<br/>& DISERTASI </span><br/></p>
                    </div>
                </div>
	<%
			}
		}
	  	//======================end pengajuan ujian sidang===============================
		//=====================pengajuan cuti===========================
		SearchDbMainNotification sdmn = new SearchDbMainNotification(validUsr.getNpm());	
		if(sdmn.getNotificationPengajuanCuti()) {
			%>
				<div class="tile bg-orange fg-white">
             	  	<div class="tile-content">
            	   		<a href="go.moCuti?target_thsms=<%=Checker.getThsmsHeregistrasi() %>" target="inner_iframe"><img src="<%=Constants.getRootWeb() %>/Metro-UI-CSS-master/docs/images/cuti.jpg" data-role="fitImage" data-format="square"></a>
            	    </div>
            	    <div style="margin:65px 0 0 60px;position: absolute;">   
               			<p class="no-margin text-shadow">PENGAJUAN<br/><span class="text-bold">CUTI</span><br/>KULIAH</p>
             	   </div>
          	  	</div>
<%
		}
	//==================end pengajuan cuti===========================
		%>

            </div>
        

	<div class="tile-group quadro">
   		<span class="tile-group-title">Overview</span>
        <div class="tile-container">
                
                
        	<div class="tile-small bg-amber fg-white" data-role="tile">
            	<div class="padding5">
                	<p id="city_weather_daily"></p>
                    <p class="no-margin text-shadow">MHS <span class="text-bold" id="pressure"></span>CUTI</p>
				</div>
          	</div>
            <div class="tile-small bg-green fg-white" data-role="tile">
            	<div class="tile-content iconic">
                	<span class="icon mif-gamepad"></span>
                </div>
            </div>
        </div>

        <!--  div class="tile-group one">
            <span class="tile-group-title">Office</span>

            <div class="tile-small bg-blue" data-role="tile">
                <div class="tile-content iconic">
                    <img src="../images/outlook.png" class="icon">
                </div>
            </div>
            <div class="tile-small bg-darkBlue" data-role="tile">
                <div class="tile-content iconic">
                    <img src="../images/word.png" class="icon">
                </div>
            </div>
            <div class="tile-small bg-green" data-role="tile">
                <div class="tile-content iconic">
                    <img src="../images/excel.png" class="icon">
                </div>
            </div>
            <div class="tile-small bg-red" data-role="tile">
                <div class="tile-content iconic">
                    <img src="../images/access.png" class="icon">
                </div>
            </div>
            <div class="tile-small bg-orange" data-role="tile">
                <div class="tile-content iconic">
                    <img src="../images/powerpoint.png" class="icon">
                </div>
            </div>
        </div>

        <div class="tile-group double">
            <span class="tile-group-title">Games</span>
            <div class="tile-container">
                <div class="tile" data-role="tile">
                    <div class="tile-content">
                        <img src="../images/grid2.jpg" data-role="fitImage" data-format="square">
                    </div>
                </div>
                <div class="tile-small" data-role="tile">
                    <div class="tile-content">
                        <img src="../images/Battlefield_4_Icon.png" data-role="fitImage" data-format="square">
                    </div>
                </div>
                <div class="tile-small" data-role="tile">
                    <div class="tile-content">
                        <img src="../images/Crysis-2-icon.png" data-role="fitImage" data-format="square" data-frame-color="bg-steel">
                    </div>
                </div>
                <div class="tile-small" data-role="tile">
                    <div class="tile-content">
                        <img src="../images/WorldofTanks.png" data-role="fitImage" data-format="square" data-frame-color="bg-dark">
                    </div>
                </div>
                <div class="tile-small" data-role="tile">
                    <div class="tile-content">
                        <img src="../images/halo.jpg" data-role="fitImage" data-format="square">
                    </div>
                </div>
                <div class="tile-wide bg-green fg-white" data-role="tile">
                    <div class="tile-content iconic">
                        <img src="../images/x-box.png" class="icon">
                    </div>
                    <div class="tile-label">X-Box Live</div>
                </div>
            </div>
        </div>

        <div class="tile-group double">
            <span class="tile-group-title">Other</span>
            <div class="tile-container">
                <div class="tile bg-teal fg-white" data-role="tile">
                    <div class="tile-content iconic">
                        <span class="icon mif-pencil"></span>
                    </div>
                    <span class="tile-label">Editor</span>
                </div>
                <div class="tile bg-darkGreen fg-white" data-role="tile">
                    <div class="tile-content iconic">
                        <span class="icon mif-shopping-basket"></span>
                    </div>
                    <span class="tile-label">Store</span>
                </div>
                <div class="tile bg-cyan fg-white" data-role="tile">
                    <div class="tile-content iconic">
                        <span class="icon mif-skype"></span>
                    </div>
                    <div class="tile-label">Skype</div>
                </div>
                <div class="tile bg-darkBlue fg-white" data-role="tile">
                    <div class="tile-content iconic">
                        <span class="icon mif-cloud"></span>
                    </div>
                    <span class="tile-label">OneDrive</span>
                </div>
            </div>
        </div-->
    </div>

    <!-- hit.ua -->
    <a href='http://hit.ua/?x=136046' target='_blank'>
        <script language="javascript" type="text/javascript"><!--
        Cd=document;Cr="&"+Math.random();Cp="&s=1";
        Cd.cookie="b=b";if(Cd.cookie)Cp+="&c=1";
        Cp+="&t="+(new Date()).getTimezoneOffset();
        if(self!=top)Cp+="&f=1";
        //--></script>
        <script language="javascript1.1" type="text/javascript"><!--
        if(navigator.javaEnabled())Cp+="&j=1";
        //--></script>
        <script language="javascript1.2" type="text/javascript"><!--
        if(typeof(screen)!='undefined')Cp+="&w="+screen.width+"&h="+
        screen.height+"&d="+(screen.colorDepth?screen.colorDepth:screen.pixelDepth);
        //--></script>
        <script language="javascript" type="text/javascript"><!--
        Cd.write("<img src='http://c.hit.ua/hit?i=136046&g=0&x=2"+Cp+Cr+
        "&r="+escape(Cd.referrer)+"&u="+escape(window.location.href)+
        "' border='0' wi"+"dth='1' he"+"ight='1'/>");
        //--></script></a>
    <!-- / hit.ua -->
</body>
</html>
