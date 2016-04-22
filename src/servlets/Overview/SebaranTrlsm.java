package servlets.Overview;

import java.io.IOException;
import java.util.ListIterator;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.overview.GetSebaranTrlsm;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;
import beans.tools.Tool;

/**
 * Servlet implementation class SebaranTrlsm
 */
@WebServlet("/SebaranTrlsm")
public class SebaranTrlsm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SebaranTrlsm() {
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
			String thsms_now = Checker.getThsmsNow();
			String thsms_prev = Tool.returnPrevThsmsGivenTpAntara(thsms_now);
			String thsms_next = Tool.returnNextThsmsGivenTpAntara(thsms_now);
			Vector v_scope = isu.returnScopeProdiOnlySortByKampusWithListIdobj("s");
			String scope_kampus = isu.getScopeKampus("s");
			//System.out.println("scope_kampus="+scope_kampus);
			ListIterator li = v_scope.listIterator();
			//while(li.hasNext()) {
			//	String brs = (String)li.next();
			//	System.out.println(brs);
			//}
			GetSebaranTrlsm sb = new GetSebaranTrlsm(isu.getNpm());
			Vector v_overview = sb.getInfoOverviewSebaranTrlsmTable(v_scope,thsms_now);
			//Vector v_mhs_aktif = sb.getSummaryMhsAktifPerKampus(v_scope,thsms_now);
			session.setAttribute("v_overview", v_overview);
			//session.setAttribute("v_mhs_aktif", v_mhs_aktif);
			
			//Vector v_now_npm_aktif = sb.getNpmMhsAktif(v_scope, thsms_now);
			
			//sb.test();
			//c
			
			//ToUnivSatyagama/WebContent/InnerFrame/Overview/Trlsm/indexSebaranTrlsm.jsp
			String target = Constants.getRootWeb()+"/InnerFrame/Overview/Trlsm/indexSebaranTrlsm.jsp"; 
			String uri = request.getRequestURI(); 
			String url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?scope_kmp="+scope_kampus).forward(request,response);

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
