<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="beans.dbase.SearchDb" %>
<%@ page import="beans.sistem.*" %>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
	
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!--  script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script -->
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/imgResize/resize.js"></script>
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/css/fbtxtbox.css" media="screen" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/css/imgResize/resize.css" media="screen" />
<!--  script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/resizeIframe/resize.js"></script -->
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
<%

	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String ObjGenderAndNickname = (String)session.getAttribute("ObjGenderAndNickname"); //usr kdjek dan nickname
	StringTokenizer st = new StringTokenizer(ObjGenderAndNickname,"__");
	String usrKdjek = st.nextToken();
	String usrObjNickname = st.nextToken();
	//is user = termasuk ke list nickname baa
	boolean userIsTheObject = false,match=false;
	String atMenu = request.getParameter("atMenu");
	//System.out.println("targetObjNickName@tampleteMainTopik. ATMENU = "+atMenu);
	st = new StringTokenizer(usrObjNickname,",");
	String[]listObjNicknameDomain = null;
	listObjNicknameDomain = Constants.getListObjectNickname(atMenu);
	//System.out.println("targetObjNickName@tampleteMainTopik.jsp");
	
	
	
	while(st.hasMoreTokens()&&!match) {
		String usrNick = st.nextToken();
		for(int i=0;i<listObjNicknameDomain.length&&!match;i++) {
			if(usrNick.equalsIgnoreCase(listObjNicknameDomain[i])) {
				userIsTheObject = true;
				match = true;
			}
		}
	}
	
	//System.out.println("kdjek,usrObjNickname="+usrKdjek+","+usrObjNickname);
	String xy = request.getParameter("xy");
	//long x = 0;
	//System.out.println("xy="+xy);
	st = new StringTokenizer(xy,",");
	String startAt = st.nextToken();
	long beginAt = Integer.valueOf(startAt).intValue();
	String quantity = st.nextToken();
	int totData = Integer.valueOf(quantity).intValue(); // bisa diganti dengan value dari Constants
	String targetObjNickName = request.getParameter("targetObjNickName");
	/*
	* targetObjNickName = obj nickname di baa yg bisa dikontak / dalam scope usr 
	*/
	if(targetObjNickName==null || Checker.isStringNullOrEmpty(targetObjNickName)) {
		targetObjNickName = (String) session.getAttribute("targetObjNickName");
		session.removeAttribute("targetObjNickName");
	}	
	boolean adaDataDibawah = false;
	boolean adaDataDiatas = false;
	SearchDb sdb = new SearchDb(validUsr.getNpm());
	//System.out.println("targetObjNickName=="+targetObjNickName);
	
	
	//?????????????????????????????????????????????????????		
	//menentukan lawan bicara 
	Vector vScopeLawanBicara = new Vector();
	ListIterator litmp = vScopeLawanBicara.listIterator();
	
	st = new StringTokenizer(targetObjNickName,"__");
	while(st.hasMoreTokens()) {
		litmp.add(st.nextToken());
	}
	
	 
	//litmp.add(targetObjNickName);
	//Vector v = sdb.getNonHiddenNonDeletedTopikForOrFromBaaBasedOnObjNickName(targetObjNickName,startAt,quantity);
	//Vector v = sdb.getMostRecentMsgWithLimit(usrObjNickname,vScopeObjNicknameOwnInbox, startAt, quantity);
	String unreadInfo = request.getParameter("unreadInfo");
	//String unreadInfo = request.getParameter("unreadInfo");
	//System.out.println("unreadInfo@tampleteTopik.jsp="+unreadInfo);
	boolean singleMode = false;
	String topik_idTopik1 = null;
	String topik_conten1 = null;
	String topik_npmhsCreator1 = null;
	String topik_nmmhsCreator1 = null;
	String topik_creatorObjId1 = null;
	String topik_creatorObjNickname1 = null;
	String topik_targetKdpst1 = null;
	String topik_targetNpmhs1 = null;
	String topik_targetSmawl1 = null;
	String topik_targetObjId1 = null;
	String topik_targetObjNickname1 = null;
	String topik_targetGroupId1 = null;
	String topik_groupPwd1 = null;
	String topik_shownToGroupOnly1 = null;
	String topik_deletedByCreator1 = null;
	String topik_hidenAtCreator1 = null;
	String topik_pinedAtCreator1 = null;
	String topik_markedAsReadAtCreator1 = null;
	String topik_deletedAtTarget1 = null;
	String topik_hidenAtTarget1 = null;
	String topik_pinedAtTarget1 = null;
	String topik_markedAsReasAsTarget1 = null;
	String topik_creatorAsAnonymous1 = null;
	String topik_creatorIsPetugas1 = null;
	String topik_updtm1 = null;
	String sub_id1 = null;
	String sub_idTopik1 = null;
	String sub_comment1 = null;
	String sub_npmhsSender1 = null;
	String sub_nmmhsSender1 = null;
	String sub_anonymousReply1 = null;
	String sub_shownToCreatorObly1 = null;
	String sub_commenterIsPetugas1 = null;
	String sub_markedAsReadAtCreator1 = null;
	String sub_markedAsReadAtSender1 = null;
	String sub_objNicknameSender1 = null;
	String sub_npmhsReceiver1 = null;
	String sub_nmmhsReceiver1 = null;
	String sub_objNicknameReceiver1 = null;
	String sub_updtm1 = null;

	if(unreadInfo!=null && !Checker.isStringNullOrEmpty(unreadInfo)) {
		singleMode = true;
		st = new StringTokenizer(unreadInfo,"__");
		topik_idTopik1 = st.nextToken();
		topik_conten1 = st.nextToken();
		topik_npmhsCreator1 = st.nextToken();
		topik_nmmhsCreator1 = st.nextToken();
		topik_creatorObjId1 = st.nextToken();
		topik_creatorObjNickname1 = st.nextToken();
		topik_targetKdpst1 = st.nextToken();
		topik_targetNpmhs1 = st.nextToken();
		topik_targetSmawl1 = st.nextToken();
		topik_targetObjId1 = st.nextToken();
		topik_targetObjNickname1 = st.nextToken();
		topik_targetGroupId1 = st.nextToken();
		topik_groupPwd1 = st.nextToken();
		topik_shownToGroupOnly1 = st.nextToken();
		topik_deletedByCreator1 = st.nextToken();
		topik_hidenAtCreator1 = st.nextToken();
		topik_pinedAtCreator1 = st.nextToken();
		topik_markedAsReadAtCreator1 = st.nextToken();
		topik_deletedAtTarget1 = st.nextToken();
		topik_hidenAtTarget1 = st.nextToken();
		topik_pinedAtTarget1 = st.nextToken();
		topik_markedAsReasAsTarget1 = st.nextToken();
		topik_creatorAsAnonymous1 = st.nextToken();
		topik_creatorIsPetugas1 = st.nextToken();
		topik_updtm1 = st.nextToken();
		sub_id1 = st.nextToken();
		sub_idTopik1 = st.nextToken();
		sub_comment1 = st.nextToken();
		sub_npmhsSender1 = st.nextToken();
		sub_nmmhsSender1 = st.nextToken();
		sub_anonymousReply1 = st.nextToken();
		sub_shownToCreatorObly1 = st.nextToken();
		sub_commenterIsPetugas1 = st.nextToken();
		sub_markedAsReadAtCreator1 = st.nextToken();
		sub_markedAsReadAtSender1 = st.nextToken();
		sub_objNicknameSender1 = st.nextToken();
		sub_npmhsReceiver1 = st.nextToken();
		sub_nmmhsReceiver1 = st.nextToken();
		sub_objNicknameReceiver1 = st.nextToken();
		sub_updtm1 = st.nextToken();
		
		
		//overiding menentukan lawanbicara diatas
		boolean usrIsTarget = false;
		vScopeLawanBicara = new Vector();
		litmp = vScopeLawanBicara.listIterator();
		
		st = new StringTokenizer(usrObjNickname,",");
		while(st.hasMoreTokens()&&!usrIsTarget) {
			String usrNick = st.nextToken();
			if(targetObjNickName.contains(usrNick)) {
				usrIsTarget=true;
			}
		}
		if(usrIsTarget) {
			litmp.add(topik_creatorObjNickname1);
		}
		else {
			st = new StringTokenizer(targetObjNickName,"__");
			while(st.hasMoreTokens()) {
				litmp.add(st.nextToken());
			}
		}	
		
	}
	
	
	
	
	Vector v = null;
	if(unreadInfo==null || Checker.isStringNullOrEmpty(unreadInfo)) { //unread disini artinya bukan belum terbaca tapi link per satuan dari dashContacUs
		//System.out.println("is user the object = "+userIsTheObject);
		//System.out.println("==objNickNameUsr "+usrObjNickname);
		if(userIsTheObject) {
			//System.out.println("user is obj0");
			//get msg yg baa create
			//v = sdb. getMostRecentMsgDistinctTopikIdWithRange(usrObjNickname,vScopeLawanBicara,beginAt,totData+1);
			v = sdb.getMostRecentMsgWhereUsrIsTheObjectWithRange(usrObjNickname,vScopeLawanBicara,beginAt,totData+1);
			
		}
		else {
			//System.out.println("user is not obj0");
			//v = sdb.getMostRecentMsgWhereUsrIsNotTheObjectWithRange
			//v = sdb. getMostRecentMsgDistinctTopikIdWithRangeUsrIsNotTarget(usrObjNickname,vScopeLawanBicara,beginAt,totData+1);
			v = sdb.getMostRecentMsgWhereUsrIsNotTheObjectWithRange(usrObjNickname,vScopeLawanBicara,beginAt,totData+1);
		}
	}
	else {
		//long x = sdb.getValueIndexXMostRecentMsgDistinctTopikIdWithRangeAndStartAtTargetTopikId(usrObjNickname,vScopeObjNicknameOwnInbox,topik_idTopik1);
		/*
		* fungsi dibawah mungkin bisa direvisi ulang, biar lebih specifik anatara user dan lawam bicara
		*/
		if(userIsTheObject) {
			//System.out.println("user is obj1");
			//System.out.println("usrObjNickname-->"+usrObjNickname);
			beginAt = sdb.getValueIndexXMostRecentMsgTargetedTopikIdUsrIsTheObjectWithRange(usrObjNickname,vScopeLawanBicara,topik_idTopik1);
			//beginAt = beginAt -1;
			startAt = ""+beginAt;
			//System.out.println("startAt=="+startAt);
			v = sdb.getMostRecentMsgWhereUsrIsTheObjectWithRange(usrObjNickname,vScopeLawanBicara,beginAt,totData+1);
			
		}
		else {
			//System.out.println("user is not obj1 usrObjNickname="+usrObjNickname);
			beginAt = sdb.getValueIndexXMostRecentMsgTargetedTopikIdUsrIsNotTheObjectWithRange(usrObjNickname,vScopeLawanBicara,topik_idTopik1);
			//beginAt = beginAt -1;
			startAt = ""+beginAt;
			//System.out.println("startAt=="+startAt);
			//jangan lupa index = -1
			v = sdb.getMostRecentMsgWhereUsrIsNotTheObjectWithRange(usrObjNickname,vScopeLawanBicara,Long.valueOf(startAt).longValue(),totData+1);
		}
		/*===============OLD METHOD==================
		long x = sdb.getValueIndexXMostRecentMsgDistinctTopikIdWithRangeAndStartAtTargetTopikId(usrObjNickname,vScopeLawanBicara,topik_idTopik1);
		totData = Constants.getQuantityMsgShownPerPage();
		beginAt = x;
		if(userIsTheObject) {
			v = sdb.getMostRecentMsgDistinctTopikIdWithRange(usrObjNickname,vScopeLawanBicara,beginAt,totData+1);
		}
		else {
			v = sdb.getMostRecentMsgDistinctTopikIdWithRangeUsrIsNotTarget(usrObjNickname,vScopeLawanBicara,beginAt,totData+1);
		}
		===============OLD METHOD==================*/
	}
	//ListIterator li = v.listIterator();
	//while(li.hasNext()) {
	//	String brs = (String)li.next();
		//System.out.println("baru="+brs);
	//}
	
	if(v!=null && v.size()>totData) {
		adaDataDibawah = true;
	}
	if(beginAt>0) {
		adaDataDiatas = true;
	}
	
	
	//System.out.println("v size = "+v.size());
	//System.out.println("adaDataDibawah = "+adaDataDibawah);
	//System.out.println("adaDataDiatas = "+adaDataDiatas);
	

