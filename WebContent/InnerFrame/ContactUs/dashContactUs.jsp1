<!DOCTYPE html>
<!--  
-	
-
-
-->

<head>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.sistem.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
	
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String usrObjNickname = validUsr.getObjNickNameGivenObjId();
	//System.out.println("usrObjNickname11="+usrObjNickname);
	Vector vUnread = (Vector)session.getAttribute("vUnread");
	Vector vRecent = (Vector)session.getAttribute("vRecent");
	System.out.println("vUnread length = "+vUnread.size());
	String sta_index = request.getParameter("sta_index");
	String range = request.getParameter("range");
	boolean ada_unread_diatas = false;
	boolean ada_unread_dibawah = false;
	if(Integer.parseInt(sta_index)>0) {
		ada_unread_diatas = true;
	}
	if(vUnread.size()>Integer.parseInt(range)) {
		ada_unread_dibawah = true;
	}
	//====PERHATIAN=========
	//remove data di last index krn memang sizenya ditambah satu 
	//untuk keperluan pengecekan apa ada data dibawah KECUALI BILA vSize = range
	if(vUnread.size()>Integer.parseInt(range)) {
		vUnread.remove(vUnread.size()-1);
	}
	System.out.println("vUnread length after remove= "+vUnread.size());
	
	//untuk tampilan most recent maka di reverse, SUDAH TIDAK DIREVERSE LAGI
	if(vUnread!=null) {
		//Collections.reverse(vUnread);
	}
	if(vRecent!=null) {
		//Collections.reverse(vRecent);
	}
	//System.out.println("vUnread & vRecent="+vUnread.size()+","+vRecent.size());
	String tdBgColor = "#f4f4f4";
	String tableBgColoe = "#f4f4f4";
	String txtColor = "#369";
	String txtColorUnread = "#DD0404";
	String unread = null;
	
	String show_msg_mode = request.getParameter("show");
