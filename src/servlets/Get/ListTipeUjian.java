package servlets.Get;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.PathFinder;
import beans.dbase.*;
/**
 * Servlet implementation class ListTipeUjian
 */
@WebServlet("/ListTipeUjian")
public class ListTipeUjian extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListTipeUjian() {
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
		String thsms_aktif=Checker.getThsmsNow();
		Vector vScope = isu.getScopeUpd7des2012ReturnDistinctKdpst("hasKartuUjianMenu");
		SearchDb sdb = new SearchDb(isu.getNpm());
		String listTipeUjian=sdb.getListTipeUjian(thsms_aktif, vScope);//seperated by #
		session.setAttribute("listTipeUjian", listTipeUjian);
		String target = Constants.getRootWeb()+"/InnerFrame/Perkuliahan/KartuUjian/dashKartuUjian.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		request.getRequestDispatcher(url).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
