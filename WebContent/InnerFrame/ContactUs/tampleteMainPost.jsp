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
	String refreshTampleteTopic = ""+request.getParameter("refreshTampleteTopic");
	String targetObjNickName = request.getParameter("targetObjNickName"); //target bisa beberapa token
	String listTargetObjNickName = ""+targetObjNickName;
	//System.out.println("targetObjNickName@tampleteMainPost.jsp="+targetObjNickName);
	String ObjGenderAndNickname = (String)session.getAttribute("ObjGenderAndNickname");
	StringTokenizer st = new StringTokenizer(ObjGenderAndNickname,"__");
	String usrKdjek = st.nextToken();
	String listUsrObjNickname = st.nextToken();
	String atMenu = request.getParameter("atMenu");
	String validUsrNick = validUsr.getObjNickNameGivenObjId();
%>
<!--  script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script -->
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/imgResize/resize.js"></script>
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/css/fbtxtbox0.css" media="screen" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/css/imgResize/resize.css" media="screen" />
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/resizeIframe/resize.js"></script>
<script type="text/javascript">
$(function() 
{

$("#content").focus(function()
{
$(this).animate({"height": "60px",}, "fast" );
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
<%
if(refreshTampleteTopic.equalsIgnoreCase("true")) {
		//System.out.println("true");
	session.setAttribute("targetObjNickName",targetObjNickName);
%>
	<script type="text/javascript">
	parent.document.getElementById('subMainFrame').src=parent.document.getElementById('subMainFrame').src;
//parent.getElementByName("subMainFrame").location.reload();
	</script>
<%		
}
else {
	//System.out.println("false");
}
%>

	<form method="post" action="post.mainPostTopic" target="_self">
	<!--  input type="hidden" name="listUsrObjNickname" value="<%=listUsrObjNickname %>" -->
	<input type="hidden" name="listTargetObjNickName" value="<%=listTargetObjNickName %>">	
<%
st = new StringTokenizer(listUsrObjNickname,",");
if(st.countTokens()>1) {
%>
	PENGIRIM PESAN &nbsp:&nbsp<select name="objNicknameSenderAs" style="width:200px">
<%
	while(st.hasMoreTokens()) {
		String nick = st.nextToken();
		boolean match=false;
		String[]listObjectNicknameBolehPost = Constants.getListAllowableObjNicknameSender();
		for(int i=0;i<listObjectNicknameBolehPost.length && !match;i++) {
			if(listObjectNicknameBolehPost[i].equals(nick)) {
				match = true;
			}
		}
		if(match) {
%>
		<option value="<%=nick%>"><%=nick.replace("OPERATOR","")%></option>
<%		
		}
	}
%>		
	</select><br/>
<%	
}
else {
%>
	<input type="hidden" name="objNicknameSenderAs" value="<%=st.nextToken() %>" />
<%	
}

st = new StringTokenizer(targetObjNickName,"__");
/*
=========================================================================
kalo user adalah mhs, pilhan kontak pada tu maha ditujukan kepada tu saja, jadi kalo ktu dan tu =1orang
maka hanya tu saja yg tampil
*/
String nuTargetObj = null;
boolean first = true;
//System.out.println("atMenu="+atMenu);
//System.out.println("validUsr1="+validUsr.getObjNickNameGivenObjId());
if(atMenu!=null && atMenu.equalsIgnoreCase("tu") && validUsrNick.contains("MHS")) {
	//System.out.println("1");
	st = new StringTokenizer(targetObjNickName,"__");
	//System.out.println("2");
	while(st.hasMoreTokens()) {
		//System.out.println("3");
		String tmpNickname = st.nextToken();
		//System.out.println("4a");
		if(tmpNickname.contains("TU")&&!tmpNickname.contains("KTU")) {
			//System.out.println("tmpNickname="+tmpNickname);
			//System.out.println("5");
			//System.out.println("tmpNickname="+tmpNickname);
			if(first) {
				//System.out.println("6");
				nuTargetObj = ""+tmpNickname;
				first = false;
			}
			else {
				//System.out.println("7");
				nuTargetObj = nuTargetObj+"__"+tmpNickname;
			}
		}
		//System.out.println("8");
	}
	st = new StringTokenizer(nuTargetObj,"__");
}
//System.out.println("st nuTargetObj="+nuTargetObj);
//System.out.println("st targetObjNickName="+targetObjNickName);
if(st.countTokens()>1) {
%>
	TUJUAN PESAN &nbsp&nbsp&nbsp&nbsp&nbsp:&nbsp<select name="targetObjNickName" style="width:200px">
<%
	while(st.hasMoreTokens()) {
		String nick = st.nextToken();
%>
		<option value="<%=nick%>"><%=nick.replace("OPERATOR","")%></option>
<%		
	}
%>		
	</select><br/>
<%		
}
else {
%>
	<input type="hidden" name="targetObjNickName" value="<%=st.nextToken() %>" />
<%	
}
%>    		
		
		
		<textarea  id="content" placeholder="Silahkan Ajukan Pertanyaan Anda" name="mainTopic"></textarea>
		<div id="button_block">
			<input type="submit" id="button" value=" Kirim "/>
			<input type="submit" id='cancel' value=" Batal " />
		</div>
	</form>
</body>
</html>