package servlets.view;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Vector;
import java.util.ListIterator;

import beans.dbase.SearchDb;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;
import beans.tools.Tool;

/**
 * Servlet implementation class ListInInbox
 */
@WebServlet("/ListInInbox")
public class ListInInbox extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListInInbox() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("servlet listInbox");
		
		String sta_index=request.getParameter("sta_index");
		String show=request.getParameter("show");
		String range=request.getParameter("range");
		String monitoring = request.getParameter("monitoring");
		//System.out.println("listInbox="+sta_index+","+show+","+range);
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
			SearchDb sdb = new SearchDb(isu.getNpm());
			//Vector vScopeMonitoredNickname = isu.getScopeObjNickname("scopeMonitorInbox");
			Vector vScopeOwnInbox = isu.getScopeObjNickname("scopeOwnInbox");
			//ListIterator li = vScopeOwnInbox.listIterator();
			
			Vector vScopeMonitorInbox = isu.getScopeObjNickname("scopeMonitorInbox");
			//System.out.println("---list scope monitor onbox -----");
			ListIterator li = vScopeMonitorInbox.listIterator();
			//while(li.hasNext()) {
				//System.out.println((String)li.next());
			//}
			//System.out.println("isu.getObjNickNameGivenObjId()="+isu.getObjNickNameGivenObjId());
			Vector vUnreadOwnInbox = null;
			if(monitoring!=null && monitoring.equalsIgnoreCase("true")) {
				vUnreadOwnInbox = sdb.getUnreadMsgInboxMonitor(isu.getObjNickNameGivenObjId(),vScopeMonitorInbox);
			}
			else {
				//vUnreadOwnInbox = sdb.getUnreadMsgInbox(isu.getObjNickNameGivenObjId(),vScopeOwnInbox);
				vUnreadOwnInbox = sdb.getUnreadMsgInboxWithinRange_v2(isu.getObjNickNameGivenObjId(),vScopeOwnInbox,Integer.parseInt(sta_index),Integer.parseInt(range));
			}	
			//System.out.println("size vUnreadOwnInbox= "+vUnreadOwnInbox.size());
			/*
			 * ganti dengan distinct topik id
			 * Vector vRecentOwnInbox = sdb.getMostRecentMsg(100,isu.getObjNickNameGivenObjId(),vScopeOwnInbox);
			 */
			Vector vRecentOwnInbox = sdb.get100MostRecentMsgDistinctTopikIdWithRange_v2(isu.getObjNickNameGivenObjId(),vScopeOwnInbox,0,100);
			//System.out.println("size vRecentOwnInbox= "+vRecentOwnInbox.size());
			
			
			//vRecentOwnInbox = Tool.removeDuplicateSdb_getMostRecentMsg_with_Sdb_getUnreadMsg(vUnreadOwnInbox, vRecentOwnInbox);
			
			
			//System.out.println("size vRecentOwnInbox= "+vRecentOwnInbox.size());
			session.setAttribute("vUnread", vUnreadOwnInbox);
			session.setAttribute("vRecent", vRecentOwnInbox);
			session.setAttribute("vlistMonitorNickname", vScopeMonitorInbox);
			String target = null;
			/*
			 * disable monitring
			 */
			//if(monitoring!=null && monitoring.equalsIgnoreCase("true")) {
			//	target = Constants.getRootWeb()+"/InnerFrame/ContactUs/dashContactUs.jsp";
			//}
			//else {
				target = Constants.getRootWeb()+"/InnerFrame/ContactUs/dashContactUs.jsp";
			//}	
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?sta_index="+sta_index+"&range="+range+"&show="+show).forward(request,response);

		}
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
