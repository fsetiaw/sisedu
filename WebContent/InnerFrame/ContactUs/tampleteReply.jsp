<!DOCTYPE html>
<%@page import="beans.sistem.AskSystem"%>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/css/verticalScrollbar/style.css" media="screen" />
	
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
String refreshTampleteReply = ""+request.getParameter("refreshTampleteReply");
if(refreshTampleteReply.equalsIgnoreCase("true")) {
	//session.setAttribute("targetObjNickName",targetObjNickName);
%>
	<!--  script type="text/javascript">
	parent.document.getElementById('subMainFrame').src=parent.document.getElementById('subMainFrame').src;
	parent.document.getElementById('replyFrame').src=parent.document.getElementById('replyFrame').src;
	</script -->
<%		
}
else {
	System.out.println("false");
}
%>
<%
	//System.out.println("ttampleteReplyJsp");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String ObjGenderAndNickname = (String)session.getAttribute("ObjGenderAndNickname");
	StringTokenizer st = new StringTokenizer(ObjGenderAndNickname,"__");
	String usrKdjek = st.nextToken();
	String usrObjNickname = st.nextToken();
	//System.out.println("1");
	//String usrObjNickname = validUsr.getObjNickNameGivenObjId();
	//System.out.println("2");
	//String usrKdjek = validUsr.getKdjek();
	//System.out.println("3");
	Vector vSubTopik = (Vector) request.getAttribute("vSubTopik");
	//System.out.println("4");
	String usrNpm = validUsr.getNpm();
	//System.out.println("5");
	String topikInfo = request.getParameter("topikInfo");
	//System.out.println("topikInfo1="+topikInfo);
	st = new StringTokenizer(topikInfo,"||");
	String idTopik = st.nextToken();
	String content = st.nextToken();
	String npmhsCreator = st.nextToken();
	String nmmhsCreator = st.nextToken();
	String creatorObjId = st.nextToken();
	String creatorObjNickName = st.nextToken();
	String targetKdpst = st.nextToken();
	String targetNpmhs = st.nextToken();
	String targetSmawl = st.nextToken();
	String targetObjId = st.nextToken();
	String targetObjNickName = st.nextToken();
	//st.nextToken();
	String targetGroupId = st.nextToken();
	String groupPwd = st.nextToken();
	String showOnlyToGroup = st.nextToken();
	String deletedByCreator = st.nextToken();
	String hiddenAtCreator = st.nextToken();
	String pinnedAtCreator = st.nextToken();
	String markedAsReadAtCreator = st.nextToken();
	String deletedAtTarget = st.nextToken();
	String hiddenAtTarget = st.nextToken();
	String pinnedAtTartget = st.nextToken();
	String markedAsReadAtTarget = st.nextToken();
	String creatorAsAnonymous = st.nextToken();
	String cretorIsPetugas = st.nextToken();
	String updtm = st.nextToken();
	
	String imgUsed = "";

%>
<!--  script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script -->
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/imgResize/resize.js"></script>
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/css/fbtxtbox.css" media="screen" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/css/imgResize/resize.css" media="screen" />
<script type="text/javascript">
$(function() 
{

$("#content1").focus(function()
{
$(this).animate({"height": "33px",}, "fast" );
$("#button_block1").slideDown("fast");
return false;
});

$("#cancel1").click(function()
{
$("#content1").animate({"height": "20px",}, "fast" );
$("#button_block1").slideUp("fast");
return false;
});

});
</script>



