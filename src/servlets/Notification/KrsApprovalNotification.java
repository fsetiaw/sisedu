package servlets.Notification;

import java.io.IOException;
import java.util.ListIterator;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.SearchDb;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;

/**
 * Servlet implementation class KrsApprovalNotification
 */
@WebServlet("/KrsApprovalNotification")
public class KrsApprovalNotification extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KrsApprovalNotification() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.
		response.setContentType("text/html");
		HttpSession session = request.getSession(true); 
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr"); 
		if(isu==null) { 
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
		} 
		else { 
		//kode here
			System.out.println("inlobe");
			SearchDb sdb = new SearchDb(isu.getNpm());
			String scope_kampus = isu.getScopeKampus("krsPaApproval");
			//System.out.println("scope_kampus="+scope_kampus);
			Vector vScopeProdi = isu.getScopeUpd7des2012ReturnDistinctKdpst("krsPaApproval");
			String tkn_npm_pa = "";
			String tkn_kdpst = "";
			if(vScopeProdi!=null && vScopeProdi.size()>0) {
				//String tkn_kdpst = "";
				ListIterator lis = vScopeProdi.listIterator();
				while(lis.hasNext()) {
					tkn_kdpst = tkn_kdpst+(String)lis.next()+"`";
				}
				
				/*
				 *  !!!!!!updated krsPaApproval hanya untuk system perwalian!!!!!
				 *  KASUS SEPERTI PUTRI MEWAKILKAN PA LAIN DALAN SCOPE FAKULTAS MAKA krsPaApproval diisi
				 *  
				 *  TAPI KALO kasiat yg hanya approve anak yang dibimbing only tidak akan masuk keksini tkn_npm_pa = own 
				 */
				
				tkn_npm_pa = Getter.getListPembimbingAkademik(tkn_kdpst);	
				
			}
			/*
			 * jika tkn_npm_pa = maka tkn_npm_pa adalah npm user sendiri
			 */
			if(tkn_npm_pa==null || Checker.isStringNullOrEmpty(tkn_npm_pa)) {
				tkn_npm_pa = new String(isu.getNpm());
			}
			String tknKrsNotifications = sdb.getReqKrsApprovalNotification(tkn_npm_pa,tkn_kdpst,scope_kampus);
			String tknKrsNotificationsForSender = sdb.getNonHiddenReqKrsApprovalNotificationForSender();
			session.setAttribute("tknKrsNotifications", tknKrsNotifications);
			session.setAttribute("tknKrsNotificationsForSender", tknKrsNotificationsForSender);
			String target = Constants.getRootWeb()+"/InnerFrame/Notifications/krsNotification.jsp"; 
			String uri = request.getRequestURI(); 
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url).forward(request,response);

		}


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