%>


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
		<br/>
	<%
	System.out.println("sta_index="+sta_index);
	System.out.println("range="+range);
	System.out.println("show_msg_mode="+show_msg_mode);
	%>	
	form = <%=sta_index %> &range= <%=range %> &show= <%=show_msg_mode%>
	<form action="get.msgInbox?sta_index=<%=sta_index %>&range=<%=range %>" method="post">
		Pesan&nbsp&nbsp
		<%
		if(show_msg_mode!=null && show_msg_mode.equalsIgnoreCase("unread")) {
		%>
	   	<input type="radio" name="show" value="unread" onchange='this.form.submit()' checked="checked">Belum Terbaca
		<%	
		}
		else {
		%>
   		<input type="radio" name="show" value="unread" onchange='this.form.submit()' >Belum Terbaca
		<%
		}
		%>
		&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
		<%
		if(show_msg_mode!=null && show_msg_mode.equalsIgnoreCase("recent")) {
		%>
		
		<input type="radio" name="show" value="recent" onchange='this.form.submit()' checked="checked">Terkini
		<%	
		}
		else {
		%>
		<input type="radio" name="show" value="recent" onchange='this.form.submit()'>Terkini
		<%
		}
		%>
		<br/>
		<%
		if(show_msg_mode!=null &&  show_msg_mode.equalsIgnoreCase("unread")) {
		// unread page
			if((vUnread!=null && vUnread.size()>0)||(vRecent!=null && vRecent.size()>0)) {
	
				if(usrObjNickname.contains("OPERATOR")) {
%>
			<div style="background:white;font-size:0.8em;font-style:italic;color:<%=txtColor%>;text-align:center">
			* Note: setiap pesan harus dibalas, walaupun pesan tidak membutuhkan respon balik, harap dijawab dengan 'terima kasih',dll.
			</div>
		<br/>
<%
				}
				if(ada_unread_diatas) {
					
					int tmp_sta = Integer.parseInt(sta_index);
					int tmp_range = Integer.parseInt(range);
					int nuStaIndex = tmp_sta - tmp_range;
					if(nuStaIndex<0) {
						nuStaIndex=0;
					}
%>
	<div align="center">
			<a href="get.msgInbox?sta_index=<%=nuStaIndex %>&range=<%=range %>&show=unread">
			<img alt="Client Logo" title="Client Logo" src="<%=Constants.getRootWeb() %>/images/icon/upArrow2.jpg" /></a>
	</div>
<%		
				}
%>		
		<table align="center" style="background:<%=tableBgColoe %>;color:<%=txtColor %>;width:500px">
<%	
				if(vUnread!=null && vUnread.size()>0) {
					ListIterator liu = vUnread.listIterator();
					while(liu.hasNext()) {
						unread = (String) liu.next();
						StringTokenizer st = new StringTokenizer(unread,"||");
						String topik_idTopik = st.nextToken();
						String topik_conten = st.nextToken();
						String topik_npmhsCreator = st.nextToken();
						String topik_nmmhsCreator = st.nextToken();
						String topik_creatorObjId = st.nextToken();
						String topik_creatorObjNickname = st.nextToken();
						String topik_targetKdpst = st.nextToken();
						String topik_targetNpmhs = st.nextToken();
						String topik_targetSmawl = st.nextToken();
						String topik_targetObjId = st.nextToken();
						String topik_targetObjNickname = st.nextToken();
						String topik_targetGroupId = st.nextToken();
						String topik_groupPwd = st.nextToken();
						String topik_shownToGroupOnly = st.nextToken();
						String topik_deletedByCreator = st.nextToken();
						String topik_hidenAtCreator = st.nextToken();
						String topik_pinedAtCreator = st.nextToken();
						String topik_markedAsReadAtCreator = st.nextToken();
						String topik_deletedAtTarget = st.nextToken();
						String topik_hidenAtTarget = st.nextToken();
						String topik_pinedAtTarget = st.nextToken();
						String topik_markedAsReasAsTarget = st.nextToken();
						String topik_creatorAsAnonymous = st.nextToken();
						String topik_creatorIsPetugas = st.nextToken();
						String topik_updtm = st.nextToken();
						String sub_id = st.nextToken();
						String sub_idTopik = st.nextToken();
						String sub_comment = st.nextToken();
						String sub_npmhsSender = st.nextToken();
						String sub_nmmhsSender = st.nextToken();
						String sub_anonymousReply = st.nextToken();
						String sub_shownToCreatorObly = st.nextToken();
						String sub_commenterIsPetugas = st.nextToken();
						String sub_markedAsReadAtCreator = st.nextToken();
						String sub_markedAsReadAtSender = st.nextToken();
						String sub_objNicknameSender = st.nextToken();
						String sub_npmhsReceiver = st.nextToken();
						String sub_nmmhsReceiver = st.nextToken();
						String sub_objNicknameReceiver = st.nextToken();
						String sub_updtm = st.nextToken();
						String postedAt = null;
						if(sub_updtm.equalsIgnoreCase("null")) {
							postedAt = AskSystem.getPostdTimeInHrOrDay(AskSystem.getCurrentTimestamp(), Timestamp.valueOf(topik_updtm));
						}
						else {
							postedAt = AskSystem.getPostdTimeInHrOrDay(AskSystem.getCurrentTimestamp(), Timestamp.valueOf(sub_updtm));
						}	
		
			%>
			<tr>
			<%
						if(!sub_updtm.equalsIgnoreCase("null")) {
							//msg ada di subtopik table
			%>			
				<td>
					<%
							unread = Tool.gantiSpecialChar(unread);
					%>
					<a href="proses.redirectUnreadMsg?infoUnread=<%=""+unread.replace("||","__")%>">
					<div style="background:white;font-size:0.9em;font-weight:bold;color:<%=txtColor%>">
					<%
					//jika anda operator maka tampilkan nama dan nickname
							if(usrObjNickname.contains("OPERATOR")) {
								out.print(Tool.capFirstLetterInWord(sub_nmmhsSender)+" ("+sub_objNicknameSender.replace("OPERATOR ", "")+")");
							}
							else {
								out.print(sub_objNicknameSender.replace("OPERATOR ", ""));
							}
					%>
					</div> 
					<div style="background:white;font-size:0.8em;font-style:italic;color:<%=txtColor%>">&nbsp&nbspmengirim komen <b>"
					<%
							if(sub_comment.length()>57) {
								out.print(sub_comment.substring(0, 55)+" ...");
							}
							else {
								out.print(sub_comment);
							}
					%>"</b> <br/> @ topik <b>"
					<%
							if(topik_conten.length()>79) {
								out.print(topik_conten.substring(0, 77)+" ...");
							}
							else {
								out.print(topik_conten);
							}
					%>"</b></div>
					<div style="background:white;font-size:0.7em;font-weight:bold;text-align:right;color:<%=txtColor%>"><%=postedAt %></div>
					</a>
				</td>
			<%
						}
						else {
							//msg ada di topik table - selalu tampilkan nama pengirim kecualicreatonya operator (pengumuman)
				%>			
				<td>
		<%
							unread = Tool.gantiSpecialChar(unread);
		%>
					<a href="proses.redirectUnreadMsg?infoUnread=<%=""+unread.replace("||","__")%>">
					<div style="background:white;font-size:0.9em;font-weight:bold;color:<%=txtColor%>">
		<%
		//jika objectnickname start with 'operator' maka tampilkan nama pengirim kalo tidak tampilkan nama nickname Obj
		
							if(usrObjNickname.contains("OPERATOR")) {
								out.print(Tool.capFirstLetterInWord(topik_nmmhsCreator)+" ("+topik_creatorObjNickname.replace("OPERATOR ", "")+")");
							}
							else {
								if(topik_creatorObjNickname.contains("OPERATOR") && !usrObjNickname.contains("OPERATOR")) {
									out.print(topik_creatorObjNickname.replace("OPERATOR ", ""));
								}
							}
			//out.print(Tool.capFirstLetterInWord(topik_nmmhsCreator)+" ("+topik_creatorObjNickname.replace("OPERATOR ", "")+")");
							if(topik_npmhsCreator!=null && !Checker.isStringNullOrEmpty(topik_npmhsCreator) && usrObjNickname.contains("OPERATOR")) {
		%>
					</div><div style="background:white;font-size:0.6em;font-style:italic;color:<%=txtColor%>">NPM : <%=topik_npmhsCreator %></div>
		<%
							}
		%> 
					<div style="background:white;font-size:0.8em;font-style:italic;color:<%=txtColor%>">&nbsp&nbsp&nbsp&nbspmengirim komen <b>"
		<%
							if(topik_conten.startsWith("BcFromOperator-")) {
								topik_conten=topik_conten.replaceAll("BcFromOperator-", "");
							}
							if(topik_conten.length()>157) {
								out.print(topik_conten.substring(0, 157)+" ...");
							}
							else {
								out.print(topik_conten);
							}
				%>"</b> </div>
					<div style="background:white;font-size:0.7em;font-weight:bold;text-align:right;color:<%=txtColor%>"><%=postedAt %></div>
					</a>
				</td>
<%
						}
%>		
			</tr>			
<%		
					}
%>
		</table>
   	</form>

<%	
					if(ada_unread_dibawah) {	
						int tmp_sta = Integer.parseInt(sta_index);
						int tmp_range = Integer.parseInt(range);
						int nuStaIndex = tmp_sta+tmp_range;;
						//if(nuStaIndex<0) {
						//	nuStaIndex=
						//}
%>
	<div align="center">
			<a href="get.msgInbox?sta_index=<%=nuStaIndex %>&range=<%=range %>&show=unread"><img alt="Client Logo" title="Client Logo" src="<%=Constants.getRootWeb() %>/images/icon/downArrow2.jpg" /></a>
	</div>
<%		
					}
				}	
			}
		}
		else if(show_msg_mode!=null &&  show_msg_mode.equalsIgnoreCase("recent")) {
			if(vRecent!=null && vRecent.size()>0) {
				
				if(usrObjNickname.contains("OPERATOR")) {
%>
			<div style="background:white;font-size:0.8em;font-style:italic;color:<%=txtColor%>;text-align:center">
			* Note: setiap pesan harus dibalas, walaupun pesan tidak membutuhkan respon balik, harap dijawab dengan 'terima kasih',dll.
			</div>
		<br/>
<%
				}
				if(false) {
					
					int tmp_sta = Integer.parseInt(sta_index);
					int tmp_range = Integer.parseInt(range);
					int nuStaIndex = tmp_sta - tmp_range;
					if(nuStaIndex<0) {
						nuStaIndex=0;
					}
%>
	<div align="center">
			<a href="get.msgInbox?sta_index=<%=nuStaIndex %>&range=<%=range %>&show=unread">
			<img alt="Client Logo" title="Client Logo" src="<%=Constants.getRootWeb() %>/images/icon/upArrow2.jpg" /></a>
	</div>
<%		
				}
%>		
		<table align="center" style="background:<%=tableBgColoe %>;color:<%=txtColor %>;width:500px">
<%	
				if(vRecent!=null && vRecent.size()>1) {
					Vector vSorted = new Vector();
					ListIterator lis = vSorted.listIterator();
					ListIterator lir = vRecent.listIterator();
		
					while(lir.hasNext()) {
						String recent = (String) lir.next();
						StringTokenizer st = new StringTokenizer(recent,"||");
						String timeForSort = st.nextToken();
						String topik_idTopik = st.nextToken();
						String topik_conten = st.nextToken();
						String topik_npmhsCreator = st.nextToken();
						String topik_nmmhsCreator = st.nextToken();
						String topik_creatorObjId = st.nextToken();
						String topik_creatorObjNickname = st.nextToken();
						String topik_targetKdpst = st.nextToken();
						String topik_targetNpmhs = st.nextToken();
						String topik_targetSmawl = st.nextToken();
						String topik_targetObjId = st.nextToken();
						String topik_targetObjNickname = st.nextToken();
						String topik_targetGroupId = st.nextToken();
						String topik_groupPwd = st.nextToken();
						String topik_shownToGroupOnly = st.nextToken();
						String topik_deletedByCreator = st.nextToken();
						String topik_hidenAtCreator = st.nextToken();
						String topik_pinedAtCreator = st.nextToken();
						String topik_markedAsReadAtCreator = st.nextToken();
						String topik_deletedAtTarget = st.nextToken();
						String topik_hidenAtTarget = st.nextToken();
						String topik_pinedAtTarget = st.nextToken();
						String topik_markedAsReasAsTarget = st.nextToken();
						String topik_creatorAsAnonymous = st.nextToken();
						String topik_creatorIsPetugas = st.nextToken();
						String topik_updtm = st.nextToken();
						String sub_id = st.nextToken();
						String sub_idTopik = st.nextToken();
						String sub_comment = st.nextToken();
						String sub_npmhsSender = st.nextToken();
						String sub_nmmhsSender = st.nextToken();
						String sub_anonymousReply = st.nextToken();
						String sub_shownToCreatorObly = st.nextToken();
						String sub_commenterIsPetugas = st.nextToken();
						String sub_markedAsReadAtCreator = st.nextToken();
						String sub_markedAsReadAtSender = st.nextToken();
						String sub_objNicknameSender = st.nextToken();
						String sub_npmhsReceiver = st.nextToken();
						String sub_nmmhsReceiver = st.nextToken();
						String sub_objNicknameReceiver = st.nextToken();
						String sub_updtm = st.nextToken();
						String tmp = null;
						if(sub_updtm==null || Checker.isStringNullOrEmpty(sub_updtm)) {
							tmp = topik_updtm+"||"+topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+sub_id+"||"+sub_idTopik+"||"+sub_comment+"||"+sub_npmhsSender+"||"+sub_nmmhsSender+"||"+sub_anonymousReply+"||"+sub_shownToCreatorObly+"||"+sub_commenterIsPetugas+"||"+sub_markedAsReadAtCreator+"||"+sub_markedAsReadAtSender+"||"+sub_objNicknameSender+"||"+sub_npmhsReceiver+"||"+sub_nmmhsReceiver+"||"+sub_objNicknameReceiver+"||"+sub_updtm;
						}
						else {
							tmp = sub_updtm+"||"+topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+sub_id+"||"+sub_idTopik+"||"+sub_comment+"||"+sub_npmhsSender+"||"+sub_nmmhsSender+"||"+sub_anonymousReply+"||"+sub_shownToCreatorObly+"||"+sub_commenterIsPetugas+"||"+sub_markedAsReadAtCreator+"||"+sub_markedAsReadAtSender+"||"+sub_objNicknameSender+"||"+sub_npmhsReceiver+"||"+sub_nmmhsReceiver+"||"+sub_objNicknameReceiver+"||"+sub_updtm;
						}
						lis.add(tmp);
					}
					/*
					Comparator comparator = Collections.reverseOrder();
					Collections.sort(vSorted,comparator);
		
					lis = vSorted.listIterator();
		
					vRecent = new Vector();
					lir = vRecent.listIterator();
					while(lis.hasNext()) {
						String recent = (String) lis.next();
						StringTokenizer st = new StringTokenizer(recent,"||");
						st.nextToken();
						String topik_idTopik = st.nextToken();
						String topik_conten = st.nextToken();
						String topik_npmhsCreator = st.nextToken();
						String topik_nmmhsCreator = st.nextToken();
						String topik_creatorObjId = st.nextToken();
						String topik_creatorObjNickname = st.nextToken();
						String topik_targetKdpst = st.nextToken();
						String topik_targetNpmhs = st.nextToken();
						String topik_targetSmawl = st.nextToken();
						String topik_targetObjId = st.nextToken();
						String topik_targetObjNickname = st.nextToken();
						String topik_targetGroupId = st.nextToken();
						String topik_groupPwd = st.nextToken();
						String topik_shownToGroupOnly = st.nextToken();
						String topik_deletedByCreator = st.nextToken();
						String topik_hidenAtCreator = st.nextToken();
						String topik_pinedAtCreator = st.nextToken();
						String topik_markedAsReadAtCreator = st.nextToken();
						String topik_deletedAtTarget = st.nextToken();
						String topik_hidenAtTarget = st.nextToken();
						String topik_pinedAtTarget = st.nextToken();
						String topik_markedAsReasAsTarget = st.nextToken();
						String topik_creatorAsAnonymous = st.nextToken();
						String topik_creatorIsPetugas = st.nextToken();
						String topik_updtm = st.nextToken();
						String sub_id = st.nextToken();
						String sub_idTopik = st.nextToken();
						String sub_comment = st.nextToken();
						String sub_npmhsSender = st.nextToken();
						String sub_nmmhsSender = st.nextToken();
						String sub_anonymousReply = st.nextToken();
						String sub_shownToCreatorObly = st.nextToken();
						String sub_commenterIsPetugas = st.nextToken();
						String sub_markedAsReadAtCreator = st.nextToken();
						String sub_markedAsReadAtSender = st.nextToken();
						String sub_objNicknameSender = st.nextToken();
						String sub_npmhsReceiver = st.nextToken();
						String sub_nmmhsReceiver = st.nextToken();
						String sub_objNicknameReceiver = st.nextToken();
						String sub_updtm = st.nextToken();
						String tmp = topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+sub_id+"||"+sub_idTopik+"||"+sub_comment+"||"+sub_npmhsSender+"||"+sub_nmmhsSender+"||"+sub_anonymousReply+"||"+sub_shownToCreatorObly+"||"+sub_commenterIsPetugas+"||"+sub_markedAsReadAtCreator+"||"+sub_markedAsReadAtSender+"||"+sub_objNicknameSender+"||"+sub_npmhsReceiver+"||"+sub_nmmhsReceiver+"||"+sub_objNicknameReceiver+"||"+sub_updtm;
						lir.add(tmp);
					}
					*/
				//}
//======end sorting==================
					lir = vRecent.listIterator();
					while(lir.hasNext()) {
						String recent = (String) lir.next();
						StringTokenizer st = new StringTokenizer(recent,"||");
						String topik_idTopik = st.nextToken();
						String topik_conten = st.nextToken();
						String topik_npmhsCreator = st.nextToken();
						String topik_nmmhsCreator = st.nextToken();
						String topik_creatorObjId = st.nextToken();
						String topik_creatorObjNickname = st.nextToken();
						String topik_targetKdpst = st.nextToken();
						String topik_targetNpmhs = st.nextToken();
						String topik_targetSmawl = st.nextToken();
						String topik_targetObjId = st.nextToken();
						String topik_targetObjNickname = st.nextToken();
						String topik_targetGroupId = st.nextToken();
						String topik_groupPwd = st.nextToken();
						String topik_shownToGroupOnly = st.nextToken();
						String topik_deletedByCreator = st.nextToken();
						String topik_hidenAtCreator = st.nextToken();
						String topik_pinedAtCreator = st.nextToken();
						String topik_markedAsReadAtCreator = st.nextToken();
						String topik_deletedAtTarget = st.nextToken();
						String topik_hidenAtTarget = st.nextToken();
						String topik_pinedAtTarget = st.nextToken();
						String topik_markedAsReasAsTarget = st.nextToken();
						String topik_creatorAsAnonymous = st.nextToken();
						String topik_creatorIsPetugas = st.nextToken();
						String topik_updtm = st.nextToken();
						String sub_id = st.nextToken();
						String sub_idTopik = st.nextToken();
						String sub_comment = st.nextToken();
						String sub_npmhsSender = st.nextToken();
						String sub_nmmhsSender = st.nextToken();
						String sub_anonymousReply = st.nextToken();
						String sub_shownToCreatorObly = st.nextToken();
						String sub_commenterIsPetugas = st.nextToken();
						String sub_markedAsReadAtCreator = st.nextToken();
						String sub_markedAsReadAtSender = st.nextToken();
						String sub_objNicknameSender = st.nextToken();
						String sub_npmhsReceiver = st.nextToken();
						String sub_nmmhsReceiver = st.nextToken();
						String sub_objNicknameReceiver = st.nextToken();
						String sub_updtm = st.nextToken();
						String postedAt = null;
						if(sub_updtm.equalsIgnoreCase("null")) {
							postedAt = AskSystem.getPostdTimeInHrOrDay(AskSystem.getCurrentTimestamp(), Timestamp.valueOf(topik_updtm));
						}
						else {
							postedAt = AskSystem.getPostdTimeInHrOrDay(AskSystem.getCurrentTimestamp(), Timestamp.valueOf(sub_updtm));
						}
%>
				<tr>
<%
						if(!sub_updtm.equalsIgnoreCase("null")) {
%>			
					<td>
<%
							recent = Tool.gantiSpecialChar(recent);
%>
						<a href="proses.redirectUnreadMsg?infoMostRecent=<%=""+recent.replace("||","__")%>">
						<div style="background:#f4f4f4;font-size:0.9em;font-weight:bold;color:<%=txtColor%>">
<%

							if(usrObjNickname.contains("OPERATOR")) {
								out.print(Tool.capFirstLetterInWord(sub_nmmhsSender)+" ("+sub_objNicknameSender.replace("OPERATOR ", "")+")");
							}
							else {
								out.print(sub_objNicknameSender.replace("OPERATOR ", ""));
							} 
%>
						</div> 
						<div style="background:#f4f4f4;font-size:0.8em;font-style:italic;color:<%=txtColor%>">&nbsp&nbspmengirim komen <b>"
<%
							if(sub_comment.length()>57) {
								out.print(sub_comment.substring(0, 55)+" ...");
							}
							else {
								out.print(sub_comment);
							}
						%>"</b><br/> @ topik <b>"
<%
							if(topik_conten.length()>79) {
								out.print(topik_conten.substring(0, 77)+" ...");
							}
							else {
								out.print(topik_conten);
							}
				%>"</b></div>
					<div style="background:#f4f4f4;font-size:0.7em;font-weight:bold;text-align:right;color:<%=txtColor%>"><%=postedAt %></div>
					</a>
				</td>
<%
						}
						else {
%>			
				<td>
<%
							recent = Tool.gantiSpecialChar(recent);
%>
					<a href="proses.redirectUnreadMsg?infoMostRecent=<%=""+recent.replace("||","__")%>">
					<div style="background:#f4f4f4;font-size:0.9em;font-weight:bold;color:<%=txtColor%>">
<%
//jika objectnickname start with 'operator' maka tampilkan nama pengirim kalo tidak tampilkan nama nickname Obj
	//out.print(Tool.capFirstLetterInWord(topik_nmmhsCreator)+" ("+topik_creatorObjNickname.replace("OPERATOR ", "")+")");
							if(usrObjNickname.contains("OPERATOR")) {
								out.print(Tool.capFirstLetterInWord(topik_nmmhsCreator)+" ("+topik_creatorObjNickname.replace("OPERATOR ", "")+")");
							}
							else {
								if(topik_creatorObjNickname.contains("OPERATOR") && !usrObjNickname.contains("OPERATOR")) {
									out.print(topik_creatorObjNickname.replace("OPERATOR ", ""));
								}
							}


							if(topik_npmhsCreator!=null && !Checker.isStringNullOrEmpty(topik_npmhsCreator) && usrObjNickname.contains("OPERATOR")) {
%>
					</div><div style="background:#f4f4f4;font-size:0.6em;font-style:italic;color:<%=txtColor%>">NPM : <%=topik_npmhsCreator %></div>
<%
							}
%> 
					<div style="background:#f4f4f4;font-size:0.8em;font-style:italic;color:<%=txtColor%>">&nbsp&nbsp&nbsp&nbspmengirim komen <b>"<%
							if(topik_conten.startsWith("BcFromOperator-")) {
								topik_conten=topik_conten.replaceAll("BcFromOperator-", "");
							}
							if(topik_conten.length()>157) {
								out.print(topik_conten.substring(0, 157)+" ...");
							}
							else {
								out.print(topik_conten);
							}
					%>"</b> </div>
					<div style="background:#f4f4f4;font-size:0.7em;font-weight:bold;text-align:right;color:<%=txtColor%>"><%=postedAt %></div>
					</a>
				</td>
<%
						}
%>	
			</tr>			
<%		
					}	
				}	
%>
</table>
<%
			}
		}
		
