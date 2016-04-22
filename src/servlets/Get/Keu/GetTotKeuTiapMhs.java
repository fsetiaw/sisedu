package servlets.Get.Keu;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.keu.SearchDbKeu;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class GetTotKeuTiapMhs
 */
@WebServlet("/GetTotKeuTiapMhs")
public class GetTotKeuTiapMhs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTotKeuTiapMhs() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("get.heu");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			String forceGoTo=request.getParameter("forceGoTo");
			String atMenu=request.getParameter("atMenu");
			String target_thsms=request.getParameter("target_thsms");
			String target_nmpst=request.getParameter("target_nmpst");
			String target_kdpst=request.getParameter("target_kdpst");
			Vector vTmp = isu.getScopeUpd7des2012("vbak");
			if(vTmp!=null && vTmp.size()>0) {
				Vector vSummaryPMB = (Vector)session.getAttribute("vSummaryPMB");
				session.removeAttribute("vSummaryPMB");
				
				SearchDbKeu sdb = new SearchDbKeu(isu.getNpm());
				vSummaryPMB = sdb.getTotPembayaranTiapMhs(vSummaryPMB, target_kdpst, target_thsms);
				System.out.println("vSummaryPMB="+vSummaryPMB);
				session.setAttribute("vSummaryPMB",vSummaryPMB);
			}	
			String target = Constants.getRootWeb()+forceGoTo;
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?atMenu="+atMenu+"&target_thsms="+target_thsms+"&target_nmpst="+target_nmpst+"&target_kdpst="+target_kdpst).forward(request,response);
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
