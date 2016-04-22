<!DOCTYPE html>
<head>
<%@page import="beans.setting.Constants"%>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>


<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");

Vector v= null; 
String cmd = request.getParameter("cmd");
String msg = request.getParameter("msg");
String objId = request.getParameter("id_obj");
String nmm = request.getParameter("nmm");
String npm = request.getParameter("npm");
String kdpst = request.getParameter("kdpst");
String obj_lvl =  request.getParameter("obj_lvl");
/*
*/

Vector v_bak = (Vector) request.getAttribute("v_bak");
%>
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"> </script>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script>
            $(document).ready(function() {                        // When the HTML DOM is ready loading, then execute the following function...
                $('#somebutton').click(function() {               // Locate HTML DOM element with ID "somebutton" and assign the following function to its "click" event...
                    $.get('go.uploadFile2', function(responseText) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
                        $('#somediv').text(responseText);         // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
                    });
                });
            });
</script>
</head>
<body onload="location.href='#'">
<div id="header">
<ul>
	<li><a href="javascript:history.go(-1)" >BACK <span><b style="color:#eee">---</b></span></a></li>
</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<button id="somebutton">press here</button>
        <div id="somediv"></div>
  		<!-- Column 1 end -->
	</div>
</div>


</body>
</html>