%>
</head>
<body>
<%
if(adaDataDiatas) {
	//System.out.println("adaDataDiAtas xy = "+beginAt+","+totData);
	long tmp = beginAt-(totData);
	if(tmp<0) {
		tmp = 0;
	}
	//System.out.println("atas="+tmp+","+(totData));
%>
	<div align="center">
			<a href="tampleteTopic.jsp?atMenu=<%=atMenu %>&targetObjNickName=<%=targetObjNickName %>&xy=<%=tmp%>,<%=totData%>"><img alt="Client Logo" title="Client Logo" src="<%=Constants.getRootWeb() %>/images/icon/upArrow2.jpg" /></a>
	</div>
<%		
}
else {
%>
<br/><br/><br/>
<%	
}
boolean singleMatch = false;
if(v!=null && v.size()>0) {
	int counter = 0;
%>
<%	
	ListIterator li = v.listIterator();
	//while(li.hasNext() && counter<(totData) && !singleMatch) {
	while(li.hasNext() && counter<(totData) ) {
	
		counter++;
		//System.out.println("vsiss="+v.size());
		String brs = (String)li.next();
		st = new StringTokenizer(brs,"||");
		String idTopik = st.nextToken();
		//System.out.println("idTopik="+idTopik);
		String content = st.nextToken();
		String npmhsCreator = st.nextToken();
		String nmmhsCreator = st.nextToken();
		String creatorObjId = st.nextToken();
		String creatorObjNickName = st.nextToken();
		String targetKdpst = st.nextToken();
		String targetNpmhs = st.nextToken();
		String targetSmawl = st.nextToken();
		String targetObjId = st.nextToken();
		//String targetObjNickName = st.nextToken();
		st.nextToken();
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
		
		String postedAt = AskSystem.getPostdTimeInHrOrDay(AskSystem.getCurrentTimestamp(), Timestamp.valueOf(updtm));
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
		//if((singleMode && idTopik.equalsIgnoreCase(topik_idTopik1)) || !singleMode ) {
			//if(singleMode) {
			//	singleMatch = true;
			//	System.out.println("singleMatch="+singleMatch);
			//}
		
%>
<table width="557px" style="border-collapse:collapse;border: 1px solid #F0F0F0;outline: 1px solid #F0F0F0;">
<%
		if(npmhsCreator.equalsIgnoreCase(validUsr.getNpm())) {
			
		
%>
	<tr >
		<td width="25px" rowspan="3" style="height:50px" valign="top">
			<div id="myDiv">
    			<img alt="Client Logo" title="Client Logo" src="<%=Constants.getRootWeb() %>/images/icon/<%=imgUsed %>" />
			</div>
		</td>
		<td width="475px" valign="middle" style="height:50px">
		<%
		//	if(npmhsCreator.equalsIgnoreCase(validUsr.getNpm())) {
			if(false) {	
		%>
			<div style="font-size:0.8em;text-align:right"><a href="proses.hapusTopic?idTopik=<%=idTopik %>&npmhsCreator=<%=npmhsCreator %>&targetObjNickName=<%=targetObjNickName %>&xy=<%=xy%>" style="color:red">Hapus</a>&nbsp&nbsp&nbsp</div>
		<%
			}
			if(usrObjNickname.contains("OPERATOR")) {
		%>	
			<div style="font-size:0.8em;text-align:left"><%= nmmhsCreator%></div>
		<%		
			}
		%>
			<div style="font-size:0.8em;text-align:left"><%= postedAt%></div>
			<div style="font-size:1em;font-style:bold;text-align:left;color:#082081">
			<!--  div style="font-size:1.25em;font-style:bold"-->
			<%
			if(content.startsWith("BcFromOperator-")) {
				out.print(content.substring(15, content.length())); 
			}
			else {
				out.print(content);
			}
			%></div>
		</td>
	</tr>
<%
		}
		else {
			if(imgUsed.equalsIgnoreCase("opr.jpg")) {
				imgUsed = "opr1.jpg";
			}
			if(creatorObjNickName.contains("OPERATOR")) {
				imgUsed = "opr1.jpg";
			}
%>	
	<tr >
		
		<td width="475px" valign="middle" style="height:50px">
		<%
		//	if(npmhsCreator.equalsIgnoreCase(validUsr.getNpm())) {
			if(false) {	
		%>
			<div style="font-size:0.8em;text-align:right"><a href="proses.hapusTopic?idTopik=<%=idTopik %>&npmhsCreator=<%=npmhsCreator %>&targetObjNickName=<%=targetObjNickName %>&xy=<%=xy%>" style="color:red">Hapus</a>&nbsp&nbsp&nbsp</div>
		<%
			}
			if(usrObjNickname.contains("OPERATOR")) {
				%>	
			<div style="font-size:0.8em;text-align:right"><%= npmhsCreator.replace("null","")%> - <%= nmmhsCreator%> - <%= creatorObjNickName.replace("OPERATOR","")%></div>
				<%		
			}
			else {
			/*
			*	updated 4 april 2014
			*/
			//msg creator operator 
				%>	
				<div style="font-size:0.8em;text-align:right"><%= creatorObjNickName.replace("OPERATOR","")%></div>
				<%	
			}
		%>
			<div style="font-size:0.8em;text-align:right"><%= postedAt%></div>
			<div style="font-size:1em;font-style:bold;text-align:left;color:#082081">
			<%
			if(content.startsWith("BcFromOperator-")) {
				out.print(content.substring(15, content.length())); 
			}
			else {
				out.print(content);
			}
			%></div>
		</td>
		<td width="25px" rowspan="3" style="height:50px" valign="top">
			<div id="myDiv">
    			<img alt="Client Logo" title="Client Logo" src="<%=Constants.getRootWeb() %>/images/icon/<%=imgUsed %>" />
			</div>
		</td>
	</tr>
<%
		}
if(!creatorObjNickName.equalsIgnoreCase("tamu") && !content.startsWith("BcFromOperator-"))  {		
%>
	<tr>
		<td>
			<iframe name="rf" id="replyFrame" src="proces.prepTampleteReply?topikInfo=<%=brs %>" scrolling="yes" frameborder="0" style="border:none; overflow-x:hidden; overflow-y:hidden; width:505px; height:100%;" allowTransparency="true"></iframe>
		</td>
	</tr>
<%
}
%>	
</table>	
<%
	}	
	//if(adaDataDibawah && singleMatch) {
	if(adaDataDibawah) {	
		//System.out.println("adaDataDibawah xy = "+(beginAt+(totData))+","+totData);
%>
		<div align="center">
    			<a href="tampleteTopic.jsp?atMenu=<%=atMenu %>&targetObjNickName=<%=targetObjNickName %>&xy=<%=beginAt+(totData)%>,<%=totData%>"><img alt="Client Logo" title="Client Logo" src="<%=Constants.getRootWeb() %>/images/icon/downArrow2.jpg" /></a>
		</div>
<%		
	}
}
//if(!singleMatch) {
//	out.print("no match id topik="+topik_idTopik1);
//}
%>	
	
</body>
</html>