%>	
		





















<%
if((vUnread!=null && vUnread.size()>0)||(vRecent!=null && vRecent.size()>0)) {
	
	if(usrObjNickname.contains("OPERATOR")) {
%>
<div style="background:white;font-size:0.8em;font-style:italic;color:<%=txtColor%>;text-align:center">
* Note: setiap pesan harus dibalas, walaupun pesan tidak membutuhkan respon balik, harap dijawab dengan 'terima kasih',dll.
</div>
<br/>
<%
}
%>
<%=ada_unread_diatas %>
<table align="center" style="background:<%=tableBgColoe %>;color:<%=txtColor %>;width:500px">
<%	

	if(vUnread!=null && vUnread.size()>0) {
		ListIterator liu = vUnread.listIterator();
		while(liu.hasNext()) {
			unread = (String) liu.next();
			//unread = Tool.gantiSpecialChar(unread);
			//System.out.println("unread11a="+unread);
			StringTokenizer st = new StringTokenizer(unread,"||");
			String topik_idTopik = st.nextToken();
			String topik_conten = st.nextToken();
			//System.out.println("topik_conten1="+topik_conten);
			//System.out.println("topik_contenupdated1="+topik_conten.replace("_", "tandaGarisBawah"));
			//System.out.println("topik_contenupdated2="+Tool.gantiSpecialChar(topik_conten));
			String topik_npmhsCreator = st.nextToken();
			String topik_nmmhsCreator = st.nextToken();
			String topik_creatorObjId = st.nextToken();
			String topik_creatorObjNickname = st.nextToken();
			String topik_targetKdpst = st.nextToken();
			String topik_targetNpmhs = st.nextToken();
			String topik_targetSmawl = st.nextToken();
			String topik_targetObjId = st.nextToken();
			String topik_targetObjNickname = st.nextToken();
			String topik_targetGroupId = st.nextToken();
			String topik_groupPwd = st.nextToken();
			String topik_shownToGroupOnly = st.nextToken();
			String topik_deletedByCreator = st.nextToken();
			String topik_hidenAtCreator = st.nextToken();
			String topik_pinedAtCreator = st.nextToken();
			String topik_markedAsReadAtCreator = st.nextToken();
			String topik_deletedAtTarget = st.nextToken();
			String topik_hidenAtTarget = st.nextToken();
			String topik_pinedAtTarget = st.nextToken();
			String topik_markedAsReasAsTarget = st.nextToken();
			String topik_creatorAsAnonymous = st.nextToken();
			String topik_creatorIsPetugas = st.nextToken();
			String topik_updtm = st.nextToken();
			String sub_id = st.nextToken();
			String sub_idTopik = st.nextToken();
			String sub_comment = st.nextToken();
			String sub_npmhsSender = st.nextToken();
			String sub_nmmhsSender = st.nextToken();
			String sub_anonymousReply = st.nextToken();
			String sub_shownToCreatorObly = st.nextToken();
			String sub_commenterIsPetugas = st.nextToken();
			String sub_markedAsReadAtCreator = st.nextToken();
			String sub_markedAsReadAtSender = st.nextToken();
			String sub_objNicknameSender = st.nextToken();
			String sub_npmhsReceiver = st.nextToken();
			String sub_nmmhsReceiver = st.nextToken();
			String sub_objNicknameReceiver = st.nextToken();
			String sub_updtm = st.nextToken();
			String postedAt = null;
			if(sub_updtm.equalsIgnoreCase("null")) {
				postedAt = AskSystem.getPostdTimeInHrOrDay(AskSystem.getCurrentTimestamp(), Timestamp.valueOf(topik_updtm));
			}
			else {
				postedAt = AskSystem.getPostdTimeInHrOrDay(AskSystem.getCurrentTimestamp(), Timestamp.valueOf(sub_updtm));
			}	
		
%>
	<tr>
<%
			if(!sub_updtm.equalsIgnoreCase("null")) {
				//msg ada di subtopik table
%>			
		<td>
		<%
		unread = Tool.gantiSpecialChar(unread);
		%>
		<a href="proses.redirectUnreadMsg?infoUnread=<%=""+unread.replace("||","__")%>">
		<div style="background:white;font-size:0.9em;font-weight:bold;color:<%=txtColor%>">
		<%
		//jika anda operator maka tampilkan nama dan nickname
		if(usrObjNickname.contains("OPERATOR")) {
			out.print(Tool.capFirstLetterInWord(sub_nmmhsSender)+" ("+sub_objNicknameSender.replace("OPERATOR ", "")+")");
		}
		else {
			out.print(sub_objNicknameSender.replace("OPERATOR ", ""));
		}
		%>
		</div> 
		<div style="background:white;font-size:0.8em;font-style:italic;color:<%=txtColor%>">&nbsp&nbspmengirim komen <b>"
		<%
		if(sub_comment.length()>57) {
			out.print(sub_comment.substring(0, 55)+" ...");
		}
		else {
			out.print(sub_comment);
		}
		%>"</b> <br/> @ topik <b>"
		<%
		if(topik_conten.length()>79) {
			out.print(topik_conten.substring(0, 77)+" ...");
		}
		else {
			out.print(topik_conten);
		}
		%>"</b></div>
		<div style="background:white;font-size:0.7em;font-weight:bold;text-align:right;color:<%=txtColor%>"><%=postedAt %></div>
		</a>
		</td>
<%
			}
			else {
				//msg ada di topik table - selalu tampilkan nama pengirim kecualicreatonya operator (pengumuman)
	%>			
		<td>
		<%
		unread = Tool.gantiSpecialChar(unread);
		%>
		<a href="proses.redirectUnreadMsg?infoUnread=<%=""+unread.replace("||","__")%>">
		<div style="background:white;font-size:0.9em;font-weight:bold;color:<%=txtColor%>">
		<%
		//jika objectnickname start with 'operator' maka tampilkan nama pengirim kalo tidak tampilkan nama nickname Obj
		
		if(usrObjNickname.contains("OPERATOR")) {
			out.print(Tool.capFirstLetterInWord(topik_nmmhsCreator)+" ("+topik_creatorObjNickname.replace("OPERATOR ", "")+")");
		}
		else {
			if(topik_creatorObjNickname.contains("OPERATOR") && !usrObjNickname.contains("OPERATOR")) {
				out.print(topik_creatorObjNickname.replace("OPERATOR ", ""));
			}
		}
			//out.print(Tool.capFirstLetterInWord(topik_nmmhsCreator)+" ("+topik_creatorObjNickname.replace("OPERATOR ", "")+")");
		if(topik_npmhsCreator!=null && !Checker.isStringNullOrEmpty(topik_npmhsCreator) && usrObjNickname.contains("OPERATOR")) {
		%>
		</div><div style="background:white;font-size:0.6em;font-style:italic;color:<%=txtColor%>">NPM : <%=topik_npmhsCreator %></div>
		<%
		}
		%> 
		<div style="background:white;font-size:0.8em;font-style:italic;color:<%=txtColor%>">&nbsp&nbsp&nbsp&nbspmengirim komen <b>"
		<%
		if(topik_conten.startsWith("BcFromOperator-")) {
			topik_conten=topik_conten.replaceAll("BcFromOperator-", "");
		}
		if(topik_conten.length()>157) {
			out.print(topik_conten.substring(0, 157)+" ...");
		}
		else {
			out.print(topik_conten);
		}
		%>"</b> </div>
		<div style="background:white;font-size:0.7em;font-weight:bold;text-align:right;color:<%=txtColor%>"><%=postedAt %></div>
		</a>
		</td>
<%
			}
%>		
	</tr>			
<%		
		}
	}	
	if(vRecent==null || vRecent.size()<1) {
%>
</table>

<%			
	}
	else {
		//ListIterator lir = vRecent.listIterator();
		//sorting vRecent - jadi kalo belum ada reply maka subtopik updatime = topik update time lalu sort berdasarkan subtopik updtime
		if(vRecent!=null && vRecent.size()>1) {
			Vector vSorted = new Vector();
			ListIterator lis = vSorted.listIterator();
			ListIterator lir = vRecent.listIterator();
				
			while(lir.hasNext()) {
				String recent = (String) lir.next();
				//recent = Tool.gantiSpecialChar(recent);
				//System.out.println("recent11="+recent);
				StringTokenizer st = new StringTokenizer(recent,"||");
				String topik_idTopik = st.nextToken();
				String topik_conten = st.nextToken();
				String topik_npmhsCreator = st.nextToken();
				String topik_nmmhsCreator = st.nextToken();
				String topik_creatorObjId = st.nextToken();
				String topik_creatorObjNickname = st.nextToken();
				String topik_targetKdpst = st.nextToken();
				String topik_targetNpmhs = st.nextToken();
				String topik_targetSmawl = st.nextToken();
				String topik_targetObjId = st.nextToken();
				String topik_targetObjNickname = st.nextToken();
				String topik_targetGroupId = st.nextToken();
				String topik_groupPwd = st.nextToken();
				String topik_shownToGroupOnly = st.nextToken();
				String topik_deletedByCreator = st.nextToken();
				String topik_hidenAtCreator = st.nextToken();
				String topik_pinedAtCreator = st.nextToken();
				String topik_markedAsReadAtCreator = st.nextToken();
				String topik_deletedAtTarget = st.nextToken();
				String topik_hidenAtTarget = st.nextToken();
				String topik_pinedAtTarget = st.nextToken();
				String topik_markedAsReasAsTarget = st.nextToken();
				String topik_creatorAsAnonymous = st.nextToken();
				String topik_creatorIsPetugas = st.nextToken();
				String topik_updtm = st.nextToken();
				String sub_id = st.nextToken();
				String sub_idTopik = st.nextToken();
				String sub_comment = st.nextToken();
				String sub_npmhsSender = st.nextToken();
				String sub_nmmhsSender = st.nextToken();
				String sub_anonymousReply = st.nextToken();
				String sub_shownToCreatorObly = st.nextToken();
				String sub_commenterIsPetugas = st.nextToken();
				String sub_markedAsReadAtCreator = st.nextToken();
				String sub_markedAsReadAtSender = st.nextToken();
				String sub_objNicknameSender = st.nextToken();
				String sub_npmhsReceiver = st.nextToken();
				String sub_nmmhsReceiver = st.nextToken();
				String sub_objNicknameReceiver = st.nextToken();
				String sub_updtm = st.nextToken();
				String tmp = null;
				if(sub_updtm==null || Checker.isStringNullOrEmpty(sub_updtm)) {
					//sub_updtm = ""+topik_updtm;
					tmp = topik_updtm+"||"+topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+sub_id+"||"+sub_idTopik+"||"+sub_comment+"||"+sub_npmhsSender+"||"+sub_nmmhsSender+"||"+sub_anonymousReply+"||"+sub_shownToCreatorObly+"||"+sub_commenterIsPetugas+"||"+sub_markedAsReadAtCreator+"||"+sub_markedAsReadAtSender+"||"+sub_objNicknameSender+"||"+sub_npmhsReceiver+"||"+sub_nmmhsReceiver+"||"+sub_objNicknameReceiver+"||"+sub_updtm;
				}
				else {
					tmp = sub_updtm+"||"+topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+sub_id+"||"+sub_idTopik+"||"+sub_comment+"||"+sub_npmhsSender+"||"+sub_nmmhsSender+"||"+sub_anonymousReply+"||"+sub_shownToCreatorObly+"||"+sub_commenterIsPetugas+"||"+sub_markedAsReadAtCreator+"||"+sub_markedAsReadAtSender+"||"+sub_objNicknameSender+"||"+sub_npmhsReceiver+"||"+sub_nmmhsReceiver+"||"+sub_objNicknameReceiver+"||"+sub_updtm;
				}
				
				//String tmp = sub_updtm+"||"+topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+sub_id+"||"+sub_idTopik+"||"+sub_comment+"||"+sub_npmhsSender+"||"+sub_nmmhsSender+"||"+sub_anonymousReply+"||"+sub_shownToCreatorObly+"||"+sub_commenterIsPetugas+"||"+sub_markedAsReadAtCreator+"||"+sub_markedAsReadAtSender+"||"+sub_objNicknameSender+"||"+sub_npmhsReceiver+"||"+sub_nmmhsReceiver+"||"+sub_objNicknameReceiver+"||"+sub_updtm;
				lis.add(tmp);
			}	
			Comparator comparator = Collections.reverseOrder();
			Collections.sort(vSorted,comparator);
				
			lis = vSorted.listIterator();
				
			vRecent = new Vector();
			lir = vRecent.listIterator();
			while(lis.hasNext()) {
				String recent = (String) lis.next();
				//recent = Tool.gantiSpecialChar(recent);
				//System.out.println("recent11="+recent);
				StringTokenizer st = new StringTokenizer(recent,"||");
				st.nextToken();
				String topik_idTopik = st.nextToken();
				String topik_conten = st.nextToken();
				String topik_npmhsCreator = st.nextToken();
				String topik_nmmhsCreator = st.nextToken();
				String topik_creatorObjId = st.nextToken();
				String topik_creatorObjNickname = st.nextToken();
				String topik_targetKdpst = st.nextToken();
				String topik_targetNpmhs = st.nextToken();
				String topik_targetSmawl = st.nextToken();
				String topik_targetObjId = st.nextToken();
				String topik_targetObjNickname = st.nextToken();
				String topik_targetGroupId = st.nextToken();
				String topik_groupPwd = st.nextToken();
				String topik_shownToGroupOnly = st.nextToken();
				String topik_deletedByCreator = st.nextToken();
				String topik_hidenAtCreator = st.nextToken();
				String topik_pinedAtCreator = st.nextToken();
				String topik_markedAsReadAtCreator = st.nextToken();
				String topik_deletedAtTarget = st.nextToken();
				String topik_hidenAtTarget = st.nextToken();
				String topik_pinedAtTarget = st.nextToken();
				String topik_markedAsReasAsTarget = st.nextToken();
				String topik_creatorAsAnonymous = st.nextToken();
				String topik_creatorIsPetugas = st.nextToken();
				String topik_updtm = st.nextToken();
				String sub_id = st.nextToken();
				String sub_idTopik = st.nextToken();
				String sub_comment = st.nextToken();
				String sub_npmhsSender = st.nextToken();
				String sub_nmmhsSender = st.nextToken();
				String sub_anonymousReply = st.nextToken();
				String sub_shownToCreatorObly = st.nextToken();
				String sub_commenterIsPetugas = st.nextToken();
				String sub_markedAsReadAtCreator = st.nextToken();
				String sub_markedAsReadAtSender = st.nextToken();
				String sub_objNicknameSender = st.nextToken();
				String sub_npmhsReceiver = st.nextToken();
				String sub_nmmhsReceiver = st.nextToken();
				String sub_objNicknameReceiver = st.nextToken();
				String sub_updtm = st.nextToken();
				String tmp = topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+sub_id+"||"+sub_idTopik+"||"+sub_comment+"||"+sub_npmhsSender+"||"+sub_nmmhsSender+"||"+sub_anonymousReply+"||"+sub_shownToCreatorObly+"||"+sub_commenterIsPetugas+"||"+sub_markedAsReadAtCreator+"||"+sub_markedAsReadAtSender+"||"+sub_objNicknameSender+"||"+sub_npmhsReceiver+"||"+sub_nmmhsReceiver+"||"+sub_objNicknameReceiver+"||"+sub_updtm;
				lir.add(tmp);
			}
		}
		//======end sorting==================
		ListIterator lir = vRecent.listIterator();
		while(lir.hasNext()) {
			String recent = (String) lir.next();
			//recent = Tool.gantiSpecialChar(recent);
			//System.out.println("recent11="+recent);
			StringTokenizer st = new StringTokenizer(recent,"||");
			String topik_idTopik = st.nextToken();
			String topik_conten = st.nextToken();
			String topik_npmhsCreator = st.nextToken();
			String topik_nmmhsCreator = st.nextToken();
			String topik_creatorObjId = st.nextToken();
			String topik_creatorObjNickname = st.nextToken();
			String topik_targetKdpst = st.nextToken();
			String topik_targetNpmhs = st.nextToken();
			String topik_targetSmawl = st.nextToken();
			String topik_targetObjId = st.nextToken();
			String topik_targetObjNickname = st.nextToken();
			String topik_targetGroupId = st.nextToken();
			String topik_groupPwd = st.nextToken();
			String topik_shownToGroupOnly = st.nextToken();
			String topik_deletedByCreator = st.nextToken();
			String topik_hidenAtCreator = st.nextToken();
			String topik_pinedAtCreator = st.nextToken();
			String topik_markedAsReadAtCreator = st.nextToken();
			String topik_deletedAtTarget = st.nextToken();
			String topik_hidenAtTarget = st.nextToken();
			String topik_pinedAtTarget = st.nextToken();
			String topik_markedAsReasAsTarget = st.nextToken();
			String topik_creatorAsAnonymous = st.nextToken();
			String topik_creatorIsPetugas = st.nextToken();
			String topik_updtm = st.nextToken();
			String sub_id = st.nextToken();
			String sub_idTopik = st.nextToken();
			String sub_comment = st.nextToken();
			String sub_npmhsSender = st.nextToken();
			String sub_nmmhsSender = st.nextToken();
			String sub_anonymousReply = st.nextToken();
			String sub_shownToCreatorObly = st.nextToken();
			String sub_commenterIsPetugas = st.nextToken();
			String sub_markedAsReadAtCreator = st.nextToken();
			String sub_markedAsReadAtSender = st.nextToken();
			String sub_objNicknameSender = st.nextToken();
			String sub_npmhsReceiver = st.nextToken();
			String sub_nmmhsReceiver = st.nextToken();
			String sub_objNicknameReceiver = st.nextToken();
			String sub_updtm = st.nextToken();
			//String postedAt = AskSystem.getPostdTimeInHrOrDay(AskSystem.getCurrentTimestamp(), Timestamp.valueOf(sub_updtm));
			String postedAt = null;
			if(sub_updtm.equalsIgnoreCase("null")) {
				postedAt = AskSystem.getPostdTimeInHrOrDay(AskSystem.getCurrentTimestamp(), Timestamp.valueOf(topik_updtm));
			}
			else {
				postedAt = AskSystem.getPostdTimeInHrOrDay(AskSystem.getCurrentTimestamp(), Timestamp.valueOf(sub_updtm));
			}
	%>
		<tr>
<%
			if(!sub_updtm.equalsIgnoreCase("null")) {
				//String tmp = ""+recent.replace("||","__");
				//System.out.println("tmpRecebt="+tmp);
%>			
		<td>
		<%
		recent = Tool.gantiSpecialChar(recent);
		%>
		<a href="proses.redirectUnreadMsg?infoMostRecent=<%=""+recent.replace("||","__")%>">
		<div style="background:#f4f4f4;font-size:0.9em;font-weight:bold;color:<%=txtColor%>">
		<%
		
		if(usrObjNickname.contains("OPERATOR")) {
			out.print(Tool.capFirstLetterInWord(sub_nmmhsSender)+" ("+sub_objNicknameSender.replace("OPERATOR ", "")+")");
		}
		else {
			out.print(sub_objNicknameSender.replace("OPERATOR ", ""));
		} 
		%></div> 
		<div style="background:#f4f4f4;font-size:0.8em;font-style:italic;color:<%=txtColor%>">&nbsp&nbspmengirim komen <b>"
		<%
		if(sub_comment.length()>57) {
			out.print(sub_comment.substring(0, 55)+" ...");
		}
		else {
			out.print(sub_comment);
		}
		%>"</b><br/> @ topik <b>"
		<%
		if(topik_conten.length()>79) {
			out.print(topik_conten.substring(0, 77)+" ...");
		}
		else {
			out.print(topik_conten);
		}
		%>"</b></div>
		<div style="background:#f4f4f4;font-size:0.7em;font-weight:bold;text-align:right;color:<%=txtColor%>"><%=postedAt %></div>
		</a>
		</td>
<%
			}
			else {
				//String tmp = ""+recent.replace("||","__");
				//System.out.println("tmpRecebt="+tmp);
	%>			
		<td>
		<%
		recent = Tool.gantiSpecialChar(recent);
		%>
		<a href="proses.redirectUnreadMsg?infoMostRecent=<%=""+recent.replace("||","__")%>">
		<div style="background:#f4f4f4;font-size:0.9em;font-weight:bold;color:<%=txtColor%>">
		<%
		//jika objectnickname start with 'operator' maka tampilkan nama pengirim kalo tidak tampilkan nama nickname Obj
			//out.print(Tool.capFirstLetterInWord(topik_nmmhsCreator)+" ("+topik_creatorObjNickname.replace("OPERATOR ", "")+")");
		if(usrObjNickname.contains("OPERATOR")) {
			out.print(Tool.capFirstLetterInWord(topik_nmmhsCreator)+" ("+topik_creatorObjNickname.replace("OPERATOR ", "")+")");
		}
		else {
			if(topik_creatorObjNickname.contains("OPERATOR") && !usrObjNickname.contains("OPERATOR")) {
				out.print(topik_creatorObjNickname.replace("OPERATOR ", ""));
			}
		}
		
		
		if(topik_npmhsCreator!=null && !Checker.isStringNullOrEmpty(topik_npmhsCreator) && usrObjNickname.contains("OPERATOR")) {
		%>
		</div><div style="background:#f4f4f4;font-size:0.6em;font-style:italic;color:<%=txtColor%>">NPM : <%=topik_npmhsCreator %></div>
		<%
		}
		%> 
		<div style="background:#f4f4f4;font-size:0.8em;font-style:italic;color:<%=txtColor%>">&nbsp&nbsp&nbsp&nbspmengirim komen <b>"<%
		if(topik_conten.startsWith("BcFromOperator-")) {
			topik_conten=topik_conten.replaceAll("BcFromOperator-", "");
		}
		if(topik_conten.length()>157) {
			out.print(topik_conten.substring(0, 157)+" ...");
		}
		else {
			out.print(topik_conten);
		}
		%>"</b> </div>
		<div style="background:#f4f4f4;font-size:0.7em;font-weight:bold;text-align:right;color:<%=txtColor%>"><%=postedAt %></div>
		</a>
		</td>
<%
			}
%>	
		</tr>			
	<%		
		}	
%>
</table>
<%			
		
	}
}
%>		

		<!-- Column 1 start -->
	</div>
</div>		
</body>
</html>