</head>
<!--  body style="overflow-x:hidden;" -->
<body>
<%
if(vSubTopik==null || vSubTopik.size()<1) {
	if(usrObjNickname.contains("OPERATOR")) {
		imgUsed = "opr.jpg";
	}
	else {
		if(usrKdjek.equalsIgnoreCase("L")) {
			imgUsed = "man.jpg";
		}
		else {
			if(usrKdjek.equalsIgnoreCase("P")) {
				imgUsed = "woman.jpg";
			}
			else {
				imgUsed = "unisex.jpg";
			}
		}
	}
	%>
	<table width="475px">
		<tr>
			<td width="25px">
				<div id="myDiv1">
	    			<img alt="Client Logo" title="Client Logo" src="<%=Constants.getRootWeb() %>/images/icon/<%= imgUsed%>" />
				</div>
			</td>
			
			<td width="425px" valign="middle" style="background:#eee;color:#369;text-align:center;">
				<form method="post" action="post.replyTopic">
				
				<input type="hidden" name="topikInfo" value="<%=topikInfo %>" />
				<textarea  id="content1" placeholder="Komentar Anda" name="isiReply"></textarea>
				<div id="button_block1">
					<input type="submit" id="button1" value=" Kirim "/>
					<input type="submit" id='cancel1' value=" Batal " />
				</div>
			</form>
			</td>
			<td width="25px">
			</td>
		</tr>
	</table>
	<%	
}
else {
	ListIterator li = vSubTopik.listIterator();
%>
<table width="475px">
<%	
	while(li.hasNext()) {
		String brs = (String)li.next();
		st = new StringTokenizer(brs,"||");
		String topikId = st.nextToken();
		String idSubTopik = st.nextToken();
		String contentReply = st.nextToken();
		String npmhsSender = st.nextToken();
		String nmmhsSender = st.nextToken();
		String anonymous = st.nextToken();
		String shownToCreatorOnly = st.nextToken();
		String commentorIsPetugas = st.nextToken();
		String updtmReply = st.nextToken();
		String postedAt = AskSystem.getPostdTimeInHrOrDay(AskSystem.getCurrentTimestamp(), Timestamp.valueOf(updtmReply));
		if(usrObjNickname.contains("OPERATOR")) {
			imgUsed = "opr.jpg";
		}
		else {
			if(usrKdjek.equalsIgnoreCase("L")) {
				imgUsed = "man.jpg";
			}
			else {
				if(usrKdjek.equalsIgnoreCase("P")) {
					imgUsed = "woman.jpg";
				}
				else {
					imgUsed = "unisex.jpg";
				}
			}
		}
%>
	<tr>
<%
		if(usrNpm.equalsIgnoreCase(npmhsSender)) {
%>	

		<td width="25px">
			<div id="myDiv1">
    			<img alt="Client Logo" title="Client Logo" src="<%=Constants.getRootWeb() %>/images/icon/<%=imgUsed %>" />
			</div>
		</td>
		<td width="425px" valign="middle" style="background:#eee;color:#369;text-align:left;">
			<div style="font-size:1em"><%=contentReply %></div>
			<div style="font-size:0.7em"><%=postedAt %></div>
		</td>
		<td width="25px">
		</td>
<%
		}
		else {
			if(imgUsed.equalsIgnoreCase("opr.jpg")) {
				imgUsed = "opr1.jpg";
			}
%>		
		<td width="25px">
		</td>
		<td width="425px" valign="middle" style="background:#eee;color:#369;text-align:right;">
			<div style="font-size:1em"><%=contentReply %></div>
			<div style="font-size:0.7em"><%=postedAt %></div>
		</td>
		<td width="25px">
			<div id="myDiv1">
    			<img alt="Client Logo" title="Client Logo" src="<%=Constants.getRootWeb() %>/images/icon/<%=imgUsed %>" />
			</div>
		</td>
<%
}
%>		
	</tr>
<%
	}

%>
	<tr>
		<td width="25px">
			<div id="myDiv1">
			<%
			if(imgUsed.contains("opr")) {
				imgUsed = "opr.jpg";
			}
			%>
				<img alt="Client Logo" title="Client Logo" src="<%=Constants.getRootWeb() %>/images/icon/<%=imgUsed %>" />
			</div>
		</td>
		
		<td width="425px" valign="middle" style="background:#eee;color:#369;text-align:center;">
			<form method="post" action="post.replyTopic">
				<input type="hidden" name="topikInfo" value="<%=topikInfo %>" />
				<textarea  id="content1" placeholder="Komentar Anda" name="isiReply"></textarea>
				<div id="button_block1">
					<input type="submit" id="button1" value=" Kirim "/>
					<input type="submit" id='cancel1' value=" Batal " />
				</div>
			</form>
		</td>
		<td width="25px">
		</td>
	</tr>
</table>
<%
}
%>					
</body>
</html>