<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
	
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String targetObjNickName = request.getParameter("targetObjNickName");
	/*
	* targetObjNickName = obj nickname di baa yg bisa dikontak / dalam scope usr 
	*/
	String unreadInfo = ""+request.getParameter("unreadInfo");
	
	//System.out.println("unreadInfo@dashContactUsBaa.jsp="+unreadInfo);
	session.removeAttribute("vSubTopik");
%>
<!--  script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script -->
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/imgResize/resize.js"></script>
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/css/fbtxtbox.css" media="screen" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/css/imgResize/resize.css" media="screen" />
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/resizeIframe/resize.js"></script>
<script type="text/javascript">
$(function() 
{

$("#content").focus(function()
{
$(this).animate({"height": "85px",}, "fast" );
$("#button_block").slideDown("fast");
return false;
});

$("#cancel").click(function()
{
$("#content").animate({"height": "30px",}, "fast" );
$("#button_block").slideUp("fast");
return false;
});

});




</script>



</head>
<!--  body onload="location.href='#'" -->
<body>
<div id="header">
<%@ include file="contactUsSubMenu-0.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<iframe name="mf" id="mainFrame" src="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/ContactUs/tampleteMainPost.jsp?atMenu=pmb&targetObjNickName=<%=targetObjNickName %>" scrolling="no" frameborder="0" style="border:none; overflow:hidden; width:600px; height:100%" allowTransparency="true"></iframe>
		<iframe name="smf" id="subMainFrame" src="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/ContactUs/tampleteTopic.jsp?atMenu=pmb&targetObjNickName=<%=targetObjNickName %>&xy=0,<%=Constants.getQuantityMsgShownPerPage() %>&unreadInfo=<%=unreadInfo %>" scrolling="no" frameborder="0" style="border:none; overflow:hidden; width:600px; height:100%;" allowTransparency="true"></iframe>
		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>