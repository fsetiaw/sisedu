package servlets.Summary;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Vector;
import java.util.ListIterator;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;
import beans.dbase.SearchDb;
/**
 * Servlet implementation class PMB
 */
@WebServlet("/PMB")
public class PMB extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PMB() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("pmb");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String target_thsms = request.getParameter("target_thsms");
		SearchDb sdb = new SearchDb();
		Vector vSumPmb = null;
		boolean noTarget = true;
		if(target_thsms==null || target_thsms.equalsIgnoreCase("null")) {
			vSumPmb = sdb.getSummaryPmb(isu.getScopeUpd7des2012("summaryPMB"));
		}
		else {
			noTarget = false;
			vSumPmb = sdb.getSummaryPmb(isu.getScopeUpd7des2012("summaryPMB"),target_thsms);
		}
		session.removeAttribute("vSummaryPMB");
		session.setAttribute("vSummaryPMB", vSumPmb);
		String target = Constants.getRootWeb()+"/InnerFrame/Summary/Civitas/Mhs/summaryPmb.jsp";
		String uri = request.getRequestURI();
		String url_ff = PathFinder.getPath(uri, target);
		if(noTarget) {
			request.getRequestDispatcher(url_ff+"?atMenu=summaryPmbMenu").forward(request,response);
		}
		else {
			request.getRequestDispatcher(url_ff+"?atMenu=summaryPmbMenu&target_thsms="+target_thsms).forward(request,response);
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
