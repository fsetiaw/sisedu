package servlets.update.classPoll;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.classPoll.UpdateDbClassPoll;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class insDataCpRules
 */
@WebServlet("/insDataCpRules")
public class InsDataCpRules extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsDataCpRules() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
		     response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			System.out.println("iyadeh");
			String thsms_target = request.getParameter("thsms_target");
			String target_kampus = request.getParameter("target_kampus");
			String[]kdpst = request.getParameterValues("kdpst");
			String[]alur = request.getParameterValues("alur");
			String[]kdpst_urut = request.getParameterValues("urut");
			if(kdpst!=null && kdpst.length>0) {
				System.out.println("iyadeh sampe");
				UpdateDbClassPoll udb = new UpdateDbClassPoll(isu.getNpm());
				udb.updateClassPollRules(thsms_target, target_kampus.toUpperCase(), kdpst, alur, kdpst_urut);
			}
			String target = Constants.getRootWeb()+"/InnerFrame/Parameter/PraKuliah/KelasKuliah/formParamPengajuanKelas.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
//		url+"?atMenu=kelasKuliah&scopeKampusCmd=paramKlsKuliah"
			request.getRequestDispatcher(url+"?atMenu=kelasKuliah&scopeKampusCmd=paramKlsKuliah").forward(request,response);
			
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
