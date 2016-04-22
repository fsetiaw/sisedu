package servlets.Notification;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONArray;

import beans.dbase.keu.SearchDbKeu;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class PaymentApprovalNotification
 */
@WebServlet("/PaymentApprovalNotification")
public class PaymentApprovalNotification extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentApprovalNotification() {
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
			//System.out.println("dirimi");
			//====================get payment approval========================
			//isu.getScopeUpd7des2012ReturnDistinctKdpst(command_code)
			Vector vKeu = isu.getScopeUpd7des2012ReturnDistinctKdpst("pymntApprovee");
			if(vKeu!=null && vKeu.size()>0) {
			//if(isu.isAllowTo("pymntApprovee")>0) {
				SearchDbKeu sdbk = new SearchDbKeu(isu.getNpm());
				//vKeu = sdbk.getPymntApprovalRequest(vKeu);
				JSONArray jsoa = sdbk.getPymntApprovalRequestJsonStyle(vKeu);
				//System.out.println("-----------------");
				//System.out.println(jsoa.toString());
				//System.out.println("-----------------");
				if(jsoa!=null && jsoa.length()>0) {
					session.setAttribute("jsoaPymntReq", jsoa);
				}
				//if(vKeu!=null && vKeu.size()>0) {
				//	session.setAttribute("vReqAprKeu", vKeu);
				//}	
				//System.out.println("vKeu = "+vKeu.size());
			}
			
			
			String target = Constants.getRootWeb()+"/InnerFrame/Keu/requestKeuAprovalForm.jsp"; 
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
