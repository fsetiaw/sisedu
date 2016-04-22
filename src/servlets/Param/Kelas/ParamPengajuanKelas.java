package servlets.Param.Kelas;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.classPoll.SearchDbClassPoll;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;

/**
 * Servlet implementation class ParamPengajuanKelas
 */
@WebServlet("/ParamPengajuanKelas")
public class ParamPengajuanKelas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ParamPengajuanKelas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("paramPk");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
		     response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			String kode_kampus = request.getParameter("atMenu");
			String scopeKampusCmd = request.getParameter("scopeKampusCmd");
			String thsms_target = Checker.getThsmsBukaKelas();
			Vector vScope = isu.getScopeUpd7des2012ProdiOnly(scopeKampusCmd,kode_kampus);
			if(vScope!=null && vScope.size()>0) {
				SearchDbClassPoll sdb = new SearchDbClassPoll(isu.getNpm());
				vScope = sdb.getClassPollRules(vScope,kode_kampus);
				request.setAttribute("vScope", vScope);
			}
			//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/PraKuliah/KelasKuliah/formParamPengajuanKelas.jsp";
			String target = Constants.getRootWeb()+"/InnerFrame/Parameter/PraKuliah/KelasKuliah/formParamPengajuanKelas.jsp";
			String uri = request.getRequestURI();
			String url_ff = PathFinder.getPath(uri, target);
			//String forceBackTo = "/InnerFrame/Notifications/krsNotification.jsp";
			request.getRequestDispatcher(url_ff+"?atMenu="+kode_kampus+"&scopeKampusCmd="+scopeKampusCmd+"&thsms_target="+thsms_target).forward(request,response);
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
