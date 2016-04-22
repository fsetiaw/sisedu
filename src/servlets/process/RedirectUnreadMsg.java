package servlets.process;
import java.sql.Timestamp;
import java.util.Vector;
import java.util.ListIterator;
import beans.setting.*;
import beans.login.InitSessionUsr;
import beans.sistem.*;
import beans.tools.Checker;
import beans.tools.PathFinder;
import beans.dbase.UpdateDb;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.StringTokenizer;
/**
 * Servlet implementation class RedirectUnreadMsg
 */
@WebServlet("/RedirectUnreadMsg")
public class RedirectUnreadMsg extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RedirectUnreadMsg() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("redirect unread msg");
		/*
		 * ini digunakan dari dash contact - targeted topik 
		 * logikanya sama dengan PrepDasboardXXXX.java hanya saja starting index
		 * nya adalah topik id tergeted.
		 */
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String ObjGenderAndNickname = (String)session.getAttribute("ObjGenderAndNickname");
		StringTokenizer st = new StringTokenizer(ObjGenderAndNickname,"__");
		String usrKdjek = st.nextToken();
		String usrObjNickname = st.nextToken();//list usrObjNicknm
		String unreadInfo = request.getParameter("infoUnread");
		//System.out.println("unreadInfo="+unreadInfo);
		String recentInfo = request.getParameter("infoMostRecent");
		//System.out.println("recentInfo="+recentInfo);
		Vector vUnread = (Vector)session.getAttribute("vUnread");
		Vector vRecent = (Vector)session.getAttribute("vRecent");
		Vector vListMonitorNickname = (Vector)session.getAttribute("vlistMonitorNickname");
		
		st = null;
		boolean unread = false;
		if(unreadInfo!=null && !Checker.isStringNullOrEmpty(unreadInfo)) {
			st = new StringTokenizer(unreadInfo,"__");
			unread = true;
		}
		else {
			st = new StringTokenizer(recentInfo,"__");
		}
		
		String topik_idTopik = st.nextToken();
		//System.out.println(topik_idTopik);
		String topik_conten = st.nextToken();
		//System.out.println(topik_conten);
		String topik_npmhsCreator = st.nextToken();
		//System.out.println(topik_npmhsCreator);
		String topik_nmmhsCreator = st.nextToken();
		//System.out.println(topik_nmmhsCreator);
		String topik_creatorObjId = st.nextToken();
		//System.out.println(topik_creatorObjId);
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
		//System.out.println("sub_updtm="+sub_updtm);
		//System.out.println("topik_updtm="+topik_updtm);
		if(sub_updtm.equalsIgnoreCase("null")) {
			postedAt = AskSystem.getPostdTimeInHrOrDay(AskSystem.getCurrentTimestamp(), Timestamp.valueOf(topik_updtm));
		}
		else {
			postedAt = AskSystem.getPostdTimeInHrOrDay(AskSystem.getCurrentTimestamp(), Timestamp.valueOf(sub_updtm));
		}
		
		
		
		 
		
		//System.out.println("topik_updtm="+topik_updtm);
		//System.out.println("sub_updtm="+sub_updtm);
		
		//System.out.println("unreadInfo="+unreadInfo);
		//goto.dashContactBaa?atMenu=baa&targetObjNickName=OPERATOR BAA"
		/*
		 * redirecting
		 */
		
		//target object
		//String atMenuAndRedirectUrl = Constants.redirectObjectNickname(topik_targetObjNickname,null);
		//String atMenuAndRedirectUrl = Constants.redirectObjectNickname(topik_creatorObjNickname,null);
		//StringTokenizer st = new StringTokenizer(usrObjNickname
		//st = new StringTokenizer(atMenuAndRedirectUrl,"||");
		//String atMenu = st.nextToken();
		//String redirectUrl = st.nextToken();
		//String objNickname = st.nextToken();//overiding dng 
		//String target = ""+redirectUrl;
		//System.out.println("aneh = "+atMenu+","+redirectUrl+","+objNickname+","+topik_targetObjNickname);
		//System.out.println("aneh sub_objNicknameReceiver = "+sub_objNicknameReceiver);
		String toggledUser = "";
		//String uri = request.getRequestURI();
		//String url = PathFinder.getPath(uri, target);
		/*
		 * set markedreadat at receiver
		 */
		UpdateDb udb = new UpdateDb(isu.getNpm());
		if(sub_objNicknameReceiver==null || Checker.isStringNullOrEmpty(sub_objNicknameReceiver)) {
			//artinya blm ada reply = baru ada di topik table
			System.out.println("sub_objNicknameReceiver==null");
			//if(topik_targetObjNickname.contains(usrObjNickname)) {
			if(usrObjNickname.contains(topik_targetObjNickname)) {
				System.out.println("usrObjNickname contains "+topik_targetObjNickname);
				toggledUser = ""+topik_targetObjNickname;
				//usr skrg adalah target msg 
				/*
				 * todo:
				 * 1.markedAsRead at table bila you target objec
				 * 2.remove from vUnread
				 * 3.put in vRecent
				 */
				//UpdateDb udb = new UpdateDb(isu.getNpm());
				//udb.markedAsReadAtTopikByObjekNickname(Long.valueOf(topik_idTopik).longValue(), usrObjNickname);
				boolean monitoredNickname = false;
				if(vListMonitorNickname!=null && vListMonitorNickname.size()>0) {
					//System.out.println("=======vListMonitorNickname================");
					ListIterator lit = vListMonitorNickname.listIterator();
					while(lit.hasNext() && !monitoredNickname) {
						String monNicknam = (String)lit.next();
						if(topik_creatorObjNickname.equalsIgnoreCase(monNicknam)) {
							System.out.println("topik_creatorObjNickname vs monNickname = "+topik_creatorObjNickname+" vs. "+monNicknam);
							monitoredNickname = true;
						}
					}	
				}
				System.out.println("monitoredNickname="+monitoredNickname);
				if(!monitoredNickname) {
					//System.out.println("!monitoredNickname");
					//if(false) {
					if(topik_targetNpmhs!=null && !Checker.isStringNullOrEmpty(topik_targetNpmhs)) {
						udb.markedAsReadAtTopikByTargetedNpmhs(Long.valueOf(topik_idTopik).longValue(), topik_targetNpmhs);
					}
					else {
						udb.markedAsReadAtTopikByObjekNickname(Long.valueOf(topik_idTopik).longValue(), topik_targetObjNickname);
					}	
				}
				ListIterator liun = vUnread.listIterator();
				boolean match = false;
				while(liun.hasNext()&&!match) {
					String unreadBrs = (String)liun.next();
					System.out.println("unreadBrs = "+unreadBrs);
					st = new StringTokenizer(unreadBrs,"||");
					String topik_idTopik1 = st.nextToken();
					String topik_conten1 = st.nextToken();
					String topik_npmhsCreator1 = st.nextToken();
					String topik_nmmhsCreator1 = st.nextToken();
					String topik_creatorObjId1 = st.nextToken();
					String topik_creatorObjNickname1 = st.nextToken();
					String topik_targetKdpst1 = st.nextToken();
					String topik_targetNpmhs1 = st.nextToken();
					String topik_targetSmawl1 = st.nextToken();
					String topik_targetObjId1 = st.nextToken();
					String topik_targetObjNickname1 = st.nextToken();
					String topik_targetGroupId1 = st.nextToken();
					String topik_groupPwd1 = st.nextToken();
					String topik_shownToGroupOnly1 = st.nextToken();
					String topik_deletedByCreator1 = st.nextToken();
					String topik_hidenAtCreator1 = st.nextToken();
					String topik_pinedAtCreator1 = st.nextToken();
					String topik_markedAsReadAtCreator1 = st.nextToken();
					String topik_deletedAtTarget1 = st.nextToken();
					String topik_hidenAtTarget1 = st.nextToken();
					String topik_pinedAtTarget1 = st.nextToken();
					String topik_markedAsReasAsTarget1 = st.nextToken();
					String topik_creatorAsAnonymous1 = st.nextToken();
					String topik_creatorIsPetugas1 = st.nextToken();
					String topik_updtm1 = st.nextToken();
					String sub_id1 = st.nextToken();
					String sub_idTopik1 = st.nextToken();
					String sub_comment1 = st.nextToken();
					String sub_npmhsSender1 = st.nextToken();
					String sub_nmmhsSender1 = st.nextToken();
					String sub_anonymousReply1 = st.nextToken();
					String sub_shownToCreatorObly1 = st.nextToken();
					String sub_commenterIsPetugas1 = st.nextToken();
					String sub_markedAsReadAtCreator1 = st.nextToken();
					String sub_markedAsReadAtSender1 = st.nextToken();
					String sub_objNicknameSender1 = st.nextToken();
					String sub_npmhsReceiver1 = st.nextToken();
					String sub_nmmhsReceiver1 = st.nextToken();
					String sub_objNicknameReceiver1 = st.nextToken();
					String sub_updtm1 = st.nextToken();
					if(topik_idTopik1.equalsIgnoreCase(topik_idTopik) && sub_id1.equalsIgnoreCase(sub_id)) {
						match = true;
						if(vRecent!=null) {
							System.out.println("vRecent!=null");
							ListIterator litmp = vRecent.listIterator();
							while(litmp.hasNext()) {
								litmp.next();
							}
							litmp.add(unreadBrs);
							
						}
						else {
							System.out.println("vRecent==null");
							vRecent = new Vector();
							ListIterator litmp = vRecent.listIterator();
							litmp.add(unreadBrs);
						}
						liun.remove();
					}
				}
			}
			else {
				//krn bukan targetnya maka jgn markedAsread = keperluan monitoring
			}
		}
		else {
			//posisi msg ada di subtopik table
			//if(sub_objNicknameReceiver.contains(usrObjNickname)) {
			if(usrObjNickname.contains(sub_objNicknameReceiver)) {
				toggledUser = ""+sub_objNicknameReceiver;
				//System.out.println("mask siini");
				//System.out.println("sub_objNicknameReceiver!=null="+sub_objNicknameReceiver);
				//usr skrg adalah target msg = markedRead at target
				/*
				 * todo:
				 * 1.markedAsRead at table bila you target objec
				 * 2.remove from vUnread
				 * 3.put in vRecent
				 */
				//UpdateDb udb = new UpdateDb(isu.getNpm());
				boolean monitoredNickname = false;
				if(vListMonitorNickname!=null && vListMonitorNickname.size()>0) {
					//System.out.println("=======vListMonitorNickname================");
					ListIterator lit = vListMonitorNickname.listIterator();
					while(lit.hasNext() && !monitoredNickname) {
						String monNicknam = (String)lit.next();
						if(topik_creatorObjNickname.equalsIgnoreCase(monNicknam)) {
							monitoredNickname = true;
						}
					}	
				}
				//if(!monitoredNickname) { //bikin masalah web admin msg ttp unread
				if(true) {
					
					udb.markedAsReadAtSubTopikByObjekNickname(Long.valueOf(sub_id).longValue(), sub_objNicknameReceiver);
				}
				ListIterator liun = vUnread.listIterator();
				boolean match = false;
				while(liun.hasNext()&&!match) {
					String unreadBrs = (String)liun.next();
					st = new StringTokenizer(unreadBrs,"||");
					String topik_idTopik1 = st.nextToken();
					String topik_conten1 = st.nextToken();
					String topik_npmhsCreator1 = st.nextToken();
					String topik_nmmhsCreator1 = st.nextToken();
					String topik_creatorObjId1 = st.nextToken();
					String topik_creatorObjNickname1 = st.nextToken();
					String topik_targetKdpst1 = st.nextToken();
					String topik_targetNpmhs1 = st.nextToken();
					String topik_targetSmawl1 = st.nextToken();
					String topik_targetObjId1 = st.nextToken();
					String topik_targetObjNickname1 = st.nextToken();
					String topik_targetGroupId1 = st.nextToken();
					String topik_groupPwd1 = st.nextToken();
					String topik_shownToGroupOnly1 = st.nextToken();
					String topik_deletedByCreator1 = st.nextToken();
					String topik_hidenAtCreator1 = st.nextToken();
					String topik_pinedAtCreator1 = st.nextToken();
					String topik_markedAsReadAtCreator1 = st.nextToken();
					String topik_deletedAtTarget1 = st.nextToken();
					String topik_hidenAtTarget1 = st.nextToken();
					String topik_pinedAtTarget1 = st.nextToken();
					String topik_markedAsReasAsTarget1 = st.nextToken();
					String topik_creatorAsAnonymous1 = st.nextToken();
					String topik_creatorIsPetugas1 = st.nextToken();
					String topik_updtm1 = st.nextToken();
					String sub_id1 = st.nextToken();
					String sub_idTopik1 = st.nextToken();
					String sub_comment1 = st.nextToken();
					String sub_npmhsSender1 = st.nextToken();
					String sub_nmmhsSender1 = st.nextToken();
					String sub_anonymousReply1 = st.nextToken();
					String sub_shownToCreatorObly1 = st.nextToken();
					String sub_commenterIsPetugas1 = st.nextToken();
					String sub_markedAsReadAtCreator1 = st.nextToken();
					String sub_markedAsReadAtSender1 = st.nextToken();
					String sub_objNicknameSender1 = st.nextToken();
					String sub_npmhsReceiver1 = st.nextToken();
					String sub_nmmhsReceiver1 = st.nextToken();
					String sub_objNicknameReceiver1 = st.nextToken();
					String sub_updtm1 = st.nextToken();
					if(topik_idTopik1.equalsIgnoreCase(topik_idTopik) && sub_id1.equalsIgnoreCase(sub_id)) {
						match = true;
						if(vRecent!=null) {
							ListIterator litmp = vRecent.listIterator();
							while(litmp.hasNext()) {
								litmp.next();
							}
							litmp.add(unreadBrs);
							
						}
						else {
							vRecent = new Vector();
							ListIterator litmp = vRecent.listIterator();
							litmp.add(unreadBrs);
						}
						liun.remove();
					}
				}
			}
			else {
				//krn bukan targetnya maka jgn markedAsread = keperluan monitoring
			}
		}
		/*
		 * todo ext
		 * if vUnread.size = 1, maka update table New_msg_notification to masage = false
		 * 
		 */
		// ditch the idea for now
		//if(vUnread==null || vUnread.size()<1) {
			//udb.togleNewMsgObjectNicknameGotMsg(toggledUser, false); // ditch the idea for now
		//}
		if(unreadInfo!=null) { // unread info = target info jadi bisa single selected unread ato most recent
			unreadInfo = unreadInfo.replace("||", "__");
		}
		else {
			unreadInfo = ""+recentInfo.replace("||", "__");
		}
		//request.getRequestDispatcher(target+"?atMenu="+atMenu+"&targetObjNickName="+topik_targetObjNickname+"&unreadInfo="+unreadInfo).forward(request,response);
		//System.out.println("unreadInfo@redirct.java="+unreadInfo);
		String atMenuAndRedirectUrl = null;
		
		//if(sub_objNicknameSender.equalsIgnoreCase("null")) {
		if(usrObjNickname.contains(topik_creatorObjNickname)) {
			//usr is creator
			atMenuAndRedirectUrl = Constants.redirectObjectNickname(null,Constants.redirectObjectNickname(topik_targetObjNickname));
		}
		else {
			//usr isnt creator
			atMenuAndRedirectUrl = Constants.redirectObjectNickname(null,Constants.redirectObjectNickname(topik_creatorObjNickname));
		}
			
		//}
		//else {
		//	atMenuAndRedirectUrl = Constants.redirectObjectNickname(null,Constants.redirectObjectNickname(sub_objNicknameSender));
		//}
		//System.out.println("atMenuAndRedirectUrlNew="+atMenuAndRedirectUrl);
		st = new StringTokenizer(atMenuAndRedirectUrl,"||"); //goto.dashContactTu
		String tgtMenu = st.nextToken();
		String redirectUrl = st.nextToken();
		String objNickname = st.nextToken(); //ditch - nanti overide di step berikutnya
		//String target = "goto.dashContactBaa";
	
		String target = ""+redirectUrl;
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		
		request.getRequestDispatcher(target+"?atMenu="+tgtMenu+"&targetObjNickName="+topik_targetObjNickname+"&unreadInfo="+unreadInfo).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
